/* *****************************************************************************
 *  Name:              Jiaojiao Fan
 *  Coursera User ID:  sbyebss@gmail.com
 *  Last modified:     10/28/2019
 **************************************************************************** */


import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int n; // number of items in the RQ ;
    private Item[] items;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[2];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    private void resize(int capacity) {
        assert capacity >= n;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = items[i];
        }
        items = temp;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("item mustn't be null");
        if (n == items.length) resize(2 * items.length);  // double array length if necessary
        items[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("RandomizedQueue underflow");
        int tempIndex = StdRandom.uniform(n);
        Item tempRemove = items[tempIndex];
        items[tempIndex] = items[n - 1];
        items[n - 1] = null;
        n--;
        if (n > 0 && n == items.length / 4) resize(items.length / 2);
        return tempRemove;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("RandomizedQueue underflow");
        return items[StdRandom.uniform(n)]; // generate a pseudo-random integer between 0 and n − 1
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        StdRandom.shuffle(items);
        return new RandomArrayIterator();

    }

    private class RandomArrayIterator implements Iterator<Item> {
        private int i = n - 1;

        public boolean hasNext() {
            return i >= 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return items[i--];
        }
    }


    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) queue.enqueue(item);
            else if (!queue.isEmpty()) StdOut.print(queue.dequeue() + " ");
        }
        StdOut.println("(" + queue.size() + " left on queue)");
        for (String str : queue)
            StdOut.println(str);
    }

}
