package sleepchild.aura;

public class AudioRecorder
{
    private static AudioRecorder deftInstance;
    
    private AudioRecorder(){
        //
    }
    
    public static AudioRecorder getDefault(){
        AudioRecorder inst = deftInstance;
        if(inst==null){
            synchronized(AudioRecorder.class){
                inst = AudioRecorder.deftInstance;
                if(inst==null){
                    inst = AudioRecorder.deftInstance = new AudioRecorder();
                }
            }
        }
        return inst;
    }
}
