package Assignment_4;

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
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SlotMachineView extends JPanel implements ActionListener {
	public SlotSkin skin;
	public CustomButton playButton;
	private JLabel tokens;
	private int[] symbols;
	
	public SlotMachineView() {
		System.out.println("View not given model. Initialization failed.");
	}
	
	public SlotMachineView(SlotMachineModel model) {
		// Get skin
		skin = new SlotSkin();
		symbols = new int[0];
		
		// Set layout of this JPanel
		this.setLayout(null);
		
		// Set number of tokens
		tokens = new JLabel("Tokens: " + model.getTokens());
		tokens.setLocation(new Point(465, 110));
		tokens.setSize(new Dimension(70, 14));
		tokens.setForeground(Color.white);
		tokens.setBackground(Color.black);
		tokens.setOpaque(true);
		this.add(tokens);
		
		// Initialize play button
		playButton = new CustomButton(skin.getPlay(), skin.getPlayPressed());
		playButton.setLocation(new Point(410, 40));
		playButton.addMouseListener(playButton);
		this.add(playButton);
		
		// Send init event to model
		ActionEvent initEvent = new ActionEvent(this, 0, "init");
		model.actionPerformed(initEvent);
	}

	public void paintComponent(Graphics g) {		
		// Draw background
		g.drawImage(skin.getBackground(), 0, 0, 1000, 663, null);
		
		// Draw symbols
		for(int i = 0; i < symbols.length; i++){
			g.drawImage(skin.getSymbol(symbols[i]), (190 + i * 180), 225, skin.getSymbolWidth(), skin.getSymbolHeight(), null);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SlotMachineModel model = (SlotMachineModel) e.getSource();
		symbols = model.getSymbols();
		String nums = new String();
		for(int i : model.getSymbols()) {
			nums += i;
		}
		System.out.println(nums);
		System.out.println(model.getTokens() + " // " + model.getWinnings());
		tokens.setText("Tokens: " + model.getTokens());
		this.repaint();
	}
	
	// Define custom button
	public static class CustomButton extends JComponent implements MouseListener {
		private ArrayList<ActionListener> listeners;
		boolean isPressed;
		Image normal;
		Image pressed;
		
		CustomButton(Image normal, Image pressed) {
			this.listeners = new ArrayList<ActionListener>();
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
