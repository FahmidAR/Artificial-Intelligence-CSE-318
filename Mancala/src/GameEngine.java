import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;


public class GameEngine {

    static int depthTree = 12;
    static int depthTreeTwo = 12;
    static int weightOne = 3;
    static int weightTwo = 2;
    static int weightThree = 1;
    static int weightFour = 1;

    public static void main(String[] args) {

        Scanner userInput = new Scanner(System.in);
        Scanner userInput2 = new Scanner(System.in);
        Scanner userInput3 = new Scanner(System.in);

        System.out.println("+=================================+");
        System.out.println("|         Mancala Game            |");
        System.out.println("|         Fahmid Al Rifat         |");
        System.out.println("|         Roll: 1705087           |");
        System.out.println("+=================================+\n");

        int playerOneOption = 1;
        int playerTwoOption = 1;

        System.out.println("Choose Player One = ");
        System.out.println("-- Press 1 for Ai Agent :)  ");
        System.out.println("-- Press 2 for Human Agent :D  \n");
        System.out.print("-- Enter Choice = \n");

        playerOneOption=userInput2.nextInt();
        Context conOne = new Context(new HeuristicOne());

        if(playerOneOption==1)
        {
            System.out.println("-- Ai Difficulty Peremeter");
            System.out.print("-- Enter Depth (2-20) = ");
            depthTree=userInput3.nextInt();
            System.out.print("-- Enter Heuristic (1-6) = ");
            int n=userInput3.nextInt();

            if(n==1)
            {
                conOne=new Context(new HeuristicOne());
            }
        }

        System.out.println("\nChoose Player Two = ");
        System.out.println("-- Press 1 for Ai Agent XD  ");
        System.out.println("-- Press 2 for Human Agent :p  \n");
        System.out.print("-- Enter Choice = \n");

        playerTwoOption=userInput2.nextInt();
        Context conTwo = new Context(new HeuristicOne());

        if(playerTwoOption==1)
        {
            System.out.println("-- Ai Difficulty Peremeter");
            System.out.print("-- Enter Depth (2-20) = ");
            depthTreeTwo=userInput3.nextInt();
            System.out.print("-- Enter Heuristic (1-6) = ");
            int n=userInput3.nextInt();

            if(n==1)
            {
                conTwo=new Context(new HeuristicOne());
            }
        }

        State game = new State();
        game.PrintBoard();

        while (true)
        {
            int a= depthTree,b=depthTree;
            int ai = minMaxMancalaPruningAi(game,a,b,Integer.MIN_VALUE,Integer.MAX_VALUE,1,conOne);
            System.out.println("Player One Best move is = "+ String.valueOf(ai));

            int move;

            if(playerOneOption==1)
            {
                move=ai;
            }
            else
            {
                System.out.print("Give Move Player 1 (bottom), Enter Your Block no (1-6) = ");
                move = userInput.nextInt();
            }

            if(move==0||move>6)
            {
                System.out.println("Player One Left , Other Player Win !!");
                break;
            }
            int moveGame= game.gameMove(1,move);

            while (moveGame==1)
            {
                game.PrintBoard();

                a= depthTree;b=depthTree;
                ai = minMaxMancalaPruningAi(game,a,b,Integer.MIN_VALUE,Integer.MAX_VALUE,1,conOne);
                System.out.println("Player One Best move is = "+ String.valueOf(ai));

                System.out.println("Player Player 1 (bottom) got Free move :D ");

                if(playerOneOption==1)
                {
                    move=ai;
                }
                else
                {
                    System.out.print("Give Move Player 1 (bottom), Enter Your Block no (1-6) = ");
                    move = userInput.nextInt();
                }

                moveGame= game.gameMove(1,move);

            }
            if(moveGame==2)
            {
                System.out.println("Player Player 1 (bottom) Captured some Bins  :) ");
            }
            else if(moveGame==3)
            {
                game.PrintBoard();
                System.out.println("Game Over , Player Player 1 (bottom) Win ;) ");
                break;
            }
            else if(moveGame==4)
            {
                game.PrintBoard();
                System.out.println("Game Over , Player Player 2 (up) Win ;) ");
                break;
            }
            else if(moveGame==5)
            {
                game.PrintBoard();
                System.out.println("Game Over , DRAW !!! XD ");
                break;
            }
            game.PrintBoard();

            a= depthTreeTwo;b=depthTreeTwo;
            ai = minMaxMancalaPruningAi(game,a,b,Integer.MIN_VALUE,Integer.MAX_VALUE,2,conTwo);
            System.out.println("Player Two Best move is = "+ String.valueOf(ai));

            if(playerTwoOption==1)
            {
                move=ai;
            }
            else
            {
                System.out.print("Give Move Player 2 (up), Enter Your Block no (1-6) = ");
                move = userInput.nextInt();
            }

            if(move==0||move>6)
            {
                System.out.println("Player One Left , Other Player Win !!");
                break;
            }
            moveGame= game.gameMove(2,move);

            while (moveGame==1)
            {
                game.PrintBoard();

                a= depthTreeTwo;b=depthTreeTwo;
                ai = minMaxMancalaPruningAi(game,a,b,Integer.MIN_VALUE,Integer.MAX_VALUE,2,conTwo);
                System.out.println("Player Two Best move is = "+ String.valueOf(ai));

                System.out.println("Player 2 (up) got Free move :D ");

                if(playerTwoOption==1)
                {
                    move=ai;
                }
                else
                {
                    System.out.print("Give Move Player 2 (up), Enter Your Block no (1-6) = ");
                    move = userInput.nextInt();
                }

                moveGame= game.gameMove(2,move);

            }
            if(moveGame==2)
            {
                System.out.println("Player Player 2 (up) Captured some Bins  :) ");
            }
            else if(moveGame==3)
            {
                game.PrintBoard();
                System.out.println("Game Over , Player Player 1 (bottom) Win ;) ");
                break;
            }
            else if(moveGame==4)
            {
                game.PrintBoard();
                System.out.println("Game Over , Player Player 2 (up) Win ;) ");
                break;
            }
            else if(moveGame==5)
            {
                game.PrintBoard();
                System.out.println("Game Over , DRAW !!! XD ");
                break;
            }
            game.PrintBoard();
        }

    }

    public interface Strategy {
        public int doOperation(State current,int user);
    }

    public static class HeuristicOne implements Strategy{
        @Override
        public int doOperation(State current,int user){
            int HeuristicOne = 0 ;

            if(user==1)
            {
                return current.binsNum[current.totalBlockMid]-current.binsNum[0];
            }
            else
            {
                return current.binsNum[0]-current.binsNum[current.totalBlockMid];
            }
        }
    }

    public static class HeuristicTwo implements Strategy{
        @Override
        public int doOperation(State current,int user){
            int HeuristicTwo = 0 ;


            return HeuristicTwo;
        }
    }


    public static class HeuristicThree implements Strategy{
        @Override
        public int doOperation(State current,int user){
            int HeuristicThree = 0 ;


            return HeuristicThree;
        }
    }

    public static class Context {
        private Strategy strategy;

        public Context(Strategy strategy){
            this.strategy = strategy;
        }

        public int executeStrategy(State current,int user){
            return strategy.doOperation(current,user);
        }
    }

    public static int minMaxMancalaPruningAi(State stateRun , int depth, int fixDepth ,int alpha , int beta , int user ,Context context)
    {
        //stateRun.PrintBoard();

        if(depth==0||stateRun.isGameOver())
        {
            if(user==1)
            {
                return context.executeStrategy(stateRun,1);
            }
            else
            {
                return context.executeStrategy(stateRun,1);
            }
        }

        if(user==1)
        {
            //int maxHeuVal = Integer.MIN_VALUE;
            int maxHeuVal = Integer.MIN_VALUE;
            int optMove = -1;

            for (int i = 1; i < stateRun.totalBlockMid; i++)
            {
                int heuVal= Integer.MIN_VALUE;
                if(stateRun.binsNum[i]>0)
                {
                    State temp = new State();

                    temp=stateCopy(stateRun,temp);

                    if(temp.gameMove(1,i)==1)
                    {
                        heuVal = minMaxMancalaPruningAi(temp,depth-1,fixDepth,alpha,beta,1,context);
                    }
                    else
                    {
                        heuVal = minMaxMancalaPruningAi(temp,depth-1,fixDepth,alpha,beta,2,context);
                    }
                }

                if(heuVal>maxHeuVal)
                {
                    maxHeuVal = heuVal;
                    optMove = i;
                }

                if(heuVal>alpha)
                {
                    alpha=heuVal;
                }

                if (beta<=alpha)
                {
                    break;
                }
            }

            if(depth==(fixDepth))
            {
                return optMove;
            }
            else
            {
                return maxHeuVal;
            }

        }
        else
        {
            int minHeuVal = Integer.MAX_VALUE;
            int optMove = -1;

            for (int i = 1; i < stateRun.totalBlockMid; i++)
            {
                int heuVal= Integer.MAX_VALUE;
                int c = stateRun.totalBlockMid-i;
                if(stateRun.binsNum[stateRun.totalBlockMid+c]>0)
                {
                    //System.out.println("meu");
                    State temp = new State();
                    temp=stateCopy(stateRun,temp);

                    if(temp.gameMove(2,i)==1)
                    {
                        heuVal = minMaxMancalaPruningAi(temp,depth-1,fixDepth,alpha,beta,2,context);
                    }
                    else
                    {
                        heuVal = minMaxMancalaPruningAi(temp,depth-1,fixDepth,alpha,beta,1,context);
                    }
                }

                if(heuVal<minHeuVal)
                {
                    minHeuVal = heuVal;
                    optMove = i;
                }

                if(heuVal<beta)
                {
                    beta=heuVal;
                }

                if (beta<=alpha)
                {
                    break;
                }
            }

            if(depth==(fixDepth))
            {
                return optMove;
            }
            else
            {
                return minHeuVal;
            }
        }
    }

    public static State stateCopy(State A ,State B)
    {

        for (int i = 0; i < A.totalBlock; i++) {
            B.binsNum[i]=A.binsNum[i];
        }
        return B;
    }

}
