/**
* Author: Yanfei Dai, Ang Li
* Description: This class is used to control game
* Date: October 21, 2014
**/

package fei.ang.gobang;

import java.util.List;
import android.widget.TextView;
import android.view.ViewGroup;

public class GameControl {
	//restart game
	public static void restartGame(List<ChessPiece> list, boolean color, boolean over, TextView msg, int flag) {
		list.clear();
		color = true;
		over = false;
		ChessBoard.setPieceColor(color);
		ChessBoard.setGameOver(over);
		
		//if msg TextView is set in alert in ChessBoard class, remove msg TextViw. 
		//if not remove, it will raise a duplicate id error
		if (flag == 1) {
			((ViewGroup) msg.getParent()).removeView(msg);
			ChessBoard.setflag(0);
		} else if (flag ==0) {
			return;
		}
	}
	
	//undo piece
	public static void undoPiece(List<ChessPiece> list, boolean color) {
		if (list == null){
			return;
		} else {
			if (list.size()>0) {
				list.remove(list.size()-1);
				color = !color;
				ChessBoard.setPieceColor(color);
			}
		}
	}
	
	//main algorithm to judge if it matches the win conditions
	public static boolean Win(List<ChessPiece> list, int gridNum) {
		//set a count, if count >= 5, win
		int count = 1;
		
		//get current piece and attributes
		ChessPiece cpiece = list.get(list.size()-1);
		int cpieceX = cpiece.getX();
		int cpieceY = cpiece.getY();
		int cpieceColor = cpiece.getColor();
		
		//Horizontal searching
		//search the pieces to the left direction of current piece
		for (int pieceX = cpieceX-1; pieceX >= 0; pieceX-- ) {
			if (searchPiece(list, pieceX, cpieceY, cpieceColor) == true) {
				count = count+1;
			} else {
				break;
			}
		}
		
		//search the pieces to the right direction of current piece
		for (int pieceX = cpieceX+1; pieceX <= gridNum; pieceX++) {
			if (searchPiece(list, pieceX, cpieceY, cpieceColor) == true) {
				count = count+1;
			} else {
				break;
			}
		}
		
		if(count >= 5){
			return true;
		} else {
			//reset count
			count = 1;
		}
		
		//vertical searching
		//search the pieces to the up direction of current piece
		for (int pieceY = cpieceY-1; pieceY >= 0; pieceY--) {
			if (searchPiece(list, cpieceX, pieceY, cpieceColor) == true) {
				count = count+1;
			} else {
				break;
			}
		}
		
		//search the pieces to the down direction of current piece
		for (int pieceY = cpieceY+1; pieceY <= gridNum; pieceY++) {
			if (searchPiece(list, cpieceX, pieceY, cpieceColor) == true) {
				count = count+1;
			} else {
				break;
			}
		}
		
		if(count >= 5){
			return true;
		} else {
			count = 1;
		}
		
		//Right Diagonal searching
		//search the pieces to the top-left direction of current piece
		for (int pieceX = cpieceX-1, pieceY = cpieceY-1; pieceX >=0 && pieceY >= 0; pieceX--, pieceY--) {
			if (searchPiece(list, pieceX, pieceY, cpieceColor) == true) {
				count = count+1;
			} else {
				break;
			}
		}
		
		//search the pieces to the bottom-right direction of current piece
		for (int pieceX = cpieceX+1, pieceY = cpieceY+1; pieceX <=gridNum && pieceY <= gridNum; pieceX++, pieceY++) {
			if (searchPiece(list, pieceX, pieceY, cpieceColor) == true) {
				count = count+1;
			} else {
				break;
			}
		}
		
		if(count >= 5){
			return true;
		} else {
			count = 1;
		}
		
		//Left Diagonal searching
		//search the pieces to the top-right direction of current piece
		for (int pieceX = cpieceX+1, pieceY = cpieceY-1; pieceX <=gridNum && pieceY >= 0; pieceX++, pieceY--) {
			if (searchPiece(list, pieceX, pieceY, cpieceColor) == true) {
				count = count+1;
			} else {
				break;
			}
		}
		
		//search the pieces to the bottom-left direction of current piece
		for (int pieceX = cpieceX-1, pieceY = cpieceY+1; pieceX >= 0 && pieceY <= gridNum; pieceX--, pieceY++) {
			if (searchPiece(list, pieceX, pieceY, cpieceColor) == true) {
				count = count+1;
			} else {
				break;
			}
		}

		if(count >= 5){
			return true;
		} else {
			count = 1;
		}
		
		return false;
	}
	
	//search pieces in pieceList to find if there is piece satisfies the win condition
	public static boolean searchPiece(List<ChessPiece> list, int indexX, int indexY, int color) {
		for (ChessPiece p: list) {
			if (p.getX() == indexX && p.getY() == indexY && p.getColor() == color) {
				return true;
			}
		}
		return false;
	}
}