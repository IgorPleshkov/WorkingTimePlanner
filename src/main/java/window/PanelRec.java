package window;

import dataBase.Request;
import jpa.entity.Car;
import jpa.entity.RepairPost;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PanelRec {
    private Date dateStart;
    private Date dateEnd;
    private RepairPost repairPost;
    private Car car;
    //private String description;
    private Request request = new Request();

    public PanelRec(JPanel jPanel, JTabbedPane tabbedPane) throws SQLException {
        jPanel.setLayout(new GridBagLayout());
        tabbedPane.addTab("Запись на ремонт", jPanel);

        JTextField textField = new JTextField(10); //поле ввода
        JButton buttonRec = new JButton("Записать на ремонт");
        JTextArea description = new JTextArea("Причина обращения");

        JXDatePicker startTime = new JXDatePicker(); //начало записи
        startTime.setDate(Calendar.getInstance().getTime());
        startTime.setFormats(new SimpleDateFormat("dd.MM.yyyy  HH:mm"));
        dateStart = startTime.getDate();
        startTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(((JXDatePicker) e.getSource()).getDate());
                JFormattedTextField editor = startTime.getEditor();
                // dateStart = startTime.getDate();
                dateStart = (Date) editor.getValue();
            }
        });

        JXDatePicker endTime = new JXDatePicker(); //начало записи
        endTime.setDate(Calendar.getInstance().getTime());
        endTime.setFormats(new SimpleDateFormat("dd.MM.yyyy  HH:mm"));
        dateEnd = endTime.getDate();
        endTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(((JXDatePicker) e.getSource()).getDate());
                JFormattedTextField editor = endTime.getEditor();

                //dateEnd = endTime.getDate();
                dateEnd = (Date) editor.getValue();
            }
        });

        Car[] itemsCar = request.getAllCars().toArray(Car[]::new);
        JComboBox comboBoxCar = new JComboBox(itemsCar);
        car = (Car) comboBoxCar.getSelectedItem();
        comboBoxCar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                car = (Car) comboBoxCar.getSelectedItem();

            }
        });


        JLabel label1 = new JLabel("Автомобиль:");
        jPanel.add(label1, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.WEST, GridBagConstraints.WEST, new Insets(1, 1, 1, 1), 0, 0));
//        jPanel.add(textField, new GridBagConstraints(0, 1, 1, 1, 1, 1,
//                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));

        jPanel.add(comboBoxCar, new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));

        JLabel label2 = new JLabel("Время начала:");
        jPanel.add(label2, new GridBagConstraints(0, 2, 1, 1, 1, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));
        jPanel.add(startTime, new GridBagConstraints(0, 3, 1, 1, 2, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));

        JLabel label3 = new JLabel("Время окончания:");
        jPanel.add(label3, new GridBagConstraints(0, 4, 1, 1, 1, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));
        jPanel.add(endTime, new GridBagConstraints(0, 5, 1, 1, 1, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));

        JLabel label4 = new JLabel("Пост ремонта:");
        jPanel.add(label4, new GridBagConstraints(0, 6, 1, 1, 1, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));


        RepairPost[] items = request.getRpp().toArray(RepairPost[]::new);
        JComboBox comboBox = new JComboBox(items);
        repairPost = (RepairPost) comboBox.getSelectedItem();
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repairPost = (RepairPost) comboBox.getSelectedItem();

            }
        });

        jPanel.add(comboBox, new GridBagConstraints(0, 7, 1, 1, 1, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));


        jPanel.add(buttonRec, new GridBagConstraints(0, 8, 1, 1, 2, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));

        jPanel.add(description, new GridBagConstraints(1, 0, 0, 0, 100, 0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));


        buttonRec.addActionListener(new ActionListener() { // кнопка записи на ремонт
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = "";
                try {
                    if (repairPost == null || car == null || dateStart == null || dateEnd == null) {
                        JOptionPane.showMessageDialog(tabbedPane, "НЕ ВСЕ ДАННЫЕ ЗАПОЛНЕНЫ!");
                    } else {
                        if (request.recordingByCar(car, dateStart, dateEnd, repairPost, description.getText())) {
                            JOptionPane.showMessageDialog(tabbedPane, "Гос. номер: " + textField.getText() + " " +
                                    "Записан с: " + dateStart + " по " + dateEnd + " " +
                                    repairPost);
                        } else{
                            JOptionPane.showMessageDialog(tabbedPane, "Ошибка записи!");
                        }
                    }

                } catch (NumberFormatException | SQLException e1) {
                    JOptionPane.showMessageDialog(tabbedPane, "Некорректный ввод!");
                }
            }
        });

    }
}
