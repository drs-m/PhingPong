package de.mint400.hpi;

import javax.swing.JLabel;

public class PointView extends JLabel {
	private static final long serialVersionUID = -5142518772852219128L;

	private final PhingPong instance;

	private final int x = 680;
	private final int y = 20;
	private final int width = 100;
	private final int height = 40;

	private int count = 0;

	public PointView(PhingPong instance) {
		this.instance = instance;

		setBounds(x, y, width, height);
		reset();
		setVisible(true);
		this.instance.add(this);
	}
	
	public void reset() {
		count = 0;
		updateLabel();
	}
	
	public void increment() {
		count++;
		updateLabel();
	}
	
	private void updateLabel() {
		setText("Punkte: " + count);
	}

}
