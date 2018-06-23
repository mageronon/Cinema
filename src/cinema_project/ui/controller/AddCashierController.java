package cinema_project.ui.controller;

import cinema_project.data_base.Connect_Data_Base;
import cinema_project.ui.view.AddCashier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class AddCashierController extends Connect_Data_Base {

    private Statement statement = null;
    private AddCashier addCashier;
    private JTable table1;
    private JButton deleteButton;
    private JButton addButton;
    private JTextField emailCashier;
    private JTextField firstname;
    private DefaultTableModel model;
    private JTextField lastname;
    private JTextField secondname;
    private JScrollPane jscrollPane;

    private String[] str = {"1", "2", "3", "4", "5"};
    private List<String[]> list = new ArrayList<String[]>();

    public AddCashierController(){
        initComponents();
        initListeners();
        setCashier();

    }



    private void setCashier() {
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from cashier where workingnow = true order by tab_num;");
            while (resultSet.next()){
                str = new String[]{String.valueOf(resultSet.getInt(1)), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5)};
                model.addRow(str);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean presentCashier(){
        try{
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from cashier where telephone_num = '" + emailCashier.getText() + "'");
            if (resultSet.next()){
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void initListeners() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(emailCashier.getText().length() == 9)
                if(!emailCashier.getText().equals("") && presentCashier()){
                    if(!firstname.getText().equals("") && !lastname.getText().equals("") && !secondname.getText().equals("")){
                        try {
                            Statement statement1 = connection.createStatement();
                            ResultSet resultSet = statement1.executeQuery("select Max(tab_num) + 1 from cashier");
                            if(resultSet.next()){
                                statement.execute("insert Into cashier (tab_num, first_name, last_name, second_name, telephone_num) VALUES ( " +
                                        resultSet.getInt(1) +" , '" + firstname.getText() +"' , '" + lastname.getText() +"' , '" + secondname.getText() + "' , '" + emailCashier.getText() + "' );");
                                emailCashier.setText("");
                                firstname.setText("");
                                lastname.setText("");
                                secondname.setText("");
                                for(int i = table1.getRowCount(); i > 0; --i)
                                    model.removeRow(i);
                                setCashier();
                            }
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }else showMessageDialog(null, "Enter full name please.");
                }else showMessageDialog(null, "This telephone is used");
                else showMessageDialog(null, "Enter telephone correctly");
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(table1.getSelectedRow() > -1){
                    try {
                        statement = connection.createStatement();
                        statement.executeUpdate("update cashier set workingnow = false where tab_num = " + model.getValueAt(table1.getSelectedRow(), 0));
                        for(int i = table1.getRowCount(); i > 0; --i)
                            model.removeRow(i - 1);
                        setCashier();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private void initComponents() {
        addCashier = new AddCashier();
        table1 = addCashier.getTable1();
        deleteButton = addCashier.getDeleteButton();
        emailCashier = addCashier.getEmailCashier();
        firstname = addCashier.getFirstname();
        lastname = addCashier.getLastname();
        secondname = addCashier.getSecondname();
        model = addCashier.getModel();
        addButton = addCashier.getAddButton();
        jscrollPane = addCashier.getJscrollPane();
    }
}

