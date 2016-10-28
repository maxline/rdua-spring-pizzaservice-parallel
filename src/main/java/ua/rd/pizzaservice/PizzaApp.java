package ua.rd.pizzaservice;

import ua.rd.pizzaservice.domain.Customer;
import ua.rd.pizzaservice.domain.Order;
import ua.rd.pizzaservice.infrastructure.ApplicationContext;
import ua.rd.pizzaservice.infrastructure.Context;
import ua.rd.pizzaservice.infrastructure.JavaConfig;
import ua.rd.pizzaservice.repository.PizzaRepository;
import ua.rd.pizzaservice.services.OrderService;

import java.lang.reflect.InvocationTargetException;

public class PizzaApp {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Customer customer = null;

        Context context = new ApplicationContext(new JavaConfig());
        //проверяем что контекст подымается и создает наш бин
        //можно проверить если подряд вызвать гетБин, будет один и тот же объект

        PizzaRepository pizzaRepository = context.getBean("pizzaRepository");
        System.out.println(pizzaRepository.find(1));

        OrderService orderService = context.getBean("orderService");
        Order order = orderService.placeNewOrder(customer, 1, 2, 3);
        //пытаемся создать конструктор объекта
        //вызываем рефлекшн с параметрами (передаем массив объектов с которым должен быть вызван конструктор)
        //объекты можем получить либо из контекста, если их еще нет, то создаем
        //достаем тип преобразываем в строку, если нету то пытаемся создать рекурсивно
    }
}