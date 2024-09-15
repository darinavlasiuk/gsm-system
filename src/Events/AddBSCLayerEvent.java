package Events;

import Visual.BSCLayerPanel;

import java.util.EventObject;

public class AddBSCLayerEvent extends EventObject {
    public AddBSCLayerEvent(BSCLayerPanel source) {
        super(source);
    }
    @Override
    public BSCLayerPanel getSource() {
        return (BSCLayerPanel) super.getSource();
    }
}
