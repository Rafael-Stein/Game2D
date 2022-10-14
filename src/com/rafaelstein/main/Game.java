package com.rafaelstein.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import com.rafaelstein.entities.Player;
import com.rafaelstein.graficos.Spritesheet;
import com.rafaelstein.world.World;

public class Game extends Canvas implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;
	private final int SCALE = 2; 
	private boolean isRunning;
	private BufferedImage image;
	private int height;
	private int width;
	private JFrame frame;
	private Player player;
	private Spritesheet spritesheet;
	private Thread thread;
	private World world;
	
	public Game() {
		Image cursor = null;
		Image icon = null;

		this.width = 320;
		this.height = 208;

		this.addKeyListener(this);
		this.setPreferredSize(new Dimension(this.width * SCALE, this.height * SCALE));
		
		this.frame = new JFrame("Game 2D");
		this.frame.add(this);
		this.frame.setResizable(false);
		this.frame.pack();
		
		try {
			Toolkit toolKit = Toolkit.getDefaultToolkit(); 
					
			icon = ImageIO.read(getClass().getResource("/icon.png"));
			cursor = toolKit.getImage(getClass().getResource("/cursor.png"));
			
			Cursor c = toolKit.createCustomCursor(cursor, new Point(0, 0), "cursor");
			
			this.frame.setCursor(c);
			this.frame.setIconImage(icon);
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		this.frame.setLocationRelativeTo(null);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setVisible(true);
		
		this.image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
		this.spritesheet = new Spritesheet("/spritesheet.png");
		this.player = new Player(0, 0, 2, 16, 16, spritesheet);
		this.world = new World("/map01.png", this.spritesheet, this.player);
		
	}
	
	public static void main(String args[]) {
		Game game = new Game();
		game.start();
	}
	
	public synchronized void start() {

		this.isRunning = true;
		
		this.thread = new Thread(this);
		this.thread.start();
		
	}
	
	public synchronized void stop() {
		
		this.isRunning = false;
		
		try {
			
			this.thread.join();
			
		}
		catch (InterruptedException e) {
			
			e.printStackTrace();
			
		}
	}
	
	public void run() {
		double amountOfTicks = 60.0;
		double nanoSecondsPerTick = 1000000000 / amountOfTicks;
		double timer = System.currentTimeMillis();
		double unprocessed = 0;
		long lastTime = System.nanoTime();
		
		this.requestFocus();
		
		while(this.isRunning) {
			boolean shouldRender = false;
			long now = System.nanoTime();
			
			unprocessed += (now - lastTime) / nanoSecondsPerTick;
			
			lastTime = now;
			
			while(unprocessed >= 1) {
			
				this.tick();
				
				unprocessed--;
				shouldRender = true;
				
			}
			
			if(shouldRender) {
				this.render();
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
			}
			
		}
		
		this.stop();
		
	}
	
	public void tick() {
		
		this.player.tick(this.width, this.height, this.world);
		
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();

		if(bs == null) {
			
			this.createBufferStrategy(3);
			this.requestFocus();
			
			return;
			
		}

		Graphics g = image.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, this.width,  this.height);
		
		this.world.render(g, this.width, this.height, this.player.getCamera());
		this.player.render(g);
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, this.width * SCALE, this.height * SCALE, null);
		bs.show();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
			this.player.left = true;
		}
		else if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
			this.player.right = true;
		}
		
		if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
			this.player.up = true;
		}
		else if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
			this.player.down = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
			this.player.left = false;
		}
		else if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
			this.player.right = false;
		}
		
		if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
			this.player.up = false;
		}
		else if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
			this.player.down = false;
		}

	}
	
}
