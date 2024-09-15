package Visual;

import javax.swing.*;
import java.awt.*;
import Events.RemoveBSCLayerEvent;

public class BSCLayerPanel extends BTSLayerPanel {

    private final String name;
    public BSCLayerPanel(String name, BSCLayersPanel panel){
        super();
        this.name=name;
        setType("BSC");

        JButton button=new JButton("Remove Layer");
        button.addActionListener(e -> {
            panel.fireRemoveBSCLayerEvent(new RemoveBSCLayerEvent(this));
        });
        button.setPreferredSize(new Dimension(200, 30));
        this.add(button, BorderLayout.SOUTH);

        int height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setPreferredSize(new Dimension(230, height-120));
    }
    public String getName(){
        return name;
    }

}


