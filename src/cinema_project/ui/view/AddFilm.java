package cinema_project.ui.view;

import cinema_project.data_base.LimitedCharInput;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Date;

public class AddFilm extends JFrame {

    private JPanel filmPanel;
    private JTable FilmTableList;
    private JButton addFilmButton;
    private JButton deleteButton;
    private JTextField nameOfFilm;
    private JTextArea DescriptionArea;
    private JTextField duration;
    private JTextField budgets;
    private DefaultTableModel model;

    public DefaultTableModel getModel() {
        return model;
    }

    public JTextField getBudgets() {
        return budgets;
    }

    private JTextField ageRestictions;
    private JTextField imageChoose;
    private JScrollPane jscrollpane;
    private JComboBox actor;
    private JComboBox director;
    private JButton addActorsButton;

    public JComboBox getActor() {
        return actor;
    }

    public JComboBox getDirector() {
        return director;
    }

    public JButton getAddActorsButton() {
        return addActorsButton;
    }

    public JButton getAddDirectorsButton() {
        return addDirectorsButton;
    }

    private JButton addDirectorsButton;

    public JButton getClearAllButton() {
        return clearAllButton;
    }

    private JButton clearAllButton;

    private SpinnerDateModel model1;
    private SpinnerDateModel model2;

    public SpinnerDateModel getModel1() {
        return model1;
    }

    public SpinnerDateModel getModel2() {
        return model2;
    }

    public JButton getSelectButton() {
        return selectButton;
    }

    private JButton selectButton;

    public JSpinner getSpinner1() {
        return spinner1;
    }

    public JSpinner getSpinner2() {
        return spinner2;
    }

    public JComboBox getComboBox1() {
        return comboBox1;
    }

    private JComboBox comboBox1;
    private JSpinner spinner1;
    private JSpinner spinner2;

    public JSpinner getProdactYearSpiner() {
        return prodactYearSpiner;
    }

    public SpinnerDateModel getModel3() {
        return model3;
    }

    private JSpinner prodactYearSpiner;
    private SpinnerDateModel model3;

    public JPanel getFilmPanel() {
        return filmPanel;
    }

    public JTable getFilmTableList() {
        return FilmTableList;
    }


    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JTextField getNameOfFilm() {
        return nameOfFilm;
    }

    public JTextArea getDescriptionArea() {
        return DescriptionArea;
    }


    public JTextField getDuration() {
        return duration;
    }

    public JTextField getAgeRestictions() {
        return ageRestictions;
    }

    public JTextField getImageChoose() {
        return imageChoose;
    }

    public JButton getAddFilmButton() {
        return addFilmButton;
    }

    public JScrollPane getJscrollpane() {
        return jscrollpane;
    }

    public AddFilm(){

        String[] columnNames = {"Id", "Film", "Duration", "Prodact year", "Premiere date", "End showing", "Age", "Budget", "image"};
        Object[][] objects = {};
        model = new DefaultTableModel(objects, columnNames);
        FilmTableList.setModel(model);
        FilmTableList.setFillsViewportHeight(true);
        TableColumnModel colmodel = FilmTableList.getColumnModel();
        colmodel.getColumn(0).setPreferredWidth(100);
        colmodel.getColumn(1).setPreferredWidth(300);
        colmodel.getColumn(2).setPreferredWidth(200);
        colmodel.getColumn(3).setPreferredWidth(200);
        colmodel.getColumn(4).setPreferredWidth(200);
        colmodel.getColumn(5).setPreferredWidth(200);
        colmodel.getColumn(6).setPreferredWidth(100);
        colmodel.getColumn(7).setPreferredWidth(300);
        colmodel.getColumn(8).setPreferredWidth(300);

        model1 = new SpinnerDateModel(new Date(), null, null,
                Calendar.DAY_OF_MONTH);

        spinner1.setModel(model1);
        String format = "yyyy-MM-dd";
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner1, format);
        spinner1.setEditor(editor);


        model2 = new SpinnerDateModel(new Date(), null, null,
                Calendar.DAY_OF_MONTH);
        spinner2.setModel(model2);
        String format1 = "yyyy-MM-dd";
        JSpinner.DateEditor editor1 = new JSpinner.DateEditor(spinner2, format1);
        spinner2.setEditor(editor1);

        model3 = new SpinnerDateModel(new Date(), null, null,
                Calendar.DAY_OF_MONTH);
        prodactYearSpiner.setModel(model3);
        String format2 = "yyyy";
        JSpinner.DateEditor editor2 = new JSpinner.DateEditor(prodactYearSpiner, format2);
        prodactYearSpiner.setEditor(editor2);
        duration.setDocument(new LimitedCharInput(3));
        duration.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char vchar = e.getKeyChar();
                if(!(Character.isDigit(vchar)) || vchar == KeyEvent.VK_BACK_SPACE || vchar == KeyEvent.VK_DELETE){
                    e.consume();
                }
            }
        });
        budgets.setDocument(new LimitedCharInput(10));
        budgets.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char vchar = e.getKeyChar();
                if(!(Character.isDigit(vchar)) || vchar == KeyEvent.VK_BACK_SPACE || vchar == KeyEvent.VK_DELETE){
                    e.consume();
                }
            }
        });
        ageRestictions.setDocument(new LimitedCharInput(2));
        ageRestictions.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char vchar = e.getKeyChar();
                if(!(Character.isDigit(vchar)) || vchar == KeyEvent.VK_BACK_SPACE || vchar == KeyEvent.VK_DELETE){
                    e.consume();
                }
            }
        });
        pack();
        setSize(1200,700);
        setContentPane(filmPanel);
        setLocationRelativeTo(null);
        setTitle("Admin menu about films");
        setVisible(true);



    }
}
