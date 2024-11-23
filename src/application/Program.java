package application;

import java.util.Locale;
import java.util.Date;

import model.entities.Department;
import model.entities.Seller;

import model.dao.SellerDao;
import model.dao.impl.SellerDaoJDBC;
import model.dao.DaoFactory;

import java.util.List;

public class Program {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        System.out.println("==============TEST-findById==============");
        SellerDao sellerDao = DaoFactory.createSellerDao();
        Seller seller = sellerDao.findById(2);
        System.out.println(seller);

        System.out.println("\n==============TEST-findByDepartment==============");
        Department department1 = new Department(2, "Eletronics");
        List<Seller> sellersList = sellerDao.findByDepartment(department1);
        sellersList.forEach(System.out::println);

        System.out.println("\n==============TEST-findAll==============");
        sellersList = sellerDao.findAll();
        sellersList.forEach(System.out::println);

        System.out.println("\n==============TEST-Delete==============");
        sellerDao.deleteById(10);
    }
}
