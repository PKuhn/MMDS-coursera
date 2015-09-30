import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import java.util.List;
import java.util.Map;

public class PageRankCalculator {
    Map<Integer, List<Integer>> adjencyList;
    Integer nodeCount;

    public PageRankCalculator(Map<Integer, List<Integer>> adjencyList) {
        this.adjencyList = adjencyList;
        nodeCount = this.adjencyList.keySet().size();


    }

    public RealVector getPageRankAfterIteration(int maxIteration, double randomTeleportProp) {
        RealMatrix propMatrix = getInitialMatrix(randomTeleportProp);
        RealVector rankVector = getInitialRankVector(this.nodeCount);
        for (int iteration = 0; iteration < maxIteration; iteration++) {
            rankVector = calculateNextIteration(propMatrix, rankVector, randomTeleportProp);
        }
        return rankVector;
    }

    public List<Integer> computePageRank() {
        return null;
    }

    private RealMatrix getInitialMatrix(double teleportPropability) {
        double[][] matrixData = getJumpMatrix();
        RealMatrix rankDistances = MatrixUtils.createRealMatrix(matrixData);
        RealMatrix scaledDistances = rankDistances.scalarMultiply(1 - teleportPropability);

        return scaledDistances;
    }

    private RealVector getRandomJumpVector(double teleportPropability) {
        double[] matrixData = new double[this.nodeCount];
        for (int i = 0; i < this.nodeCount; i++) {
            matrixData[i] = teleportPropability ;
        }

        return MatrixUtils.createRealVector(matrixData);
    }


    private RealVector getInitialRankVector(int count) {
        double[] initialRankVector = new double[count];
        for (int i = 0; i < count; i++) {
            initialRankVector[i] = (double) 1 / this.nodeCount;
        }

        return new ArrayRealVector(initialRankVector);
    }

    private double[][] getJumpMatrix() {
        double[][] matrixData = new double[this.nodeCount][this.nodeCount];
        for (Map.Entry<Integer, List<Integer>> entry : adjencyList.entrySet()) {
            List<Integer> outgoingNodes = entry.getValue();
            if (outgoingNodes.size() > 0) {
                for (Integer outgoingNode : outgoingNodes) {
                    matrixData[outgoingNode][entry.getKey()] = (float) 1 / outgoingNodes.size();
                }
            } else {
                for (int i = 0; i < matrixData[0].length; i++) {
                    matrixData[i][entry.getKey()] = (float) 1 / this.nodeCount;
                }
            }

        }
        return matrixData;
    }

    private RealVector calculateNextIteration(RealMatrix matrix, RealVector currentRankVector, double teleportPropability) {
        RealVector randomJumpVector = getRandomJumpVector(teleportPropability);
        RealVector newRankVector = matrix.operate(currentRankVector);
        return newRankVector.add(randomJumpVector);
    }

    public void printGrid(double a[][]) {
        for (int i = 0; i < a.length; i++) {
            for(int j = 0; j < a[0].length; j++) {
                System.out.printf("%5f ", a[i][j]);
            }
            System.out.println();
        }
    }
}
