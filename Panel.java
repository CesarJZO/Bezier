import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class Panel extends JPanel {
    public static Point2D[] keyPointP;
    public static Ellipse2D.Double[] keyPointE;
    public static int keyPointNum;
    public static double t = 0.001;
    public static boolean flagShow = true;
    public int keyPointID = -1;

    public Panel() {
        this.addMouseListener(new MouseAction());
        this.addMouseMotionListener(new MouseAction());
    }

    @Override
    protected void paintComponent(Graphics gs) {
        // Sobrescribir repintar
        super.paintComponent(gs);
        Graphics2D g = (Graphics2D) gs;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.BLUE);
        if (flagShow) {
            for (int i = 0; i < keyPointNum; i++) {
                if (i > 0 && i < (keyPointNum - 1)) {
                    g.fill(keyPointE[i]);
                } else {
                    g.draw(keyPointE[i]);
                }
                if (keyPointNum > 1 && i < (keyPointNum - 1)) {
                    g.drawLine((int) keyPointP[i].getX(), (int) keyPointP[i].getY(),
                        (int) keyPointP[i + 1].getX(),
                        (int) keyPointP[i + 1].getY()
                    );
                }
                if (i == 0) {
                    g.drawString("1", (int) keyPointE[i].x, (int) keyPointE[i].y);
                } else if (i == 1) {
                    g.drawString("2", (int) keyPointE[i].x, (int) keyPointE[i].y);
                } else if (i == 2) {
                    g.drawString("3", (int) keyPointE[i].x, (int) keyPointE[i].y);
                } else if (i == 3) {
                    g.drawString("4", (int) keyPointE[i].x, (int) keyPointE[i].y);
                }
            }
        }
        
        if (keyPointNum == 3) {
            double x, y;
            g.setColor(Color.RED);
            for (double k = t; k <= 1 + t; k += t) {
                double r = 1 - k;
                x = Math.pow(r, 2) * keyPointP[0].getX()
                        + 2 * k * r * keyPointP[1].getX() + Math.pow(k, 2) * keyPointP[2].getX();

                y = Math.pow(r, 2) * keyPointP[0].getY()
                        + 2 * k * r * keyPointP[1].getY() + Math.pow(k, 2) * keyPointP[2].getY();

                
                g.drawOval((int) x, (int) y, 1, 1);
            }
        }
        
        if (keyPointNum == 4) {
            double x, y;
            g.setColor(Color.RED);
            for (double k = t; k <= 1 + t; k += t) {
                double r = 1 - k;
                x = Math.pow(r, 3) * keyPointP[0].getX() + 3 * k * Math.pow(r, 2) * keyPointP[1].getX()
                        + 3 * Math.pow(k, 2) * (1 - k) * keyPointP[2].getX() + Math.pow(k, 3) * keyPointP[3].getX();
                y = Math.pow(r, 3) * keyPointP[0].getY() + 3 * k * Math.pow(r, 2) * keyPointP[1].getY()
                        + 3 * Math.pow(k, 2) * (1 - k) * keyPointP[2].getY() + Math.pow(k, 3) * keyPointP[3].getY();
                g.drawOval((int) x, (int) y, 1, 1);
            }
        }
    }

    private class MouseAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (keyPointNum < 4) {
                    double x = e.getX();
                    double y = e.getY();
                    keyPointP[keyPointNum] = new Point2D.Double(x, y);
                    keyPointE[keyPointNum] = new Ellipse2D.Double(x - 4, y - 4, 8, 8);
                    keyPointNum++;
                    repaint();
                }
            }
            else if (e.getButton() == MouseEvent.BUTTON3) {
                flagShow = false;
                repaint();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            for (int i = 0; i < keyPointNum; i++) {
                if (keyPointE[i].contains((Point2D) e.getPoint())) {
                    keyPointID = i;
                    break;
                } else {
                    keyPointID = -1;
                }
            }
        }
    }
}
