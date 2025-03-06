package com.generic.ecom.repo;

import com.generic.ecom.model.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;


@Repository
public class UserRepo {
    private final SessionFactory sessionFactory;
    public UserRepo(){
        Configuration config = new Configuration();
        config.setProperty("hibernate.connection.url", System.getenv("DB_URL"));
        config.setProperty("hibernate.connection.username", System.getenv("DB_USERNAME"));
        config.setProperty("hibernate.connection.password", System.getenv("DB_PASSWORD"));
        this.sessionFactory = config.configure().buildSessionFactory();
    }

    // Save a new user to the database
    public void saveUser(Users user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(user); // Save the User object to the database
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback in case of an error
            }
            e.printStackTrace();
        }
        finally {
            session.close(); // Close the session
        }
    }

    // Get a user by their USERNAME
    public Users getUserByUserName(String userName) {
        // here we need to implement bloom filter before this
        Session session = sessionFactory.openSession();
        Users user = null;
        try {
            user = session.get(Users.class, userName); // Get the user by ID
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close(); // Close the session
        }

        return user;
    }

    // Close the SessionFactory when not needed (optional cleanup)
    public void close() {
        sessionFactory.close();
    }

}
