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
            double sizeD = size;
	    if(size>10)
	    {
	    	StdDraw.setCanvasSize((512+(size-10)*50), (int)((512+(size-10)*50)*(1+(1/2*sizeD))), "Lines of Action");
	    }
	    else{
	    	StdDraw.setCanvasSize(512, (int)(512*(1+(1/2*sizeD))), "Lines of Action");
	    }
	    StdDraw.setXscale(0, size*10);
	    StdDraw.setYscale(0, size*10+5);
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
            for(int i=0; i<size; i++)
            {
                for(int j=0; j<size; j++)
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
            //

            //draw top elements
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.line(0, size*10, size*10, size*10);
            StdDraw.setPenColor(230, 204, 179);
            StdDraw.filledRectangle((size/2)*10, size*10+RADIUS, (size/2)*10, RADIUS);
                //draw close button
                StdDraw.setFont();
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.filledRectangle((size*10)-2, max-6, 2, 1);
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.text((size*10)-2, max-6, "Close");
                //
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
