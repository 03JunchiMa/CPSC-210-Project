package ui.component.button;

import javax.swing.*;
import java.awt.*;

// The customized button for add expense info in the expense panel
public class AddButton extends JButton {

    // EFFECTS: set up the button, and initialize the look and feel
    public AddButton(String title) {
        super(title);
        this.setSize(30,20);
        this.setForeground(new Color(221,134,15));
    }


}
