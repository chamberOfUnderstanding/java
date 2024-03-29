package stack;

import java.util.Stack;

/**
 * @author 47un
 * 
 * Find the largest rectangular area possible in a given histogram where the largest rectangle can be made of a number of contiguous bars.
 * For simplicity, assume that all bars have same width and the width is 1 unit.
 * For example, for the histogram with 7 bars of heights {6, 2, 5, 4, 5, 1, 6}. The largest rectangle possible is 12.
 * 5, 4, 5 each have 4 => 4 * 3 => 12
 * 
 * http://www.geeksforgeeks.org/largest-rectangle-under-histogram/
 *
 * ================
 * METHOD 1 : Stack
 * ================
 * 
 * For each bar
 *  If stack is empty or the current bar is taller than stack top push it.
 *  If the current bar is shorter, then we need to find the region with max area covered by all the histograms in stack till now.
 *    Since a shorter bar has been seen, a new area calculation session has to begin within the stack, that starts with this bar.
 *    To calculate the area, pop the stack. The larger areas come into existence as the stack gets emptied. 
 *    e.g. : 6,8,2. When 2 is seen, stack holds indices to 6 and 8 i.e. [0,1]. 
 *           So top will be 1 and first area calculated will be : 8*(2-1) = 8. 
 *           Now top is 0 and second area will be : 6*(2-0) = 12 (Max area)
 *    Update max area after area calculation
 *     
 * NOTE: i is not incremented when a shorter is seen, since the area needs to be calculated for ALL the histograms in the stack w.r.t the current short i
 * If all elements have been processed and the stack is not empty, empty it by doing the same thing that's done when a new shorter is seen
 * 
 * TIME  : O(n)
 * SPACE : O(n)
 */

public class Histogram {

	public static void main(String[] args) {
		int bars = 7;
		int[] histogram1 = {6, 2, 5, 4, 5, 1, 6};
		int[] histogram2 = {6, 2, 5, 4, 5, 2, 6};
		System.out.println(findMaxArea(bars, histogram1));
		System.out.println(findMaxArea(bars, histogram2));
	}

	public static int findMaxArea(int bars, int[] histogram){		
		// Stack stores the histograms in increasing order of height
		Stack<Integer> stack = new Stack<>();
		int maxArea = Integer.MIN_VALUE;
		int i = 0;
		while(i < bars)
			// If stack is empty or the current bar is taller than stack top push it.
			if(stack.isEmpty() || histogram[i] >= histogram[stack.peek()]) {
				stack.push(i);
				i++;
			}
		        // If the current bar is shorter, then we need to find the region with max area covered by all the histograms in stack till now.
			else {
				while(!stack.isEmpty() && histogram[i] < histogram[stack.peek()] ) {
					maxArea = Math.max(maxArea, calculateArea(i, histogram, stack));	
				}
				// Since a shorter bar has been seen, a new area calculation session has to begin within the stack, that starts with this bar.
			}
		while(!stack.isEmpty())
			maxArea = Math.max(maxArea, calculateArea(i, histogram, stack));
		return maxArea;
	}

	// To calculate the area, pop the stack. The larger areas come into existence as the stack gets emptied. 
        // e.g. : 6,8,2. When 2 is seen, stack holds indices to 6 and 8 i.e. [0,1]. 
 	// So top will be 1 and first area calculated will be : 8*(2-1) = 8. 
 	// Now top is 0 and second area will be : 6*(2-0) = 12 (Max area)
	// if stack is empty, then top value * where it was encountered (since the width is 1 for all)
	// else top value * number of items between the current item and the next one on top of the stack
	public static int calculateArea(int i, int[] histogram, Stack<Integer> stack){
		int lastItem = histogram[stack.pop()];
		return lastItem * (stack.isEmpty()? i : i - stack.peek() - 1);
	}
}
