package Visual;

import javax.swing.*;
import java.awt.*;

import Events.AddBSCLayerEvent;
import Events.RemoveBSCLayerEvent;

public class BSCLayersPanel extends JPanel{
    private final JPanel insidePanel;
    private int layerCounter=0;
    private final VisualManager manager;

    public BSCLayersPanel(VisualManager manager){
        this.manager=manager;

        this.setLayout(new BorderLayout());

        JButton button =new JButton("Add Layer");
        button.setPreferredSize(new Dimension(680, 30));
        button.addActionListener(e -> {
            addLayer();
        });
        this.add(button, BorderLayout.SOUTH);

        insidePanel=new JPanel();
        insidePanel.setLayout(new BoxLayout(insidePanel, BoxLayout.LINE_AXIS));

        JPanel mainPanel=new JPanel(new BorderLayout());
        JScrollPane scrollPane=new JScrollPane(insidePanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        this.add(mainPanel, BorderLayout.CENTER);

    }
    public void addLayer(){
        BSCLayerPanel newPanel=new BSCLayerPanel(""+layerCounter, this);
        manager.fireAddBSCLayerEvent(new AddBSCLayerEvent(newPanel));
        insidePanel.add(newPanel);
        layerCounter++;

        revalidate();
        repaint();
    }

    public void fireRemoveBSCLayerEvent(RemoveBSCLayerEvent e){
        manager.fireRemoveBSCLayerEvent(e);
        insidePanel.remove(e.getSource());

        revalidate();
        repaint();
    }

}




