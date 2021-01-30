package window;

import dataBase.Request;
import jpa.entity.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class WindowAddCar {
    private JFrame jFrame2 = new JFrame("Создание автомобиля");
    private JButton button = new JButton("Записать");
    private Request request = new Request();
    private Client client;

    public void initInterface() throws SQLException {

        jFrame2.setSize(300, 300);
        jFrame2.setLayout(new GridBagLayout());
        JTextField textModel = new JTextField(15);
        JLabel model = new JLabel("Модель:");
        jFrame2.add(model, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));
        jFrame2.add(textModel, new GridBagConstraints(1, 0, 1, 1, 1, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));

        JTextField textNumber = new JTextField(15);
        JLabel number = new JLabel("Гос номер:");
        jFrame2.add(number, new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));
        jFrame2.add(textNumber, new GridBagConstraints(1, 1, 1, 1, 1, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));


        JTextField textYear = new JTextField(15);
        JLabel year = new JLabel("Год выпуска:");
        jFrame2.add(year, new GridBagConstraints(0, 2, 1, 1, 1, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));
        jFrame2.add(textYear, new GridBagConstraints(1, 2, 1, 1, 1, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));

        Client[] items = request.getAllClients().toArray(Client[]::new);
        JComboBox comboBox = new JComboBox(items);
        client = (Client) comboBox.getSelectedItem();
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client = (Client) comboBox.getSelectedItem();

            }
        });

        jFrame2.add(comboBox, new GridBagConstraints(1, 3, 1, 1, 2, 1,
                GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets(1, 1, 1, 1), 0, 0));
        JLabel owner = new JLabel("Владелец:");
        jFrame2.add(owner, new GridBagConstraints(0, 4, 1, 1, 1, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));

        jFrame2.add(button, new GridBagConstraints(1, 5, 1, 1, 2, 1,
                GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets(1, 1, 1, 1), 0, 0));


        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (textModel.getText().trim().equals("") || textYear.getText().trim().equals("")
                            || textNumber.getText().trim().equals("") || client == null) {
                        JOptionPane.showMessageDialog(jFrame2, "НЕ ВСЕ ДАННЫЕ ЗАПОЛНЕНЫ!");
                    } else {

                       request.addCar(textModel.getText(), textNumber.getText(), Integer.parseInt(textYear.getText()), client);
                       jFrame2.dispose();

                    }

                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(jFrame2, "Некорректный ввод!");
                }
            }
        });
        jFrame2.setVisible(true);
    }
}
