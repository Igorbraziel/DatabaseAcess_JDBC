package model.dao.impl;

import model.dao.DepartmentDao;
import model.entities.Department;

import db.DB;
import db.DbException;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {
    @Override
    public void insert(Department obj){

    }

    @Override
    public void update(Department obj){

    }

    @Override
    public void deleteById(Integer id){

    }

    @Override
    public Department findById(Integer id){
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            conn = DB.getConnection();
            statement = conn.createStatement();
            String sqlQuery = String.format("SELECT * FROM department WHERE Id = %d", id);
            resultSet = statement.executeQuery(sqlQuery);

            String departmentName = resultSet.getString("Name");
            if(departmentName != null){
                return new Department(id, departmentName);
            }
            return null;
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Department> findAll(){
        return null;
    }
}
