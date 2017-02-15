/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.warehouse.loader;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author pawel_000
 */
public class LoadFXML {
    private static LoadFXML loadFXML = null;
    
    protected LoadFXML(){
        
    }
    
    public static synchronized LoadFXML getInstance(){
        if(loadFXML == null)
            loadFXML = new LoadFXML();
        
        return loadFXML;
    }
    
    public URL getPath(String fileName){
        return getClass().getResource("/fxml/" + fileName + ".fxml");
    }

    public void loadFile(final String fileName) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getPath(fileName));

        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
}
