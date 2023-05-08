package com.binar.finalproject.flightticket.service;

import net.sf.jasperreports.engine.JasperPrint;

import java.util.UUID;

public interface InvoiceService {

    JasperPrint generateInvoice(UUID orderId);
}
