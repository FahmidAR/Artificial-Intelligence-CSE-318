import static java.lang.StrictMath.abs;

public class State {

    static final int totalBlock = 14;
    static final int totalBlockMid = 7;

    int additionalMoveUp = 0;
    int additionalMoveDown = 0;

    int binsNum [];

    public State() {

        this.binsNum = new int[totalBlock];

        for (int i = 0; i < totalBlock; i++) {

            if (i%totalBlockMid==0) {
                binsNum[i]=0;
            }
            else
            {
                binsNum[i]=4;
            }
        }
    }

    int gameMove(int user,int move)
    {
        boolean captureFlag =false;
        if(user==1)
        {
            //System.out.println(this.binsNum[move]);
            int loopCount = this.binsNum[move];

            if(this.binsNum[move]==0) return 1;

            this.binsNum[move]=0;

            for(int i=1;i<=loopCount;i++)
            {
                int index = (move+i)%totalBlock;
                //if (index==0) index++;
                if (index==0)
                {
                    loopCount++;
                    continue;
                }
                this.binsNum[index]=this.binsNum[index]+1;
            }

            int lastMove = (move+loopCount)%this.totalBlock;

            if(lastMove<totalBlockMid&&lastMove>0)
            {
                if(this.binsNum[lastMove]==1&&this.binsNum[this.totalBlock-lastMove]>0)
                {
                    this.binsNum[lastMove]=0;
                    this.binsNum[this.totalBlockMid]+=1+this.binsNum[this.totalBlock-lastMove];
                    this.binsNum[this.totalBlock-lastMove]=0;
                    captureFlag = true;
                }
            }

            int sum = 0 , sumE=0;
            for (int i=1;i<this.totalBlockMid;i++)
            {
                sum+=this.binsNum[i];
            }
            for (int i=this.totalBlockMid+1;i<this.totalBlock;i++)
            {
                sumE+=this.binsNum[i];
            }
            if (sum==0||sumE==0)
            {
                int antiSum = 0;
                for (int i=this.totalBlockMid+1;i<this.totalBlock;i++)
                {
                    antiSum+=this.binsNum[i];
                    this.binsNum[i]=0;
                }
                for (int i=1;i<this.totalBlockMid;i++)
                {
                    antiSum+=this.binsNum[i];
                    this.binsNum[i]=0;
                }

                this.binsNum[0] += antiSum;

                if(this.binsNum[totalBlockMid]>this.binsNum[0])
                {
                    return 3;
                }
                else if(this.binsNum[totalBlockMid]<this.binsNum[0])
                {
                    return 4;
                }
                else
                {
                    return 5;
                }
            }

            if(captureFlag)return 2;


            if(lastMove==this.totalBlockMid)
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
        else
        {
            //System.out.println(this.binsNum[move]);
            move = this.totalBlock-move;
            int loopCount = this.binsNum[move];

            if(this.binsNum[move]==0) return 1;

            this.binsNum[move]=0;

            for(int i=1;i<=loopCount;i++)
            {
                int index = (move+i)%totalBlock;

                if (index==totalBlockMid)
                {
                    loopCount++;
                    continue;
                }

                this.binsNum[index]=this.binsNum[index]+1;

            }
            int lastMove = (move+loopCount)%this.totalBlock;

            if(lastMove<totalBlock&&lastMove>totalBlockMid)
            {
                if(this.binsNum[lastMove]==1&&this.binsNum[this.totalBlock-lastMove]>0)
                {
                    this.binsNum[lastMove]=0;
                    this.binsNum[0]+=1+this.binsNum[this.totalBlock-lastMove];
                    this.binsNum[this.totalBlock-lastMove]=0;
                    captureFlag=true;
                }
            }

            int sum = 0 , sumE=0;
            for (int i=1;i<this.totalBlockMid;i++)
            {
                sum+=this.binsNum[i];
            }
            for (int i=this.totalBlockMid+1;i<this.totalBlock;i++)
            {
                sumE+=this.binsNum[i];
            }
            if (sum==0||sumE==0)
            {
                int antiSum = 0;
                for (int i=1;i<this.totalBlockMid;i++)
                {
                    antiSum+=this.binsNum[i];
                    this.binsNum[i]=0;
                }
                for (int i=this.totalBlockMid+1;i<this.totalBlock;i++)
                {
                    antiSum+=this.binsNum[i];
                    this.binsNum[i]=0;
                }

                this.binsNum[this.totalBlockMid] += antiSum;

                if(this.binsNum[totalBlockMid]>this.binsNum[0])
                {
                    return 3;
                }
                else if(this.binsNum[totalBlockMid]<this.binsNum[0])
                {
                    return 4;
                }
                else
                {
                    return 5;
                }
            }

            if(captureFlag)return 2;

            if(lastMove==0)
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
    }

    int currentCaptureDown()
    {
        int capMax =0;

        for(int k=1 ; k<this.totalBlockMid ; k++)
        {
            int loopCount = this.binsNum[k];

            for(int i=1;i<=loopCount;i++)
            {
                int index = (k+i)%totalBlock;
                if (index==0)
                {
                    loopCount++;
                }
            }

            int lastMove = (k+loopCount)%this.totalBlock;

            if(lastMove<totalBlockMid&&lastMove>0)
            {
                if(this.binsNum[lastMove]==1||lastMove==k)
                {
                    if(this.binsNum[this.totalBlock-lastMove]>capMax)
                    {
                        capMax=this.binsNum[this.totalBlock-lastMove];
                    }
                }
            }

        }
        return capMax;
    }

    boolean isGameOver() {
        if((this.binsNum[0]+this.binsNum[this.totalBlockMid])==48) return true;
        else return false;
    }

    int moveUpStorage() {
        int antiSum = 0;
        for (int i=this.totalBlockMid+1;i<this.totalBlock;i++)
        {
            if(this.binsNum[i]!=0)
            {
                antiSum++;
            }
        }
        return antiSum;
    }

    int howCloseIwinStone() {

        int ret = 0;
        if(this.binsNum[this.totalBlockMid]<25)
        {
            ret = this.binsNum[this.totalBlockMid]-25;
        }
        return ret;
    }

    int moveDownStorage() {
        int antiSum = 0;
        for (int i=1;i<this.totalBlockMid;i++)
        {
            if(this.binsNum[i]!=0)
            {
                antiSum++;
            }
        }
        return antiSum;
    }

    int stoneUpStorage() {
        int antiSum = 0;
        for (int i=this.totalBlockMid+1;i<this.totalBlock;i++)
        {
            antiSum+=this.binsNum[i];
        }
        return antiSum;
    }

    int stoneDownStorage() {
        int antiSum = 0;
        for (int i=1;i<this.totalBlockMid;i++)
        {
            antiSum+=this.binsNum[i];
        }
        return antiSum;
    }

    void PrintBoard() {

        System.out.println();

        for (int i = 0; i < this.totalBlockMid; i++)
        {

            System.out.print("=============");

            if(i==this.totalBlockMid-1)
            {
                System.out.print("=============\"\n");
            }
        }

        System.out.print("|");

        for (int i = 0; i < this.totalBlockMid; i++)
        {
            if(i!=0)
            {
                if(this.binsNum[this.totalBlock-i]<10)
                {
                    System.out.print("     " + this.binsNum[this.totalBlock-i] + "      |");
                }
                else
                {
                    System.out.print("     " + this.binsNum[this.totalBlock-i] + "     |");
                }
            }
            else
            {
                System.out.print("            |");
            }

            if(i==this.totalBlockMid-1)
            {
                System.out.println("            |");
            }
        }

        for (int i = 0; i < this.totalBlockMid; i++)
        {
            if(i==0)
            {
                if(this.binsNum[0]<10)
                {
                    System.out.print("|     "+this.binsNum[0]+"      ");
                }
                else
                {
                    System.out.print("|     "+this.binsNum[0]+"     ");
                }
            }
            else if(i==totalBlockMid-1)
            {
                System.out.print("|============|");
            }
            else
            {
                System.out.print("|============");
            }


            if(i==this.totalBlockMid-1)
            {
                if(this.binsNum[totalBlockMid]<10)
                {
                    System.out.print("      "+this.binsNum[totalBlockMid]+"     |\n");
                }
                else
                {
                    System.out.print("      "+this.binsNum[totalBlockMid]+"    |\n");
                }
            }
        }

        System.out.print("|");

        for (int i = 0; i < this.totalBlockMid; i++)
        {
            if(i!=0)
            {
                if(this.binsNum[i]<10)
                {
                    System.out.print("     "+this.binsNum[i]+"      |");
                }
                else
                {
                    System.out.print("     "+this.binsNum[i]+"     |");
                }

            }
            else
            {
                System.out.print("            |");
            }


            if(i==this.totalBlockMid-1)
            {
                System.out.println("            |");
            }
        }

        for (int i = 0; i < this.totalBlockMid; i++)
        {

            System.out.print("=============");

            if(i==this.totalBlockMid-1)
            {
                System.out.print("=============\"\n");
            }
        }

        System.out.println();
        System.out.println();
        System.out.println("Player 1(bottom) Score = "+this.binsNum[totalBlockMid]);
        System.out.println("Player 2(up) Score = "+this.binsNum[0]);
        System.out.println();
        System.out.println();

    }

}
