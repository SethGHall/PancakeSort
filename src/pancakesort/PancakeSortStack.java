/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pancakesort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Seth
 */
public class PancakeSortStack extends ArrayList<Pancake>{
    
    public PancakeSortStack()
    {   super();
        //super.elements = new Pancake[50];
        //numElements = 0;
    }
    
    public void flip(int index){
        
        Queue<Pancake> queue = new LinkedList<>();
        int size = size();
        for(int i=index; i<size ;i++)
            queue.offer((Pancake)pop());
        
        while(!queue.isEmpty())
            push(queue.poll());
        
    }
    
    public Pancake pop()
    {
        return remove(size()-1);
    }
    
    public Pancake peek()
    {
        return get(size()-1);
    }
    
    public void push(Pancake p)
    {   add(p);
    }
    
    
    public int findMaxPancakeFromIndex(int offset)
    {
        Pancake biggestPancake = (Pancake)get(offset); 
        int biggestPosition = offset;
        for(int i=offset+1;i<size();i++)
        {
            if(biggestPancake.compareTo((Pancake)get(i)) < 0)
            {   biggestPosition =i;
                biggestPancake = (Pancake)get(i);
            }
        }
        
        return biggestPosition;
    }
    
    public void pancakeSort()
    {
        //repeat until all sorted
        int numPancakes = size()-1;
        for(int posFromBottom=0;posFromBottom<numPancakes;posFromBottom++)
        {
            System.out.println("Starting from pancake number "+posFromBottom);
            //find biggest pancake
            int maxPancakeIndex = findMaxPancakeFromIndex(posFromBottom);
            
            System.out.println(toString());
            
            if(maxPancakeIndex != posFromBottom)
            {   
                if(maxPancakeIndex != numPancakes)
                {
                    System.out.println("FLIP MAX"+maxPancakeIndex);
                    flip(maxPancakeIndex);
                    System.out.println(toString());
                }
                
                System.out.println("FLIP POS"+posFromBottom);
                flip(posFromBottom);
                System.out.println(toString());
            }
        }
       
    }
    
    @Override
    public String toString()
    {  String output = "";
       for (int i=size()-1; i>=0; i--)
       {  output += i+")|__"+(get(i))+"__|\n";
          
       }
       output += "-----------\n";
       return output;
    }
      
    
    public static void main(String[] args)
    {
        List<Integer> list = new ArrayList<>();
        
        int max = 50;
        
        for(int i=0;i<max;i++)
            list.add(i);
        
        Collections.shuffle(list);
        
        PancakeSortStack stack = new PancakeSortStack();
        
        for(int i=0;i<list.size();i++)
            stack.push(new Pancake(list.get(i)));
        
        System.out.println("Top of stack is "+stack.peek());
        
        System.out.println("STACK:\n");
        System.out.println(stack.toString());
        
        stack.pancakeSort();
        
    }
}
