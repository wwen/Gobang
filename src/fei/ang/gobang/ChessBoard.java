/**
* Author: Yanfei Dai, Ang Li
* Description: Create chess board view, draw chess board, and pieces
* Date: October 18, 2014
**/

package fei.ang.gobang;

import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.MotionEvent;
import android.widget.TextView;
import android.content.Context;
import android.graphics.*;
import android.app.AlertDialog;
import java.util.*;
import android.os.Vibrator;

public class ChessBoard extends View {
	private static final int MARGIN = 20;
	
	//define the position to start drawing chess board by startX, startY
	private static int startX = 0;
	private static int startY = 0;
	
	private static final int BOARD_WIDTH = GetScreen.getWidth()-MARGIN*2;
	private static final int GRID_NUM = 15;
	private static final int totalPieceNum = (GRID_NUM+1)*(GRID_NUM+1);
	private static final int GRID_SPAN = BOARD_WIDTH/GRID_NUM;
	private static final int GRID_MOD = BOARD_WIDTH%GRID_NUM;
	
	//paint to draw chess board
	private static Paint paintChessboard = new Paint();
	
	//paint to draw piece
	private static Paint paintPiece = new Paint();
	
	//game begins with white piece
	private static boolean isWhite = true;
	
	private static boolean isOver = false;
	
	//create an ArrayList to hold the pieces that are dropped;
	private static List<ChessPiece> pieceList = new ArrayList<ChessPiece>();

	private static float touchX;
	private static float touchY;
	private static AlertDialog.Builder builder;
	private static AlertDialog alert;
	private static TextView msg;
	
	//flag is used to check if dialog has msg TextView
	private static int flag = 0;
	
	private Vibrator vibrate;
	
	//color array
	private static int[] bkgrColor = {Color.rgb(153, 153, 153), Color.rgb(209, 135, 25), 
		                              Color.rgb(127, 137, 193), Color.rgb(48, 131, 255), 
		                              Color.rgb(131, 255, 131)};
	
	public ChessBoard(Context context, AttributeSet attrs) {
		super(context, attrs);

		//makes the paint draw more smooth
		paintPiece.setAntiAlias(true);
		paintChessboard.setAntiAlias(true);
		
		//set the color of paint to draw
		paintChessboard.setColor(Color.BLACK);
		
		//set the margin to the the screen boundary
		if (GRID_MOD == 0) {
			startX = MARGIN;
			startY = MARGIN;
		} else {
			startX = MARGIN+GRID_MOD/2;
			startY = MARGIN+GRID_MOD/2;
		}
		
		builder = new AlertDialog.Builder(context);
		msg = new TextView(context); 
		vibrate = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		
		this.setBackgroundColor(bkgrColor[0]);
	}
	
	public void onDraw(Canvas canvas) {
		
		//draw chess board
		for (int i = 0; i <= GRID_NUM; i++){
			canvas.drawLine(startX, startY+i*GRID_SPAN, startX+GRID_NUM*GRID_SPAN, 
					        startY+i*GRID_SPAN, paintChessboard);
			canvas.drawLine(startX+i*GRID_SPAN, startY, startX+i*GRID_SPAN, 
					        startY+GRID_NUM*GRID_SPAN, paintChessboard);
		}
		
		//using maskFiler to make filter effect on point
		float[] direction = {1,1,1};
		float light = (float)0.2;
		float specular = (float)10.0;
		float blurradius = (float)3.5;
		EmbossMaskFilter filter = new EmbossMaskFilter(direction, light, specular, blurradius);
		paintPiece.setMaskFilter(filter);
		
		//draw chess pieces
		for (ChessPiece piece: pieceList) {
			paintPiece.setColor(piece.getColor());
			
			//set the position for piece
			int pieceX = piece.getX()*GRID_SPAN + startX;
			int pieceY = piece.getY()*GRID_SPAN + startY;
			
			//draw the piece
			canvas.drawCircle(pieceX, pieceY, piece.getRadius(), paintPiece);
		}
	}
	
	@Override
	//add listener for touch screen event
	public boolean onTouchEvent(MotionEvent drawpiece) {
		//then the game is over, can't draw pieces
		if (isOver) {
			alert.show();
			return true;
		}
		
		String colorName = isWhite ? "White" : "Black";
		
		//get the touch position
		touchX = drawpiece.getX();
		touchY = drawpiece.getY();	
		
		//convert the touch position to the index in PieceList
		//we do this for sometimes the player does touch the across of grid,
		//so we need to judge which position to draw the piece on the grid
		int indexX = Math.round((touchX - startX)/GRID_SPAN);
		int indexY = Math.round((touchY - startY)/GRID_SPAN);
		
		//can't draw piece if the touch position is out of chess board
		if (touchX < startX || touchY < startY || touchX > startX+GRID_NUM*GRID_SPAN || touchY > startY+GRID_NUM*GRID_SPAN) {
			return true;
		} else {
			//if the touch position has already have a piece, do not draw
			if (FindPiece(indexX, indexY)) {
				return true;
			} else {
				int currentPieceColor = isWhite ? Color.WHITE : Color.BLACK; 
				ChessPiece piece = new ChessPiece(indexX, indexY, currentPieceColor, GRID_SPAN/2-1);
				
				pieceList.add(piece);
			}
		}

		//re-execute onDraw method
		invalidate();
		
		//change the color of next piece
		isWhite = !isWhite;
		
		//judge who wins
		boolean Win = GameControl.Win(pieceList, GRID_NUM);
			
		if (Win) {
			String message ="Congraduations, " + colorName + " Win";
			msg.setText(message);
			msg.setGravity(Gravity.CENTER);
			msg.setPadding(0, 10, 0, 0);
			msg.setTextSize(18);
			builder.setView(msg);
			alert = builder.create();
			flag = 1;
			alert.show();
			isOver = true;
		}
		
		//Check if this game is daw
		if (pieceList.size() == totalPieceNum) {
			msg.setText("Draw, have a nother game");
			msg.setGravity(Gravity.CENTER);
			msg.setPadding(0, 10, 0, 0);
			msg.setTextSize(18);
			builder.setView(msg);
			alert = builder.create();
			flag = 1;
			alert.show();
			isOver = true;
		}
		
		//vibrate device when drop the piece
		vibrate.vibrate(500);
		return super.onTouchEvent(drawpiece);
	}
	
	//find if the index of new piece is already occupied
	public boolean FindPiece(int x, int y) {
		for (ChessPiece p: pieceList) {
			if (p.getX() == x && p.getY() == y) {
				return true;
			}
		}
		return false;
	}
	
	//get pieceList
	public List<ChessPiece> getPieceList() {
		return pieceList;
	}
	
	//get isWhite
	public boolean getPieceColor() {
		return isWhite;
	}
	
	public boolean getisOver() {
		return isOver;
	}
	
	//set isWhite
	public static void setPieceColor(boolean color) {
		isWhite = color;
	}
	
	//set isOver
	public static void setGameOver(boolean over) {
		isOver = over;
	}
	
	public TextView getMsg() {
		return msg;
	}
	
	public static void setflag(int f) {
		flag = f;
	}
	
	public int getFlag() {
		return flag;
	}
	
	public int[] getbgColor() {
		return bkgrColor;
	}
}