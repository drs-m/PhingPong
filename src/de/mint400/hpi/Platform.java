package de.mint400.hpi;

import java.awt.Color;

import javax.swing.JPanel;

public final class Platform extends JPanel {
	private static final long serialVersionUID = -8991417807350558231L;

	private final PhingPong instance;
	
	public int x = 100;
	public final int y = 720;
	
	public final int width = 120;
	public final int height = 20;
	
	public final int speed = 5;
	
	
	public Platform(PhingPong instance)  {
		this.instance = instance;
		
		setBounds(x, y, width, height);
		setBackground(Color.BLACK);
		setVisible(true);
		this.instance.add(this);
	}
	
	public void updateLocation(){
		setLocation(x, y);
	}
	
}