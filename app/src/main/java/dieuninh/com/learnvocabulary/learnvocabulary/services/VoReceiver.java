package dieuninh.com.learnvocabulary.learnvocabulary.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by DieuLinh on 12/30/2016.
 */

public class VoReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        if(action.equals(Intent.ACTION_SCREEN_OFF)/*||action.equals(Intent.ACTION_BOOT_COMPLETED)*/)
        {
            Intent displayIntent=new Intent(context,LockScreenService.class);
            context.startService(displayIntent);
        }
    }
}
