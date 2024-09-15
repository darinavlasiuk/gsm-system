package Events;

import Logic.VRD;

import java.util.EventObject;

public class UpdateCounterEvent extends EventObject {
    private final int numberOfReceivedMessages;
    public UpdateCounterEvent(VRD source) {
        super(source);
        this.numberOfReceivedMessages=source.numberOfReceivedMessages();
    }

    @Override
    public VRD getSource() {
        return (VRD) super.getSource();
    }
    public int numberOfReceivedMessages(){
        return numberOfReceivedMessages;
    }
}