import java.util.Arrays;

public class Solution {
    public int removeCoveredIntervals(int[][] intervals) {
        // 1. Sort the intervals
        // If start points are different, sort by start point ascending.
        // If start points are equal, sort by end point descending.
        Arrays.sort(intervals, (a, b) -> {
            if (a[0] != b[0]) {
                return Integer.compare(a[0], b[0]); // ascending by start
            } else {
                return Integer.compare(b[1], a[1]); // descending by end
            }
        });

        int removedCount = 0;
        int maxEnd = 0;

        // 2. Single pass to find covered intervals
        for (int[] interval : intervals) {
            int currentEnd = interval[1];

            // If the current end fits inside our biggest maxEnd seen so far
            if (currentEnd <= maxEnd) {
                removedCount++; // It's completely covered!
            } else {
                maxEnd = currentEnd; // Update the boundary
            }
        }

        // 3. Remaining intervals = Total - Removed
        return intervals.length - removedCount;
    }
}