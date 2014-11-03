import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class SlotControl extends JPanel implements ActionListener
{
	private JTextField token,winning;
	private JButton play;
	private ActionListener a;
	
	public SlotControl()
	{
		super(new BorderLayout());
		
		JMenuBar jmb=new JMenuBar();
		JMenu jm=new JMenu("Files");
		JMenuItem jmi=new JMenuItem("New Game");
		jmi.addActionListener(new NewGameListener());
		jm.add(jmi);
		jmb.add(jm);
		
		JPanel menu= new JPanel(new BorderLayout());
		menu.add(jmb,BorderLayout.NORTH);
		this.add(menu,BorderLayout.NORTH);
		
		JPanel scores= new JPanel();
		menu.add(scores,BorderLayout.CENTER);
		scores.add(new JLabel("Tokens: "));
		token=(new JTextField("010"));
		token.setEditable(false);
		scores.add(token);
		scores.add(new JLabel("Winnings: "));
		winning=(new JTextField("000"));
		winning.setEditable(false);
		scores.add(winning);
		
		JPanel playPanel=new JPanel();
		this.add(playPanel, BorderLayout.CENTER);
		play=new JButton("Play");
		play.addActionListener(new PlayListener());
		playPanel.add(play);
		
				
	}
	
	public void addActionListener(ActionListener a)
	{
		this.a=a;	
	}
	
	
	
	class PlayListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("Play button clicked...");
			ActionEvent newEvent = new ActionEvent(0, ActionEvent.ACTION_FIRST, "update");
			a.actionPerformed(newEvent);
		}
	}
	
	class NewGameListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("New Game option is selected...");
			ActionEvent newEvent = new ActionEvent(0, ActionEvent.ACTION_FIRST, "reset");
			a.actionPerformed(newEvent);		
		}
	}


	public void actionPerformed(ActionEvent e) 
	{
		SlotMachine temp=(SlotMachine)e.getSource();
		String newWinning=String.format("%03d",temp.getWinning());
		String newToken=String.format("%03d",temp.getToken());
		winning.setText(newWinning);
		token.setText(newToken);
		if(temp.getToken()==0)
			play.setEnabled(false);
		else
			play.setEnabled(true);

		
	}
}
