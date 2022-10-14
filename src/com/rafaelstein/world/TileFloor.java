package com.rafaelstein.world;

import com.rafaelstein.graficos.Spritesheet;

public class TileFloor extends Tile {

	public TileFloor(int x, int y, Spritesheet spritesheet) {
		super(x, y, spritesheet);
		
		this.sprite = spritesheet.getSprite(0, 0, World.TILE_SIZE, World.TILE_SIZE);
		
	}

}
