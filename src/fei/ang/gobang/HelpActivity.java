/**
* Author: Yanfei Dai, Ang Li
* Description: This activity is used to show the help view
* Date: October 11, 2014
**/

package fei.ang.gobang;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class HelpActivity extends Activity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        
        Button backbtn = (Button) this.findViewById(R.id.button1);
        
        backbtn.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent backintent = new Intent(HelpActivity.this, MainActivity.class);
				HelpActivity.this.startActivity(backintent);
				finish();
			}});
    }
}