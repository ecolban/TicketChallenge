package com.github.ecolban.ticketchallenge

import com.github.ecolban.ticketchallenge.utils.IntHeap
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
     */
    @JvmStatic
    fun solve1(vendors: IntArray, ticketsToBuy: Int): Long {
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
        return result
    }

    /**
     * Similar algorithm to solve1, but instead of decrementing maxPrice in steps
     * of 1, decrements maxPrice to the next price in vendors. Instead of sorting the
     * vendors array, a heap is used.
     */
    @JvmStatic
    fun solve(vendors: IntArray, ticketsToBuy: Int): Long {
        val vendorHeap = IntHeap(vendors)
        var result: Long = 0
        var maxPrice = vendorHeap.peek()
        var ticketsYetToBuy = ticketsToBuy
        var ticketsAtMaxPrice = 0
        while (ticketsYetToBuy > 0) {
            while (vendorHeap.peek() == maxPrice) {
                vendorHeap.pop()
                ticketsAtMaxPrice++
            }
            val nextPrice = vendorHeap.peek()
            val ticketsAtMoreThanNextPrice = (maxPrice - nextPrice) * ticketsAtMaxPrice
            if (ticketsAtMoreThanNextPrice < ticketsYetToBuy) {
                val q = maxPrice - nextPrice
                result += ticketsAtMaxPrice * (q * nextPrice + q * (q + 1) / 2)
                ticketsYetToBuy -= ticketsAtMoreThanNextPrice
                maxPrice = nextPrice
            } else {
                val q = ticketsYetToBuy / ticketsAtMaxPrice
                val r = ticketsYetToBuy % ticketsAtMaxPrice
                result += ticketsAtMaxPrice * (q * (maxPrice - q) + q * (q + 1) / 2) +
                        r * (maxPrice - q)
                ticketsYetToBuy = 0
            }
        }
        return result
    }

}
