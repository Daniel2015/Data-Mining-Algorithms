package pkg3.nqueensproblem.minconflicts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class NQueensProblemMinConflicts {

    static int MAX_ITER;
    static int N;
    static Board startingState;
    static int[][] mapOfConflicts;
    static int[] positionOfQueens;

    public static void main(String[] args) {
        System.out.print("N: ");
        Scanner nInput = new Scanner(System.in);
        N = nInput.nextInt();

        MAX_ITER = 1000 * N;
        constructStartingState(N);
        minConflictsNQueens();
    }

    public static void constructStartingState(int n) {
        char[][] initialQueenConfiguration = new char[N][N];
        positionOfQueens = new int[N];
        mapOfConflicts = new int[N][N];
        for (int i = 0; i < initialQueenConfiguration.length; i++) {
            for (int j = 0; j < initialQueenConfiguration[0].length; j++) {
                if (i == j) {
                    initialQueenConfiguration[i][j] = '*';
                    positionOfQueens[i] = j;
                } else {
                    initialQueenConfiguration[i][j] = '_';
                }
            }
        }
        startingState = new Board(initialQueenConfiguration);
        findInitialConflicts();
    }

    public static boolean minConflictsNQueens() {
        for (int i = 0; i < MAX_ITER; i++) {
            if (isGoalState() == true) {
                printBoard();
                return true;
            }
            int maxConflictQueenColumn = findIndexOfMaxConflictQueen(positionOfQueens);
            int minConflictQueenRow = findMinConflictInColumn(maxConflictQueenColumn);
            removeQueenFrom(maxConflictQueenColumn, positionOfQueens[maxConflictQueenColumn]);
            insertQueenTo(maxConflictQueenColumn, minConflictQueenRow);
        }
        return false;
    }

    public static boolean isGoalState() {
        int indexOfMaxConflictQueen = findIndexOfMaxConflictQueen(positionOfQueens);
        if (mapOfConflicts[positionOfQueens[indexOfMaxConflictQueen]][indexOfMaxConflictQueen] == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static void updateMapOfConflicts(int ColOfQueen1, int RowOfQueen2) {

    }

    public static void insertQueenTo(int x, int y) {
        for (int i = 0; i < mapOfConflicts.length; ++i) {
            for (int j = 0; j < mapOfConflicts[i].length; ++j) {
                if (i == y && j == x) {
                    continue;
                }
                if (Math.abs(i - y) == Math.abs(j - x) || (i - y == 0 && j - x != 0)) {
                    mapOfConflicts[i][j]++;
                }
            }
        }
        positionOfQueens[x] = y;
    }

    public static void removeQueenFrom(int x, int y) {
        for (int i = 0; i < mapOfConflicts.length; i++) {
            for (int j = 0; j < mapOfConflicts[i].length; j++) {
                if (i == y && j == x) {
                    continue;
                }
                if (Math.abs(i - y) == Math.abs(j - x) || i - y == 0 && j - x != 0) {
                    mapOfConflicts[i][j]--;
                }
            }
        }
    }

    public static int findMinConflictInColumn(int indexOfQueen) {
        int temp[] = new int[mapOfConflicts[0].length];
        for (int i = 0; i < mapOfConflicts[0].length; i++) {
            if (i == positionOfQueens[indexOfQueen]) {
                temp[i] = 1000000;
            } else {
                temp[i] = mapOfConflicts[i][indexOfQueen];
            }
        }
        int smallNumber = Integer.MAX_VALUE;
        List<Integer> indexesOfSmallestConflictValue = new ArrayList<>();
        for (int k = 0; k < temp.length; k++) {
            if (temp[k] < smallNumber) {
                indexesOfSmallestConflictValue.removeAll(indexesOfSmallestConflictValue);
                smallNumber = temp[k];
                indexesOfSmallestConflictValue.add(k);
            } else if (temp[k] == smallNumber) {
                indexesOfSmallestConflictValue.add(k);
            }
        }
        int row = getRandom(indexesOfSmallestConflictValue);
        return row;
    }

    public static void findInitialConflicts() {
        for (int i = 0; i < positionOfQueens.length; i++) {
            insertQueenTo(i, positionOfQueens[i]);
        }
    }

    public static int findIndexOfMaxConflictQueen(int[] queens) {
        int[] temp = new int[queens.length];
        for (int x = 0; x < queens.length; x++) {
            temp[x] = mapOfConflicts[queens[x]][x];
        }
        int largeNumber = -1;
        List<Integer> indexesOfLargestConflictValue = new ArrayList<>();
        for (int k = 0; k < temp.length; k++) {
            if (temp[k] > largeNumber) {
                indexesOfLargestConflictValue.removeAll(indexesOfLargestConflictValue);
                largeNumber = temp[k];
                indexesOfLargestConflictValue.add(k);
            } else if (temp[k] == largeNumber) {
                indexesOfLargestConflictValue.add(k);
            }
        }

        int index = getRandom(indexesOfLargestConflictValue);
        return index;
    }

    public static void printBoard() {
        for (int i = 0; i < mapOfConflicts.length; i++) {
            for (int j = 0; j < mapOfConflicts[0].length; j++) {
                if (positionOfQueens[j] == i) {
                    System.out.print("*");
                    System.out.print(" ");
                } else {
                    System.out.print("_");
                    System.out.print(" ");
                }
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public static void printMapOfConflicts() {
        for (int i = 0; i < mapOfConflicts.length; i++) {
            for (int j = 0; j < mapOfConflicts[0].length; j++) {
                System.out.print(mapOfConflicts[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public static int getRandom(List<Integer> l) {
        int rnd = new Random().nextInt(l.size());
        return l.get(rnd);
    }
}
