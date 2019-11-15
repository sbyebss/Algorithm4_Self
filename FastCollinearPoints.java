/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private int numberSegment;
    // private Point[] p;
    private final ArrayList<LineSegment> lines;
    private double slope1;
    private double slope2;
    // private double[] slope2p;
    // private Point[] otherP;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        numberSegment = 0;
        int n = points.length;
        // how do I select the origin point?
        Point[] p = new Point[n];
        for (int i = 0; i < n; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            p[i] = points[i];
        }
        Arrays.sort(p);

        for (int i = 0; i < n - 1; i++) {
            if (p[i].compareTo(p[i + 1]) == 0) throw new IllegalArgumentException();
        }
        lines = new ArrayList<LineSegment>(); // initialize a new null arraylist
        Point[] temp = Arrays.copyOf(p, n);

        for (int i = 0; i < n; i++) {
            Arrays.sort(temp, p[i].slopeOrder());
            //below is my imperfect code
            /*for (int j = 1; j < n - 2; j++) {
                // Point firstPoint = temp[j];
                int indexEndP = j + 2;
                if (p[i].slopeTo(temp[j]) == p[i].slopeTo(temp[j + 1]) &&
                        p[i].slopeTo(temp[j + 1]) == p[i].slopeTo(temp[j + 2]) &&
                        p[i].slopeTo(temp[j + 2]) == p[i].slopeTo(temp[j + 3])) {
                    while (p[i].slopeTo(temp[indexEndP]) == p[i].slopeTo(temp[indexEndP + 1]) &&
                            indexEndP < n - 1) {
                        indexEndP++;
                    }
                    LineSegment line = new LineSegment(p[i], p[indexEndP]);
                    lines.add(line);
                    numberSegment++;
                }
            }*/
            Point min = p[i];
            Point max = p[i];
            int count = 2;
            for (int j = 0; j < n - 1; j++) {
                slope1 = p[i].slopeTo(temp[j]);
                slope2 = p[i].slopeTo(temp[j + 1]);
                if (slope1 == slope2) {
                    if (temp[j + 1].compareTo(max) > 0) {
                        // this means locate at its right up direction
                        max = temp[j + 1];
                    }
                    else if (temp[j + 1].compareTo(min) < 0) {
                        min = temp[j + 1];
                    }
                    count++;
                    if (j == n - 2 && count >= 4 && !(p[i] == max) && p[i].compareTo(min) == 0) {
                        lines.add(new LineSegment(p[i], max));
                        numberSegment++;

                    }
                }
                else {
                    if (count >= 4 && !(p[i] == max) && p[i].compareTo(min) == 0) {
                        lines.add(new LineSegment(p[i], max));
                        numberSegment++;
                    }
                    if (p[i].compareTo(temp[j + 1]) > 0) {
                        max = p[i];
                        min = temp[j + 1];
                        count = 2;
                    }
                    else {
                        max = temp[j + 1];
                        min = p[i];
                        count = 2;
                    }
                }
            }
            /*Point min = p[i];
            Point max = p[i];
            int count = 2;
            for (int j = 0; j < n - 1; j++) {
                slope1 = p[i].slopeTo(temp[j]);
                slope2 = p[i].slopeTo(temp[j + 1]);
                if (slope1 == slope2) {
                    if (temp[j + 1].compareTo(max) > 0) {
                        max = temp[j + 1];
                    }
                    else if (temp[j + 1].compareTo(min) < 0) {
                        min = temp[j + 1];
                    }
                    count++;
                    if (j == n - 2 && count >= 4 && p[i].compareTo(min) == 0) {
                        lines.add(new LineSegment(min, max));
                        numberSegment++;
                    }
                }
                else {
                    if (count >= 4 && p[i].compareTo(min) == 0) {
                        lines.add(new LineSegment(min, max));
                        numberSegment++;
                    }
                    if (p[i].compareTo(temp[j + 1]) > 0) {
                        max = p[i];
                        min = temp[j + 1];
                        count = 2;
                    }
                    else {
                        max = temp[j + 1];
                        min = p[i];
                        count = 2;
                    }
                }
            }*/
        }
    }


    public int numberOfSegments()        // the number of line segments
    {
        return numberSegment;
    }

    // the line segments
    public LineSegment[] segments() {
        return lines.toArray(new LineSegment[numberOfSegments()]);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
