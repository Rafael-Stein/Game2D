package com.rafaelstein.world;

import com.rafaelstein.graficos.Spritesheet;

public class TileWall extends Tile {

	public TileWall(int x, int y, Spritesheet spritesheet) {
		super(x, y, spritesheet);
		
		this.sprite = spritesheet.getSprite(0, 16, World.TILE_SIZE, World.TILE_SIZE);
		
	}

}
