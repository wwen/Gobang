/**
* Author: Yanfei Dai, Ang Li
* Description: This start activity class is executed to show the chess board
* 			   view after clicking start button on main menu, and control game
* Date: October 18, 2014
**/

package fei.ang.gobang;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.graphics.Point;
import android.media.MediaPlayer;

public class StartActivity extends Activity {
	private MediaPlayer bkgrMusic;
	private ChessBoard chessboard;
	private int bgcolorIndex = 1;
	
	@SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //get the instance of device window
        Display display = getWindowManager().getDefaultDisplay();
        Point screensize = new Point();
        display.getSize(screensize);
         
        //pass the screensize to set the screen width and height
        GetScreen.setWidth(screensize.x);
        GetScreen.setHeight(screensize.y);
        
        //instantiate chess board view
        
        setContentView(R.layout.activity_start);
        
        //get the instance for chess board
        chessboard= (ChessBoard) this.findViewById(R.id.chessboardView);
        
        //create music stream
        bkgrMusic = MediaPlayer.create(StartActivity.this, R.raw.qiji);
        
        //set on click event listener to restart button
        Button restartBtn = (Button) this.findViewById(R.id.button1);
        
        restartBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GameControl.restartGame(chessboard.getPieceList(), 
										chessboard.getPieceColor(), 
										chessboard.getisOver(), 
										chessboard.getMsg(), 
										chessboard.getFlag());
				chessboard.invalidate();
			}
		});
        
        //set on click event listener to undo button
        Button undoBtn = (Button) this.findViewById(R.id.button2);
        
        undoBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GameControl.undoPiece(chessboard.getPieceList(), chessboard.getPieceColor());
				chessboard.invalidate();
			}
		});

        //set on click event listener to back button
        Button backBtn = (Button) this.findViewById(R.id.button3);
        
        backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//back to main activity
				stopMusic(bkgrMusic);
				Intent backintent = new Intent(StartActivity.this, MainActivity.class);
				StartActivity.this.startActivity(backintent);
				finish();
			}
		});
        
        //set on click event listener to playMusic button
        Button playBtn = (Button) this.findViewById(R.id.button4);
        
        playBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				playMusic(bkgrMusic);
			}
		});
        
        //set on click event listener to stopMusic button
        Button stopBtn = (Button) this.findViewById(R.id.button5);
        
        stopBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pauseMusic(bkgrMusic);
			}
		});
        
        //set on click event listener to background color button
        Button bkcolorBtn = (Button) this.findViewById(R.id.button6);
        
        bkcolorBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				backgroundColor(chessboard.getbgColor(), bgcolorIndex);
				bgcolorIndex++;
				
				if (bgcolorIndex == chessboard.getbgColor().length-1) {
					bgcolorIndex = 0;
				}
			}
		});
    }
	
	//play music
	public void playMusic(MediaPlayer music) {
		if (music.isPlaying() == true) {
			music.stop();
			music.setLooping(true);
			music.start();
		} else {
			if (music.isLooping() != false){
				music.setLooping(true);
				music.start();
			} else {
				music.start();
			}
		}
	}
	
	//pause music
	public void pauseMusic(MediaPlayer music) {
		if (music.isPlaying() == true) {
			music.pause();
		} else {
			return;
		}
	}
	
	//stop music
	public void stopMusic(MediaPlayer music) {
		if (music.isPlaying() == true) {
			music.stop();
		}
	}
	
	//set background color
	public void backgroundColor(int[] bgColor, int bgcolorIndex) {
		chessboard.setBackgroundColor(bgColor[bgcolorIndex]);
	}
}