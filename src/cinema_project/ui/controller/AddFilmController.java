package cinema_project.ui.controller;

import cinema_project.data_base.Connect_Data_Base;
import cinema_project.ui.model.users.Film;
import cinema_project.ui.view.AddFilm;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static cinema_project.ui.controller.MainFrameController.findSpaceInString;
import static javax.swing.JOptionPane.showMessageDialog;

public class AddFilmController extends Connect_Data_Base {

    Statement statement = null;

    private AddFilm addFilmPane;
    private JTable FilmTableList;
    private JButton addFilmButton;
    private JButton deleteButton;
    private JTextField nameOfFilm;
    private JTextArea DescriptionArea;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private JSpinner prodactYearSpiner;
    private SpinnerDateModel model3;

    private SpinnerDateModel model1;
    private SpinnerDateModel model2;
    private JTextField duration;
    private JTextField ageRestictions;
    private JTextField imageChoose;
    private JTextField budgets;
    private DefaultTableModel model;
    private JScrollPane jScrollPane;
    private JButton clearAllButton;
    static JComboBox actor;
    static JComboBox director;
    private JButton addActorsButton;
    private JButton addDirectorsButton;
    private JButton selectButton;
    private JComboBox comboBox1;

    public AddFilmController(){
        initComponents();
        initListeners();
        setTable();
    }

    private List<Film> listOfFilm = new ArrayList<Film>();

    private void setTable() {
        for(int i = FilmTableList.getRowCount(); i > 0; --i)
            model.removeRow(i - 1);
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from  movies where status = true order by movieid ");
            Statement statement1 = connection.createStatement();
            Statement statement2 = connection.createStatement();
            String str = "";
            int id = 0;
            while (resultSet.next()){

                int[] arrayListActor = new int[100];
                int[] arrayListDirector = new int[100];
                id = resultSet.getInt(1);
                ResultSet resultSet2 = statement1.executeQuery("select actorid from  acting_movies " +
                        "where movieid = " + id);
                ResultSet resultSet3 = statement2.executeQuery("select directorid from  directing_movies " +
                        "where movieid = " + id);
                int i = 0;
                while (resultSet2.next()) {
                    arrayListActor[i] = resultSet2.getInt(1);
                    i++;
                }
                i = 0;
                while (resultSet3.next()){
                    arrayListDirector[i] = resultSet3.getInt(1);
                    i++;
                }
                Statement statement3 = connection.createStatement();
                ResultSet resultSet4 = statement3.executeQuery("select name from genres where genreid IN (SELECT genreid from genres_movies where movieid = " + id + ")");
                if(resultSet3.next())str = resultSet4.getString(1);
                listOfFilm.add(new Film(id , resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4),
                        resultSet.getInt(5), resultSet.getDate(6),
                        resultSet.getDate(7), resultSet.getString(8),
                        resultSet.getInt(9), arrayListActor ,arrayListDirector, resultSet.getString(10), str));
                model.addRow(new String[]{String.valueOf(listOfFilm.get(listOfFilm.size() - 1).get_id()), listOfFilm.get(listOfFilm.size() - 1).getName(), String.valueOf(listOfFilm.get(listOfFilm.size() - 1).get_duration())
                , String.valueOf(listOfFilm.get(listOfFilm.size() - 1).get_prodactYear()), String.valueOf(listOfFilm.get(listOfFilm.size() - 1).get_start()), String.valueOf(listOfFilm.get(listOfFilm.size() - 1).get_end()),
                        String.valueOf(listOfFilm.get(listOfFilm.size() - 1).get_age()), listOfFilm.get(listOfFilm.size() - 1).get_budget(), listOfFilm.get(listOfFilm.size() - 1).getImage()});

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initListeners() {
        spinner1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
//Calendar cal = Calendar.getInstance();
//cal.add(Calendar.MONTH, 1);
                Calendar cal = Calendar.getInstance();
                cal.set(Integer.parseInt(dateFormat.format(spinner1.getValue()).substring(0,4)),
                        Integer.parseInt(dateFormat.format(spinner1.getValue()).substring(5,7)) ,
                        Integer.parseInt(dateFormat.format(spinner1.getValue()).substring(8,10)));
                Date day = cal.getTime();
                model2.setValue(day);

            }
        });

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser("C:\\Users\\Molod\\IdeaProjects\\Project\\src\\image");
                int ret = fileopen.showDialog(null, "Open file");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    imageChoose.setText("/image/films/" + file.getName());
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(FilmTableList.getSelectedRow() > -1) {
                    try {
                        statement.executeUpdate("update movies set status = false where movieid = " + listOfFilm.get(FilmTableList.getSelectedRow()).get_id());
                        listOfFilm.remove(FilmTableList.getSelectedRow());
                        setTable();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        addFilmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textRight()){
                    try {
                        statement = connection.createStatement();
                        int j = getMovied();
                        statement.execute("insert Into movies (movieid, name, description, production_year, duration, premiere_date," +
                                " end_showing, budget, age_restrictions, image, status) VALUES(" + j
                                + ", '" +nameOfFilm.getText() +"', '" + DescriptionArea.getText() + "', '" + Integer.parseInt(dateFormat.format(prodactYearSpiner.getValue()).substring(0,4)) + "', '"+
                                duration.getText() +"', '" + dateFormat.format(spinner1.getValue()) + "', '" + dateFormat.format(spinner2.getValue()) + "', '" +
                                budgets.getText() +"', '" + ageRestictions.getText() + "', '" + imageChoose.getText() + "', true )");
                        Statement statement1 = connection.createStatement();
                        statement1.execute("insert into genres_movies (movieid, genreid) VALUES (" + j + " , " + genresID(comboBox1.getSelectedItem().toString()) + ")");
                        int[] _director = new int[100];
                        int[] _actor = new int[100];
                        for (int i = 0; i < actor.getItemCount(); ++i){
                            statement.execute("insert into acting_movies (movieid, actorid) VALUES (" + j + ", " + actorPresent(actor.getItemAt(i).toString()) + ")");
                            _actor[i] =  actorPresent(actor.getItemAt(i).toString());
                        }
                        for (int i = 0; i < director.getItemCount(); ++i){
                            statement.execute("insert into directing_movies (movieid, directorid) VALUES (" + j + ", " + directorPresent(director.getItemAt(i).toString()) + ")");
                            _director[i] =  directorPresent(director.getItemAt(i).toString());
                        }
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        listOfFilm.add(new Film(j , nameOfFilm.getText(),
                                DescriptionArea.getText(), Integer.parseInt(dateFormat.format(prodactYearSpiner.getValue()).substring(0,4)),
                                Integer.parseInt(duration.getText()), simpleDateFormat.parse(dateFormat.format(spinner1.getValue())),
                                simpleDateFormat.parse(dateFormat.format(spinner2.getValue())), budgets.getText(),
                                        Integer.parseInt(ageRestictions.getText()), _actor ,_director, imageChoose.getText(), comboBox1.getSelectedItem().toString()));
                        showMessageDialog(null, "Film added");
                        clear();
                        setTable();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        ListSelectionModel cellSelectionModel = FilmTableList.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        addActorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddActorOrDirectorsController("actor");
            }
        });
        addDirectorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddActorOrDirectorsController("director");
            }
        });
        clearAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
    }

    private int genresID(String string){
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select genreid from genres where name = '" + string + "'");
            if (resultSet.next()) return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int actorPresent(String string) {
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select actorid from actors where last_name = '" + string.substring(0, findSpaceInString(string))
                    + "' AND first_name = '" +  string.substring(findSpaceInString(string) + 1, string.length()) + "';");
            if(resultSet.next()) return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int directorPresent(String string) {
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select directorid from directors where last_name = '" + string.substring(0, findSpaceInString(string))
                    + "' AND first_name = '" +  string.substring(findSpaceInString(string) + 1, string.length()) + "';");
            if(resultSet.next()) return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int getMovied(){
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select MAX(movieid) + 1 from movies");
            if(resultSet.next())return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private boolean textRight() {
        if(!nameOfFilm.getText().equals("") && !DescriptionArea.getText().equals("")  &&
                !duration.getText().equals("") &&
                !budgets.getText().equals("") && !ageRestictions.getText().equals("") && !imageChoose.getText().equals("")){
            for(int i = 0; i < listOfFilm.size(); ++i){
                if(listOfFilm.get(i).getName().equals(nameOfFilm.getText())){
                    showMessageDialog(null, "Film with this name is present");
                    return false;
                }
            }
            return true;
        }
        showMessageDialog(null, "Look at your entered");
        return false;
    }

    private void clear(){
        nameOfFilm.setText("");
        DescriptionArea.setText("");
        duration.setText("");
        budgets.setText("");
        ageRestictions.setText("");
        imageChoose.setText("");
        actor.removeAllItems();
        director.removeAllItems();
    }

    private String getFullname(int id, String table){
        String string = "";
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select last_name, first_name from " + table + "s where " + table + "id = " + id);
            if(resultSet.next())string = resultSet.getString(1) + ' ' + resultSet.getString(2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return string;
    }

    private int getCountOf(String string, int id){
        int i = 0;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select count(*) from " + string + "_movies where movieid = " + id);
            if(resultSet.next())i = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }


    private void initComponents() {
        this.addFilmPane = new AddFilm();

        selectButton = addFilmPane.getSelectButton();
        FilmTableList = addFilmPane.getFilmTableList();
        deleteButton = addFilmPane.getDeleteButton();
        nameOfFilm = addFilmPane.getNameOfFilm();
        DescriptionArea = addFilmPane.getDescriptionArea();
        duration = addFilmPane.getDuration();
        prodactYearSpiner = addFilmPane.getProdactYearSpiner();
        model3 = addFilmPane.getModel3();
        model1 = addFilmPane.getModel1();
        model2 = addFilmPane.getModel2();
        spinner1 = addFilmPane.getSpinner1();
        spinner2 = addFilmPane.getSpinner2();
        comboBox1 = addFilmPane.getComboBox1();
        ageRestictions = addFilmPane.getAgeRestictions();
        imageChoose = addFilmPane.getImageChoose();
        addFilmButton = addFilmPane.getAddFilmButton();
        budgets = addFilmPane.getBudgets();
        model = addFilmPane.getModel();
        jScrollPane = addFilmPane.getJscrollpane();
        actor = addFilmPane.getActor();
        director = addFilmPane.getDirector();
        spinner1 = addFilmPane.getSpinner1();
        spinner2 = addFilmPane.getSpinner2();
        addActorsButton = addFilmPane.getAddActorsButton();
        addDirectorsButton = addFilmPane.getAddDirectorsButton();
        clearAllButton = addFilmPane.getClearAllButton();
        DescriptionArea.setWrapStyleWord(true);
        DescriptionArea.setLineWrap(true);
        DescriptionArea.setColumns(1);
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select name from genres");
            while (resultSet.next()){
                comboBox1.addItem(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
