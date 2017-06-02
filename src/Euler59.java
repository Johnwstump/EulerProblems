import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

/*
    Problem Description:

    Each character on a computer is assigned a unique code and the preferred standard is ASCII
    (American Standard Code for Information Interchange). For example, uppercase A = 65, asterisk (*)
    = 42, and lowercase k = 107.

    A modern encryption method is to take a text file, convert the bytes to ASCII, then XOR each byte
    with a given value, taken from a secret key. The advantage with the XOR function is that using the same
    encryption key on the cipher text, restores the plain text; for example, 65 XOR 42 = 107, then 107 XOR 42 = 65.

    For unbreakable encryption, the key is the same length as the plain text message, and the key is made up of
    random bytes. The user would keep the encrypted message and the encryption key in different locations, and without
    both "halves", it is impossible to decrypt the message.

    Unfortunately, this method is impractical for most users, so the modified method is to use a password as a key.
    If the password is shorter than the message, which is likely, the key is repeated cyclically throughout the
    message. The balance for this method is using a sufficiently long password key for security, but short enough
    to be memorable.

    Your task has been made easy, as the encryption key consists of three lower case characters.
    Using cipher.txt (right click and 'Save Link/Target As...'), a file containing the encrypted ASCII codes,
    and the knowledge that the plain text must contain common English words, decrypt the message and find the
    sum of the ASCII values in the original text.
 */
public class Euler59 {

    private static TreeSet<String> dictionary = null;
    private static String decryptedMessage = null;
    private static int decryptedValuesCount = 0;

    /* Tests for Project Euler Problem 59 */
    public static void main(String[] args){
        loadDictionary(new File("input\\common_words.txt"));
        int[] inputValues = readEncryptedFile(new File("input\\p059_cipher.txt"), ",");
        if (likelyKeyDecryptMessage(inputValues, 3)
               /* || bruteForceDecryptMessage(inputValues, 3) */){
            printWithWrap(decryptedMessage, 80);
            // Answer for Euler Problem 59
            System.out.printf("Sum of Ascii Values: %d\n", decryptedValuesCount);
        }

        // Compare run times for different methods of decryption
        // Run time for decryption by likely key (Considering character use and space frequency)
        long startTime = System.nanoTime();
        likelyKeyDecryptMessage(inputValues, 3);
        long endTime = System.nanoTime();
        long likelyDecryptRuntime = (endTime - startTime) / 1000000;

        // Run time for decryption by brute force
        startTime = System.nanoTime();
        bruteForceDecryptMessage(inputValues, 3);
        endTime = System.nanoTime();
        long bruteForceDecryptRuntime = (endTime - startTime) / 1000000;

        System.out.printf("Brute Force Method: %dms\nLikely Character Method: %dms\n",
                bruteForceDecryptRuntime, likelyDecryptRuntime);
    }


    /**
     * Prints a given string where each line is as close as possible to
     * WRAP length while still being broken correctly on a space.
     * @param string - The string to be wrapped and printed.
     * @param wrap - The maximum length of each line
     */
    private static void printWithWrap(String string, int wrap){
        int startIndex = 0;
        int wrapIndex = 0;
        while (startIndex + wrap < string.length()){
            wrapIndex = string.lastIndexOf(' ', startIndex + wrap);
            System.out.println(string.substring(startIndex, wrapIndex));
            startIndex = wrapIndex + 1;
        }
        System.out.println(string.substring(startIndex, string.length()) + "\n");
    }

    /**
     * Loads a dictionary of words to be used when determining whether a given encrypted
     * message has been decoded. Assumes all words are newline delimited
     * @param dictionaryFile - The file containing the word dictionary to be loaded
     */
    private static void loadDictionary(File dictionaryFile){
        loadDictionary(dictionaryFile, System.getProperty("line.separator"));
    }

    /**
     * Loads a dictionary of words to be used when determining whether a given encrypted
     * message has been decoded.
     * @param dictionaryFile - The file containing the word dictionary to be loaded
     * @param delimiter - The delimiter to be used when parsing tokens
     */
    private static void loadDictionary(File dictionaryFile, String delimiter){
        dictionary = new TreeSet<String>();

        Scanner scan = null;

        try {
            scan = new Scanner(dictionaryFile);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("Dictionary file not found");
            System.exit(1);
        }

        scan.useDelimiter(delimiter);

        while (scan.hasNext())
            dictionary.add(scan.next().trim());
    }

    /**
     * Reads in an encrypted file from INPUT using DELIMITER as an array
     * of encrypted integers
     * @param input - The file to be read
     * @param delimiter - The delimiter to use when reading the file
     * @return An int[] where each int is an encrypted character
     */
    private static int[] readEncryptedFile(File input, String delimiter){
        ArrayList<Integer> in = new ArrayList<Integer>();;
        Scanner scan = null;

        try {
            scan = new Scanner(input);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("Encrypted file not found");
            System.exit(1);
        }

        scan.useDelimiter(delimiter);

        while (scan.hasNext())
            in.add(Integer.parseInt(scan.next().trim()));

        int[] out = new int[in.size()];
        int j = 0;
        for (Integer i : in)
            out[j++] = i;

        return out;
    }

    /**
     * Attempts to decrypt a given message presented as an array of integer
     * values with a known key length.
     * @param input - An int[] representing the encrypted characters in the message
     * @param keyLength - The length of the key used to encrypt this message
     * @return Whether a probable decryption was found.
     */
    private static boolean bruteForceDecryptMessage(int[] input, int keyLength){
        return testAllKeys(keyLength, new int[keyLength], input);
    }

    /**
     * This function is used by the brute force method to try all possible keys
     * of length KEYLENGTH.
     * @param keyLength - The length of the key to try.
     * @param key - The key currently being examined/constructed.
     * @param input - The input to be decoded
     * @return Whether a probable decryption was found.
     */
    private static boolean testAllKeys(int keyLength, int[] key, int[] input){
        if(keyLength == 0)
            return (testMessage(decrypt(input, key)));

        for (int i = 'a'; i <= 'z'; i++){
            key[keyLength - 1] = i;
            if (testAllKeys(keyLength-1, key, input)){
                return true;
            }
        }
        return false;
    }

    /**
     * This method attempts to determine whether MESSAGE represents a valid English sentence
     * by looking for certain characteristics related to space frequency, unusual character frequency.
     * and the percentage of words which we can find in a preloaded dictionary.
     * @param message - The potential message to be analyzed
     * @return - True if this is a valid message
     */
    private static boolean testMessage(int[] message){
        StringBuilder messageAsWords = new StringBuilder();

        // The sum of the decoded ascii values - required for problem solution
        int sum = 0;
        // The number of spaces seen
        int spaces = 0;
        // The number of non-letter or space characters seen
        // in a row
        int notLetterOrSpace = 0;

        for (int l: message) {
            sum += l;

            // Exclude unusual ASCII values
            if ((l > 5 && l < 32) || l > 126)
               return false;

            // We count the number of spaces for later comparison
            if (l == 32)
               spaces++;

            // Exclude if there is a run of more than 4
            // characters which are not letters, numbers,
            // or spaces
            if (notLetterOrSpace > 4)
                return false;
            if (!Character.isLetterOrDigit(l) && l != 32)
                notLetterOrSpace++;
            else
                notLetterOrSpace = 0;

            // If previous tests are passed, we continue building the message
            messageAsWords.append((char) l);
        }

        // If this message does not have at least one space for every 15 characters, we
        // exclude it
        if (spaces < message.length / 15)
           return false;

        int wordsRecognized = 0;

        // We count the number of words in the potential message which we can find in our dictionary
        for (String word: messageAsWords.toString().split(" ")){
            if (dictionary.contains(word))
                wordsRecognized++;
        }

        // We expect to find at least one word in our dictionary for every 20 characters.
        if (wordsRecognized >= message.length / 20) {
            decryptedMessage = messageAsWords.toString();
            decryptedValuesCount = sum;
            return true;
        }

        return false;
    }

    /**
     * Decrypts INPUT using KEY and an XOR encryption/decryption method
     * @param input - The input to be decrypted
     * @param key - The key to use when decrypting
     * @return An integer array containing the decrypted int values
     */
    private static int[] decrypt(int[] input, int[] key){
        int[] output = new int[input.length];

        int j = 0;

        // Use key and bitwise XOR to decrypt mesage
        for (int i = 0; i < input.length; i++){
            output[i] = input[i] ^ key[j % key.length];
            j++;
        }

        return output;
    }

    /**
     * Determines whether a given character is likely to be part of a properly decrypted message
     * @param c The character being examined
     * @return Whether this character is likely part of a properly decrypted message
     */
    private static boolean isProbableCharacter(char c){
        return ((c >= 32 || c < 5) && c < 126);
    }

    /**
     * Recursively tests all permutations of probable keys. Returns true if probable match is found.
     * @param keyPossibilities - A list of lists of possible keys for each column position
     * @param input - The input values
     * @param key - The current state of the key
     * @param currIndex - The current index in the key
     * @return Whether a probable match was found
     */
    private static boolean testLikelyKeys(List<Integer>[] keyPossibilities, int[] input, int[] key, int currIndex){
        if(currIndex == -1)
            return (testMessage(decrypt(input, key)));

        for (int i = 0; i < keyPossibilities[currIndex].size(); i++){
            key[currIndex] = keyPossibilities[currIndex].get(i);
            if (testLikelyKeys(keyPossibilities, input, key, currIndex - 1)){
                return true;
            }
        }
        return false;
    }

    /**
     * Attempts to decrypt a given INPUT which was encrypted using a bitwise XOR method
     * and a key of size KEYLENGTH by identifying individual probable keys based on the
     * frequency and nature of the characters generated, then testing all permutations
     * of those possible keys until a probable message is found.
     * @param input - An integer array of values to be decryped
     * @param keyLength - The size of the key used to encrypt this message
     * @return - Whether a probable decryption was found
     */
    private static boolean likelyKeyDecryptMessage(int[] input, int keyLength) {

        ArrayList<Integer>[] keys = new ArrayList[keyLength];
        ArrayList<Integer>[] inputColumns = new ArrayList[keyLength];

        // Initialize arrays
        for (int i = 0; i < keyLength; i++) {
            keys[i] = new ArrayList<Integer>();
            inputColumns[i] = new ArrayList<Integer>();
        }

        // Brake the encrypted message into KEYLENGTH columns
        for (int i = 0; i < input.length; i++){
            inputColumns[i % keyLength].add(input[i]);
        }

        // Whether the given key is a probable match for the given
        // column
        boolean probable;

        // For every character which could be a key
        for (char c = 'a'; c <= 'z'; c++) {
            // For each of the columns
            for (int i = 0; i < keyLength; i++){
                probable = true;
                // Evaluate the column
                for (int curr : inputColumns[i]){
                    if (!isProbableCharacter((char)(curr ^ c)))
                        probable = false;
                }
                if (probable)
                    keys[i].add((int)c);
            }
        }

        // If we could find no probable key for any column, this method
        // was unsuccessful
        for (int i = 0; i < keyLength; i++){
            if (keys[i].size() == 0)
                return false;
        }

        return testLikelyKeys(keys, input, new int[keyLength], keyLength - 1);
    }
}
