package ui.component;

import ui.listener.SetThemeListener;

import javax.swing.*;
import javax.swing.plaf.multi.MultiLookAndFeel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// The top menu bar, set
public class TopMenuBar extends JMenuBar {

    private static JMenuItem setLightTheme;
    private static JMenuItem setDraculaTheme;
    private static TopMenuBar topMenuBar;

    // private constructor, since top menu bar will be shared
    private TopMenuBar() {

    }

    // MODIFIES: this
    // EFFECTS: construct a new top menu bar if top menu bar is null, otherwise return the top menu bar
    public static TopMenuBar getTopMenuBarInstance() {
        if (topMenuBar == null) {
            topMenuBar = new TopMenuBar();
        }
        init();
        return topMenuBar;
    }

    // MODIFIES: this
    // EFFECTS: add all the menus to the menu bar
    public static void init() {

        // setting menu
        JMenu settingMenu = new JMenu("Setting");
        settingMenu.setIcon(new ImageIcon("data/Icon/Setting Icon.png"));
        // add sub menu
        setLightTheme = new JMenuItem("Light theme");
        setDraculaTheme = new JMenuItem("Dracula theme");
        settingMenu.add(setLightTheme);
        settingMenu.add(setDraculaTheme);
        topMenuBar.add(settingMenu);

        // about menu
        JMenu aboutMenu = new JMenu("About");
        aboutMenu.setIcon(new ImageIcon("data/Icon/About Icon.png"));
        topMenuBar.add(aboutMenu);

        // synchronize menu
        JMenu synchronizeMenu = new JMenu("Synchronize");
        synchronizeMenu.setIcon(new ImageIcon("data/Icon/Synchronize Up.png"));
        topMenuBar.add(synchronizeMenu);

        // save menu
        JMenu saveMenu = new JMenu("Save");
        saveMenu.setIcon(new ImageIcon("data/Icon/Save Icon.png"));
        topMenuBar.add(saveMenu);

        // exit menu
        JMenu exitMenu = new JMenu("Exit");
        exitMenu.setIcon(new ImageIcon("data/Icon/Exit Icon.png"));
        topMenuBar.add(exitMenu);
    }

    // EFFECTS: return getSetLightTheme
    public static JMenuItem getSetLightTheme() {
        return setLightTheme;
    }

    // EFFECTS: return getSetDraculaTheme
    public static JMenuItem getSetDraculaTheme() {
        return setDraculaTheme;
    }

}
