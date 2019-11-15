/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private int numberSegments; // the number of line segments
    // private Point[] p;
    private final ArrayList<LineSegment> lines;

    public BruteCollinearPoints(Point[] points) { // finds all line segments containing 4 points
        numberSegments = 0;
        int n = points.length;
        Point[] p = new Point[n];
        for (int i = 0; i < n; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            p[i] = points[i];
        }
        Arrays.sort(p); // the natural order:Sorts the elements of the array of an object type into
        // ascending order, using the order defined by Comparable interface, which defines the compareTo method
        for (int i = 0; i < n - 1; i++) {
            if (p[i].compareTo(p[i + 1]) == 0) throw new IllegalArgumentException();
        }
        lines = new ArrayList<LineSegment>(); // initialize a new null arraylist
        for (int i = 0; i < n - 3; i++) {
            for (int j = i + 1; j < n - 2; j++) {
                for (int m = j + 1; m < n - 1; m++) {
                    for (int q = m + 1; q < n; q++) {
                        if (p[i].slopeTo(p[j]) == p[j].slopeTo(p[m])
                                && p[m].slopeTo(p[q]) == p[j].slopeTo(p[m])) {
                            LineSegment line = new LineSegment(p[i], p[q]);
                            lines.add(line);
                            numberSegments++;
                        }
                    }
                }
            }
        }

    }

    public int numberOfSegments() { // the number of line segments
        return numberSegments;
    }

    public LineSegment[] segments() { // the line segments: change from the LineList to the array
        return lines.toArray(new LineSegment[numberOfSegments()]);
    }

    public static void main(String[] args) {
        //read  n points from the file???
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        //    draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32000);
        StdDraw.setYscale(0, 32000);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        //    print and draw the line segments
        BruteCollinearPoints lnPs = new BruteCollinearPoints(points);
        // how to determine the argement?
        // by seeing the construction function
        for (LineSegment segment : lnPs.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
