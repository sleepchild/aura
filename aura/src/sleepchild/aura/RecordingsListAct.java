package sleepchild.aura;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;

public class RecordingsListAct extends Activity
{

    ListView list1;
    RecordingsAdaptor adapter;
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        setContentView(R.layout.recordings);
        
        init();
	}
    
    void init(){
        list1 = (ListView) findViewById(R.id.recordings_list1);
        list1.setDivider(null);
        adapter = new RecordingsAdaptor(this);
        list1.setAdapter(adapter);
        
        getFiles();
        
    }
    
    //
    void getFiles(){
        // do in background.
        adapter.update( Utils.getRecordings());
    }
    
	
}
