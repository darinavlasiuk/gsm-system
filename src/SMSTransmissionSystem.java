import Logic.TechnicalManager;
import Visual.VisualManager;

public
class SMSTransmissionSystem {
    private final TechnicalManager tech;
    private final VisualManager visual;
    public SMSTransmissionSystem(){
        tech = new TechnicalManager();
        visual = new VisualManager();
        setAllListeners();
        visual.setVisible(true);
    }
    public void setAllListeners(){
        visual.setVisualManagerListener(tech);
        tech.setLayerListeners(visual.getLayerListenerBTSSender(),
                visual.getLayerListenerBTSRecipient());
    }
}
