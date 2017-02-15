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
import com.warehouse.impl.OrderDaoImpl;
import java.io.OutputStream;
import java.util.Date;

/**
 *
 * @author pawel_000
 */
public class OrderMenuAbstractController{
    protected OrderDao orderDao = new OrderDaoImpl();

    public void handleGeneratePDF() {
        try {
            OutputStream file = new FileOutputStream(new File("./Test.pdf"));
            Document document = new Document();
            PdfWriter.getInstance(document, file);
            document.open();
            
            document.add(new Paragraph("PDF test"));

            document.add(new Paragraph(new Date().toString()));
            document.close();

            file.close();

            AlertBox.getInstance().display(getClass().getSimpleName().toString(), "Successful generated *.PDF file !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleInformationAction() {
        AlertBox.getInstance().display("About", "It's a simple version of the ERP system wrote by playerony(Paweł Wojtasiński)");
    }
}
