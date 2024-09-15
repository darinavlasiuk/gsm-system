package Logic;
import Events.TerminateEvent;
public class BTS extends Station{
    private final boolean isRecipient;
    private final BTSLayer layer;
    public BTS(TechnicalManager manager, String name, boolean isRecipient, BTSLayer layer) {
        super(manager, name);
        super.setStoringTime(3);
        this.isRecipient=isRecipient;
        this.layer=layer;
    }

    @Override
    public void passSMS(byte[] message) {
        if (!isRecipient) {
            manager().fromBTStoBSC(message);
        } else {
            manager().fromBTStoVRD(message, PDU.decodeRecipient(message));
        }
    }

    @Override
    public void perform(TerminateEvent e){
        layer.removeStation(this);
        terminate();
    }
}
