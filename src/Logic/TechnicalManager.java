package Logic;

import Events.*;
import Listeners.LayerListener;
import Listeners.VisualManagerListener;
import Visual.BSCLayerPanel;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import Exceptions.RecipientNotFoundException;

public class TechnicalManager implements VisualManagerListener{
    private final List<VBD> VBDLayer=new ArrayList<>();
    private final BTSLayer BTSLayerSender=new BTSLayer(this, false);
    private final List<BSCLayer> BSCLayers=new ArrayList<>();
    private final BTSLayer BTSLayerRecipient=new BTSLayer(this, true);
    private final List<VRD> VRDLayer=new ArrayList<>();
    private long deviceCounter=48000000000L;
    public TechnicalManager(){
    }
    public void setLayerListeners(LayerListener BTSSender, LayerListener BTSRecipient){
        BTSLayerSender.setListener(BTSSender);
        BTSLayerRecipient.setListener(BTSRecipient);
        BTSLayerSender.addStation();
        BTSLayerRecipient.addStation();
    }

    public List<VBD> getVBDLayer(){
        return VBDLayer;
    }
    public List<VRD> getVRDLayer(){
        return VRDLayer;
    }

    public VBD addSender(String message) {
        VBD newSender=new VBD(this, ""+deviceCounter, message);
        deviceCounter++;
        VBDLayer.add(newSender);
        return newSender;
    }
    public VRD addRecipient() {
        VRD newRecipient=new VRD(this, ""+deviceCounter);
        deviceCounter++;
        VRDLayer.add(newRecipient);
        return newRecipient;
    }
    public String randomRecipient(){
        int index=((int)(Math.random()*VRDLayer.size()*10000))%VRDLayer.size();
        return VRDLayer.get(index).getName();
    }
    public void addBSCLayer(BSCLayerPanel panel) {
        BSCLayer newLayer=new BSCLayer(this, panel.getName(), panel);
        BSCLayers.add(newLayer);
    }
    public void fromVBDtoBTS(byte[] message){
        BTSLayerSender.receiveMessage(message);
    }
    public void fromBTStoBSC(byte[] message){
        BSCLayers.get(0).receiveMessage(message);
    }
    public void fromBSC(byte[] message, BSCLayer senderLayer){
        if(BSCLayers.get(BSCLayers.size()-1).name().equals(senderLayer.name())){
            fromBSCtoBTS(message);
        }
        else{
            int recipient=BSCLayers.size()+1;
            for(int i=0; i<BSCLayers.size(); ++i)
                if(BSCLayers.get(i).name().equals(senderLayer.name())){
                    recipient=i+1;
                    break;
                }

            fromBSCtoBSC(message, recipient);
        }
    }
    public void fromBSCtoBSC(byte[] message, int recipientLayer){
        if(recipientLayer>=BSCLayers.size()) fromBSCtoBTS(message);
        else BSCLayers.get(recipientLayer).receiveMessage(message);
    }
    public void fromBSCtoBTS(byte[] message){
        BTSLayerRecipient.receiveMessage(message);
    }
    public void fromBTStoVRD(byte[] message, String recipient) {
        try{
            sendToRecipient(message, recipient);
        } catch (RecipientNotFoundException e){
            e.printStackTrace();
        }
    }
    public void sendToRecipient(byte[] message, String recipient) throws RecipientNotFoundException {
        boolean recipientFound = false;

        List<VRD> listCopy = VRDLayer;
        VRD copy = null;

        for(int i=0; i<listCopy.size(); ++i) {
            if(listCopy.get(i).getName().equals(recipient)) {
                copy = listCopy.get(i);
                recipientFound = true;
                break;
            }
        }

        synchronized (this) {
            if(copy!=null && !copy.getName().equals(recipient)) recipientFound = false;

            if(copy!=null && recipientFound) copy.receiveMessage(message);
            else
                throw new RecipientNotFoundException("Recipient "+recipient+ " has NOT been found. Message lost.");
        }
    }
    @Override
    public VBD perform(AddVBDEvent e) {
        return addSender(e.getMessage());
    }

    @Override
    public VRD perform(AddVRDEvent e) {
        return addRecipient();
    }

    @Override
    public void perform(AddBSCLayerEvent e) {
        addBSCLayer(e.getSource());
    }

    @Override
    public synchronized void perform(RemoveBSCLayerEvent e){
        for(int i=0; i<BSCLayers.size(); ++i){
            if(BSCLayers.get(i).name().equals(e.getSource().getName())){
                BSCLayers.get(i).terminate();
                BSCLayers.remove(BSCLayers.get(i));
            }
        }
    }

    @Override
    public void perform(SaveInfoEvent event) {
        try (
                BufferedWriter bw = Files.newBufferedWriter(Paths.get("src/source.bin"))
        ) {
            List<VBD> copy=VBDLayer;
            for(VBD device: copy){
                String line="+"+device.getName()+" sent "+device.getNumberOfSentMessages()+" times: '"+device.getMessage()+"'";
                bw.write(line);
                bw.newLine();
            }

        } catch(IOException e) {
            e.getStackTrace();
        }
        System.exit(0);
    }
}


