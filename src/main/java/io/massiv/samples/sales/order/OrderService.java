package io.massiv.samples.sales.order;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.newrelic.api.agent.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("/orders")
public class OrderService {
    private static final String SHIPPING = "http://localhost:8081/shipping";
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
        orders.put(order.getItem(), order);
        auditService.orderReceived(order);

        //        RestTemplate restTemplate = new RestTemplate();
        //        String result = restTemplate.getForObject(SHIPPING, String.class);
    }
}
