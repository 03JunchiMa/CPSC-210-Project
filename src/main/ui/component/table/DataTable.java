package ui.component.table;

import model.Expense;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public abstract class DataTable extends JTable {

    protected JPopupMenu popupMenu;
    protected JMenuItem item;
    protected DefaultTableModel model = new DefaultTableModel();

    // EFFECTS: initialize the customized table
    public DataTable() {
        initTable();
        addRightClickedListener();
    }

    // MODIFIES: this
    // EFFECTS: set up the structure
    public void initTable() {
        this.setFillsViewportHeight(true);
        this.setRowSelectionAllowed(true);
        this.setRowHeight(23);
        this.setModel(model);
        initModel();
    }

    // MODIFIES: this
    // EFFECTS: initialize the model
    protected abstract void initModel();
//      model.addColumn("Expense Amount");
//        model.addColumn("Category");
//        model.addColumn("id");
//        model.addColumn("Date");

    // MODIFIES: this
    // EFFECTS: add the corresponding data to row
    public abstract void addRow(Object object);
//        Vector<Object> rowData = new Vector<>();
//        rowData.add(expense.getAmount());
//        rowData.add(expense.getCategory());
//        rowData.add(expense.getId());
//        rowData.add(getDate());
//        model.addRow(rowData);


    // EFFECTS: return the current date to string
    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = dateFormat.format(new Date());
        return time;
    }

    // MODIFIES: this
    // EFFECTS: delete the selected row
    protected void deleteRow() {
        int[] rows = this.getSelectedRows();
        for (int i = rows.length - 1; i >= 0; i--) {
            int index = rows[i];
            deleteRowData(index);
            model.removeRow(index);
        }
    }

    // MODIFIES: this
    // EFFECTS: delete the row data in the model instead in the table
    protected abstract void deleteRowData(int row);

    // MODIFIES: this
    // EFFECTS: pop up the delete menu
    private void onTableRightClicked(MouseEvent e) {
        int row = this.rowAtPoint(e.getPoint());
        if (row < 0) {
            return;
        }

        this.clearSelection();
        this.setRowSelectionInterval(row,row);

        popupMenu = new JPopupMenu();
        item = new JMenuItem("Delete");
        popupMenu.add(item);
        this.add(popupMenu);

        popupMenu.show(e.getComponent(),e.getX(),e.getY());

        itemAddListener();
    }

    // MODIFIES: this
    // EFFECTS: add the listener to the item, so that user can delete the item when right-clicked
    private void itemAddListener() {
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteRow();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: add the right-clicked listener to this
    private void addRightClickedListener() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    onTableRightClicked(e);
                }
            }
        });
    }

}
