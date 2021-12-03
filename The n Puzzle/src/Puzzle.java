import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

import static java.lang.StrictMath.abs;

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
            System.out.print("Enter Your value of K = ");
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

            if (line == null) {
                System.out.println("Enter a valid State!");
                i--;
                continue;
            }

            row = line.split(" ");

            for (int ii = 0; ii < k; ii++) {
                if (row[ii].equals("*")) {
                    initialState.state[i][ii] = -1;
                } else {
                    initialState.state[i][ii] = Integer.parseInt(row[ii]);
                }
            }
        }

        System.out.println("\n The Initail state is = \n");
        initialState.PrintBoard();

        State goalState = new State(k);
        int count = 1;

        for (int i = 0; i < k; i++) {
            for (int ii = 0; ii < k; ii++) {
                goalState.state[i][ii] = count;
                count++;

                if (count == k * k) {
                    count = -1;
                }
            }
        }

        System.out.println("\n The Goal state is = \n");
        goalState.PrintBoard();

        if (initialState.Solvable()) {
            System.out.println("\n## The Puzzle is Solvable ##\n");

            System.out.println("## Press 1 for  Hamming ##");
            System.out.println("## Press 2 for Manhattan ##");
            System.out.println("## Press 3 for LinearConflict ##");
            System.out.println("## Press other for ALL ##");
            System.out.print("## Enter Choice = ");
            int userChoose = userInput3.nextInt();

            if(userChoose==1)
            {
                System.out.println("\n## Using A * with Hamming Heauristic ##");
                aStarAlgo(initialState,goalState,new Context(new Hamming()));
            }
            else if(userChoose==2)
            {
                System.out.println("\n## Using A * with Manhattan Heauristic##");
                aStarAlgo(initialState,goalState,new Context(new Manhattan()));
            }
            else if(userChoose==3)
            {
                System.out.println("\n## Using A * with LinearConflict Heauristic##");
                aStarAlgo(initialState,goalState,new Context(new LinearConflict()));
            }
            else
            {
                System.out.println("\n## Using A * with Hamming Heauristic ##");
                aStarAlgo(initialState,goalState,new Context(new Hamming()));
                System.out.println("\n## Using A * with Hamming Manhattan ##");
                aStarAlgo(initialState,goalState,new Context(new Manhattan()));
                System.out.println("\n## Using A * with Hamming LinearConflict ##");
                aStarAlgo(initialState,goalState,new Context(new LinearConflict()));
            }


        } else {
            System.out.println("\n## The Puzzle is Un-Solvable ##\n");
        }

    }


    public interface Strategy {
        public int doOperation(State current , State goal, int row[] , int col[]);
    }

    public static class Hamming implements Strategy{
        @Override
        public int doOperation(State current , State goal, int row[] , int col[]){
            int hammingConflict = 0 ;
            for (int i = 0; i < current.number; i++) {
                for (int ii = 0; ii < current.number; ii++) {

                    if(current.state[i][ii]!=-1&&current.state[i][ii]!=goal.state[i][ii])
                    {
                        hammingConflict++;
                    }
                }
            }
            return hammingConflict;
        }
    }

    public static class Manhattan implements Strategy{
        @Override
        public int doOperation(State current , State goal, int row[] , int col[]){
            int ManhattanConflict = 0 ;

            for (int i = 0; i < current.number; i++) {
                for (int ii = 0; ii < current.number; ii++) {

                    if(current.state[i][ii]!=-1&&current.state[i][ii]!=goal.state[i][ii])
                    {
                        ManhattanConflict+=abs(row[current.state[i][ii]]-i)+abs(col[current.state[i][ii]]-ii);
                    }
                }
            }



            return ManhattanConflict;
        }
    }

    public static class LinearConflict implements Strategy{
        @Override
        public int doOperation(State current , State goal, int row[] , int col[]){
            int LinearConflictConflict = 0 ;

            for (int i = 0; i < current.number; i++) {
                for (int ii = 0; ii < current.number; ii++) {

                    if(current.state[i][ii]!=-1&&current.state[i][ii]!=goal.state[i][ii])
                    {
                        LinearConflictConflict+=abs(row[current.state[i][ii]]-i)+abs(col[current.state[i][ii]]-ii);
                    }
                }
            }

            for (int i = 0; i < current.number; i++) {
                for (int ii = 0; ii < current.number; ii++) {
                    for (int iii = ii+1; iii < current.number; iii++) {

                        if (current.state[i][ii] != -1 && current.state[i][iii]!=-1) {
                            if(row[current.state[i][ii]]==i&&row[current.state[i][ii]]==row[current.state[i][iii]])
                                if(current.state[i][ii]>current.state[i][iii])
                                {
                                    LinearConflictConflict += 2;
                                }
                        }
                    }
                }
            }

            return LinearConflictConflict;
        }
    }

    public static class Context {
        private Strategy strategy;

        public Context(Strategy strategy){
            this.strategy = strategy;
        }

        public int executeStrategy(State current , State goal, int row[] , int col[]){
            return strategy.doOperation(current, goal, row , col);
        }
    }

    public static void aStarAlgo(State initial , State goal , Context context)
    {

        int goalStateRow[] = new int[goal.number*goal.number];
        int goalStateCol[] = new int[goal.number*goal.number];

        for (int i = 0; i < goal.number; i++) {
            for (int ii = 0; ii < goal.number; ii++) {

                if(goal.state[i][ii]!=-1)
                {
                    goalStateRow[goal.state[i][ii]] = i;
                    goalStateCol[goal.state[i][ii]] = ii;
                }
            }
        }



        PriorityQueue<State> openQueue = new PriorityQueue<State>(50, new Comparator<State>() {
            @Override
            public int compare(State o1, State o2) {
                if(o1.heuristic +o1.level> o2.heuristic +o2.level) {
                    return 1;
                } else if (o1.heuristic +o1.level< o2.heuristic +o2.level) {
                    return -1;
                } else {
                    return 0;
                }
            }

        });
        ArrayList<State> closeList = new ArrayList<>();


        initial.heuristic = context.executeStrategy(initial,goal,goalStateCol,goalStateCol);
        openQueue.add(initial);

        int optCost = 0 ;

        while (!openQueue.isEmpty())
        {
            State now = openQueue.peek();

            //now.PrintBoard();

            if (now.heuristic == 0) {
                optCost= now.level;
                //now.PrintBoard();
                now.PrintParrentTrack();
                break;
            }

            closeList.add(now);
            openQueue.poll();

            State u = new State(initial.number);
            State d = new State(initial.number);
            State l = new State(initial.number);
            State r = new State(initial.number);

            u=stateCopy(now,u);
            d=stateCopy(now,d);
            l=stateCopy(now,l);
            r=stateCopy(now,r);

            u = u.upChild();
            if(u!=null && arrayContain(closeList,u)!=true) {
                //u.PrintBoard();
                u.heuristic = context.executeStrategy(u,goal,goalStateRow,goalStateCol);
                //System.out.println(u.heuristic);
                u.level = now.level+1;
                u.parent = now;
                openQueue.add(u);
            }

            d = d.downChild();
            if(d!=null && arrayContain(closeList,d)!=true) {
                //d.PrintBoard();
                d.heuristic = context.executeStrategy(d, goal,goalStateRow,goalStateCol);
                //System.out.println(d.heuristic);
                d.level = now.level+1;
                d.parent = now;
                openQueue.add(d);
            }

            l = l.leftChild();
            if(l!=null && arrayContain(closeList,l)!=true) {
                //l.PrintBoard();
                l.heuristic = context.executeStrategy(l,goal,goalStateRow,goalStateCol);
                //System.out.println(l.heuristic);
                l.level = now.level+1;
                l.parent = now;
                openQueue.add(l);
            }

            r = r.rightChild();
            if(r!=null && arrayContain(closeList,r)!=true) {
                //r.PrintBoard();
                r.heuristic = context.executeStrategy(r,goal,goalStateRow,goalStateCol);
               // System.out.println(r.heuristic);
                r.level = now.level+1;
                r.parent = now;
                openQueue.add(r);
            }


        }

        System.out.println("Optimal Cost = "+ optCost);
        System.out.println("Expanded Node = "+ closeList.size());
        int nodeCount = closeList.size() + openQueue.size();
        System.out.println("Explored Node = "+nodeCount);

    }

    public static boolean arrayContain(ArrayList<State> AL ,State A)
    {
        for (int i = 0 ; i< AL.size();i++)
        {
            if(arrayCompare(AL.get(i).state,A.state))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean arrayCompare(int a[][] , int b[][])
    {
        boolean ret = true ;

        for (int i = 0; i < a.length; i++) {
            for (int ii = 0; ii < a.length; ii++) {

                if(a[i][ii]!=b[i][ii]) ret = false;
            }
        }

        return ret;
    }

    public static State stateCopy(State A ,State B)
    {
        int n = A.number;
        B.number = n ;

        for (int i = 0; i < n; i++) {
            for (int ii = 0; ii < n; ii++) {
                B.state[i][ii]=A.state[i][ii];
            }
        }

        return B;
    }

}

