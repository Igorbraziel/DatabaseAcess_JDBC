package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import java.util.Properties;

import java.io.IOException;
import java.io.FileInputStream;

public class DB {
    private static Connection conn = null;

    public static Connection getConnection(){
        try {
            if(conn == null){
                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                conn = DriverManager.getConnection(url, props);
            }
            return conn;
        } catch(SQLException sqlException){
            throw new DbException(sqlException.getMessage());
        }
    }

    public static void closeConnection(){
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException sqlException){
            throw new DbException(sqlException.getMessage());
        }
    }

    public static void closeStatement(Statement statement){
        if(statement != null){
            try {
                statement.close();
            } catch (SQLException e){
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet){
        if(resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e){
                throw new DbException(e.getMessage());
            }
        }
    }

    private static Properties loadProperties(){
        try(FileInputStream fileInputStream = new FileInputStream("db.properties")){
            Properties props = new Properties();
            props.load(fileInputStream);
            return props;
        } catch (IOException e){
            throw new DbException(e.getMessage());
        }
    }
}
