package com.patronage.konrad.baranowski;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	private EditText 	timeEdit;
	private Button 		buttonPlay;
	private TextView 	labelResult;
	
	Context thisActivity = this;
	
	private boolean validateInput(){
		String data = timeEdit.getText().toString();
		if(data.isEmpty() == true){
			timeEdit.setBackgroundColor(Color.RED);
			labelResult.setText(R.string.main_empty_time);
			timeEdit.requestFocus();
			return false;
		}
		long time = Long.valueOf(data);
		if(time < 5 || time > 1000){
			timeEdit.setBackgroundColor(Color.RED);
			labelResult.setText(R.string.main_incorrect_time);
			timeEdit.requestFocus();
			return false;
		}
		
		return true;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		labelResult 	= ((TextView)this.findViewById(R.id.label_result));
        timeEdit		= ((EditText)this.findViewById(R.id.edit_time));
        buttonPlay		= ((Button)this.findViewById(R.id.button_play));
        if(UserData.isLose == true){
        	labelResult.setText(R.string.main_lose);
        	UserData.flush();
        }else if(UserData.result!=0){
        	labelResult.setText(getResources().getString(R.string.main_result) + " " + UserData.result + " " + getResources().getString(R.string.main_congratulations));
        	UserData.flush();
        }else{
        	labelResult.setText(R.string.main_play);
        }
        
        buttonPlay.setOnClickListener(new View.OnClickListener() 
        {
            @Override
            public void onClick(View v) 
            {
            	if(validateInput()){
            		String data = timeEdit.getText().toString();
            		UserData.time = Long.valueOf(data);
            		startActivity(new Intent(thisActivity, GameActivity.class));
            		finish();
            	}
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
