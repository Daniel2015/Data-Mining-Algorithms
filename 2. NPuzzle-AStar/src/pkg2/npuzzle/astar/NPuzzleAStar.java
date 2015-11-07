package pkg2.npuzzle.astar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class NPuzzleAStar {

    static int N;
    static Node startingNode;
    static int[][] finalState;
    static PriorityQueue<Node> queue = new PriorityQueue<>(100, (Node a, Node b) -> a.cost - b.cost);
    static HashSet<Node> closedSet = new HashSet<>();

    public static void main(String[] args) {

        System.out.print("N: ");
        Scanner nInput = new Scanner(System.in);
        N = nInput.nextInt();
        constructTables(N);
        AStarSearch(startingNode);
    }

    public static boolean AStarSearch(Node startingState) {
        queue.add(startingState);
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            List<Node> availableNodes = constructPossibleMoves(currentNode);
            for (int i = 0; i < availableNodes.size(); i++) {
                Node successorNode = availableNodes.get(i);
                if (successorNode.heuristicFunction == 0) {
                    System.out.println(successorNode.distanceFromRoot);
                    return true;
                }
                if (!queue.contains(successorNode) && !closedSet.contains(successorNode)) {
                    queue.add(successorNode);
                }
            }
            closedSet.add(currentNode);
        }
        return false;
    }
    
    public static int[][] deepCopyIntMatrix(int[][] input) {
        if (input == null) {
            return null;
        }
        int[][] result = new int[input.length][];
        for (int r = 0; r < input.length; r++) {
            result[r] = input[r].clone();
        }
        return result;
    }

    public static List<Node> constructPossibleMoves(Node parentNode) {
        int[][] childState;
        List<Node> availableEdges = new ArrayList<>();
        for (int i = 0; i < parentNode.state.length; i++) {
            for (int j = 0; j < parentNode.state[0].length; j++) {
                if (parentNode.state[i][j] == 0) {
                    if (i > 0) {
                        childState = deepCopyIntMatrix(parentNode.state);
                        childState[i][j] = childState[i - 1][j];
                        childState[i - 1][j] = 0;
                        availableEdges.add(new Node(childState, parentNode.distanceFromRoot + 1, parentNode));

                    }
                    if (i < parentNode.state.length - 1) {
                        childState = deepCopyIntMatrix(parentNode.state);
                        childState[i][j] = childState[i + 1][j];
                        childState[i + 1][j] = 0;
                        availableEdges.add(new Node(childState, parentNode.distanceFromRoot + 1, parentNode));

                    }
                    if (j > 0) {
                        childState = deepCopyIntMatrix(parentNode.state);
                        childState[i][j] = childState[i][j - 1];
                        childState[i][j - 1] = 0;
                        availableEdges.add(new Node(childState, parentNode.distanceFromRoot + 1, parentNode));

                    }
                    if (j < parentNode.state[0].length - 1) {
                        childState = deepCopyIntMatrix(parentNode.state);
                        childState[i][j] = childState[i][j + 1];
                        childState[i][j + 1] = 0;
                        availableEdges.add(new Node(childState, parentNode.distanceFromRoot + 1, parentNode));

                    }
                }
            }
        }
        return availableEdges;
    }

    public static void constructTables(int n) {
        int[][] startingState = new int[n][n];
        finalState = new int[n][n];
        Scanner tableStartingInput = new Scanner(System.in);
        Scanner tableFinalInput = new Scanner(System.in);
        System.out.println("Input starting state for " + n + "x" + n + " table:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                startingState[i][j] = tableStartingInput.nextInt();
            }
        }
        System.out.println("Input final state for " + n + "x" + n + " table:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                finalState[i][j] = tableFinalInput.nextInt();
            }
        }
        startingNode = new Node(startingState, 0, null);
    }
}