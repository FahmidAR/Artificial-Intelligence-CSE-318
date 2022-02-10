import java.util.Scanner;

public class FindCasper {

    static double edgeProbabilty = 0.9;
    static double sensorProbabilty = 0.85;

    public static void main(String[] args) {

        Scanner userInput = new Scanner(System.in);
        Scanner userInput2 = new Scanner(System.in);

        System.out.println("+=================================+");
        System.out.println("|         Casper Finder            |");
        System.out.println("|         Fahmid Al Rifat         |");
        System.out.println("|         Roll: 1705087           |");
        System.out.println("+=================================+\n");

        int n , m , k ;

        System.out.println("\nEnter value of Row , Col , Obstrucle no  = ");

        n = userInput.nextInt();
        m = userInput.nextInt();
        k = userInput.nextInt();

        //System.out.println("The values are = "+n+" "+m+" "+k);

        HauntedRoom roomCasper = new HauntedRoom(n,m,k);
        roomCasper.setEdgePorbabilty(edgeProbabilty);
        roomCasper.setSensorProbabilty(sensorProbabilty);
        roomCasper.PrintRoom();

        System.out.println("Give the obstracles =");

        while (k!=0)
        {
            //System.out.println("Obstracle Fund "+k);
            n = userInput.nextInt();
            m = userInput.nextInt();

            roomCasper.addObstrucle(n,m);
            k--;
        }

        System.out.println("Obstrucle added succesfully :D ");
        roomCasper.PrintRoom();
        System.out.println("\nGive Command = ");

        String commandString ;

        while(true)
        {
            commandString = userInput2.nextLine();
            //System.out.println(commandString);
            String[] words = commandString.split("\\ ");

            if(words[0].equalsIgnoreCase("r"))
            {
                if(words.length<4)
                {
                    System.out.println("Enter the Valid Command :)");
                    continue;
                }
                System.out.println("Sensor reading are "+Integer.parseInt(words[1])+" "+Integer.parseInt(words[2])+" "+Integer.parseInt(words[3]));
                roomCasper.addEvidence(Integer.parseInt(words[1]),Integer.parseInt(words[2]),Integer.parseInt(words[3]));
                roomCasper.PrintRoom();
            }
            else if(words[0].equalsIgnoreCase("c"))
            {
                System.out.println(roomCasper.findGhoast());
            }
            else if(words[0].equalsIgnoreCase("q"))
            {
                System.out.println("Bye Casper!");
                break;
            }
            else
            {
                System.out.println("Enter the Valid Command :)");
            }

        }

    }
}
