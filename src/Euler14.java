import java.util.Stack;

/*
    The following iterative sequence is defined for the set of positive integers:

    n → n/2 (n is even)
    n → 3n + 1 (n is odd)

    Using the rule above and starting with 13, we generate the following sequence:
    13 → 40 → 20 → 10 → 5 → 16 → 8 → 4 → 2 → 1

    It can be seen that this sequence (starting at 13 and finishing at 1) contains 10 terms. Although it has not been proved yet (Collatz Problem), it is thought that all starting numbers finish at 1.

    Which starting number, under one million, produces the longest chain?
 */
public class Euler14 {

    /**
     * Finds and prints length of the longest Collatz Chain with
     * starting value below BELOW
     * @param below - The number below which all positive starting
     *              values will be searched for the longest chain.
     */
    private static void findLongestChain(int below){
        long max = 0;
        int maxStartingVal = 0;
        long currLength;

        for (int i = 1; i < below; i++){
            currLength = findCollatzChainLength(i);
            if (currLength > max){
                max = currLength;
                maxStartingVal = i;
            }
        }
        System.out.printf("%d : %d \n", maxStartingVal, max);
    }

    /**
     * Generates a collatz chain with starting value STARTINGNUM
     * @param startingNum - The starting value for this collatz chain
     * @return A stack containing a Collatz chain
     */
    private static Stack<Long> generateCollatzChain(int startingNum){
        Stack<Long> collatzChain = new Stack<Long>();
        long currNum = startingNum;

        collatzChain.push(currNum);
        while (currNum != 1){
            if (currNum % 2 == 0)
                currNum = currNum / 2;
            else
                currNum = currNum * 3 + 1;

            collatzChain.push(currNum);
        }

        return collatzChain;
    }

    /**
     * Finds the length of a collatz chain with starting value STARTINGNUM
     * @param startingNum - The starting value for the collatz chain to be measured
     * @return The size of the collatz chain with starting value STARTINGNUM
     */
    private static long findCollatzChainLength(int startingNum){
        long size = 1;
        long currNum = startingNum;

        while (currNum > 1) {
            if (currNum % 2 == 0)
                currNum = currNum / 2;
            else
                currNum = currNum * 3 + 1;
            size++;
        }

        return size;
    }

    /*
    Test for Project Euler Problem 14
     */
    public static void main(String args[]){
        findLongestChain(1000000);
    }
}
