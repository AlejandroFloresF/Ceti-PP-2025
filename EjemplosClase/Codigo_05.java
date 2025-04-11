import java.util.*;
import java.util.concurrent.*;
//Strong Consistency con synchronized
public class Codigo_05 {
    private final Map<Integer, Integer> map = new ConcurrentHashMap<>();

    public synchronized int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        return null; 
    }

    public static void main(String[] args) throws InterruptedException {
        Codigo_05 solver = new Codigo_05();

        int[] nums = {2, 7, 11, 15};
        int target = 9;

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> System.out.println(Arrays.toString(solver.twoSum(nums, target))));
        executor.submit(() -> System.out.println(Arrays.toString(solver.twoSum(nums, 26))));
        executor.shutdown();
    }
}
