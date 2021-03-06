
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Loa
{
  public static void main(String[] args) {
    boolean exit = false;
    int[] count = {0, 0};//stores how many times players have won;
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

        if(mode < 0 || mode > 4)//check mode valid
        {
          System.out.println("ERROR: illegal mode");
          System.exit(0);
        }

        switch(mode)
        {
          case 0:
            new testmode(size);//start in testmode
          break;

          case 1:
            new single(size, false, count);//start in command-line singleplayer
          break;

          case 2:
            if(args.length < 3){
              System.out.println("ERROR: too few arguments");
              System.exit(0);
            }
            else{
              String ip = args[2];
              new multiplayer(size, ip, false, count);//start in command-line multiplayer
            }
          break;

          case 3:
              new single(size, true, count);//start in graphic singleplayer
          break;

          case 4:
              String ip = args[2];
              new multiplayer(size, ip, true, count);//start in graphic multiplayer
          break;
        }
        int temp = 0;
        if(mode>2)
        {
            //asks if player would like to continue in graphics mode
            temp = JOptionPane.showConfirmDialog(null, "Would you like to play another game?", "Play again", JOptionPane.YES_NO_OPTION);
        }
        else
        {
            //asks if player would like to continue in command-line mode
            System.out.println("Would you like to play another game? (Y or N)");
            Scanner sc = new Scanner(System.in);
            String s = sc.next();
            if(s.equals("Y")){temp = 0;}
            else{temp=1;}
        }
        exit = !(temp == 0);//exits looping games
    }
    System.exit(0);
  }
}
