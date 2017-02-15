/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.warehouse.impl;

import com.warehouse.dao.ItemDao;
import com.warehouse.entity.Item;
import com.warehouse.service.ItemService;
import org.hibernate.SessionFactory;

/**
 *
 * @author pawel_000
 */
public class ItemDaoImpl implements ItemDao{
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @Override
    public Item getItemById(int id){
        for(Item i : ItemService.list(sessionFactory))
            if(i.getId() == id)
                return i;
        
        return null;
    }
}
