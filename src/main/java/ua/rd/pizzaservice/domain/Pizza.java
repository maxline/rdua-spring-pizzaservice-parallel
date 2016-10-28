package ua.rd.pizzaservice.domain;

import java.math.BigDecimal;

public class Pizza {
    private Long id;
    private String name;
    private BigDecimal price;
    private PizzaType type;

    public enum PizzaType {
        VEGETARIAN, SEA, MEAT
    }


    public Pizza(Long id, String name, BigDecimal price, PizzaType type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type=" + type +
                '}';
    }
}
