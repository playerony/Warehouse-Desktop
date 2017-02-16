/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.warehouse.impl;

import com.warehouse.dao.UserDao;
import com.warehouse.entity.User;
import com.warehouse.service.UserService;
import java.io.IOException;
import org.hibernate.SessionFactory;

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
    public boolean find(String name, String password){
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

    @Override
    public String getUserRank(String login, String password) {
        for(User u : UserService.list(sessionFactory))
            if(u.getLogin().equals(login) && u.getPassword().equals(password))
                return u.getRank();
        
        return null;
    }

    @Override
    public void addUser(User user) throws IOException{
        UserService.insert(user, sessionFactory);
    }

    @Override
    public boolean deleteUser(User user) throws IOException {
        for(User u : UserService.list(sessionFactory))
            if(u.getLogin().equals(user.getLogin()) && u.getPassword().equals(user.getPassword()) &&
                u.getFirstname().equals(user.getFirstname()) && u.getLastname().equals(user.getLastname()))
            {
                    UserService.delete(u, sessionFactory);
                    return true;
            }
        
        return false;
    }
}
