package Events;

import Logic.Layer;
import Logic.Station;

import java.util.EventObject;

public class AddStationEvent extends EventObject {
    private final Station station;
    public AddStationEvent(Layer source, Station station) {
        super(source);
        this.station=station;
    }
    @Override
    public Layer getSource() {
        return (Layer) super.getSource();
    }
    public Station getStation(){
        return station;
    }
}