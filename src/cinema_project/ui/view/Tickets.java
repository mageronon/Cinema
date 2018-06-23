package cinema_project.ui.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class Tickets extends JFrame {

    private JPanel tickets = new JPanel();
    private JButton printSelectButton = new JButton("Print Selected");
    private DefaultTableModel model;
    private JTable table = new JTable();

    public JPanel getTickets() {
        return tickets;
    }

    public JButton getPrintSelectButton() {
        return printSelectButton;
    }

    public DefaultTableModel getListModel() {
        return model;
    }

    public JTable getJlist() {
        return table;
    }

    public Tickets(){
        String[] columnNames = {"Film",
                "Row",
                "Seat",
                "Hall",
                "Date",
                "Session Begin",
                "Price",
                "Buyer Last name",
                "Buyer First name",
                "Buyer phone number",
                "Cashier First name",
                "Cashier Last name"};
        Object[][] objects = {};
        model = new DefaultTableModel(objects, columnNames);

        table.setModel(model);
        table.setFillsViewportHeight(true);
        TableColumnModel colmodel = table.getColumnModel();
       // table.setSize(2000, 8000);
        colmodel.getColumn(0).setPreferredWidth(400);
        colmodel.getColumn(1).setPreferredWidth(50);
        colmodel.getColumn(2).setPreferredWidth(50);
        colmodel.getColumn(3).setPreferredWidth(50);
        colmodel.getColumn(4).setPreferredWidth(300);
        colmodel.getColumn(5).setPreferredWidth(100);
        colmodel.getColumn(6).setPreferredWidth(100);
        colmodel.getColumn(7).setPreferredWidth(300);
        colmodel.getColumn(8).setPreferredWidth(300);
        colmodel.getColumn(9).setPreferredWidth(300);
        colmodel.getColumn(10).setPreferredWidth(300);
        colmodel.getColumn(11).setPreferredWidth(300);
        tickets.setLayout(new BorderLayout());
        tickets.add(table);
        tickets.add(new JScrollPane(table), BorderLayout.CENTER);
        setResizable(false);
        tickets.add(printSelectButton, BorderLayout.SOUTH);
        add(tickets);
        setSize(1400, 600);
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
