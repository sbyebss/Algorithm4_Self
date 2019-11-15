/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> testQueue = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            if (testQueue.size() < k) testQueue.enqueue(StdIn.readString());
            else break;
            // if (!item.equals("-")) StdOut.print(TestQueue.dequeue() + " ");
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(testQueue.dequeue());
        }
    }
}
