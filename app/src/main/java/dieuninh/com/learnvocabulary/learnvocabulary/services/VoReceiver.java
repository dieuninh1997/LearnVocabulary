package dieuninh.com.learnvocabulary.learnvocabulary.services;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;


public class VoReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        if(action.equals(Intent.ACTION_SCREEN_OFF)||action.equals(Intent.ACTION_BOOT_COMPLETED))
        {

            Log.i("[BroadcastReceiver]", "Screen OFF");
             Intent displayIntent=new Intent(context,LockScreenService.class);
                context.startService(displayIntent);


        }
    }
}
