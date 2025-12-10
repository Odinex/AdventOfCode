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
            Map<Direct, Direct> oppositeDirectionsMap = new HashMap<>();
                    oppositeDirectionsMap.put(directions.get(0), directions.get(1));
            oppositeDirectionsMap.put(directions.get(1), directions.get(0));
            oppositeDirectionsMap.put(directions.get(2), directions.get(3));
            oppositeDirectionsMap.put(directions.get(3), directions.get(2));
            Set<Point> points = new HashSet<>();
            Point start = null;
            int maxX = 0;
            int maxY = 0;
            for (String line; (line = reader.readLine()) != null;) {
                String[] split = line.split(",");
                int x1 = Integer.parseInt(split[0]);
                int y1 = Integer.parseInt(split[1]);
                Point point = new Point(x1, y1);
                if(start == null) {
                    start = point;
                }
                points.add(point);
                if (x1 > maxX) {
                    maxX = x1;
                }
                if (y1 > maxY) {
                    maxY = y1;
                }
            }


            Point current = start;
            LinkedList<Point> ordered = new LinkedList<>();
            ordered.add(start);
            Direct prevMove = null;
            do {
                int distance = Integer.MAX_VALUE;
                Point nextPoint = null;
                Direct currentMove = prevMove;
                for(Direct move : directions) {
                    if(move.equals(oppositeDirectionsMap.get(prevMove))) {
                        continue;
                    }
                    assert current != null;
                    Point found = findPointInDirection(current, move, points,maxY, maxX);
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
//                if(current.x == nextPoint.x) {
//                    for(int i = current.y + currentMove.y; i != nextPoint.y; i += currentMove.y) {
//                        matrix.get(i).set(current.x, 'Y');
//                    }
//                }
//
//                if(current.y == nextPoint.y) {
//                    for(int i = current.x + currentMove.x; i != nextPoint.x; i += currentMove.x) {
//                        matrix.get(current.y).set(i, 'Y');
//                    }
//                }

                current = nextPoint;
                if(!current.equals(start)) {
                    nextPoint.direct = currentMove;
                    ordered.add(nextPoint);
                } else {
                    ordered.get(0).direct = currentMove;
                }
                prevMove = currentMove;
            } while (!current.equals(start));

            int maxArea = Integer.MIN_VALUE;
            for(int i =0; i < ordered.size(); i++){
                Point p0 = ordered.get(i);

                List<Point> filled = new LinkedList<>(ordered);
                if(i > 0) {
                    filled.addAll(ordered.subList(0, i));
                }
                Point p1 = filled.get(i+1);
                Point p2 = filled.get(i+2);
                Direct xDirection = null;
                Direct yDirection = null;
                int p0Limit = 0;
                // TODO make sure p0 is a turn by removing all moves that a sequence and have the same direction
                if(p1.direct != p2.direct) {

                }
                for(int j =i+1; j < filled.size(); j++) {
                    Point test = filled.get(j);
                    if(test.x ==9 && test.y == 5) {
                        System.out.println("now");
                    }
                    boolean isInsideRect = true;
                    boolean isLine = false;
                    if(Math.abs(j-i) == 1) {
                        isLine = true;
                    }
                    if(!isLine) {

                        if (isInsideRect) {
                            for (int s = j + 1; s < filled.size(); s++) {

                                Point point = filled.get(s);
                                if (point.x > p2.x) {
                                    isInsideRect = false;
                                    break;
                                }

                            }
                        }
                    }
                    if (isInsideRect) {
                        int area = calculateArea(p1, p2);
                        if (area > maxArea) {
                            maxArea = area;
                        }
                    }
                }
            }
            System.out.println("executeSecond " + maxArea);


        }
    }



    private Point findPointInDirection(Point start, Direct move, Set<Point> points, int maxY, int maxX) {

        int row = start.x;
        int column = start.y ;
        Point p = null;

        while(true) {
            row  += move.move.x;
            column += move.move.y;
            Point point = new Point(row, column);
            if(column >= 0 && column <= maxY && row >=0 && row <= maxX) {
                if(points.contains(point)) {
                    return point;
                }
            } else {
                return null;
            }
        }
    }
//                    if(p1.direct == Direct.DOWN) {
//        xDirection = Direct.UP;
//        yDirection = oppositeDirectionsMap.get(p2.direct);
//        p0Limit = p0.y;
//    } else if(p1.direct == Direct.UP) {
//        xDirection = Direct.DOWN;
//        yDirection = p2.direct;
//        p0Limit = p0.y;
//    } else if(p1.direct == Direct.RIGHT) {
//        yDirection = Direct.LEFT;
//        xDirection = p2.direct;
//        p0Limit = p0.x;
//    } else {
//        yDirection = Direct.RIGHT;
//        xDirection = oppositeDirectionsMap.get(p2.direct);
//        p0Limit = p0.x;
//    }

//                            if(xDirection == Direct.DOWN) {
//                            if(p1.x < test.x) {
//                                isInsideRect = false;
//                            }
//                        } else if(xDirection == Direct.UP) {
//                            if(p1.x > test.x) {
//                                isInsideRect = false;
//                            }
//                        }
//                        if(yDirection == Direct.RIGHT) {
//                            if(p1.y < test.y) {
//                                isInsideRect = false;
//                            }
//                        } else if(yDirection == Direct.LEFT) {
//                            if(p1.y > test.y) {
//                                isInsideRect = false;
//                            }
//                        }
    class Point {
        public final int x;
        public final int y;
        public Direct direct = null;


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

    static class Move {
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
    static List<Direct> directions = Arrays.asList(
            Direct.RIGHT,   // right
            Direct.LEFT,  // left
            Direct.DOWN,   // down
            Direct.UP  // up
//            Pair(1, 1),   // down-right
//            Pair(1, -1),  // down-left
//            Pair(-1, 1),  // up-right
//            Pair(-1, -1)  // up-left
    );

    enum Direct {
        DOWN(new Move(-1,0)), LEFT(new Move(0,-1)), RIGHT(new Move(0,1)), UP(new Move(1,0));

        final Move move;

        Direct(Move move) {
            this.move = move;
        }
    }


}

