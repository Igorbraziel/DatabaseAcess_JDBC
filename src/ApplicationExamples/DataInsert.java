package ApplicationExamples;

import db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.text.SimpleDateFormat;

public class DataInsert {
    public static void main(String[] args){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            connection = DB.getConnection();
//            preparedStatement = connection.prepareStatement("INSERT INTO seller " +
//                                                                "(Name, Email, BirthDate, BaseSalary, DepartmentId) " +
//                                                                "VALUES " +
//                                                                "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
//            preparedStatement.setString(1, "Carl Purple");
//            preparedStatement.setString(2, "carl@gmail.com");
//            preparedStatement.setDate(3, new Date(simpleDateFormat.parse("22/10/1985").getTime()));
//            preparedStatement.setDouble(4, 3000.0);
//            preparedStatement.setInt(5, 4);

            preparedStatement = connection.prepareStatement("INSERT INTO department " +
                                                            "(Name) VALUES (?), (?)");

            preparedStatement.setString(1, "D1");
            preparedStatement.setString(2, "D2");

            int rowsAffected = preparedStatement.executeUpdate();

//            if(rowsAffected > 0){
//                ResultSet resultSet = preparedStatement.getGeneratedKeys();
//                while(resultSet.next()){
//                    int id = resultSet.getInt(1);
//                    System.out.println("Id: " + id);
//                }
//            } else {
//                System.out.println("No rows affected");
//            }

            System.out.println("Done! rows affected: " + rowsAffected);

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeConnection();
        }
    }
}
