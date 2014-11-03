import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

import java.util.Random;

public class SlotMachine implements ActionListener
{
	private int[] symbols=new int[4];
	private boolean result;
	private int token,winning;
	private ArrayList<ActionListener> listeners;
	
	public SlotMachine()
	{
		token=10;
		winning=0;
		listeners=new ArrayList();
	}
	public SlotMachine(int t, int w)
	{
		token=t;
		winning=w;
	}
	
	public void addActionListener(ActionListener a)
	{
		listeners.add(a);
	}
	
	private void update()
	{
		//cost 
		token--;
		
		//assigning new numbers.
		Random rand=new Random();
		for(int i=0;i<symbols.length;i++)
			symbols[i]=rand.nextInt(7);

		//checking win condition
		int done=0;
		int i=-1;
		for(i=0; i<symbols.length && done<3;i++)
		{
			done=0;
			for(int j=0;j<symbols.length;j++)
			{
				if (symbols[i]==symbols[j])
					done++;
			}
		}
		
		//if won
		if(done>=3)
		{
			result=true;
			System.out.print("tokens:" + token + "--");
			token=token+symbols[i]*done;
			System.out.println(token);
			System.out.print("winnings:"+winning+"--");
			winning=winning+symbols[i]*done;
			System.out.println(winning);
		}
		else
			result=false;
	}
	
	
	//getters for passing data.
	public int[] getSymbols(){return symbols;}
	public boolean getResult(){return result;}
	public int getToken(){return token;}
	public int getWinning(){return winning;}
	
	
	
	//Calculations and pass new data to both SlotView & SlotControl 
	public void actionPerformed(ActionEvent e)
	{
		ActionEvent newEvent=null;
		if (e.getActionCommand().equals("update"))
		{
			System.out.println("Slot Machine is ready to update...");
			update();
			newEvent = new ActionEvent(this, ActionEvent.ACTION_FIRST, "update");
		}
		else if (e.getActionCommand().equals("reset"))
		{
			System.out.println("Slot Machine is about to reset...");
			token=10;
			winning=0;
			newEvent = new ActionEvent(this, ActionEvent.ACTION_FIRST, "reset");
		}
		
		for( ActionListener a : listeners )
			a.actionPerformed(newEvent);
	}
}
