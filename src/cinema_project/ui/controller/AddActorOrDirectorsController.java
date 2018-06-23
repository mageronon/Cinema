package cinema_project.ui.controller;

import cinema_project.data_base.Connect_Data_Base;
import cinema_project.ui.view.AddActorsOrDirectors;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import static javax.swing.JOptionPane.showMessageDialog;

public class AddActorOrDirectorsController extends Connect_Data_Base {
    private AddActorsOrDirectors panel;
    private JButton addButton;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField citizenShip;
    private Statement statement;
    private String str;
    private JTextField textField1;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton addToFilmButton;
    private JButton deleteFromFilmButton;
    private JButton finallyAddToFilmsButton;

    private JButton selectButton;
    private SpinnerDateModel model1;
    private JSpinner dateSpiner;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public AddActorOrDirectorsController(String string){
        panel = new AddActorsOrDirectors();
        str = string;
        citizenShip = panel.getCitizenShip();
        lastName = panel.getLastName();

        selectButton = panel.getSelectButton();
        firstName = panel.getFirstName();
        addButton = panel.getAddButton();
        finallyAddToFilmsButton = panel.getFinallyAddToFilmsButton();
        textField1 = panel.getTextField1();
        comboBox1 = panel.getComboBox1();
        comboBox2 = panel.getComboBox2();
        addToFilmButton = panel.getAddToFilmButton();
        deleteFromFilmButton = panel.getDeleteFromFilmButton();
        model1 = panel.getModel1();

        dateSpiner = panel.getDateSpiner();
        setShoto();
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    JFileChooser fileopen = new JFileChooser("C:\\Users\\Molod\\IdeaProjects\\Project\\src\\image");
                    int ret = fileopen.showDialog(null, "Open file");
                    if (ret == JFileChooser.APPROVE_OPTION) {
                        File file = fileopen.getSelectedFile();
                        textField1.setText("/image/" + str + "s/" + file.getName());
                    }
            }
        });
        finallyAddToFilmsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(str.equals("actor")){
                    for (int i = 0; i < comboBox2.getItemCount(); ++i) AddFilmController.actor.addItem(comboBox2.getItemAt(i).toString());

                }
                if(str.equals("director")){
                    for (int i = 0; i < comboBox2.getItemCount(); ++i) AddFilmController.director.addItem(comboBox2.getItemAt(i).toString());
                }
                panel.setVisible(false);
            }
        });
        addToFilmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboBox1.getSelectedIndex() != -1 && comboBox1.getItemCount() != 0){
                    if(hasnot()){
                        comboBox2.addItem(comboBox1.getSelectedItem().toString());
                    }
                }
            }
        });
        deleteFromFilmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox2.getSelectedIndex() != -1 && comboBox2.getItemCount() != 0){
                    comboBox2.removeItemAt(comboBox2.getSelectedIndex());
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
           if(dontExist()) {
                try {
                    statement = connection.createStatement();
                    statement.execute("insert into actors (actorid, last_name, first_name, birth_date, citizenship, image) VALUES (" + getId() +
                            ", '" + lastName.getText() + "', '" + firstName.getText() + "', '" + dateFormat.format(dateSpiner.getValue()) + "', '" + citizenShip.getText()
                            + "', '" + textField1.getText() + "')");
                    citizenShip.setText("");
                    lastName.setText("");
                    firstName.setText("");
                    textField1.setText("");
                    comboBox1.removeAllItems();
                    setShoto();

                    showMessageDialog(null,   str + " added");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
             }

            }
        });
    }

    private boolean dontExist() {
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from " + str + "s where last_name = '" + lastName.getText() + "' AND first_name = '" + firstName.getText() + "' ");
            if (resultSet.next()) return false;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return true;
    }

    private boolean hasnot() {
        for (int i = 0; i < comboBox2.getItemCount(); ++i)
        {
            if(comboBox2.getItemAt(i).toString().equals(comboBox1.getSelectedItem().toString()))return false;
        }
        return true;
    }

    private void setShoto() {
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select last_name, first_name from " + str + "s");
            while (resultSet.next()) comboBox1.addItem(resultSet.getString(1) + ' ' + resultSet.getString(2));
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    private int getId(){
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select MAX(" + str + "id) + 1 from " + str + "s");
            if (resultSet.next()) return resultSet.getInt(1);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return -1;
    }
}
