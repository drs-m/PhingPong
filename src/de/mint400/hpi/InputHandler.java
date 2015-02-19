package de.mint400.hpi;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputHandler extends KeyAdapter {

	public Key pressed = Key.NONE;
	
	public enum Key {
		
		NONE("none"), LEFT("left"), RIGHT("right");
		
		public String debugName;
		
		private Key(String debugName){
			this.debugName = debugName;
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			pressed = Key.LEFT;
			break;
		case KeyEvent.VK_RIGHT:
			pressed = Key.RIGHT;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_RIGHT:
			pressed = Key.NONE;
			break;
		}
	}

}
