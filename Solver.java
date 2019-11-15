/* *****************************************************************************
 *  Name:              Jiaojiao Fan
 *  Coursera User ID:  sbyebss
 *  Last modified:     11/15/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private SearchNode cur;
    private boolean isSolvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("initial==null");
        MinPQ<SearchNode> searchNodeMinPQ = new MinPQ<>();
        // it didn't use the odd-even discussion way to determine the solvable or unsolvable
        searchNodeMinPQ.insert(new SearchNode(initial, null));
        // two PQ???? one for real, the other for the twin?
        MinPQ<SearchNode> searchTwinMinPQ = new MinPQ<>();
        searchTwinMinPQ.insert(new SearchNode(initial.twin(), null));
        while (true) {
            // delete the current minimum one
            cur = searchNodeMinPQ.delMin();
            // if the current one is the goal board
            if (cur.bd.isGoal()) break;
            // add the neighbors to the PQ
            for (Board neighbor : cur.bd.neighbors()) {
                // 1) if the current one is the root.
                // 2) if the current one isn't the root but it is not the previous deleted one
                // then we can add it
                if (cur.previous == null || !neighbor.equals(cur.previous.bd))
                    searchNodeMinPQ.insert(new SearchNode(neighbor, cur));
            }
            // in lockstep
            cur = searchTwinMinPQ.delMin();
            if (cur.bd.isGoal()) break;
            // add the neighbors to the twin PQ
            for (Board neighbor : cur.bd.neighbors()) {
                if (cur.previous == null || !neighbor.equals(cur.previous.bd))
                    searchTwinMinPQ.insert(new SearchNode(neighbor, cur));
            }

        }
        SearchNode detect = cur;
        //就这一个地方错了!!!
        while (detect.previous != null) detect = detect.previous;
        if (detect.bd.equals(initial)) isSolvable = true;
            // this proves this queue containing the goal is the original one
        else isSolvable = false;
        //  this means this queue is the twin one
    }

    // define a SearchNode, this is in fact like a ListNode
    private class SearchNode implements Comparable<SearchNode> {
        private final Board bd; // what will exist in the SearchNode, the current board
        private final SearchNode previous; // this one is the previous SearchNode
        private final int priority; // every SearchNode has a priority and moves!!!!
        private int moves;

        public SearchNode(Board board, SearchNode pre) {
            // when you construct a SearchNode, you could only know its board and previous searchnode
            // the priority and moves are calculated but they are also parts of the SearchNode
            bd = board;
            previous = pre;
            if (pre != null) moves = pre.moves + 1;
            else moves = 0; // maybe this searchnode is the first one
            priority = bd.manhattan() + moves;
        }

        @Override
        public int compareTo(SearchNode that) {
            // that is another Node prepared to compare
            if (this.priority < that.priority) return -1;
            else if (this.priority > that.priority) return 1;
            return 0;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        if (isSolvable) return cur.moves;
        else return -1;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (!isSolvable) return null;
        Stack<Board> solu = new Stack<>();
        SearchNode temp = cur;
        // this place: it is while instead of if!!!
        while (temp != null) {
            solu.push(temp.bd);
            temp = temp.previous;
        }
        return solu;
    }
}
