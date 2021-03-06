package dieuninh.com.learnvocabulary.learnvocabulary.activities;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import br.com.goncalves.pugnotification.interfaces.ImageLoader;
import br.com.goncalves.pugnotification.interfaces.OnImageLoadingCompleted;
import dieuninh.com.learnvocabulary.learnvocabulary.R;
import dieuninh.com.learnvocabulary.learnvocabulary.adapters.CardsAdapter;
import dieuninh.com.learnvocabulary.learnvocabulary.application.AppController;
import dieuninh.com.learnvocabulary.learnvocabulary.models.DatabaseHandler;
import dieuninh.com.learnvocabulary.learnvocabulary.models.Vocabulary;
import dieuninh.com.learnvocabulary.learnvocabulary.services.AlertReceiver;
import dieuninh.com.learnvocabulary.learnvocabulary.services.LockScreenService;
import dieuninh.com.learnvocabulary.learnvocabulary.services.VoService;

public class SettingsActivity extends AppCompatActivity implements ImageLoader {
    Switch swich1, swich2, swich3_sound;

    DatabaseHandler myDBHandler;
    private Context mContext;
    //    List<Vocabulary> list;
//    int numOfList = 0;
    //    static boolean checkNotify=false ;
    private Target viewTarget;
    PendingIntent pendingIntent;
    private static final String PREF_LOCK_NAME = "sharedPrefLock";
    private static final String PREF_NOTIFY_NAME = "sharedPrefNotify";
    private static final String PREF_SOUND_NAME = "sharedPrefSound";


    private static final String LOCK = "lock";
    private static final String NOTIFY = "notify";
    private static final String SOUND = "sound";

    SharedPreferences sharedPref_lock, sharedPref_notify, sharedPref_sound;
    SharedPreferences.Editor editor_lock, editor_notify, editor_sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_settings);

        sharedPref_lock = getApplicationContext().getSharedPreferences(PREF_LOCK_NAME, Context.MODE_PRIVATE);
        editor_lock = sharedPref_lock.edit();

        sharedPref_notify = getApplicationContext().getSharedPreferences(PREF_NOTIFY_NAME, Context.MODE_PRIVATE);
        editor_notify = sharedPref_notify.edit();

        sharedPref_sound = getApplicationContext().getSharedPreferences(PREF_SOUND_NAME, Context.MODE_PRIVATE);
        editor_sound = sharedPref_sound.edit();


        myDBHandler = new DatabaseHandler(SettingsActivity.this);

//        list = AppController.getInstance().getListVocabularies();
        swich1 = (Switch) findViewById(R.id.sw1);

        swich1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent intent = new Intent(SettingsActivity.this, VoService.class);
                if (isChecked) {
                    if (myDBHandler.checkForTables())//ko rỗng
                    {
                        editor_lock.putBoolean(LOCK, true).commit();
                        startService(intent);
                    } else {
                        Toast.makeText(SettingsActivity.this, R.string.empty_vo, Toast.LENGTH_SHORT).show();
                        editor_lock.putBoolean(LOCK, false).commit();
                        stopService(intent);
                    }

                } else {
                    editor_lock.putBoolean(LOCK, false).commit();
                    stopService(intent);
                }
            }
        });

//        swich1.setChecked(isMyServiceRunning(VoService.class));
//        numOfList = list.size();
        swich1.setChecked(getCheckedOfLock());
        swich2 = (Switch) findViewById(R.id.sw2);

        swich2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if (compoundButton.isChecked()) {
                    if (myDBHandler.checkForTables()) {
                        Intent alertIntent = new Intent(SettingsActivity.this, AlertReceiver.class);
                        pendingIntent = PendingIntent.getBroadcast(SettingsActivity.this, 0, alertIntent, 0);
//                        Long alertTime = new GregorianCalendar().getTimeInMillis() + 5 * 1000;//5 second
                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        alarmManager.cancel(pendingIntent);
//                      alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),alertTime,pendingIntent);
                        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 2 * 60 * 1000, pendingIntent);//-> next từ mới
//                        checkNotify = true;
                        editor_notify.putBoolean(NOTIFY, true).commit();

                    } else {
                        Toast.makeText(SettingsActivity.this, R.string.empty_vo, Toast.LENGTH_SHORT).show();
//                        checkNotify = false;
                        editor_notify.putBoolean(NOTIFY, false).commit();
                    }
                } else {
                    Intent alertIntent = new Intent(SettingsActivity.this, AlertReceiver.class);
                    pendingIntent = PendingIntent.getBroadcast(SettingsActivity.this, 0, alertIntent, 0);
//                    checkNotify = false;
                    editor_notify.putBoolean(NOTIFY, false).commit();
                    AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    manager.cancel(pendingIntent);
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.cancelAll();
                }
            }
        });
//        Log.e("CHECK=", checkNotify + "");
        swich2.setChecked(getCheckedOfNotify());
//        Toast.makeText(getApplicationContext(),getCheckedOfNotify()+"",Toast.LENGTH_SHORT).show();

        swich3_sound = (Switch) findViewById(R.id.sw3);
        swich3_sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    editor_sound.putBoolean(SOUND, true).commit();
                  /*  Intent i1=new Intent(getApplicationContext(),TestActivity.class);
                    i1.putExtra("sound1","1");
                    Toast.makeText(getApplicationContext(),"gui sound 1 intent" , Toast.LENGTH_SHORT).show();

                    Intent i2=new Intent(getApplicationContext(),LockScreenService.class);
                    i1.putExtra("sound2","1");

                    Intent i3=new Intent(getApplicationContext(),CardsAdapter.class);
                    i1.putExtra("sound3","1");
*/


                } else {
                    editor_sound.putBoolean(SOUND, false).commit();
                }
            }
        });
        swich3_sound.setChecked(getCheckedOfSOund());

    }

    private Boolean getCheckedOfLock() {
        return sharedPref_lock.getBoolean(LOCK, false);
    }

    private Boolean getCheckedOfNotify() {
        return sharedPref_notify.getBoolean(NOTIFY, false);
    }


    private Boolean getCheckedOfSOund() {
        return sharedPref_sound.getBoolean(SOUND, false);
    }
    /*
    public void createNotification1() {
        Random random = new Random();
        int num = random.nextInt(numOfList);
        String tuMoi = getTu(num, 1);
        String nghia = getTu(num, 2);

        Intent intents = new Intent(SettingsActivity.this, VocaListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(SettingsActivity.this, 1, intents, 0);

        Load mLoad = PugNotification.with(mContext).load()
                .smallIcon(R.drawable.ic_launcher)
                .autoCancel(true)
                .largeIcon(R.drawable.logo_lv)
                .title(tuMoi)
                .message(nghia)
                .flags(Notification.DEFAULT_ALL)
                .autoCancel(true)
                .onlyAlertOnce(false)
                .ongoing(true)
                .click(pendingIntent);
        mLoad.progress().build();

//  mLoad.simple().build();

    }*/


    private static Target getViewTarget(final OnImageLoadingCompleted onCompleted) {
        return new Target() {

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                onCompleted.imageLoadingCompleted(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
//                Toast.makeText(getApplicationContext(),"Lockscreen true",Toast.LENGTH_SHORT).show();
                return true;
            }

        }
//        Toast.makeText(getApplicationContext(),"Lockscreen false",Toast.LENGTH_SHORT).show();

        return false;

    }

    @Override
    public void load(String uri, OnImageLoadingCompleted onCompleted) {
        viewTarget = getViewTarget(onCompleted);
        Picasso.with(this).load(uri).into(viewTarget);
    }

    @Override
    public void load(int imageResId, OnImageLoadingCompleted onCompleted) {
        viewTarget = getViewTarget(onCompleted);
        Picasso.with(this).load(imageResId).into(viewTarget);
    }
}
