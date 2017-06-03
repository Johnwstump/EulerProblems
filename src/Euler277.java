import java.security.InvalidAlgorithmParameterException;

/*
    A modified Collatz sequence of integers is obtained from a starting value a1 in the following way:

    an+1 = an/3 if an is divisible by 3. We shall denote this as a large downward step, "D".

    an+1 = (4an + 2)/3 if an divided by 3 gives a remainder of 1. We shall denote this as an upward step, "U".

    an+1 = (2an - 1)/3 if an divided by 3 gives a remainder of 2. We shall denote this as a small downward step, "d".

    The sequence terminates when some an = 1.

    Given any integer, we can list out the sequence of steps.
    For instance if a1=231, then the sequence {an}={231,77,51,17,11,7,10,14,9,3,1} corresponds to the steps
    "DdDddUUdDD".

    Of course, there are other sequences that begin with that same sequence "DdDddUUdDD....".
    For instance, if a1=1004064, then the sequence is DdDddUUdDDDdUDUUUdDdUUDDDUdDD.
    In fact, 1004064 is the smallest possible a1 > 106 that begins with the sequence DdDddUUdDD.

    What is the smallest a1 > 1015 that begins with the sequence "UDDDUdddDDUDDddDdDddDDUDDdUUDd"?

 */
public class Euler277 {

    /* Solution for Euler problem 277 */
    public static void main(String[] args){
        String subsequence = "UDDDUdddDDUDDddDdDddDDUDDdUUDd";
        try {
            System.out.printf("Solution: %d\n", findMatchingCollatzSequence(subsequence, (long) Math.pow(10, 15)));
            System.exit(0);
        }
        catch (Exception e){
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }

    /**
     * Finds the first value greater than GREATERTHAN which has SUBSEQUENCE as the first part of its
     * modified modified Collatz sequence.
     * @param subSequence - The subsequence to be matched
     * @param greaterThan - The Power-of-10 from which to start searching for subsequence matches
     * @return The values greater than GREATERTHAN which matches SUBSEQUENCE
     * @throws InvalidAlgorithmParameterException
     */
    public static Long findMatchingCollatzSequence(String subSequence, long greaterThan)
            throws InvalidAlgorithmParameterException{
        double epsilon = .000000001;
        if (greaterThan <= 0
                || Math.log10(greaterThan) - (long)Math.log10(greaterThan) > epsilon){
            throw new InvalidAlgorithmParameterException("GreaterThan value must be a power of 10 and >= 1");
        }
        boolean foundMatch = true;

        if (subSequence.charAt(0) == 'D'){
            greaterThan += 2;
        }
        else if (subSequence.charAt(0) == 'd'){
            greaterThan += 1;
        }
        long val;
        char nextChar = 'A';

        // The current step between values
        long step = 3;
        // The index of the subsequence we're currently trying to find a pattern for
        int patternIndex = 2;
        // The previous value which matched the subsequence to patternIndex places
        long prev = -1;

        /*
            Simply, we iterate over all possible values greater than GREATERTHAN
            and analyze the difference between the first two values which match the
            subsequence to a given length (PATTERNINDEX). We then use that difference as the new step
            value and attempt to find the pattern (difference) between values which match
            the subsequence at an even longer length.
         */
        for (long i = (long)greaterThan; i < Long.MAX_VALUE; i += step){
            foundMatch = true;
            val = i;
            for (int j = 0; j < subSequence.length(); j++){
                // Determine the next value and character in the sequence
                switch ((int)(val % 3)){
                    case 0:
                        val = val / 3;
                        nextChar = 'D';
                        break;
                    case 1:
                        val = ((4 * val + 2) / 3);
                        nextChar = 'U';
                        break;
                    case 2:
                        val = ((2 * val - 1) / 3);
                        nextChar = 'd';
                        break;
                    default:
                        // Should never be reached
                        System.err.print("Input error");
                        System.exit(-1);
                }

                // If this number matches the subsequence to PATTERNINDEX length
                if (j == patternIndex){
                    // If a value has previously been found which matched subsequence to PATTERNINDEX length
                    if (prev != -1) {
                        // Update the step based on the difference
                        step = i - prev;
                        patternIndex++;
                        prev = -1;
                    }
                    // Otherwise, this value is the first one found at this length
                    else{
                        prev = i;
                    }
                }

                // If this value does not match the subsequence at a given point
                if (nextChar != subSequence.charAt(j)){
                    // Discard this value
                    foundMatch = false;
                    break;
                }
            }
            if (foundMatch){
                return i;
            }
        }

        return -1L;
    }

}
