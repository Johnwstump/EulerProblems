import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Euler277Test {
    @Test
    void findMatchingCollatzSequence() {
        try {
            assertEquals((long)Euler277.findMatchingCollatzSequence(
                    "DdDddUUdDD", 1), 232);
            assertEquals((long)Euler277.findMatchingCollatzSequence(
                    "DdDddUUdDD", (long)Math.pow(10, 6)), 1004064 );
            assertEquals((long)Euler277.findMatchingCollatzSequence(
                    "DdDddUUdDDDdUDUUUdDdUUDDDUdDD", 1), 1004064 );
        }
        catch (Exception e){
            fail("Incorrect input validation.");
        }
    }

}