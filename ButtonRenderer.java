package newelectricityBillingSystem;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);
        setText("Pay Now");
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}
