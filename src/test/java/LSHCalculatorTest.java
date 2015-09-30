import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.util.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class LSHCalculatorTest {


    @Test
    public void test() {
        double[][] matrixData = new double[6][4];
        matrixData[0] = new double[]{0,1,1,0};
        matrixData[1] = new double[]{1,0,1,1};
        matrixData[2] = new double[]{0,1,0,1};
        matrixData[3] = new double[]{0,0,1,0};
        matrixData[4] = new double[]{1,0,1,0};
        matrixData[5] = new double[]{0,1,0,0};
        RealMatrix matrix = MatrixUtils.createRealMatrix(matrixData);

        int[] permutation = new int[] {3,5,0,2,4,1};

        LSHCalculator calculator = new LSHCalculator();
        Assert.assertArrayEquals(new int[] {3,3,5,3}, calculator.getMinHash(matrix, permutation));
    }

    @Test
    public void testJaccardSimialarity() {
        String first = "ABRACADABRA";
        String second = "BRICABRAC";

        LSHCalculator calculator = new LSHCalculator();
        double calculatedJaccardSimiliarity = calculator.calculateJaccardSimiliarity(first, second, 2);
        Assert.assertEquals(5.0 / 9.0, calculatedJaccardSimiliarity, 0);
    }

    @Test
    public void testBandEquality() {
        LSHCalculator calculator = new LSHCalculator();
        double[][] matrixData = new double[2][7];
        matrixData[0] = new double[]{1,2,1,1,2,5,4};
        matrixData[1] = new double[]{2,3,4,2,3,2,2};
        RealMatrix matrix = MatrixUtils.createRealMatrix(matrixData);

        List<Pair<Integer, Integer>> createdPairs = calculator.findCandidatePairsForBand(matrix);

        List<Pair<Integer, Integer>> expectedPairs = new ArrayList<>();
        Pair<Integer, Integer> pair = new Pair<>(0,3);
        Pair<Integer, Integer> secondPair = new Pair<>(1,4);
        expectedPairs.add(pair);
        expectedPairs.add(secondPair);

        Assert.assertEquals(expectedPairs, createdPairs);
    }

    @Test
    public void testFindCandidatePairs() {
        double[][] matrixData = new double[6][7];
        matrixData[0] = new double[]{1,2,1,1,2,5,4};
        matrixData[1] = new double[]{2,3,4,2,3,2,2};
        matrixData[2] = new double[]{3,1,2,3,1,3,2};
        matrixData[3] = new double[]{4,1,3,1,2,4,4};
        matrixData[4] = new double[]{5,2,5,1,1,5,1};
        matrixData[5] = new double[]{6,1,6,4,1,1,4};
        RealMatrix matrix = MatrixUtils.createRealMatrix(matrixData);

        LSHCalculator calculator = new LSHCalculator();
        List<Pair<Integer, Integer>> createdPairs = calculator.findCandidatePairs(matrix, 2);
        Set<Pair<Integer, Integer>> uniquePairs = new HashSet<>(createdPairs);

        List<Pair<Integer, Integer>> expectedPairs = new ArrayList<>();
        Pair<Integer, Integer> pair1 = new Pair<>(0, 2);
        Pair<Integer, Integer> pair2 = new Pair<>(0, 3);
        Pair<Integer, Integer> pair3 = new Pair<>(1, 4);
        Pair<Integer, Integer> pair4 = new Pair<>(0, 5);
        Pair<Integer, Integer> pair5 = new Pair<>(3, 6);

        expectedPairs.add(pair1);
        expectedPairs.add(pair2);
        expectedPairs.add(pair3);
        expectedPairs.add(pair4);
        expectedPairs.add(pair5);
        Set<Pair<Integer, Integer>> expectedUnique = new HashSet<>(expectedPairs);
        Assert.assertEquals(expectedUnique, uniquePairs);
    }
}
