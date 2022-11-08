package ramiro.allende.quadruplets.sum;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class QuadrupletsSumChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuadrupletsSumChecker.class);

    private QuadrupletsSumChecker() {
    }

    public static List<Integer[]> fourNumberSum(int[] array, int targetSum) {
        if (array.length < 4) {
            return new LinkedList<>();
        } else if (array.length == 4) {
            boolean addsUp = Arrays.stream(array)
                    .reduce(0, Integer::sum) == targetSum;
            if (addsUp) {
                return List.<Integer[]>of(Arrays.stream(array).boxed().toArray(Integer[]::new));
            }
        }

        boolean[][] subsetMap = getSubsetMap(array, targetSum);
        List<Integer[]> quadruplets = new LinkedList<>();

        // I will start drawing paths from the result with the whole set
        List<NodeTrace> nodeTraces = new LinkedList<>();
        NodeTrace nodeTrace = new NodeTrace(
                subsetMap.length - 1,
                subsetMap[0].length - 1,
                array.length - 1,
                // this was a subtotal, I switched it to targetSum - subtotal to avoid out of bounds errors
                targetSum - array[array.length - 1]
        );
        nodeTraces.add(nodeTrace);

        // I can either ignore the element or use it
        while (!nodeTraces.isEmpty()) {
            // I take the last element, I will either be updating it, forking it or processing it
            nodeTrace = nodeTraces.get(nodeTraces.size() - 1);
            nodeTraces.remove(nodeTrace);

            // If we are behind the end line for the path
            if (nodeTrace.getX() > 0) {
                if (subsetMap
                        [nodeTrace.getX() - 1]
                        [nodeTrace.getY()]
                ) {
                    List<Integer> newNumberPath = new ArrayList<>(nodeTrace.getNumbers());
                    newNumberPath.remove(newNumberPath.size() - 1);
                    newNumberPath.add(nodeTrace.getX() - 1);

                    int newSubtotal = nodeTrace.getWhatsLeft() + array[nodeTrace.getX()] - array[nodeTrace.getX() - 1];

                    nodeTraces.add(new NodeTrace(
                            nodeTrace.getX() - 1,
                            nodeTrace.getY(),
                            newNumberPath,
                            // this will the targetSum - subtotal to avoid out of bounds issues
                            newSubtotal
                    ));
                }

                int nextResult = nodeTrace.getY() - array[nodeTrace.getX()];

                // Before processing check for numbers size avoid out of bounds errors
                if (nodeTrace.getNumbers().size() < 4
                        && nextResult >= 0
                        && nextResult < subsetMap[0].length
                        && subsetMap[nodeTrace.getX() - 1][nextResult]
                ) {
                    List<Integer> newNumberPath = new ArrayList<>(nodeTrace.getNumbers());
                    newNumberPath.add(nodeTrace.getX() - 1);

                    int newSubtotal = nodeTrace.getWhatsLeft() - array[nodeTrace.getX() - 1];

                    nodeTraces.add(new NodeTrace(
                            nodeTrace.getX() - 1,
                            nextResult,
                            newNumberPath,
                            newSubtotal
                    ));
                }
            }

            if (nodeTrace.getNumbers().size() == 4 && nodeTrace.getWhatsLeft() == 0) {
                quadruplets.add(nodeTrace.getNumbers().stream().map(idx -> array[idx]).toArray(Integer[]::new));
            }
        }

        for (Integer[] quadruplet : quadruplets) {
            LOGGER.info(Arrays.toString(quadruplet));
        }

        return quadruplets;
    }

    /**
     * It will return a map of [x][y] where y is the sum and x is a member of the array.
     * [x,y] == true if y can be built with one or more members of the subset array[0..x]
     */
    public static boolean[][] getSubsetMap(int[] array, int targetSum) {
        Arrays.sort(array);

        // the offset will be the quantity of negative results, it will be applied to all the y index
        int offset = Arrays.stream(array)
                .filter(num -> num < 0)
                .reduce(0, Integer::sum) * -1;

        // results quantity will be all the positives, plus the negatives and zero
        int resultsNumber = targetSum + offset + 1;
        boolean[][] subsetMap = new boolean[array.length][resultsNumber];

        // at x == 0 (single element subset of the array), [x,y] if true if y == x
        for (int y = 0; y < resultsNumber; y++) {
            subsetMap[0][y] = y - offset == array[0];
        }

        /* after initialization, I build the map based on these premises:
            - If a subset for [x, y] is true, it will be true for any bigger value of x (there will always be a subset)
            - If array[x] == y, the value is good enough to qualify as a subset.
            - If I apply x to y by subtracting it from the total and removing it from the subset [x-1, y-array[x]],
                I have just to check if it is true for the new subset (like in a coin change problem).
            - I can ignore x and see if the subset condition has already been fulfilled for the same result [x-1, y].
         */
        for (int x = 1; x < array.length; x++) {
            for (int y = 0; y < resultsNumber; y++) {
                int offsetTarget = y - offset;
                boolean noNeedForNumber = subsetMap[x - 1][y];
                boolean singleSubset = array[x] == offsetTarget;

                if (noNeedForNumber || singleSubset) {
                    subsetMap[x][y] = true;
                } else {
                    int resultIndex = y - array[x];
                    subsetMap[x][y] = resultIndex >= 0
                            && resultIndex < resultsNumber
                            && subsetMap[x - 1][resultIndex];
                }
            }
        }

        printSubsetMap(subsetMap, array, targetSum);

        return subsetMap;
    }

    public static void printSubsetMap(boolean[][] subsetMap, int[] array, int targetSum) {

        int lowestNegative = Arrays.stream(array)
                .filter(n -> n < 0)
                .reduce(0, Integer::sum);

        StringBuilder rowBuilder = new StringBuilder("#");
        for (int i = 0; i < targetSum - lowestNegative + 1; i++) {
            rowBuilder.append('\t');
            rowBuilder.append(i + lowestNegative);
        }
        LOGGER.info(rowBuilder.toString());

        for (int x = 0; x < subsetMap.length; x++) {
            StringBuilder sb = new StringBuilder();
            sb.append(array[x]);
            for (int y = 0; y < subsetMap[0].length; y++) {
                sb.append('\t');
                sb.append(subsetMap[x][y] ? 'T' : 'F');
            }
            LOGGER.info(sb.toString());
        }
    }
}
