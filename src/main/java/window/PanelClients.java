package window;

import dataBase.Request;
import jpa.entity.Car;
import jpa.entity.Client;
import lombok.SneakyThrows;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class PanelClients {
    private ArrayList<Client> clients;
    private Request request = new Request();
    private JPanel jPanel = new JPanel();
    private JTable table1;
    private JButton buttonRefresh = new JButton("Refresh");
    public PanelClients(JTabbedPane tabbedPane) throws SQLException {
       jPanel.setLayout(new GridBagLayout());
        tabbedPane.addTab("Клиенты", jPanel);

        clients = request.getAllClients();

        table1 = new JTable(new Table1(clients));
        table1.setPreferredScrollableViewportSize(new Dimension(300, 0));
        table1.setEnabled(false);
        JScrollPane spTable1 = new JScrollPane(table1);


        JButton buttonAdd = new JButton("Добавить клиента");
       // JButton buttonDel = new JButton("Удалить клиента");


        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowAddClient windowAddClient = new WindowAddClient();
                try {
                    windowAddClient.initInterface();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        buttonRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clients = request.getAllClients();
                    jPanel.repaint();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });

        jPanel.add(buttonAdd, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.WEST, GridBagConstraints.WEST, new Insets(1, 1, 1, 1), 0, 0));

       // jPanel.add(buttonDel, new GridBagConstraints(0, 1, 1, 1, 1, 1,
       //         GridBagConstraints.WEST, GridBagConstraints.WEST, new Insets(1, 1, 1, 1), 0, 0));

        jPanel.add(buttonRefresh, new GridBagConstraints(0, 2, 1, 1, 1, 1,
                GridBagConstraints.WEST, GridBagConstraints.WEST, new Insets(1, 1, 1, 1), 0, 0));

        jPanel.add(spTable1, new GridBagConstraints(1, 0, 0, 0, 100, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

    }



    class Table1 extends AbstractTableModel {
       ArrayList<Client> clients;
        public Table1(ArrayList<Client> clients) {
            super();
            this.clients = clients;
        }

        @Override
        public int getRowCount() {
            return clients.size();
        }

        @Override
        public int getColumnCount() {
            return 5;
        }

        @SneakyThrows
        @Override
        public Object getValueAt(int x, int y) {
            switch (y) {
                case 0:
                    return clients.get(x).getName();
                case 1:
                    return clients.get(x).getSurname();
                case 2:
                    return clients.get(x).getContactInfo();
                case 3:
                    Car car;
                   // Request request = new Request();
                    return request.getClientCar(clients.get(x));
                    //return clients.get(x).getContactInfo();
                default:
                    return "";
            }

        }
        @Override
        public String getColumnName(int c) {
            switch (c) {
                case 0:
                    return "Имя";
                case 1:
                    return "Фамилия";
                case 2:
                    return "Контактнтые данные";
                case 3:
                    return "Автомобиль";
                default:
                    return "";
            }
        }
    }



}
