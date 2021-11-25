public class State {

    int number ;
    int[][] state ;
    State parent = null;

    public State(int theNumber) {
        this.number = theNumber;
        this.state = new int[number][number];
    }

    public State(State another) {
        this.state = another.state;
        this.number = another.number;
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
                return false;
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

                    if(i%2==0)
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

        for (int i = 0; i < this.number; i++) {
            for (int ii = 0; ii < this.number; ii++) {

                if(this.state[i][ii]!=-1) {

                    Boolean firstTime = true;
                    int current = this.state[i][ii];

                    for (int j = i; j < this.number; j++) {
                        for (int jj = ii; jj < this.number; jj++) {

                            if (firstTime) {
                                firstTime = false;
                                continue;
                            }

                            if (this.state[j][jj]<current&&this.state[j][jj]!=-1)
                            {
                                inversionTotal++;
                            }

                        }
                    }

                }


            }
        }

        if(inversionTotal%2==0)
        {
            return true;
        }
        else
        {
            return false;
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
            System.out.print("=============\"\n");
        }

    }





}
