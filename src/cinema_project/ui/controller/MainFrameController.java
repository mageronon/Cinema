package cinema_project.ui.controller;

import cinema_project.Main;
import cinema_project.data_base.Connect_Data_Base;
import cinema_project.ui.model.users.Film;
import cinema_project.ui.view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;


public class MainFrameController extends Connect_Data_Base{

    private List<String> res = null;

    private MainFrame mainFrame;

    static JButton ticketsButton;

    private JButton actorTicketsInfoButton;
    private JButton directorsPopularityInfoButton;
    private JButton genresPopularityInfoButton;
    private JButton infoFilmButton;
    public static JButton logInButton;
    public static JButton logOutButton;
    private static JTextField firstNameField;
    private static JTextField lastNameField;
    private static JTextField phoneField;
    static JButton saveChangeButton;
    public static JTabbedPane tabbedPane1;
    private static JPanel films;
    private static JPanel account;
    public static JPanel admin;
    private JLabel image;
    private JComboBox dateChoose;
    private JComboBox TimeChoose;
    private static JButton buyTicketsButton;
    private JTextArea descriptionText;
    private JComboBox actorsBox;
    private JComboBox directorsBox;
    private JLabel actorIcon;
    private JLabel nameOfFilm;
    private JLabel directorIcon;

    private static JComboBox moviesByGenre;
    private static JComboBox moviesByActor;
    private static JComboBox moviesByDirectors;

    private static JButton changeSessionButton;
    private static JButton changeFilmButton;
    private static JButton CashierButton;


    private static JComboBox comboBox1;
    Statement statement = null;
    private static Integer inFilm = 0;
    private ImageIcon imageIcon;
    private Image img;
    private Image NewImage;
    private ImageIcon ime;
    static JButton resetOldButton;

    public static ArrayList<Film> vetorOfFilm  = new ArrayList<Film>();

    public MainFrameController() {
        initComponents();
        initListeners();
        setFilmsToVector();
        setFilm();

        if(Main.user != null){
            logInButton.setVisible(false);
            saveChangeButton.setEnabled(true);
            ticketsButton.setEnabled(true);
            resetOldButton.setEnabled(true);
            logOutButton.setVisible(true);
            if(!Main.user.isAdmin()){
                tabbedPane1.remove(2);
            }
        }else {
            logInButton.setVisible(true);
            saveChangeButton.setEnabled(false);
            ticketsButton.setEnabled(false);
            resetOldButton.setEnabled(false);
            logOutButton.setVisible(false);
            tabbedPane1.remove(2);
        }
    }


    private static void setGetnres(){
        moviesByGenre.addItem("No Choose");
        try{
            staticStatement = connection.createStatement();
            ResultSet resultSet = staticStatement.executeQuery("select name from genres;");
            while (resultSet.next()){
                moviesByGenre.addItem(resultSet.getString(1));
            }
        } catch (SQLException e) {
         e.printStackTrace();
        }
    }

    private static void setDirectors(){
        moviesByDirectors.addItem("No Choose");
        try{
            staticStatement = connection.createStatement();
            staticStatement = connection.createStatement();
            ResultSet resultSet = staticStatement.executeQuery("select last_name, first_name from directors;");
            while (resultSet.next()){
                moviesByDirectors.addItem(resultSet.getString(1) + ' ' + resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void setActors(){
        moviesByActor.addItem("No Choose");
        try{
            staticStatement = connection.createStatement();
            staticStatement = connection.createStatement();
            ResultSet resultSet = staticStatement.executeQuery("select last_name, first_name from actors;");
            while (resultSet.next()){
                moviesByActor.addItem(resultSet.getString(1) + ' ' + resultSet.getString(2));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int findSpaceInString(String string){
        for (int i = 0; i < string.length(); ++i){
            if(string.charAt(i) == ' ')return i;
        }
        return -1;
    }

    private  static  void setFilm(){
        comboBox1.removeAllItems();
        try{
            staticStatement = connection.createStatement();
            Statement staticStatement1 = connection.createStatement();
            Statement staticStatement2 = connection.createStatement();
            String string = "";
            String string1 = "";
            String string2 = "";
            int i = findSpaceInString(moviesByDirectors.getSelectedItem().toString());
            int j = findSpaceInString(moviesByActor.getSelectedItem().toString());

            if(moviesByDirectors.getSelectedItem().toString().equals("No Choose")){
                string = "";
            }else{
                string = " d.last_name = '" + moviesByDirectors.getSelectedItem().toString().substring(0, i) + "' AND d.first_name = '" +
                        moviesByDirectors.getSelectedItem().toString().substring(i + 1, moviesByDirectors.getSelectedItem().toString().length()) + "' ";
            }
            if(moviesByGenre.getSelectedItem().toString().equals("No Choose")){
                string1 = "";
            }else{
                string1 = " genres.name = '" + moviesByGenre.getSelectedItem().toString() + "' ";
            }
            if(moviesByActor.getSelectedItem().toString().equals("No Choose")){
                string2 = "";
            }else{
                string2 = " a2.last_name = '" + moviesByActor.getSelectedItem().toString().substring(0, j) + "' AND a2.first_name = '"
                        + moviesByActor.getSelectedItem().toString().substring(j + 1, moviesByActor.getSelectedItem().toString().length()) + "' ";
            }
            String string3 = "";
            if(string.equals("") && string1.equals("") && string2.equals("")){
                string3 = " where movies.status = true ";
            }else if(string.equals("") && string1.equals("")){
                string3 = " WHERE " + string2 + " AND movies.status = true ";
            }else if(string2.equals("") && string1.equals("")){
                string3 = " WHERE " + string  + " AND movies.status = true ";
            }else if(string.equals("") && string2.equals("")){
                string3 = " WHERE " + string1  + " AND movies.status = true ";
            }else if(string.equals("")) {
                string3 = " WHERE " + string1 + " AND " + string2  + " AND movies.status = true ";
            }else if(string1.equals("")) {
                string3 = " WHERE " + string + " AND " + string2  + " AND movies.status = true ";
            }else if(string2.equals("")) {
                string3 = " WHERE " + string + " AND " + string1  + " AND movies.status = true ";
            }else {
                string3 = " WHERE " + string + " AND " + string1 + " AND " + string2  + " AND movies.status = true ";
            }
            ResultSet resultSet = staticStatement.executeQuery("select distinct movies.name , movies.movieid , count(*), movies.description , movies.production_year , movies.duration" +
                    " ,movies.premiere_date, movies.end_showing, movies.budget, movies.age_restrictions, movies.image " +
                    " from movies inner join genres_movies movie on movies.movieid = movie.movieid" +
                    " inner join genres on movie.genreid = genres.genreid" +
                    " inner join directing_movies m on movies.movieid = m.movieid" +
                    " inner join  directors d on m.directorid = d.directorid" +
                    " inner join  acting_movies a on movies.movieid = a.movieid" +
                    " inner join actors a2 on a.actorid = a2.actorid " + string3 +
                    " GROUP BY movies.name , movies.movieid , movies.description , movies.production_year , movies.duration " +
                    " ,movies.premiere_date, movies.end_showing, movies.budget, movies.age_restrictions, movies.image " +
                    "ORDER By movies.name;");

            while (resultSet.next()){
                comboBox1.addItem(resultSet.getString(1));
            }

    } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setBoxes() {
        setGetnres();
        setDirectors();
        setActors();
        setFilm();
    }


    private String getGenre(){
        String string = "";
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select genres.name" +
                    " from movies inner join genres_movies movie on movies.movieid = movie.movieid" +
                    " inner join genres on movie.genreid = genres.genreid" +
                    " where movies.name = '" + vetorOfFilm.get(inFilm).getName() + "'");

            while (resultSet.next()) string += resultSet.getString(1) + ' ';

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return string;
    }

    public void setFirstFrame(int i){

        actorsBox.removeAllItems();
        directorsBox.removeAllItems();
        dateChoose.removeAllItems();
        TimeChoose.removeAllItems();
        //comboBox1.setSelectedIndex(i);
        if(comboBox1.getSelectedIndex() != -1) {
            imageIcon = new ImageIcon(getClass().getResource(vetorOfFilm.get(i).getImage()));
            //image.setIcon(imageIcon);
            image.setBounds(0, 0, 500, 600);
            img = imageIcon.getImage();
            NewImage = img.getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_SMOOTH);
            ime = new ImageIcon(NewImage);
            image.setIcon(ime);

            nameOfFilm.setText(vetorOfFilm.get(i).getName());
            descriptionText.setText(vetorOfFilm.get(i).getDescription() + "\n\nProdaction Year: " + vetorOfFilm.get(i).get_prodactYear() +
                    "\n\nDuration:" + vetorOfFilm.get(i).get_duration() + "\n\nPremeiera Date: " + vetorOfFilm.get(i).get_start() +
                    "\n\nAge restrictions: " + vetorOfFilm.get(i).get_age() +
                    "\n\nBudget: " + vetorOfFilm.get(i).get_budget() + "$" +
                    "\n\nGenre: " + getGenre());
            int actornum = getCountOfActor();
            for (int j = 0; j < actornum; ++j) {
                actorsBox.addItem(getLastNameOfActor(j) + ' ' + getFirstNameOfActor(j));
            }
            int directornum = getCountOfDirector();
            for (int j = 0; j < directornum; ++j) {
                directorsBox.addItem(getLastNameOfDirector(j) + ' ' + getFirstNameOfDirector(j));
            }
            setImageIconForActors(actorsBox.getSelectedIndex());
            setImageIconForDirectors(directorsBox.getSelectedIndex());
            actorsBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (actorsBox.getItemCount() != 0) setImageIconForActors(actorsBox.getSelectedIndex());
                }
            });
            directorsBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (directorsBox.getItemCount() != 0) setImageIconForDirectors(directorsBox.getSelectedIndex());
                }
            });
            getDateSesion();
            getTimeOfSesionAtChoseDay();
            dateChoose.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (dateChoose.getItemCount() != 0) getTimeOfSesionAtChoseDay();
                }
            });
        }
    }

    private void getDateSesion() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(); // your date
        String strDate = dateFormat.format(date).substring(0, 10);
        String time = dateFormat.format(date).substring(11, 16);
        dateChoose.removeAllItems();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select distinct date from session " +
                    " where movieid = " + vetorOfFilm.get(inFilm).get_id() + " and status = true and date > '" + strDate + "' order by date");

            while(resultSet.next()){
                dateChoose.addItem(resultSet.getString(1));
            }
            getTimeOfSesionAtChoseDay();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getTimeOfSesionAtChoseDay() {
        TimeChoose.removeAllItems();
        if(dateChoose.getItemCount() != 0) {
            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select distinct session_begin from session " +
                        "where movieid = " + vetorOfFilm.get(inFilm).get_id() + " AND date = '" + dateChoose.getSelectedItem().toString() + "' and status = true  order by session_begin ");
                while (resultSet.next()) {
                    TimeChoose.addItem(resultSet.getString(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setImageIconForActors(int i){
        ImageIcon imageIcon1 = new ImageIcon(getClass().getResource(getStrimageForActor(i)));
        actorIcon.setBounds(actorIcon.getX(), actorIcon.getY(), 100 , 150);
        Image img1 = imageIcon1.getImage();
        Image NewImage1 = img1.getScaledInstance(actorIcon.getWidth(), actorIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon ime1 = new ImageIcon(NewImage1);
        actorIcon.setIcon(ime1);
    }

    public void setImageIconForDirectors(int i){
       // System.out.println("Im here");
        ImageIcon imageIcon1 = new ImageIcon(getClass().getResource(getStrimageForDirector(i)));
        directorIcon.setBounds(directorIcon.getX(), directorIcon.getY(), 100 , 150);
        Image img1 = imageIcon1.getImage();
        Image NewImage1 = img1.getScaledInstance(directorIcon.getWidth(), directorIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon ime1 = new ImageIcon(NewImage1);
        directorIcon.setIcon(ime1);
    }

    private String getStrimageForActor(int index){
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select image from actors " +
                    "where actorid = " + vetorOfFilm.get(inFilm).get_actor()[index]);
            if(resultSet.next()){
                //System.out.println(resultSet.getString(1));
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getStrimageForDirector(int index){
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select image from directors " +
                    "where  directorid = " + vetorOfFilm.get(inFilm).get_director()[index]);
            if(resultSet.next()){
               // System.out.println(resultSet.getString(1));
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getFirstNameOfActor(int index){
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select first_name from actors " +
                    "where actorid = " + vetorOfFilm.get(inFilm).get_actor()[index]);
            if(resultSet.next()){
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getLastNameOfActor(int index){
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select last_name from actors " +
                    "where actorid = " + vetorOfFilm.get(inFilm).get_actor()[index]);
            if(resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getFirstNameOfDirector(int index){
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select first_name from directors " +
                    "where directorid = " + vetorOfFilm.get(inFilm).get_director()[index]);
            if(resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getLastNameOfDirector(int index){
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select last_name from directors " +
                    "where directorid = " + vetorOfFilm.get(inFilm).get_director()[index]);
            if(resultSet.next()) {
                return resultSet.getString(1);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private int getCountOfActor(){
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select count (*) from acting_movies " +
                    "where movieid = " + vetorOfFilm.get(inFilm).get_id());
            if(resultSet.next())
                return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getCountOfDirector(){
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select  count (*) from  directing_movies " +
                    "where movieid = " + vetorOfFilm.get(inFilm).get_id());
            if(resultSet.next())
                return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setFilmsToVector() {
        try {
            statement = connection.createStatement();
            Statement statement1 = connection.createStatement();
            Statement statement2 = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from  movies where status = true ");

            int id = 0;
            Film asd = null;
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
                String str = "";
                while (resultSet3.next()){
                    arrayListDirector[i] = resultSet3.getInt(1);
                    i++;
                }
                Statement statement3 = connection.createStatement();
                ResultSet resultSet4 = statement3.executeQuery("select name from genres where genreid IN (SELECT genreid from genres_movies where movieid = " + id + ")");
                if(resultSet3.next())str = resultSet4.getString(1);
                asd = new Film(id , resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4),
                        resultSet.getInt(5), resultSet.getDate(6),
                        resultSet.getDate(7), resultSet.getString(8),
                        resultSet.getInt(9), arrayListActor ,arrayListDirector, resultSet.getString(10), str);
                //System.out.println(asd);
                vetorOfFilm.add(asd);

            }
            //System.out.println(vetorOfFilm);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setValueToAccount() {
        firstNameField.setText(Main.user.getFirst_name());
        lastNameField.setText(Main.user.getLast_name());
        phoneField.setText(Main.user.getPhone_number());
        firstNameField.setEditable(true);
        lastNameField.setEditable(true);
        phoneField.setEditable(true);
    }

    public void showMainFrameWindow(){
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("Cinema");

    }

    private void initListeners() {
        infoFilmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InfoFilmController();
            }
        });
        genresPopularityInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminPanelInfoController(4);
            }
        });
        directorsPopularityInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminPanelInfoController(3);
            }
        });
        actorTicketsInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminPanelInfoController(2);
            }
        });
        resetOldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                phoneField.setText(Main.user.getPhone_number());
                firstNameField.setText(Main.user.getFirst_name());
                lastNameField.setText(Main.user.getLast_name());
            }
        });
        logInButton.addActionListener(new LogInBtnListener());
        logOutButton.addActionListener(new logOutButtonListener());
        saveChangeButton.addActionListener(new SaveChangeButtonLister());
        buyTicketsButton.addActionListener(new BuyTicketsButtonListener());

        ticketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Main.user != null) {
                    TicketsController ticketsController = new TicketsController();
                    ticketsController.setJtable();
                }
            }
        });
        moviesByGenre.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                if(moviesByGenre.getItemCount() != -1){
                    setFilm();
                }
            }
        });
        moviesByActor.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                if(moviesByActor.getItemCount() != -1){
                    setFilm();
                }
            }
        });
        moviesByDirectors.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                if(moviesByDirectors.getItemCount() != -1){
                    setFilm();
                }
            }
        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboBox1.getItemCount() != 0){
                    inFilm = find();

                    setFirstFrame(inFilm);
                }
            }
        });
    }

    private static  int find(){
        for(int i = 0; i < vetorOfFilm.size(); ++i){
            if(comboBox1.getSelectedItem().toString().equals(vetorOfFilm.get(i).getName())) return i;
        }
        return -1;
    }

    private void initComponents(){
        this.mainFrame = new MainFrame();
        logInButton = mainFrame.getLogInButton();
        logOutButton = mainFrame.getLogOutButton();
        firstNameField = mainFrame.getFirstNameField();
        lastNameField = mainFrame.getLastNameField();
        phoneField = mainFrame.getPhoneField();
        firstNameField.setEditable(false);
        lastNameField.setEditable(false);
        phoneField.setEditable(false);
        saveChangeButton = mainFrame.getSaveChangeButton();
        tabbedPane1 = mainFrame.getTabbedPane1();
        films = mainFrame.getFilms();
        account = mainFrame.getAccount();
        admin = mainFrame.getAdmin();
        image = mainFrame.getImage();
        dateChoose = mainFrame.getDateChoose();
        TimeChoose = mainFrame.getTimeChoose();
        buyTicketsButton = mainFrame.getBuyTicketsButton();
        descriptionText = mainFrame.getDescriptionText();
        actorsBox = mainFrame.getActorsBox();
        directorsBox = mainFrame.getDirectorsBox();
        actorIcon = mainFrame.getActorIcon();
        nameOfFilm = mainFrame.getNameOfFilm();
        directorIcon = mainFrame.getDirectorIcon();
        tabbedPane1.add(films, 0);
        tabbedPane1.add(account, 1);
        tabbedPane1.add(admin, 2);
        tabbedPane1.setTitleAt(0, "Films");
        tabbedPane1.setTitleAt(1, "Account");
        tabbedPane1.setTitleAt(2, "Admin menu");
        tabbedPane1.setSelectedIndex(0);
        descriptionText.setWrapStyleWord(true);
        descriptionText.setLineWrap(true);

        actorTicketsInfoButton = mainFrame.getActorTicketsInfoButton();
        directorsPopularityInfoButton = mainFrame.getDirectorsPopularityInfoButton();
        genresPopularityInfoButton = mainFrame.getGenresPopularityInfoButton();
        infoFilmButton = mainFrame.getInfoFilmButton();

        ticketsButton = mainFrame.getTicketsButton();
        moviesByGenre = mainFrame.getMoviesByGenre();
        moviesByActor = mainFrame.getMoviesByActor();
        moviesByDirectors = mainFrame.getMoviesByDirectors();
        changeSessionButton = mainFrame.getChangeSessionButton();
        changeFilmButton = mainFrame.getChangeFilmButton();
        CashierButton = mainFrame.getCashierButton();
        comboBox1 = mainFrame.getComboBox1();
        resetOldButton = mainFrame.getResetOldButton();

        setBoxes();
    }
    private class LogInBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            LoginWindowController loginWindowController = new LoginWindowController();
            loginWindowController.showLogInWiundow();
        }
    }


    private class logOutButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (Main.user.isAdmin()){
                tabbedPane1.remove(2);
            }
            Main.user = null;
            logInButton.setVisible(true);
            logOutButton.setVisible(false);
            lastNameField.setText("");
            firstNameField.setText("");
            phoneField.setText("");
            lastNameField.setEditable(false);
            firstNameField.setEditable(false);
            phoneField.setEditable(false);

            saveChangeButton.setEnabled(false);
            ticketsButton.setEnabled(false);
            resetOldButton.setEnabled(false);
        }
    }

    public void updateInt(String table, String what, String uprate_to, String where){
        try {
            statement = connection.createStatement();
            statement.executeUpdate("UPDATE "  + table + " SET " + what + " = " + uprate_to + " WHERE " + where);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private class SaveChangeButtonLister implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(Main.user != null ){
                int reply = JOptionPane.showConfirmDialog(null, "Do you want to update your private information?",
                        "Accept", JOptionPane.YES_NO_OPTION);
                if(reply == 0) {
                    String first_name = firstNameField.getText();
                    String last_name = lastNameField.getText();
                    String phone = phoneField.getText();
                    updateStr("client", "first_name", first_name, "telephone_num = " + Main.user.getPhone_number());
                    updateStr("client", "last_name", last_name, "telephone_num = " + Main.user.getPhone_number());
                    updateInt("client", "telephone_num", phone, "telephone_num = " + Main.user.getPhone_number());
                    Main.user.setFirst_name(first_name);
                    Main.user.setLast_name(last_name);
                    Main.user.setPhone_number(phone);
                }
            }
        }
    }

    private void updateStr(String table, String what, String uprate_to, String where) {
        try {
            statement = connection.createStatement();
            statement.executeUpdate("UPDATE "  + table + " SET " + what + " = '" + uprate_to + "' WHERE " + where);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private int getHall(){
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select hallid from session " +
                    "where movieid = " + vetorOfFilm.get(inFilm).get_id() +
                    " AND session_begin = '" + TimeChoose.getSelectedItem().toString() +
                    "' AND date = '" + dateChoose.getSelectedItem().toString() + '\'');
            if(resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getSesionID(){
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select sessionid from session " +
                    "where movieid = " + vetorOfFilm.get(inFilm).get_id() +
                    " AND session_begin = '" + TimeChoose.getSelectedItem().toString() +
                    "' AND date = '" + dateChoose.getSelectedItem().toString() + '\'');
            if(resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    private class BuyTicketsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (getHall()){
                case 1:
                    HallController hallController = new HallController(14, 11, getSesionID());
                    hallController.setTitle("Hall 1");
                    hallController.rowSeatIsBooked();
                    hallController.show();
                    break;
                case 2:
                    HallController hallController1 = new HallController(14, 10, getSesionID());
                    hallController1.setTitle("Hall 2");
                    hallController1.rowSeatIsBooked();
                    hallController1.show();
                    break;
                case 3:
                    HallController hallController2 = new HallController(14, 8, getSesionID());
                    hallController2.setTitle("Hall 3");
                    hallController2.rowSeatIsBooked();
                    hallController2.show();
                    break;
                default:
                    showMessageDialog(null, "Something was wrong");
            }
        }
    }

    private static Statement staticStatement;
    private static int getCountOfFilm(){
        try {
            staticStatement = connection.createStatement();
            ResultSet resultSet = staticStatement.executeQuery("select count (*) from movies");
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }



    public static void adminMenu(){

    }




}
