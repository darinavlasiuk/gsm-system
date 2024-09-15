package Logic;

import Events.AddStationEvent;
import Listeners.LayerListener;

public class BSCLayer extends Layer {
    private final String name;
    private final LayerListener listener;
    public BSCLayer(TechnicalManager manager, String name, LayerListener listener){
        super(manager);
        this.name=name;
        this.listener=listener;
        addStation();
    }
    public String name(){
        return name;
    }
    @Override
    public synchronized void addStation(){
        String name=""+stationCounter();
        BSC newBSC=new BSC(manager(),this, name);
        stations().add(newBSC);
        fireAddStationEvent(new AddStationEvent(this, newBSC));

        increaseStationCounter();
    }
    public void fireAddStationEvent(AddStationEvent e) {
        listener.perform(e);
    }

    public synchronized void terminate() {
        terminateAllStations();
    }
    public synchronized void terminateAllStations(){
        for(Station station: stations()){
            station.terminate();
        }
    }

}
