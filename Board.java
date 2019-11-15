/* *****************************************************************************
 *  Name:              Jiaojiao Fan
 *  Last modified:     11/05/2019
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private final int n;
    private int iBlank; // the index of the blank square
    private int jBlank;
    private final int[][] board;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    // 0 represents the blank square. Assume that 2 ≤ n < 128.
    public Board(int[][] tiles) {
        if (tiles == null) throw new IllegalArgumentException("tiles are null");
        n = tiles.length;
        board = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                board[i][j] = tiles[i][j];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) {
                    iBlank = i;
                    jBlank = j;
                }
            }
        }
    }

    @Override
    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int dHamming = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == n - 1 && j == n - 1) break;
                if (board[i][j] != i * n + j + 1) dHamming++;
            }
        }
        return dHamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int dManhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int value = board[i][j];
                if (value != 0 && value != i * n + j + 1) {
                    int row = (value - 1) / n;
                    int col = (value - 1) % n;
                    dManhattan += Math.abs(row - i) + Math.abs(col - j);
                }
            }
        }
        return dManhattan;
    }


    // is this board the goal board?
    public boolean isGoal() {
        return (hamming() == 0 && manhattan() == 0);
    }

    @Override
    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return Arrays.deepEquals(this.board, that.board); // according to the assignment Q&A
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        // We don't need to add a new ListIterator like before
        // Your client code should not depend on whether the iterable
        // returned is a stack or queue (because it could be some any iterable)!!!
        ArrayList<Board> listNeighbors = new ArrayList<>();
        // the left one
        if (iBlank - 1 >= 0) {
            exch(board, iBlank, jBlank, iBlank - 1, jBlank);
            listNeighbors.add(new Board(board));
            exch(board, iBlank, jBlank, iBlank - 1, jBlank);
        }
        if (iBlank + 1 < n) {
            exch(board, iBlank, jBlank, iBlank + 1, jBlank);
            listNeighbors.add(new Board(board));
            exch(board, iBlank, jBlank, iBlank + 1, jBlank);
        }
        if (jBlank - 1 >= 0) {
            exch(board, iBlank, jBlank, iBlank, jBlank - 1);
            listNeighbors.add(new Board(board));
            exch(board, iBlank, jBlank, iBlank, jBlank - 1);
        }
        if (jBlank + 1 < n) {
            exch(board, iBlank, jBlank, iBlank, jBlank + 1);
            listNeighbors.add(new Board(board));
            exch(board, iBlank, jBlank, iBlank, jBlank + 1);
        }
        return listNeighbors;
    }

    private void exch(int[][] tiles, int rowO, int colO, int rowE, int colE) {
        int temp = tiles[rowO][colO];
        tiles[rowO][colO] = tiles[rowE][colE];
        tiles[rowE][colE] = temp;
    }

    // a board that is obtained by exchanging any pair of tiles
    // So we don't need to exchange the random one
    // We only need to exchange the special one. it's Okay.
    public Board twin() {
        int[][] twinB = new int[n][n];
        // twinB = board;
        //这个地方是不可以写成上面这种的！！因为改twinB的同时也会把board给改掉。
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                twinB[i][j] = board[i][j];
        if (twinB[0][0] != 0 && twinB[0][1] != 0) {
            exch(twinB, 0, 0, 0, 1);
        }
        else {
            exch(twinB, n - 1, n - 1, n - 1, n - 2);
        }
        return new Board(twinB);
    }
}

