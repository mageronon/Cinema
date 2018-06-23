package cinema_project.ui.controller;

import cinema_project.data_base.Connect_Data_Base;
import cinema_project.ui.view.AmdinPanelIOInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminPanelInfoController extends Connect_Data_Base {

    private AmdinPanelIOInfo panel;
    private DefaultTableModel model;
    private JTable table1;

    Statement statement;

    public  AdminPanelInfoController(int i){
        this.panel = new AmdinPanelIOInfo();
        table1 = panel.getTable1();

        String[] columnNames;
        Object[][] objects = {};
        table1.setFillsViewportHeight(true);
        TableColumnModel colmodel = table1.getColumnModel();
        switch (i){
            case 1:
                columnNames = new String[]{"Id", "Film", "Visits"};
                model = new DefaultTableModel(objects, columnNames);
                table1.setModel(model);
                colmodel.getColumn(0).setPreferredWidth(100);
                colmodel.getColumn(1).setPreferredWidth(300);
                colmodel.getColumn(2).setPreferredWidth(100);
                try {
                    statement = connection.createStatement();
                    Statement statement1 = connection.createStatement();
                    ResultSet resultSet1 = statement1.executeQuery("select movieid, name from movies" +
                            " order by movieid");
                    while (resultSet1.next()){
                        ResultSet resultSet = statement.executeQuery("select count (*) from movies left outer join session on movies.movieid = session.movieid" +
                                " left outer  join  tickets t on session.sessionid = t.sessionid" +
                                " where (bought = true or booked = true ) AND movies.movieid = " + resultSet1.getInt(1));
                        if(resultSet.next()){
                            model.addRow(new String[]{String.valueOf(resultSet1.getInt(1)), resultSet1.getString(2), String.valueOf(resultSet.getInt(1))});
                        }else {
                            model.addRow(new String[]{String.valueOf(resultSet1.getInt(1)), resultSet1.getString(2), "0"});
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                columnNames = new String[]{"ActorId", "Last name", "First name", "Visits"};
                model = new DefaultTableModel(objects, columnNames);
                table1.setModel(model);
                colmodel.getColumn(0).setPreferredWidth(50);
                colmodel.getColumn(1).setPreferredWidth(200);
                colmodel.getColumn(2).setPreferredWidth(200);
                colmodel.getColumn(3).setPreferredWidth(100);
                try {
                    Statement statement1 = connection.createStatement();
                    ResultSet resultSet1 = statement1.executeQuery("select actorid, last_name, first_name from actors order by  actorid");
                    statement = connection.createStatement();
                    while (resultSet1.next()){
                        ResultSet resultSet = statement.executeQuery("select count(*) from actors left outer join acting_movies a on actors.actorid = a.actorid" +
                                " left outer join movies m on a.movieid = m.movieid left outer join session s on m.movieid = s.movieid left outer join tickets t on s.sessionid = t.sessionid" +
                                " where (booked = true OR bought = true ) AND actors.actorid = " + resultSet1.getInt(1));
                        if(resultSet.next()){
                            model.addRow(new String[]{String.valueOf(resultSet1.getInt(1)), resultSet1.getString(2), resultSet1.getString(3)
                                    , String.valueOf(resultSet.getInt(1))});
                        }else {
                            model.addRow(new String[]{String.valueOf(resultSet1.getInt(1)), resultSet1.getString(2), resultSet1.getString(3)
                                    , "0"});
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                columnNames = new String[]{"DirectorId", "Last name", "First name", "Visits"};
                model = new DefaultTableModel(objects, columnNames);
                table1.setModel(model);
                colmodel.getColumn(0).setPreferredWidth(50);
                colmodel.getColumn(1).setPreferredWidth(200);
                colmodel.getColumn(2).setPreferredWidth(200);
                colmodel.getColumn(3).setPreferredWidth(100);
                try {
                    Statement statement1 = connection.createStatement();
                    ResultSet resultSet1 = statement1.executeQuery("select directorid, last_name, first_name from directors order by directorid");
                    statement = connection.createStatement();
                    while (resultSet1.next()){
                        ResultSet resultSet = statement.executeQuery("select count (*) from directing_movies " +
                                " left outer join movies m on directing_movies.movieid = m.movieid left outer join session on m.movieid = session.movieid left outer join tickets t on session.sessionid = t.sessionid" +
                                " where (bought = true  or booked = true) AND directing_movies.directorid = " + resultSet1.getInt(1));
                        if(resultSet.next()){
                            model.addRow(new String[]{String.valueOf(resultSet1.getInt(1)), resultSet1.getString(2), resultSet1.getString(3),
                                    String.valueOf(resultSet.getInt(1))});
                        }else {
                            model.addRow(new String[]{String.valueOf(resultSet1.getInt(1)), resultSet1.getString(2), resultSet1.getString(3),
                                    "0"});
                        }
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                columnNames = new String[]{"GenreIf", "name", "visits"};
                model = new DefaultTableModel(objects, columnNames);
                table1.setModel(model);
                colmodel.getColumn(0).setPreferredWidth(50);
                colmodel.getColumn(1).setPreferredWidth(200);
                colmodel.getColumn(2).setPreferredWidth(100);
                try {
                    Statement statement1 = connection.createStatement();
                    ResultSet resultSet1 = statement1.executeQuery("select genreid, name from genres order by genreid");
                    statement = connection.createStatement();
                    while (resultSet1.next()){
                        ResultSet resultSet = statement.executeQuery("select count(*) from genres_movies " +
                                " left outer join movies m on genres_movies.movieid = m.movieid" +
                                " left outer join session on session.movieid = m.movieid left outer join tickets t on session.sessionid = t.sessionid" +
                                " where (booked = true or bought = true ) AND  genres_movies.genreid = " + resultSet1.getInt(1)
                        );
                        if(resultSet.next()){
                            model.addRow(new String[]{String.valueOf(resultSet1.getInt(1)), resultSet1.getString(2), String.valueOf(resultSet.getInt(1))});
                        }else {
                            model.addRow(new String[]{String.valueOf(resultSet1.getInt(1)), resultSet1.getString(2), "0"});
                        }

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }
}
