package Logic;

import java.time.Instant;

public class SMS {
    private final byte[] message;
    private final Instant timeOfSending;
    public SMS(byte[] message, int storingTime){
        this.message=message;
        timeOfSending=Instant.now().plusSeconds(storingTime);
    }
    public Instant timeOfSending(){
        return timeOfSending;
    }
    public byte[] getMessage(){
        return message;
    }

}
