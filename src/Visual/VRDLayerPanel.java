package Visual;

import Events.AddVRDEvent;
import Logic.VRD;

import javax.swing.*;
import java.awt.*;

public class VRDLayerPanel extends LayerPanel {
    private final JPanel mainInsideLayer;
    private final VisualManager manager;

    public VRDLayerPanel(VisualManager manager){
        super();

        this.manager=manager;
        mainInsideLayer=mainInsideLayer();

        JButton AddVRDButton=new JButton("Add VRD");
        AddVRDButton.setPreferredSize(new Dimension(200, 30));

        AddVRDButton.addActionListener(e -> {
            addVRD();
            revalidate();
            repaint();

        });
        this.add(AddVRDButton, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    public void addVRD(){
        VRD newVRD=manager.getVisualManagerListener().perform(new AddVRDEvent(this));
        VRDPanel newPanel=new VRDPanel(newVRD.getName(), this, newVRD);
        newVRD.setListener(newPanel);
        mainInsideLayer.add(newPanel);
    }

}


