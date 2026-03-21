import java.util.Arrays;
import java.util.Comparator;

public class GreedyIntervalScheduling {

    public static void main(String[] args) {
        int[] start = {1, 2, 3};
        int[] end = {2, 4, 5};
        System.out.println("Before sorting: ");
        for (int i : start) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i : end) {
            System.out.print(i + " ");
        }

        int[] schedule = greedyIntervalScheduling(start, end);
        System.out.println("\nAfter sorting: ");
        for (int i : start) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i : schedule) {
            System.out.print(i + " ");
        }

    }

     public static int[] greedyIntervalScheduling(int[] start, int[] end) {

         // Sort the intervals based on their end points
         Arrays.sort(start, end, new Comparator<int[]>() {
             @Override
             public int compare(int[] o1, int[] o2) {
                 return Integer.compare(o1[1], o2[1]);
             }
         });

         // Initialize the schedule with the first interval
         int[] schedule = {start[0]};

         // Iterate over the intervals starting from the second one
         for (int i = 1; i < start.length; i++) {
            if (end[i-1] <= start[i]) {
                // If the current interval does not conflict with the last scheduled interval, add it to the schedule
                schedule = addInterval(schedule, start[i], end[i]);
            }
         }

        return schedule;
     }

     public static int[] addInterval(int[] schedule, int s, int e) {
       int[] newSchedule = Arrays.copyOf(schedule, schedule.length + 1);
        newSchedule[schedule.length] = s;
        newSchedule[schedule.length+1] = e;

        return newSchedule;
    }
}