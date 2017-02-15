/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.warehouse.controller.worker;

import com.warehouse.abstractController.OrderMenuAbstractController;
import com.warehouse.abstractController.OrderMenuInterface;
import com.warehouse.cookie.Cookie;
import com.warehouse.dao.ItemDao;
import com.warehouse.dao.PackingDao;
import com.warehouse.entity.PalleteInfo;
import com.warehouse.entity.PalletsPacked;
import com.warehouse.impl.ItemDaoImpl;
import com.warehouse.impl.PackingDaoImpl;
import com.warehouse.informations.PackingInformations;
import com.warehouse.loader.LoadFXML;
import com.warehouse.utility.AlertBox;
import com.warehouse.utility.Validate;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author pawel_000
 */
public class PackingMenuController extends OrderMenuAbstractController implements Initializable, OrderMenuInterface{
    
    @FXML
    public TableView<PackingInformations> tableView;
    @FXML
    public TableColumn<PackingInformations, String> id;
    @FXML
    public TableColumn<PackingInformations, String> workerLogin;
    @FXML
    public TableColumn<PackingInformations, String> type;
    @FXML
    public TableColumn<PackingInformations, String> itemCode;
    @FXML
    public TableColumn<PackingInformations, String> itemAmount;
    @FXML
    public TableColumn<PackingInformations, String> clientName;
    @FXML
    public TableColumn<PackingInformations, String> clientAddress;
    @FXML
    public TableColumn<PackingInformations, String> whenOrder;
    
    private ItemDao itemDao;
    private PackingDao packingDao;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initInstances();
        initTableView();
    }
    
    @Override
    public void initInstances() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        itemDao = context.getBean(ItemDaoImpl.class);
        packingDao = context.getBean(PackingDaoImpl.class);
    }

    @Override
    public void initTableView() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        workerLogin.setCellValueFactory(new PropertyValueFactory<>("workerLogin"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        itemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        itemAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        clientName.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        clientAddress.setCellValueFactory(new PropertyValueFactory<>("clientAddress"));
        whenOrder.setCellValueFactory(new PropertyValueFactory<>("whenOrder"));

        tableView.setItems(getObservableList());
    }
    
    @Override
    public ObservableList<PackingInformations> getObservableList() {
        ObservableList<PackingInformations> result = FXCollections.observableArrayList();

        try {
            List<PalletsPacked>palletsPacked = packingDao.getPackedPallets();
            
            for (PalletsPacked p : palletsPacked) {
                List<PalleteInfo>palleteInfo = Validate.getPalleteInformations(p.getProducts());
                
                for (PalleteInfo pa : palleteInfo) {
                    PackingInformations cm = new PackingInformations();
                    cm.setId(p.getId());
                    cm.setType(p.getType());
                    cm.setClientName(p.getClient().getName());
                    cm.setClientAddress(p.getClient().getAddress());
                    cm.setItemCode(itemDao.getItemById(p.getId()).getCode());
                    cm.setAmount(pa.getAmount());
                    cm.setWorkerLogin(p.getUser().getLogin());
                    cm.setWhenOrder(p.getDate().toString());
                    
                    result.add(cm);
                }
            }
            
            return result;
        }catch(GenericJDBCException c){
            AlertBox.getInstance().display(getClass().getSimpleName(), "Connection problem");
            c.printStackTrace();
            System.exit(0);
        }catch (IOException e) {
            AlertBox.getInstance().display(getClass().getSimpleName(), "Some problems with getting info from db");
            e.printStackTrace();
            System.exit(0);
        }

        return result;
    }
    
    @Override
    public void handleBackMenuItem() {
        try {
            Stage stage = (Stage) tableView.getScene().getWindow();
            stage.close();
            
            LoadFXML.getInstance().loadFile("checkOrderNumber");
        } catch (IOException e) {
            AlertBox.getInstance().display(getClass().getSimpleName(), "Some problems by loading checkOrderNumber.fxml");
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void handleLogoutMenuItem() {
        try {
            Stage stage = (Stage) tableView.getScene().getWindow();
            stage.close();
            Cookie.getInstance().clear();
            
            stage.setScene(new Scene(FXMLLoader.load(LoadFXML.getInstance().getPath("loginPanel"))));
        } catch (IOException e) {
            AlertBox.getInstance().display(getClass().getSimpleName(), "Some problems by loading loginPanel.fxml");
            e.printStackTrace();
            System.exit(0);
        }
    }
    
    public void handleShowOrderAction() {
        try {
            Stage stage = (Stage) tableView.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(LoadFXML.getInstance().getPath("checkOrder-" + Cookie.getInstance().get("rank")))));
        } catch (IOException e) {
            AlertBox.getInstance().display(getClass().getSimpleName(), "Some problems by loading checkOrder.fxml");
            e.printStackTrace();
            System.exit(0);
        }
    }
    
    public void handleShowPickingAction() {
        try {
            Stage stage = (Stage) tableView.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(LoadFXML.getInstance().getPath("pickingMenu-" + Cookie.getInstance().get("rank")))));
        } catch (IOException e) {
            AlertBox.getInstance().display(getClass().getSimpleName(), "Some problems by loading pickingMenu.fxml");
            e.printStackTrace();
            System.exit(0);
        }
    }
}
