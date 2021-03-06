import java.awt.Color;
import java.awt.Font;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
public class single
{

  public static String printColour;//string to store what colour the player is
  private static int[] count;//stores players win counter

  public single(int size, boolean Gui, int[] count)//constructor
  {
    this.count = count;
    int pass = 0;
    //allow for colour choice
      String player = "";//string for command-line colour choice
      boolean colour = false;//indicates colour (true=white)
      if(Gui == false)//reads colour choice for command-line mode
      {
    	  System.out.println("Chose a colour: (B or W)");
    	  Scanner sc = new Scanner(System.in);
    	  player = sc.next();
    	  if(player.equals("B")){colour = false;}
    	  else if(player.equals("W")){colour = true;}
      }
      else//reads colour choice for graphics mode
      {
    	  int choice = JOptionPane.showOptionDialog(null, "Chose a colour",
        "Colour choice", JOptionPane.DEFAULT_OPTION,
        JOptionPane.INFORMATION_MESSAGE, null,new Object[]{"Black", "White"},1);
    	  if(choice==0){
    		  colour = false;
    		  player = "B";
    	  }
    	  else {
    		  colour = true;
    		  player = "W";
    	  }
      }
      printColour = (colour == true) ? "(White)" : "(Black)";
    //
    player[] p = new player[2];
    p[0] = new player(size, colour);//player
    p[1] = new player(size, !colour);//computer
    board b;
    if(Gui == false)//initialises command-line board
    {
    	b = new board(size, p[0], p[1]);
    }
    else//initialises graphic board
    {
    	b = new GuiBoard(size, p[0], p[1]);
    }
    singleGame(size, colour, p, b, player, pass, Gui);//starts game loop method
  }

  private static void singleGame(int size, boolean colour, player[] p, board b, String player, int pass, boolean Gui)
  {
    boolean shown = false;//stores if text for move has been shown
    boolean endgame = false;
    int i=0,j=1,add=1;//variables for alternating move
    if(colour == true)
    {
      i=1;
      j=0;
      add=-1;
    }
    while(endgame == false)//loop until game ends
    {
      for(; Math.abs(i)<2 && Math.abs(j)<2; i+=add, j-=add)//alternate turns
      {
       if(checks.pass(p[i], p[j], size, b) == false)//checks if player must pass
       {
        shown = false;
        int[][] move = null;
        while(move == null)
        {
          if(i == 0)//player to move
          {
            System.out.print(b.toString());//print board
            if(Gui == false)//reads player move for command-line mode
            {
            	System.out.println();
            	System.out.println("Your move:");
            	move = movereader(size, p[i], p[j], b);//Read and check move
            }
            else//prints score & reads player move for graphics mode
            {
              StdDraw.setPenColor(Color.BLACK);
              StdDraw.setFont(new Font(Font.SANS_SERIF, Font.ITALIC+ Font.BOLD, 20));
              StdDraw.text(9+2*(size-4), size*10+(double)size/2, "Your move "+printColour);
              StdDraw.text(7*size, size*10+(double)size/2,"Score: "+count[0]+" | "+count[1]);
              StdDraw.show(0);
              shown = true;
            	move = checks.GuiMove(size, p[i], p[j], b, false);
            }
          }
          else//computer to move
          {
            if(shown == false)//restrics the baord from re-showing each inorrect move
            {
              shown = true;
            }
            move = compmove(size, p[i], p[j], b);//chose and check move
          }

          if(i==1 && move != null)//if the computer makes a valid move
          {
	         System.out.print(b.toString());//print board
	         if(Gui == false) {System.out.println();}
                 else//for graphic mode, show the opponent is "making" a move
                 {
                    StdDraw.setPenColor(Color.BLACK);
                    StdDraw.setFont(new Font(Font.SANS_SERIF, Font.ITALIC+ Font.BOLD, 20));
                    StdDraw.text(9+2*(size-4), size*10+(double)size/2, "Opponents move");
                    StdDraw.text(7*size, size*10+(double)size/2,"Score: "+count[0]+" | "+count[1]);
                    StdDraw.show(0);
                 }
	         //delay output to show that computer moves
	         try
	         {
	            Thread.sleep(500);
	         }
	         catch (InterruptedException ex) {
	            Logger.getLogger(single.class.getName()).log(Level.SEVERE,
	                                                              null, ex);
	         }//
	         //print computers move
                 if(Gui == false)
                 {
                    System.out.print("Computer Moves: ");
                    for(int m1 =0; m1<2; m1++)
                    {
                      for(int m2=0; m2<2; m2++)
                      {
                        if(m2 == 0)
                        {
                          System.out.print((char)((size-1-move[m1][m2])+(int)('A')));
                        }
                        else
                        {
                          System.out.print((char)(move[m1][m2]+(int)('A')));
                        }
                      }
                    }
                    System.out.println();
                 }
	         //
          }
        }
          //check if landing on opponent
          if(p[j].occupied(move[1][0], move[1][1]) != -1)
          {
        	//delete opponent pieceif(b.won[1])
            {

            }
            p[j].delete(p[j].occupied(move[1][0], move[1][1]));
          }
          //
          p[i].move(move);//Implement move
          b.reset();//Reset board to show new move

          //check for win
          String winner = b.checkwinner(p, player);
          if(winner != null)
          {
            count[(b.won[0] == true) ? 0 : 1]++;
            System.out.print(b.toString());//print board
            if(Gui == false)//comman-line mode
            {
            	System.out.println();
            	System.out.println(winner);
            }
            else//grapics mode
            {
            	JOptionPane.showMessageDialog(null, winner);
            }
            endgame = true;
            i = 3;
          }
          //
       }
       else//pass functionality
       {
          if(pass == 3)//for draws
          {
              if(Gui == false)
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
      }
      if(colour == true)//reset turn variables
      {
        i=1;
        j=0;
      }
      else
      {
        i = 0;
        j = 1;
      }
    }
  }

  //pAcc: active player | pDor: dormant player
  public static int[][] movereader(int size, player pAcc, player pDor, board b)
  {
    //read move
    Scanner sc = new Scanner(System.in);
    int move[][] = new int[2][3];
    String input = sc.next();//
    //check quit
    if(input.equals("QUIT"))
    {
  	  System.out.println("player quit");
      System.exit(0);
    }//
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
    }//

    if(checks.movecheck(size,pAcc,pDor,b,move) != false)
    {
      return move;
    }
    else
    {
      	  System.out.println("ERROR: invalid move");
      return null;
    }
  }

  //decides on computers move
  public static int[][] compmove(int size, player pAcc, player pDor, board b)
  {
    int[][] move = new int[2][3];
    for(int i=0; i<2; i++)
    {
      for(int j =0; j<2; j++)
      {
        move[i][j] = (int)(Math.random()*size+0);
      }
    }

    if(checks.movecheck(size,pAcc,pDor,b,move) != false)
    {
      return move;
    }
    else
    {
      return null;
    }
  }
}
