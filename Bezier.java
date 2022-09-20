import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class Bezier extends JFrame {
    private static final Color bgColor = new Color(0x282c34);
    private static final Color keyPointColor = new Color(0xf1f1f1);
    private static final Color lineColor = new Color(0x57afef);
    private static final Color curveColor = new Color(0xe5b55f);

    private static Point2D[] keyPoints;
    private static Ellipse2D.Double[] shapes;
    private static int keyPointCounter;

    public static void main(String[] args) { new Bezier(); }

    public Bezier() {
        super("Bézier");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        keyPointCounter = 0;
        keyPoints = new Point2D[4];
        shapes = new Ellipse2D.Double[4];
        Panel panel = new Panel();
        panel.setPreferredSize(new Dimension(800, 600));
        panel.setBackground(bgColor);

        setContentPane(panel);
        setVisible(true);
    }

    static class Panel extends JPanel {
        private int currentKeyPoint = -1;

        public Panel() {
            this.addMouseListener(new MouseAction());
            this.addMouseMotionListener(new MouseDrag());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D graphics = (Graphics2D) g;

            graphics.setColor(lineColor);

            for (int i = 0; i < keyPointCounter; i++) {

                graphics.setColor(keyPointColor);
                graphics.draw(shapes[i]);
                graphics.setColor(lineColor);

                if (keyPointCounter > 1 && i < (keyPointCounter - 1)) {
                    // Dibuja una línea entre los puntos clave
                    graphics.drawLine((int) keyPoints[i].getX(), (int) keyPoints[i].getY(),
                            (int) keyPoints[i + 1].getX(),
                            (int) keyPoints[i + 1].getY()
                    );
                }
            }

            final double t = 0.001;

            if (keyPointCounter == 3) {
                graphics.setColor(curveColor);
                for (double k = t; k <= 1 + t; k += t) {
                    double r = 1 - k;
                    double x = Math.pow(r, 2) * keyPoints[0].getX() + 2 * k * r * keyPoints[1].getX()
                            + Math.pow(k, 2) * keyPoints[2].getX();

                    double y = Math.pow(r, 2) * keyPoints[0].getY() + 2 * k * r * keyPoints[1].getY()
                            + Math.pow(k, 2) * keyPoints[2].getY();

                    graphics.drawOval((int) x, (int) y, 1, 1);
                }
            }

            if (keyPointCounter == 4) {
                graphics.setColor(curveColor);
                for (double k = t; k <= 1 + t; k += t) {
                    double r = 1 - k;
                    double x = Math.pow(r, 3) * keyPoints[0].getX() + 3 * k * Math.pow(r, 2) * keyPoints[1].getX()
                            + 3 * Math.pow(k, 2) * (1 - k) * keyPoints[2].getX() + Math.pow(k, 3) * keyPoints[3].getX();
                    double y = Math.pow(r, 3) * keyPoints[0].getY() + 3 * k * Math.pow(r, 2) * keyPoints[1].getY()
                            + 3 * Math.pow(k, 2) * (1 - k) * keyPoints[2].getY() + Math.pow(k, 3) * keyPoints[3].getY();
                    graphics.drawOval((int) x, (int) y, 1, 1);
                }
            }
        }

        class MouseAction extends MouseAdapter {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (keyPointCounter < 4) {
                        double x = e.getX();
                        double y = e.getY();
                        keyPoints[keyPointCounter] = new Point2D.Double(x, y);
                        shapes[keyPointCounter] = new Ellipse2D.Double(x - 4, y - 4, 8, 8);
                        keyPointCounter++;
                        repaint();
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                for (int i = 0; i < keyPointCounter; i++) {
                    if (shapes[i].contains(e.getPoint())) {
                        currentKeyPoint = i;
                        break;
                    } else {
                        currentKeyPoint = -1;
                    }
                }
            }
        }

        class MouseDrag extends MouseMotionAdapter {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (currentKeyPoint != -1) {
                    double x = e.getX();
                    double y = e.getY();
                    keyPoints[currentKeyPoint] = new Point2D.Double(x, y);
                    shapes[currentKeyPoint] = new Ellipse2D.Double(x - 4, y - 4, 8, 8);
                    repaint();
                }
            }
        }
    }
}
