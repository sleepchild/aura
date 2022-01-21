package sleepchild.aura;
import android.widget.*;
import android.view.*;
import android.content.*;
import java.util.*;
import android.graphics.drawable.*;


public class RecordingsAdaptor extends BaseAdapter
{
    Context ctx;
    LayoutInflater inf;
    List<RecordingItem> rlist = new ArrayList<>();
    
    public RecordingsAdaptor(Context ctx){
        this.ctx = ctx;
        inf = LayoutInflater.from(ctx);
    }
    
    public void update(List<RecordingItem> list){
        rlist= list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return rlist.size();
    }

    @Override
    public Object getItem(int pos)
    {
        return rlist.get(pos);
    }

    @Override
    public long getItemId(int p1)
    {
        return p1;
    }

    @Override
    public View getView(int pos, View v, ViewGroup p3)
    {
        RecordingItem ri = rlist.get(pos);
        
        v = inf.inflate(R.layout.rec_item, null, false);
        //
        TextView title = (TextView)v.findViewById(R.id.rec_item_title);
        title.setText(ri.name);
        
        View bg = v.findViewById(R.id.rec_item_bg);
        style(bg);
        
        return v;
    }
    
    private void style(View v){
        GradientDrawable g = new GradientDrawable();
        g.setCornerRadius(50);
        //g.setStroke(5, ctx.getResources().getColor(R.color.primary));
        g.setColor(ctx.getResources().getColor(R.color.primary));
        v.setBackground(g);
    }
    
}
