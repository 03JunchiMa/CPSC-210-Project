package ui.component.panel;

import javax.swing.*;
import java.awt.*;

// Panel for drawing the fractal tree
// Painting Reference: https://zhuanlan.zhihu.com/p/30381139
public class FractalTreePanel extends JPanel {

    public FractalTreePanel() {
        this.setLayout(new BorderLayout());
    }

    // EFFECTS: draw the fractal tree recursively
    private void drawTree(Graphics2D g, int x1, int y1, double angle, int depth) throws InterruptedException {
        // drawing the fractal tree,depth is the depth for the recursion
        if (depth == 0) {
            return;
        }

        int x2 = x1 + (int) (Math.cos(Math.toRadians(angle)) * depth * 10.0);
        //Math.toRadians,radian=degrees/π
        int y2 = y1 + (int) (Math.sin(Math.toRadians(angle)) * depth * 10.0);
        g.setColor(Color.getHSBColor(0.25f - 0.125f / depth, 0.9f, 0.6f)); // color gradient
        g.setStroke(new BasicStroke(depth)); // the thickness of the stroke
        g.drawLine(x1, y1, x2, y2);

        drawTree(g, x2, y2, angle - 30, depth - 1);// recursion
        drawTree(g, x2, y2, angle + 30, depth - 1);
    }

    // EFFECTS: override the paint method to draw the fractal tree
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D gc = (Graphics2D) g;
        gc.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);// Anti-aliasing
        try {
            drawTree(gc, 1350 / 2, 730 * 12 / 13, -90, 7);
        } catch (InterruptedException e) {
            System.out.println("Exception: drawing fractal tree");
        }
    }

}
