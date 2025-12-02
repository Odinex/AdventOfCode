package org.example;


import java.io.*;
import java.nio.charset.StandardCharsets;

public class Day1 {
    private static final int MIN = 0;
    private static final int MAX = 99;
    private static final int HUND = 100;
    public void executeExample() throws IOException {
        try (InputStream resource = this.getClass().getResourceAsStream("/Day1ex.txt")) {
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
            System.out.println(counterOfZero);

        }
    }
}
