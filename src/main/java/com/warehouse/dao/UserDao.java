/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.warehouse.dao;

import java.net.ConnectException;
import org.hibernate.exception.GenericJDBCException;

/**
 *
 * @author pawel_000
 */
public interface UserDao {
    public boolean find(String name, String password) throws GenericJDBCException;
    
    public String getUserNameById(int id) ;
}
