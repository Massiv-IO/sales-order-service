package io.massiv.samples.sales.order;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class SupplierService {

    @Autowired
    protected JmsTemplate jmsTemplate;

    @JmsListener(destination = "supplier.queue")
    public void receiveMessage(final String message) {
        System.out.println("Received <" + message + ">");
        jmsTemplate.send("backorder.queue", new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }
}