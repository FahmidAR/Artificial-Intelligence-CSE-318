import com.sun.tools.javac.Main;

import javax.print.DocFlavor;
import java.text.DecimalFormat;
import java.util.*;


public class GameEngine {

    static int moveOrder = 1;

    static int depthTree = 12;
    static int depthTreeTwo = 12;

    static int weightOne = 3;
    static int weightTwo = 2;
    static int weightThree = 1;


    static int weightFour = 5;
    static int weightFive = 1;
    static int weightZero = 0;

    public static void main(String[] args) {

        Scanner userInput = new Scanner(System.in);
        Scanner userInput2 = new Scanner(System.in);
        Scanner userInput3 = new Scanner(System.in);
        Scanner userInput4 = new Scanner(System.in);

        System.out.println("+=================================+");
        System.out.println("|         Mancala Game            |");
        System.out.println("|         Fahmid Al Rifat         |");
        System.out.println("|         Roll: 1705087           |");
        System.out.println("+=================================+\n");

        System.out.println("Choose Running Mode = ");
        System.out.println("-- Press 1 for Normal Mode :)  ");
        System.out.println("-- Press 2 for Statistic Mode :D  \n");
        System.out.print("-- Enter Choice = \n");

        int mode =userInput4.nextInt();

        if(mode==1)
        {
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
                conOne=chooseHeu(n);
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
                conTwo=chooseHeu(n);
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
        else
        {
            System.out.println("### Satistic Mode , Mancala >_< ###\n");

            System.out.println("# We Generate Satistic Varrying  ");
            System.out.println("-- Heuristic Combination ");
            System.out.println("-- Weight Combination ");
            System.out.println("-- Depth Combination ");
            System.out.println("-- Different Move Order ");
            System.out.println("-- Different Algo ");

            System.out.println("\nChoose Statistic Preference  = ");
            System.out.println("-- Press 1 for Heuristic Combination with Different Same Depth :)  ");
            System.out.println("-- Press 2 for Same Heuristic with Different Depth :D  ");
            System.out.println("-- Press 3 for Weight Combination with Fix Depth :P  ");
            System.out.println("-- Press 4 Move Order Combination with Fix Depth XD  ");
            System.out.println("-- Press 5 for Different Depth Algo :(  \n");
            System.out.println("-- Press 6 for Fix Heuristic Combination with Different Same Depth :)  ");
            System.out.print("-- Enter Choice = \n");

            int modestat =userInput2.nextInt();

            if(modestat==1)
            {
                System.out.println("\n### Stat One , Heuristic Combination with Different Same Depth :D ###\n");

                for (int ph1=1;ph1<7;ph1++)
                {
                    for (int ph2=1;ph2<7;ph2++)
                    {

                        System.out.println("\n# Player One heu = "+ph1+" Player two heu = "+ph2+" Running :) \n");

                        int win =0, lose = 0 ,draw = 0 ,result = 0 ,total = 0;
                        for (int dep=1;dep<15;dep++) {
                            State game = new State();
                            Context conOne = chooseHeu(ph1);
                            Context conTwo = chooseHeu(ph2);
                            result = Play(game, dep, dep, conOne, conTwo);

                            String resStr = "Player One Win";

                            if (result == 1) {
                                resStr = "Player One Win";
                                win++;
                                total++;
                            } else if (result == -1) {
                                resStr = "Player Two Win";
                                lose++;
                                total++;
                            } else if (result == 0) {
                                resStr = "Game Draw";
                                draw++;
                                total++;
                            }

                            System.out.println("--- Match No = "+total+" Both Player Depth  = "+dep+" Result  "+resStr);

                        }

                        final DecimalFormat df = new DecimalFormat("0.00");
                        double p1,p2 ;

                        p1 = (1.0*win/total)*100;
                        p2 = (1.0*lose/total)*100;

                        System.out.println("\nPlayer One heu = "+ph1+" Player two heu = "+ph2+" Result bellow ");
                        System.out.println("\nPlayer One Win = "+p1+" % Player two Win = "+p2+" %  ");
                    }


                }
            }
            if(modestat==6)
            {
                System.out.println("\n### Stat Two , Fix Heuristic Combination with Different Same Depth :D ###\n");

                System.out.print("Enter The Fix Heu for Player One = ");
                int ph1 = userInput3.nextInt();
                Context conOne = chooseHeu(ph1);
                System.out.print("Enter The Fix Heu for Player Two= ");
                int ph2 = userInput3.nextInt();
                Context conTwo = chooseHeu(ph2);

                System.out.println("\n# Player One heu = "+ph1+" Player two heu = "+ph2+" Running :) \n");

                int win =0, lose = 0 ,draw = 0 ,result = 0 ,total = 0;
                for (int dep=1;dep<15;dep++) {
                    State game = new State();

                    result = Play(game, dep, dep, conOne, conTwo);

                    String resStr = "Player One Win";

                    if (result == 1) {
                        resStr = "Player One Win";
                        win++;
                        total++;
                    } else if (result == -1) {
                        resStr = "Player Two Win";
                        lose++;
                        total++;
                    } else if (result == 0) {
                        resStr = "Game Draw";
                        draw++;
                        total++;
                    }

                    System.out.println("--- Match No = "+total+" Both Player Depth  = "+dep+" Result  "+resStr);

                }

                final DecimalFormat df = new DecimalFormat("0.00");
                double p1,p2 ;

                p1 = (1.0*win/total)*100;
                p2 = (1.0*lose/total)*100;

                System.out.println("\nPlayer One heu = "+ph1+" Player two heu = "+ph2+" Result bellow ");
                System.out.println("\nPlayer One Win = "+p1+" % Player two Win = "+p2+" %  ");

            }
            else if(modestat==2)
            {
                System.out.println("\n### Stat Two , Same Heuristic with Different Depth :D ###\n");
                System.out.print("Enter The Fix Heu = ");
                int heu = userInput3.nextInt();
                Context conOne = chooseHeu(heu);
                Context conTwo = chooseHeu(heu);

                for (int dep1=1;dep1<15;dep1++) {

                    System.out.println("\n# Player One Depth = " + dep1 + " with Player two various depth Running :) \n");

                    int win =0, lose = 0 ,draw = 0 ,result = 0 ,total = 0;

                    for (int dep2 = 1; dep2 < 15; dep2++) {

                        State game = new State();

                        result = Play(game, dep1, dep2, conOne, conTwo);

                        String resStr = "Player One Win";

                        if (result == 1) {
                            resStr = "Player One Win";
                            win++;
                            total++;
                        } else if (result == -1) {
                            resStr = "Player Two Win";
                            lose++;
                            total++;
                        } else if (result == 0) {
                            resStr = "Game Draw";
                            draw++;
                            total++;
                        }

                        System.out.println("--- Match No = "+total+" Player One Depth = "+dep1+" Player Two Depth = "+dep2+" Result  "+resStr);
                    }

                    final DecimalFormat df = new DecimalFormat("0.00");
                    double p1,p2 ;

                    p1 = (1.0*win/total)*100;
                    p2 = (1.0*lose/total)*100;

                    System.out.println("\nPlayer One Depth = "+dep1+" with  Player two Different Depth Result bellow ");
                    System.out.println("\nPlayer One Win = "+p1+" % Player two Win = "+p2+" %  ");
                }
            }
            else if(modestat==4)
            {
                System.out.println("\n### Stat Four , Move Order Combination with Fix Depth XD  :D ###\n");
                System.out.print("Enter The Fix Heu = ");
                int heu = userInput3.nextInt();
                Context conOne = chooseHeu(heu);
                Context conTwo = chooseHeu(heu);
                System.out.print("Enter The Fix Depth = ");
                int dep = userInput3.nextInt();

                for (int run = 1; run < 3; run++) {

                    System.out.println("\n# Move ordering no = "+run);

                    int win =0, lose = 0 ,draw = 0 ,result = 0 ,total = 0;

                    moveOrder=run;

                    for (int run2 = 1; run2 < 100; run2++)
                    {
                        State game = new State();

                        result = Play(game, dep, dep, conOne, conTwo);

                        String resStr = "Player One Win";

                        if (result == 1) {
                            resStr = "Player One Win";
                            win++;
                            total++;
                        } else if (result == -1) {
                            resStr = "Player Two Win";
                            lose++;
                            total++;
                        } else if (result == 0) {
                            resStr = "Game Draw";
                            draw++;
                            total++;
                        }

                        System.out.println("--- Match No = "+total+" Result  "+resStr);

                    }
                    final DecimalFormat df = new DecimalFormat("0.00");
                    double p1,p2 ;

                    p1 = (1.0*win/total)*100;
                    p2 = (1.0*lose/total)*100;

                    System.out.println("\n Move Ordering = "+run+" Result bellow ");
                    System.out.println("\nPlayer One Win = "+p1+" % Player two Win = "+p2+" %  ");
                }



            }
            else if(modestat==3)
            {

                System.out.println("\n### Stat Three , Weight Combination with Fix Depth :D ###\n");
                System.out.print("Enter The Fix Heu = ");
                int heu = userInput3.nextInt();
                Context conOne = chooseHeu(heu);
                Context conTwo = chooseHeu(heu);
                System.out.print("Enter The Fix Depth = ");
                int dep = userInput3.nextInt();

                int w1Max=0,w2Max=0,w3Max=0,w4Max=0;
                double p = 0;

                for (int run = 1; run < 10; run++)
                {
                    Random random = new Random();
                    weightOne = random.nextInt(10 - 1) + 1;
                    weightTwo = random.nextInt(5 - 1) + 1;
                    weightThree = random.nextInt(10 - 1) + 1;
                    weightFour = random.nextInt(10 - 1) + 1;

                    System.out.println("\n# Random weight, one = " + weightOne +" two = " + weightTwo +
                            " three = " + weightThree +" four = " + weightFour +" :) \n");

                    int win =0, lose = 0 ,draw = 0 ,result = 0 ,total = 0;

                    for (int run2 = 1; run2 < 10; run2++)
                    {
                        State game = new State();

                        result = Play(game, dep, dep, conOne, conTwo);

                        String resStr = "Player One Win";

                        if (result == 1) {
                            resStr = "Player One Win";
                            win++;
                            total++;
                        } else if (result == -1) {
                            resStr = "Player Two Win";
                            lose++;
                            total++;
                        } else if (result == 0) {
                            resStr = "Game Draw";
                            draw++;
                            total++;
                        }

                        System.out.println("--- Match No = "+total+" Result  "+resStr);

                    }

                    final DecimalFormat df = new DecimalFormat("0.00");
                    double p1,p2 ;

                    p1 = (1.0*win/total)*100;
                    p2 = (1.0*lose/total)*100;

                    System.out.println("\n Different wieght Combo = "+run+" Result bellow ");
                    System.out.println("\nPlayer One Win = "+p1+" % Player two Win = "+p2+" %  ");

                    if(p1>p)
                    {
                        w1Max= weightOne;w1Max=weightTwo; w1Max=weightThree; w1Max=weightFour;
                    }
                }

                System.out.println("\n# Good  weight, one = " + w1Max +" two = " + w2Max +
                        "three = " + w3Max +" four = " + w4Max +" :) \n");

            }
            else if(modestat==5)
            {
                System.out.println("\n### Stat Five , Different Depth Algo :D ###\n");

                System.out.println("\nDepth Limited DFS (current) : ");
                System.out.println("Work better In this Scenario  ");
                System.out.println("\nIterative Depth Search : ");
                System.out.println("Memory overun as in First move grow the tree by increasing depth " +
                        "\nuntill child with game over found , Not efficient ");
                System.out.println("\nBFS Search : ");
                System.out.println("Alpha Beta pruning not possible " +
                        "\nRun time exponential , Not efficient ");
            }
        }

    }

    public static Context chooseHeu(int n)
    {
        if(n==1)
        {
            return new Context(new HeuristicOne());
        }
        else if(n==2)
        {
            return new Context(new HeuristicTwo());
        }
        else if(n==3)
        {
            return new Context(new HeuristicThree());
        }
        else if(n==4)
        {
            return new Context(new HeuristicFour());
        }
        else if(n==5)
        {
            return new Context(new HeuristicFive());
        }
        else if(n==6)
        {
            return new Context(new HeuristicSix());
        }
        else if(n==7)
        {
            return new Context(new HeuristicSeven());
        }
        else {
            return new Context(new HeuristicOne());
        }
    }

    public static int Play(State game , int depthOne, int depthTwo, Context conOne, Context conTwo)
    {
        while (true)
        {
            int a= depthOne,b=depthOne;
            int move = minMaxMancalaPruningAi(game,a,b,Integer.MIN_VALUE,Integer.MAX_VALUE,1,conOne);

            if(move==0||move>6)
            {
                return -1;
            }
            int moveGame= game.gameMove(1,move);

            while (moveGame==1)
            {
                a= depthOne;b=depthOne;
                move = minMaxMancalaPruningAi(game,a,b,Integer.MIN_VALUE,Integer.MAX_VALUE,1,conOne);
                moveGame= game.gameMove(1,move);
            }

            if(moveGame==3)
            {
                return 1;
            }
            else if(moveGame==4)
            {
                return -1;
            }
            else if(moveGame==5)
            {
                return 0;
            }

            a= depthTwo;b=depthTwo;
            move = minMaxMancalaPruningAi(game,a,b,Integer.MIN_VALUE,Integer.MAX_VALUE,2,conTwo);

            if(move==0||move>6)
            {
                return -1;
            }
            moveGame= game.gameMove(2,move);

            while (moveGame==1)
            {

                a= depthTreeTwo;b=depthTreeTwo;
                move = minMaxMancalaPruningAi(game,a,b,Integer.MIN_VALUE,Integer.MAX_VALUE,2,conTwo);
                moveGame= game.gameMove(2,move);
            }
             if(moveGame==3)
            {
                return 1;
            }
            else if(moveGame==4)
            {
                return -1;
            }
            else if(moveGame==5)
            {
                return 0;
            }
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

            if(user==1)
            {
                return weightOne*(current.binsNum[current.totalBlockMid]-current.binsNum[0])
                        +weightTwo*(current.stoneDownStorage()-current.stoneUpStorage());
            }
            else
            {
                return weightOne*(-current.binsNum[current.totalBlockMid]+current.binsNum[0])
                        +weightTwo*(-current.stoneDownStorage()+current.stoneUpStorage());
            }
        }
    }


    public static class HeuristicThree implements Strategy{
        @Override
        public int doOperation(State current,int user){
            int HeuristicThree = 0 ;

            if(user==1)
            {
                return weightOne*(current.binsNum[current.totalBlockMid]-current.binsNum[0])
                        +weightThree*(current.stoneDownStorage()-current.stoneUpStorage())
                        +weightTwo*(current.additionalMoveDown-current.additionalMoveUp);

            }
            else
            {
                return weightOne*(-current.binsNum[current.totalBlockMid]+current.binsNum[0])
                        +weightThree*(-current.stoneDownStorage()+current.stoneUpStorage())
                        +weightTwo*(-current.additionalMoveDown+current.additionalMoveUp);
            }

        }
    }

    public static class HeuristicFour implements Strategy{
        @Override
        public int doOperation(State current,int user){
            int HeuristicThree = 0 ;

            if(user==1)
            {
                return weightOne*(current.binsNum[current.totalBlockMid]-current.binsNum[0])
                        +weightTwo*(current.stoneDownStorage()-current.stoneUpStorage())
                        +weightThree*(current.additionalMoveDown-current.additionalMoveUp)
                        +weightThree*(current.moveDownStorage()-current.moveUpStorage());
            }
            else
            {
                return weightOne*(-current.binsNum[current.totalBlockMid]+current.binsNum[0])
                        +weightTwo*(-current.stoneDownStorage()+current.stoneUpStorage())
                        +weightThree*(-current.additionalMoveDown+current.additionalMoveUp)
                        +weightThree*(-current.moveDownStorage()+current.moveUpStorage());
            }

        }
    }

    public static class HeuristicFive implements Strategy{
        @Override
        public int doOperation(State current,int user){
            int HeuristicThree = 0 ;

            if(user==1)
            {
                return weightOne*(current.binsNum[current.totalBlockMid]-current.binsNum[0])
                        +weightThree*(current.stoneDownStorage()-current.stoneUpStorage())
                        +weightTwo*(current.additionalMoveDown-current.additionalMoveUp)
                        +weightFour*(current.howCloseIwinStone());

            }
            else
            {
                return weightTwo*(-current.binsNum[current.totalBlockMid]+current.binsNum[0])
                        +weightThree*(-current.stoneDownStorage()+current.stoneUpStorage())
                        +weightTwo*(-current.additionalMoveDown+current.additionalMoveUp)
                        +weightOne*(-current.howCloseIwinStone());
            }

        }
    }

    public static class HeuristicSix implements Strategy{
        @Override
        public int doOperation(State current,int user){
            int HeuristicThree = 0 ;

            if(user==1)
            {
                return weightOne*(current.binsNum[current.totalBlockMid]-current.binsNum[0])
                        +weightThree*(current.stoneDownStorage()-current.stoneUpStorage())
                        +weightTwo*(current.additionalMoveDown-current.additionalMoveUp)
                        +weightThree*current.binsNum[current.totalBlockMid-1];

            }
            else
            {
                return weightOne*(-current.binsNum[current.totalBlockMid]+current.binsNum[0])
                        +weightThree*(-current.stoneDownStorage()+current.stoneUpStorage())
                        +weightTwo*(-current.additionalMoveDown+current.additionalMoveUp)
                        +weightThree*current.currentCaptureDown();
            }

        }
    }

    public static class HeuristicSeven implements Strategy{
        @Override
        public int doOperation(State current,int user){
            int HeuristicThree = 0 ;

            if(user==1)
            {
                return weightOne*(current.binsNum[current.totalBlockMid]-current.binsNum[0])
                        +weightThree*(current.stoneDownStorage()-current.stoneUpStorage())
                        +weightTwo*(current.additionalMoveDown-current.additionalMoveUp)
                        +weightThree*current.binsNum[current.totalBlockMid-1];

            }
            else
            {
                return weightOne*(-current.binsNum[current.totalBlockMid]+current.binsNum[0])
                        +weightThree*(-current.stoneDownStorage()+current.stoneUpStorage())
                        +weightTwo*(-current.additionalMoveDown+current.additionalMoveUp);
            }

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

            ArrayList<Integer> list = new ArrayList<Integer>();

            if(moveOrder==2)
            {
                for (int i=1; i<7; i++) {
                    list.add(i);
                }
                Collections.shuffle(list);
            }

            int g =0 ;

            for (int i = 1; i < stateRun.totalBlockMid; i++)
            {
                int heuVal= Integer.MIN_VALUE;

                if(moveOrder==1)
                {
                    g = i;
                }
                else if(moveOrder==2)
                {
                    g = list.get(i-1);

                }

                if(stateRun.binsNum[i]>0)
                {
                    State temp = new State();

                    temp=stateCopy(stateRun,temp);

                    if(temp.gameMove(1,g)==1)
                    {
                        temp.additionalMoveDown=temp.additionalMoveDown+1;
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
                    optMove = g;
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

            ArrayList<Integer> list = new ArrayList<Integer>();

            if(moveOrder==2)
            {
                for (int i=1; i<7; i++) {
                    list.add(i);
                }
                Collections.shuffle(list);
            }

            int g =0 ;

            for (int i = 1; i < stateRun.totalBlockMid; i++)
            {
                int heuVal= Integer.MAX_VALUE;

                if(moveOrder==1)
                {
                    g = i;
                }
                else if(moveOrder==2)
                {
                    g = list.get(i-1);
                }

                int c = stateRun.totalBlockMid-g;
                if(stateRun.binsNum[stateRun.totalBlockMid+c]>0)
                {
                    //System.out.println("meu");
                    State temp = new State();
                    temp=stateCopy(stateRun,temp);

                    if(temp.gameMove(2,g)==1)
                    {
                        temp.additionalMoveUp=temp.additionalMoveUp+1;
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
                    optMove = g;
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
