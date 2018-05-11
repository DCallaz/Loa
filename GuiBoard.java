import java.awt.Font;

public class GuiBoard extends board{
	
	public final int RADIUS = 5;
	
	public GuiBoard(int size, player p1, player p2)
	{
		super(size, p1, p2);
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
	    if(size>10)
	    {
	    	StdDraw.setCanvasSize(512+(size-10)*50, 512+(size-10)*50);
	    }
	    else{
	    	StdDraw.init("Lines of Action");
	    }
	    StdDraw.setXscale(-10, size*10);
	    StdDraw.setYscale(0, size*10+10);
	}
	
	public String toString()
	{
		//set & reset variables and board
	    String temp = "";
        int max = 10*(size+1);
        int diameter = 2*RADIUS;
        StdDraw.setPenRadius(0.01);
        //
        StdDraw.show(0);
        
        //draw background squares
        for(int i=-1; i<max; i++)
        {
        	for(int j=0; j<max; j++)
        	{
        		if(Math.abs(i+j)%2 == 1)
        		{
        			StdDraw.setPenColor(StdDraw.BROWN);
        			StdDraw.filledSquare(i*10+RADIUS, j*10+RADIUS, RADIUS);
        		}
        		else
        		{
        			StdDraw.setPenColor(StdDraw.DARK_BROWN);
        			StdDraw.filledSquare(i*10+RADIUS, j*10+RADIUS, RADIUS);
        		}
        	}
        }
        
        
        //draw row/col headers
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setFont(new Font("SansSerif", Font.BOLD, 16+(16-size)));
        int count = 0;
        for(char c = 'A'; c != (char)((int)('A')+size); c = (char)((int)(c)+1))
        {
          StdDraw.text(-10+RADIUS, count*10+RADIUS, c+"");
          count++;
        }
        count=0;
        for(char c = 'A'; c != (char)((int)('A')+size); c = (char)((int)(c)+1))
        {
          StdDraw.text(count*10+RADIUS,max-RADIUS , c+"");
          count++;
        }
        //
        
        //draw pieces
        for(int i=0; i<size; i++)
        {
          for(int j=0;j<size; j++)
          {
            if(tile[i][j].equals("B"))
            {
            	StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.filledCircle(j*diameter+RADIUS,(max-10)-(i*diameter+RADIUS), RADIUS-0.5);
            }
            else if(tile[i][j].equals("W"))
            {
            	StdDraw.setPenColor(StdDraw.BOOK_RED);
                StdDraw.filledCircle(j*diameter+RADIUS,(max-10)-(i*diameter+RADIUS), RADIUS-0.5);
            }
          }
        }
        //
        StdDraw.show(0);
        return temp;
	}
}
