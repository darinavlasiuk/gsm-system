package Visual;

import Events.AddStationEvent;
import Listeners.LayerListener;
import Logic.Station;

import javax.swing.*;

public class BTSLayerPanel extends LayerPanel implements LayerListener {
    private final JPanel mainInsideLayer;
    private String type="BTS";

    public BTSLayerPanel(){
        super();
        this.mainInsideLayer=mainInsideLayer();
    }
    public void setType(String type){
        this.type=type;
    }

    public void addStation(Station station){
        StationPanel newStation=new StationPanel(station.getName(), type,this, station);
        mainInsideLayer.add(newStation);

        revalidate();
        repaint();
    }

    @Override
    public void perform(AddStationEvent e) {
        addStation(e.getStation());
    }
}
