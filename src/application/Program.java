package application;

import java.util.Locale;
import java.util.Date;

import model.entities.Department;
import model.entities.Seller;

import model.dao.SellerDao;
import model.dao.DaoFactory;

public class Program {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        SellerDao sellerDao = DaoFactory.createSellerDao();

        Seller seller = sellerDao.findById(2);

        System.out.println(seller);
    }
}
