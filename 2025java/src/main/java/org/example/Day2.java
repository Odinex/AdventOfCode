package org.example;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class Day2 {


    public void executeFirst(String name) throws IOException {
        try (InputStream resource = this.getClass().getResourceAsStream(name)) {
            assert resource != null;
            InputStreamReader streamReader = new InputStreamReader(resource, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            AtomicLong sum = new AtomicLong();

            for (String line; (line = reader.readLine()) != null;) {
                String[] idPairs = line.split(",");
                Arrays.stream(idPairs).forEach(pair-> { // parallel().
                    String[] split = pair.split("-");
                    long startRange = Long.parseLong(split[0]);
                    long endRange = Long.parseLong(split[1]);


                    for(long current = startRange; current <=endRange; current++) {
                        String string = Long.toString(current);
                        int length = string.length();
                        if(current == 565656) {
                            System.out.println(current);
                        }
                        int endIndex = length / 2;
                        if(endIndex > 0) {
                            String substring = string.substring(0, endIndex);
                            long firstPart = Long.parseLong(substring);
                            String substring1 = string.substring(endIndex);
                            long secondPart = Long.parseLong(substring1);

                            boolean hasRepeatedPartAtLeastTwice = checkRepeated( substring1, substring);
                            boolean hasRepeatedPartAtLeastTwice2 = checkRepeated(  substring, substring1);

                            if (!substring.startsWith("0") && !substring1.startsWith("0") && (firstPart == secondPart || (hasRepeatedPartAtLeastTwice && hasRepeatedPartAtLeastTwice2))) {
                                sum.addAndGet(current);
                            }
                        }
                    }

                });

            }
            System.out.println("executeFirst");
            System.out.println(sum);

        }
    }

    private static boolean checkRepeated(String first, String second) {
        int count= 0;
        Set<String> parts = new HashSet<>();
        for(int start = 0; start < first.length() ; start++) {
            for(int end = start + 1; end <= first.length(); end++) {
                parts.add(first.substring(start, end));
            }
        }

        for (String part : parts) {
            int repeatedTimes = second.length() / part.length();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < repeatedTimes; i++) {
                stringBuilder.append(part);
            }
            if (stringBuilder.toString().equals(second)) {
                return true;
            }
        }
        return false;
    }


    private static boolean checkRepeated(String string) {

        Set<String> parts = new HashSet<>();
        for(int start = 0; start < string.length() ; start++) {
            for(int end = start + 1; end <= string.length(); end++) {
                parts.add(string.substring(start, end));
            }
        }

        for (String part : parts) {
            int repeatedTimes = string.length() / part.length();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < repeatedTimes; i++) {
                stringBuilder.append(part);
            }
            if (repeatedTimes > 1 && stringBuilder.toString().equals(string)) {
                return true;
            }
        }
        return false;
    }

    public void executeSecond(String name) throws IOException {
        try (InputStream resource = this.getClass().getResourceAsStream(name)) {
            assert resource != null;
            InputStreamReader streamReader = new InputStreamReader(resource, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            AtomicLong sum = new AtomicLong();

            for (String line; (line = reader.readLine()) != null;) {
                String[] idPairs = line.split(",");
                Arrays.stream(idPairs).parallel().forEach(pair-> { // parallel().
                    String[] split = pair.split("-");
                    long startRange = Long.parseLong(split[0]);
                    long endRange = Long.parseLong(split[1]);


                    for(long current = startRange; current <=endRange; current++) {
                        String string = Long.toString(current);
                        int length = string.length();
                        if(current == 565656) {
                            System.out.println(current);
                        }
                        boolean hasRepeatedPartAtLeastTwice = checkRepeated( string);

                        if (hasRepeatedPartAtLeastTwice) {
                            sum.addAndGet(current);
                        }
                    }

                });

            }
            System.out.println(sum);
            System.out.println("executeSecond");

        }
    }
}
