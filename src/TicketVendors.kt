import java.util.HashMap
import kotlin.math.max
import kotlin.math.min


object TicketVendors {

    /**
     * A vendor who has N tickets, has one ticket at price N, one ticket at price N - 1, etc,
     * down to one ticket at price 1. However, the ticket at price N - 1 becomes _available_
     * only after the ticket at price N has been sold. The algorithm is based on using a map
     * that maps price to number of tickets available at that price. After N tickets at price
     * P are sold, N more tickets at price P - 1 become available and the map is updated
     * accordingly.
     */
    @JvmStatic
    fun solve(vendors: IntArray, ticketsToBuy: Int): Int {

        val availableTicketsAtPrice = HashMap<Int, Int>()
        var maxPrice = 0
        for (i in vendors) {
            availableTicketsAtPrice[i] = (availableTicketsAtPrice[i] ?: 0) + 1
            maxPrice = max(i, maxPrice)
        }

        var result = 0

        var ticketsAtMaxPrice: Int = availableTicketsAtPrice[maxPrice]!!
        var ticketsYetToBuy = ticketsToBuy
        while (ticketsYetToBuy > 0) {
            val ticketsBoughtAtMaxPrice = min(ticketsYetToBuy, ticketsAtMaxPrice) // ticketsBoughtAtMaxValue >= 1
            result += maxPrice * ticketsBoughtAtMaxPrice
            ticketsYetToBuy -= ticketsBoughtAtMaxPrice
            maxPrice--
            ticketsAtMaxPrice = (availableTicketsAtPrice[maxPrice] ?: 0) + ticketsBoughtAtMaxPrice
        }

        return result
    }

}
