package com.example.headfirstdesignpatternsexample.FactoryPattern.Creator

import com.example.headfirstdesignpatternsexample.FactoryPattern.Pizza
import com.example.headfirstdesignpatternsexample.FactoryPattern.PizzaForFactoryMethod

abstract class PizzaStoreForFactoryMethod {
    fun orderPizza(type: String) : PizzaForFactoryMethod? {
        val pizza: PizzaForFactoryMethod? = createPizza(type)
        pizza?.let {
            it.prepare()
            it.bake()
            it.cut()
            it.box()
        }
        return pizza
    }

    protected abstract fun createPizza(type: String): PizzaForFactoryMethod?
}