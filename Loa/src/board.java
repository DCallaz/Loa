public class board
{
  //initialise all variables for board and child classes
    protected Object[][] tile;
    protected int size;
    protected player p1;
    protected player p2;
    public static boolean[] won;
  //
  public board(int size, player p1, player p2)//constructor
  {
    this.size = size;
    tile = new Object[size][size];
    if(p1.side == true)
    {
      this.p1 = p1;
      this.p2 = p2;
    }
    else
    {
      this.p1 = p2;
      this.p2 = p1;
    }
    reset();
  }

  public void reset()//redraws board tiles with player pieces
  {
    for(int i=0; i<size; i++)
    {
      for(int j=0; j<size; j++)
      {
        if(p1.occupied(i, j) != -1)
        {
          tile[i][j] = "W";
        }
        else if(p2.occupied(i, j) != -1)
        {
          tile[i][j] = "B";
        }
        else
        {
          tile[i][j] = ".";
        }
      }
    }
  }

  public int getSize()
  {
    return size;
  }

  public Object getOb(int x, int y)
  {
    return tile[x][y];
  }

  /*  count the number of pieces in row or column
      rc = 0 for row, = 1 for col
  */
  public int rcCount(int rc, int letter, player p1, player p2)
  {
    int count = 0;
    for(int i=0; i<size; i++)
    {
        if(rc == 0)
        {
            if(p1.occupied(letter, i) != -1 || p2.occupied(letter, i) != -1)
            {
              count++;
            }
        }
        else
        {
            if(p1.occupied(i, letter) != -1 || p2.occupied(i, letter) != -1)
            {
              count++;
            }
        }
    }
    return count;
  }

  /*  count the number of pieces in a diagonal
      pn =0 for positive diag.,=1 for negetive diag.
  */
  public int diagCount(int pn, int row, int col, player p1, player p2)
  {
    row = (size-1)-row;
    int count = 0;
    //getting first tile of diagonal
      int max = (-size+1)*(pn)+size;
        while(row != (size-1) && col != (max-1))
        {
          row++;
          col += 1-(2*pn);
        }
        row++;
        col++;
    //

    //counting
      max = (size-1)*(pn-1)+size;
      while(row != 1 && col != max)
      {
        if(p1.occupied(size-row, col-1) != -1 || p2.occupied(size-row, col-1) != -1)
        {
          count++;
        }
        row--;
        col += 1+(2*(pn-1));
      }
      if(p1.occupied(size-row, col-1) != -1 || p2.occupied(size-row, col-1) != -1)
      {
        count++;
      }
    //
      return count;
  }

  public String checkwinner(player[] p, String player)//checks if a player has won
  {
      won = new boolean[]{false, false};
      for(int i=0; i<2; i++)//loop for both players
      {
          if(p[i].group() == true)
          {
            won[i] = true;
          }
      }
      if(won[0] == true && won[1] == true)
      {
        return "DRAW";
      }
      else if(won[0] == true)
      {
          if(player.equals("B"))
          {
            return "WINNER: black";
          }
          else
          {
            return "WINNER: white";
          }
      }
      else if(won[1] == true)
      {
          if(player.equals("B"))
          {
            return "WINNER: white";
          }
          else
          {
            return "WINNER: black";
          }
      }
      else
      {
        return null;
      }
  }

  //method to print out the board
  @Override
  public String toString()
  {
    String temp = "  ";
    //printing top board headers
    for(char c = 'A'; c != (char)((int)('A')+size); c = (char)((int)(c)+1))
    {
      temp += c+" ";
    }
    temp += "\n";
    for(int i=0; i<size; i++)
    {
      for(int j=-1;j<size; j++)
      {
        if(j == -1)//printing side board headers
        {
          temp += (char)(('A')+size-1-i)+" ";
        }
        else
        {
            temp += tile[i][j]+" ";
        }
      }
      if(i != size-1)
      {
        temp += "\n";
      }
    }
    return temp;
  }
}
