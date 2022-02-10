import java.text.DecimalFormat;

public class HauntedRoom {

    int row ;
    int col ;
    int time ;

    double edgePorbabilty;
    double sensorRightProbabilty;

    public void setEdgePorbabilty(double edgePorbabilty) {
        this.edgePorbabilty = edgePorbabilty;
    }

    public void setSensorProbabilty(double sensorProbabilty) {
        this.sensorRightProbabilty = sensorProbabilty;
    }

    double room [][];
    double roomTemp [][];

    public HauntedRoom(int r, int c, int k)
    {
        this.row = r;
        this.col = c;
        this.time = 0;
        //default prob when initialized nothing
        this.edgePorbabilty=0.9;
        this.sensorRightProbabilty=0.85;
        this.room = new double[row][col];
        this.roomTemp = new double[row][col];

        for(int i=0 ; i<row ;i++)
        {
            for(int j=0 ; j<col ;j++)
            {
                room[i][j] = (double) (1.0/((row*col)-k));
                roomTemp[i][j] = 0;
            }
        }
    }

    String findGhoast()
    {
        // System.out.println(this.movingProbabilty(3,3,2,2));

        double maxVal = 0 ;
        String positionCasper = "The Casper is in = \n";

        for(int i=0;i<this.row;i++)
        {
            for (int j=0;j<this.col;j++)
            {
                if(maxVal<this.room[i][j])
                {
                    maxVal = this.room[i][j];
                }
            }
        }

        for(int i=0;i<this.row;i++)
        {
            for (int j=0;j<this.col;j++)
            {
                if(maxVal==this.room[i][j])
                {
                    positionCasper+= "Cell position ("+Integer.toString(i)+","+Integer.toString(j)+") \n";
                }
            }
        }
        positionCasper+="[Most Probable Positions]\n";

        return positionCasper;
    }

    void addObstrucle(int r ,int c)
    {
       this.room[r][c] = -1 ;
    }

    double movingProbabilty(int sorceRow ,int sorceCol, int desRow ,int desCol)
    {
        double p = this.edgePorbabilty ;
        double result = 0 ; // defualt

        if(this.room[sorceRow][sorceCol]==-1||this.room[desRow][desCol]==-1)
        {
            return 0;
        }

        int obstracleEdge = 0;
        int obstracleTotal =0;

        for (int i = sorceRow-1;i<=sorceRow+1;i++)
        {
            for (int j=sorceCol-1;j<=sorceCol+1;j++)
            {
                if(i==this.row||j==this.col||i<0||j<0)
                {
                    if( ((Math.abs(sorceRow-i) ==1)&&(Math.abs(sorceCol-j) ==0  )) ||
                            ((Math.abs(sorceRow-i) ==0)&&(Math.abs(sorceCol-j) ==1  ))  )
                    {
                        obstracleEdge++;
                    }
                    obstracleTotal++;
                    //System.out.println(obstracleTotal);
                }
                else
                {
                    if(room[i][j]==-1)
                    {
                        obstracleTotal++;
                        //System.out.println(obstracleTotal);

                        if( ((Math.abs(sorceRow-i) ==1)&&(Math.abs(sorceCol-j) ==0  )) ||
                                ((Math.abs(sorceRow-i) ==0)&&(Math.abs(sorceCol-j) ==1  ))  )
                        {
                            obstracleEdge++;
                        }
                    }
                }
            }
        }

        //System.out.println(obstracleEdge);
        //System.out.println(obstracleTotal);


        if(obstracleEdge==4) { p = 0; }

        if( ((Math.abs(sorceRow-desRow) ==1)&&(Math.abs(sorceCol-desCol) ==0  )) ||
                ((Math.abs(sorceRow-desRow) ==0)&&(Math.abs(sorceCol-desCol) ==1  ))  )
        {
            result = p / (4-obstracleEdge);
            //System.out.println((p)+"/"+(4-obstracleEdge));
        }
        else
        {
            result = (1-p) / (5-(obstracleTotal-obstracleEdge));
            //System.out.println((1-p)+"/"+(5-(obstracleTotal-obstracleEdge)));

        }
        //System.out.println();

        return result;

    }


    void updatePartialBelieve()
    {
        for(int i=0 ; i<this.row ;i++)
        {
            for(int j=0 ; j<this.col ;j++)
            {
                if(room[i][j]!=-1)
                {
                    this.roomTemp[i][j] = this.updateSinglePartialBelieve(i,j);
                }
                else
                {
                    this.roomTemp[i][j]=-1;
                }
            }
        }

        for(int i=0 ; i<this.row ;i++)
        {
            for(int j=0 ; j<this.col ;j++)
            {
                this.room[i][j] = this.roomTemp[i][j];
            }
        }
    }

    double updateSinglePartialBelieve(int sorceRow ,int sorceCol)
    {
        double allTransitionsProb = 0 ;

        for (int i = sorceRow-1;i<=sorceRow+1;i++)
        {
            for (int j=sorceCol-1;j<=sorceCol+1;j++)
            {
                if(i==this.row||j==this.col||i<0||j<0)
                {
                    continue;
                }
                else if(room[i][j]!=-1)
                {
                    allTransitionsProb += this.room[i][j]*movingProbabilty(i,j,sorceRow,sorceCol);
                }
            }
        }

        return allTransitionsProb;

    }

    void updateFullBelieve(int r ,int c, int value)
    {
        if(value==1)
        {
            for(int i=0 ; i<this.row ;i++)
            {
                for(int j=0 ; j<this.col ;j++)
                {
                    if(room[i][j]!=-1)
                    {
                        if((i>=(r-1)&&i<=(r+1))&&(j>=(c-1)&&j<=(c+1)))
                        {
                            this.room[i][j] *= (sensorRightProbabilty); // ghost yes * right guess = ghost yes
                        }
                        else
                        {
                            this.room[i][j] *= (1-sensorRightProbabilty); // ghost yes * wrong guess = ghost yes
                        }
                    }
                }
            }
        }
        else
        {
            for(int i=0 ; i<row ;i++)
            {
                for(int j=0 ; j<col ;j++)
                {
                    if(room[i][j]!=-1)
                    {
                        if((i>=(r-1)&&i<=(r+1))&&(j>=(c-1)&&j<=(c+1)))
                        {
                            this.room[i][j] *= (1-sensorRightProbabilty); // ghost yes * wrong guess = ghost yes
                        }
                        else
                        {
                            this.room[i][j] *= (sensorRightProbabilty); // ghost yes * right guess = ghost yes
                        }
                    }
                }
            }
        }
    }

    void normalizeBoard()
    {
        double normTotal = 0 ;

        for(int i=0 ; i<row ;i++) {

            for (int j = 0; j < col; j++) {

                if (room[i][j] != -1)
                {
                    normTotal+= room[i][j];
                }
            }
        }

        for(int i=0 ; i<row ;i++) {

            for (int j = 0; j < col; j++) {

                if (room[i][j] != -1)
                {
                    this.room[i][j] = room[i][j]/normTotal;
                }
            }
        }
    }

    void addEvidence(int r ,int c, int value)
    {
        this.time += 1;

        if(value==1)
        {
            System.out.println("Ghost sensor Blinked !! ");
        }
        else
        {
            System.out.println("Ghost sensor NOT Blinked !! ");
        }

        this.updatePartialBelieve();
        //System.out.println("Before");
        //this.PrintRoom();
        this.updateFullBelieve(r,c,value);
        //System.out.println("After");
        //this.PrintRoom();
        this.normalizeBoard();
        //System.out.println("Normla");
        //this.PrintRoom();

    }

    void PrintRoom()
    {
        System.out.println("\nTime : "+this.time);
        System.out.println("==============\n");

        DecimalFormat df = new DecimalFormat("#.##");
        double format = 100;

        System.out.println();

        for(int i=0 ; i<row ;i++)
        {
            for(int j=0 ; j<col ;j++)
            {
                System.out.print("===========");
            }
            System.out.println();

            for(int j=0 ; j<col ;j++)
            {
                if(room[i][j]==-1)
                {
                    System.out.print("|    0     ") ;
                }
                else if(room[i][j]<=0.001)
                {
                    System.out.print("|    "+df.format(room[i][j]*format)+"     ") ;
                }
                else
                {
                    System.out.print("|   "+df.format(room[i][j]*format)+"   ") ;
                }

            }
            System.out.println("|");
        }

        for(int j=0 ; j<col ;j++)
        {
            System.out.print("===========");
        }
        System.out.println();
    }
}
