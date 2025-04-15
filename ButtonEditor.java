package newelectricityBillingSystem;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean clicked;
    private int row;
    private paybill frame;

    public ButtonEditor(JCheckBox checkBox, paybill frame) {
        super(checkBox);
        this.frame = frame;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                frame.processPayment(row);
            }
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
        boolean isSelected, int row, int column) {
        this.row = row;
        label = (value == null) ? "Pay Now" : value.toString();
        button.setText(label);
        clicked = true;
        return button;
    }

    public Object getCellEditorValue() {
        return label;
    }

    public boolean stopCellEditing() {
        clicked = false;
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
