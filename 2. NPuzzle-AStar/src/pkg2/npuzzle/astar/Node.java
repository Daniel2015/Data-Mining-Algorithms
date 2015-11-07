package pkg2.npuzzle.astar;

import java.util.Arrays;

public class Node {

    final int[][] state;
    int distanceFromRoot;
    int heuristicFunction;
    int cost;
    Node parent;

    Node(int[][] state, int distance, Node parent) {
        this.parent = parent;
        this.distanceFromRoot = distance;
        this.state = state;
        this.heuristicFunction = computeHeuristicFunction();
        this.cost = this.heuristicFunction + this.distanceFromRoot;
    }

    private int computeHeuristicFunction() {
        int counter = 0;
        for (int i = 0; i < NPuzzleAStar.N; i++) {
            for (int j = 0; j < NPuzzleAStar.N; j++) {
                if (NPuzzleAStar.finalState[i][j] != this.state[i][j]) {
                    counter++;
                }
            }
        }
        return counter;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(state);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        if (!Arrays.deepEquals(this.state, other.state)) {
            return false;
        }
        return true;
    }

    void print() {
        System.out.println("cost = " + this.cost);
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                System.out.print(state[i][j] + " ");
            }
            System.out.println("");
        }
    }

    void printAll() {
        if (parent != null) {
            parent.printAll();
        }
        System.out.println();
        print();
    }
}