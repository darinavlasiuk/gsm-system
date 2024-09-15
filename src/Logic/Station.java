package Logic;

import Listeners.StationPanelListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public abstract class Station extends Thread implements StationPanelListener {
    private final TechnicalManager manager;
    private int waitingMessages=0;
    private boolean working=false;
    private int storingTime;
    private static final int massagesLimit=5;
    private List<SMS> smses = new ArrayList<>();
    public Station(TechnicalManager manager, String name){
        super(name);
        this.manager=manager;
        start();
    }
    //setters
    public void addWaitingMessage(){
        waitingMessages++;
    }
    public void setStoringTime(int time){
        this.storingTime=time;
    }

    //getters
    public TechnicalManager manager(){
        return manager;
    }
    public int numberOfStoredMessages(){
        return smses.size()+waitingMessages;
    }

    public abstract void passSMS(byte[] message);
    public void deleteSMS(SMS sms){
        smses.remove(sms);
    }
    public synchronized void receiveSMS(byte[] message){
        SMS sms=new SMS(message, storingTime);
        addSMS(sms);
        waitingMessages--;
    }
    public synchronized void addSMS(SMS sms){
        smses.add(sms);
    }

    @Override
    public void run() {
        while(working) {

            Instant now=Instant.now();
            synchronized (this) {
                for (int i = 0; i < smses.size(); ++i) {
                    if (smses.get(i).timeOfSending().isBefore(now)) {
                        passSMS(smses.get(i).getMessage());
                        deleteSMS(smses.get(i));
                    }
                }
            }
            try{
                Thread.sleep(100);
            } catch(InterruptedException ignored){}
        }
    }
    public boolean isBusy() {
        return numberOfStoredMessages()+waitingMessages==massagesLimit;
    }
    public synchronized void terminate() {
        for(int i=0; i<smses.size(); ++i){
            passSMS(smses.get(i).getMessage());
        }
        commitSuicide();
    }

    @Override
    public void start() {
        working=true;
        super.start();
    }

    public void commitSuicide(){
        smses=new ArrayList<>();
        working=false;
    }

}
