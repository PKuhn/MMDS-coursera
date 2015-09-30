import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.util.Pair;

import java.util.*;

public class LSHCalculator {
    public double calculateJaccardSimiliarity(String first, String second, int shingleLength) {
        Set<String> firstShingles = getShingles(first, shingleLength);
        Set<String> secondShingles = getShingles(second, shingleLength);


        Set<String> union = new HashSet<>(firstShingles);
        union.addAll(secondShingles);
        int unionSize = union.size();

        Set<String> intersection = new HashSet<>(firstShingles);
        intersection.retainAll(secondShingles);
        int intersectionSize = intersection.size();

        return (double) intersectionSize / unionSize;
    }

    private Set<String> getShingles(String input, int shingleLength) {
        Set<String> shingles = new HashSet<>();
        for (int i = 0; i < input.length() - shingleLength + 1; i++) {
            shingles.add(input.substring(i, i + shingleLength));
        }
        return shingles;
    }

    public List<Pair<Integer, Integer>> findCandidatePairs(RealMatrix signatures, int rowsPerBand) {
        List<Pair<Integer, Integer>> candidatePairs = new ArrayList<>();

        for (int rowIndex = 0; rowIndex < signatures.getRowDimension(); rowIndex += rowsPerBand) {
            int maxRow = Math.min(rowIndex + rowsPerBand, signatures.getRowDimension() - 1);
            RealMatrix band = signatures.getSubMatrix(rowIndex, maxRow,
                    0, signatures.getColumnDimension() - 1);
            candidatePairs.addAll(findCandidatePairsForBand(band));
        }

        return candidatePairs;
    }

    public List<Pair<Integer, Integer>> findCandidatePairsForBand(RealMatrix band) {
        int bandWitdh = band.getRowDimension();
        int columnCount = band.getColumnDimension();
        List<Pair<Integer, Integer>> pairs = new ArrayList<>();

        // Check if signatures are equal for each column combination
        for (int firstColumn = 0; firstColumn < columnCount - 1; firstColumn++) {
            for (int secondColumn = firstColumn + 1; secondColumn < columnCount; secondColumn++) {
                boolean equalColumns = true;
                for (int i = 0; i < bandWitdh; i++) {
                    if (band.getEntry(i, firstColumn) != band.getEntry(i, secondColumn)) {
                        equalColumns = false;
                    }
                }
                if (equalColumns) {
                    Pair<Integer, Integer> candidate = new Pair<>(firstColumn, secondColumn);
                    pairs.add(candidate);
                }
            }
        }
        return pairs;
    }

    public int[] getMinHash(RealMatrix signatures, int[] permutation) {
        int documentSize = signatures.getRow(0).length;

        int[] currentMinima = new int[documentSize];
        int[] minRows = new int[documentSize];

        for (int i = 0; i < documentSize; i++) {
            currentMinima[i] = Integer.MAX_VALUE;
        }

        for (int rowIndex : permutation) {
            RealVector currentRow = signatures.getRowVector(rowIndex);

            for (int rowPosition = 0; rowPosition < currentRow.getDimension(); rowPosition++) {
                int entry = (int) currentRow.getEntry(rowPosition);
                if (entry < currentMinima[rowPosition]) {
                    currentMinima[rowPosition] = entry;
                    minRows[rowPosition] = rowIndex;
                }
            }
        }

        return minRows;
    }

}
