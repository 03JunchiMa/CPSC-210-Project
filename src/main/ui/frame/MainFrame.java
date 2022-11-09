package ui.frame;

import com.formdev.flatlaf.*;
import ui.component.TopMenuBar;
import ui.listener.SetThemeListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// the main frame for the treeApp
public class MainFrame extends JFrame {

    private JPanel rootPanel;
    private static TopMenuBar topMenuBar;

    // MODIFIES:this
    // EFFECTS: Initializes the main frame for the main page of the treeApp.
    public MainFrame(String title) {
        super(title);

        FlatLaf.setup(new FlatLightLaf());
        /*
        external library, set the look and feel
        */
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        super.setSize(1350,1000);


        this.initializeTopMenuBar();
        this.initializePanel();

        addSetLightListener();
        addSetDraculaListener();

    }

    // MODIFIES: this
    // EFFECTS: initialize the content panel
    public void initializePanel() {
        rootPanel = new JPanel();
        super.setContentPane(rootPanel);
    }

    // MODIFIES: this
    // EFFECTS: initialize the menu bar
    public void initializeTopMenuBar() {
        topMenuBar = TopMenuBar.getTopMenuBarInstance();
        this.setJMenuBar(topMenuBar);
    }

    // MODIFIES: this
    // EFFECTS: let user change to the Dracula theme when click on the menu
    private void addSetDraculaListener() {
        TopMenuBar.getSetDraculaTheme().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UIManager.setLookAndFeel(new FlatDarculaLaf());
                    SwingUtilities.updateComponentTreeUI(MainFrame.this);
                } catch (Exception ex) {
                    System.err.println("Failed to initialize LaF");
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: let user change to the light theme when click on the menu
    private void addSetLightListener() {
        TopMenuBar.getSetLightTheme().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UIManager.setLookAndFeel(new FlatLightLaf());
                    SwingUtilities.updateComponentTreeUI(MainFrame.this);
                } catch (Exception ex) {
                    System.err.println("Failed to initialize LaF");
                }
            }
        });
    }




}
