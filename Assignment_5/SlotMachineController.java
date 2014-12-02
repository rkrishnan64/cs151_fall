package Assignment_5;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
		
		// Add listener for play button
		view.playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ActionEvent newEvent = new ActionEvent(this, ActionEvent.ACTION_FIRST, "update");
				for( ActionListener a : listeners ) a.actionPerformed(newEvent);
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
					String path = chooser.getCurrentDirectory().getAbsolutePath();
					view.skin.loadSkin(path);
					ActionEvent newEvent = new ActionEvent(view, ActionEvent.ACTION_FIRST, "loadSkin");
					for( ActionListener a : listeners ) a.actionPerformed(newEvent);
				} else {
					System.out.println("No skin selected.");
				}
			}
		});
	}
	
	/**
	 * ActionListener for Stop Buttons
	 *
	 */
	public class stopButtonAction implements ActionListener {
		private Thread targetThread;
		
		public void ThreadButtonAction(Thread target) {
			targetThread = target;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			targetThread.interrupt();
		}
	}
	
	/**
	 * Add an ActionListener
	 * @param a The ActionListener to add
	 */
	public void addActionListener(ActionListener a) {
		listeners.add(a);
		
		// Send first events
		ActionEvent newEvent = new ActionEvent(view, ActionEvent.ACTION_FIRST, "init");
		for( ActionListener al : listeners ) al.actionPerformed(newEvent);
	}
}
