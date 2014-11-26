package Assignment_4;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class SlotMachineRunner {
	private static SlotMachineView view;
	private static SlotMachineController controller;

	public static void main(String[] args) {
		// Model
		SlotMachineModel model = new SlotMachineModel();
		
		// View
		view = new SlotMachineView(model);
		model.addActionListener(view);
		
		// Controller
		controller = new SlotMachineController(view);
		controller.addActionListener(model);
	}

}
