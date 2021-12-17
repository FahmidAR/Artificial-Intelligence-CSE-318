import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

public class State {

    int number ;
    int[][] state ;
    State parent = null;
    int level = 0 ;
    int heuristic ;

    public State(int theNumber) {
        this.number = theNumber;
        this.state = new int[number][number];
    }

    public State(State another) {

        this.state = another.state;
        this.number = another.number;

    }

    public State upChild()
    {
        State temp = new State(this);

        int indexRow = 0 , indexCol = 0;

        for (int i = 0; i < this.number; i++) {
            for (int ii = 0; ii < this.number; ii++) {

                if (this.state[i][ii] == -1) {

                    indexRow = i ;
                    indexCol = ii;
                    break;
                }
            }
        }

        if(indexRow==0)
        {
            return null;
        }
        else
        {
            temp.state[indexRow][indexCol] = this.state[indexRow-1][indexCol];
            temp.state[indexRow-1][indexCol] = -1;
            return temp;
        }


    }

    public State downChild()
    {
        State temp = new State(this);

        int indexRow = 0 , indexCol = 0;

        for (int i = 0; i < this.number; i++) {
            for (int ii = 0; ii < this.number; ii++) {

                if (this.state[i][ii] == -1) {

                    indexRow = i ;
                    indexCol = ii;
                    break;
                }
            }
        }

        if(indexRow==this.number-1)
        {
            return null;
        }
        else
        {
            temp.state[indexRow][indexCol] = this.state[indexRow+1][indexCol];
            temp.state[indexRow+1][indexCol] = -1;
            return temp;
        }


    }

    public State leftChild()
    {
        State temp = new State(this);

        int indexRow = 0 , indexCol = 0;

        for (int i = 0; i < this.number; i++) {
            for (int ii = 0; ii < this.number; ii++) {

                if (this.state[i][ii] == -1) {

                    indexRow = i ;
                    indexCol = ii;
                    break;
                }
            }
        }

        if(indexCol==0)
        {
            return null;
        }
        else
        {
            temp.state[indexRow][indexCol] = this.state[indexRow][indexCol-1];
            temp.state[indexRow][indexCol-1] = -1;
            return temp;
        }


    }

    public State rightChild()
    {
        State temp = new State(this);

        int indexRow = 0 , indexCol = 0;

        for (int i = 0; i < this.number; i++) {
            for (int ii = 0; ii < this.number; ii++) {

                if (this.state[i][ii] == -1) {

                    indexRow = i ;
                    indexCol = ii;
                    break;
                }
            }
        }

        if(indexCol==this.number-1)
        {
            return null;
        }
        else
        {
            temp.state[indexRow][indexCol] = this.state[indexRow][indexCol+1];
            temp.state[indexRow][indexCol+1] = -1;
            return temp;
        }


    }

    Boolean Solvable()
    {
        if(this.number%2==0)
        {
            if(blankEvenRow()&&!invertionEven())
            {
                return true;
            }
            else if(!blankEvenRow()&&invertionEven())
            {
                return true;
            }
            else
            {
                return false;
            }

        }
        else
        {
            return invertionEven();
        }

    }

    boolean blankEvenRow()
    {
        for (int i = 0; i < this.number; i++) {
            for (int ii = 0; ii < this.number; ii++) {

                if (this.state[i][ii] == -1) {

                    int p = this.number-i;
                    //System.out.println("Blank row = "+i);
                    //System.out.println("K = "+this.number);

                    if((this.number-i)%2==0)
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
            }
        }

        return false;
    }

    Boolean invertionEven()
    {
        int inversionTotal = 0;
        Vector<Integer> all = new Vector<>();

        for (int i = 0; i < this.number; i++) {
            for (int ii = 0; ii < this.number; ii++) {

                if(this.state[i][ii]!=-1) {
                    all.add(this.state[i][ii]);
                }

            }
        }

        for(int i = 0 ;i<all.size();i++)
        {
            for(int ii = i+1 ;ii<all.size();ii++)
            {
                if(all.get(ii)<all.get(i))
                {
                    inversionTotal++;
                }
            }
        }
        System.out.println("Inversion Total = "+inversionTotal);

        if(inversionTotal%2==0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    void PrintParrentTrack()
    {
        //PrintBoard();

        State temp = parent ;

        ArrayList<State> solve= new ArrayList<>();
        solve.add(this);

        while(temp!=null)
        {
            //temp.PrintBoard();
            solve.add(temp);
            temp = temp.parent;
        }

        Collections.reverse(solve);

        for (int i = 0 ; i < solve.size();i++)
        {
            if(i!=0)
            {
                System.out.print("             ");
                for (int ii = 1; ii < this.number-1; ii++)
                {
                    System.out.print("   ⟱  ⟱  ⟱   ");
                }
                System.out.print("            \n\n");

            }
            solve.get(i).PrintBoard();
        }
    }


    void PrintBoard() {

        //System.out.println(state.length);

        System.out.print("\"=============");
        for (int ii = 1; ii < this.number-1; ii++)
        {
            System.out.print("=============");
        }
        System.out.print("=============\"\n");

        for (int i = 0; i < this.number; i++) {

            System.out.print("|");

            for (int ii = 0; ii < this.number; ii++)
            {
                if(state[i][ii]==-1)
                {
                    System.out.print("     *      |");
                }
                else
                {
                    System.out.print("     "+this.state[i][ii]+"      |");
                }
            }
            System.out.println();

            System.out.print("\"=============");
            for (int ii = 1; ii < this.number-1; ii++)
            {
                System.out.print("=============");
            }
            System.out.println("=============\"");
        }
        System.out.println();
        System.out.println();

    }

}
