package Events;

import java.util.EventObject;

public class TerminateEvent extends EventObject {
    public TerminateEvent(Object source) {
        super(source);
    }
}
