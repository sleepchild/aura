package sleepchild.aura;

import android.content.SharedPreferences;
import android.content.Context;
import java.io.File;
import android.os.Environment;
import java.util.Date;
import java.util.concurrent.*;
import java.util.*;
import android.preference.*;
import android.widget.*;

public class Utils
{
	Context ctx;
	SharedPreferences sPrefs;
	SharedPreferences.Editor prefsEdit;
	//
	private final String sp_UI_COLOR="sleepchild.aura.shared.prefs.ui.color";
	private final String sp_SAVE_DIRECTORY="sleepchild.aura.shared.prefs.save.dir.path";
	//
	private String SAVE_DIRECTORY="";
	private String defaultDir = "/.sleepchild/aura/rec/";
	//
	//
	public final static String ACTION_START="sleep.aura.action.record.start";
	public final static String ACTION_STOP="sleepchild.aura.action.record.stop";
	public final static String ACTION_UPDATE="sleep.child.aura.action.ui.update";
	
	public Utils(Context context){
		this.ctx=context;
		//
		sPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		prefsEdit = sPrefs.edit();
		//
		defaultDir = Environment.getExternalStorageDirectory().getAbsolutePath()+
			defaultDir;
		getSaveDir();
	}
	
	public String getSavePath(){
		String savePath = SAVE_DIRECTORY+new Date().toGMTString()
			.replace(" ","")
			.replace(":","_")+".amr";
			//.substring(0,16)+".amr";
            
            savePath = savePath.toLowerCase();
            
			return savePath;
	}
	
	private void getSaveDir(){
		SAVE_DIRECTORY = sPrefs.getString(sp_SAVE_DIRECTORY,defaultDir);
		mkdirs(SAVE_DIRECTORY);
	}
	
	public void setNewSavePath(String path){
		if(!getFile(path).exists()){
			mkdirs(path);
		}
		prefsEdit.putString(sp_SAVE_DIRECTORY,path)
		  . apply();
	}
	
	public static List<RecordingItem> getRecordings(){
		List<RecordingItem> rlist = new ArrayList<>();
        File dir = new File("/sdcard/.sleepchild/aura/rec/");
        if(dir.exists()){
            for(File fl : dir.listFiles() ){
                rlist.add(new RecordingItem(fl));
            }
        }
        return rlist;
	}
	
	private File getFile(String path){
		return new File(path);
	}
	
	public boolean fileExists(String path){
		if(getFile(path).exists()){
			return true;
		}//else
		return false;
	}
	
	private void mkdirs(String path){
		File f = getFile(path);
		if(!f.exists()){
			f.mkdirs();
		}
	}
	
	public String formatDuration(final int duration) {
		return String.format(Locale.getDefault(), "%02d:%02d:%02d",
		    TimeUnit.MILLISECONDS.toHours(duration),
			TimeUnit.MILLISECONDS.toMinutes(duration),
			TimeUnit.MILLISECONDS.toSeconds(duration) -
			TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
	}
	
	public static void toast(Context ctx, String text){
		Toast.makeText(ctx,text,500).show();
	}
	
}
