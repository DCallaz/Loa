import java.awt.Color;
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
	    StdDraw.setCanvasSize(512, (int)(512*(1+(1/8))));
	    StdDraw.setXscale(0, size*10);
	    StdDraw.setYscale(0, size*10+size);
	}
	
	public String toString()
	{
		//set & reset variables and board
	    String temp = "";
            double halfbar = (double)size/2;
            int board = size*10;
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
                            StdDraw.setPenColor(Color.getHSBColor(26.09f, 31f, 66.27f));
                            StdDraw.filledSquare(i*10+RADIUS, j*10+RADIUS, RADIUS);
                    }
                    else
                    {
                            StdDraw.setPenColor(new Color(198, 138, 83));
                            StdDraw.filledSquare(i*10+RADIUS, j*10+RADIUS, RADIUS);
                    }
                }
            }
            //

            //draw top elements
            double sized = size;
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.line(0, board, board, board);
            StdDraw.setPenColor(230, 204, 179);
            StdDraw.filledRectangle((halfbar)*10, board+halfbar, (halfbar)*10, halfbar);
                //draw close button
                StdDraw.setFont();
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.filledRectangle((board)-(size/2), size+board-halfbar/2, size/2, halfbar/2);
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.text((board)-(size/2), size+board-halfbar/2, "Close");
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
                    StdDraw.filledCircle(j*diameter+RADIUS,(size*10)-(i*diameter+RADIUS), RADIUS-0.5);
                }
                else if(tile[i][j].equals("W"))
                {
                    StdDraw.setPenColor(StdDraw.BOOK_RED);
                    StdDraw.filledCircle(j*diameter+RADIUS,(size*10)-(i*diameter+RADIUS), RADIUS-0.5);
                }
              }
            }
            //
            StdDraw.show(0);
            return temp;
        }
}
