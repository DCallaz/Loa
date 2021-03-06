
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

	  public static boolean jumpOpp(int[][] move, player pDor)//checks if the move jumps over opposition
	  {
	    int x = move[1][0]-move[0][0];
	    int y = move[1][1]-move[0][1];
	    int addx=0, addy=0;
	    if(x == 0){addx = 1;}
	    if(y == 0){addy = 1;}
	    int xchange = (move[1][0]-move[0][0])/Math.abs(move[1][0]-move[0][0]+addx);
	    int ychange = (move[1][1]-move[0][1])/Math.abs(move[1][1]-move[0][1]+addy);
	    int[] checker = {move[0][0]+xchange, move[0][1]+ychange};
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
	    return true;
	  }
	  
	  public static boolean pass(player pAcc, player pDor,int size,board b)
	  {
	    for(int id=0; id<pAcc.getNum(); id++)
	    {
	      int[][] move = {{pAcc.piece(id)[1],pAcc.piece(id)[0]},{0, 0}};
	      for(int x=1; x>-1; x--)
	      {
	        for(int y=1; y>-1; y--)
	        {
	          if(x!=0 || y!=0)
	          {
	            int change = 0;
	            if(x == 0)
	            {
	              change = b.rcCount(0, move[0][0], pAcc, pDor);
	            }
	            else if(y == 0)
	            {
	              change = b.rcCount(1, move[0][1], pAcc, pDor);
	            }
	            else if(y+x == 0)
	            {
	              change = b.diagCount(0,move[0][0],move[0][1],pAcc,pDor);
	            }
	            else
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
}
