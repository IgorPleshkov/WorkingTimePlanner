package window;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
 
public class TableInfoRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, false, row, column);
 
        if(column==1) c.setHorizontalAlignment(CENTER);
        else  c.setHorizontalAlignment(LEFT);
        if (row >= 0 && column == 0) {
            c.setBackground(new JLabel().getBackground());
            //c.setBackground(Color.gray);
        } else if (value != null && !value.equals("")){
            c.setBackground(Color.green);
        } else {
            c.setBackground(Color.white);
           // c.setBackground(new JLabel().getBackground());
        }
        return c;
    }
}