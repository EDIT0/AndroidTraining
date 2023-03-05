package com.example.headfirstdesignpatternsexample.FactoryPattern

import com.example.headfirstdesignpatternsexample.FactoryPattern.ConcreteCreator.ChicagoPizzaStore
import com.example.headfirstdesignpatternsexample.FactoryPattern.ConcreteCreator.ChicagoPizzaStoreForFactoryMethod
import com.example.headfirstdesignpatternsexample.FactoryPattern.ConcreteCreator.NYPizzaStore
import com.example.headfirstdesignpatternsexample.FactoryPattern.ConcreteCreator.NYPizzaStoreForFactoryMethod
import com.example.headfirstdesignpatternsexample.Utility

fun main() {
    val TAG = "PizzaTestDrive"
    val nyStore = NYPizzaStore()
    val chicagoStore = ChicagoPizzaStore()

    var pizza: Pizza? = nyStore.orderPizza("cheese")
    Utility.Log(TAG, "주문1: ${pizza?.getName()}")

    pizza = chicagoStore.orderPizza("cheese")
    Utility.Log(TAG, "주문2: ${pizza?.getName()}")

    val nyStoreForFactoryMethod = NYPizzaStoreForFactoryMethod()
    val chicagoStoreForFactoryMethod = ChicagoPizzaStoreForFactoryMethod()

    var pizza2: PizzaForFactoryMethod? = nyStoreForFactoryMethod.orderPizza("cheese")
    Utility.Log(TAG, "주문3: ${pizza2?.getName()}")

    pizza2 = chicagoStoreForFactoryMethod.orderPizza("cheese")
    Utility.Log(TAG, "주문4: ${pizza2?.getName()}")

}