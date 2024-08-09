package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManeger;

public class GamePanel extends JPanel implements Runnable {

	// SCREEN settings
	final int originalTileSize = 16; // 16x16 tile

	final int scale = 3;
	// 16x16pxだと画面に対して小さなピクセルになる為3倍の48x48にスケールさせる
	public final int tileSize = originalTileSize * scale;

	// 画面サイズの設定
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 768pixels
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

	final int FPS = 60;

	// ナノ秒の単位で1秒を表している
	final int nanoSecond = 1000000000; // 1 second

	TileManeger tileM = new TileManeger(this);
	KeyHandler keyH = new KeyHandler();

	// ゲームに時間の概念を作り出す為にThreadとwhileループを使用する
	Thread gameThread;

	Player player = new Player(this, keyH);

	public GamePanel() {

		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void startGameTherea() {

		// thisでGamePanelクラスを渡している
		gameThread = new Thread(this);

		// runメソッドが呼ばれる
		gameThread.start();

	}

	@Override
	public void run() {

		// 0.01666秒ごとに描画している
		double drawInterval = nanoSecond / FPS; // 0.01666 seconds
		// deltaは差分という意味
		double delta = 0;
		// 経過した時間
		double deltaTime;

		long lastTime = System.nanoTime();
		long currentTime;

		while (gameThread != null) {

			currentTime = System.nanoTime();

			// 経過した時間を算出
			deltaTime = currentTime - lastTime;

			delta = delta + deltaTime / drawInterval;

			lastTime = currentTime;

			if (delta >= 1) {
				update();

				// paintComponentを呼び出す
				repaint();

				// 値をリセット
				delta = 0;
			}

		}
	}

	public void update() {
		player.update();
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		tileM.draw(g2);

		player.draw(g2);

		g2.dispose();
	}
}
