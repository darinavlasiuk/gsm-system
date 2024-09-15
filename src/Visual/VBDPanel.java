package Visual;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import Events.SetActiveEvent;
import Events.SetFrequencyEvent;
import Events.SetWaitingEvent;
import Events.TerminateEvent;
import Listeners.VBDPanelListener;


public class VBDPanel extends JPanel {
    private final String name;
    private final VBDPanelListener listener;
    private final VBDLayerPanel layer;
    public VBDPanel(String name, String message, VBDLayerPanel layer, VBDPanelListener listener){
        this.name=name;
        this.layer=layer;
        this.listener=listener;

        this.setLayout(new GridLayout(5, 0, 0, 0));

        JLabel label=new JLabel("VBD +"+name);
        this.add(label);

        JTextField messageText=new JTextField(message);
        messageText.setEditable(false);
        this.add(messageText);

        JSlider slider=new JSlider(1, 100);
        slider.addChangeListener(e -> {
            fireSetFrequencyEvent(new SetFrequencyEvent(this, 10000/slider.getValue()));
        });
        this.add(slider);

        JComboBox<String> box=new JComboBox<>();
        box.addItem("ACTIVE");
        box.addItem("WAITING");
        this.add(box);

        box.addActionListener(e ->{
            if (Objects.equals(box.getSelectedItem(), "ACTIVE")) {
                fireSetActiveEvent(new SetActiveEvent(this));
            } else {
                fireSetWaitingEvent(new SetWaitingEvent(this));
            }

        });


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
    public void fireSetWaitingEvent(SetWaitingEvent e){
        listener.perform(e);
    }
    public void fireSetActiveEvent(SetActiveEvent e){
        listener.perform(e);
    }
    public void fireSetFrequencyEvent(SetFrequencyEvent e){
        listener.perform(e);
    }

}








