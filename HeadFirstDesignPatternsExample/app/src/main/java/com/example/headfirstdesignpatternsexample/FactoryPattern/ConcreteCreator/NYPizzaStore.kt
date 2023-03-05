package com.example.headfirstdesignpatternsexample.FactoryPattern.ConcreteCreator

import com.example.headfirstdesignpatternsexample.FactoryPattern.Pizza
import com.example.headfirstdesignpatternsexample.FactoryPattern.Creator.PizzaStore
import com.example.headfirstdesignpatternsexample.FactoryPattern.NYPizzaIngredientFactory
import com.example.headfirstdesignpatternsexample.FactoryPattern.Product.CheesePizza
import com.example.headfirstdesignpatternsexample.FactoryPattern.Product.ClamPizza
import com.example.headfirstdesignpatternsexample.FactoryPattern.Product.NYStyleCheesePizza

class NYPizzaStore : PizzaStore() {
    override fun createPizza(type: String): Pizza? {
        var pizza: Pizza? = null
        val ingredientFactory = NYPizzaIngredientFactory()
        when(type) {
            "cheese" -> {
                pizza = CheesePizza(ingredientFactory)
                pizza.setName("뉴욕 스타일 치즈 피자")
                return pizza
            }
            "veggie" -> {

            }
            "clam" -> {
                pizza = ClamPizza(ingredientFactory)
                pizza.setName("뉴욕 스타일 조개 피자")
                return pizza
            }
            "pepperoni" -> {

            }
            else -> {
                return null
            }
        }
        return null
    }
}