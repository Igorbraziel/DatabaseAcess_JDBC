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
    private Connection conn;

    public DepartmentDaoJDBC(Connection conn){
        this.conn = conn;
    }

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
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String sqlQuery = "SELECT department.Name FROM department " +
                                "WHERE Id = ?";
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                String departmentName = resultSet.getString("Name");
                if(departmentName != null){
                    return new Department(id, departmentName);
                }
            }
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
        return null;
    }

    @Override
    public List<Department> findAll(){
        return null;
    }
}
