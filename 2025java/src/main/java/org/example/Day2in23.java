package org.example;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2in23 {

    private static final int MIN = 0;
    private static final int MAX = 99;
    private static final int HUND = 100;
    Pattern gameIdPattern = Pattern.compile("^Game (\\d+):");
    // 4. Regex to find cube counts and colors
    Pattern cubePattern = Pattern.compile("(\\d+) (blue|red|green)");
    public void executeFirst(String name) throws IOException {
        try (InputStream resource = this.getClass().getResourceAsStream(name)) {
            assert resource != null;
            InputStreamReader streamReader = new InputStreamReader(resource, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            long sumOfIds = 0;
            for (String line; (line = reader.readLine()) != null;) {

                Matcher gameIdMatcher = gameIdPattern.matcher(line);
                if (gameIdMatcher.find()) {
                    // group(1) is the first capture group (the digits)
                    int gameId = Integer.parseInt(gameIdMatcher.group(1));
                    System.out.println("Processing Game ID: " + gameId);

                    // 2. Split the string to separate the ID from the game data
                    // We split on the first colon to get the rest of the line
                    String gameData = line.split(":", 2)[1];

                    // 3. Split by semicolon to process each 'Round' individually
                    String[] rounds = gameData.split(";");


                    int roundNumber = 1;
                    boolean isLegit = true;
                    for (String round : rounds) {
                        System.out.println("  --- Round " + roundNumber + " ---");
                        Matcher cubeMatcher = cubePattern.matcher(round);

                        // Use a while loop to find ALL matches in this specific round
                        while (isLegit && cubeMatcher.find()) {
                            int count = Integer.parseInt(cubeMatcher.group(1)); // The number
                            String color = cubeMatcher.group(2);               // The color
                            if(color.equals("red") && count > 12 ||
                                    color.equals("green") && count > 13 ||
                                        color.equals("blue") && count > 14) {
                                isLegit = false;
                                break;
                            }
                            System.out.println("    Found: " + count + " " + color);
                        }

                        roundNumber++;
                        if(!isLegit) {
                            break;
                        }
                    }
                    if(isLegit) {
                        sumOfIds += gameId;
                    }
                }

            }
            System.out.println("executeFirst");
            System.out.println(sumOfIds);

        }
    }

    public void executeSecond(String name) throws IOException {
        try (InputStream resource = this.getClass().getResourceAsStream(name)) {
            assert resource != null;
            InputStreamReader streamReader = new InputStreamReader(resource, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            long sumOfPower = 0;
            for (String line; (line = reader.readLine()) != null;) {

                Matcher gameIdMatcher = gameIdPattern.matcher(line);
                if (gameIdMatcher.find()) {
                    // group(1) is the first capture group (the digits)
                    int gameId = Integer.parseInt(gameIdMatcher.group(1));
                    System.out.println("Processing Game ID: " + gameId);

                    // 2. Split the string to separate the ID from the game data
                    // We split on the first colon to get the rest of the line
                    String gameData = line.split(":", 2)[1];

                    // 3. Split by semicolon to process each 'Round' individually
                    String[] rounds = gameData.split(";");
                    int redMax = 0;
                    int blueMax = 0;
                    int greenMax = 0;

                    int roundNumber = 1;
                    boolean isLegit = true;
                    for (String round : rounds) {
                        System.out.println("  --- Round " + roundNumber + " ---");
                        Matcher cubeMatcher = cubePattern.matcher(round);

                        // Use a while loop to find ALL matches in this specific round
                        while (isLegit && cubeMatcher.find()) {
                            int count = Integer.parseInt(cubeMatcher.group(1)); // The number
                            String color = cubeMatcher.group(2);               // The color
                            if (color.equals("red") ) {
                                if(count > redMax) {
                                    redMax = count;
                                }
                            } else if(
                                    color.equals("green")) {
                                if(count > greenMax) {
                                    greenMax = count;
                                }
                            } else if(
                                    color.equals("blue")) {
                                if(count>blueMax) {
                                    blueMax = count;
                                }
                            }
                            System.out.println("    Found: " + count + " " + color);
                        }

                        roundNumber++;
                    }
                    sumOfPower += (long) redMax *greenMax*blueMax;
                    
                }
            }
            System.out.println("executeSecond " + sumOfPower);

        }
    }
}
