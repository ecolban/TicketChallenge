import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TicketVendors {

    public static int solve(int[] vendors, int k) {

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
