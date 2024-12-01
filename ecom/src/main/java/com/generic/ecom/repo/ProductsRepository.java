package com.generic.ecom.repo;

import com.generic.ecom.model.Products;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public class ProductsRepository {

    private final SessionFactory sessionFactory;

    public ProductsRepository(){
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    // Save a product
    public Integer save(Products product) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(product);
        transaction.commit();
        session.close();
        return product.getProdId();
    }

    // Find all products
    public List<Products> findAll() {
        // This needs to be implemented catagory wise.
        Session session = sessionFactory.openSession();
        List<Products> productList = session.createQuery("from products", Products.class).list();
        session.close();
        return productList;
    }

    // Find product by ID
    public Products findById(Integer id) {
        Session session = sessionFactory.openSession();
        Products product = session.get(Products.class, id);
        session.close();
        return product;
    }

    // Update a product
    public Integer update(Products product) {
        //This update will make update only the given field and make others as null
        //need to handle this in UI by providing a form with existing values so that user will alter only the required and keep the other same
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(product);
        transaction.commit();
        session.close();
        return product.getProdId();
    }

    // Delete a product
    public void delete(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Products product = session.get(Products.class, id);
        if (product != null) {
            session.remove(product);
        }
        transaction.commit();
        session.close();
    }
}

