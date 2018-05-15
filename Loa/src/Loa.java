
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Loa
{
  public static void main(String[] args) {
    boolean exit = false;
    while(exit == false)
    {
        if(args.length < 2)//test for valid amount of inputs
        {
          System.out.println("ERROR: too few arguments");
          System.exit(0);
        }
        int size = Integer.parseInt(args[0]);//get size
        int mode = Integer.parseInt(args[1]);//get mode

        if(size < 4 || size > 16)//check size valid
        {
          System.out.println("ERROR: illegal size");
          System.exit(0);
        }

        if(mode < 0 || mode > 3)//check mode valid
        {
          System.out.println("ERROR: illegal mode");
          System.exit(0);
        }

        switch(mode)
        {
          case 0:
            new testmode(size);
          break;

          case 1:
            new single(size, false);
          break;

          case 2:
            if(args.length < 3){
              System.out.println("ERROR: too few arguments");
              System.exit(0);
            }
            else{
              String ip = args[2];
              new multiplayer(size, ip, false);
            }
          break;

          case 3:
              new single(size, true);
          break;
          
          case 4:
              String ip = args[2];
              new multiplayer(size, ip, true);
          break;
        }
        int temp = 0;
        if(mode>2)
        {
            temp = JOptionPane.showConfirmDialog(null, "Would you like to play another game?", "Play again", JOptionPane.YES_NO_OPTION);
        }
        else
        {
            System.out.println("Would you like to play another game? (Y or N)");
            Scanner sc = new Scanner(System.in);
            String s = sc.next();
            if(s.equals("Y")){temp = 0;}
            else{temp=1;}
        }
        exit = !(temp == 0);
    }
    System.exit(0);
  }
}