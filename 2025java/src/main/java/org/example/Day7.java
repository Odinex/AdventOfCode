package org.example;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day7 {

    private static final int MIN = 0;
    private static final int MAX = 99;
    private static final int HUND = 100;

    public void executeFirst(String name) throws IOException {
        try (InputStream resource = this.getClass().getResourceAsStream(name)) {
            assert resource != null;
            InputStreamReader streamReader = new InputStreamReader(resource, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);

            Set<Integer> beamColumns = new HashSet<>();
            int beamSplittingCount = 0;
            for (String line; (line = reader.readLine()) != null;) {
                char[] charArray = line.toCharArray();
                for (int i = 0; i < charArray.length; i++) {
                    char c = charArray[i];
                    if(c == 'S') {
                        beamColumns.add(i);
                    } else if(c == '^' && beamColumns.contains(i)) {
                        beamColumns.remove(i);
                        if(i-1 >= 0) {
                            beamColumns.add(i-1);
                        }
                        if(i+1 < line.length()) {
                            beamColumns.add(i+1);
                        }
                        beamSplittingCount++;
                    }
                }

            }
            System.out.println("executeFirst " + beamSplittingCount);

        }
    }

    public void executeSecond(String name) throws IOException {
        try (InputStream resource = this.getClass().getResourceAsStream(name)) {
            assert resource != null;
            InputStreamReader streamReader = new InputStreamReader(resource, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);

            Map<Integer, Long> beamRoutes = new HashMap<>();
            List<char[]> matrix = new ArrayList<>();
            for (String line; (line = reader.readLine()) != null;) {
                char[] charArray = line.toCharArray();
                matrix.add(charArray);
            }
            for(int matrixIndex = 0; matrixIndex < matrix.size() ; matrixIndex++) {
                char[] chars = matrix.get(matrixIndex);
                for (int i = 0; i < chars.length; i++) {
                    char c = chars[i];
                    if(c == 'S') {
                        beamRoutes.put(i, 1L);
                    } else if(c == '^' && beamRoutes.containsKey(i) ) {
                        int finalI = i;

                        Long removed = beamRoutes.remove(i);
                        if (i - 1 >= 0) {
                            if(beamRoutes.containsKey(i-1)) {
                                beamRoutes.compute(i-1, (k, splitLeftCount) -> splitLeftCount + removed );
                            } else {
                                beamRoutes.put(i-1,  removed);
                            };
                        }
                        if (i + 1 < matrix.size()) {
                            if(beamRoutes.containsKey(i + 1)) {
                                beamRoutes.compute(i + 1, (k, splitLeftCount) -> splitLeftCount + removed );
                            } else {
                                beamRoutes.put(i + 1,  removed);
                            }
                        }
                    }
                    

                }

            }
            System.out.println("executeSecond " + beamRoutes.values().stream().reduce(Long::sum));

        }
    }
}
