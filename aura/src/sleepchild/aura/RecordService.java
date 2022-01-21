package sleepchild.aura;

import android.app.Service;
import android.os.IBinder;
import android.content.Intent;
import android.os.Binder;
import android.content.Context;
import android.media.MediaRecorder;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

public class RecordService extends Service
{
	final IBinder binder= new BinderClass();
	Utils util;
	Context ctx;
	MediaRecorder recorder;
	private String savePath;
	boolean isRecording=false;

	@Override
	public IBinder onBind(Intent p1){
		return binder;
	}

	@Override
	public void onCreate() {
		// TODO: Implement this method
		super.onCreate();
		ctx = getApplicationContext();
		util = new Utils(ctx);
		resetRecorder();
        
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		//
		switch(intent.getAction()){
			case Utils.ACTION_START:
				//startRecording();
				break;
			case Utils.ACTION_STOP:
				//stopRecording();
				break;
			case Utils.ACTION_UPDATE:
                // prevent it running 2wice the first time the service is created 
				if(mListener!=null){
                    requestUpdate();
                }
                //toast("update");
				break;
			default:
			    //no action specified; do nothing
			    break;
		}
		return START_NOT_STICKY;
	}
	
	private void resetRecorder(){
		if(recorder==null){
			recorder = new MediaRecorder();
			recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		}
		try{
			recorder.stop();
			recorder.reset();
		}catch(Exception e){}
		//
		newSavePath();
		recorder.setOutputFile(savePath);
	}
    
    public void startStopRecorder(){
        if(isRecording){
            stopRecording();
        }else{
            startRecording();
        }
    }
	
	public void startRecording(){
		if(!isRecording){
			resetRecorder();
			//
			try
			{
				recorder.prepare();
				recorder.start();
                
                isRecording=true;
                startForeground(6263, Notif.get(this, savePath));
                if(mListener!=null){
                    mListener.onRecordStart();
                }
                //toast("started");
			}
			catch (IllegalStateException e)
			{
                toast("IllegalStateException: "+e.getLocalizedMessage());
            }
			catch (IOException e)
			{
                toast("IOException: "+e.getMessage());
            }
		}
	}
	
	public void stopRecording(){
		if(isRecording){
			isRecording=false;
        }
        
		if(recorder!=null){
			try{
                recorder.stop();
                recorder.reset();
                recorder.release();
                recorder=null;
            }catch(Exception e){
                Utils.toast(this, "Exception: "+e.getMessage());
            }
		}
        
        stopForeground(false);
        Notif.showStopped(this, savePath);
		if(mListener!=null){
            mListener.onRecordStop();
        }
        //toast("stopped");
	}
    
    public void requestUpdate(){
        if(isRecording){
            if(mListener!=null){
                mListener.onRecordStart();
            }
        }else{
            if(mListener!=null){
                mListener.onRecordStop();
            }
        }
    }
    
    void toast(String msg){
        Utils.toast(this, msg);
    }
	
	private void newSavePath(){
		savePath = util.getSavePath();
	}
    
    RecordStateListener mListener;
    public void registerListener(RecordStateListener listener){
        mListener = listener;
    }
    
    public void removeListener(){
        mListener = null;
    }

	@Override
	public void onDestroy(){
		super.onDestroy();
	}
	
	public class BinderClass extends Binder{
		public RecordService getService(){
			return RecordService.this;
		}
	}

}
