package sleepchild.aura;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Context;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Button;
import android.graphics.Color;
import android.content.*;
import android.os.*;
import android.graphics.drawable.*;

public class MainActivity extends Activity implements RecordStateListener{
	
	Context ctx;
	TextView ticker;
	Button toggle ;
    
	private RecordService recordService;
	
	ServiceConnection sConnect = new ServiceConnection(){
		@Override
		public void onServiceConnected(ComponentName p1, IBinder ibinder){
			RecordService.BinderClass binder = (RecordService.BinderClass) ibinder;
			recordService = binder.getService();
			recordService.registerListener(MainActivity.this);
            recordService.requestUpdate();
		}

		@Override
		public void onServiceDisconnected(ComponentName p1){
			//
		}
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		//PostMan.getInstance().register(this);
        setContentView(R.layout.layout61);
		//
		initVoid();
        
    }
	
	public void initVoid(){
		ctx = getApplicationContext();
		
		toggle = (Button) findViewById(R.id.layout61_btn_startstop);
		ticker = (TextView) findViewById(R.id.layout61_tv_ticker);
        //
	}
	
	private void serviceStart(){
		Intent recordIntent = new Intent(MainActivity.this, RecordService.class);
		recordIntent.setAction(Utils.ACTION_UPDATE); //todo: should we updaye here?
		//*
		bindService(
		    recordIntent,
			sConnect,
			ctx.BIND_AUTO_CREATE);
		//*/
		startService(recordIntent);
	}

    @Override
    public void onRecordStart()
    {
        //
        updateUI(true);
    }

    @Override
    public void onRecordStop()
    {
        //
        updateUI(false);
    }

    public void startStopRecording(View v){
        if(recordService!=null){
            recordService.startStopRecorder();
        }
    }
    
	public void showRecordings(View v){
        startActivity(new Intent(this, RecordingsListAct.class));
	}
    
    public void showSettings(View v){
        //
        Utils.toast(this, "nothing to change yet.");
    }
	
	public void updateUI(boolean isRecording){
		int col=0;
        GradientDrawable gd = new GradientDrawable();
		if(isRecording){
			toggle.setText("stop");
			col = Color.parseColor("#DF4137");
		}else{
			toggle.setText("start");
			col = Color.parseColor("#37DF8C");
		}
        gd.setStroke(5, col);
        gd.setCornerRadius(30);
		toggle.setTextColor(col);
        toggle.setBackground(gd);
	}

    @Override
    protected void onResume()
    {
        if(recordService!=null){
            recordService.registerListener(this);
        }
        serviceStart();
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        if(recordService!=null){
            recordService.removeListener();
        }
        super.onPause();
    }
	
	
}
