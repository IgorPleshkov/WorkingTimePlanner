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

public class PanelCar {
    private ArrayList<Car> cars;
    private Request request = new Request();
    public PanelCar(JPanel jPanel, JTabbedPane tabbedPane) throws SQLException {
        jPanel.setLayout(new GridBagLayout());
        tabbedPane.addTab("Автомобили", jPanel);

        cars = request.getAllCars();


        JTable table2 = new JTable(new Table2(cars));
        table2.setPreferredScrollableViewportSize(new Dimension(300, 0));
        JScrollPane spTable2 = new JScrollPane(table2);

        JButton buttonAdd = new JButton("Добавить автомобиль");
        //JButton buttonDel = new JButton("Удалить автомобиль");

        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowAddCar windowAddCar = new WindowAddCar();
                try {
                    windowAddCar.initInterface();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        jPanel.add(buttonAdd, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.WEST, GridBagConstraints.WEST, new Insets(1, 1, 1, 1), 0, 0));

        //jPanel.add(buttonDel, new GridBagConstraints(0, 1, 1, 1, 1, 1,
         //       GridBagConstraints.WEST, GridBagConstraints.WEST, new Insets(1, 1, 1, 1), 0, 0));
        jPanel.add(spTable2, new GridBagConstraints(1, 0, 0, 0, 100, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

    }


    class Table2 extends AbstractTableModel {
        ArrayList<Car> cars;
        public Table2(ArrayList<Car> cars) {
            super();
            this.cars = cars;
        }

        @Override
        public int getRowCount() {
            return cars.size();
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
                    return cars.get(x).getModel();
                case 1:
                    return cars.get(x).getYear();
                case 2:
                    return cars.get(x).getStateNumber();
                case 3:
                    Client owner;
                    //Request request = new Request();
                    return request.getOwner(cars.get(x));
                //return clients.get(x).getContactInfo();
                default:
                    return "";
            }

        }
        @Override
        public String getColumnName(int c) {
            switch (c) {
                case 0:
                    return "Модель";
                case 1:
                    return "Год выпуска";
                case 2:
                    return "Гос. номер";
                case 3:
                    return "Владелец";
                default:
                    return "";
            }
        }
    }
}
