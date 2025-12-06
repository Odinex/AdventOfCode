package org.example;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Day6 {

    private static final String STAR = "*";
    private static final String PLUS = "+";
    private static final Character STARC = '*';
    private static final Character PLUSC = '+';

    public void executeFirst(String name) throws IOException {
        try (InputStream resource = this.getClass().getResourceAsStream(name)) {
            assert resource != null;
            InputStreamReader streamReader = new InputStreamReader(resource, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);

            List<List<String>> listOfGroups = new ArrayList<>();
            for (String line; (line = reader.readLine()) != null;) {
                String[] parts = line.split(" ");
                if(listOfGroups.isEmpty()) {
                    for (int i = 0; i < parts.length; i++) {
                        listOfGroups.add(new ArrayList<>());
                    }

                }
                for (int i = 0; i < parts.length; i++) {
                    String part = parts[i];
                    listOfGroups.get(i).add(part);
                }
            }
            List<Long> results = new ArrayList<>();
            for(List<String> group : listOfGroups) {
                String sign = group.get(group.size() - 1);
                group.remove(sign);
                LongStream longStream = group.stream().mapToLong(it -> Long.parseLong(it));
                Long result;
                if(Objects.equals(sign, PLUS)) {
                    result = longStream.sum();
                } else {
                    result = longStream.reduce(1, (a, b) -> a * b);
                }
                results.add(result);
            }
            System.out.println("executeFirst");
            System.out.println(results.stream().reduce(Long::sum));

        }
    }

    public void executeSecond(String name) throws IOException {
        try (InputStream resource = this.getClass().getResourceAsStream(name)) {
            assert resource != null;
            InputStreamReader streamReader = new InputStreamReader(resource, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);

            List<List<Character>> listOfGroups = new ArrayList<>();
            for (String line; (line = reader.readLine()) != null;) {
                char[] parts = line.toCharArray();
                if(listOfGroups.isEmpty()) {
                    for (int i = parts.length - 1; i >= 0; i--) {
                        listOfGroups.add(new ArrayList<>());
                    }
                }
                for (int i = parts.length - 1; i >= 0; i--) {
                    char part = parts[i];
                    listOfGroups.get(i).add(part);
                }
            }
            List<String> strings = new ArrayList<>();
            List<Long> results = new ArrayList<>();
            for (int i = listOfGroups.size()-1; i >= 0; i--) {
                List<Character> group = listOfGroups.get(i);
                StringBuilder stringBuilder = new StringBuilder();
                boolean hasCalculatedResult = false;
                for (Character c : group) {
                    if (c.equals(PLUSC)) {
                        List<Long> numbers = new ArrayList<>();
                        numbers.add(Long.parseLong(stringBuilder.toString().trim()));
                        strings.forEach(s -> {
                            String trim = s.trim();
                            if (!trim.isEmpty()) {
                                numbers.add(Long.parseLong(trim));
                            }
                        });
                        strings.clear();
                        hasCalculatedResult = true;
                        Optional<Long> result = numbers.stream().reduce(Long::sum);
                        results.add(result.get());
                    } else if (c.equals(STARC)) {
                        List<Long> numbers = new ArrayList<>();
                        numbers.add(Long.parseLong(stringBuilder.toString().trim()));
                        strings.forEach(s -> {
                            String trim = s.trim();
                            if (!trim.isEmpty()) {
                                numbers.add(Long.parseLong(trim));
                            }
                        });
                        strings.clear();
                        hasCalculatedResult = true;
                        Optional<Long> result = numbers.stream().reduce((a, b) -> a * b);
                        results.add(result.get());
                    } else {
                        stringBuilder.append(c);
                    }
                }
                if (!hasCalculatedResult) {
                    strings.add(stringBuilder.toString());
                }

            }
            System.out.println("executeSecond");
            System.out.println(results.stream().reduce(Long::sum));

        }
    }
}
