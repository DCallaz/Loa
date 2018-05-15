/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.JOptionPane;

/**
 *
 * @author Dylan
 */
public class Draw {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        StdDraw.init();
        int size = 0;
        while(size<4 || size>16)
        {
            size = Integer.parseInt(JOptionPane.showInputDialog("Enter size"));
        }
        int max = 10*(size+1);
        StdDraw.setScale(0, max);
        int radius = 5;
        int diameter = 2*radius;
        StdDraw.setPenRadius(0.01);
        for(int i = 0; i<2; i++)
        {
            for(int line = 0; line<=size; line++)
            {
                StdDraw.line(line*10*i, line*10*(1-i), (line*10*i)+(1-i)*(max), (line*10*(1-i))+(i)*(max));
            }
        }
        StdDraw.setPenRadius();
        for(int x = 0; x<size; x++)
        {
            for(int y=0; y<size; y++)
            {
                if(x-y == 0 || Math.abs(x-y)==(size-1))
                { 
                }
                else if(x==0 || x==(size-1))
                {
                    StdDraw.setPenColor(StdDraw.BLUE);
                    StdDraw.filledCircle(x*diameter+radius,y*diameter+radius, radius-0.5);
                }
                else if(y==0 || y==(size-1))
                {
                    StdDraw.setPenColor(StdDraw.RED);
                    StdDraw.filledCircle(x*diameter+radius,y*diameter+radius, radius-0.5);
                }
            }
        }
        
    }
    
}