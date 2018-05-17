
import javax.swing.JOptionPane;


public class checks {
	  public static boolean movecheck(int size,player pAcc, player pDor, board b,
	          int[][] move)
		{
			//check place & jump valid
			if((pAcc.occupied(move[0][0],move[0][1])==-1) || jumpOpp(move, pDor)==false || (pAcc.occupied(move[1][0], move[1][1])!=-1))
			{
				return false;
			}//

			//check row/col jump valid
			//row counting
			if(move[0][0] == move[1][0])
			{
				int row = b.rcCount(0, move[0][0], pAcc, pDor);
				if(Math.abs(move[0][1]-move[1][1]) != row)
				{
					return false;
				}
			}//
			//column counting
			else if(move[0][1] == move[1][1])
			{
				int col = b.rcCount(1, move[0][1], pAcc, pDor);
				if(Math.abs(move[0][0]-move[1][0]) != col)
				{
					return false;
				}
			}//
			//positive diagonal counting
			else if(move[1][0]-move[0][0] == -(move[1][1]-move[0][1]))
			{
				int diag = b.diagCount(0, move[0][0], move[0][1], pAcc, pDor);
				if(Math.abs(move[1][0]-move[0][0]) != diag)
				{
					return false;
				}
			}//
			//negative diagonal counting
			else if(move[1][0]-move[0][0] == move[1][1]-move[0][1])
			{
				int diag = b.diagCount(1, move[0][0], move[0][1], pAcc, pDor);
				if(Math.abs(move[1][0]-move[0][0]) != diag)
				{
					return false;
				}
			}//
			//not moving in valid line
			else
			{
				return false;
			}//
			return true;//if valid move, return true
		}

		/*checks if the move jumps over opposition
			NB  This method uses vectors of the x and y components to find the
					direction which the piece is moving, by finding the vector direction
		*/
	  public static boolean jumpOpp(int[][] move, player pDor)
	  {
	    int x = move[1][0]-move[0][0];//x-component
	    int y = move[1][1]-move[0][1];//y-component
	    int addx=0, addy=0;
	    if(x == 0){addx = 1;}
	    if(y == 0){addy = 1;}
	    int xchange = (x)/Math.abs(move[1][0]-move[0][0]+addx);//x-direction
	    int ychange = (y)/Math.abs(move[1][1]-move[0][1]+addy);//y-direction
	    int[] checker = {move[0][0]+xchange, move[0][1]+ychange};
			//loops through all pieces in the direction
		    while(checker[0] != move[1][0] || checker[1] != move[1][1])
		    {
		      if(pDor.occupied(checker[0], checker[1]) != -1)
		      {
		        return false;
		      }
		      if(checker[0] != move[1][0])
		      {
		        checker[0] += xchange;
		      }
		      if(checker[1] != move[1][1])
		      {
		         checker[1] += ychange;
		      }
		    }
			//
	    return true;
	  }

		//method to check if a player must pass
	  public static boolean pass(player pAcc, player pDor,int size,board b)
	  {
	    for(int id=0; id<pAcc.getNum(); id++)//loops through all piceces
	    {
					//initialise move
		      int[][] move = {{pAcc.piece(id)[1],pAcc.piece(id)[0]},{0, 0}};
		      for(int x=1; x>-1; x--)//loops for all x directions
		      {
			        for(int y=1; y>-1; y--)//loops for all y directions
			        {
				          if(x!=0 || y!=0)//while the move is not in no direction
				          {
					            int change = 0;//stores direction and valid length
					            if(x == 0)//vertical movement
					            {
					              change = b.rcCount(0, move[0][0], pAcc, pDor);
					            }
					            else if(y == 0)//horizontal movement
					            {
					              change = b.rcCount(1, move[0][1], pAcc, pDor);
					            }
					            else if(y+x == 0)//negative diagonal movement
					            {
					              change = b.diagCount(0,move[0][0],move[0][1],pAcc,pDor);
					            }
					            else//positive diagonal movement
					            {
					              change = b.diagCount(1,move[0][0],move[0][1],pAcc,pDor);
					            }
					            move[1][0] = move[0][0]+x*change;
					            move[1][1] = move[0][1]+y*change;
					            if(movecheck(size, pAcc, pDor, b, move) == true)
					            {
					              return false;
					            }
				          }
			        }
		      }
	    }
	    return true;
	  }

		//reads a move from the player for graphical mode
    public static int[][] GuiMove(int size, player pAcc, player pDor, board b, boolean net)
    {
        int move[][] = new int[2][3];
        int count = 0;
        int x=-1, y =-1;

	     	while(count < 2)//loop until two positions are obtained
	      {
	         if(StdDraw.mousePressed() == true)
	         {
	            int tempx = (int)(StdDraw.mouseX()/10);//x-value for player class
	            int tempy = (int)(StdDraw.mouseY()/10);//y-value for player class
							//if the next input is not the current & is a valid board position
	            if((x != tempx || y != tempy) && tempx>-1 && tempy<size)
	            {
									//save a valid first move & draw active piece
	                if((count == 0 && pAcc.occupied((size-1)-tempy,tempx) != -1) || (count == 1))
	                {
	                    x = tempx;
	                    y = tempy;
	                    move[count][1] = x;
	                    move[count][0] = y;

	                    pAcc.toggleColour();
	                    StdDraw.filledCircle(move[count][1]*10+5, move[count][0]*10+5, 4.5);
	                    StdDraw.show(0);
	                    count++;
	                    StdDraw.mouseRelease();
	                }
	            }
							//check if player clicks quit button
	            if(StdDraw.mouseX()>(size*10-(size)) && StdDraw.mouseY()>(10*size+size/2))
	            {
	                JOptionPane.showMessageDialog(null,"Thank you for playing","Player quit", JOptionPane.PLAIN_MESSAGE);
	                if(net)
	                {
	                    return new int[][]{{-1}};
	                }
	                else
	                {
	                    System.exit(0);
	                }
	            }
	         }
	      }

        //check place exists
	        for(int i=0; i<2; i++)
	        {
	          for(int j=0; j<2; j++)
	          {
	            if(j == 0)
	            {
	              move[i][j] = (size-1)-move[i][j];
	            }
	            if((move[i][j]+1) > size || (move[i][j])<0)
	            {
	              JOptionPane.showMessageDialog(null, "Invalid move", "ERROR", JOptionPane.ERROR_MESSAGE);
	              return null;
	            }
	          }
	        }
				//

        if(checks.movecheck(size,pAcc,pDor,b,move) != false)//check valid move
        {
          return move;
        }
        else
        {
          JOptionPane.showMessageDialog(null, "Invalid move", "ERROR", JOptionPane.ERROR_MESSAGE);
          return null;
        }
    }
}
