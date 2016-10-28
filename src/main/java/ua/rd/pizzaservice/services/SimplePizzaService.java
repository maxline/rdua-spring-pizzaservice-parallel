package ua.rd.pizzaservice.services;

import ua.rd.pizzaservice.domain.Pizza;
import ua.rd.pizzaservice.infrastructure.BenchMark;
import ua.rd.pizzaservice.repository.PizzaRepository;

public class SimplePizzaService implements PizzaService {

    private PizzaRepository pizzaRepository;

    public SimplePizzaService(PizzaRepository pizzaRepository) {
        // InitialContext context = new InitialContext();
        // this.pizzaRepository = (PizzaRepository) context.getInstance("pizzaRepository");
        this.pizzaRepository = pizzaRepository;
    }

    @BenchMark(false)
    @Override
    public Pizza find(Integer id) {
        return pizzaRepository.find(id);
    }
}
