package Assignment_5;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Model for Slot Machine
 *
 */
public class SlotMachineModel implements ActionListener {
	private ArrayList<ActionListener> listeners;
	private int tokens, winnings, numSymbols;
	
	/**
	 * Constructor
	 */
	public SlotMachineModel() {
		listeners = new ArrayList<ActionListener>();
		
		tokens = 10;
		winnings = 0;
	}
	
	/**
	 * Add ActionListener
	 * @param a The ActionListener to add
	 */
	public void addActionListener(ActionListener a) {
		listeners.add(a);
	}
	
	/**
	 * Perform action from listener
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("update")) {
			// Cost of playing game
			tokens--;
			
			// Send update event
			ActionEvent newEvent = new ActionEvent(this, ActionEvent.ACTION_FIRST, "update");
			for( ActionListener a : listeners ) a.actionPerformed(newEvent);
		}else if (e.getActionCommand().equals("results")) {
			// Get current symbols
			int[] symbols = (int[]) e.getSource();
			
			// Checking win condition
			int done[][]=new int[numSymbols][2];
			int i=-1;
			for(i=0; i<numSymbols;i++)
			{
	            done[i][0]=-1;
	            done[i][1]=0;
				for(int j=0;j<symbols.length;j++)
				{
					if (i==symbols[j]){
                        if(done[i][0]==-1)
                            done[i][0]=i;
                        done[i][1]++;
                    }
				}
			}
			
			// Check for win
			winnings=0;
            for(int[] d:done){
                winnings+=d[0]*(d[1]>=3?d[1]:0);
            }
            tokens += winnings;
			
			// Check for game over
			if(tokens <= 0) {
				// Reset data for new game
				tokens = 10;
				winnings = 0;
				
				// Send results and gameOver events
				ActionEvent newEvent = new ActionEvent(this, ActionEvent.ACTION_FIRST, "results");
				for( ActionListener a : listeners ) a.actionPerformed(newEvent);
				ActionEvent newEvent2 = new ActionEvent(this, ActionEvent.ACTION_FIRST, "gameOver");
				for( ActionListener a : listeners ) a.actionPerformed(newEvent2);
				return;
			}
			
			// Send results event
			ActionEvent newEvent = new ActionEvent(this, ActionEvent.ACTION_FIRST, "results");
			for( ActionListener a : listeners ) a.actionPerformed(newEvent);
		} else if (e.getActionCommand().equals("init")) {
			// Get symbol info from view
			SlotMachineView view = (SlotMachineView) e.getSource();
			numSymbols = view.skin.getInt("numSymbols");
			
			// Send init event
			ActionEvent newEvent = new ActionEvent(this, ActionEvent.ACTION_FIRST, "init");
			for( ActionListener a : listeners ) a.actionPerformed(newEvent);
		} else if (e.getActionCommand().equals("loadSkin")) {
			// Get Symbol info from view
			SlotMachineView view = (SlotMachineView) e.getSource();
			numSymbols = view.skin.getInt("numSymbols");
			
			// Reset data for new game
			tokens = 10;
			winnings = 0;
			
			// Send loadskin and gameOver events
			for( ActionListener a : listeners ) a.actionPerformed(e);
			ActionEvent newEvent = new ActionEvent(this, ActionEvent.ACTION_FIRST, "gameOver");
			for( ActionListener a : listeners ) a.actionPerformed(newEvent);
		}
	}
	
	/**
	 * Get number of tokens
	 * @return The number of tokens
	 */
	public int getTokens() {
		return tokens;
	}
	
	/**
	 * Get number of tokens won
	 * @return The number of tokens won.
	 */
	public int getWinnings() {
		return winnings;
	}
}
