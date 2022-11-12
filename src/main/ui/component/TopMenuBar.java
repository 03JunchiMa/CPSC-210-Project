package ui.component;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;

// The top menu bar, set
public class TopMenuBar extends JMenuBar {

    private static JMenuItem setLightTheme;
    private static JMenuItem setDraculaTheme;
    private static JMenu file;
    private static JMenuItem synchronizeItem;
    private static JMenuItem saveItem;
    private static TopMenuBar topMenuBar;
    private static JMenu exitMenu;

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

        // about menu
        JMenu aboutMenu = new JMenu("About");
        aboutMenu.setIcon(new ImageIcon("data/Icon/Tree Icon.png"));
        topMenuBar.add(aboutMenu);

        // setting menu
        JMenu settingMenu = new JMenu("Setting");
        settingMenu.setIcon(new ImageIcon("data/Icon/Setting Icon.png"));

        // add theme menu
        initThemeMenu(settingMenu);

        // initialize the file menu
        initFileMenu();

        // exit menu
        exitMenu = new JMenu("Exit");
        exitMenu.setIcon(new ImageIcon("data/Icon/Exit Icon.png"));
        topMenuBar.add(exitMenu);
    }

    // MODIFIES: this
    // EFFECTS: initialize the theme menu
    private static void initThemeMenu(JMenu settingMenu) {
        setLightTheme = new JRadioButtonMenuItem("Light theme",true);
        setDraculaTheme = new JRadioButtonMenuItem("Dracula theme",false);
        settingMenu.add(setLightTheme);
        settingMenu.add(setDraculaTheme);
        ButtonGroup themeGroup = new ButtonGroup();
        themeGroup.add(setLightTheme);
        themeGroup.add(setDraculaTheme);
        topMenuBar.add(settingMenu);
    }

    // MODIFIES: this
    // EFFECTS: initialize the file menu
    private static void initFileMenu() {
        file = new JMenu("File");
        file.setIcon(new ImageIcon("data/Icon/File Icon.png"));
        synchronizeItem = new JRadioButtonMenuItem("Synchronize");
        saveItem = new JRadioButtonMenuItem("Save");
        synchronizeItem.setIcon(new ImageIcon("data/Icon/Synchronize Up Icon .png"));
        saveItem.setIcon(new ImageIcon("data/Icon/Save Icon.png"));
        file.add(synchronizeItem);
        file.add(saveItem);
        topMenuBar.add(file);
    }

    // EFFECTS: return getSetLightTheme
    public static JMenuItem getSetLightTheme() {
        return setLightTheme;
    }

    // EFFECTS: return getSetDraculaTheme
    public static JMenuItem getSetDraculaTheme() {
        return setDraculaTheme;
    }

    // EFFECTS: return file
    public static JMenuItem getSynchronizeItem() {
        return synchronizeItem;
    }

    // EFFECTS: return saveItem menu
    public static JMenuItem getSaveItem() {
        return saveItem;
    }

    // EFFECTS: return exit menu
    public static JMenu getExitMenu() {
        return exitMenu;
    }
}
