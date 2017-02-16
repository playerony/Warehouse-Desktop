/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.warehouse.service;

import com.warehouse.entity.User;
import java.io.IOException;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.GenericJDBCException;

/**
 *
 * @author pawel_000
 */
public class UserService {
    public static void insert(User user, SessionFactory sessionFactory) throws IOException{
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(user);
        
        transaction.commit();
        session.close();
    }
    
    public static void delete(User user, SessionFactory sessionFactory) throws IOException{
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(user);
        
        transaction.commit();
        session.close();
    }
    
    public static List<User> list(SessionFactory sessionFactory)  throws GenericJDBCException{
        Session session = sessionFactory.openSession();
        List<User> list = session.createQuery("from User").list();
        session.close();
        
        return list;
    }
}
