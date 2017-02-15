/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.warehouse.controller;

import com.warehouse.cookie.Cookie;
import com.warehouse.dao.OrderDao;
import com.warehouse.impl.OrderDaoImpl;
import com.warehouse.loader.LoadFXML;
import com.warehouse.utility.AlertBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author pawel_000
 */
public class CheckOrderNumberController implements Initializable{
    @FXML
    public TextField orderField;
    @FXML
    public Label helloLabel;

    private OrderDao orderDao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initInstances();
        setTextInHelloLabel();
    }
    
    private void initInstances(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        orderDao = context.getBean(OrderDaoImpl.class);
    }

    @FXML
    public void handleButtonClick() {
        try{
            if(orderDao.checkOrderById(Integer.parseInt(orderField.getText())) && !Cookie.getInstance().get("rank").isEmpty()){
                Stage stage = (Stage) orderField.getScene().getWindow();
                stage.close();

                Cookie.getInstance().add("orderID", orderField.getText());
                LoadFXML.getInstance().loadFile("checkOrder-" + Cookie.getInstance().get("rank"));
            }else
                AlertBox.getInstance().display(getClass().getSimpleName(), "Wrong value");
        }catch(GenericJDBCException c){
            AlertBox.getInstance().display(getClass().getSimpleName(), "Connection problem");
            c.printStackTrace();
            System.exit(0);
        }catch(IOException e){
            AlertBox.getInstance().display(getClass().getSimpleName(), "Some problems by loading checkOrder.fxml");
            e.printStackTrace();
            System.exit(0);
        }
    }
    
    private void setTextInHelloLabel(){
        helloLabel.setText("Hello " + Cookie.getInstance().get("rank") + "!");
    }
}
