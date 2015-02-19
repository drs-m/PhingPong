package de.mint400.hpi;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.phidgets.InterfaceKitPhidget;
import com.phidgets.PhidgetException;
import com.phidgets.event.SensorChangeEvent;
import com.phidgets.event.SensorChangeListener;

public class PhingPong extends Canvas {
	private static final long serialVersionUID = 1L;
	
	private JFrame frame;
	private JPanel panel;
	private JPanel ballPanel;
	private JLabel points;
	private final InterfaceKitPhidget IFK = new InterfaceKitPhidget();
	
	private final int frameHeight = 800;
	private final int frameWidth = 800;
	
	private int cursorX = 100;
	private int cursorY = 720;
	private final int cursorWidth = 120;
	private final int cursorHeight = 20;

	private int pointCounter = 0;
	private Random random = new Random();
	
	class Ball {
		public int y = 20;
		public final int size = 20;
		public int x = new Random().nextInt(780);
		
		public double velocityX = -2;
		public double velocityY = 2;
	}
	
	private final Ball ball = new Ball();
	
	public PhingPong() throws Exception {
		IFK.openAny();
		IFK.waitForAttachment();
		
		frame = new JFrame();
		frame.setSize(frameHeight, frameWidth);
		frame.setResizable(false);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); 
		int x = (d.width - frame.getSize().width) / 2; 
		int y = (d.height - frame.getSize().height) / 2; 
		frame.setLocation(x, y); 

		frame.setTitle("Phing-Pong");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(cursorX, cursorY, cursorWidth, cursorHeight);
		panel.setBackground(Color.BLACK);
		panel.setVisible(true);
		frame.add(panel);
		
		points = new JLabel();
		points.setBounds(680, 20, 100, 40);
		points.setText("Punkte: 0");
		points.setVisible(true);
		frame.add(points);
		
		ballPanel = new JPanel();
		ballPanel.setBounds(ball.x, ball.y, ball.size, ball.size);
		ballPanel.setBackground(new Color(0, 255, 0));
		ballPanel.setVisible(true);
		frame.add(ballPanel);
		
		frame.setVisible(true);
		
		boolean sensor = true;
		if (sensor){
			IFK.addSensorChangeListener(new SensorChangeListener() {
				@Override
				public void sensorChanged(SensorChangeEvent event) {
					try {
						onSensorChanged(event);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		
		boolean running = true;
		while (running) {
			// ball bewegen
			ball.y += ball.velocityY;
			ball.x += ball.velocityX;
			
			
			if (ball.x <= 0 || (ball.x + ball.size) >= frameWidth) {
				ball.velocityX = -ball.velocityX;
			}
			
			if (ball.y <= 0 || (ball.y+ball.size > cursorY && (ball.x + ball.size) > cursorX && ball.x < (cursorX + cursorWidth))) {
				/*
				System.out.print("ball.x: " + ball.x);
				System.out.print(" ball.x + ball.size: " + (ball.x + ball.size));
				System.out.print(" cursorX: " + cursorX);
				System.out.println(" cursorX + cursorWidth: " + (cursorX + cursorWidth));
				*/
				
				ball.velocityY = -ball.velocityY;
				// damit ball nicht stecken bleibt
				if (ball.velocityY < 0) {
					ball.y = cursorY - ball.size;
					if (random.nextInt(100) <= 75){ 
						ball.velocityY -= 1;
					}
					if (random.nextInt(100) <= 30){
						ball.velocityX = ball.velocityX < 0 ? ball.velocityX - 1 : ball.velocityX + 1;
					}
					points.setText("Punkte: " + ++pointCounter);
				}
			} else if (ball.y + ball.size >= frameHeight) {
				running = false;
				//lives--;
			}
			
			ballPanel.setLocation(ball.x, ball.y);
			Thread.sleep(8);
		}
	}
	
	private void onSensorChanged(SensorChangeEvent event) throws PhidgetException{
		int sensorValue = IFK.getSensorValue(0);
		double relativeX = sensorValue / 999.0;
		int newCursorX = (int) (relativeX * (frameWidth - cursorWidth));
		cursorX = newCursorX;
		/*if (newCursorX + cursorWidth > frameWidth){
			newCursorX = frameWidth - cursorWidth;
		}*/
		
		// System.out.println("Wert: " + IFK.getSensorValue(0) + " - Relativ: " + relativeX + " Neues X: " + newCursorX);
		panel.setLocation(newCursorX, cursorY);
	}

	public static void main(String[] args) throws Exception{
		new PhingPong();
	}
	
}
