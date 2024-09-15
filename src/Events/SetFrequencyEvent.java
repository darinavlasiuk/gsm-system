package Events;

import java.util.EventObject;

public class SetFrequencyEvent extends EventObject {
    private final int time;
    public SetFrequencyEvent(Object source, int milliseconds) {
        super(source);
        this.time=milliseconds;
    }
    public int getTime(){
        return time;
    }

}