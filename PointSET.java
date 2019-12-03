import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;

public class PointSET {
    private SET<Point2D> set;

    public PointSET() {
        // construct an empty set of points
        /* We don't need to use this!!!
        set = new SET<Point2D>();*/
        set = new SET<>();
    }

    public boolean isEmpty() {
        // is the set empty?
        return set.isEmpty();
    }

    public int size() {
        // number of points in the set
        return set.size();
        // already coded in the TreeSet
    }

    public void insert(Point2D p) {  // add the point to the set (if it is not already in the set)
        if (p == null) throw new IllegalArgumentException("called insert() with a null point");
        set.add(p);
    }

    public boolean contains(Point2D p) {            // does the set contain point p?
        if (p == null) throw new IllegalArgumentException("called contains() with a null point");
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D pDraw : set) pDraw.draw();
    }


    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle (or on the boundary)
        if (rect == null)
            throw new IllegalArgumentException("called Iterable() with a null rectangle");
        // return new ListPoints();
        Stack<Point2D> listPoints = new Stack<>();
        for (Point2D pIn : set) {
            if (rect.contains(pIn)) listPoints.push(pIn);
        }
        return listPoints;
    }

    /* private class ListPoints implements Iterator<Point2D> {

     }*/
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("called nearest() with a null point");
        if (set.isEmpty()) return null;
        double minDistance = Integer.MAX_VALUE;
        double dNeighbor = p.distanceSquaredTo(set.min());
        // Point2D pNeighbor = new Point2D();
        Point2D pNeighbor = null;
        for (Point2D pFind : set) {
            if (p.distanceSquaredTo(pFind) < minDistance) {
                pNeighbor = pFind;
                minDistance = p.distanceSquaredTo(pFind);
            }
        }
        return pNeighbor;
    }

    public static void main(String[] args) {

    }
}
