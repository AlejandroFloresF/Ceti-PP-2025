import java.util.*;
//Weak Consistency sin sincronización
public class Codigo_06 {
    private final Map<Integer, Integer> map = new HashMap<>();

    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        return null;
    }

    public static void main(String[] args) {
        Codigo_06 solver = new Codigo_06();

        Runnable task = () -> {
            int[] result = solver.twoSum(new int[]{2, 7, 11, 15}, 9);
            System.out.println(Arrays.toString(result));
        };

        new Thread(task).start();
        new Thread(task).start();
    }
}

