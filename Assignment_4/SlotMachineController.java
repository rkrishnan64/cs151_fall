package Assignment_4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
	}

	public void addActionListener(ActionListener a) {
		listeners.add(a);
	}
}
