package Visual;

import Events.TerminateEvent;
import Listeners.StationPanelListener;

import javax.swing.*;
import java.awt.*;

public
class StationPanel extends JPanel {
    private final String name;
    private final StationPanelListener listener;
    private final LayerPanel layer;

    public StationPanel(String name, String type, LayerPanel layer, StationPanelListener listener){
        this.name=name;
        this.layer=layer;
        this.listener=listener;

        this.setLayout(new GridLayout(2, 0, 0, 0));

        JLabel label=new JLabel(type+" "+name);
        this.add(label);

        JButton button=new JButton("Terminate");
        button.addActionListener(e ->{
            fireTerminateEvent(new TerminateEvent(this));

        });

        button.setPreferredSize(new Dimension(180, 30));
        this.add(button);

    }
    public String name(){
        return name;
    }
    public void fireTerminateEvent(TerminateEvent e){
        listener.perform(e);
        layer.removePanel(this);
    }

}


