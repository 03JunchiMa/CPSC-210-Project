package ui.frame;

import com.formdev.flatlaf.*;
import model.TreeApp;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.component.renderer.LeftMenuCellRenderer;
import ui.component.LeftMenuItem;
import ui.component.panel.ExpensePanel;
import ui.component.panel.FractalTreePanel;
import ui.component.TopMenuBar;
import ui.component.panel.TimeTablePanel;
import ui.dialog.ExitDialog;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;


// the main frame for the treeApp
public class MainFrame extends JFrame {

    private static final String JSON_STORE = "./data/TreeApp.json";

    private TreeApp treeApp;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JList<LeftMenuItem> leftMenu = new JList<>();
    private JPanel container = new JPanel();

    private ExpensePanel expensePanel;

    private static TopMenuBar topMenuBar;

    // MODIFIES:this
    // EFFECTS: Initializes the main frame for the main page of the treeApp.
    public MainFrame(String title) {
        super(title);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        treeApp = new TreeApp();

        flatLafSetUp();

        super.setSize(1350,1000);

        // initialize the main frame
        this.initializeTopMenuBar();
        this.initializePanel();
        this.initLeftMenu();

        // add listener to the menu bar
        addSetLightListener();
        addSetDraculaListener();
        addSynchronizeItemListener();
        addSaveItemListener();
        addExitMenuListener();
        addLeftMenuListener();
    }

    // EFFECTS: set up the FlatLaf ui look and feel
    private void flatLafSetUp() {
        FlatLaf.setup(new FlatLightLaf());
        /*
        external library, set the look and feel
        */
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            SwingUtilities.updateComponentTreeUI(MainFrame.this);
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
    }

    // EFFECTS: initialize the left menu
    private void initLeftMenu() {
        DefaultListModel<LeftMenuItem> listModel = new DefaultListModel<>();

        // add item to model
        listModel.addElement(new LeftMenuItem("Main Page"));
        listModel.addElement(new LeftMenuItem("Expense Page"));
        listModel.addElement(new LeftMenuItem("TimeTable Page"));

        leftMenu.setModel(listModel);
        leftMenu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        leftMenu.setCellRenderer(new LeftMenuCellRenderer());

        leftMenu.setOpaque(true);
        leftMenu.setBackground(new Color(0x2F4056));

        JScrollPane scrollPane = new JScrollPane(leftMenu);
        this.getContentPane().add(scrollPane,BorderLayout.LINE_START);
    }

    // MODIFIES: this
    // EFFECTS: initialize the content panel
    private void initializePanel() {

        container.setLayout(new CardLayout());
        container.setOpaque(true);
        container.setBackground(new Color(0xF4F4F4));
        container.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, new Color(0xCCCCCC)));

        this.getContentPane().add(container,BorderLayout.CENTER);

        // add cards
        container.add(new FractalTreePanel(), "View 0");
        expensePanel = new ExpensePanel();
        container.add(expensePanel,"View 1");
        expensePanel.setMainFrame(this);
        expensePanel.setExpenseRecording(treeApp.getExpenseRecording());
        container.add(new TimeTablePanel(),"View 2");

        // default view
        selectView(0);
    }

    // MODIFIES: this
    // EFFECTS: initialize the menu bar
    public void initializeTopMenuBar() {
        topMenuBar = TopMenuBar.getTopMenuBarInstance();
        this.setJMenuBar(topMenuBar);
    }

    // EFFECTS: switch content pane view according to the index
    public void selectView(int index) {
        String name = "View " + index;

        CardLayout cardLayout = (CardLayout) container.getLayout();
        cardLayout.show(container, name);

        if (leftMenu.getSelectedIndex() != index) {
            leftMenu.setSelectedIndex(index);
        }

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

    // MODIFIES: this
    // EFFECTS: let user load the previously saved data
    private void addSynchronizeItemListener() {
        TopMenuBar.getSynchronizeItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTreeApp();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: let user save the data
    private void addSaveItemListener() {
        TopMenuBar.getSaveItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTreeApp();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: pop up the dialog and let user decide whether the file will be saved or not
    private void addExitMenuListener() {
        TopMenuBar.getExitMenu().addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                ExitDialog exitDialog = new ExitDialog(MainFrame.this);
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });
    }

    // MODIFIES: this
    // EFFECTS: add the listener to the left menu, which change the view of the panel when click on different items.
    private void addLeftMenuListener() {
        leftMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = leftMenu.locationToIndex(e.getPoint());
                if (index >= 0) {
                    selectView(index);
                }
            }
        });
    }

    // EFFECTS: save the TreeApp
    public void saveTreeApp() {
        try {
            jsonWriter.open();;
            jsonWriter.write(treeApp);
            jsonWriter.close();
            System.out.println("Saved the data to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: load the TreeApp from previous saved data
    public void loadTreeApp() {
        try {
            treeApp = jsonReader.read();
            System.out.println("Loaded successfully from previous saved data");
            System.out.printf("!!!!!------Your remaining budget: "
                    + treeApp.getExpenseRecording().getBudget() + " ------!!!!!\n");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
