package cinema_project.ui.view;

import cinema_project.data_base.LimitedCharInput;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AddCashier extends JFrame {

    private JPanel cashierPanel;// = new JPanel();

    public JPanel getCashierPanel() {
        return cashierPanel;
    }

    public JScrollPane getJscrollPane() {
        return jscrollPane;
    }

    public JTable getTable1() {
        return table1;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JTextField getLastname() {
        return lastname;
    }

    public JTextField getSecondname() {
        return secondname;
    }

    public JTextField getEmailCashier() {
        return emailCashier;
    }

    public JTextField getFirstname() {
        return firstname;
    }


    private JTable table1;
    private JButton deleteButton;
    private JTextField emailCashier;

    public JButton getAddButton() {
        return addButton;
    }

    private JTextField firstname;
    private JTextField lastname;
    private JTextField secondname;
    private JButton addButton;
    private JScrollPane jscrollPane;


    public DefaultTableModel getModel() {
        return model;
    }


    private DefaultTableModel model;

    public AddCashier(){
        String[] columnNames = {"Tab #", "Last name", "First name", "Second name", "Telephone number"};
        Object[][] objects = {};
        model = new DefaultTableModel(objects, columnNames);
        table1.setModel(model);
        table1.setFillsViewportHeight(true);
        TableColumnModel colmodel = table1.getColumnModel();
        colmodel.getColumn(0).setPreferredWidth(100);
        colmodel.getColumn(1).setPreferredWidth(300);
        colmodel.getColumn(2).setPreferredWidth(300);
        colmodel.getColumn(3).setPreferredWidth(400);
        colmodel.getColumn(4).setPreferredWidth(400);
        emailCashier.setDocument(new LimitedCharInput(9));
        emailCashier.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char vchar = e.getKeyChar();
                if(!(Character.isDigit(vchar)) || vchar == KeyEvent.VK_BACK_SPACE || vchar == KeyEvent.VK_DELETE){
                    e.consume();
                }
            }
        });
        setContentPane(cashierPanel);
        setResizable(false);
        setSize(1200,500);
        setTitle("Admin menu about cashiers");
        setVisible(true);
        setLocationRelativeTo(null);

    }
}
