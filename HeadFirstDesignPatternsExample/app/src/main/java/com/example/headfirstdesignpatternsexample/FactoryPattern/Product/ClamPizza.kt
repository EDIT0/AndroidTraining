package com.example.headfirstdesignpatternsexample.FactoryPattern.Product

import com.example.headfirstdesignpatternsexample.FactoryPattern.Pizza
import com.example.headfirstdesignpatternsexample.FactoryPattern.PizzaIngredientFactory
import com.example.headfirstdesignpatternsexample.Utility

class ClamPizza(
    private val ingredientFactory: PizzaIngredientFactory
): Pizza() {
    override fun prepare() {
        super.prepare()
        Utility.Log(javaClass.name, "준비 중: ${getName()}")

        setDough(ingredientFactory.createDough())
        setSauce(ingredientFactory.createSauce())
        setCheese(ingredientFactory.createCheese())
        setClams(ingredientFactory.createClam())
    }
}