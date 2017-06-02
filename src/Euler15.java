import java.math.BigInteger;

/*
    Problem Description:
    Starting in the top left corner of a 2×2 grid, and only being able to move to the right and down,
    there are exactly 6 routes to the bottom right corner.
    How many such routes are there through a 20×20 grid?
 */
public class Euler15 {

    /**
     * Counts the number of paths from the
     * upper left to the bottom right in a square
     * grid of size SIZE.
     * @return The number of paths.
     */
    private static int countPaths(int size){
        return traverseGrid(0, 0, size);
    }

    /**
     * This method uses a brute-force approach to determining
     * the number of paths through a square grid of size
     * SIZE, where each path only consists of down and right
     * movements and the destination is the bottom right corner.
     *
     * This algorithm runs in O(2^SIZE) time, and so is not
     * viable for solving large grids
     * @param size - The size (width and height) of the grid being travrsed
     * @param row - The current row position
     * @param col - The current column position
     * @return - The number of paths through the grid
     */
    private static int traverseGrid(int row, int col, int size){

        if (row > size || col > size)
            return 0;

        if (row == size && col == size)
            return 1;

        return traverseGrid(row + 1, col, size) + traverseGrid(row, col + 1, size);
    }

    /**
     * Adapts a simplified N choose K algorithm to
     * return the number of paths from the upper left to
     * the bottom right through a square grid of
     * width/height SIZE.
     * @param size - The size (width and height) of the grid being traversed
     * @return - The number of paths through the grid
     */
    private static BigInteger numPathsThroughGrid(int size){

        // N choose K is simplified here because n = 2(size) and
        // K = size.
        // So instead of (2(size))! / ((size)!(2(size) - (size))!
        // We can simplify to (2(size))! / size!(size!)

        BigInteger n = BigInteger.valueOf(size + 1);
        for (int i = size + 2; i <= size * 2; i++){
            n = n.multiply(BigInteger.valueOf(i));
        }

        BigInteger d = BigInteger.ONE;
        for (int i = 1; i <= size; i++){
            d = d.multiply(BigInteger.valueOf(i));
        }

        // return n / d
        return n.divide(d);
    }

    /*
      Test for Project Euler Problem 15
     */
    public static void main(String[] args){
        System.out.printf("5 x 5 has %d paths\n", countPaths(5));
        System.out.printf("20 x 20 has %s paths\n", numPathsThroughGrid(20).toString());
    }

}
