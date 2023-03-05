package com.example.headfirstdesignpatternsexample.FactoryPattern.ConcreteCreator

import com.example.headfirstdesignpatternsexample.FactoryPattern.Pizza
import com.example.headfirstdesignpatternsexample.FactoryPattern.Creator.PizzaStore
import com.example.headfirstdesignpatternsexample.FactoryPattern.Creator.PizzaStoreForFactoryMethod
import com.example.headfirstdesignpatternsexample.FactoryPattern.NYPizzaIngredientFactory
import com.example.headfirstdesignpatternsexample.FactoryPattern.PizzaForFactoryMethod
import com.example.headfirstdesignpatternsexample.FactoryPattern.Product.CheesePizza
import com.example.headfirstdesignpatternsexample.FactoryPattern.Product.ClamPizza
import com.example.headfirstdesignpatternsexample.FactoryPattern.Product.NYStyleCheesePizza

class NYPizzaStoreForFactoryMethod : PizzaStoreForFactoryMethod() {
    override fun createPizza(type: String): PizzaForFactoryMethod? {
        when(type) {
            "cheese" -> {
                return NYStyleCheesePizza()
            }
            "veggie" -> {

            }
            "clam" -> {

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