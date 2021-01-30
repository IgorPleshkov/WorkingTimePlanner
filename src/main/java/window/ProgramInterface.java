package window;

import dataBase.Request;
import jpa.entity.RepairPost;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProgramInterface {
    private JFrame jFrame = new JFrame("Планировщик рабочего времени СТО");
    private JButton buttonRefresh = new JButton("Refresh");

    public void initInterface() throws SQLException {

        ArrayList<RepairPost> repairPosts = new ArrayList<>();

        Request request = new Request();
        repairPosts = request.getRpp();

        jFrame.setSize(1900, 450); //основное окно
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = jFrame.getContentPane(); //контейнер
        TablePosts tablePosts = new TablePosts(repairPosts, container); //таблица постов ремонта

      //  UpPanel upPanel = new UpPanel();
        DownPanel downPanel = new DownPanel();

       // container.add(upPanel.getjPanel(), BorderLayout.NORTH);
        container.add(downPanel.getTabbedPane(), BorderLayout.SOUTH);
        jFrame.setVisible(true);
    }


}