package ui;

import ui.frame.MainFrame;

import javax.swing.*;
import java.awt.*;

// The main method
public class Main {
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new MainFrame("TreeApp");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

}
