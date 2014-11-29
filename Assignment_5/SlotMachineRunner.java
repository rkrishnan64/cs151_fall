package Assignment_5;


public class SlotMachineRunner {
	private static SlotMachineView view;
	private static SlotMachineController controller;

	public static void main(String[] args) {
		// Model
		SlotMachineModel model = new SlotMachineModel();
		
		// View
		view = new SlotMachineView();
		model.addActionListener(view);
		
		// Controller
		controller = new SlotMachineController(view);
		controller.addActionListener(model);
	}

}
