import java.util.List;
import java.util.Map;

public class PageRankCalculator {
    Map<Integer, List<Integer>> adjencyList;
    Integer nodeCount;

    public PageRankCalculator(Map<Integer, List<Integer>> adjencyList) {
        this.adjencyList = adjencyList;
        nodeCount = this.adjencyList.keySet().size();
        double[][] matrixData = getJumpMatrix();
        printGrid(matrixData);
    }

    public List<Integer> getPageRankAfterIteration(int iteration, float randomTeleportProp) {
        return null;
    }

    public List<Integer> computePageRank() {
        return null;
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

    public void printGrid(double a[][]) {
        for(int i = 0; i < a.length; i++) {
            for(int j = 0; j < a[0].length; j++) {
                System.out.printf("%5f ", a[i][j]);
            }
            System.out.println();
        }
    }
}
