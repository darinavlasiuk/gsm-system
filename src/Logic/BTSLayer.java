package Logic;

import Events.AddStationEvent;
import Listeners.LayerListener;

public class BTSLayer extends Layer{
    private final boolean isRecipient;
    private LayerListener listener;
    public BTSLayer(TechnicalManager manager, boolean isRecipient){
        super(manager);
        this.isRecipient=isRecipient;
    }
    public void setListener(LayerListener listener){
        this.listener=listener;
    }
    @Override
    public synchronized void addStation() {
        String name=""+stationCounter();
        BTS newBTS=new BTS(manager(), name, isRecipient, this);
        stations().add(newBTS);
        fireAddStationEvent(new AddStationEvent(this, newBTS));

        increaseStationCounter();
    }
    public void fireAddStationEvent(AddStationEvent e) {
        listener.perform(e);
    }
}
