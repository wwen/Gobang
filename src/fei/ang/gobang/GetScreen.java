/**
* Author: Yanfei Dai, Ang Li
* Description: This Class is created for getting the screen size of your device
* Date: October 13, 2014
**/

package fei.ang.gobang;

public class GetScreen {
	static int width;
	static int height;
	
	//get screen's width
	public static int getWidth() {
		return width;
	}
	
	//get screen's height
	public static int getHeight() {
		return height;
	}
	
	//set the width of screen
	public static void setWidth(int Width) {
		width = Width;
	}
	
	//set the height of screen
	public static void setHeight(int Height) {
		height = Height;
	}
}