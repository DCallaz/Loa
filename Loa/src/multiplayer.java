
import java.util.Scanner;
public class multiplayer
{
  private static networking net;
  public multiplayer(int size, String ip)
  {
    net = new networking();
    int mode = 0;
    if (ip.equals("192.168.1.100"))
		{
			mode = net.connect(ip, true);
		}
		else
		{
			mode = net.connect(ip);
		}
		if (mode == net.SERVER_MODE) {
			System.out.println("(Server mode)");
			System.out.println("Your colour is WHITE");
			System.out.println("Sending size...");
			net.write(size+"");
			System.out.println("size sent");
		} else if (mode == net.CLIENT_MODE) {
			System.out.println("(Client mode)");
			System.out.println("Your colour is BLACK");
			System.out.println("Waiting for size...");
			size = Integer.parseInt(net.read());
			System.out.println("Size received: "+size);
		} else {
			System.out.println("Connection error");
      System.exit(0);
		}
    boolean endgame = false;
    player[] p = new player[2];
    //assign colour
    boolean colour = true;
    String player = "";
    if(mode == net.CLIENT_MODE)
    {
      colour = false;
      player = "B";
    }
    else if(mode == net.SERVER_MODE)
    {
      colour = true;
      player = "W";
    }
    //

    p[0] = new player(size, colour);
    p[1] = new player(size, !colour);
    board b = new board(size, p[0], p[1]);
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
        int[][] move = null;
        while(move == null)//loops until valid move
        {
          if(i == 0)//player moving
          {
            System.out.println(b.toString());//print board
            System.out.println("Your move:");
            move = movereader(size, p[i], p[j], b);//Read and check move
          }
          else//player waiting
          {
        	System.out.println(b.toString());//print board
            System.out.println("Wait for opponent to move...");
            String sMove = net.read();
          	String[] parts = sMove.split("");
            move = new int[2][2];
          	if(sMove.equals("QUIT"))
          	{
          		System.out.println("Opponent has quit");
          		net.close();
          		System.exit(0);
          	}
          	else{

          		for(int x=0; x<2; x++)
          		{
          			for(int y=0; y<2; y++)
          			{
          				move[x][y] = (int)(parts[(x*2)+y].charAt(0))-(int)('A');
                  if(y == 0)
                  {
                    move[i][j] = (size-1)-move[i][j];
                  }
          			}
          		}
          	}
          }
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
              System.out.println(b.toString());//print board
              System.out.println(winner);
              endgame = true;
              System.exit(0);
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
    String input = sc.nextLine();//
    //check quit
    if(input.equals("QUIT"))
    {
      System.out.println("Quitting game");
      net.write("QUIT");
      net.close();
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
      net.write(input);
      return move;
    }
    else
    {
      System.out.println("ERROR: invalid move");
      return null;
    }
  }
}
