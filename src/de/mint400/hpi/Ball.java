package de.mint400.hpi;

import java.awt.Color;
import java.util.Random;

import javax.swing.JPanel;

public final class Ball extends JPanel {
	private static final long serialVersionUID = 5573572573927161931L;

	private final PhingPong instance;
	
	private static final int START_Y = 20;
	private static final int START_VELOCITY = 2;
	
	public final int size = 20;
	public int x;
	public int y = START_Y;

	public double velocityX;
	public double velocityY;
	
	public Ball(PhingPong instance) {
		this.instance = instance;
		resetPosition();
		resetVelocity();
		
		setBounds(x, y, size, size);
		setBackground(new Color(0, 255, 0));
		setVisible(true);
		this.instance.add(this);
	}

	public void resetPosition() {
		this.x = new Random().nextInt(instance.getWidth() - size);
		this.y = START_Y;
	}
	
	public void resetVelocity() {
		this.velocityX = new Random().nextBoolean() ? START_VELOCITY : -START_VELOCITY;
		this.velocityY = START_VELOCITY;
	}
	
	public void updateLocation() {
		setLocation(x, y);
	}

}
