package org.example;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Day5 {

    private static final int MIN = 0;
    private static final int MAX = 99;
    private static final int HUND = 100;
    class Range {
        private final Long start;
        private final Long end;

        public Range(Long start, Long end) {
            this.start = start;
            this.end = end;
        }
        public boolean isInRage(Long value) {
            return value >= start && value <= end;
        }
    }
    public void executeFirst(String name) throws IOException {
        try (InputStream resource = this.getClass().getResourceAsStream(name)) {
            assert resource != null;
            InputStreamReader streamReader = new InputStreamReader(resource, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            boolean isInRangesMode = true;
            List<Range> ranges = new ArrayList<>();
            List<Long> idsOfFresh = new ArrayList<>();
            for (String line; (line = reader.readLine()) != null;) {
                if(line.isEmpty()) {
                    isInRangesMode = false;
                    continue;
                }
                if(isInRangesMode) {
                    Set<Long> currentRange = new HashSet<>();
                    String[] array = line.split("-");
                    long start = Long.parseLong(array[0]);
                    long end = Long.parseLong(array[1]);

                    ranges.add( new Range(start, end));
                } else {

                    long e = Long.parseLong(line);
                    if(ranges.stream().anyMatch(it ->
                        it.isInRage(e)
                    )) {
                        idsOfFresh.add(e);
                    }
                }

            }
            System.out.println("executeFirst");
            System.out.println(idsOfFresh.size());
            System.out.println(idsOfFresh.toString());

        }
    }

    public void executeSecond(String name) throws IOException {
        try (InputStream resource = this.getClass().getResourceAsStream(name)) {
            assert resource != null;
            InputStreamReader streamReader = new InputStreamReader(resource, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);

            for (String line; (line = reader.readLine()) != null;) {
                char first = line.charAt(0);


            }
            System.out.println("executeSecond");

        }
    }
}
