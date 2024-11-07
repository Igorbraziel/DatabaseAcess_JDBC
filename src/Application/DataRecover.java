package Application;

import db.DB;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataRecover {
    public static void main(String[] args){
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            conn = DB.getConnection();
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM department");

            while(resultSet.next()){
                System.out.println(resultSet.getInt("Id") + ", " + resultSet.getString("Name"));
            }
        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
            DB.closeConnection();
        }
    }
}
