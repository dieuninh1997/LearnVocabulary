package dieuninh.com.learnvocabulary.learnvocabulary.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.List;
import java.util.Random;

import br.com.goncalves.pugnotification.notification.Load;
import br.com.goncalves.pugnotification.notification.PugNotification;
import dieuninh.com.learnvocabulary.learnvocabulary.R;
import dieuninh.com.learnvocabulary.learnvocabulary.activities.VocaListActivity;
import dieuninh.com.learnvocabulary.learnvocabulary.application.AppController;
import dieuninh.com.learnvocabulary.learnvocabulary.models.Vocabulary;



public class AlertReceiver extends BroadcastReceiver {
    int numOfList = 0;
    List<Vocabulary> list;
    @Override
    public void onReceive(Context context, Intent intent) {

        list = AppController.getInstance().getListVocabularies();
        numOfList = list.size();

        Random random = new Random();
        int num = random.nextInt(numOfList);
        String tuMoi = getTu(num, 1);
        String nghia = getTu(num, 2);


        Intent repeatingIntent= new Intent(context,VocaListActivity.class);
        repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent notifiIntent=PendingIntent.getActivity(context,0,
             repeatingIntent  ,PendingIntent.FLAG_UPDATE_CURRENT);
        Load mLoad = PugNotification.with(context).load()
                .smallIcon(R.drawable.ic_launcher)
                .autoCancel(true)
                .largeIcon(R.drawable.logo_lv)
                .title(tuMoi)
                .message(nghia)
                .flags(Notification.DEFAULT_ALL)
                .onlyAlertOnce(true)
                .ongoing(true)
                .click(notifiIntent);
        mLoad.simple().build();
      /*  NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.logo_lv)
                .setContentTitle(tuMoi)
                .setTicker("AlertReceiver")
                .setContentText(nghia)
                ;
        mBuilder.setContentIntent(notifiIntent);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager manager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1,mBuilder.build());*/


    }

    private String getTu(int index, int column) {
        if (column == 1) {
            return list.get(index).getNewWord();
        } else {
            return list.get(index).getMeaning();
        }
       /* String query1="SELECT * FROM "+TABLE_NAME+ " WHERE ID="+index;
        c=myDBHandler.db.rawQuery(query1,null);
        c.moveToFirst();
        return c.getString(column);*/
    }

}