package org.example;


import java.io.*;
import java.nio.charset.StandardCharsets;

public class Day1 {
    private static final int MIN = 0;
    private static final int MAX = 99;
    private static final int HUND = 100;

    public void executeFirst(String name) throws IOException {
        try (InputStream resource = this.getClass().getResourceAsStream(name)) {
            assert resource != null;
            InputStreamReader streamReader = new InputStreamReader(resource, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            int counterOfZero = 0;
            long dialPosition = 50;
            for (String line; (line = reader.readLine()) != null;) {
                char first = line.charAt(0);
                boolean isNegation = first == 'L';
                long number = Long.parseLong(line.substring(1));
                if(isNegation) {
                    dialPosition -= number;
                    while(dialPosition < MIN) {
                        dialPosition += HUND;
                    }
                } else {
                    dialPosition += number;
                    while(dialPosition > MAX) {
                        dialPosition -= HUND;
                    }
                }
                if(dialPosition == MIN) {
                    counterOfZero++;
                }
            }
            System.out.println("executeFirst");
            System.out.println(counterOfZero);

        }
    }

    public void executeSecond(String name) throws IOException {
        try (InputStream resource = this.getClass().getResourceAsStream(name)) {
            assert resource != null;
            InputStreamReader streamReader = new InputStreamReader(resource, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            long counterOfZero = 0;
            long dialPosition = 50;
            for (String line; (line = reader.readLine()) != null;) {
                char first = line.charAt(0);
                boolean isNegation = first == 'L';
                long number = Long.parseLong(line.substring(1));

                long dialTurns = number / HUND;
                counterOfZero += dialTurns;
                long numberLeftAfterTurning = number % HUND;
                boolean isStartAtZero = dialPosition == MIN;
                if(isNegation) {
                    dialPosition -= numberLeftAfterTurning;

                    if(dialPosition <= MIN) {
                        if(dialPosition < MIN) {
                            dialPosition += HUND;
                        }
                        if(!isStartAtZero) {
                            counterOfZero++;
                        }
                    }
                } else {
                    dialPosition += numberLeftAfterTurning;
                    if(dialPosition > MAX || dialPosition == MIN) {
                        if(dialPosition > MAX) {
                            dialPosition -= HUND;
                        }
                        counterOfZero++;
                    }
                }
            }
            System.out.println("executeSecond");
            System.out.println(counterOfZero);

        }
    }
}
