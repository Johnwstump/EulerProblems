import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
    For a number written in Roman numerals to be considered valid there are basic rules which must be followed.
    Even though the rules allow some numbers to be expressed in more than one way there is always a "best" way
    of writing a particular number.

    For example, it would appear that there are at least six ways of writing the number sixteen:

        IIIIIIIIIIIIIIII
        VIIIIIIIIIII
        VVIIIIII
        XIIIIII
        VVVI
        XVI

    However, according to the rules only XIIIIII and XVI are valid, and the last example is considered to be the
    most efficient, as it uses the least number of numerals.

    The 11K text file, roman.txt (right click and 'Save Link/Target As...'), contains one thousand numbers written
    in valid, but not necessarily minimal, Roman numerals.

    Find the number of characters saved by writing each of these in their minimal form.

    Note: You can assume that all the Roman numerals in the file contain no more than four consecutive
    identical units.
**/
@SuppressWarnings("Duplicates")
public class Euler89 {

    /**
     * A hashmap of roman numerals onto their values
     */
    private static HashMap<Character, Integer> numeralValues = null;
    /**
     * A treemap of values onto the corresponding roman numeral
     */
    private static TreeMap<Integer, String> numerals;

    /* Solution for Project Euler Problem 76 */
    public static void main(String args[]){
        System.out.printf("Difference is: %d\n", simplifyNumerals("input\\p089_roman.txt"));
    }

    /**
     * Reads in a list of newline delimited roman numerals from FILE and returns the sum of the difference,
     * in number of characters, between the current numerals and the most parsimonious numeral representation
     * of those numbers.
     * @param fileName - A file containing a newline delimited list of roman numerals
     * @return The sum of the difference in characters between the current numeral representations and the simplest
     * numeral representations
     */
    private static int simplifyNumerals(String fileName){
        List<String> input = readNumerals(new File(fileName));

        int difference = 0;

        String simpleNumeral;

        for (String numeral : input){
            simpleNumeral = shortToRoman(romanToShort(numeral));
            difference += numeral.length() - simpleNumeral.length();
        }

        return difference;
    }

    /**
     * Instantiates and fills the value - to - roman numeral map, NUMERALS
     */
    private static void buildNumeralsMap(){
        if (numerals != null){
            return;
        }

        numerals = new TreeMap<>();
        numerals.put(1, "I");
        numerals.put(4, "IV");
        numerals.put(5, "V");
        numerals.put(9, "IX");
        numerals.put(10, "X");
        numerals.put(40, "XL");
        numerals.put(50, "L");
        numerals.put(90, "XC");
        numerals.put(100, "C");
        numerals.put(400, "CD");
        numerals.put(500, "D");
        numerals.put(900, "CM");
        numerals.put(1000, "M");
    }

    /**
     * Reads in a newline-delimited list of roman numerals from FILE
     * @param file A file containing a newline-delimited list of roman numerals
     * @return A List of strings representing the read-in numerals
     */
    private static List<String> readNumerals(File file){
        ArrayList<String> input = new ArrayList<String>();;
        Scanner scan = null;

        try {
            scan = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("Encrypted file not found");
            System.exit(1);
        }

        while (scan.hasNext()) {
            input.add(scan.nextLine().trim());
        }

        return input;
    }

    /**
     * Converts the given short to the simplest roman numeral representation
     * @param num - The number to be converted
     * @return A roman numeral representation of NUM
     */
    protected static String shortToRoman(short num) {
        buildNumeralsMap();

        StringBuilder numeral = new StringBuilder();
        int digit;
        while (num != 0){
            digit = numerals.floorKey((int)num);
            numeral.append(numerals.get(digit));
            num -= digit;
        }

        return numeral.toString();
    }

    /**
     * Converts the provided roman numeral to a short
     * @param numeral The numeral to be converted
     * @return A short containing the value of NUMERAL
     */
    protected static short romanToShort(String numeral) {
        buildNumeralValueMap();

        int currNum = 0;
        short total = 0;
        for (int i = 0; i < numeral.length(); i++){
            currNum = numeralValues.get(numeral.charAt(i));
            if (i + 1 < numeral.length() && numeralValues.get(numeral.charAt(i + 1)) > currNum)
                total -= currNum;
            else
                total += currNum;
        }
        return total;
    }

    /**
     * Instantiates and fills the numeral - to - value map.
     */
    private static void buildNumeralValueMap(){

        if (numeralValues != null){
            return;
        }

        numeralValues = new HashMap<>();
        numeralValues.put('I', 1);
        numeralValues.put('V', 5);
        numeralValues.put('X', 10);
        numeralValues.put('L', 50);
        numeralValues.put('C', 100);
        numeralValues.put('D', 500);
        numeralValues.put('M', 1000);
    }

}
