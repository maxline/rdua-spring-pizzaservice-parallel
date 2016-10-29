package ua.rd.pizzaservice.repository;

import org.springframework.stereotype.Repository;
import ua.rd.pizzaservice.domain.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * не умеет создавать order , умеет искать
 * по сути repository это dao
 */
@Repository("orderRepository")
public class InMemoryOrderRepository implements OrderRepository {

    private final List<Order> orders = new ArrayList();

    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public Order save(Order order) {
        orders.add(order);
        return order;
    }
}
