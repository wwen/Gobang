/**
* Author: Yanfei Dai, Ang Li
* Description: Define all the attributes for chess piece in ChessPiece class,
*              and chess piece can be instantiated when player touch screen
*              by this class
* Date: October 15, 2014
**/

package fei.ang.gobang;

public class ChessPiece {
	//the chess piece's x index in chess board
	private int x;
	
	//the chess piece's y index in chess board
	private int y;
	
	public int radius;
	
	//the color of chess piece
	private int color;
	
	//the constructor of ChessPiece
	public ChessPiece(int x, int y, int color, int radius) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.radius = radius;
	}
	
	//get the x index in chess board
	public int getX() {
		return x;
	}
	
	//get the y index in chess board
	public int getY() {
		return y;
	}
	
	//get the color of chess piece
	public int getColor() {
		return color;
	}
	
	//get the radius of chess piece
	public int getRadius() {
		return radius;
	}
}