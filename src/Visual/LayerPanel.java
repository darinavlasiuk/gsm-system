package Visual;

import javax.swing.*;
import java.awt.*;


public abstract class LayerPanel extends JPanel {
    private final JPanel mainInsideLayer;

    public LayerPanel(){
        this.setLayout(new BorderLayout());

        JPanel mainPanel=new JPanel(new BorderLayout());

        JPanel insideScrollPane = new JPanel();
        insideScrollPane.setLayout(new BorderLayout());

        JPanel VBDPusher = new JPanel();
        insideScrollPane.add(VBDPusher, BorderLayout.CENTER);

        mainInsideLayer =new JPanel();
        mainInsideLayer.setLayout(new BoxLayout(mainInsideLayer, BoxLayout.Y_AXIS));
        insideScrollPane.add(mainInsideLayer, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(insideScrollPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(scrollPane);
        this.add(mainPanel);

        int height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setPreferredSize(new Dimension(230, height-50));


        revalidate();
        repaint();
    }

    //getters
    public JPanel mainInsideLayer(){
        return mainInsideLayer;
    }

    public void removePanel(JPanel panel){
        mainInsideLayer.remove(panel);
        revalidate();
        repaint();
    }

}




