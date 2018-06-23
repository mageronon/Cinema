package cinema_project.ui.controller;

import cinema_project.data_base.Connect_Data_Base;
import cinema_project.ui.view.AddSesion;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class AddSessionController  extends Connect_Data_Base {

    Statement statement = null;
    private AddSesion addSesion;
    private JTable table1;
    private JComboBox chooseFilm;
    private JComboBox chooseHall;
    private JButton deleteButton;
    private JButton addButton;
    private DefaultTableModel model;
    private JScrollPane jScrollPane;
    private JSpinner timeSpinerH;
    private JSpinner endTimeSpinerH;
    private JSpinner dateSpinerY;
    private SpinnerDateModel model1;
    private SpinnerDateModel modelTime2;
    private SpinnerDateModel modelTime3;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    public AddSessionController(){
        initComponents();
        initListeners();
        setTable();
    }

    private void initListeners() {

        timeSpinerH.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                String str = chooseFilm.getSelectedItem().toString();
                int i = 0;
                try {
                    statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("select duration from movies where name = '" + str + "';");
                    if(resultSet.next()){
                        i = resultSet.getInt(1);
                      //  System.out.println(1);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                String time = setTime(i);

                //System.out.println(2);

                Calendar cal = Calendar.getInstance();

                //cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.substring(0,2)));
                if(time.length() == 5){
                    cal.set(Calendar.MINUTE,Integer.parseInt(time.substring(3,5)));
                    cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.substring(0,2)));
                    //System.out.println(4);
                }
                else{
                    if(time.substring(1,2).equals(":")){
                        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.substring(0,1)));
                        cal.set(Calendar.MINUTE,Integer.parseInt(time.substring(2,4)));
                    }else{
                        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.substring(0,2)));
                        cal.set(Calendar.MINUTE,Integer.parseInt(time.substring(3,4)));
                    }

                    //System.out.println(5);
                }
                cal.set(Calendar.SECOND,0);
                cal.set(Calendar.MILLISECOND,0);

                Date d = cal.getTime();
                modelTime3.setValue(d);
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(chooseFilm.getSelectedIndex() != -1){
                    if(!isPresentSesseion()){
                        try {
                            int id = seseionId();
                            statement.execute("insert into session (sessionid, date, session_begin, session_end, movieid, hallid, status) VALUES (" +
                                    + id + ", '" + dateFormat.format(dateSpinerY.getValue()) + "', '" + timeFormat.format(timeSpinerH.getValue()) + "', '" + timeFormat.format(endTimeSpinerH.getValue()) + "', " +
                                    + movieId() + ", " + chooseHall.getSelectedItem().toString() +  ", true )");
                            int row = getRow();
                            int col = getCol();
                            int ticketId = getTicketId();
                            boolean vIp = false;
                            int price = 2;
                            for(int i = 0; i < row; ++i){
                                for(int j = 0; j < col; ++j){
                                    if(chooseHall.getSelectedItem().toString().equals("1") || chooseHall.getSelectedItem().toString().equals("3")){
                                        if(i == (row - 1)){
                                            price = 4;
                                            vIp = true;
                                        }else{
                                            price = 2;
                                            vIp = false;
                                        }
                                    }else {
                                        price = 2;
                                        vIp = false;
                                    }
                                    statement.execute("insert into tickets (ticketid, bought, booked, vip, seat, row, telephone_num, tab_num, sessionid, price) VALUES (" +
                                           ticketId + " ,false , false, " + vIp + ", " + (j + 1) + " , " + (i + 1) + ", null , null, " + id + ", " + price +")");
                                    ticketId++;
                                }
                            }
                            showMessageDialog(null, "Session added");
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(table1.getSelectedRow() > -1){
                    try {
                        statement.executeUpdate("update session set status = false where sessionid = " + table1.getValueAt(table1.getSelectedRow(), 0).toString());
                        listSession.remove(table1.getSelectedRow());
                        setTable();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    private int getTicketId() {
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select MAX(ticketid) + 1 from tickets");
            if(resultSet.next())return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int getRow() {
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select row_count from halls where hallid = " + chooseHall.getSelectedItem().toString());
            if(resultSet.next())return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getCol() {
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select row_seats_num from halls where hallid = " + chooseHall.getSelectedItem().toString());
            if(resultSet.next())return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int movieId() {
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select movieid from movies where name = '" + chooseFilm.getSelectedItem().toString() + "';");
            if(resultSet.next())return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int seseionId() {
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select Max(sessionid) + 1 from session");
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private boolean isPresentSesseion() {
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from session where hallid = " + chooseHall.getSelectedItem().toString() +
            " AND date = '" + dateFormat.format(dateSpinerY.getValue()) + "';");
            while (resultSet.next()){
                if(isAtInterval(resultSet.getString(3), resultSet.getString(4))){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isAtInterval(String string, String string1) {

        int from = Integer.parseInt(timeFormat.format(timeSpinerH.getValue()).substring(0, 2)) * 100 + Integer.parseInt(timeFormat.format(timeSpinerH.getValue()).substring(3, 5));
        int to = Integer.parseInt(timeFormat.format(endTimeSpinerH.getValue()).substring(0, 2)) * 100 + Integer.parseInt(timeFormat.format(endTimeSpinerH.getValue()).substring(3, 5));
        int start = Integer.parseInt(string.substring(0, 2)) * 100 + Integer.parseInt(string.substring(3, 5));
        int end = Integer.parseInt(string1.substring(0, 2)) * 100 + Integer.parseInt(string1.substring(3, 5));
        if((from >= start && from >= end) || (from < start && to < start)){
            return false;
        }
        return true;
    }

    private String setTime(int durat){
        int h = 0;
        int m = 0;
        if(timeFormat.format(timeSpinerH.getValue()).length() == 5){
            m = Integer.parseInt(timeFormat.format(timeSpinerH.getValue()).substring(3,5));

            h = Integer.parseInt(timeFormat.format(timeSpinerH.getValue()).substring(0,2));
            //System.out.println(4);
        }
        else{
            if(timeFormat.format(timeSpinerH.getValue()).substring(1,2).equals(":")){
                h = Integer.parseInt(timeFormat.format(timeSpinerH.getValue()).substring(0,1));
                m = Integer.parseInt(timeFormat.format(timeSpinerH.getValue()).substring(2,4));
            }else{
                h = Integer.parseInt(timeFormat.format(timeSpinerH.getValue()).substring(0,2));
                m = Integer.parseInt(timeFormat.format(timeSpinerH.getValue()).substring(3,4));
            }

            //System.out.println(5);
        }

        while(--durat != -1){
            if(++m >= 60){
                m = 0;
                if(++h >= 24){
                    h = 0;
                }
            }
        }
        return String.valueOf(h) + ':' + String.valueOf(m);

    }

    private void initComponents() {
        addSesion = new AddSesion();
        table1 = addSesion.getTable1();
        chooseFilm = addSesion.getChooseFilm();
        chooseHall = addSesion.getChooseHall();
        deleteButton = addSesion.getDeleteButton();
        addButton = addSesion.getAddButton();
        model = addSesion.getModel();
        jScrollPane = addSesion.getjScrollPane();
        timeSpinerH = addSesion.getTimeSpinerH();
        endTimeSpinerH = addSesion.getEndTimeSpinerH();
        dateSpinerY = addSesion.getDateSpinerY();
        model1 = addSesion.getModel1();
        modelTime2 = addSesion.getModelTime2();
        modelTime3 = addSesion.getModelTime3();

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select name from movies where status = true");
            while (resultSet.next()) chooseFilm.addItem(resultSet.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select hallid from halls");
            while (resultSet.next()) chooseHall.addItem(resultSet.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private List<String[]> listSession = new ArrayList<String[]>();


    private void setTable() {
        for(int i = table1.getRowCount(); i > 0; --i)
            model.removeRow(i - 1);
        try{
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select sessionid, m.name, hallid, session_begin, session_end, date  from session INNER join movies m on session.movieid = m.movieid where session.status = true order by sessionid");
            while (resultSet.next()){
                listSession.add(new String[]{String.valueOf(resultSet.getInt(1)), resultSet.getString(2), String.valueOf(resultSet.getInt(3)),
                        String.valueOf(resultSet.getDate(6)), resultSet.getString(4), resultSet.getString(5)});
                model.addRow(listSession.get(listSession.size() - 1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
