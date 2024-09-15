package Events;

import Visual.BSCLayerPanel;

import java.util.EventObject;

public class RemoveBSCLayerEvent extends EventObject {
    public RemoveBSCLayerEvent(BSCLayerPanel source) {
        super(source);
    }

    @Override
    public BSCLayerPanel getSource() {
        return (BSCLayerPanel) super.getSource();
    }
}