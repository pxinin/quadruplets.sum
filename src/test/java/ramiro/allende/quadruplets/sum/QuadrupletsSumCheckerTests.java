package ramiro.allende.quadruplets.sum;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class QuadrupletsSumCheckerTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuadrupletsSumCheckerTests.class);

    @Test
    public void testCheckers() {
        List<QuadrupletsTestCase> testCases = new LinkedList<>();

        testCases.add(QuadrupletsTestCase.builder()
                .target(4)
                .array(-2, -1, 1, 2, 3, 4, 5, 6, 7, 8, 9)
                .quadruplet(-2, -1, 1, 6)
                .quadruplet(-2, 1, 2, 3)
                .quadruplet(-2, -1, 2, 5)
                .quadruplet(-2, -1, 3, 4)
                .build());

        testCases.add(QuadrupletsTestCase.builder()
                .target(16)
                .array(7, 6, 4, -1, 1, 2)
                .quadruplet(7, 6, 4, -1)
                .quadruplet(7, 6, 1, 2)
                .build());

        testCases.add(QuadrupletsTestCase.builder()
                .target(10)
                .array(1, 2, 3, 4, 5, 6, 7)
                .quadruplet(1, 2, 3, 4)
                .build());

        testCases.add(QuadrupletsTestCase.builder()
                .target(0)
                .array(5, -5, -2, 2, 3, -3)
                .quadruplet(5, -5, -2, 2)
                .quadruplet(5, -5, 3, -3)
                .quadruplet(-2, 2, 3, -3)
                .build());

        testCases.add(QuadrupletsTestCase.builder()
                .target(30)
                .array(-1, 22, 18, 4, 7, 11, 2, -5, -3)
                .quadruplet(-1, 22, 7, 2)
                .quadruplet(22, 4, 7, -3)
                .quadruplet(-1, 18, 11, 2)
                .quadruplet(18, 4, 11, -3)
                .quadruplet(22, 11, 2, -5)
                .build());

        testCases.add(QuadrupletsTestCase.builder()
                .target(20)
                .array(-10, -3, -5, 2, 15, -7, 28, -6, 12, 8, 11, 5)
                .quadruplet(-5, 2, 15, 8)
                .quadruplet(-3, 2, -7, 28)
                .quadruplet(-10, -3, 28, 5)
                .quadruplet(-10, 28, -6, 8)
                .quadruplet(-7, 28, -6, 5)
                .quadruplet(-5, 2, 12, 11)
                .quadruplet(-5, 12, 8, 5)
                .build());

        testCases.add(QuadrupletsTestCase.builder()
                .target(100)
                .array(1, 2, 3, 4, 5)
                .build());

        testCases.add(QuadrupletsTestCase.builder()
                .target(5)
                .array(1, 2, 3, 4, 5, -5, 6, -6)
                .quadruplet(2, 3, 5, -5)
                .quadruplet(1, 4, 5, -5)
                .quadruplet(2, 4, 5, -6)
                .quadruplet(1, 3, -5, 6)
                .quadruplet(2, 3, 6, -6)
                .quadruplet(1, 4, 6, -6)
                .build());

        for (QuadrupletsTestCase testCase : testCases) {
            List<Integer[]> results = QuadrupletsSumChecker.fourNumberSum(testCase.getArray(), testCase.getTarget());
            testCase.getQuadruplets().forEach(quadruplet -> LOGGER.info(Arrays.toString(quadruplet)));
            assert areResultsEqual(results, testCase.getQuadruplets());
        }
    }

    private boolean areResultsEqual(List<Integer[]> result, List<Integer[]> expectedResult) {
        return resultsToSet(expectedResult).equals(resultsToSet(result));
    }

    private Set<Set<Integer>> resultsToSet(List<Integer[]> results) {
        Set<Set<Integer>> resultSet = new HashSet<>();

        for (Integer[] result : results) {
            resultSet.add(Arrays.stream(result).collect(Collectors.toSet()));
        }

        return resultSet;
    }

    private Set<Set<Integer>> resultsToSet(int[][] results) {
        Integer[][] boxed = new Integer[results.length][results[0].length];

        for (int i = 0; i < results.length; i++) {
            boxed[i] = Arrays.stream(results[i]).boxed().toArray(Integer[]::new);
        }

        return resultsToSet(Arrays.stream(boxed).collect(Collectors.toList()));
    }
}
