/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.warehouse.controller.admin;

import com.warehouse.dao.UserDao;
import com.warehouse.entity.User;
import com.warehouse.impl.UserDaoImpl;
import com.warehouse.utility.AlertBox;
import com.warehouse.utility.Validate;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author pawel_000
 */
public class DeleteUserPanelController implements Initializable{
    @FXML
    public Button deleteUserButton;
    @FXML
    public Button closeButton;
    @FXML
    public TextField loginField;
    @FXML
    public TextField passwordField;
    @FXML
    public TextField firstnameField;
    @FXML
    public TextField lastnameField;
    
    private UserDao userDao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initInstances();
    }
    
    private void initInstances(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        userDao = context.getBean(UserDaoImpl.class);
    }
    
    @FXML
    public void handleDeleteUserButtonClick(){
        String login = loginField.getText();
        String password = passwordField.getText();
        String firstname = firstnameField.getText();
        String lastname = lastnameField.getText();
        
        try{
            if(login.isEmpty() && login.length() < 10)
                AlertBox.getInstance().display(getClass().getSimpleName(), "Wrong login!");
            else if(password.isEmpty() &&  password.length() < 10)
                AlertBox.getInstance().display(getClass().getSimpleName(), "Wrong password!");
            else if(firstname.isEmpty() && Validate.checkNumbersInString(firstname) && firstname.length() < 10)
                AlertBox.getInstance().display(getClass().getSimpleName(), "Wrong firstname!");
            else if(lastname.isEmpty() &&  Validate.checkNumbersInString(lastname) && lastname.length() < 10)
                AlertBox.getInstance().display(getClass().getSimpleName(), "Wrong lastname!");
            else{
                User user = new User();
                user.setId(0);
                user.setFirstname(firstname);
                user.setLastname(lastname);
                user.setLogin(login);
                user.setPassword(password);

                if(userDao.deleteUser(user)){
                    AlertBox.getInstance().display(getClass().getSimpleName(), "Successful delete user");
                    closeStage();
                }else
                    AlertBox.getInstance().display(getClass().getSimpleName(), "Some problems by deleting user 1");
            }
        }catch(IOException e){
            AlertBox.getInstance().display(getClass().getSimpleName(), "Some problems by deleting user 2");
            e.printStackTrace();
            closeStage();
        }
    }
    
    @FXML
    public void handleCloseButtonClick(){
        closeStage();
    }
    
    private void closeStage(){
        Stage stage = (Stage) deleteUserButton.getScene().getWindow();
        stage.close();
    }
}
