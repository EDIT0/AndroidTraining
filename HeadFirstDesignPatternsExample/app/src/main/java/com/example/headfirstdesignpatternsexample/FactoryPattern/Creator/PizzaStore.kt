package com.example.headfirstdesignpatternsexample.FactoryPattern.Creator

import com.example.headfirstdesignpatternsexample.FactoryPattern.Pizza

abstract class PizzaStore {
    fun orderPizza(type: String) : Pizza? {
        val pizza: Pizza? = createPizza(type)
        pizza?.let {
            it.prepare()
            it.bake()
            it.cut()
            it.box()
        }
        return pizza
    }

    protected abstract fun createPizza(type: String): Pizza?
}