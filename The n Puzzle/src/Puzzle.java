import java.util.Scanner;

public class Puzzle {

    public static void main(String[] args) {

        Scanner userInput = new Scanner(System.in);
        Scanner userInput2 = new Scanner(System.in);
        Scanner userInput3 = new Scanner(System.in);

        System.out.println("+=================================+");
        System.out.println("|         N-Puzzel Solver         |");
        System.out.println("|         Fahmid Al Rifat         |");
        System.out.println("|         Roll: 1705087           |");
        System.out.println("+=================================+\n");

        int k = 1, n;

        while (true) {
            System.out.println("Enter Your value of K = ");
            k = userInput.nextInt();

            n = k * k - 1;

            if (k >= 3) {
                System.out.println("Your value of n (BLOCKS) is = " + n);
                break;
            } else {
                System.out.println("K Value must be above 3, Try again");
            }

        }

        System.out.println("\nEnter Initail puzzle State with white spaces separating numbers ");
        System.out.println("and hit enter to go to the next row. Use 0 to represent the blank slot");
        System.out.println("Give Input = \n");

        //int[][] state = new int[k][k];
        State initialState = new State(k);

        for (int i = 0; i < k; i++) {

            String[] row;
            String line = userInput2.nextLine();

            if (line == null)
            {
                System.out.println("Enter a valid State!");
                i--;
                continue;
            }

            row = line.split(" ");

            for (int ii = 0; ii < k; ii++)
            {
                if(row[ii].equals("*"))
                {
                    initialState.state[i][ii] = -1;
                }
                else
                {
                    initialState.state[i][ii] = Integer.parseInt(row[ii]);
                }
            }
        }

        System.out.println("\n The Initail state is = \n");
        initialState.PrintBoard();

        State goalState = new State(k);
        int count = 1;

        for (int i = 0; i < k; i++)
        {
            for (int ii = 0; ii < k; ii++)
            {
                goalState.state[i][ii] = count;
                count++;

                if(count == k*k)
                {
                    count= -1;
                }
            }
        }

        System.out.println("\n The Goal state is = \n");
        goalState.PrintBoard();

        if(initialState.Solvable())
        {
            System.out.println("\n## The Puzzle is Solvable ##\n");
        }
        else
        {
            System.out.println("\n## The Puzzle is Un-Solvable ##\n");
        }

        

    }

}

