package dieuninh.com.learnvocabulary.learnvocabulary.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class VoService extends Service {
    BroadcastReceiver receiver;

    public VoService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
       // throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter=new IntentFilter(Intent.ACTION_SCREEN_OFF);
       filter.addAction(Intent.ACTION_BOOT_COMPLETED);
        receiver=new VoReceiver();
        registerReceiver(receiver,filter);
    }
}
