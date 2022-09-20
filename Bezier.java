import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class Bezier extends Panel {
    public Bezier() {
        JFrame f = new JFrame();
        f.setTitle("Curva de Bezier");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(800, 600);
        f.setLocationRelativeTo(null);

        keyPointNum = 0;
        keyPointP = new Point2D[4];
        keyPointE = new Ellipse2D.Double[4];
        Panel bezierPanel = new Panel();
        bezierPanel.setPreferredSize(new Dimension(800, 600));
        bezierPanel.setBackground(Color.WHITE);

        f.setContentPane(bezierPanel);
        f.setVisible(true);
    }
}
