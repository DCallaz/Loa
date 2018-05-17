import java.awt.Color;

public class player
{
  private int number = 0;
  private int pieces[][];
  public boolean side;
  private Color ACTIVE;
  private Color PASSIVE;
  private boolean on = false;

  public player(int size, boolean side)//constructor
  {
    this.side = side;
    number = (size-2)*2;
    pieces = new int[number][2];
    int count =0;
    //iniitialises pieces at sides
      for(int i=0; i<=size; i+=size-1)
      {
        for(int j=1; j<size-1; j++)
        {
          if(side == true)//for white
          {
            pieces[count][0] = i;
            pieces[count][1] = j;
            count++;
          }
          else//for black
          {
            pieces[count][0] = j;
            pieces[count][1] = i;
            count++;
          }
        }
      }
    //
    //initialise active colours
      if(side == true)
      {
      	ACTIVE = StdDraw.LIGHT_GRAY;
      	PASSIVE = StdDraw.WHITE;
      }
      else
      {
      	ACTIVE = StdDraw.RED;
      	PASSIVE = StdDraw.BOOK_RED;
      }
   //
  }

  public int getNum()//returns the number of pieces
  {
    return number;
  }

  public void setNum(int ne)//sets the number of pieces
  {
    number = ne;
  }

  public int[] piece(int id)//returns the piece coordinates
  {
    return pieces[id];
  }

  //checks if space is occupied, and returns id, else -1
  public int occupied(int x, int y)
  {
    for(int i=0; i<number; i++)
    {
      if(x == pieces[i][1] && y == pieces[i][0])
      {
        return i;
      }
    }
    return -1;
  }

  public void move(int[][] move)//moves a piece
  {
    int id = occupied(move[0][0], move[0][1]);
    pieces[id][0] = move[1][1];
    pieces[id][1] = move[1][0];
  }

  public void delete(int id)//deletes a piece
  {
    for(int i =id; i<number-1; i++)
    {
      pieces[i] = pieces[i+1];
    }
    number--;
  }

  public void toggleColour()//sets active colour
  {
	  if(on == false)
	  {
		  StdDraw.setPenColor(ACTIVE);
	  }
	  else
	  {
		  StdDraw.setPenColor(PASSIVE);
	  }
  }

  public void print()//prints out array of pieces
  {
    System.out.println();
    for(int i=0; i<number; i++)
    {
      System.out.print(i+":["+pieces[i][1]+" "+pieces[i][0]+"]  ");
    }
    System.out.println();
  }

  //methods to check if pieces connected
  private static int groupCount;//all counted pieces in group
  private static String checked;//all checked pieces

  public boolean group()//start function of connected check
  {
    groupCount = 1;
    checked = " 0 ";
    //starts the outcheck procedure at the first piece (id=0)
    if(outcheck(new int[] {piece(0)[1],piece(0)[0]}) == true)
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  //method to check surrounding places for connected pieces
  public boolean outcheck(int[] point)
  {
    int[] pos = point;
    for(int xchange=1; xchange>-2; xchange--)//x values around pieces
    {
      int x = pos[0]+xchange;
      for(int ychange=1; ychange>-2; ychange--)//y values around pieces
      {
        int y = pos[1]+ychange;
        if(occupied(x,y) != -1 && !checked.contains(" "+occupied(x,y)+" "))
        {
          groupCount++;//adds 1 to pieces in the group
          checked += occupied(x,y)+" ";//adds the piece checked pieces
          outcheck(new int[]{piece(occupied(x, y))[1],piece(occupied(x, y))[0]});
          //calls incheck on the connected piece
        }
      }
    }
    if(groupCount == number)//checks if all pieces are connected
    {
      return true;
    }
    else
    {
      return false;
    }
  }
}
