import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/*
 *
 * Several ticket vendors are selling their tickets at the fair.
 * The price of a ticket is determined by the number of tickets the vendor has.
 * If the vendor has 9 tickets, the next ticket bought from that vendor will be 9 dollars.
 * After the purchase, the vendor will have 8 tickets and so will sell the next ticket for 8 dollars and so on.
 *
 * Given an array representing the number of tickets at each vendor, you must buy n tickets from the vendors.
 * Your goal is to spend as much money as possible.
 * Write an algorithm that will do just that and return the amount of money that you have spent.
 *
 */
public class TestTickets {

    @Test
    public void testASimpleInstance() {
        int[] vendors = {3, 4, 8, 6, 8, 9};
        assertEquals(40, TicketVendors.solve2(vendors, 5));
    }

    @Test
    public void testAnotherSimpleInstance() {
        int[] vendors = {3, 4, 8, 6, 8, 9, 9, 0, 3, 4, 5};
        System.out.println(TicketVendors.solve(vendors, 10));
        assertEquals(78, TicketVendors.solve2(vendors, 10));
    }

    @Test//(timeout = 45)
    public void testOptimizedVendors() {
        long time = System.currentTimeMillis();
        int[] vendors = {3, 4, 8, 6, 8, 9, 100, 0, 3, 4, 1000007, 1000, 300, 3000, 300, 40050, 60000, 6000};
        assertEquals("Input does not match", 95007050028L, TicketVendors.solve2(vendors, 100007));
//        assertEquals("Input does not match", 517769516, TicketVendors.solve2(vendors, 100007));
        System.out.println((System.currentTimeMillis() - time));
    }

    @Test(timeout = 20)
    public void testManyVendorsWithManyTickets() {
        int[] vendors = new int[1000];
        for (int i = 0; i < vendors.length; i++) {
            vendors[i] = (i + 1) * 1000;
        }
        long startTime = System.currentTimeMillis();
        assertEquals("Input does not match", 999500500, TicketVendors.solve2(vendors, 1000));
        System.out.println((System.currentTimeMillis() - startTime));
    }

    @Test(timeout = 20)
    public void testManyVendorsWithManyTicketsFewPurchase() {
        int[] vendors = new int[10000];
        for (int i = 0; i < vendors.length; i++) {
            vendors[i] = 1000000;
        }
        long startTime = System.currentTimeMillis();
        assertEquals("Input does not match", 2000000, TicketVendors.solve2(vendors, 2));
        System.out.println((System.currentTimeMillis() - startTime));
    }

    @Test
    public void testVendorWithHugeNumberOfTickets() {
        int huge = Integer.MAX_VALUE >> 1;
        int[] vendors = {huge};
        assertEquals(huge, TicketVendors.solve2(vendors, 1));
    }
}
