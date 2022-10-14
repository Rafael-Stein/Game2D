package com.rafaelstein.graficos;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Spritesheet {

	private BufferedImage imagesheet;
	
	public Spritesheet(String path)
	{
		
		try {
			this.imagesheet = ImageIO.read(getClass().getResource(path));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public BufferedImage getSprite(int x, int y, int width, int height) {
		return this.imagesheet.getSubimage(x, y, width, height);
	}
	
}
