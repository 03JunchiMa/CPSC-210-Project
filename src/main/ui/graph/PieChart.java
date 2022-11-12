package ui.graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Convert to the pie chart
// Class Reference: https://study.163.com/course/courseLearn.htm?courseId=1209013812#/learn/video?lessonId=1278565112&courseId=1209013812
public class PieChart extends JPanel {

    private List<Part> partList = new ArrayList<>();
    private PartClickedListener partClickedListener;

    public PieChart() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                onMouseClicked(e);
            }

        });
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: focus (repaint) on the piece when clicked
    private void onMouseClicked(MouseEvent e) {
        Part activePart = null;

        // determine which part are selected
        for (Part p : partList) {
            if (p.shape == null) {
                continue;
            }

            if (p.shape.contains(e.getX(), e.getY())) {
                p.selected = true;
                activePart = p;
            } else {
                p.selected = false;
            }
        }

        if (activePart != null) {
            repaint();

            if (this.partClickedListener != null) {
                partClickedListener.partClicked(activePart.tag);
            }
        }
    }

    // EFFECTS: inner interface
    public interface PartClickedListener {
        public void partClicked(Object tag);
    }

    // MODIFIES: this
    // EFFECTS: set up the listener
    public void setPartClickedListener(PartClickedListener listener) {
        this.partClickedListener = listener;
    }

    // MODIFIES: this
    // EFFECTS: convert each data into the corresponding sector for drawing
    public void addPart(double amount, String tag, Color color) {
        if (amount <= 0) {
            return;
        }

        Part part = new Part();
        part.amount = amount;
        part.tag = tag;
        part.color = color;
        if (color == null) {
            int rgb = new Random().nextInt(0xFFFFFF);
            part.color = new Color(rgb);
        }

        partList.add(part);
    }

    // EFFECTS: return the partlist
    public List<Part> getPartList() {
        return this.partList;
    }

    // EFFECTS: calculate the corresponding portion according to the part value
    private void calculate() {
        if (partList.size() == 0) {
            return;
        }

        int totalAmount = 0;
        for (Part p : partList) {
            totalAmount += p.amount;
        }

        calculatePart(totalAmount);

        Rectangle rect = new Rectangle(30,20,200,200);
        rect.grow(-4,-4); // 往里缩一点

        int start = 90;
        for (Part p : partList) {
            p.shape = new Arc2D.Double(rect, start,0 - p.degree,Arc2D.PIE);
            start -= p.degree;
        }
    }

    // EFFECTS: calculate the different parts
    private void calculatePart(int totalAmount) {
        int totalDegrees = 0;
        for (int i = 0; i < partList.size();i++) {
            Part p = partList.get(i);
            p.degree = (int) (360 * p.amount / totalAmount);
            if (i == partList.size() - 1) {
                p.degree = 360 - totalDegrees; // 确保占满360度
            }

            totalDegrees += p.degree;
        }
    }

    // EFFECTS: paint the sector
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // Anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        this.calculate();

        for (Part part : partList) {
            if (part.shape != null) {
                g2d.setPaint(part.color);
                g2d.fill(part.shape);

                if (part.selected) {
                    g2d.setPaint(Color.GRAY);
                    g2d.setStroke(new BasicStroke(2));
                    g2d.draw(part.shape);

                }
            }
        }
    }

    // Inner class
    private static class Part {
        double amount;
        int degree;
        Color color;
        String tag;  // name tag
        Arc2D shape; // actual shape
        boolean selected;
    }
}
