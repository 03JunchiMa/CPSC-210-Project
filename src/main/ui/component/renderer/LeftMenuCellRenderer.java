package ui.component.renderer;

import ui.component.LeftMenuItem;

import javax.swing.*;
import java.awt.*;

// The customized renderer for the left menu
// Class reference: https://study.163.com/course/courseLearn.htm?courseId=1209013812#/learn/video?lessonId=1278646339&courseId=1209013812
public class LeftMenuCellRenderer extends JLabel implements ListCellRenderer<LeftMenuItem> {

    // EFFECTS: initialize the cell renderer
    public LeftMenuCellRenderer() {
        this.setPreferredSize(new Dimension(150, 40));
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setForeground(new Color(0x333333));
        this.setBorder(BorderFactory.createEmptyBorder(10,4,10,4));

        this.setOpaque(true);
    }

    // EFFECTS: change the color of the corresponding list entry when selected
    @Override
    public Component getListCellRendererComponent(JList list,
                                                  LeftMenuItem value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        String text = value.getText();
        this.setText(text);

        if (value.getText().equals("Main Page")) {
            setIcon(new ImageIcon("data/Icon/Star Icon.png"));
        } else if (value.getText().equals("Expense Page")) {
            setIcon(new ImageIcon("data/Icon/Expense Icon.png"));
        }  else if (value.getText().equals("TimeTable Page")) {
            setIcon(new ImageIcon("data/Icon/TimeTable Icon.png"));
        }

        if (isSelected) {
            this.setBackground(new Color(255,255,255,60));
            this.setForeground(new Color(0x66B4FF));
        } else {
            this.setBackground(new Color(255,255,255,20));
            this.setForeground(new Color(0xF1F1F1));
        }
        return this;
    }

}
