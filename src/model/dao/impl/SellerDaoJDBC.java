package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;

import model.entities.Seller;
import model.entities.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class SellerDaoJDBC implements SellerDao {
    private Connection conn;

    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj){
        PreparedStatement preparedStatement = null;

        try {
            String sqlQuery = "INSERT INTO seller " +
                    "(Name, Email, BirthDate, BaseSalary, DepartmentId) " +
                    "VALUES (?, ?, ?, ?, ?)";

            preparedStatement = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, obj.getName());
            preparedStatement.setString(2, obj.getEmail());
            preparedStatement.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            preparedStatement.setDouble(4, obj.getBaseSalary());
            preparedStatement.setInt(5, obj.getDepartment().getId());

            int rowsAffected = preparedStatement.executeUpdate();

            if(rowsAffected > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next()){
                    int id = resultSet.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(resultSet);
            } else {
                throw new DbException("Unexpected error, no rows affected");
            }

        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
        }
    }

    @Override
    public void update(Seller obj){

    }

    @Override
    public void deleteById(Integer id){

    }

    @Override
    public Seller findById(Integer id){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String sqlQuery = "SELECT seller.*,department.Name as DepName " +
                    "FROM seller INNER JOIN department ON seller.DepartmentId = department.Id " +
                    "WHERE seller.Id = ?";

            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Department department = instantiateDepartment(resultSet);
                return instantiateSeller(resultSet, department);
            }
            return null;

        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            DB.closeResultSet(resultSet);
            DB.closeStatement(preparedStatement);
        }

        return null;
    }

    @Override
    public List<Seller> findByDepartment(Department department){
        List<Seller> sellersList = new ArrayList<>();
        PreparedStatement preparedStatement= null;
        ResultSet resultSet = null;

        try {
            String sqlQuery = "SELECT seller.*,department.Name AS DepName " +
                    "FROM seller INNER JOIN department ON " +
                    "seller.DepartmentId = department.Id " +
                    "WHERE department.Id = ? " +
                    "ORDER BY seller.Name ASC";
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, department.getId());

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                sellersList.add(instantiateSeller(resultSet, department));
            }

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
        return sellersList;
    }

    @Override
    public List<Seller> findAll(){
        List<Seller> sellersList = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = conn.createStatement();
            String sqlQuery = "SELECT seller.*,department.Name AS DepName " +
                                "FROM seller INNER JOIN department ON " +
                                "seller.DepartmentId = department.Id " +
                                "ORDER BY seller.Name ASC";
            resultSet = statement.executeQuery(sqlQuery);

            Map<Integer, Department> departmentMap = new HashMap<>();

            while(resultSet.next()){
                Integer departmentId = resultSet.getInt("DepartmentId");
                Department department = departmentMap.get(departmentId);

                if(department == null){
                    department = instantiateDepartment(resultSet);
                    departmentMap.put(department.getId(), department);
                }

                sellersList.add(instantiateSeller(resultSet, department));
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }

        return sellersList;
    }

    public Department instantiateDepartment(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getInt("DepartmentId"));
        department.setName(resultSet.getString("DepName"));
        return department;
    }

    public Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException {
        Seller seller = new Seller();
        seller.setId(resultSet.getInt("Id"));
        seller.setName(resultSet.getString("Name"));
        seller.setEmail(resultSet.getString("Email"));
        seller.setBirthDate(resultSet.getDate("BirthDate"));
        seller.setBaseSalary(resultSet.getDouble("BaseSalary"));
        seller.setDepartment(department);
        return seller;
    }
}
