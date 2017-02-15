package com.warehouse.controller;

import com.warehouse.dao.UserDao;
import com.warehouse.impl.UserDaoImpl;
import com.warehouse.loader.LoadFXML;
import com.warehouse.utility.AlertBox;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LoginPanelController implements Initializable{
    public TextField loginField;
    public TextField passwordField;
    
    private UserDao userDao;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        userDao = context.getBean(UserDaoImpl.class);
    }
    
    public void handleButtonClick() {
        try{
            if(userDao.find(loginField.getText(), passwordField.getText())){
                Stage stage = (Stage) loginField.getScene().getWindow();
                stage.close();

                LoadFXML.getInstance().loadFile("checkOrderNumber");
            }else
                AlertBox.getInstance().display(getClass().getSimpleName(), "Wrong login or password");
        }catch(GenericJDBCException c){
            AlertBox.getInstance().display(getClass().getSimpleName(), "Connection problem");
            c.printStackTrace();
            System.exit(0);
        }catch(IOException e){
            AlertBox.getInstance().display(getClass().getSimpleName(), "Some problems by loading checkOrderNumber.fxml");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
