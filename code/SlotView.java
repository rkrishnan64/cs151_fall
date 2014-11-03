import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class SlotView extends JPanel implements ActionListener
{
	private JTextField[] sym;
	
	public SlotView()
	{
		super(new GridLayout(1,4));
		sym=new JTextField[4];
		for(int i=0;i<sym.length;i++)
		{
			sym[i]=new JTextField("IM READY!");
			this.add(sym[i]);
			sym[i].setFont(new Font("SansSerif", Font.BOLD, 20));
			sym[i].setEditable(false);
		}	
	}
	
	//updates the slots.
	private void setSymbols(int[] array)
	{
		for(int i=0;i<array.length;i++)
		{
			sym[i].setEditable(true);
			sym[i].setText(array[i]+"");
			sym[i].setEditable(false);
		}
	}
	
	//show whether the user win or lose.
	private void showResult(boolean result)
	{
		if(result)
			JOptionPane.showMessageDialog(null, "Contragulation!!");
		else
			JOptionPane.showMessageDialog(null, "Try it again!");
	}
	
	//receiving data and updating the view interface
	public void actionPerformed(ActionEvent e)
	{
		SlotMachine temp=(SlotMachine)e.getSource();
		if (e.getActionCommand().equals("update"))
		{
			System.out.println("View is ready to update...");
			setSymbols(temp.getSymbols());
			showResult(temp.getResult());
		}
		else if (e.getActionCommand().equals("reset"))
		{
			for(JTextField x:sym)
			{
				x.setEditable(true);
				x.setText("IM READY!");
				x.setEditable(false);
			}
		}
	}
}
