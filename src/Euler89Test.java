import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Euler89Test {
    @Test
    void testIntToRoman() {
        assertEquals(Euler89.shortToRoman((short)49), "XLIX");
        assertEquals(Euler89.shortToRoman((short)16), "XVI");
        assertEquals(Euler89.shortToRoman((short)19), "XIX");
    }

    @Test
    void testRomanToInt() {
        assertEquals(Euler89.romanToShort("XLIX"), 49);
        assertEquals(Euler89.romanToShort("XXXXIIIIIIIII"), 49);
        assertEquals(Euler89.romanToShort("XIIIIII"), 16);
        assertEquals(Euler89.romanToShort("XVI"), 16);
        assertEquals(Euler89.romanToShort("XIIIIIIIII"), 19);
        assertEquals(Euler89.romanToShort("XIX"), 19);
    }
}