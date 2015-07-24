package io.massiv.samples.sales.order;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class SupplierService {

    @JmsListener(destination = "supplier.queue")
    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
    }
}