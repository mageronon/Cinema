package cinema_project.ui.controller;

import cinema_project.Main;
import cinema_project.data_base.Connect_Data_Base;
import cinema_project.ui.model.users.Ticket;
import cinema_project.ui.view.Tickets;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class TicketsController extends Connect_Data_Base {
    private static Statement staticStatement;
    private static JTable table ;
    private static Tickets panel1;
    private static JButton printSelectButton;
    private static DefaultTableModel model ;

    public TicketsController(){
        this.panel1 = new Tickets();
        table = panel1.getJlist();
        printSelectButton = panel1.getPrintSelectButton();
        model = panel1.getListModel();



        printSelectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(table.isColumnSelected(table.getSelectedColumn())){

                    Document document = new Document();
                    try {
                        PdfWriter.getInstance(document, new FileOutputStream("Tickets.pdf"));
                        document.open();
                        BaseFont bf = BaseFont.createFont("c:/windows/fonts/arialbd.ttf",
                                "Cp1251", BaseFont.EMBEDDED);
                        Font font = new Font(bf, 12);
                        for(int j = 0; j < table.getSelectedRows().length; ++j){
                            for(int i = 0 ; i < table.getColumnCount(); ++i){
                                document.add(new Paragraph(table.getColumnName(i) + " : " + String.valueOf(table.getValueAt(table.getSelectedRows()[j], i)), font));
                            }
                            document.add(new Paragraph("-------------"));
                            if(j % 2 == 0 && j != 0) document.newPage();
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


                }
            }
        });

    }

    private static int countOfMyTickets(){
        try {
            staticStatement = connection.createStatement();
            ResultSet resultSet = staticStatement.executeQuery("select count(*) from tickets where telephone_num = '" + Main.user.getPhone_number() + "'");
            if (resultSet.next())return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static List<Ticket> listOfTickets = new ArrayList<Ticket>();

    private static void setListTickets(){
        try {
            staticStatement = connection.createStatement();
            ResultSet resultSet = staticStatement.executeQuery("select *" +
                    " from tickets " +
                    " where telephone_num ='" + Main.user.getPhone_number() + "';");
            Statement statement = connection.createStatement();
            Statement statement1 = connection.createStatement();
            ResultSet resultSet1;
            ResultSet resultSet2;
            String firs_name = "";
            String last_name = "";
            String name = "";
            String date = "";
            String time = "";
            int hallId = -1;
            String last = "";
            String first = "";
            while (resultSet.next()){

                resultSet1 = statement.executeQuery("select name, date, session_begin, hallid " +
                        "from session inner join movies on movies.movieid = session.movieid " +
                        "where sessionid = " + resultSet.getInt(9) );
                if(resultSet1.next()){
                    name = resultSet1.getString(1);
                    date = resultSet1.getString(2);
                    time = resultSet1.getString(3);
                    hallId = resultSet1.getInt(4);
                }else {
                    name = "";
                    date = "";
                    time = "";
                    hallId = -1;
                }
                resultSet2 = statement1.executeQuery("select last_name, first_name from cashier where tab_num = " + resultSet.getInt(8));
                if (resultSet2.next()){
                    last = resultSet2.getString(1);
                    first = resultSet2.getString(2);
                }else {
                    last = "";
                    first = "";
                }

                listOfTickets.add(new Ticket(name, date, time, resultSet.getInt(6),
                        resultSet.getInt(5), Main.user.getFirst_name(), Main.user.getLast_name(), hallId, Main.user.getPhone_number(),
                        last, first, resultSet.getString(10).substring(0, resultSet.getString(10).length() - 1)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setJtable(){
        listOfTickets.clear();
        setListTickets();
        String[] string = new String[12];

        for (int i = 0; i < countOfMyTickets(); ++i){
            string[0] = listOfTickets.get(i).getNameMovies();
            string[1] = String.valueOf(listOfTickets.get(i).getRow());
            string[2] = String.valueOf(listOfTickets.get(i).getCol());
            string[3] = String.valueOf(listOfTickets.get(i).getHall());
            string[4] = String.valueOf(listOfTickets.get(i).getDate());
            string[5] = String.valueOf(listOfTickets.get(i).getTime());
            string[6] = listOfTickets.get(i).getPrice();
            string[7] = listOfTickets.get(i).getLastName();
            string[8] = listOfTickets.get(i).getFirstName();
            string[9] = listOfTickets.get(i).getPhoneNumber();
            string[10] = listOfTickets.get(i).getLastCahier();
            string[11] = listOfTickets.get(i).getFirstCahier();
            model.addRow(string);
        }
    }

}
