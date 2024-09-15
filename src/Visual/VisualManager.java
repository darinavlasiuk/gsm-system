package Visual;

import Events.AddBSCLayerEvent;
import Events.RemoveBSCLayerEvent;
import Events.SaveInfoEvent;
import Listeners.LayerListener;
import Listeners.VisualManagerListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class VisualManager extends JFrame{

    private VisualManagerListener listener;
    private final VRDLayerPanel VRDLayer;
    private final BSCLayersPanel BSCLayers;
    private final BTSLayerPanel BTSLayerSender;
    private final BTSLayerPanel BTSLayerRecipient;

    public VisualManager() {

        this.getContentPane().setLayout(new BorderLayout());

        //VBD Panel
        VBDLayerPanel VBDLayer = new VBDLayerPanel(this);
        this.add(VBDLayer, BorderLayout.LINE_START);


        //VRD Panel
        VRDLayer = new VRDLayerPanel(this);
        this.add(VRDLayer, BorderLayout.LINE_END);


        // Stations
        JPanel stationsLayer=new JPanel(new BorderLayout());
        this.add(stationsLayer, BorderLayout.CENTER);

        //BTS Sender Panel
        BTSLayerSender=new BTSLayerPanel();
        stationsLayer.add(BTSLayerSender, BorderLayout.LINE_START);


        //BTS Recipient Panel
        BTSLayerRecipient = new BTSLayerPanel();
        stationsLayer.add(BTSLayerRecipient, BorderLayout.LINE_END);


        //BSC stations
        BSCLayers=new BSCLayersPanel(this);
        stationsLayer.add(BSCLayers, BorderLayout.CENTER);


        this.pack();
        revalidate();
        repaint();

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                fireSaveInfoEvent(new SaveInfoEvent(this));
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    public LayerListener getLayerListenerBTSSender(){
        return BTSLayerSender;
    }
    public LayerListener getLayerListenerBTSRecipient(){
        return BTSLayerRecipient;
    }
    public VisualManagerListener getVisualManagerListener(){
        return listener;
    }

    public void setVisualManagerListener(VisualManagerListener listener){
        this.listener=listener;
        BSCLayers.addLayer();
    }

    public void fireAddBSCLayerEvent(AddBSCLayerEvent e){
        listener.perform(e);
    }
    public void fireRemoveBSCLayerEvent(RemoveBSCLayerEvent e) {
        listener.perform(e);
    }
    public void fireSaveInfoEvent(SaveInfoEvent e){
        listener.perform(e);
    }
}


