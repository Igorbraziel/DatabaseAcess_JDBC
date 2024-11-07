package ApplicationExamples;

import db.*;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DataTransactions {
    public static void main(String[] args){
        Connection connection = null;
        Statement statement = null;


        try {
            connection = DB.getConnection();

            connection.setAutoCommit(false);

            statement  = connection.createStatement();

            int rows1 = statement.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");

//            int x = 1;
//            if(x < 2){
//                throw new SQLException("Fake Error");
//            }

            int rows2 = statement.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2");

            connection.commit();

            System.out.println("rows1 = " + rows1);
            System.out.println("rows2 = " + rows2);

        } catch (SQLException e){
            try {
                connection.rollback();
                throw new DbException(e.getMessage());
            } catch(SQLException e1){
                throw new DbException(e1.getMessage());
            }
        } finally {
            DB.closeStatement(statement);
            DB.closeConnection();
        }
    }

}
