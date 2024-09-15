package Logic;

import Events.SetMessageRemovingActiveEvent;
import Events.SetMessageRemovingInactiveEvent;
import Events.TerminateEvent;
import Events.UpdateCounterEvent;
import Listeners.VRDListener;
import Listeners.VRDPanelListener;


public class VRD extends Device implements VRDPanelListener {
    private final TechnicalManager manager;
    private boolean messageRemovingIsActive;
    private int numberOfReceivedMessages=0;
    private VRDListener listener;
    public VRD(TechnicalManager manager, String name){
        super(name);
        this.manager=manager;
        messageRemovingIsActive=false;
        setFrequency(10000); //milliseconds
        super.start();
    }
    public int numberOfReceivedMessages(){
        return numberOfReceivedMessages;
    }
    public void setListener(VRDListener listener){
        this.listener=listener;
    }

    @Override
    public void performAction() {
        if(messageRemovingIsActive)
            resetCounter();
    }

    public void resetCounter(){
        numberOfReceivedMessages=0;
        fireUpdateCounterEvent(new UpdateCounterEvent(this));
    }
    public void setMessageRemovingActive(){
        messageRemovingIsActive=true;
    }
    public void setMessageRemovingInactive(){
        messageRemovingIsActive=false;
    }

    public void receiveMessage(byte[] message){
        numberOfReceivedMessages++;
        fireUpdateCounterEvent(new UpdateCounterEvent(this));
    }

    @Override
    public void perform(TerminateEvent e) {
        manager.getVRDLayer().remove(this);
        terminate();
    }

    @Override
    public void perform(SetMessageRemovingInactiveEvent e) {
        setMessageRemovingInactive();
    }

    @Override
    public void perform(SetMessageRemovingActiveEvent e) {
        setMessageRemovingActive();
    }

    public void fireUpdateCounterEvent(UpdateCounterEvent e){
        listener.perform(e);
    }
}
