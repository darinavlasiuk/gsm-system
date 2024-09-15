package Events;

import java.util.EventObject;

public class AddVBDEvent extends EventObject {
    private final String message;
    public AddVBDEvent(Object source, String message) {
        super(source);
        this.message=message;
    }
    public String getMessage(){
        return message;
    }
}
