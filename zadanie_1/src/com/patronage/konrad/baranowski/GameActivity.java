package com.patronage.konrad.baranowski;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;
import android.view.View;
import android.widget.Button;


public class GameActivity extends Activity {
	
	private Button[] button;
	public MyFinishingTimerTask finish; 
	private int base = 10;
	//zielony
	private int curr = 5;
	private int range = 5;
	//niebieski
	private int blue = 5;
	private int bluerange = 5;
	//czerwony
	private int red = 5;
	private int redrange = 5;
	
	private long count = 0; 
	
	private static Activity thisActivity = null;
	
	private class MyTimerTask extends TimerTask{
		
		@Override
		public void run() {
			runOnUiThread(new Runnable(){
				
				@Override
				public void run() {
					boolean shouldContinue = true;
					do{
						curr = new Random().nextInt(button.length);
						ColorDrawable buttonColor = (ColorDrawable)button[curr].getBackground();
						int colorID = buttonColor.getColor();
						if(colorID == Color.WHITE){
							button[curr].setBackgroundColor(Color.GREEN);
							shouldContinue = false;
						}
					
					}while(shouldContinue);
				}
			});
		}
	}
	
	public class MyRandomTimerTask extends TimerTask{
		
		@Override
		public void run() {
			runOnUiThread(new Runnable(){
				
				@Override
				public void run() {

					boolean shouldContinue = true;
					do{
						blue = new Random().nextInt(button.length);
						ColorDrawable buttonColor = (ColorDrawable)button[blue].getBackground();
						int colorID = buttonColor.getColor();
						if(colorID == Color.WHITE){
							button[blue].setBackgroundColor(Color.BLUE);
							shouldContinue = false;
						}
					
					}while(shouldContinue);				
				}
			});
		}
	}
	public class MyBombTimerTask extends TimerTask{
		
		@Override
		public void run() {
			runOnUiThread(new Runnable(){
				
				@Override
				public void run() {

					boolean shouldContinue = true;
					do{
						red = new Random().nextInt(button.length);
						ColorDrawable buttonColor = (ColorDrawable)button[red].getBackground();
						int colorID = buttonColor.getColor();
						if(colorID == Color.WHITE){
							button[red].setBackgroundColor(Color.RED);
							shouldContinue = false;
						}
					
					}while(shouldContinue);				
				}
			});
		}
	}
	
	public class MyFinishingTimerTask extends TimerTask{
		@Override
		public void run() {
			runOnUiThread(new Runnable(){

				@Override
				public void run() {
					
					if(thisActivity!=null){
						UserData.result = count;
						startActivity(new Intent(thisActivity, MainActivity.class) );
					}
					finish();
				}
			});
		}
	}
	
	private class MyOnClickListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			ColorDrawable buttonColor = (ColorDrawable)v.getBackground();
			int colorID = buttonColor.getColor();
			if(colorID == Color.GREEN){
				flushButtons();
				count += 1;
				new Timer().schedule(new MyTimerTask(), (base + new Random().nextInt(range) ));
				new Timer().schedule(new MyRandomTimerTask(), base + new Random().nextInt(bluerange));
				new Timer().schedule(new MyBombTimerTask(), base + new Random().nextInt(redrange));
			}else if(colorID == Color.RED){
				UserData.isLose = true;
				finish.cancel();
				startActivity(new Intent(thisActivity, MainActivity.class) );
				thisActivity=null;
				finish();
			}
		}
	}
	
	public void flushButtons(){
		for(int i = 0; i < button.length; ++i){
			button[i].setOnClickListener(new MyOnClickListener());
			button[i].setBackgroundColor(Color.WHITE);
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		thisActivity = this;
		button = new Button[]{
				(Button)findViewById(R.id.test_button_test0),
				(Button)findViewById(R.id.test_button_test1),
				(Button)findViewById(R.id.test_button_test2),
				(Button)findViewById(R.id.test_button_test3),
				(Button)findViewById(R.id.test_button_test4),
				(Button)findViewById(R.id.test_button_test5),
				(Button)findViewById(R.id.test_button_test6),
				(Button)findViewById(R.id.test_button_test7),
				(Button)findViewById(R.id.test_button_test8)
				};
		
		flushButtons();
		finish = new MyFinishingTimerTask ();
		new Timer().schedule(new MyTimerTask(), base + new Random().nextInt(range) );
		new Timer().schedule(new MyRandomTimerTask(), base + new Random().nextInt(bluerange));
		new Timer().schedule(new MyBombTimerTask(), base + new Random().nextInt(redrange));
		new Timer().schedule(finish, Long.valueOf(UserData.time) * 1000);
	}
	
	protected void onDestroy() {
		thisActivity = null;
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

}
