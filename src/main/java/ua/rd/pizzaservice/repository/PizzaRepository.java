package ua.rd.pizzaservice.repository;

import ua.rd.pizzaservice.domain.Pizza;

public interface PizzaRepository {
    Pizza find(Integer id);
}
