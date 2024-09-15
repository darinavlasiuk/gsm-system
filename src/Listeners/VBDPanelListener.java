package Listeners;

import Events.SetFrequencyEvent;
import Events.SetWaitingEvent;
import Events.TerminateEvent;
import Events.SetActiveEvent;

public interface VBDPanelListener{
    void perform(TerminateEvent e);
    void perform(SetWaitingEvent e);
    void perform(SetActiveEvent e);
    void perform(SetFrequencyEvent e);

}
