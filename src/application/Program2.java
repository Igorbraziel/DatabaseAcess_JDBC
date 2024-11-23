package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;

import model.entities.Department;
import model.entities.Seller;

import java.util.List;
import java.util.Locale;

public class Program2 {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        System.out.println("==============TEST-findById==============");
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        Department department = departmentDao.findById(1);
        System.out.println(department);
    }
}
