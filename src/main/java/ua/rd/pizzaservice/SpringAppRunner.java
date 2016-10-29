package ua.rd.pizzaservice;

/**
 * @author Serhii_Mykhliuk
 */
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.repository.OrderRepository;
import ua.rd.pizzaservice.repository.PizzaRepository;
import ua.rd.pizzaservice.services.OrderService;

import java.util.Arrays;

public class SpringAppRunner {
    public static void main(String[] args) {

        ConfigurableApplicationContext repoContext = new ClassPathXmlApplicationContext("repoContext.xml");  //запускает создание контейнера и считывает все бины
        System.out.println("repoContext: " + Arrays.toString(repoContext.getBeanDefinitionNames()));

        ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext(
                                new String[]{"appContext.xml"}, repoContext);

        System.out.println("appContext: " + Arrays.toString(appContext.getBeanDefinitionNames()));

        PizzaRepository pizzaRepository = (PizzaRepository) repoContext.getBean("pizzaRepository"); //имя плюс тип тогда не нужно будет делать каст
        System.out.println("find(1): " + pizzaRepository.find(1));  // null т.к. не вызвался метод init


        OrderRepository orderRepository = (OrderRepository) repoContext.getBean("orderRepository");
        //System.out.println(orderRepository.find());

        OrderService orderService = (OrderService) appContext.getBean("orderService");
        Order order = orderService.placeNewOrder(null, 1, 3);
        System.out.println("order 1,3: " + order);
        order = orderService.placeNewOrder(null, 1);
        System.out.println("order 1: " + order);
        System.out.println(orderService.getClass()); //сделает прокси

        appContext.close();
        repoContext.close();
    }
}