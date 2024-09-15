package Listeners;

import Events.SetMessageRemovingActiveEvent;
import Events.SetMessageRemovingInactiveEvent;
import Events.TerminateEvent;

public interface VRDPanelListener{
    void perform(TerminateEvent e);
    void perform(SetMessageRemovingInactiveEvent e);
    void perform(SetMessageRemovingActiveEvent e);

}
