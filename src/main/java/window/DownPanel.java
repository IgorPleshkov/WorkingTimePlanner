package window;

import javax.swing.*;
import java.sql.SQLException;

public class DownPanel extends JPanel {

    private JTabbedPane tabbedPane;

    public DownPanel() throws SQLException {
        tabbedPane = new JTabbedPane();

        JPanel jPanel = new JPanel();
        PanelRec panel1 = new PanelRec(jPanel, tabbedPane);


        //JPanel jPanel2 = new JPanel();
        //Panel2 panel2 = new Panel2(jPanel2, tabbedPane);
        PanelClients panel2 = new PanelClients(tabbedPane);


        JPanel jPanel3 = new JPanel();
        PanelCar panel3 = new PanelCar(jPanel3, tabbedPane);

    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }
}
