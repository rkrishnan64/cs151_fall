import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


import javax.swing.JFrame;


public class SlotRunner 
{

	public static void main(String [] args)
	{
		SlotRunner run = new SlotRunner();
		run.init();		
	}
	
	public void init()
	{
		JFrame window=new JFrame("Slot Machine"); 
		window.setSize(600,600);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		SlotControl control=new SlotControl();
		SlotView view = new SlotView();
		SlotMachine model=new SlotMachine();
		
		control.addActionListener(model);
		model.addActionListener(view);
		model.addActionListener(control);
		
		window.add(control,BorderLayout.NORTH);
		window.add(view);
		window.setVisible(true);
	}
}
