package cinema_project.data_base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect_Data_Base {

    private final String HOST = "jdbc:postgresql://localhost:5432/Cinema";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "Kostya1999__";
    public static Connection connection = null;



    public Connect_Data_Base(){
        try {
            connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
            if(!connection.isClosed()){
                //System.out.println("Connected to db Cinema");
            }else{
                System.out.println("NOT Connected to db Cinema");
                return;
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
