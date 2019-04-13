package com.github.ecolban.ticketchallenge

import java.util.HashMap
import kotlin.math.max
import kotlin.math.min
import java.lang.System.currentTimeMillis as now


object TicketVendors {

    /**
     * A vendor who has N tickets, has one ticket at price N, one ticket at price N - 1, etc,
     * down to one ticket at price 1. However, the ticket at price N - 1 becomes _available_
     * only after the ticket at price N has been sold. The algorithm is based on using a map
     * that maps price to number of tickets available at that price. After N tickets at price
     * P are sold, N more tickets at price P - 1 become available. The map is initialized at
     * the beginning, but a variable <code>ticketsAtMaxPrice</code> keeps track of the number
     * of tickets available at maxPrice, thus removing the need to update the map.
     */
    @JvmStatic
    fun solve0(vendors: IntArray, ticketsToBuy: Int): Int {

        val availableTicketsAtPrice = HashMap<Int, Int>()
        var maxPrice = 0
        for (i in vendors) {
            availableTicketsAtPrice[i] = (availableTicketsAtPrice[i] ?: 0) + 1
            maxPrice = max(i, maxPrice)
        }

        var result = 0

        var ticketsAtMaxPrice: Int = availableTicketsAtPrice[maxPrice] ?: 0
        var ticketsYetToBuy = ticketsToBuy
        while (ticketsYetToBuy > 0) {
            val ticketsBoughtAtMaxPrice = min(ticketsYetToBuy, ticketsAtMaxPrice) // ticketsBoughtAtMaxValue >= 1
            result += maxPrice * ticketsBoughtAtMaxPrice
            ticketsYetToBuy -= ticketsBoughtAtMaxPrice
            maxPrice--
            ticketsAtMaxPrice += (availableTicketsAtPrice[maxPrice] ?: 0)
        }

        return result
    }

    /**
     * Similar algorithm to <code>solve0</code> but uses the given vendors array
     * instead of a map. This saves the time of initializing the map, but requires
     * sorting the vendors array. Theoretically this algorithm has a higher complexity
     * since sorting is O(m log m) whereas initializing the map is O(m), where m is
     * the length of the array, but in practice sorting is much faster when the
     * vendors array size is within the limits that don't cause an OutOfMemoryError.
     *
     * [A possible improvement is to build a priority queue with all the
     * vendors values (which is O(n)), and pull just as many vendors out of that
     * queue to complete the purchase of k tickets. If that number is m, then
     * pulling m values from the queue is O(m log(n)), ... but I don't really
     * believe this will improve the time in practice for the sizes that don't
     * result in OutOfMemoryErrors]
     */
    @JvmStatic
    fun solve1(vendors: IntArray, ticketsToBuy: Int): Long {
        val start = now()
        vendors.sortDescending()
        var result: Long = 0
        var maxPrice = vendors[0]
        var idx = 0
        var nextIdx = (idx + 1 until vendors.size).find { vendors[it] < maxPrice } ?: vendors.size

        var ticketsAtMaxPrice = nextIdx - idx
        idx = nextIdx
        // vendors[idx] is the first vendor who sells for less than maxPrice, if any
        var ticketsYetToBuy = ticketsToBuy
        while (ticketsYetToBuy > 0) {
            val ticketsBoughtAtMaxPrice = min(ticketsYetToBuy, ticketsAtMaxPrice) // ticketsBoughtAtMaxValue >= 1
            result += maxPrice * ticketsBoughtAtMaxPrice
            ticketsYetToBuy -= ticketsBoughtAtMaxPrice
            maxPrice--
            nextIdx = (idx until vendors.size).find { vendors[it] < maxPrice } ?: vendors.size
            ticketsAtMaxPrice += nextIdx - idx
            idx = nextIdx
        }
        println(now() - start)
        return result
    }

    /**
     * Similar algorithm to solve1, but instead of decrementing maxPrice in steps
     * of 1, decrements maxPrice to the next price in vendors. If vendors were
     * provided pre-sorted, then this algorithm would have been O(min(m, sqrt(k))),
     * where m is the size of the vendors array and k is the number of tickets
     * to buy.
     *
     * Proof of complexity:
     * One iteration through the loop is O(1).
     * The number of iterations through the loop is bounded by m (= vendors.size)
     * In each iteration, the number of tickets bought increases by at least 1,
     * so the number of iterations is bounded by the number of terms of the sum
     * 1 + 2 + ... = k, where k = ticketsToBuy. The number of terms in this sum is
     * O(sqrt(k)), since the sum increases quadratically as function of the number
     * of terms. So the number of iterations is O(min(m, sqrt(k)). This also a
     * lower bound since with input vendors = {n, n-1, n-2, ..., n-a+1, ...} and
     * k = a*(a + 1)/2, there will be a iterations through the loop.
     */
    @JvmStatic
    fun solve(vendors: IntArray, ticketsToBuy: Int): Long {
        val start = now()
        vendors.sortDescending()
        var result: Long = 0
        var maxPrice = vendors[0]
        var idx = 0
        var ticketsYetToBuy = ticketsToBuy
        var ticketsAtMaxPrice = 0
        /*

        */
        // vendors[idx] is the first vendor who sells for maxPrice
        while (ticketsYetToBuy > 0) {
            val nextIdx = (idx + 1 until vendors.size).find { vendors[it] < maxPrice } ?: vendors.size
            ticketsAtMaxPrice += nextIdx - idx
            val nextPrice = if (nextIdx < vendors.size) vendors[nextIdx] else 0
            val ticketsAtMoreThanNextPrice = (maxPrice - nextPrice) * ticketsAtMaxPrice
            if (ticketsAtMoreThanNextPrice < ticketsYetToBuy) {
                val q = maxPrice - nextPrice
                result += ticketsAtMaxPrice * (q * nextPrice + q * (q + 1) / 2)
                ticketsYetToBuy -= ticketsAtMoreThanNextPrice
                idx = nextIdx
                maxPrice = nextPrice
            } else {
                val q = ticketsYetToBuy / ticketsAtMaxPrice
                val r = ticketsYetToBuy % ticketsAtMaxPrice
                result += ticketsAtMaxPrice * (q * (maxPrice - q) + q * (q + 1) / 2) +
                        r * (maxPrice - q)
                ticketsYetToBuy = 0
            }
        }
        println(now() - start)
        return result
    }

}
