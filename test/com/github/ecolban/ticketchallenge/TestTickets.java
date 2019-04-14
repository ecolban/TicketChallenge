package com.github.ecolban.ticketchallenge;

import static org.junit.Assert.assertEquals;

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
        int expected = 9 + 3 * 8 + 7;
        assertEquals(expected, TicketVendors.solve(vendors, 5));
    }

    @Test
    public void testAnotherSimpleCase() {
        int[] vendors = {3, 4, 8, 6, 8, 9, 9, 0, 3, 4, 5};
        int expected = 2 * 9 + 4 * 8 + 4 * 7;
        assertEquals(expected, TicketVendors.solve(vendors, 10));
    }

    @Test(timeout = 20)
    public void testSeveralVendorsOneWithManyTicketsLargePurchase() {
        int[] vendors = {3, 4, 8, 6, 8, 9, 100, 0, 3, 4, 1000007, 1000, 300, 3000, 300, 40050, 60000, 6000};
        int a = 1007;
        int expected = a * (1000007 - a) + a * (a + 1) / 2;
        assertEquals(expected, TicketVendors.solve(vendors, 1007));
    }

    @Test(timeout = 20)
    public void testManyVendorsWithManyTicketsLargePurchase() {
        int[] vendors = new int[1000];
        for (int i = 0; i < vendors.length; i++) {
            vendors[i] = (i + 1) * 1000;
        }
        assertEquals(999500500, TicketVendors.solve(vendors, 1000));
    }

    @Test(timeout = 20)
    public void testManyVendorsWithManyTicketsFewPurchases() {
        int[] vendors = new int[10000];
        for (int i = 0; i < vendors.length; i++) {
            vendors[i] = 1000000;
        }
        assertEquals(2000000, TicketVendors.solve(vendors, 2));
    }

    @Test(timeout = 20)
    public void testVendorWithHugeNumberOfTickets() {
        int[] vendors = {Integer.MAX_VALUE};
        assertEquals(Integer.MAX_VALUE, TicketVendors.solve(vendors, 1));
    }

    @Test(timeout = 400)
    public void testLargeNumberOfVendors() {
        int[] vendors = new int[1000000];
        for (int i = 0; i < vendors.length; i++) {
            vendors[i] = 2 * i + 1;
        }
        int m = (1000000 - 1) * 2 + 1;
        int expected = m + (m - 1) + 2 * (m - 2) + 2 * (m - 3) + 3 * (m - 4) + (m - 5);
        shuffle(vendors);
        assertEquals(expected, TicketVendors.solve(vendors, 10));
    }

    private void shuffle(int[] a) {
        ThreadLocalRandom rng = ThreadLocalRandom.current();
        for (int i = a.length - 1; i > 0; i--) {
            int j = rng.nextInt(i + 1);
            int tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }


    @Test
    public void testRandomCases() {

        ThreadLocalRandom rng = ThreadLocalRandom.current();
        for (int n = 0; n < 10; n++) {
            int[] vendors = new int[rng.nextInt(1000)];
            for (int i = 0; i < vendors.length; i++) {
                vendors[i] = rng.nextInt(1000);
            }
            int k = rng.nextInt(1000);
            int expected = hiddenSolve(vendors, k);
            assertEquals(expected, TicketVendors.solve(vendors, k));
        }

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
