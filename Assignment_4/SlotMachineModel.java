package Assignment_4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class SlotMachineModel implements ActionListener {
	private ArrayList<ActionListener> listeners;
	private int tokens, winnings;
	private int[] symbols;
	
	public SlotMachineModel() {
		listeners = new ArrayList<ActionListener>();
		
		tokens = 10;
		winnings = 0;
		
		ActionEvent newEvent = new ActionEvent(this, ActionEvent.ACTION_FIRST, "update");
		for( ActionListener a : listeners ) a.actionPerformed(newEvent);
	}

	public void addActionListener(ActionListener a) {
		listeners.add(a);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("update")) {
			//cost 
			tokens--;
			
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
			if(done >= 3) {
				winnings = symbols[i] * done;
				tokens += winnings;
			} else
				winnings = 0;
		} else if (e.getActionCommand().equals("init")) {
			SlotMachineView view = (SlotMachineView) e.getSource();
			symbols = new int[view.skin.getNumWheels()];
		}
		
		// Send out events to listeners
		ActionEvent newEvent = new ActionEvent(this, ActionEvent.ACTION_FIRST, "update");
		for( ActionListener a : listeners ) a.actionPerformed(newEvent);
	}

	public int getTokens() {
		return tokens;
	}

	public int getWinnings() {
		return winnings;
	}

	public int[] getSymbols() {
		return symbols;
	}
	
}
