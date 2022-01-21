package sleepchild.aura;
import android.app.*;
import android.content.*;

public class Notif
{
    Notif(){}
    
    public static Notification get(Context ctx, String content){
        //
        Notification.Builder b = new Notification.Builder(ctx);
        b.setSmallIcon(R.drawable.ic_launcher);
        b.setContentTitle("Recording...");
        b.setContentText(content);
        b.setUsesChronometer(true);
        b.setContentIntent(getPE(ctx));
        
        return b.build();
    }
    
    public static void showStopped(Context ctx, String content){
        //
        Notification.Builder b = new Notification.Builder(ctx);
        b.setSmallIcon(R.drawable.ic_launcher);
        b.setContentTitle("Saved...");
        b.setContentText(content);
        b.setContentIntent(getPE(ctx));
        
        NotificationManager mgr = (NotificationManager) ctx.getSystemService(ctx.NOTIFICATION_SERVICE);
        mgr.notify(6263, b.build());
        
    }
    
    private static PendingIntent getPE(Context ctx){
        Intent i = new Intent(ctx, MainActivity.class);
        return PendingIntent.getActivity(ctx, 1233, i, Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }
    
}
