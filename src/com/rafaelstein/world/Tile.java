package com.rafaelstein.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.rafaelstein.graficos.Camera;
import com.rafaelstein.graficos.Spritesheet;

public class Tile {

	protected BufferedImage sprite;
	protected int x;
	protected int y;
	
	public Tile(int x, int y, Spritesheet spritesheet) {
		this.x = x;
		this.y = y;
		this.sprite = null;
	}
	
	public void render(Graphics g, Camera camera) {
		int x = this.x - camera.x;
		int y = this.y - camera.y;
		
		g.drawImage(this.sprite, x, y, null);
		
	}

}
