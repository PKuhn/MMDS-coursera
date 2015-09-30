import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;

public class PageRankCalculatorTest {

    @Test
    public void testMain() {
        Map<Integer, List<Integer>> pairs = readInTablePairs("question2.txt");
        PageRankCalculator calc = new PageRankCalculator(pairs);
        System.out.println(calc.getPageRankAfterIteration(100, 0.15));
    }

    private List<String> readInLines(String fileName) {
        List<String> lines = new ArrayList<>();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(line);
            }

            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private Map<Integer, List<Integer>> readInTablePairs(String fileName) {
        Map<Integer, List<Integer>> pairs = new HashMap<>();
        List<String> lines = readInLines(fileName);
        for (String line : lines) {
            addLine(pairs, line);
        }
        return pairs;
    }

    private void addLine(Map<Integer, List<Integer>> pairs, String line) {
        String[] splitted = line.split(",");
        Integer firstNode = Integer.valueOf(splitted[0]);
        Integer secondNode = Integer.valueOf(splitted[1]);
        if (!pairs.containsKey(firstNode)) {
            pairs.put(firstNode, new ArrayList<>());
        }
        if (!pairs.containsKey(secondNode)) {
            pairs.put(secondNode, new ArrayList<>());
        }
        pairs.get(firstNode).add(secondNode);
    }
}