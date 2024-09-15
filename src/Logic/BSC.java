package Logic;

import Events.TerminateEvent;

public class BSC extends Station{
    private final BSCLayer layer;
    public BSC(TechnicalManager manager, BSCLayer layer, String name){
        super(manager, name);
        this.layer=layer;
    }
    public void randomTime(){
        setStoringTime(((int)(Math.random()*10000))%11+5);
    }

    @Override
    public void receiveSMS(byte[] message) {
        randomTime();
        super.receiveSMS(message);
    }

    @Override
    public void passSMS(byte[] message) {
        manager().fromBSC(message, layer);
    }

    @Override
    public void perform(TerminateEvent e) {
        layer.removeStation(this);
        terminate();
    }
}