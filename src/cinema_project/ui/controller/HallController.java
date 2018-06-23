package cinema_project.ui.controller;

import cinema_project.Main;
import cinema_project.data_base.Connect_Data_Base;
import cinema_project.ui.model.users.Ticket;
import cinema_project.ui.view.BuyTickets;
import cinema_project.ui.view.Hall;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class HallController extends Connect_Data_Base {

    Statement statement = null;

    private static int COLL;
    private static int ROW;

    private static List<JButton> list = new ArrayList<JButton>();

    private static DefaultListModel listModel ;
    private static JList jlist;
    private static int sessionId;

    private static Hall hall;
    private static JButton buy;
    public static String phoneBuyer = "";
    private static List<JButton> buyNow = null;



    public HallController(int coll, int row, int sesion){
        COLL = coll;
        ROW = row;
        sessionId = sesion;
        hall = new Hall(COLL, ROW, sessionId);
        list = hall.getList();
        listModel = hall.getListModel();
        jlist = hall.getJlist();
        buy = hall.getBuy();

        buy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int j = listModel.getSize();
                if(j > 0){

                    if(Main.user != null){
                        if(Main.user.is_isCash() == false){
                            phoneBuyer = Main.user.getPhone_number();
                            setTicket();
                        }else {
                            BuyTickets buyTickets = new BuyTickets();
                        }
                    }else {
                        BuyTickets buyTickets = new BuyTickets();
                    }
                }
            }
        });
    }

    public void show(){
        hall.setVisible(true);
    }
    private static int findIdCashier(){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select tab_num from cashier where telephone_num = '" + Main.user.getPhone_number() + "'");
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private static List<Ticket> tickets = new ArrayList<Ticket>();

    public static void setTicket() {

        buyNow = new ArrayList<JButton>();
        for(int i = 0; i < ROW; ++i){
            for(int k = 0; k < COLL; ++k){
                if(list.get(i * COLL + k).getBackground() == Color.GREEN){
                    buyNow.add(list.get(i * COLL + k));
                }
            }
        }

        try {
            if(!clientIsPresent()){
                String last_name = JOptionPane.showInputDialog(hall, "What's your last name?");
                String first_name = JOptionPane.showInputDialog(hall, "What's your first name?");
                Statement statement = connection.createStatement();
                statement.execute("insert into client (telephone_num, first_name, last_name, accountid) VALUES (" + phoneBuyer + ", '"
                        + first_name + "', ' " + last_name + "', " + null + ")");
            }
            while (!buyNow.isEmpty()) {

                int i = ((buyNow.get(buyNow.size() - 1).getY() / buyNow.get(buyNow.size() - 1).getHeight()) * COLL + Integer.parseInt(buyNow.get(buyNow.size() - 1).getText()) - 1);

                if(buyCashier()){

                    PreparedStatement statement = connection.prepareStatement("UPDATE tickets SET bought = ?, booked = ?, telephone_num = ?, tab_num = ? where sessionid = ? AND row = ? AND seat = ?");
                    statement.setBoolean(1, true);
                    statement.setBoolean(2, true);
                    statement.setInt(3, Integer.parseInt(phoneBuyer));
                    statement.setInt(4, findIdCashier());
                    statement.setInt(5, sessionId);
                    statement.setInt(6, ((buyNow.get(buyNow.size() - 1).getY() / buyNow.get(buyNow.size() - 1).getHeight()) + 1));
                    statement.setInt(7, Integer.parseInt(buyNow.get(buyNow.size() - 1).getText()));
                    statement.executeUpdate();
                    tickets.add(new Ticket(getNameOfFilm(), getDate(), getTime(), ((buyNow.get(buyNow.size() - 1).getY() / buyNow.get(buyNow.size() - 1).getHeight()) + 1),
                            Integer.parseInt(buyNow.get(buyNow.size() - 1).getText()), getFirstName(), getLastName(), Integer.parseInt(hall.getTitle().substring(hall.getTitle().length() - 1)),
                            phoneBuyer, Main.user.getFirst_name(), Main.user.getLast_name(), getPrice(i / COLL, i % COLL)));
                    System.out.println("I AM CASHIER");
                }else{
                    PreparedStatement statement = connection.prepareStatement("UPDATE tickets SET bought = ?, booked = ?, telephone_num = ? where sessionid = ? AND row = ? AND seat = ?");
                    statement.setBoolean(1, true);
                    statement.setBoolean(2, true);
                    statement.setInt(3, Integer.parseInt(phoneBuyer));
                    statement.setInt(4, sessionId);
                    statement.setInt(5, ((buyNow.get(buyNow.size() - 1).getY() / buyNow.get(buyNow.size() - 1).getHeight()) + 1));
                    statement.setInt(6, Integer.parseInt(buyNow.get(buyNow.size() - 1).getText()));
                    statement.executeUpdate();
                    tickets.add(new Ticket(getNameOfFilm(), getDate(), getTime(), ((buyNow.get(buyNow.size() - 1).getY() / buyNow.get(buyNow.size() - 1).getHeight()) + 1),
                            Integer.parseInt(buyNow.get(buyNow.size() - 1).getText()), getFirstName(), getLastName(), Integer.parseInt(hall.getTitle().substring(hall.getTitle().length() - 1)),
                            phoneBuyer, "", "", getPrice(i / COLL, i % COLL)));
                }



                 //tickets.remove(tickets.size() - 1);
                list.get(i).setBackground(Color.gray);
                 list.get(i).setEnabled(false);

                 buyNow.remove(buyNow.size() -1);
            }
            listModel.removeAllElements();

            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream("Tickets.pdf"));
                document.open();
                BaseFont bf = BaseFont.createFont("c:/windows/fonts/arialbd.ttf",
                        "Cp1251", BaseFont.EMBEDDED);
                com.itextpdf.text.Font font = new Font(bf, 12);
                int j = 1;
                while(!tickets.isEmpty())
                {

                    document.add(new Paragraph("Film : " + tickets.get(tickets.size() - 1).getNameMovies(), font));
                    document.add(new Paragraph("Row : " + tickets.get(tickets.size() - 1).getRow(), font));
                    document.add(new Paragraph("Seat : " + tickets.get(tickets.size() - 1).getCol(), font));
                    document.add(new Paragraph("Hall : " + tickets.get(tickets.size() - 1).getHall(), font));
                    document.add(new Paragraph("Date : " + tickets.get(tickets.size() - 1).getDate(), font));
                    document.add(new Paragraph("Session Begin : " + tickets.get(tickets.size() - 1).getTime(), font));
                    document.add(new Paragraph("Price : " + tickets.get(tickets.size() - 1).getPrice(), font));
                    document.add(new Paragraph("Buyer First Name : " + tickets.get(tickets.size() - 1).getFirstName(), font));
                    document.add(new Paragraph("Buyer Last Name : " + tickets.get(tickets.size() - 1).getLastName(), font));
                    document.add(new Paragraph("Buyer phone number : " + tickets.get(tickets.size() - 1).getPhoneNumber(), font));
                    document.add(new Paragraph("Cashier First name : " + tickets.get(tickets.size() - 1).getFirstCahier(), font));
                    document.add(new Paragraph("Cashier Last name : " + tickets.get(tickets.size() - 1).getLastCahier(), font));
                    tickets.remove(tickets.size() - 1);

                    document.add(new Paragraph("-------------"));
                    if(j % 2 == 0 && j != 1) document.newPage();
                    j++;
                    //document.add(chunk);
                }

                document.close();
                showMessageDialog(null, "Saved ticket");
            } catch (DocumentException e1) {
                e1.printStackTrace();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String getPrice(int i, int j){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select price from tickets"+
                    " where sessionid = " + sessionId + " AND row = " +(i + 1) + " AND seat = " + (j + 1));
            if (resultSet.next()) {
                return resultSet.getString(1).substring(0, resultSet.getString(1).length() - 2) + '$';
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getFirstNameCashier(){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select first_name from cashier"+
                    " where tab_num = " + findIdCashier());
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getLastNameCashier(){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select last_name from cashier"+
                    " where tab_num = " + findIdCashier());
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getFirstName(){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select first_name from client "+
                    " where telephone_num = " + Integer.parseInt(phoneBuyer));
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getLastName(){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select last_name from client "+
                    " where telephone_num = " + Integer.parseInt(phoneBuyer));
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getDate(){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select date from session "+
                    " where sessionid = " + sessionId);
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getTime(){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select session_begin from session" +
                    " where sessionid = " + sessionId);
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getNameOfFilm(){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select name from movies inner join session on movies.movieid = session.movieid" +
                    " where sessionid = " + sessionId);
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static boolean clientIsPresent() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from client where telephone_num = " + Integer.parseInt(phoneBuyer));
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean buyCashier() {
        try {
            if(Main.user == null){
                return false;
            }
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from cashier where telephone_num = '" + Main.user.getPhone_number() + "';");
            if (resultSet.next()) {
               return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setTitle(String string){
        hall.setTitle(string);
    }

    private List<JButton> booked = new ArrayList<JButton>();
    private  List<JButton> vip = new ArrayList<JButton>();

    public void rowSeatIsBooked(){
        int[][] booked = new int[ROW][COLL];
        boolean[][] vip = new boolean[ROW][COLL];
        for(int i = 0; i < ROW; i++){
            for(int j = 0; j < COLL; j++){
                booked[i][j] = 0;
                vip[i][j] = false;
            }
        }

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select row, seat, vip from tickets where sessionid = " + sessionId + " AND (booked = TRUE OR bought = TRUE)");
            while (resultSet.next()) {
                booked[resultSet.getInt(1) - 1][resultSet.getInt(2) - 1] = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Statement statement1 = connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery("select row, seat, vip from tickets where sessionid = " + sessionId + " AND vip = TRUE");
            while (resultSet1.next()) {
                vip[resultSet1.getInt(1) - 1][resultSet1.getInt(2) - 1] = resultSet1.getBoolean(3);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < ROW; i++){
            for(int j = 0; j < COLL; j++){
                if(vip[i][j] == true){
                    list.get(i * COLL + j).setBackground(Color.CYAN);
                }
                if(booked[i][j] == 1){
                    list.get(i * COLL + j).setBackground(Color.gray);
                    list.get(i * COLL + j).setEnabled(false);
                }
            }
        }
    }

    public static JButton createGridButton(final int row, final int col) {
        final JButton b = new JButton("" + (col + 1));
        b.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Pair<Integer, Integer> pair = new Pair<Integer, Integer>(row, col );
                String p = "";
                try {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("select price from tickets where sessionid = " + sessionId + " AND seat = " + (col + 1) + " AND row = " + (row + 1 ));
                    if(resultSet.next()) {
                        p = resultSet.getString(1).substring(0, resultSet.getString(1).length() - 1);
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                if(getGridButton(row, col).getBackground() != Color.GREEN){
                    getGridButton(row, col).setBackground(Color.GREEN);
                    listModel.addElement("row:" + (pair.getKey() + 1) + " seat:" + (pair.getValue() + 1) + " | " + p + '$');
                }
                else {
                    getGridButton(row, col).setBackground(Color.ORANGE);
                    listModel.remove(frindIndex("row:" + (pair.getKey() + 1) + " seat:" + (pair.getValue() + 1) + " | " + p + '$'));
                }
            }
        });
        return b;
    }

    public static JButton getGridButton(int r, int c) {
        int index = r * COLL + c;
        return list.get(index);
    }

    public static int frindIndex(String string){
        for(int i = 0; i < jlist.getModel().getSize(); i++){
            if(string.equals(listModel.get(i).toString())){
                return i;
            }
        }
        return -1;
    }
}
