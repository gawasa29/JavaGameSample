package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

	// SCREEN settings
	final int originalTileSize = 16; // 16x16 tile
	final int scale = 3;

	final int tileSize = originalTileSize * scale;

	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	final int screenWidth = tileSize * maxScreenCol; // 768pixels
	final int screenHeight = tileSize * maxScreenRow; // 576 pixels

	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;

	int FPS = 60;

	KeyHandler keyH = new KeyHandler();

	Thread gameThread;

	public GamePanel() {

		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void startGameTherea() {

		gameThread = new Thread(this);
		gameThread.start();

	}

	@Override
	public void run() {

		double drawInterval = 1000000000 / FPS; // 0.01666 seconds
		double delta = 0;

		long lastTime = System.nanoTime();
		long currentTime = System.nanoTime();

		long currentTimee;
		long timer = 0;
		int drawCount = 0;

		while (gameThread != null) {

			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if (delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}

			if (timer >= 1000000000) {
				System.out.println("FPS:" + drawCount);
				timer = 0;
			}

		}

	}

	public void update() {
		if (keyH.upPressed == true) {
			playerY = playerY - playerSpeed;
		} else if (keyH.downPressed == true) {
			playerY = playerY + playerSpeed;
		} else if (keyH.leftPressed) {
			playerX = playerX - playerSpeed;
		} else if (keyH.rightPressed) {
			playerX = playerX + playerSpeed;
		}

	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(Color.white);

		g2.fillRect(playerX, playerY, tileSize, tileSize);

		g2.dispose();
	}
}
