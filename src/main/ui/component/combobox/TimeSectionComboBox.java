package ui.component.combobox;

import javax.swing.*;

// customized time section combo box
public class TimeSectionComboBox extends JComboBox<String> {

    // EFFECTS: initialize the field
    public TimeSectionComboBox() {
        initField();
    }

    // MODIFIES: this
    // EFFECTS: add all the necessary fields into the time section combo box
    public void initField() {
        String temp = "";
        for (int i = 800; i <= 2400; i += 50) {
            int j = i;
            if (j % 100 != 0) {
                j -= 20;
            }
            temp = Integer.toString(j);
            String res;
            if (j < 1000) {
                res = temp.substring(0,1) + ":" + temp.substring(1);
            }  else {
                res = temp.substring(0,2) + ":" + temp.substring(2);
            }

            this.addItem(res);
        }
    }

    // EFFECTS: return the selected time in int
    public int getSelectedTime() {
        String timeString = this.getSelectedItem().toString();
        int time = 0;
        for (int i = 0; i < timeString.length(); i++) {
            if (timeString.charAt(i) == ':') {
                continue;
            } else {
                time *= 10;
                time += (timeString.charAt(i) - '0');
            }
        }
        return time;
    }
}
