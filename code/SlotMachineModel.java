package Assignment_4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class SlotMachineModel implements ActionListener {
	private ArrayList<ActionListener> listeners;
	private int tokens, winnings, numSymbols;
	private int[] symbols;
	private boolean gameOver;
	
	public SlotMachineModel() {
		listeners = new ArrayList<ActionListener>();
		
		tokens = 10;
		winnings = 0;
		gameOver = false;
		
		ActionEvent newEvent = new ActionEvent(this, ActionEvent.ACTION_FIRST, "update");
		for( ActionListener a : listeners ) a.actionPerformed(newEvent);
	}

	public void addActionListener(ActionListener a) {
		listeners.add(a);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("update")) {
			if(gameOver) {
				// Reset
				tokens = 9;
				winnings = 0;
				gameOver = false;
				
				// Send reset event
				/*ActionEvent newEvent = new ActionEvent(this, ActionEvent.ACTION_FIRST, "reset");
				for( ActionListener a : listeners ) a.actionPerformed(newEvent);*/
				ActionEvent newEvent = new ActionEvent(this, ActionEvent.ACTION_FIRST, "update");
				for( ActionListener a : listeners ) a.actionPerformed(newEvent);
				return;
			}
			
			//cost
			tokens--;
			
			// Assigning new numbers
			Random rand=new Random();
			for(int i=0;i<symbols.length;i++){
				symbols[i]=rand.nextInt(250003)%numSymbols;
			}
			
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
                            done[i][0]=j;
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
				gameOver = true;
			}
		} else if (e.getActionCommand().equals("init")) {
			SlotMachineView view = (SlotMachineView) e.getSource();
			symbols = new int[view.skin.getNumWheels()];
			numSymbols = view.skin.getNumSymbols();
			
			// Send reset event to view
			ActionEvent initEvent = new ActionEvent(this, 0, "reset");
			view.actionPerformed(initEvent);
			return;
		} else if (e.getActionCommand().equals("loadSkin")) {
			for( ActionListener a : listeners ) a.actionPerformed(e);
			return;
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

	public boolean isGameOver() {
		return gameOver;
	}

	public int[] getSymbols() {
		return symbols;
	}
	
}
