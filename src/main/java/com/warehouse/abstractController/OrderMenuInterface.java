/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.warehouse.abstractController;

import java.io.IOException;
import javafx.collections.ObservableList;

/**
 *
 * @author pawel_000
 */
public interface OrderMenuInterface {
    public void initInstances();
    
    public void initTableView();
    
    public ObservableList<?> getObservableList();
    
    public void handleBackMenuItem();
    
    public void handleLogoutMenuItem();
}
