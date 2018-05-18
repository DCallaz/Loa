
import java.awt.Color;
import java.awt.Font;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
public class multiplayer
{
  public static String printColour;
  public int size;
  public multiplayer(int size, String ip, boolean Gui, int[] count)
  {
    this.size = size;
    //waiting dialog initialiser
        JFrame frame = new JFrame();
        if(Gui)
        {
            ImageIcon ico = new ImageIcon("loading.png");
            JLabel l = new JLabel("Waiting for opponent...", ico, JLabel.CENTER);
            frame.add(l);
            frame.pack();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    //
    int mode;
    if (ip.equals("192.168.1.100"))//if connecting to own computer
    {
        mode = networking.connect(ip, true);//start as admin
    }
    else//connecting to opponent computer
    {
        mode = networking.connect(ip);
    }
    
    frame.dispose();//close waiting dialog
    switch (mode)//sending and reciving size for server and client respectively
    {
        case networking.SERVER_MODE:
            System.out.println("(Server mode)");
            System.out.println("Your colour is WHITE");
            System.out.println("Sending size...");
            networking.write(size+"");
            System.out.println("size sent");
            break;
        case networking.CLIENT_MODE:
            System.out.println("(Client mode)");
            System.out.println("Your colour is BLACK");
            System.out.println("Waiting for size...");
            size = Integer.parseInt(networking.read());
            System.out.println("Size received: "+size);
            break;
        default:
            System.out.println("Connection error");
            System.exit(0);
    }
    boolean endgame = false;
    player[] p = new player[2];
    //assign colour
      boolean colour = true;
      String player = "";
      if(mode == networking.CLIENT_MODE)
      {
        colour = false;
        player = "B";
      }
      else if(mode == networking.SERVER_MODE)
      {
        colour = true;
        player = "W";
      }
      printColour = (colour == true) ? "(White)" : "(Black)";
    //

    p[0] = new player(size, colour);//player
    p[1] = new player(size, !colour);//opponent
    board b;
    if(Gui)//initialises graphic board
    {
        b = new GuiBoard(size, p[0], p[1]);
    }
    else//initialises command-line board
    {
        b = new board(size, p[0], p[1]);
    }
    int pass = 0;
    while(endgame == false)//loop for the game
    {
    	//initialising counters
		    int i, j, plus, end =0;
		    if(colour == false)
		    {
		    	i =0; j=1; plus=1;
		    }
		    else
		    {
		    	i =1; j=0; plus=-1;
		    }
	    //
      for(; end<2; i+=plus, j-=plus, end++)//alternates player to move
      {
        if(checks.pass(p[i], p[j], size, b) == false)//checks if player must pass
        {
            int[][] move = null;
            while(move == null)//loops until valid move
            {
              if(i == 0)//player moving
              {
                System.out.print(b.toString());//print board
                if(Gui == false)//Read and check move for command-line mode
                {
                    System.out.println();
                    System.out.println("Your move:");
                    move = movereader(size, p[i], p[j], b);
                }
                else//Display score, Read and check move for graphics mode
                {
                    StdDraw.setPenColor(Color.BLACK);
                    StdDraw.setFont(new Font(Font.SANS_SERIF, Font.ITALIC+ Font.BOLD, 20));
                    StdDraw.text(9+2*(size-4), size*10+(double)size/2, "Your move "+printColour);
                    StdDraw.text(7*size, size*10+(double)size/2,"Score: "+count[0]+" | "+count[1]);
                    StdDraw.show(0);
                    move = checks.GuiMove(size, p[i], p[j], b, true);
                    if(move != null && move[0][0] == -1)
                    {
                        networking.write("QUIT");
                        System.exit(0);
                    }
                }
              }
              else//player waiting
              {
                System.out.print(b.toString());//print board
                if(Gui)
                {
                    StdDraw.setPenColor(Color.BLACK);
                    StdDraw.setFont(new Font(Font.SANS_SERIF, Font.ITALIC+ Font.BOLD, 20));
                    StdDraw.text(9+2*(size-4), size*10+(double)size/2, "Opponents move");
                    StdDraw.text(7*size, size*10+(double)size/2,"Score: "+count[0]+" | "+count[1]);
                    StdDraw.show(0);
                }
                else
                {
                    System.out.println();
                    System.out.println("Wait for opponent to move...");
                }
                //read move from opponent
                  String sMove = networking.read();
                  String[] parts = sMove.split("");
                  move = new int[2][2];
                  if(sMove.equals("QUIT"))
                  {
                      if(Gui)
                      {
                          JOptionPane.showMessageDialog(null, "Opponent has quit");
                      }
                      else
                      {
                          System.out.println("Opponent has quit");
                      }
                      networking.close();
                      System.exit(0);
                  }
                  else
                  {
                      for(int x=0; x<2; x++)
                      {
                          for(int y=0; y<2; y++)
                          {
                              move[x][y] = (int)(parts[(x*2)+y].charAt(0))-(int)('A');
                              if(y == 0)
                              {
                                move[x][y] = (size-1)-move[x][y];
                              }
                          }
                      }
                  }
                //
              }
            }
            if(i == 0)//sends the players valid move to opponent
            {
                String temp = "";
                    for(int x=0; x<2; x++)
                    {
                        for(int y=0; y<2; y++)
                        {
                           if(y == 0)
                           {
                               temp += (char)(('A')+size-1-move[x][y]);
                           }
                           else
                           {
                               temp += (char)(('A')+move[x][y]);
                           }
                        }
                    }
                    networking.write(temp);
            }
            //implement move
              if(p[j].occupied(move[1][0], move[1][1]) != -1)//check if landing on opponent
              {
                p[j].delete(p[j].occupied(move[1][0], move[1][1]));//delete opponent piece
              }
              p[i].move(move);//Implement move
              b.reset();//Reset board to show new move
            //
            //check for win
              String winner = b.checkwinner(p, player);
              if(winner != null)
              {
                  count[(b.won[0] == true) ? 0 : 1]++;
                  System.out.print(b.toString());//print board
                  if(Gui)
                  {
                      JOptionPane.showMessageDialog(null, winner);
                  }
                  else
                  {
                      System.out.println();
                    System.out.println(winner);
                  }
                  networking.close();//closes connection
                  endgame= true;
                  end = 3;
                  if(ip.equals("192.168.10.0"))//exits for local multiplayer
                  {
                      System.exit(0);
                  }
              }
        }
        else//pass functionality
        {
            if(pass == 3)
            {
              if(Gui == false)//for draws
              {
                      System.out.println();
                      System.out.println("DRAW");
              }
              else
              {
                      JOptionPane.showMessageDialog(null, "DRAW", "Draw", JOptionPane.INFORMATION_MESSAGE);
              }
              endgame = true;
              i = 3;
            }
            if(i == 0)//player pass
            {
              if(Gui == false)
              {
                      System.out.println();
                      System.out.println("Player pass");
              }
              else
              {
                      JOptionPane.showMessageDialog(null, "Player pass", "Pass", JOptionPane.INFORMATION_MESSAGE);
              }
            }
            else//computer pass
            {
                    if(Gui == false)
                {
                      System.out.println();
                      System.out.println("Computer pass");
                }
                else
                {
                      JOptionPane.showMessageDialog(null, "Computer pass", "Pass", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            pass++;
        }
        //
      }

    }
  }
  
  public static int[][] movereader(int size, player pAcc, player pDor, board b)//pAcc: active player | pDor: dormant player
  {
    //read move
      Scanner sc = new Scanner(System.in);
      int move[][] = new int[2][3];
      String input = sc.nextLine();
    //
    //check quit
      if(input.equals("QUIT"))
      {
        System.out.println("Quitting game");
        networking.write("QUIT");
        networking.close();
        System.exit(0);
      }
    //
    String place[] = input.split("");  //split up
    //check place exists
      for(int i=0; i<2; i++)
      {
        for(int j=0; j<2; j++)
        {
          move[i][j] = (int)(place[(i*2)+j].charAt(0))-(int)('A');
          if(j == 0)
          {
            move[i][j] = (size-1)-move[i][j];
          }
          if((move[i][j]+1) > size || (move[i][j])<0)
          {
            System.out.println("ERROR: invalid move");
            return null;
          }
        }
      }
    //

    if(checks.movecheck(size,pAcc,pDor,b,move) != false)//check and write move
    {
      networking.write(input);
      return move;
    }
    else
    {
      System.out.println("ERROR: invalid move");
      return null;
    }
  }
}
