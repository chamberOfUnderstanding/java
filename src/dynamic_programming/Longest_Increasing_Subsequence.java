package dynamic_programming;

import java.util.Arrays;

/**
 * @author 47un
 * 
 * The Longest Increasing Subsequence (LIS) problem is to find the length of the longest subsequence of a given sequence,
 * such that all elements of the subsequence are sorted in increasing order.
 * For example, the length of LIS for {10, 22, 9, 33, 21, 50, 41, 60, 80} is 6 and LIS is {10, 22, 33, 50, 60, 80}.
 * 
 * http://www.geeksforgeeks.org/dynamic-programming-set-3-longest-increasing-subsequence/
 * https://www.youtube.com/watch?v=odrfUCS9sQk
 *
 * METHOD 1 : Initialize LIS to 1 for all items. This is the base case as all items are a part of
 *             the LIS containing just that item
 *            For each item i
 *             Scan rest of the items j = i + 1 to last item
 *              If j's value is greater than i, then j's LIS can be updated
 *               Prefer the largest among LIS j and LIS of i + 1 for the update
 *            Find the largest among the LISs and return it
 *            
 *            To print the LIS, during update of j's LIS, add i as j's parent
 *            Follow the parents till a -1 is seen and print them
 *
 * TIME     : O(n^2)
 * SPACE    : O(n)
 *
 * 
 */

public class Longest_Increasing_Subsequence {

    public static void main(String[] args) {
        int[] array = {50, 3, 10, 7, 40, 80};
        System.out.println(findLIS(array));
        System.out.println(findAndPrintLIS(array));
    }

    private static int findLIS(int[] array) {
        int[] lis = new int[array.length];
        
        // all items by themselves are part of a lis of size 1
        Arrays.fill(lis, 1);
        
        for(int i = 0; i < array.length; i++)
            // i + 1 because i can only influence the items on its RIGHT when looking for an increasing sequence
            for(int j = i + 1; j < array.length; j++)
                // j can be added to the sequnce if a[j] > a[i]
                if(array[j] > array[i])
                    // here lis[i] would be the sequences formed by other elements before i
                    lis[j] = Math.max(lis[j], lis[i] + 1);
        
        // find longest
        int longestSequence = lis[0];
        for(int i = 1; i < array.length; i++)
            longestSequence = Math.max(longestSequence, lis[i]);
        
        return longestSequence;
    }

    private static int findAndPrintLIS(int[] array) {
        int[] lis = new int[array.length];
        int[] parent = new int[array.length];
        Arrays.fill(lis, 1);
        Arrays.fill(parent, -1);
        for(int i = 0; i < array.length; i++)
            for(int j = i+1; j < array.length; j++)
                if(array[j] > array[i]) {
                    lis[j] = Math.max(lis[j], lis[i] + 1);
                    // i connects to j in the sequence, so i is j's parent
                    parent[j] = i;
                }
        int longestSequence      = lis[0];
        int longestSequenceIndex = parent[0];
        for(int i = 1; i < array.length; i++)
            if(lis[i] > longestSequence) {
                longestSequence      = length[i];
                longestSequenceIndex = i;
            }
        System.out.print("LIS : ");
        while(longestSequenceIndex != -1) {
            System.out.print(array[longestSequenceIndex] + " ");
            longestSequenceIndex = parent[longestSequenceIndex];
        }
        System.out.print("\nLIS length : ");
        return longestSequence;
    }
}
