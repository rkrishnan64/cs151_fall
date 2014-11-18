package Assignment_4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SlotMachineView extends JPanel implements ActionListener {
	public SlotSkin skin;
	public CustomButton playButton;
	private JLabel tokens;
	private int[] symbols;
	public JMenuItem jmi;
	public JPanel menu;
	boolean showSymbols;
	private JFrame slotMachineWindow;
	private SlotMachineModel model;
	
	public SlotMachineView() {
		System.out.println("View not given model. Initialization failed.");
	}
	
	public SlotMachineView(SlotMachineModel model) {
		// Get skin
		skin = new SlotSkin();
		symbols = new int[0];
		showSymbols = true;
		this.model = model;
		
		// Set layout of this JPanel
		this.setLayout(null);
		
		// Setup menu bar
		JMenuBar jmb = new JMenuBar();
		JMenu jm = new JMenu("Skins");
		jmi = new JMenuItem("Load New Skin");
		jm.add(jmi);
		jmb.add(jm);
		menu = new JPanel(new BorderLayout());
		menu.add(jmb,BorderLayout.NORTH);
		
		// Set number of tokens
		tokens = new JLabel("Tokens: " + model.getTokens());
		tokens.setLocation(new Point((int) skin.getLabelLocation().getX(), (int) skin.getLabelLocation().getY()));
		tokens.setSize(new Dimension(100, 14));
		tokens.setForeground(Color.white);
		tokens.setBackground(Color.black);
		tokens.setOpaque(true);
		tokens.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(tokens);
		
		// Initialize play button
		playButton = new CustomButton(skin.getPlay(), skin.getPlayPressed());
		playButton.setLocation(new Point((int) skin.getButtonLocation().getX(), (int) skin.getButtonLocation().getY()));
		playButton.addMouseListener(playButton);
		this.add(playButton);
		
		// Setup window
		slotMachineWindow = new JFrame("Slot Machine");
		slotMachineWindow.setSize(skin.getWidth(), skin.getHeight() + 40); // +40 is to offset menu bar
		slotMachineWindow.setResizable(false);
		slotMachineWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		slotMachineWindow.add(this, BorderLayout.CENTER);
		slotMachineWindow.add(menu,BorderLayout.NORTH);
		slotMachineWindow.setVisible(true);
		
		// Send init event to model
		ActionEvent initEvent = new ActionEvent(this, 0, "init");
		model.actionPerformed(initEvent);
	}

	public void paintComponent(Graphics g) {	
		// Draw background
		g.drawImage(skin.getBackground(), 0, 0, skin.getWidth(), skin.getHeight(), null);
		
		// Draw symbols
		if(showSymbols) {
			for(int i = 0; i < symbols.length; i++){
				g.drawImage(skin.getSymbol(symbols[i]), (int) skin.wheelLocation(i).getX(), (int) skin.wheelLocation(i).getY(), skin.getSymbolWidth(), skin.getSymbolHeight(), null);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Load skin if needed
		if (e.getActionCommand().equals("loadSkin")) {
			String path = (String) e.getSource();
			skin.loadSkin(path);
			showSymbols = false;
			this.repaint();
			
			// Send init event to model
			ActionEvent initEvent = new ActionEvent(this, 0, "init");
			model.actionPerformed(initEvent);
		}else if (e.getActionCommand().equals("reset")) {
			// Reset everything
			tokens.setLocation(new Point((int) skin.getLabelLocation().getX(), (int) skin.getLabelLocation().getY()));
			playButton.updateSkin(skin.getPlay(), skin.getPlayPressed());
			playButton.setLocation(new Point((int) skin.getButtonLocation().getX(), (int) skin.getButtonLocation().getY()));
			slotMachineWindow.setVisible(false);
			slotMachineWindow.setResizable(true);
			slotMachineWindow.setSize(skin.getWidth(), skin.getHeight() + 40);
			slotMachineWindow.setResizable(false);
			slotMachineWindow.setVisible(true);
		}else if (e.getActionCommand().equals("update")) {
			// Get model
			SlotMachineModel model = (SlotMachineModel) e.getSource();
			symbols = model.getSymbols();
			
			// Print results to console
			String nums = new String();
			for(int i : model.getSymbols()) {
				nums += i;
			}
			System.out.println(nums);
			System.out.println(model.getTokens() + " // " + model.getWinnings());
			
			// Update winnings label
			if(model.getWinnings() > 0){
				tokens.setText("Winnings: " + model.getWinnings());
			}
			
			// Update tokens label
			if(model.isGameOver() && model.getTokens() == 0){
				tokens.setText("GAME OVER");
				symbols = new int[0]; // Don't draw symbols
			}else if(model.getTokens() > 0) {
				tokens.setText("Tokens: " + model.getTokens());
			}
			
			if(!showSymbols) {
				showSymbols = true;
				symbols = new int[0]; // Don't draw symbols
				return;
			}
			
			// Repaint
			this.repaint();
		}
	}
	
	// Define custom button
	public static class CustomButton extends JComponent implements MouseListener {
		private ArrayList<ActionListener> listeners;
		public boolean isPressed;
		private Image normal;
		private Image pressed;
		
		CustomButton(Image normal, Image pressed) {
			this.listeners = new ArrayList<ActionListener>();
			this.normal = normal;
			this.pressed = pressed;
			isPressed = false;
			this.setSize( new Dimension( normal.getWidth(null), normal.getHeight(null) ) );
		}
		
		public void updateSkin(Image normal, Image pressed) {
			this.normal = normal;
			this.pressed = pressed;
			isPressed = false;
			this.setSize( new Dimension( normal.getWidth(null), normal.getHeight(null) ) );
		}

		@Override
		public void paintComponent(Graphics g) {
			if(isPressed) {
				g.drawImage(pressed, 0, 0, null);
			}else{
				g.drawImage(normal, 0, 0, null);
			}
		}

		@Override
		public void mouseClicked(MouseEvent arg0) { }

		@Override
		public void mouseEntered(MouseEvent arg0) { }

		@Override
		public void mouseExited(MouseEvent arg0) { }

		@Override
		public void mousePressed(MouseEvent arg0) {
			isPressed = true;
			this.repaint();
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			isPressed = false;
			this.repaint();
			
			ActionEvent event = new ActionEvent(this, 0, "click");
		    for (ActionListener l : listeners) { l.actionPerformed(event); }
		}
		
		public void addActionListener(ActionListener a) {
			listeners.add(a);
		}
	}
	
	
}
