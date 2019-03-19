import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TicketVendors {

    public static long solve(int[] vendors, int n) {
        int minPrice = 1;
        int maxPrice = 0;
        Arrays.sort(vendors);
        for (int i = 0, j = vendors.length - 1; i < j; i++, j--) {
            int tmp = vendors[i];
            vendors[i] = vendors[j];
            vendors[j] = tmp;
        }
        for (int vendor : vendors) {
            maxPrice = Math.max(maxPrice, vendor);
        }
        int[] purchases = new int[maxPrice + 1];
        for (int vendor : vendors) {
            for (int price = vendor; price >= minPrice; price--) {
                purchases[price]++;
                if (n > 0) {
                    n--;
                } else {
                    if (purchases[minPrice] == 0) minPrice = price;
                    purchases[minPrice]--;
                    if (purchases[minPrice] == 0) minPrice++;
                }
            }
        }
        long result = 0;
        for (int price = minPrice; price <= maxPrice; price++) {
            result += purchases[price] * price;
        }
        return result;
    }

    public static long solve2(int[] vendors, int k) {

        int max = 0;
        for (int i : vendors) {
            max = Math.max(i, max);
        }

        int[] quants = new int[max + 1];

        for (int i: vendors) {
            quants[i]++;
        }

        long result = 0L;

        for (int i = 0; i < k; i++) {
            quants[max]--;
            quants[max - 1]++;
            result += max;
            if (quants[max] <= 0) max--;
        }

        return result;
    }
}
