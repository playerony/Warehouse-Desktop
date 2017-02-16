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
import com.warehouse.entity.Order;
import com.warehouse.entity.PalleteInfo;
import com.warehouse.impl.ItemDaoImpl;
import com.warehouse.impl.OrderDaoImpl;
import com.warehouse.informations.OrderInformations;
import com.warehouse.loader.LoadFXML;
import com.warehouse.utility.AlertBox;
import com.warehouse.utility.Validate;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
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
public class OrderStatusController extends OrderMenuAbstractController implements Initializable, OrderMenuInterface{
    @FXML
    public TableView<OrderInformations> tableView;
    @FXML
    public TableColumn<OrderInformations, String> orderID;
    @FXML
    public TableColumn<OrderInformations, String> itemName;
    @FXML
    public TableColumn<OrderInformations, String> itemCode;
    @FXML
    public TableColumn<OrderInformations, String> amount;
    @FXML
    public TableColumn<OrderInformations, String> clientName;
    @FXML
    public TableColumn<OrderInformations, String> clientAddress;
    @FXML
    public TableColumn<OrderInformations, String> whenOrder;
    
    private ItemDao itemDao;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
        initInstances();
        initTableView();
    }
    
    @Override
    public void initInstances() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        itemDao = context.getBean(ItemDaoImpl.class);
        orderDao = context.getBean(OrderDaoImpl.class);
    }
    
    @Override
    public void initTableView() {
        orderID.setCellValueFactory(new PropertyValueFactory<>("id"));
        itemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        clientName.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        clientAddress.setCellValueFactory(new PropertyValueFactory<>("clientAddress"));
        whenOrder.setCellValueFactory(new PropertyValueFactory<>("whenOrder"));

        tableView.setItems(getObservableList());
    }
    
    @Override
    public ObservableList<OrderInformations> getObservableList() {
        ObservableList<OrderInformations> result = FXCollections.observableArrayList();

        try {
            Order order = orderDao.getOrderById(Integer.parseInt(Cookie.getInstance().get("orderID")));
            List<PalleteInfo>palleteInfo = Validate.getPalleteInformations(order.getItems());
            
            if (order != null) {
                for (PalleteInfo p : palleteInfo) {
                    OrderInformations cm = new OrderInformations();
                    cm.setId(order.getId());
                    cm.setClientName(order.getClient().getName());
                    cm.setClientAddress(order.getClient().getAddress());
                    cm.setItemName(itemDao.getItemById(p.getId()).getName());
                    cm.setItemCode(itemDao.getItemById(p.getId()).getCode());
                    cm.setAmount(p.getAmount());
                    cm.setWhenOrder(Validate.parseDate(order.getDate().toString()));

                    result.add(cm);
                }
            }else
                throw new IOException("Order equals null");
            
            return result;
        }catch (ParseException p) {
            AlertBox.getInstance().display(getClass().getSimpleName(), "Problem by parsing date");
            p.printStackTrace();
            System.exit(0);
        }catch(GenericJDBCException c){
            AlertBox.getInstance().display(getClass().getSimpleName(), "Connection problem");
            c.printStackTrace();
            System.exit(1);
        }catch (IOException e) {
            AlertBox.getInstance().display(getClass().getSimpleName(), "Some problems with getting info from db");
            e.printStackTrace();
            System.exit(2);
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
            
            LoadFXML.getInstance().loadFile("loginPanel");
        } catch (IOException e) {
            AlertBox.getInstance().display(getClass().getSimpleName(), "Some problems by loading loginPanel.fxml");
            e.printStackTrace();
            System.exit(0);
        }
    }
    
    public void handlePackingOrderAction() {
        try {
            Stage stage = (Stage) tableView.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(LoadFXML.getInstance().getPath("packingMenu-" + Cookie.getInstance().get("rank")))));
        } catch (IOException e) {
            AlertBox.getInstance().display(getClass().getSimpleName(), "Some problems by loading packingMenu.fxml");
            e.printStackTrace();
            System.exit(0);
        }
    }
    
    public void handlePickingOrderAction() {
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
