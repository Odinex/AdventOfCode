package org.example;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day4 {


    public void executeFirst(String name) throws IOException {
        try (InputStream resource = this.getClass().getResourceAsStream(name)) {
            assert resource != null;
            InputStreamReader streamReader = new InputStreamReader(resource, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            List<char[]> matrix = new ArrayList<>();
            for (String line; (line = reader.readLine()) != null;) {
                matrix.add(line.toCharArray());
            }
            int counter = 0;

            Set<String> reachableRolls;
            do {
                reachableRolls = getReachableRolls(matrix);


                for (String it : reachableRolls) {
                    String[] coordinates = it.split(",");
                    matrix.get(Integer.parseInt(coordinates[0]))[Integer.parseInt(coordinates[1])] = 'X';
                    counter++;
                }
            } while (!reachableRolls.isEmpty());
            System.out.println("executeFirst");
            System.out.println(counter);

        }
    }

    private static Set<String> getReachableRolls(List<char[]> matrix) {
        Set<String> reachableRolls = new HashSet<>();
        for(int i = 0; i < matrix.size(); i++) {
            char[] chars = matrix.get(i);
            for(int j = 0; j < chars.length; j++) {
                if(chars[j] == '@') {
                    int adjacentRollsCounter = 0;
                    for(int r = i -1; r <= i + 1; r++) {
                        if(r < 0 || r >= matrix.size()) {
                            continue;
                        }
                        for(int c = j - 1; c <= j+1; c++) {
                            if(c < 0 || c >= matrix.size() || (r==i && c==j)) {
                                continue;
                            }
                            if(matrix.get(r)[c] == '@') {
                                adjacentRollsCounter++;
                            }
                        }
                    }
                    if(adjacentRollsCounter < 4) {
                        reachableRolls.add(String.valueOf(i)+ ',' + j);
                    }
                }
            }
        }
        return reachableRolls;
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
