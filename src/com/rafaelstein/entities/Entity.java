package com.rafaelstein.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.rafaelstein.graficos.Spritesheet;

public class Entity {
	
	protected boolean isMoving;
	public boolean down;
	public boolean left;
	public boolean right;
	public boolean up;
	protected BufferedImage sprite;
	protected int x;
	protected int y;
	protected int speed;
	protected int width;
	protected int height;
	protected int animationCurrentFrames;
	protected int animationIndex;
	protected int animationMaxFrames;
	protected int animationQuantity; 
	
	public Entity(int x, int y, int speed, int width, int height, Spritesheet spritesheet) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.width = width;
		this.height = height;
		this.sprite = null;
		this.isMoving = false;
	}
	
	public void tick() {
	}
	
	public void render(Graphics g) {
		int x = this.x;
		int y = this.y;
		
		g.drawImage(this.sprite, x, y, null);

	}
	
}
