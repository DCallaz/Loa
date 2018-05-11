import java.util.Scanner;
public class testmode
{
  public testmode(int size)
  {
    boolean endgame = false;
    int pass = 0;
    player[] p = new player[2];
    p[0] = new player(size, false);//black player
    p[1] = new player(size, true);//white player
    board b = new board(size, p[0], p[1]);
    while(endgame == false)
    {
      for(int i=0, j=1; i<2; i++, j--)
      {
        if(checks.pass(p[i], p[j], size, b) == false)//checks if player must pass
        {
          int[][] move = null;
          while(move == null)
          {
            System.out.println(b.toString());//print board
            move = movereader(size, p[i], p[j], b);//Read and check move
          }
          //check if landing on opponent
          if(p[j].occupied(move[1][0], move[1][1]) != -1)
          {
            p[j].delete(p[j].occupied(move[1][0], move[1][1]));
            //delete opponent piece
          }
          p[i].move(move);//Implement move
          b.reset();//Reset board to show new move
          //check for win
          String winner = b.checkwinner(p, "B");
          if(winner != null)
          {
              System.out.println(winner);
              endgame = false;
              System.exit(0);
          }
        }
        else//pass functionality
        {
         if(pass == 3)
         {
           System.out.println("DRAW");
           endgame = true;
           System.exit(0);
         }
          pass++;
        }
      }
    }
  }
  public static Scanner sc = new Scanner(System.in);
  //pAcc: active player | pDor: dormant player
  public static int[][] movereader(int size, player pAcc, player pDor, board b)
  {
    //read move
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
          System.exit(0);
        }
      }
    }//

    if(checks.movecheck(size,pAcc,pDor,b,move) == true)
    {
      return move;
    }
    else
    {
      System.out.println("ERROR: invalid move");
      System.exit(0);
      return null;
    }
  }
}