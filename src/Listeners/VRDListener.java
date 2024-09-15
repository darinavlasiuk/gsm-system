package Listeners;

import Events.UpdateCounterEvent;

public interface VRDListener {
    void perform(UpdateCounterEvent e);
}
