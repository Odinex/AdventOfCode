package org.example;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Day9 {

    private static final int MIN = 0;
    private static final int MAX = 99;
    private static final int HUND = 100;

    public void executeFirst(String name) throws IOException {
        try (InputStream resource = this.getClass().getResourceAsStream(name)) {
            assert resource != null;
            InputStreamReader streamReader = new InputStreamReader(resource, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            List<Point> points = new ArrayList<>();
            for (String line; (line = reader.readLine()) != null;) {
                String[] split = line.split(",");
                points.add(new Point(Integer.parseInt(split[0]),Integer.parseInt(split[1])));
            }
            int maxArea = Integer.MIN_VALUE;
            for(int i =0; i < points.size()-1; i++){
                Point p1 = points.get(i);
                for(int j =i+1; j < points.size(); j++) {
                    Point p2 = points.get(j);
                    int area = calculateArea(p1,p2);
                    if(area > maxArea) {
                        maxArea=area;
                    }
                }
            }
            System.out.println("executeFirst " + maxArea);

        }
    }

    public int calculateArea(Point p1, Point p2) {
        int xD = Math.abs(p1.x - p2.x) + 1;
        int yD  = Math.abs(p1.y - p2.y) + 1;
        return xD * yD;
    }

    public void executeSecond(String name) throws IOException {
        try (InputStream resource = this.getClass().getResourceAsStream(name)) {
            assert resource != null;
            InputStreamReader streamReader = new InputStreamReader(resource, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            Map<Move, Move> oppositeDirectionsMap = new HashMap();
                    oppositeDirectionsMap.put(directions.get(0), directions.get(1));
            oppositeDirectionsMap.put(directions.get(1), directions.get(0));
            oppositeDirectionsMap.put(directions.get(2), directions.get(3));
            oppositeDirectionsMap.put(directions.get(3), directions.get(2));
            List<Point> points = new ArrayList<>();
            List<List<Character>> matrix = new ArrayList<>();
            Map<Integer, List<Point>> xMap = new HashMap<>();
            Map<Integer, List<Point>> yMap = new HashMap<>();
            int x = 0;
            int y = 0;
            for (String line; (line = reader.readLine()) != null;) {
                String[] split = line.split(",");
                int x1 = Integer.parseInt(split[0]);
                int y1 = Integer.parseInt(split[1]);
                Point point = new Point(x1, y1);
                points.add(point);
                if(x1 > x) {
                    x = x1;
                }
                if(y1 > y) {
                    y = y1;
                }
//                if(xMap.containsKey(point.x)) {
//                    xMap.get(point.x).add(point);
//                } else {
//                    ArrayList<Point> v = new ArrayList<>();
//                    v.add(point);
//                    xMap.put(point.x, v);
//                }
//
//                if(yMap.containsKey(point.y)) {
//                    yMap.get(point.y).add(point);
//                } else {
//                    ArrayList<Point> v = new ArrayList<>();
//                    v.add(point);
//                    yMap.put(point.y, v);
//                }
            }
            for(int i = 0; i<y+2; i++) {
                List<Character> row = new ArrayList<>();

                for(int j = 0; j < x+2; j++) {
                    row.add('.');

                }
                matrix.add(row);
            }
            for(Point p : points) {
                matrix.get(p.y).set(p.x, '#');
            }


            Point start = points.get(0);
            Point current = start;
            Move prevMove = null;
            do {
                int distance = Integer.MAX_VALUE;
                Point nextPoint = null;
                Move currentMove = prevMove;
                for(Move move : directions) {
                    if(move.equals(oppositeDirectionsMap.get(prevMove))) {
                        continue;
                    }
                    Point found = findPointInDirection(current, move, matrix);
                    if(found != null) {
                        boolean isFoundOK = nextPoint == null;
                        int foundDistance = 0;
                        if (!isFoundOK) {
                            foundDistance = (found.y - nextPoint.y + found.x - nextPoint.y);
                            isFoundOK = foundDistance < distance;
                        }
                        if (isFoundOK) {
                            distance = foundDistance;
                            nextPoint = found;
                            currentMove = move;
                        }
                    }
                    if(current.equals(nextPoint)) {
                        System.out.println("WTF");
                    }
                }

                assert currentMove != null;
                if(current.x == nextPoint.x) {
                    for(int i = current.y + currentMove.y; i != nextPoint.y; i += currentMove.y) {
                        matrix.get(i).set(current.x, 'Y');
                    }
                }

                if(current.y == nextPoint.y) {
                    for(int i = current.x + currentMove.x; i != nextPoint.x; i += currentMove.x) {
                        matrix.get(current.y).set(i, 'Y');
                    }
                }

                current = nextPoint;
                prevMove = currentMove;
            } while (!current.equals(start));



            System.out.println("executeSecond");
            for(int i = 0; i < matrix.size(); i++) {
                for(int j = 0; j < matrix.get(i).size(); j++) {
                    System.out.print(matrix.get(i).get(j));
                }
                System.out.println();
            }

        }
    }

    private Point findPointInDirection(Point start, Move move, List<List<Character>> matrix) {
        Character ch = null;
        int row = start.x;
        int column = start.y ;
        while(ch == null || ch != '#') {
            row  += move.x;
            column += move.y;
            if(column >= 0 && column < matrix.size() && row >=0 && row < matrix.get(column).size()) {
                ch = matrix.get(column).get(row);
            } else {
                return null;
            }
        }
        return new Point(row, column);
    }

    class Point {
        public final int x;
        public final int y;


        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "" +
                    "x=" + x +
                    ", y=" + y ;
        }

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    class Move {
        private final int x;
        private final int y;
        public Move(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Move move = (Move) o;
            return x == move.x && y == move.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
    List<Move> directions = Arrays.asList(
            new Move(0, 1),   // right
            new Move(0, -1),  // left
            new Move(1, 0),   // down
            new Move(-1, 0)  // up
//            Pair(1, 1),   // down-right
//            Pair(1, -1),  // down-left
//            Pair(-1, 1),  // up-right
//            Pair(-1, -1)  // up-left
    );


}

