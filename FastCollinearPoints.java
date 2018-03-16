import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FastCollinearPoints {
    private LineSegment[] lineSegments;
    private ArrayList<Point[]> allLineSegment = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        checkDuplicate(points);
        Point[] pointCopy = Arrays.copyOf(points, points.length);
        for (int i = 0; i < points.length; i++) {
            Point firstPoint = points[i];
            Arrays.sort(pointCopy, firstPoint.slopeOrder());
            double curSlope = 0;
            double preSlope = Double.NEGATIVE_INFINITY;
            ArrayList<Point> linePoints = new ArrayList<>();
            for (int j = 1; j < pointCopy.length; j++) {
                curSlope = firstPoint.slopeTo(pointCopy[j]);
                if (preSlope == curSlope) {
                    linePoints.add(pointCopy[j]);
                } else {
                    if (linePoints.size() >= 3) {
                        linePoints.add(firstPoint);
                        addSegment(linePoints);
                    }
                    linePoints.clear();
                    linePoints.add(pointCopy[j]);
                    preSlope = curSlope;
                }
            }
            if (linePoints.size() >= 3) {
                linePoints.add(firstPoint);
                addSegment(linePoints);
            }
        }
        lineSegments = new LineSegment[allLineSegment.size()];
        for (int i = 0; i < allLineSegment.size(); i++) {
            lineSegments[i] = new LineSegment(allLineSegment.get(i)[0], allLineSegment.get(i)[1]);
        }
    }    // finds all line segments containing 4 or more points

    private void addSegment(ArrayList<Point> lineSegment) {

        Collections.sort(lineSegment);
        Point firstPoint = lineSegment.get(0);
        Point lastPoint = lineSegment.get(lineSegment.size() - 1);
        double newSlope = firstPoint.slopeTo(lastPoint);
        for (int i = 0; i < allLineSegment.size(); i++) {
            double oldSlope = allLineSegment.get(i)[0].slopeTo(allLineSegment.get(i)[1]);
            if (newSlope == oldSlope) {
                if (lastPoint.compareTo(allLineSegment.get(i)[1]) == 0) {
                    return;
                }
            }
        }

        allLineSegment.add(new Point[]{firstPoint, lastPoint});
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }        // the number of line segments

    public LineSegment[] segments() {

        return lineSegments;
    }

    private void checkDuplicate(Point[] points) {
        if (points == null || points.length == 0) {
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
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point[] points = getPointsFromFile(args[0]);

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();


        FastCollinearPoints test = new FastCollinearPoints(points);
        for (LineSegment segment : test.segments()) {
            segment.draw();
        }
        System.out.println(test.numberOfSegments());
    }

    private static Point[] getPointsFromFile(String fileName) {
        In in = new In(fileName);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        return points;
    }
}
