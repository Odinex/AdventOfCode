package org.example;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day5 {

    private static final int MIN = 0;
    private static final int MAX = 99;
    private static final int HUND = 100;
    class Range {
        private Long start;
        private Long end;

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

            boolean isInRangesMode = true;
            List<Range> ranges = new ArrayList<>();
            for (String line; (line = reader.readLine()) != null;) {
                if(line.isEmpty()) {
                    isInRangesMode = false;
                    continue;
                }
                if(isInRangesMode) {
                    String[] array = line.split("-");
                    long start = Long.parseLong(array[0]);
                    long end = Long.parseLong(array[1]);

                    List<Range> affectedRanges = ranges.stream().filter(range -> range.isInRage(start)  ||
                            range.isInRage(end)  || range.start > start && range.end < end).collect(Collectors.toList());
                    if(affectedRanges.isEmpty()) {
                        ranges.add(new Range(start, end));
                    } else {
                        ranges.removeAll(affectedRanges);
                        affectedRanges.add(new Range(start, end));
                        long minStart = Long.MAX_VALUE;
                        long maxEnd = -1;
                        for (Range r : affectedRanges) {
                            if (r.start < minStart) {
                                minStart = r.start;
                            }
                            if (r.end > maxEnd) {
                                maxEnd = r.end;
                            }
                        }
                        ranges.add(new Range(minStart, maxEnd));

                    }
                } else {
                    break;
                }
            }
            Long countIds = 0L;
            ranges.sort(Comparator.comparing(o -> o.start));
            for (Range range : ranges) {
                countIds += range.end - (range.start - 1);
            }
            System.out.println("Еxecute second " + countIds);

        }
    }
}
