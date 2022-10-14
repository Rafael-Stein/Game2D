package com.rafaelstein.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.rafaelstein.entities.Player;
import com.rafaelstein.graficos.Camera;
import com.rafaelstein.graficos.Spritesheet;

public class World {

	public static final int TILE_SIZE = 16;
	private int width;
	private int height;
	private Tile[] tiles;
		
	public World(String mapName, Spritesheet spritesheet, Player player) {

		try {
			BufferedImage map = ImageIO.read(getClass().getResource(mapName));
			
			this.width = map.getWidth();
			this.height = map.getHeight();
			
			int[] pixels = new int[this.width * this.height];

			tiles = new Tile[this.width * this.height];
			
			map.getRGB(0, 0, this.width, this.height,pixels, 0, map.getWidth());
			
			for(int x = 0; x < this.width; x++) {
				
				for(int y = 0; y < this.height; y++) {
					int currentPixel = pixels[x + (y * this.width)];
					Tile tile = new TileFloor(x * TILE_SIZE, y * TILE_SIZE, spritesheet);

					tiles[x + (y * width)] = tile;
					
					if(currentPixel == 0xFF0026ff) {
						player.setX(x * 16);
						player.setY(y * 16);
					}
					else if(currentPixel == 0xFFffffff) {
						tiles[x + (y * width)] = new TileWall(x * TILE_SIZE, y * TILE_SIZE, spritesheet);
					}
					
				}
				
			}
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public boolean isFree(int x, int y, int w, int h) {
		int x1 = x / TILE_SIZE;
		int x2 = (x + w - 1) / TILE_SIZE;
		int y1 = y / TILE_SIZE;
		int y2 = (y + h - 1) / TILE_SIZE;
		
		return !((this.tiles[x1 + (y1 * this.width)] instanceof TileWall) ||
				(this.tiles[x2 + (y1 * this.width)] instanceof TileWall) ||
				(this.tiles[x1 + (y2 * this.width)] instanceof TileWall) ||
				(this.tiles[x2 + (y2 * this.width)] instanceof TileWall));
	}
	
	public void render(Graphics g, int screenWidth, int screenHeight, Camera camera) {
		int xstart = camera.x >> 4;
		int xfinal = xstart + (screenWidth >> 4);
		int ystart = camera.y >> 4;
		int yfinal = ystart + (screenHeight >> 4);
		
		for(int x = xstart; x <= xfinal; x++) {
			
			for(int y = ystart; y <= yfinal; y++) {
				
				if(x < 0 || y < 0 || x >= this.width || y >= this.height) {
					continue;
				}
				
				Tile tile = tiles[x + (y * this.width)];
				tile.render(g, camera);
				
			}
			
		}
		
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int getWidth() {
		return this.width;
	}
	
}
