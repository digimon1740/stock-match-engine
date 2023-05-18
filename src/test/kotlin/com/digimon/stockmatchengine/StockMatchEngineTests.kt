package com.digimon.stockmatchengine

import org.junit.jupiter.api.Test


class StockMatchEngineTests {

    @Test
    fun `지정한 금액에 매도 수량이 99개 매수 수량이 100개에서 체결된다면 매도 수량은 0개 매수 수량은 1개가 남아야한다`() {

        val engine = StockMatchingEngine()

        // 매수 주문 생성
        val buyOrder = order {
            symbol = "AAPL"
            price = 100.0.toBigDecimal()
            quantity = 100.0
            orderType = OrderType.BUY
        }

        // 매도 주문 생성
        val sellOrder = order {
            symbol = "AAPL"
            price = 100.0.toBigDecimal()
            orderType = OrderType.SELL
            quantity = 99.0
        }

        // 매수 주문 추가
        engine.submitOrder(buyOrder)

        // 매도 주문 추가
        engine.submitOrder(sellOrder)


        // 매도 수량이 99개였고 매수 수량이 100개에서 체결되었으므로 매도 수량은 0개 매수 수량은 1개가 남아야한다
        assert(buyOrder.quantity == 1.0)
        assert(sellOrder.quantity == 0.0)
    }

    @Test
    fun `매수 주문을 취소하면 매수 주문 리스트에서 제거된다`() {

        val engine = StockMatchingEngine()

        // 매수 주문 생성
        val orderQuantity = 100.0
        val buyOrder = order {
            symbol = "AAPL"
            price = 100.0.toBigDecimal()
            quantity = orderQuantity
            orderType = OrderType.BUY
        }

        // 매수 주문 추가
        engine.submitOrder(buyOrder)

        // 매수 주문 취소
        val orderCanceled = engine.cancelOrder(buyOrder)

        // 매도 주문 생성
        val sellOrder = order {
            symbol = "AAPL"
            price = 100.0.toBigDecimal()
            orderType = OrderType.SELL
            quantity = orderQuantity
        }

        // 매도 주문 추가
        engine.submitOrder(sellOrder)

        // 매수 주문이 정상적으로 취소되었다면 orderCanceled == true
        assert(orderCanceled)

        // 매수 주문이 정상적으로 취소되었다면 남은 매수 quantity는 최초와 같아야한다
        assert(buyOrder.quantity == orderQuantity)

    }
}
