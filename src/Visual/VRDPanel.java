package Visual;

import Events.SetMessageRemovingActiveEvent;
import Events.SetMessageRemovingInactiveEvent;
import Events.TerminateEvent;
import Events.UpdateCounterEvent;
import Listeners.VRDListener;
import Listeners.VRDPanelListener;

import javax.swing.*;
import java.awt.*;

public class VRDPanel extends JPanel implements VRDListener {
    private final String name;
    private final JLabel receivedMessages;
    private final VRDPanelListener listener;
    private final VRDLayerPanel layer;

    public VRDPanel(String name, VRDLayerPanel layer,  VRDPanelListener listener){
        this.name=name;
        this.layer=layer;
        this.listener=listener;

        this.setLayout(new GridLayout(4, 0, 0, 0));

        JLabel label=new JLabel("VRD +"+name);
        this.add(label);

        receivedMessages=new JLabel("0 messages");
        this.add(receivedMessages);


        JCheckBox checkBox=new JCheckBox("Remove messages");
        checkBox.addActionListener(e->{
            if(checkBox.isSelected()){
                fireSetMessageRemovingActiveEvent(new SetMessageRemovingActiveEvent(this));
            }
            else{
                fireSetMessageRemovingInactiveEvent(new SetMessageRemovingInactiveEvent(this));
            }

        });
        this.add(checkBox);


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
    public void fireSetMessageRemovingInactiveEvent(SetMessageRemovingInactiveEvent e){
        listener.perform(e);
    }
    public void fireSetMessageRemovingActiveEvent(SetMessageRemovingActiveEvent e){
        listener.perform(e);
    }

    @Override
    public void perform(UpdateCounterEvent e) {
        if(e.getSource().getName().equals(name))
            receivedMessages.setText(e.numberOfReceivedMessages()+" messages");

        revalidate();
        repaint();
    }
}





