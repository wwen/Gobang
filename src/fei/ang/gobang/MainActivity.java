/**
* Author: Yanfei Dai, Ang Li
* Description: The main activity to show main view which the game starts with 
* Date: October 10, 2014
**/

package fei.ang.gobang;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button helpbtn = (Button) this.findViewById(R.id.button1);
        Button startbtn = (Button) this.findViewById(R.id.button2);
        
        helpbtn.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent helpintent = new Intent(MainActivity.this, HelpActivity.class);
				MainActivity.this.startActivity(helpintent);
				finish();
			}
		});
        
        startbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent startintent = new Intent(MainActivity.this, StartActivity.class);
				MainActivity.this.startActivity(startintent);
				finish();
			}
		});
    }
}