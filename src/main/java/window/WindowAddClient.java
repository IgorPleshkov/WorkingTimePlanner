package window;

import dataBase.Request;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WindowAddClient {
    private JFrame jFrame2 = new JFrame("Создание клиента");
    private JXDatePicker picker = new JXDatePicker();
    private JButton button = new JButton("Записать");
    private Request request = new Request();

    public void initInterface() throws SQLException {

        jFrame2.setSize(300, 300);
        jFrame2.setLayout(new GridBagLayout());
        JTextField textName = new JTextField(15);
        JLabel name = new JLabel("Имя:");
        jFrame2.add(name, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));
        jFrame2.add(textName, new GridBagConstraints(1, 0, 1, 1, 1, 1,
               GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));

        JTextField textSurname = new JTextField(15);
        JLabel surname = new JLabel("Фамилия:");
        jFrame2.add(surname, new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));
        jFrame2.add(textSurname, new GridBagConstraints(1, 1, 1, 1, 1, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));

        JLabel dateBirthdate = new JLabel("Дата рождения:");
        jFrame2.add(dateBirthdate, new GridBagConstraints(0, 2, 1, 1, 1, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));
        picker.setDate(Calendar.getInstance().getTime());
        picker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        jFrame2.add(picker, new GridBagConstraints(1, 2, 1, 1, 1, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));

        JTextField textTel = new JTextField(15);
        JLabel tel = new JLabel("Телефон:");
        jFrame2.add(tel, new GridBagConstraints(0, 3, 1, 1, 1, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));
        jFrame2.add(textTel, new GridBagConstraints(1, 3, 1, 1, 1, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));

        jFrame2.add(button, new GridBagConstraints(1, 4, 1, 1, 2, 1,
                GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets(1, 1, 1, 1), 0, 0));


        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (textName.getText().trim().equals("") || textSurname.getText().trim().equals("")
                            || textTel.getText().trim().equals("")) {
                        JOptionPane.showMessageDialog(jFrame2, "НЕ ВСЕ ДАННЫЕ ЗАПОЛНЕНЫ!");
                    } else {
                        request.addClient(textName.getText(), textSurname.getText(), picker.getDate(), textTel.getText());
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

