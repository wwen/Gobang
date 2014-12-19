/**
* Author: Yanfei Dai, Ang Li
* Description: The main activity to show main view which the game starts with 
* Date: October 24, 2014
**/

package fei.ang.gobang;

import android.app.Activity;
import android.os.Bundle;
import android.widget.VideoView;
import android.media.MediaPlayer;
import android.net.Uri;
import android.content.Intent;

public class VideoActivity extends Activity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);
		
		VideoView videoview = (VideoView) this.findViewById(R.id.videoView1);
		Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.open_video);
		
		videoview.setVideoURI(uri);
		videoview.start();
		
		videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				Intent mainIntent = new Intent(VideoActivity.this, MainActivity.class);
				VideoActivity.this.startActivity(mainIntent);
				finish();
			}
		} );
	}
}