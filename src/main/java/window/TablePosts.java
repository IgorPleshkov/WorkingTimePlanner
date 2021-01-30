package window;

import dataBase.Request;
import jpa.entity.RecordForRepairs;
import jpa.entity.RepairPost;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TablePosts {
    private JTable jTab;

    private JPanel jPanel;
    private JXDatePicker picker;
    private JButton button;
    private boolean mouseClick = false;
    private LocalDate currentDate = LocalDate.now();

    public TablePosts(ArrayList<RepairPost> repairPosts, Container container) {
        jPanel = new JPanel();
        picker = new JXDatePicker();
        button = new JButton("Refresh");

        picker.setDate(Calendar.getInstance().getTime());
        picker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        picker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable(e);
            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable(e);
            }
        });

        jPanel.add(picker);
        jPanel.add(button);

        jTab = new JTable(new Table(repairPosts));
        JScrollPane jscrlp = new JScrollPane(jTab);
        jTab.setDefaultRenderer(Object.class, new TableInfoRenderer());
        jTab.setEnabled(false);
        jTab.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mouseClick = true;
                JTable source = (JTable) e.getSource();
                int row = source.rowAtPoint(e.getPoint());
                int column = source.columnAtPoint(e.getPoint());
                RecordForRepairs rr = (RecordForRepairs) jTab.getValueAt(row, column);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (rr == null) return;
                    JOptionPane.showMessageDialog(jPanel, rr + ", " + rr.getClient() + ", "
                            + rr.getClient().getContactInfo() + ", " + rr.getDescription());
                    //rr = null;

                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    if (rr == null) return;
                    JPanel jPanelDel = new JPanel();
                    int res = JOptionPane.showConfirmDialog(null, jPanelDel, "Удалить запись?", JOptionPane.YES_NO_OPTION);
                    if (res == 0) {
                        Request delRec = new Request();
                        try {
                            delRec.deleteRec(rr);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        container.add(jscrlp);
        container.add(jPanel, BorderLayout.NORTH);
    }

    public void refreshTable(ActionEvent e) {
        Date date = (Date) picker.getEditor().getValue();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = date.toInstant();
        currentDate = instant.atZone(defaultZoneId).toLocalDate();
        jTab.repaint();

    }

    class Table extends AbstractTableModel {
        ArrayList<RecordForRepairs> record = new ArrayList<>();
        ArrayList<RepairPost> repairPosts;
        Request request = new Request();

        public Table(ArrayList<RepairPost> repairPosts) {
            super();
            this.repairPosts = repairPosts;
        }

        @Override
        public int getRowCount() {
            return repairPosts.size();
        }

        @Override
        public int getColumnCount() {
            return (12 * 60 / RepairPost.RECORDING_INTERVAL) + 1; //получаем количество интервалов
        }

        @Override
        public Object getValueAt(int x, int y) {
            if (y == 0 || mouseClick) {
                try {
                    record = request.getRecordForRepairs(repairPosts.get(x), currentDate);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                if (!mouseClick) return repairPosts.get(x).getName();
            }
            LocalTime[] lt = getIntervalRecord(y);
            if (record.size() > 0) {
                for (int i = 0; i < record.size(); i++) {
                    if ((lt[0].plusMinutes(RepairPost.RECORDING_INTERVAL - 1).isAfter(record.get(i).getTimeStart().toLocalTime())
                            || lt[0].equals(record.get(i).getTimeStart().toLocalTime()))
                            && (lt[1].isBefore(record.get(i).getTimeStop().toLocalTime())
                            || lt[1].equals(record.get(i).getTimeStop().toLocalTime()))) {
                        mouseClick = false;
                        return record.get(i);
                    }
                }
            }
            mouseClick = false;
            return null;
        }

        @Override
        public String getColumnName(int c) {
            LocalTime[] lt = getIntervalRecord(c);
            if (lt == null) return null;
            return lt[0].toString() + "-" + lt[1].toString();
        }

        public LocalTime[] getIntervalRecord(int column) {
            if (column == 0) return null;
            LocalTime[] lt = new LocalTime[2];
            int startTime = --column * RepairPost.RECORDING_INTERVAL;
            lt[0] = RepairPost.STARTING_JOB.plusMinutes(startTime);
            lt[1] = RepairPost.STARTING_JOB.plusMinutes(startTime + RepairPost.RECORDING_INTERVAL);
            return lt;
        }
    }
}
