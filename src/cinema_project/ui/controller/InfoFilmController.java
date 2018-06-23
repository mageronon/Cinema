package cinema_project.ui.controller;

import cinema_project.data_base.Connect_Data_Base;
import cinema_project.ui.view.FilmInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InfoFilmController extends Connect_Data_Base {

    private FilmInfo panel;
    private JTable table1;
    private DefaultTableModel model;

    Statement statement;

    public InfoFilmController() {
        this.panel = new FilmInfo();
        table1 = panel.getTable1();

        String[] columnNames = {"Film", "Hall", "Date", "Session begin", "Session end", "Visits", "Profit", "Showing?"};
        Object[][] objects = {};
        table1.setFillsViewportHeight(true);
        TableColumnModel colmodel = table1.getColumnModel();
        model = new DefaultTableModel(objects, columnNames);
        table1.setModel(model);
        colmodel.getColumn(0).setPreferredWidth(200);
        colmodel.getColumn(1).setPreferredWidth(50);
        colmodel.getColumn(2).setPreferredWidth(100);
        colmodel.getColumn(3).setPreferredWidth(50);
        colmodel.getColumn(4).setPreferredWidth(50);
        colmodel.getColumn(5).setPreferredWidth(50);
        colmodel.getColumn(6).setPreferredWidth(200);
        colmodel.getColumn(7).setPreferredWidth(50);
        String str = "";
        try {

            statement = connection.createStatement();
            ResultSet resultSet1 = statement.executeQuery("select  m.name, hallid, session_begin, session_end, date , session.status , session.sessionid " +
                    "from session INNER join movies m on session.movieid = m.movieid " +
                    " order by m.name");
            while (resultSet1.next()) {

                Statement statement1 = connection.createStatement();
                ResultSet resultSet2 = statement1.executeQuery("select count(*), SUM (price) from tickets INNER join session on session.sessionid = tickets.sessionid" +
                        " where ( bought = true OR booked = true ) AND session.sessionid = " + resultSet1.getInt(7));
                if (resultSet2.next()){
                    if(resultSet2.getInt(1) == 0){
                        str = "0 $";
                        model.addRow(new String[]{String.valueOf(resultSet1.getString(1)), String.valueOf(resultSet1.getInt(2)),
                                String.valueOf(resultSet1.getDate(5)), resultSet1.getString(3), resultSet1.getString(4),
                                String.valueOf(resultSet2.getInt(1)), str,
                                String.valueOf(resultSet1.getBoolean(6))});
                    }else {
                        str = resultSet2.getString(2).substring(0, resultSet2.getString(2).length() - 2) + " $";
                        model.addRow(new String[]{String.valueOf(resultSet1.getString(1)), String.valueOf(resultSet1.getInt(2)),
                                String.valueOf(resultSet1.getDate(5)), resultSet1.getString(3), resultSet1.getString(4),
                                String.valueOf(resultSet2.getInt(1)), str,
                                String.valueOf(resultSet1.getBoolean(6))});
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
