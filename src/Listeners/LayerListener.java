package Listeners;

import Events.AddStationEvent;

public interface LayerListener{
    void perform(AddStationEvent e);
}
