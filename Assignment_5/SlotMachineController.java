package Assignment_5;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import Assignment_5.SlotMachineView.CustomButton;
/**
 * Controller for Slot Machine
 *
 */
public class SlotMachineController extends JPanel {
	private ArrayList<ActionListener> listeners;
	private ArrayList<WheelThread> wheelThreads;
	private SlotMachineView view;

	/**
	 * Constructor
	 * @param view The view for the Slot Machine
	 */
	public SlotMachineController(final SlotMachineView view) {
		// Setup skin
		view.skin = new SlotSkin();
		this.view = view;
		
		// Init
		view.playButton = new CustomButton(view.skin.getImage("play"), view.skin.getImage("playPressed"));
		view.jmi = new JMenuItem("Load New Skin");
		listeners = new ArrayList<ActionListener>();
		wheelThreads = new ArrayList<WheelThread>();
		
		// Add listener for play button
		view.playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(wheelThreads.size() == 0) {
					for(int i = 0; i < view.skin.getInt("numWheels"); i++) {
						WheelThread thisThread = new WheelThread(i, view.skin.getInt("numSymbols"));
						wheelThreads.add(thisThread);
						thisThread.start();
						view.stopButtons.get(i).addActionListener(new StopButtonAction(thisThread));
					}
					view.showSymbols = true;
					ActionEvent newEvent = new ActionEvent(this, ActionEvent.ACTION_FIRST, "update");
					for( ActionListener a : listeners ) a.actionPerformed(newEvent);
				}
			}
		});
		
		// Add listener for loading skin
		view.jmi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Choose a file
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Chose a skin file");
				
				// Load skin of chosen file
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					// Load skin from path
					String path = chooser.getCurrentDirectory().getAbsolutePath();
					view.skin.loadSkin(path);
					
					// Redo wheel threads
					for(WheelThread w : wheelThreads) {
						w.interrupt();
					}
					wheelThreads.clear();
					
					// Send loadSkin event
					ActionEvent newEvent = new ActionEvent(view, ActionEvent.ACTION_FIRST, "loadSkin");
					for( ActionListener a : listeners ) a.actionPerformed(newEvent);
				} else {
					System.out.println("No skin selected.");
				}
			}
		});
	}
	
	/**
	 * Threads for Wheels
	 *
	 */
	public class WheelThread extends Thread {
		private int targetWheel, numSymbols;
		public boolean waiting;
		
		/**
		 * Constructor
		 * @param targetWheel The index of the wheel to manipulate
		 * @param numSymbols The number of symbols available for this skin
		 */
		public WheelThread(int targetWheel, int numSymbols) {
			waiting = false;
			this.targetWheel = targetWheel;
			this.numSymbols = numSymbols;
		}
		
		/**
		 * Set wheel to random symbol then wait
		 */
		@Override
		public void run() {
			while(!waiting) {
				try {
					// Get random number for symbol
					Random rand=new Random();
					int currentSymbol = rand.nextInt(250003) % numSymbols;
					
					// Set symbol for wheel
					view.wheels.get(targetWheel).setSymbol(currentSymbol);
				
					// Wait
					this.sleep(150);
				} catch (InterruptedException e) {
					waiting = true;
				}
			}
		}
	}
	
	/**
	 * ActionListener for Stop Buttons
	 *
	 */
	public class StopButtonAction implements ActionListener {
		private WheelThread targetThread;
		
		/**
		 * Constructor
		 * @param target The WheelThread to interrupt
		 */
		public StopButtonAction(WheelThread target) {
			targetThread = target;
		}
		
		/**
		 * Interrupt thread and send data to model if this is the last stop button pressed
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!targetThread.waiting) {
				// Interrupt thread
				targetThread.interrupt();
				
				// Determine if all are stopped
				boolean allStopped = true;
				for(WheelThread w : wheelThreads) {
					if(!(w.waiting || w.equals(targetThread))) allStopped = false;
				}
				
				// If all are stopped, send symbols to model for results
				if(allStopped) {
					// Make array of current symbols
					int[] symbols = new int[view.skin.getInt("numWheels")];
					for(int i = 0; i < view.skin.getInt("numWheels"); i++) {
						symbols[i] = view.wheels.get(i).getSymbol();
					}
					
					// Send current symbols to model to determine results
					ActionEvent newEvent = new ActionEvent(symbols, ActionEvent.ACTION_FIRST, "results");
					for( ActionListener a : listeners ) a.actionPerformed(newEvent);
					wheelThreads.clear();
				}
			}
		}
	}
	
	/**
	 * Add an ActionListener and send init event
	 * @param a The ActionListener to add
	 */
	public void addActionListener(ActionListener a) {
		listeners.add(a);
		
		// Send first events
		ActionEvent newEvent = new ActionEvent(view, ActionEvent.ACTION_FIRST, "init");
		for( ActionListener al : listeners ) al.actionPerformed(newEvent);
	}
}
