package pkg1.frogleappuzzle.dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class FrogLeapPuzzleDFS {

    public static Stack<Integer[]> myStack = new Stack<>();
    public static List<Integer[]> pathToSearchedState = new ArrayList<>();

    public static void main(String[] args) {
        System.out.print("N: ");
        Scanner userInput = new Scanner(System.in);
        Integer n = userInput.nextInt();
        Integer[] startingState = constructStartingState(n);
        Integer[] finalState = constructFinalState(startingState);
        DFS(startingState, finalState);
        printPathSearchedState();
    }

    public static Integer[] constructStartingState(Integer n) {
        Integer[] state = new Integer[n * 2 + 1];
        for (int i = 0; i < (n * 2 + 1); i++) {
            if (i < n) {
                state[i] = 1;
            } else if (i == n) {
                state[i] = 0;
            } else {
                state[i] = 2;
            }
        }
        return state;
    }

    public static Integer[] constructFinalState(Integer[] startingState) {
        Integer[] finalState = new Integer[startingState.length];
        for (int i = 0; i < startingState.length; i++) {
            finalState[i] = startingState[startingState.length - i - 1];
        }
        return finalState;
    }

    public static void DFS(Integer[] startingState, Integer[] finalState) {
        pathToSearchedState.add(startingState);
        if (Arrays.equals(startingState, finalState)) {
            myStack.push(finalState);
        } else {
            myStack.push(startingState);
            List<Integer[]> availableEdges = findAvailableEdges(startingState, finalState);
            if (availableEdges.isEmpty()) {
                myStack.pop();
            } else {
                for (int i = 0; i < availableEdges.size(); i++) {
                    if (myStack.peek().equals(finalState)) {
                        break;
                    }
                    DFS(availableEdges.get(i), finalState);
                }
            }
        }
    }

    public static void printPathSearchedState() {
        for (int i = 0; i < pathToSearchedState.size(); i++) {
            System.out.println(Arrays.toString(pathToSearchedState.get(i)));
        }
    }

    public static List<Integer[]> findAvailableEdges(Integer[] state, Integer[] finalState) {
        Integer[] originalState;
        List<Integer[]> availableEdges = new ArrayList<>();
        for (int i = 0; i < state.length; i++) {
            if (i + 1 < state.length) {
                originalState = state.clone();
                if (originalState[i] == 1 && originalState[i + 1] == 0) {
                    originalState[i] = 0;
                    originalState[i + 1] = 1;
                    availableEdges.add(originalState);
                }
            }
            if (i + 2 < state.length) {
                originalState = state.clone();
                if (originalState[i] == 1 && originalState[i + 2] == 0) {
                    originalState[i] = 0;
                    originalState[i + 2] = 1;
                    availableEdges.add(originalState);
                }
            }
            if (i - 1 >= 0) {
                originalState = state.clone();
                if (originalState[i] == 2 && originalState[i - 1] == 0) {
                    originalState[i] = 0;
                    originalState[i - 1] = 2;
                    availableEdges.add(originalState);
                }
            }
            if (i - 2 >= 0) {
                originalState = state.clone();
                if (originalState[i] == 2 && originalState[i - 2] == 0) {
                    originalState[i] = 0;
                    originalState[i - 2] = 2;
                    availableEdges.add(originalState);
                }
            }
        }
        return availableEdges;
    }
}
