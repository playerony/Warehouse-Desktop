/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.warehouse.abstractController;

import com.warehouse.dao.OrderDao;
import com.warehouse.utility.AlertBox;
import java.io.File;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.warehouse.cookie.Cookie;
import com.warehouse.entity.Order;
import com.warehouse.impl.OrderDaoImpl;
import com.warehouse.loader.LoadFXML;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author pawel_000
 */
public class OrderMenuAbstractController{
    protected OrderDao orderDao;
    
    public void init() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        orderDao = context.getBean(OrderDaoImpl.class);
    }

    public void handleGeneratePDF() {
        try {
            OutputStream file = new FileOutputStream(new File("./Test.pdf"));
            Document document = new Document();
            PdfWriter.getInstance(document, file);
            document.open();
            
            document.add(new Paragraph("Order information"));
            
            for(Order o : orderDao.getOrderList())
                if(o.getId() == Integer.parseInt(Cookie.getInstance().get("orderID")))
                    document.add(new Paragraph("Order (" + o.getId() + ") " + o.getItems() + " " + o.getDate() + " " + o.getClient().getPhone()));

            document.add(new Paragraph(new Date().toString()));
            document.close();

            file.close();

            AlertBox.getInstance().display(getClass().getSimpleName(), "Successful generated *.PDF file !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void handleAddUserAction() {
        try{
            LoadFXML.getInstance().loadFile("addUserPanel");
        }catch(IOException e){
            AlertBox.getInstance().display(getClass().getSimpleName(), "Some problems by loading addUserPanel.fxml");
            e.printStackTrace();
            System.exit(0);
        }
    }
    
    public void handleDeleteUserAction() {
        try{
            LoadFXML.getInstance().loadFile("deleteUserPanel");
        }catch(IOException e){
            AlertBox.getInstance().display(getClass().getSimpleName(), "Some problems by loading deleteUserPanel.fxml");
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void handleInformationAction() {
        AlertBox.getInstance().display("About", "It's a simple version of the ERP system wrote by playerony(Paweł Wojtasiński)");
    }
}
