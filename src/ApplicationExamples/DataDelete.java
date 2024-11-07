package ApplicationExamples;

import db.DB;
import db.DbIntegrityException;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DataDelete {
    public static void main(String[] args){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DB.getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM department " +
                                                            "WHERE Id = ?");
            preparedStatement.setInt(1, 5);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Updated rows: " + rowsAffected);
        } catch (SQLException e){
            throw new DbIntegrityException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeConnection();
        }
    }
}
