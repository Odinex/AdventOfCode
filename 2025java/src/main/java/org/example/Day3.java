package org.example;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Day3 {

    private static final int MIN = 0;
    private static final int MAX = 99;
    private static final int HUND = 100;

    public void executeFirst(String name) throws IOException {
        try (InputStream resource = this.getClass().getResourceAsStream(name)) {
            assert resource != null;
            InputStreamReader streamReader = new InputStreamReader(resource, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);

            long sum = 0;
            for (String line; (line = reader.readLine()) != null;) {
                int indexOfMax = 0;
                int max = 0;
                for(int i = 0; i < line.length() - 1; i++) {
                    int current = Integer.parseInt(String.valueOf(line.charAt(i)));
                    if(max <  current) {
                       max = current;
                       indexOfMax = i;
                    }
                }
                int secondBatteryValue = 0;
                int secondIndex = 0;
                for(int y = indexOfMax+1; y < line.length(); y++) {
                    int current = Integer.parseInt(String.valueOf(line.charAt(y)));
                    if(secondBatteryValue < current) {
                        secondBatteryValue = current;
                        secondIndex = y;
                    }
                }
                int joltage = Integer.parseInt(String.valueOf(max).concat(String.valueOf(secondBatteryValue)));
                System.out.println(joltage);
                sum += joltage;
            }

            System.out.println("executeFirst");
            System.out.println(sum);

        }
    }

    public void executeSecond(String name) throws IOException {
        try (InputStream resource = this.getClass().getResourceAsStream(name)) {
            assert resource != null;
            InputStreamReader streamReader = new InputStreamReader(resource, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            long sum = 0;
            for (String line; (line = reader.readLine()) != null;) {
                StringBuilder stringBuilder = new StringBuilder();
                int searchStartIndex = 0;
                for(int i = 11; i >= 0; i --) {
                    List<Integer> indexAndMax= getMaxUpToIndex(i, line,searchStartIndex);
                    searchStartIndex = indexAndMax.get(0) + 1;
                    stringBuilder.append(indexAndMax.get(1));
                }
                long joltage = Long.parseLong(stringBuilder.toString());
                System.out.println(joltage);
                sum += joltage;

            }
            System.out.println("executeSecond");
            System.out.println(sum);

        }
    }

    private List<Integer> getMaxUpToIndex(int indexLimit, String line, int startIndex) {

        int max = 0;
        int indexOfMax = 0;
        for(int i = startIndex; i < line.length() - indexLimit; i++) {
            int current = Integer.parseInt(String.valueOf(line.charAt(i)));
            if(max <  current) {
                max = current;
                indexOfMax = i;
            }
        }
        return List.of(indexOfMax,max);
    }
}
