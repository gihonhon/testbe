package com.binar.finalproject.flightticket.service.impl;

import com.binar.finalproject.flightticket.model.Orders;
import com.binar.finalproject.flightticket.repository.OrderRepository;
import com.binar.finalproject.flightticket.service.InvoiceService;
import com.binar.finalproject.flightticket.utility.QRGenerator;
import com.google.zxing.WriterException;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DataSource dataSource;

    private Connection getConnection()
    {
        try {
            return dataSource.getConnection();
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public JasperPrint generateInvoice(UUID orderId){
        Optional<Orders> isOrders = orderRepository.findById(orderId);
        if(isOrders.isPresent() && !isOrders.get().getPnrCode().isEmpty())
        {
            try {
                JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/Invoice.jrxml"));
                Map<String, Object> params = new HashMap<>();
                params.put("ORDER_ID", orderId.toString());
                params.put("QR", new ByteArrayInputStream(
                        QRGenerator.getQRCodeImage(orderId.toString(), 140, 140)
                ));
                return JasperFillManager.fillReport(jasperReport, params, getConnection());
            }
            catch (IOException | JRException e)
            {
                e.printStackTrace();
                return null;
            } catch (WriterException e) {
                throw new RuntimeException(e);
            }
        }
        else
            return null;
    }
}
