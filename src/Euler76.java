/*
    Problem Description:

    It is possible to write five as a sum in exactly six different ways:

    4 + 1
    3 + 2
    3 + 1 + 1
    2 + 2 + 1
    2 + 1 + 1 + 1
    1 + 1 + 1 + 1 + 1

    How many different ways can one hundred be written as a sum of at least two positive integers?
 */
public class Euler76 {

    /* Tests for Project Euler Problem 76 */
    public static void main(String[] args){
        System.out.println(countSummations(100));
    }

    /**
     * A simple backtracking algorithm to count the number of ways to sum to TARGET
     * @param target - The target being summed to
     * @return A long representing the number of ways to sum to TARGET
     */
    private static long countSummations(int target) {
        if (target == 1){
            return 1;
        }

        return countSummations(target, 0, target - 1);
    }

    /**
     * A simple backtracking algorithm to count the number of ways to sum to TARGET
     * @param target - The target being summed to
     * @param currSum - The sum currently reached
     * @param lastVal - The last value added to the sum
     * @return A long representing the number of ways to sum to TARGET
     */
    private static long countSummations(int target, int currSum, int lastVal){
        if (currSum == target){
            return 1;
        }

        long numSummations = 0;

        for (int i =  1; i <= lastVal; i++){
            if (i + currSum > target){
                return numSummations;
            }
            currSum += i;
            numSummations += countSummations(target, currSum, i);
            currSum -= i;
        }
        return numSummations;
    }
}
