package com.digimon.stockmatchengine

import java.util.concurrent.CopyOnWriteArrayList

class OrderBook {

    val buyOrders = CopyOnWriteArrayList<Order>()
    val sellOrders = CopyOnWriteArrayList<Order>()

    fun addOrder(order: Order) {
        when (order.orderType) {
            OrderType.BUY -> buyOrders.add(order)
            OrderType.SELL -> sellOrders.add(order)
        }
    }

    fun cancelOrder(order: Order): Boolean {
        return when (order.orderType) {
            OrderType.BUY -> buyOrders.remove(order)
            OrderType.SELL -> sellOrders.remove(order)
        }
    }

}