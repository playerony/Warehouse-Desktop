/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.warehouse.utility;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author pawel_000
 */
public class AlertBox {
    private static AlertBox alertBox = null;
    
    protected AlertBox(){
        
    }
    
    public static AlertBox getInstance() {
      if(alertBox == null) 
         alertBox = new AlertBox();
      return alertBox;
    }
    
    // Create Alert Box function
    public void display(String title, String message){
        Stage window = new Stage();
        Label label = new Label(message);
        
        Button closeButton = new Button("OK");
        closeButton.setOnAction(e -> window.close());
        
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);
        
        initWindow(window, new Scene(layout), title);
    }
    
    // Initialize new window
    private void initWindow(Stage window, Scene scene, String title){
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(70);
        
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();
    }
}
