package pancakesort;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;

/**
 *
 * @author sehall
 */
public class PancakesGUI extends JPanel implements ActionListener, MouseListener{
    
    private Timer timer;
    private JButton resolve, reset;
    private DrawPanel drawPanel;
    private PancakeSortStack stack;
    public boolean allowPlayer, inPanel;
    public final int SLEEP = 100;
    
    
    public PancakesGUI()
    {   super(new BorderLayout());
        try
        {  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e){}

      //  timer = new Timer(20,this);        
        
        resolve = new JButton("Resolve");
        reset= new JButton("Reset");
        resolve.addActionListener((ActionListener)this);
        reset.addActionListener((ActionListener)this);
        drawPanel = new DrawPanel();
        drawPanel.addMouseListener(this);
        super.add(drawPanel,BorderLayout.CENTER);
        
        JPanel southPanel = new JPanel();
        southPanel.add(reset);
        southPanel.add(resolve);
        super.add(southPanel,BorderLayout.SOUTH);
       
        stack = null;
        resetStack();
        
        
       // timer.start();
    }
    
    private void resetStack(){
            
        List<Integer> list = new ArrayList<>();
        
        int max = 20;
        
        for(int i=1;i<=max;i++)
            list.add(i);
        
        Collections.shuffle(list);
        
        stack = new PancakeSortStack();
        
        for(int i=0;i<list.size();i++)
            stack.push(new Pancake(list.get(i)));
        
        System.out.println("STACK:\n");
        System.out.println(stack.toString());
        
        //stack.pancakeSort();
        allowPlayer = true;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        if(source == resolve)
        {
           allowPlayer = false;
           if(stack != null)
           {    
               Thread t = new Thread(new Runnable() {
                   @Override
                   public void run() {
                       performAnimatedPancakeSort();
                   }
               });
               t.start();
               
               
           }
        }
        if(source == reset)
        {   resetStack();
            
        
        }
        
      
        drawPanel.repaint();
    }
    
    private void performAnimatedPancakeSort()
    {
        //repeat until all sorted
        int numPancakes = stack.size();
        drawPanel.repaint();
        try{    Thread.sleep(SLEEP);}catch(Exception e){}
        
        for(int posFromBottom=0;posFromBottom<numPancakes;posFromBottom++)
        {
         
            int maxPancakeIndex = stack.findMaxPancakeFromIndex(posFromBottom);
            
            Pancake p = stack.get(maxPancakeIndex);
            p.highlight(true);
            drawPanel.repaint();            
            try{    Thread.sleep(SLEEP);}catch(Exception e){}
            
            if(maxPancakeIndex != posFromBottom)
            {   
                if(maxPancakeIndex != numPancakes)
                {
                    System.out.println("FLIP MAX"+maxPancakeIndex);
                    
                    stack.flip(maxPancakeIndex);

                      drawPanel.repaint();
                    try{    Thread.sleep(SLEEP);}catch(Exception e){}
                }

                Pancake bottomPancake = stack.get(posFromBottom);
                bottomPancake.highlight(true);
                
                drawPanel.repaint();
                try{    Thread.sleep(SLEEP);}catch(Exception e){}
                
                stack.flip(posFromBottom);
                drawPanel.repaint();
                try{    Thread.sleep(SLEEP);}catch(Exception e){}
                
                bottomPancake.highlight(false);
                
                drawPanel.repaint();
                try{    Thread.sleep(SLEEP);}catch(Exception e){}
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        
        //LETS TEST OUT
        if(allowPlayer && inPanel && stack != null)
        {
           //calculate which pancake;
           
            int pancakeHeight =  (getHeight())/stack.size();
            // int x = (int) (((double)((pancakeHeight)-e.getY())/(getHeight()-pancakeHeight)) * stack.size()); 
            
            int stackHeight = stack.size()*pancakeHeight;
           
                  
            int x = (stack.size() - (int)Math.round((((double)(e.getY())/stackHeight) * stack.size()))) -1;
           
            stack.flip(x);
            drawPanel.repaint();
            //getHeight() - (inc*((getHeight()-50)/stack.size())
             
            System.out.println("X is "+x+" selection number "+(getHeight()-e.getY())+ " STACK HEIGHT "+stackHeight);
            
        }
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
       inPanel=true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        inPanel = false;
    }
    
    
    
    private class DrawPanel extends JPanel
    {
        public DrawPanel()
        {   super();
            super.setBackground(Color.white);
            super.setPreferredSize(new Dimension(500,500));
        }
        
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            if(stack != null)
            {
                
                
                int cY = getHeight()/stack.size();
                int inc  = 1;
                
               
                for(int i=0;i<stack.size();i++)
                {   
                    Pancake p = stack.get(i);
                    int pancakeWidth = (getWidth())/stack.size() * p.getValue();
                    p.draw(g, getWidth()/2 - pancakeWidth/2,getHeight() - ((i+1)*((getHeight())/stack.size())), pancakeWidth, (getHeight())/stack.size());
                }
               
            }
        }
    }
    
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Pancake Sort Problem");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new PancakesGUI());
        frame.pack();                                      //pack frame
        frame.setVisible(true);                                      //show the frame
    }

}
