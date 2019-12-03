// Jiaojiao 20191201

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class KdTree {
    private Node root;
    private int size;

    private class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        // we can set a new constructor here
        public Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
        }
    }

    // construct an empty set of points
    public KdTree() {
        // root = new Node();
        root = null;
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("called insert() with a null point");
        if (root == null) root = new Node(p, new RectHV(0, 0, 1, 1));
        else root = insert(root, p, false);
        size++;
    }

    private Node insert(Node rootNew, Point2D pInsert, boolean flag) {
        if (rootNew.p.equals(pInsert)) {
            size--;
            return root;
        }
        // flag=false represents direct comparing with the root and compare by x;
        double cmp;
        if (flag) cmp = rootNew.p.y() - pInsert.y();
        else cmp = rootNew.p.x() - pInsert.x();
        // pInsert is smaller than the Node, go left
        if (cmp > 0) {
            if (rootNew.lb == null) {
                if (!flag)
                    rootNew.lb = new Node(pInsert,
                                          new RectHV(rootNew.rect.xmin(), rootNew.rect.ymin(),
                                                     rootNew.p.x(), rootNew.rect.ymax()));
                else
                    rootNew.lb = new Node(pInsert,
                                          new RectHV(rootNew.rect.xmin(), rootNew.rect.ymin(),
                                                     rootNew.rect.xmax(), rootNew.p.y()));
            }
            else {
                insert(rootNew.lb, pInsert, !flag);
            }
        }
        // go right
        else {
            if (rootNew.rt == null) {
                // compare the x
                if (!flag)
                    rootNew.rt = new Node(pInsert,
                                          new RectHV(rootNew.p.x(), rootNew.rect.ymin(),
                                                     rootNew.rect.xmax(), rootNew.rect.ymax()));
                    // compare the y
                else
                    rootNew.rt = new Node(pInsert,
                                          new RectHV(rootNew.rect.xmin(), rootNew.p.y(),
                                                     rootNew.rect.xmax(), rootNew.rect.ymax()));
            }
            else {
                insert(rootNew.rt, pInsert, !flag);
            }
        }
        return root;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("called contains() with a null point");
        return get(p) != null;
    }

    private Point2D get(Point2D pGet) {
        return get(root, pGet, false);
    }

    private Point2D get(Node x, Point2D pGet2, boolean flag) {
        if (pGet2 == null) throw new IllegalArgumentException("calls get() with a null key");
        if (x == null) return null;
        // flag=false represents direct comparing with the root and compare by x;
        if (x.p.equals(pGet2)) return x.p;
        double cmp;
        if (flag) cmp = x.p.y() - pGet2.y();
        else cmp = x.p.x() - pGet2.x();
        // >/< is so important
        if (cmp > 0) return get(x.lb, pGet2, !flag);
            // since I only compared the first value: x/y, I can't get they are totally equal
        else return get(x.rt, pGet2, !flag);
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        StdDraw.setPenRadius(0.01);
        draw(root, false);
    }

    private void draw(Node rootNew, boolean flag) {
        if (rootNew == null) return;
        if (!flag) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(rootNew.p.x(), rootNew.p.y());
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(rootNew.p.x(), rootNew.rect.ymin(), rootNew.p.x(), rootNew.rect.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(rootNew.p.x(), rootNew.p.y());
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(rootNew.rect.xmin(), rootNew.p.y(), rootNew.rect.xmax(), rootNew.p.y());
        }
        draw(rootNew.lb, !flag);
        draw(rootNew.rt, !flag);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        // Each node corresponds to an axis-aligned
        // rectangle in the unit square, which encloses all of the points in its subtree.
        // all points that are inside the rectangle (or on the boundary)
        if (rect == null)
            throw new IllegalArgumentException("called Iterable() with a null rectangle");
        // return new ListPoints();
        ArrayList<Point2D> listPoints = new ArrayList<>();
        if (root != null) listPoints.addAll(check(root, rect));
        return listPoints;
    }

    private ArrayList<Point2D> check(Node ndCheck, RectHV rect) {
        ArrayList<Point2D> listPoint = new ArrayList<>();
        if (rect.intersects(ndCheck.rect)) {
            if (rect.contains(ndCheck.p)) {
                listPoint.add(ndCheck.p);
            }
            if (ndCheck.lb != null) listPoint.addAll(check(ndCheck.lb, rect));
            if (ndCheck.rt != null) listPoint.addAll(check(ndCheck.rt, rect));
        }
        return listPoint;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("called nearest() with a null point");
        if (root == null) return null;
        // choose the subtree that is on the same side of the splitting line
        // as the query point as the first subtree to explore
        Point2D closest = root.p;
        // double distance = p.distanceSquaredTo(root.p);
        return nearest(root, p, closest, false);
        // return nearest(root, p, distance, false);
    }

    // you cannot just record the distance, you should record the point!
    private Point2D nearest(Node nd, Point2D p, Point2D closest, boolean flag) {
        double distance = closest.distanceSquaredTo(p);
        if (nd == null || nd.rect.distanceSquaredTo(p) >= distance) return closest;
        double disNew = nd.p.distanceSquaredTo(p);
        if (disNew < distance) closest = nd.p;
        // even if current disNew is bigger than the distance, we should stiil check its child.
        // so there is no else .
        double cmp;
        if (flag) cmp = nd.p.y() - p.y();
        else cmp = nd.p.x() - p.x();
        // p is smaller than the Node point, go left
        if (cmp > 0) {
            // double distance = p.distanceSquaredTo(closest);
            closest = nearest(nd.lb, p, closest, !flag);
            closest = nearest(nd.rt, p, closest, !flag);
        }
        // the fact is that even if the first subtree doesn't satisfy the requirement,
        // you should still search the second subtree! you can't skip!!
        else {
            closest = nearest(nd.rt, p, closest, !flag);
            closest = nearest(nd.lb, p, closest, !flag);

        }
        return closest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
    }
}
