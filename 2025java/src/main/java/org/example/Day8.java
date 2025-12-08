package org.example;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Day8 {

    private static final int MIN = 0;
    private static final int MAX = 99;
    private static final int HUND = 100;

    public void executeFirst(String name, int numberOfIterations) throws IOException {
        try (InputStream resource = this.getClass().getResourceAsStream(name)) {
            assert resource != null;
            InputStreamReader streamReader = new InputStreamReader(resource, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            List<Point3> points = new ArrayList<>();
            for (String line; (line = reader.readLine()) != null;) {
                String[] split = line.split(",");
                points.add(new Point3(Long.parseLong(split[0]),Long.parseLong(split[1]),Long.parseLong(split[2])));
            }
            List<Set<Point3>> connectedGroups = new ArrayList<>();
            Map<Set<Point3>, Long> pairs = new HashMap<>();

            for (int i = 0; i < points.size() - 1; i++) {
                for (int j = i + 1; j < points.size(); j++) {
                    double distance = this.findDistance(points.get(i), points.get(j));
                    pairs.put(Set.of(points.get(i), points.get(j)), (long) distance);
                }
            }
            List<Set<Point3>> sets = new ArrayList<>(pairs.keySet());
            sets.sort((a,b)-> { if(pairs.get(a)<pairs.get(b)) return -1; else return 1; });
            
            
            for(int c = 0; c< numberOfIterations; c++) {

                List<Point3> currentPair = new ArrayList<>(sets.get(c));
                List<Set<Point3>> foundSets = new ArrayList<>();
                for (Set<Point3> set : connectedGroups) {
                    if (set.contains(currentPair.get(0)) || set.contains(currentPair.get(1))) {
                        foundSets.add(set);
                    }
                }
                connectedGroups.removeAll(foundSets);
                HashSet<Point3> merg = new HashSet<>();

                merg.add(currentPair.get(0));
                merg.add(currentPair.get(1));
                for(Set<Point3> found : foundSets) {
                    merg.addAll(found);
                }
                connectedGroups.add(merg);

            }
            System.out.println("executeFirst");
            long result =1;
            connectedGroups.sort((a, b) -> { if(a.size() > b.size()) return -1;  else if(a.size() == b.size()) return 0; else return 1; } );
            List<Set<Point3>> top3 = connectedGroups.subList(0, 3);
            for (Set<Point3> set : top3) {
                result *= set.size();
            }
            
            System.out.println(result);

        }
    }
    
    class Point3 {
        private final long x;
        private final long y;
        private final long z;

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Point3 point3 = (Point3) o;
            return x == point3.x && y == point3.y && z == point3.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }

        public Point3(long x, long y, long z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
    
    private double findDistance(Point3 a, Point3 b) {
        double distanceX = Math.pow(a.x - b.x, 2);
        double distanceY = Math.pow(a.y - b.y, 2);
        double distanceZ = Math.pow(a.z - b.z, 2);
        return Math.abs(Math.sqrt(distanceZ+distanceX+distanceY));
    }

    public void executeSecond(String name) throws IOException {
        try (InputStream resource = this.getClass().getResourceAsStream(name)) {
            assert resource != null;
            InputStreamReader streamReader = new InputStreamReader(resource, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            List<Point3> points = new ArrayList<>();
            for (String line; (line = reader.readLine()) != null;) {
                String[] split = line.split(",");
                points.add(new Point3(Long.parseLong(split[0]),Long.parseLong(split[1]),Long.parseLong(split[2])));
            }
            List<Set<Point3>> connectedGroups = new ArrayList<>();
            Map<Set<Point3>, Long> pairs = new HashMap<>();

            for (int i = 0; i < points.size() - 1; i++) {
                for (int j = i + 1; j < points.size(); j++) {
                    double distance = this.findDistance(points.get(i), points.get(j));
                    pairs.put(Set.of(points.get(i), points.get(j)), (long) distance);
                }
            }
            List<Set<Point3>> sets = new ArrayList<>(pairs.keySet());
            sets.sort((a,b)-> { if(pairs.get(a)<pairs.get(b)) return -1; else return 1; });

            int c = 0;
            List<Point3> lastIteratedPair = List.of();
            while(connectedGroups.size() != 1 || connectedGroups.get(0) != null && connectedGroups.get(0).size() != points.size()) {

                lastIteratedPair = new ArrayList<>(sets.get(c));

                List<Set<Point3>> foundSets = new ArrayList<>();
                for (Set<Point3> set : connectedGroups) {
                    if (set.contains(lastIteratedPair.get(0)) || set.contains(lastIteratedPair.get(1))) {
                        foundSets.add(set);
                    }
                }
                connectedGroups.removeAll(foundSets);
                HashSet<Point3> merg = new HashSet<>();

                merg.add(lastIteratedPair.get(0));
                merg.add(lastIteratedPair.get(1));
                for(Set<Point3> found : foundSets) {
                    merg.addAll(found);
                }
                connectedGroups.add(merg);
                c++;
            }
            System.out.println("executeFirst");
            long result =1;
//            connectedGroups.sort((a, b) -> { if(a.size() > b.size()) return -1;  else if(a.size() == b.size()) return 0; else return 1; } );
//            List<Set<Point3>> top3 = connectedGroups.subList(0, 3);
//            for (Set<Point3> set : top3) {
//                result *= set.size();
//            }

            System.out.println("executeSecond " + (lastIteratedPair.get(0).x * lastIteratedPair.get(1).x) );

        }
    }
}
