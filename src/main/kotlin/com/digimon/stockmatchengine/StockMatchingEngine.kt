package com.digimon.stockmatchengine

import java.util.concurrent.ConcurrentHashMap

class StockMatchingEngine {

    private val orderBookOfSymbol = ConcurrentHashMap<String, OrderBook>()

    fun submitOrder(order: Order) {
        val orderBook = orderBookOfSymbol.getOrPut(order.symbol) { OrderBook() }
        orderBook.addOrder(order)

        matchOrders(orderBook)
    }

    fun cancelOrder(order: Order): Boolean {
        val orderBook = orderBookOfSymbol.getOrPut(order.symbol) { OrderBook() }
        return orderBook.cancelOrder(order)
    }

    private fun matchOrders(orderBook: OrderBook) {
        val buyOrders = orderBook.buyOrders
        val sellOrders = orderBook.sellOrders

        val buyIterator = buyOrders.iterator()
        while (buyIterator.hasNext()) {
            val buyOrder = buyIterator.next()
            val sellIterator = sellOrders.iterator()

            while (sellIterator.hasNext()) {
                val sellOrder = sellIterator.next()

                if (buyOrder.price >= sellOrder.price) {
                    // 체결 로직 구현
                    val quantity = minOf(buyOrder.quantity, sellOrder.quantity)
                    val price = buyOrder.price

                    // 체결 정보 처리
                    println("주문 체결 - 매수 주문: $buyOrder, 매도 주문: $sellOrder")
                    println("체결 수량: $quantity, 체결 가격: $price")

                    // 체결된 주문 처리
                    val newBuyOrder = buyOrder.decreaseQuantity(quantity)
                    val newSellOrder = sellOrder.decreaseQuantity(quantity)

                    // 주문이 모두 체결되었을 경우 리스트에서 제거
                    if (newBuyOrder.quantity == 0.0) {
                        buyOrders.clear()
                    }

                    if (newSellOrder.quantity == 0.0) {
                        sellOrders.clear()
                    }

                    // TODO 체결 후 잔고 업데이트 등 필요한 작업 수행
                } else {
                    // 매칭되는 주문 없음
                    println("매칭 주문 없음 - 매수 주문: $buyOrder, 매도 주문: $sellOrder")
                }
            }
        }
    }
}

