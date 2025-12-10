package org.example;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Day10 {

    private static final int MIN = 0;
    private static final int MAX = 99;
    private static final int HUND = 100;

    public void executeFirst(String name) throws IOException {
        try (InputStream resource = this.getClass().getResourceAsStream(name)) {
            assert resource != null;
            InputStreamReader streamReader = new InputStreamReader(resource, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            List<Long> buttonPresses = new ArrayList<>();
            for (String line; (line = reader.readLine()) != null; ) {
                int endIndex = line.indexOf(']');
                String lights = line.substring(line.indexOf('[') + 1, endIndex);
                System.out.println(lights);

                int i = line.indexOf('(', endIndex);
                List<List<Integer>> lightIndexGroups = new ArrayList<>();
                while (i != -1) {
                    endIndex = line.indexOf(')', i);
                    String button = line.substring(i + 1, endIndex);
                    String[] split = button.split(",");
                    List<Integer> indexGroup = new ArrayList<>();
                    System.out.println("button " + button);
                    for (String digit : split) {
                        indexGroup.add(Integer.parseInt(digit));
                    }
                    lightIndexGroups.add(indexGroup);

                    i = line.indexOf('(', endIndex);
                }
                String joltage = line.substring(line.indexOf('{') + 1, line.indexOf('}'));
                System.out.println("joltage " + joltage);

                StringBuilder turnedOffLightsBuilder = new StringBuilder();
                for (int j = 0; j < lights.length(); j++) {
                    turnedOffLightsBuilder.append('.');
                }
                String turnedOffLights = turnedOffLightsBuilder.toString();

                List<List<Integer>> arrayOfCombinations = new ArrayList<>();

                for (int l = 0; l < lightIndexGroups.size(); l++) {
                    arrayOfCombinations.add(new ArrayList<>());
                }
                while (arrayOfCombinations.get(lightIndexGroups.size() - 1).size() < 15) {
                    for (int y = 0; y < lightIndexGroups.size(); y++) {
                        for (List<Integer> combo : arrayOfCombinations) {
                            if (combo.isEmpty() || combo.get(combo.size() - 1) != y) {
                                combo.add(y);
                            }
                        }

                    }
                }
                int minTries = 15;

                for (List<Integer> combo : arrayOfCombinations) {
                    String init = String.copyValueOf(turnedOffLights.toCharArray());
                    for (int j = 0; j < combo.size() || j < minTries; j++) {
                        int a = combo.get(j);
                        String s = clickButton(lightIndexGroups.get(a), init);
                        if (s.equals(lights)) {
                            minTries = j;
                        }
                    }
                }
                buttonPresses.add((long) minTries);
            }
            System.out.println("executeFirst " + buttonPresses.stream().reduce(Long::sum));


        }

    }

    public String clickButton(List<Integer> indexGroup, String initial) {
        char[] charArray = initial.toCharArray();
        for (int i : indexGroup) {
            char c = charArray[i];
            if (c == '.') {
                c = '#';
            } else {
                c = '.';
            }
        }
        return Arrays.toString(charArray);

    }

    public void executeSecond(String name) throws IOException {
        try (InputStream resource = this.getClass().getResourceAsStream(name)) {
            assert resource != null;
            InputStreamReader streamReader = new InputStreamReader(resource, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);

            for (String line; (line = reader.readLine()) != null; ) {
                char first = line.charAt(0);


            }
            System.out.println("executeSecond");

        }
    }
}
