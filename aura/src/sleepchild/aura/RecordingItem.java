package sleepchild.aura;
import java.io.*;

public class RecordingItem
{
    String name;
    public File fl;
    
    public RecordingItem(File file){
        this.fl = file;
        this.name = fl.getName();
    }
}
