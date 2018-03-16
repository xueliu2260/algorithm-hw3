import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class FastCollinearPointsHashMap {
    private LineSegment[] lineSegments;
    private ArrayList<LineSegment> tempList = new ArrayList<>();
    private HashMap<Double, ArrayList<Point>> endPointsMap = new HashMap<>();
    public FastCollinearPointsHashMap(Point[] points){
        checkDuplicate(points);

        if(points == null){
            throw new IllegalArgumentException();
        }

        Point[] pointCopy = Arrays.copyOf(points, points.length);

        for(int i = 0; i < points.length; i++){
            Point firstPoint = points[i];
            Arrays.sort(pointCopy, firstPoint.slopeOrder());
            ArrayList<Point> linePoints = new ArrayList<>();
            double preSlope = Double.NEGATIVE_INFINITY;
            double curSlope = 0;
            for(int j = 1; j < pointCopy.length; j++){

                curSlope = firstPoint.slopeTo(pointCopy[j]);
                if(preSlope == curSlope){
                    linePoints.add(pointCopy[j]);
                }else {
                    if(linePoints.size() >= 3){
                        linePoints.add(firstPoint);
                        addSegmentsIfNew(preSlope, linePoints);
                    }
                    linePoints.clear();
                    linePoints.add(pointCopy[j]);

                }
                preSlope = curSlope;
            }
            if(linePoints.size() >= 3){
                linePoints.add(firstPoint);
                addSegmentsIfNew(preSlope, linePoints);
            }

        }

    }     // finds all line segments containing 4 or more points

    private void addSegmentsIfNew(double slope, ArrayList<Point> linePonits){
        Collections.sort(linePonits);
        Point startPoint = linePonits.get(0);
        Point endPoint = linePonits.get(linePonits.size()-1);

        ArrayList<Point> endList = endPointsMap.get(slope);
        if(endList == null){
            endList = new ArrayList<>();
            endList.add(endPoint);
            endPointsMap.put(slope, endList);
            tempList.add(new LineSegment(startPoint, endPoint));
        }else {
            for(Point point : endList){
                if (point.compareTo(endPoint) == 0){
                    return;
                }
            }
            endList.add(endPoint);
            tempList.add(new LineSegment(startPoint, endPoint));
        }

    }
    public int numberOfSegments(){
        return lineSegments.length;
    }        // the number of line segments
    public LineSegment[] segments(){
        lineSegments = tempList.toArray(new LineSegment[tempList.size()]);

        return lineSegments;
    }                // the line segments
    private void checkDuplicate(Point[] points){
        for(int i = 0; i < points.length; i++){
            for(int j = i + 1; j < points.length; j++){
                if(points[i] == null || points[j] == null || points[i].compareTo(points[j]) == 0){
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


        FastCollinearPointsHashMap test = new FastCollinearPointsHashMap(points);
        for (LineSegment segment : test.segments()) {
            segment.draw();
        }

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
