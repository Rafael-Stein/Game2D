package com.rafaelstein.graficos;

public class Camera {

	public int x;
	public int y;
	
	public Camera() {
		this.x = 0;
		this.y = 0;
	}
	
	public static int clamp(int actual, int min, int max) {
		
		if(actual < min) {
			actual = min;
		}
		
		if(actual > max) {
			actual = max;
		}
		
		return actual;
		
	}
	
}
