package com.binar.finalproject.flightticket.controller;

import com.binar.finalproject.flightticket.dto.MessageModel;
import com.binar.finalproject.flightticket.service.InvoiceService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    HttpServletResponse response;

    @GetMapping("/generate")
    public void generateInvoice(@RequestParam UUID orderId) throws IOException, JRException
    {
        MessageModel messageModel = new MessageModel();
        JasperPrint jasperPrint = invoiceService.generateInvoice(orderId);
        if(jasperPrint != null)
        {
            response.setContentType(MediaType.APPLICATION_PDF_VALUE);
            response.setHeader("Content-Disposition", "attachment; filename=Invoice-MTicket-" + orderId + ".pdf");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Generated invoice");
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
            response.flushBuffer();
        }
    }
}
