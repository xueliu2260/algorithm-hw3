import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> lineSegments = new ArrayList<>();
    private LineSegment[] segments;
    public BruteCollinearPoints(Point[] points) {
        checkDuplicate(points);
        Point[] pointCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointCopy);
        for (int i = 0; i < pointCopy.length - 3; i++) {
            for (int j = i + 1; j < pointCopy.length - 2; j++) {
                for (int k = j + 1; k < pointCopy.length - 1; k++) {
                    for (int m = k + 1; m < pointCopy.length; m++) {
                        Point p1 = pointCopy[i];
                        Point p2 = pointCopy[j];
                        Point p3 = pointCopy[k];
                        Point p4 = pointCopy[m];
                        double slopDiff1 = p1.slopeTo(p2);
                        double slopDiff2 = p1.slopeTo(p3);
                        double slopDiff3 = p1.slopeTo(p4);
                        if (slopDiff1 == slopDiff2 && slopDiff2 == slopDiff3) {
                            lineSegments.add(new LineSegment(p1, p4));
                        }
                    }
                }
            }
        }
        segments = lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }    // finds all line segments containing 4 points

    public int numberOfSegments() {
        return segments.length;
    }        // the number of line segments

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[numberOfSegments()]);
    }                // the line segments

    private void checkDuplicate(Point[] points) {
        if(points == null || points.length == 0){
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null){
                throw new IllegalArgumentException();
            }
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null || points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }
//
//    public static void main(String[] args) {
//        /* YOUR CODE HERE */
//        Point[] points = getPointsFromFile(args[0]);
//        StdDraw.setXscale(0, 32768);
//        StdDraw.setYscale(0, 32768);
//        for (Point p : points) {
//            p.draw();
//        }
//        StdDraw.show();
//        BruteCollinearPoints test = new BruteCollinearPoints(points);
//        test.segments();
//
//    }
//
//    private static Point[] getPointsFromFile(String fileName) {
//        In in = new In(fileName);
//        int N = in.readInt();
//        Point[] points = new Point[N];
//        for (int i = 0; i < N; i++) {
//            int x = in.readInt();
//            int y = in.readInt();
//            points[i] = new Point(x, y);
//        }
//        return points;
//    }
}
