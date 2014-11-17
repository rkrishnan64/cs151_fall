package Assignment_4;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class SlotMachineRunner {
	private static SlotMachineView view;
	private static SlotMachineController controller;

	public static void main(String[] args) {
		JFrame slotMachineWindow = new JFrame("Slot Machine");
		slotMachineWindow.setSize(1000, 662);
		slotMachineWindow.setResizable(false);
		slotMachineWindow.setLayout(new BoxLayout(slotMachineWindow.getContentPane(), BoxLayout.Y_AXIS));
		slotMachineWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Model
		SlotMachineModel model = new SlotMachineModel();
		
		// View
		view = new SlotMachineView(model);
		model.addActionListener(view);
		
		// Controller
		controller = new SlotMachineController(view);
		controller.addActionListener(model);
		
		// Add to window
		slotMachineWindow.add(view, BorderLayout.CENTER);
		
		slotMachineWindow.setVisible(true);
	}

}
