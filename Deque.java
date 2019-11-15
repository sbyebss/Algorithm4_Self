import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int n;                           // size of deque
    private Node first;                      // the head of deque
    private Node last;                      // the tail of deque

    private class Node {
        private Item item;
        private Node next;
        private Node pre;
    }

    public Deque()                           // construct an empty deque
    {
        first = null;
        last = null;
        n = 0;
    }

    public boolean isEmpty()                 // is the deque empty?
    {
        return first == null && last == null;
    }

    public int size()                        // return the number of items on the deque
    {
        return n;
    }

    public void addFirst(Item item)          // add the item to the front
    {
        if (item == null) throw new IllegalArgumentException("add null item");
        if (isEmpty()) {
            first = new Node();
            first.item = item;
            last = first;
        }
        else {
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.next = oldfirst;
            oldfirst.pre = first;
        }
        n++;
    }

    public void addLast(Item item)           // add the item to the end
    {
        if (item == null) throw new IllegalArgumentException("add null item");
        if (isEmpty()) {
            first = new Node();
            first.item = item;
            last = first;
        }
        else {
            Node oldlast = last;
            last = new Node();
            last.item = item;
            last.pre = oldlast;
            oldlast.next = last;
        }
        n++;
    }

    public Item removeFirst()                // remove and return the item from the front
    {
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = first.item;
        if (n > 1) {
            Node oldfirst = first;
            first = first.next;
            first.pre = null;
            oldfirst.next = null;
        }
        else {
            first = null;
            last = null;
        }
        n--;
        return item;
    }

    public Item removeLast()                 // remove and return the item from the end
    {
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = last.item;
        if (n > 1) {
            Node oldlast = last;
            last = last.pre;
            last.next = null;
            oldlast.pre = null;
        }
        else {
            first = null;
            last = null;
        }
        n--;
        return item;
    }

    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args)   // unit testing (optional)
    {
        Deque<String> deque = new Deque<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-"))
                deque.addLast(item);
            else if (!deque.isEmpty())
                StdOut.print(deque.removeLast() + " ");
        }
        StdOut.println("(" + deque.size() + " left on deque)");
    }
}
