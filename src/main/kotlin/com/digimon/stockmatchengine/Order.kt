package com.digimon.stockmatchengine

import java.math.BigDecimal

fun order(init: OrderBuilder.() -> Unit): Order = OrderBuilder().apply(init).build()

class OrderBuilder {
    lateinit var symbol: String
    lateinit var price: BigDecimal
    lateinit var orderType: OrderType
    var quantity: Double = 0.0

    fun build(): Order = Order(symbol, price, quantity, orderType)
}

data class Order(
    val symbol: String,
    val price: BigDecimal,
    var quantity: Double,
    val orderType: OrderType,
) {

    fun decreaseQuantity(quantity: Double): Order {
        this.quantity -= quantity
        return this
    }
}

enum class OrderType {
    BUY, SELL
}
