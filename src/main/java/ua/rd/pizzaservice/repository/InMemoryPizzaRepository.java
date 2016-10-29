package ua.rd.pizzaservice.repository;

import org.springframework.stereotype.Repository;
import ua.rd.pizzaservice.domain.Pizza;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Repository("pizzaRepository")
public class InMemoryPizzaRepository implements PizzaRepository {

    private final Map<Integer, Pizza> pizzas = new HashMap<>();   //static не нужен, в одном эксемпляре в контейнере

    //@Benchmark
    @PostConstruct
    public void init() {
        pizzas.put(1, new Pizza(1L, "pizza1", new BigDecimal(100.00), Pizza.PizzaType.VEGETARIAN));
        pizzas.put(2, new Pizza(2L, "pizza2", new BigDecimal(120.00), Pizza.PizzaType.SEA));
        pizzas.put(3, new Pizza(3L, "pizza1", new BigDecimal(150.00), Pizza.PizzaType.MEAT));
    }

    //@Benchmark
    @Override
    public Pizza find(Integer id) {
        return pizzas.get(id);
    }
}
