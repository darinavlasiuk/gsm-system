package Logic;


import Events.SetActiveEvent;
import Events.SetWaitingEvent;
import Events.TerminateEvent;
import Events.SetFrequencyEvent;
import Listeners.VBDPanelListener;

public class VBD extends Device implements VBDPanelListener {
    private final TechnicalManager manager;
    private final String message;
    private int numberOfSentMessages=0;
    public VBD(TechnicalManager manager, String name, String message){
        super(name);
        this.manager=manager;
        this.message=message;
        setFrequency(1000); //milliseconds
        super.start();
    }
    //setters
    public void sendSMS(){
        manager.fromVBDtoBTS(PDU.encode(this.getName(), manager.randomRecipient(), message));
        numberOfSentMessages++;
    }
    //getters
    public String getMessage(){
        return message;
    }
    public int getNumberOfSentMessages(){
        return numberOfSentMessages;
    }
    @Override
    public void performAction() {
        sendSMS();
    }
    @Override
    public void perform(TerminateEvent e) {
        manager.getVBDLayer().remove(this);
        terminate();
    }

    @Override
    public void perform(SetWaitingEvent e) {
        this.setWaiting();
    }

    @Override
    public void perform(SetActiveEvent e) {
        this.setActive();
    }

    @Override
    public void perform(SetFrequencyEvent e) {
        this.setFrequency(e.getTime());
    }
}
