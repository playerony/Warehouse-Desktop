/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.warehouse.impl;

import com.warehouse.dao.UserDao;
import com.warehouse.entity.User;
import com.warehouse.service.UserService;
import java.net.ConnectException;
import org.hibernate.SessionFactory;
import org.hibernate.exception.GenericJDBCException;

/**
 *
 * @author pawel_000
 */
public class UserDaoImpl implements UserDao{
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @Override
    public boolean find(String name, String password) throws GenericJDBCException{
        for(User u : UserService.list(sessionFactory))
            if(u.getLogin().equals(name) && u.getPassword().equals(password))
                return true;
        
        return false;
    }
    
    @Override
    public String getUserNameById(int id) {
        for(User u : UserService.list(sessionFactory))
            if(u.getId() == id)
                return u.getLogin();
        
        return null;
    }
}
