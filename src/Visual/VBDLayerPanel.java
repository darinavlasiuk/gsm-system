package Visual;

import javax.swing.*;
import java.awt.*;

import Events.AddVBDEvent;
import Logic.VBD;

public class VBDLayerPanel extends LayerPanel {
    private final JPanel mainInsideLayer;
    private final VisualManager manager;

    public VBDLayerPanel(VisualManager manager){
        super();
        this.manager=manager;
        mainInsideLayer=mainInsideLayer();

        JButton AddVBDButton=new JButton("Add VBD");
        AddVBDButton.setPreferredSize(new Dimension(200, 30));
        this.add(AddVBDButton, BorderLayout.SOUTH);

        AddVBDButton.addActionListener(e -> {
            String message=JOptionPane.showInputDialog(null, "Print you message:");
            if(message!=null){
                addVBD(message);
                revalidate();
                repaint();
            }
        });
    }

    public void addVBD(String message){
        VBD newVBD=manager.getVisualManagerListener().perform(new AddVBDEvent(this, message));
        VBDPanel newPanel=new VBDPanel(newVBD.getName(), message, this, newVBD);
        mainInsideLayer.add(newPanel);

        revalidate();
        repaint();
    }

}



