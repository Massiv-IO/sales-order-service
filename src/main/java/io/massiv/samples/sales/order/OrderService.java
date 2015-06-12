package io.massiv.samples.sales.order;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.newrelic.api.agent.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 */
@RestController
@RequestMapping("/orders")
public class OrderService {
    private static final String SHIPPING = "http://localhost:9000/shipping/";
    private Map<String, Order> orders = new ConcurrentHashMap<String, Order>();

    @Autowired
    protected AuditService auditService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Order getOrder(@PathVariable String id) {
        return orders.get(id);
    }

    @Trace
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void create(@RequestBody Order order) {
        System.out.println("Order received: " + order.getItem() + " Quantity: " + order.getQuantity());
        orders.put(order.getItem(), order);
        auditService.orderReceived(order);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>("{\"item\":\"1234\",\"quantity\":1}", headers);
        ResponseEntity<String> result = restTemplate.postForEntity(SHIPPING, entity, String.class);
    }
}
