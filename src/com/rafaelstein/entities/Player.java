package com.rafaelstein.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.rafaelstein.graficos.Camera;
import com.rafaelstein.graficos.Spritesheet;
import com.rafaelstein.world.World;

public class Player extends Entity {
	
	private BufferedImage[] movingLeft;
	private BufferedImage[] movingRight;
	private BufferedImage[] movingUp;
	private BufferedImage[] movingDown;
	private Camera camera;
	
	public Player(int x, int y, int speed, int width, int height, Spritesheet spritesheet) {
		super(x, y, speed, width, height, spritesheet);
		
		this.camera = new Camera();
		
		this.animationQuantity = 3;
		this.animationMaxFrames = 15;

		this.movingDown = new BufferedImage[this.animationQuantity];
		this.movingLeft = new BufferedImage[this.animationQuantity];
		this.movingRight = new BufferedImage[this.animationQuantity];
		this.movingUp = new BufferedImage[this.animationQuantity];
		
		for(int i = 0; i < this.animationQuantity; i++) {
			this.movingDown[i] = spritesheet.getSprite(16 + (i * 16), 0, World.TILE_SIZE, World.TILE_SIZE);
			this.movingLeft[i] = spritesheet.getSprite(64 + (i * 16), 16, World.TILE_SIZE, World.TILE_SIZE);
			this.movingRight[i] = spritesheet.getSprite(64 + (i * 16), 0, World.TILE_SIZE, World.TILE_SIZE);
			this.movingUp[i] = spritesheet.getSprite(16 + (i * 16), 16, World.TILE_SIZE, World.TILE_SIZE);
		}
		
	}
	
	public void tick(int screenWidth, int screenHeight, World world) {
		
		this.isMoving = false;
		
		if(this.left && world.isFree(this.x - this.speed, this.y, World.TILE_SIZE, World.TILE_SIZE)) {
			this.x -= this.speed;
			this.isMoving = true;
		}
		else if(this.right && world.isFree(this.x + this.speed, this.y, World.TILE_SIZE, World.TILE_SIZE)) {
			this.x += this.speed;
			this.isMoving = true;
		}
		
		if(this.up && world.isFree(this.x, this.y - this.speed, World.TILE_SIZE, World.TILE_SIZE)) {
			this.y -= this.speed;
			this.isMoving = true;
		}
		else if(this.down && world.isFree(this.x, this.y + this.speed, World.TILE_SIZE, World.TILE_SIZE)) {
			this.y += this.speed;
			this.isMoving = true;
		}
		
		if(this.isMoving) {
			
			this.animationCurrentFrames++;
			
			if(this.animationCurrentFrames >= this.animationMaxFrames) {
				
				this.animationCurrentFrames = 0;
				this.animationIndex++;

				if(this.animationIndex >= this.movingDown.length ||
				   this.animationIndex >= this.movingLeft.length ||
				   this.animationIndex >= this.movingRight.length ||
				   this.animationIndex >= this.movingUp.length) {
					this.animationIndex = 0;
				}

			}
			
		}
		
		this.updateCamera(screenWidth, screenHeight, world.getWidth(), world.getHeight());

	}
	
	public void render(Graphics g) {
		int x = this.x - this.camera.x;
		int y = this.y - this.camera.y;
		
		if(this.right) {
			g.drawImage(this.movingRight[this.animationIndex], x, y, null);
		}
		else if(this.left) {
			g.drawImage(this.movingLeft[this.animationIndex], x, y, null);
		}
		else if(this.up) {
			g.drawImage(this.movingUp[this.animationIndex], x, y, null);
		}
		else if(this.down) {
			g.drawImage(this.movingDown[this.animationIndex], x, y, null);
		}
		else {
			g.drawImage(this.movingDown[0], x, y, null);
		}
		
	}
	
	public int getX() {
		return this.x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public Camera getCamera() {
		return this.camera;
	}
	
	public void updateCamera(int screenWidth, int screenHeight, int mapWidth, int mapHeight) {
		this.camera.x = Camera.clamp(this.x - (screenWidth / 2), 0, (mapWidth * World.TILE_SIZE) - screenWidth);
		this.camera.y = Camera.clamp(this.y - (screenHeight / 2), 0, (mapHeight * World.TILE_SIZE) - screenHeight);
	}

}