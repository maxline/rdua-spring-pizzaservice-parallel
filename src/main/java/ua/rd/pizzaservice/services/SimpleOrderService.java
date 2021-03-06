package ua.rd.pizzaservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;
import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.infrastructure.Benchmark;
import ua.rd.pizzaservice.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * пицца, кастомер, ордер
 */
@Service("orderService")
public class SimpleOrderService implements OrderService { //, ApplicationContextAware{
    private final PizzaService pizzaService;
    private final OrderRepository orderRepository;
    //private ApplicationContext context;

    @Autowired
    public SimpleOrderService(PizzaService pizzaService, OrderRepository orderRepository) {
        this.pizzaService = pizzaService;
        this.orderRepository = orderRepository;
    }

    @Benchmark
    public Order placeNewOrder(Customer customer, Integer... pizzasID) {
        List<Pizza> pizzas = new ArrayList<>();

        for (Integer id : pizzasID) {
            pizzas.add(findPizzaByID(id));  // get Pizza from predifined in-memory list
        }
        //Order newOrder = new Order(customer, pizzas);

        Order newOrder = createNewOrder();
        newOrder.setCustomer(customer);
        newOrder.setPizzas(pizzas);

        saveOrder(newOrder);  // set Order Id and save Order to in-memory list
        return newOrder;
    }

    @Lookup
    protected Order createNewOrder() {
        throw new IllegalStateException("Container can not get bean Order!");
//        try {
//            return (Order) context.getBean("order");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }

    private Pizza findPizzaByID(Integer id) {

        return pizzaService.find(id);
    }

    private void saveOrder(Order newOrder) {
        orderRepository.save(newOrder);
    }

//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.context = applicationContext;
//
//    }
}