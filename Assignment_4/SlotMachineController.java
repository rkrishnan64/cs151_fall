package Assignment_4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class SlotMachineController extends JPanel {
	private ArrayList<ActionListener> listeners;
	
	public SlotMachineController() {
		System.out.println("Controller not given view. Initialization failed.");
	}

	public SlotMachineController(SlotMachineView view) {
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
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Chose a skin file");

				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					ActionEvent newEvent = new ActionEvent(chooser.getCurrentDirectory().getAbsolutePath(), ActionEvent.ACTION_FIRST, "loadSkin");
					for( ActionListener a : listeners ) a.actionPerformed(newEvent);
				} else {
					System.out.println("No skin selected.");
				}
			}
		});
	}

	public void addActionListener(ActionListener a) {
		listeners.add(a);
	}
}
