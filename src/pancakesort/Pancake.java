/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pancakesort;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

/**
 *
 * @author Seth
 */
public class Pancake implements Comparable<Pancake>{

    private final int value;
    private Color colour;
    private boolean highlight;
    
    public Pancake(int value)
    {
        this.value = value;
        Random rand = new Random();
        highlight = false;
        colour = new Color(rand.nextFloat(),rand.nextFloat(),rand.nextFloat());
    }
    
    @Override
    public int compareTo(Pancake o) {
        return value - o.value;
    }

    public void draw(Graphics g, int x, int y, int width, int height){
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(1));
        g.setColor(colour);
        g.fillRoundRect(x, y, width, height, 10, 10);
        g.setColor(Color.BLACK);
        
        if(highlight)
        {   g2.setStroke(new BasicStroke(5));
            g.drawRoundRect(x, y, width, height, 10, 10);
    
        }
        g.drawString(""+getValue(), x+width/2, y+height/2);
    }
    public void setColor(Color c)
    {   this.colour = c;
        
    }
    
    public void highlight(boolean highlight)
    {
        this.highlight = highlight;
    }
    
    public int getValue()
    {
        return value;
    }
    
    @Override
    public String toString()
    {
        return "|___"+value+"___|";
    }
}
