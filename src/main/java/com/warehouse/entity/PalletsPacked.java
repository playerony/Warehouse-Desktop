/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.warehouse.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author pawel_000
 */

@Entity
@Table(name="pallets_to_send")
public class PalletsPacked implements Serializable {
    
    private int id;
    private Date date;
    private String type;
    private String products;
    private User user;
    private Client client;
    
    public PalletsPacked(){
        
    }
    
    public PalletsPacked(int id, User user, Client client, String products){
        this.id = id;
        this.user = user;
        this.client = client;
        this.products = products;
    }
    
    public PalletsPacked(int id, User user, Client client, String products, Date date, String type){
        this.id = id;
        this.user = user;
        this.client = client;
        this.products = products;
        this.date = date;
        this.type = type;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }
    
    @Column(name="whenMaked")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    @OneToOne
    @JoinColumn(name = "worker_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToOne
    @JoinColumn(name = "client_id")
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
