/*
    Problem:

    If the numbers 1 to 5 are written out in words: one, two, three, four, five,
    then there are 3 + 3 + 5 + 4 + 4 = 19 letters used in total.

    If all the numbers from 1 to 1000 (one thousand) inclusive were written out in
    words, how many letters would be used?
 */
public class Euler17 {

    private static final String[] ONES = {"one", "two", "three", "four", "five", "six",
            "seven", "eight", "nine"};
    private static final String[] TENS = {"ten", "twenty", "thirty", "forty", "fifty",
            "sixty", "seventy", "eighty", "ninety"};
    private static final String[] TEENS = {"eleven", "twelve", "thirteen", "fourteen",
            "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};

    /**
     * Converts a given integer to a string representation in the British style
     * i.e 1564 == One thousand five hundred and sixty four
     * @param number The number to be converted. Must be less than 9999
     * @return A string representation of NUMBER
     */
    private static String numberAsWord(int number){
        StringBuilder num = new StringBuilder();
        hundredsToWord(number / 1000000000, num);
        if (num.length() != 0)
            num.append(" Billion");
        number = number % 1000000000;
        hundredsToWord(number / 1000000, num);
        if (num.length() != 0)
            num.append(" Million");
        number = number % 1000000;
        hundredsToWord(number / 1000, num);
        if (num.length() != 0)
            num.append(" Thousand");
        number = number % 1000;
        hundredsToWord(number, num);
        return num.toString();
    }

    /**
     *
     * A helper method which converts a number < 1000 into a string
     * representation of that number in the British style.
     * @param number - The number (< 1000) to be converted
     * @param num - The StringBuilder currently being used to assemble a number-as-word.
     */
    private static void hundredsToWord(int number, StringBuilder num){
        if (number == 0)
            return;

        int hundreds = number / 100;
        number = number % 100;
        int tens = number / 10;
        int ones = number % 10;

        if (hundreds != 0) {
            if (num.length() != 0)
                num.append(" ");
            num.append(ONES[hundreds - 1] + " Hundred");
        }

        if (hundreds != 0 && (tens != 0 || number != 0))
            num.append(" and");

        if (tens > 1){
            if (num.length() != 0)
                num.append(" ");
            num.append(TENS[tens - 1]);
        }

        if (tens == 1){
            if (num.length() != 0)
                num.append(" ");
            if (ones == 0)
                num.append(TENS[0]);
            else
                num.append(TEENS[ones - 1]);
        }
        else{
            if (ones != 0){
                if (num.length() != 0)
                    num.append(" ");
                num.append(ONES[ones - 1]);
            }
        }

    }

    /**
     * Counts the non-whitespace characters in the word representation of all numbers
     * between start and end. Numbers are presented as words in the British style
     * i.e 1564 == One thousand five hundred and sixty four
     * @param start The start of the range of numbers to count
     * @param end The end of the range of numbers to count
     * @return The total number of non-whitespace letters
     */
    private static long countLettersInRange(int start, int end){
        long letters = 0;
        for (int i = start; i <= end; i++){
            letters += numberAsWord(i).replace(" ", "").length();
        }
        return letters;
    }

    /*

    Solution to Project Euler Problem 17
     */
    public static void main(String[] args){
        System.out.println(countLettersInRange(1, 1000));
    }

}
