import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;


public class Sudoku {
    public static void main(String[] args) {


        int i, j, k;


// 1. Create a Model
        Model model = new Model("my first sudoku problem");
// 2. Create variables



        /* the board which is 9 X 9 */
        /* (0, 0) is the top left position and (8, 8) is the bottom right position */
        /*each cell is an integer variable taking their value in [1, 9] */
        IntVar[][] bd = model.intVarMatrix("bd", 6, 6, 1, 5);


        /* the nine blocks or boxes */
        /* each box is an array of 9 integer variables taking their value in [1, 9] */

        IntVar[] b0 = model.intVarArray("b0", 5, 1, 5);
        IntVar[] b1 = model.intVarArray("b1", 5, 1, 5);
        IntVar[] b2 = model.intVarArray("b2", 1, 1, 1);
        IntVar[] b3 = model.intVarArray("b3", 5, 1, 5);
        IntVar[] b4 = model.intVarArray("b4", 5, 1, 5);
        IntVar[] b5 = model.intVarArray("b5", 5, 1, 5);
        IntVar[] b6 = model.intVarArray("b6", 5, 1, 5);
        IntVar[] b7 = model.intVarArray("b7", 5, 1, 5);


// 3. Post constraints


        /* post constraints for the given hints or clues */

        model.arithm (bd[0][1], "=", 1).post();
        model.arithm (bd[0][5], "=", 3).post();

        model.arithm (bd[1][0], "=", 5).post();
        model.arithm (bd[1][2], "=", 5).post();
        model.arithm (bd[1][4], "=", 4).post();

        model.arithm (bd[2][4], "=", 2).post();

        model.arithm (bd[3][5], "=", 4).post();

        model.arithm (bd[5][3], "=", 3).post();


        /* for the nine box variables */
        /* each box variable is associated with appropriate cell positions in board */
        /* for example, b0 [0] is equal to board [0][0] and b0 [8] is equal to board [3][3] */
        /* b1 [0] is equal to board [0][3] and b1 [8] is equal to board [2][5] */
        /* b2 [0] is equal to board [0][6] and b2 [8] is equal to board [2][8] */
        /* Continuing in this way, b8 [8] is equal to board [8][8] */



        model.arithm (bd[0][0], "=", b0[0]).post();
        model.arithm (bd[1][0], "=", b0[1]).post();
        model.arithm (bd[0][1], "=", b0[2]).post();
        model.arithm (bd[1][1], "=", b0[3]).post();
        model.arithm (bd[2][1], "=", b0[4]).post();

        model.arithm (bd[2][0], "=", b1[0]).post();
        model.arithm (bd[3][0], "=", b1[1]).post();
        model.arithm (bd[3][1], "=", b1[2]).post();
        model.arithm (bd[4][1], "=", b1[3]).post();
        model.arithm (bd[4][2], "=", b1[4]).post();

        model.arithm (bd[4][0], "=", b2[0]).post();

        model.arithm (bd[5][0], "=", b3[0]).post();
        model.arithm (bd[5][1], "=", b3[1]).post();
        model.arithm (bd[5][2], "=", b3[2]).post();
        model.arithm (bd[5][3], "=", b3[3]).post();
        model.arithm (bd[5][4], "=", b3[4]).post();

        model.arithm (bd[0][2], "=", b4[0]).post();
        model.arithm (bd[1][2], "=", b4[1]).post();
        model.arithm (bd[2][2], "=", b4[2]).post();
        model.arithm (bd[3][2], "=", b4[3]).post();
        model.arithm (bd[0][3], "=", b4[4]).post();

        model.arithm (bd[1][3], "=", b5[0]).post();
        model.arithm (bd[2][3], "=", b5[1]).post();
        model.arithm (bd[2][4], "=", b5[2]).post();
        model.arithm (bd[3][4], "=", b5[3]).post();
        model.arithm (bd[3][5], "=", b5[4]).post();

        model.arithm (bd[3][3], "=", b6[0]).post();
        model.arithm (bd[4][3], "=", b6[1]).post();
        model.arithm (bd[4][4], "=", b6[2]).post();
        model.arithm (bd[4][5], "=", b6[3]).post();
        model.arithm (bd[5][5], "=", b6[4]).post();

        model.arithm (bd[0][4], "=", b7[0]).post();
        model.arithm (bd[1][4], "=", b7[1]).post();
        model.arithm (bd[0][5], "=", b7[2]).post();
        model.arithm (bd[1][5], "=", b7[3]).post();
        model.arithm (bd[2][5], "=", b7[4]).post();


        /* for the nine row variables */
        /* each row variable is associated with appropriate cell positions in board */


        for ( i = 0; i < 6; i++)
            for ( j = 0; j < 5; j++)
                model.arithm (bd[i][j], "!=", bd[i][j+1]).post();

        for ( i = 0; i < 5; i++)
            for ( j = 0; j < 5; j++)
                model.arithm (bd[i][j], "!=", bd[i+1][j+1]).post();

        for ( i = 1; i < 6; i++)
            for ( j = 0; j < 5; j++)
                model.arithm (bd[i][j], "!=", bd[i-1][j+1]).post();



        for ( j = 0; j < 6; j++)
            for ( i = 0; i < 5; i++)
                model.arithm (bd[i][j], "!=", bd[i+1][j]).post();

        for ( j = 0; j < 5; j++)
            for ( i = 0; i < 5; i++)
                model.arithm (bd[i][j], "!=", bd[i+1][j+1]).post();

        for ( j = 1; j < 6; j++)
            for ( i = 0; i < 5; i++)
                model.arithm (bd[i][j], "!=", bd[i+1][j-1]).post();


        /* post global constraint alldiff for the nine boxes */

        model.allDifferent(b0).post();
        model.allDifferent(b1).post();
        model.allDifferent(b2).post();
        model.allDifferent(b3).post();
        model.allDifferent(b4).post();
        model.allDifferent(b5).post();
        model.allDifferent(b6).post();
        model.allDifferent(b7).post();






// 4. Solve the problem




        Solver solver = model.getSolver();

        solver.showStatistics();
        solver.showSolutions();
        solver.findSolution();


// 5. Print the solution

        for ( i = 0; i < 6; i++)
        {
            for ( j = 0; j < 6; j++)
            {

                System.out.print(" ");
                /* get the value for the board position [i][j] for the solved board */
                k = bd [i][j].getValue();
                System.out.print(k );
            }
            System.out.println();
        }


    }

}