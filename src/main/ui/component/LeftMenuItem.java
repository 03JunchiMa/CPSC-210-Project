package ui.component;

import javax.swing.*;
import java.awt.*;

// The item for the left menu bar
public class LeftMenuItem {
    private String text;
    private String cmd;

    // EFFECTS: initialize the text
    public LeftMenuItem(String text) {
        this.text = text;
    }

    // EFFECTS: initialize the text and cmd
    public LeftMenuItem(String text, String cmd) {
        this.text = text;
        this.cmd = cmd;
    }

    // EFFECTS: return text
    public String getText() {
        return text;
    }

}
