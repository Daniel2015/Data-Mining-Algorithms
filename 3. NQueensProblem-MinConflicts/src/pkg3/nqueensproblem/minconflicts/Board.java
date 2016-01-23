package pkg3.nqueensproblem.minconflicts;

public class Board {

    char[][] configuration;

    Board(char[][] configuration) {
        this.configuration = configuration;
    }
    
    void print() {
        for (int i = 0; i < configuration.length; i++) {
            for (int j = 0; j < configuration[0].length; j++) {
                System.out.print(configuration[i][j] + " ");
            }
            System.out.println("");
        }
    }
}
