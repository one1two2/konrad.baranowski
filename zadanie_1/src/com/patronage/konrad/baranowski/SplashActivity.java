package com.patronage.konrad.baranowski;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class SplashActivity extends Activity {
	
	private static final long splashTime = 5000;
	private ActivityStarter starter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		starter = new ActivityStarter();
        starter.start();   
	}

	@Override
	public void onBackPressed() {	starter.shouldContinue = false;
        try {
			starter.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}
	
	private class ActivityStarter extends Thread {
		boolean shouldContinue = true;
		
        @Override
        public void run() {        		
        		try {
        			long waited = 0;
					while(shouldContinue){
						sleep(50);
						
						if( waited > splashTime ) {
	        				shouldContinue = false;
	        				Intent intent = new Intent(SplashActivity.this, MainActivity.class);
	                    	SplashActivity.this.startActivity(intent);
	                    	SplashActivity.this.finish();
	        			}else{
	        				waited+=50;
	        			}
					}
						
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					finish();
				}
        		
        	}
        }
    }
