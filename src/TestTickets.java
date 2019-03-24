import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

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
    public void testSimpleCase() {
        int[] vendors = {3, 4, 8, 6, 8, 9};
        assertEquals(40, TicketVendors.solve(vendors, 5));
    }

    @Test
    public void testAnotherSimpleCase() {
        int[] vendors = {3, 4, 8, 6, 8, 9, 9, 0, 3, 4, 5};
        assertEquals(78, TicketVendors.solve(vendors, 10));
    }

    @Test(timeout = 20)
    public void testSeveralVendorsOneWithManyTicketsLargePurchase() {
        long startTime = System.currentTimeMillis();
        int[] vendors = {3, 4, 8, 6, 8, 9, 100, 0, 3, 4, 1000007, 1000, 300, 3000, 300, 40050, 60000, 6000};
        assertEquals(1006500528, TicketVendors.solve(vendors, 1007));
        System.out.println((System.currentTimeMillis() - startTime));
    }

    @Test(timeout = 20)
    public void testManyVendorsWithManyTicketsLargePurchase() {
        int[] vendors = new int[1000];
        for (int i = 0; i < vendors.length; i++) {
            vendors[i] = (i + 1) * 1000;
        }
        long startTime = System.currentTimeMillis();
        assertEquals(999500500, TicketVendors.solve(vendors, 1000));
        System.out.println((System.currentTimeMillis() - startTime));
    }

    @Test(timeout = 20)
    public void testManyVendorsWithManyTicketsFewPurchases() {
        int[] vendors = new int[10000];
        for (int i = 0; i < vendors.length; i++) {
            vendors[i] = 1000000;
        }
        long startTime = System.currentTimeMillis();
        assertEquals(2000000, TicketVendors.solve(vendors, 2));
        System.out.println((System.currentTimeMillis() - startTime));
    }

    @Test(timeout = 20)
    public void testVendorWithHugeNumberOfTickets() {
        int[] vendors = {Integer.MAX_VALUE};
        assertEquals(Integer.MAX_VALUE, TicketVendors.solve(vendors, 1));
    }

    @Test
    public void testRandomCase() {

        ThreadLocalRandom rng = ThreadLocalRandom.current();
        int[] vendors = new int[rng.nextInt(1000)];
        for (int i = 0; i < vendors.length; i++) {
            vendors[i] = rng.nextInt(1000);
        }
        int k = rng.nextInt(1000);
        int expected = hiddenSolve(vendors, k);
//        System.out.println(expected);
        assertEquals(expected, TicketVendors.solve(vendors, k));

    }

    private int hiddenSolve(int[] vendors, int k) {

        Map<Integer, Integer> quants = new HashMap<>();
        int max = 0;
        for (int i : vendors) {
            quants.put(i, quants.getOrDefault(i, 0) + 1);
            max = Math.max(i, max);
        }

        int result = 0;

        for (int i = 0; i < k; i++) {
            int valueAfterUpdate = quants.get(max) - 1;
            quants.put(max, valueAfterUpdate);
            quants.put(max - 1, quants.getOrDefault(max - 1, 0) + 1);
            result += max;
            if (valueAfterUpdate == 0) max--;
        }

        return result;
    }
}
