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
import dieuninh.com.learnvocabulary.learnvocabulary.activities.MainActivity;
import dieuninh.com.learnvocabulary.learnvocabulary.models.DatabaseHandler;
import dieuninh.com.learnvocabulary.learnvocabulary.models.Vocabulary;



public class AlertReceiver extends BroadcastReceiver {
    int numOfList = 0;
    List<Vocabulary> list;
    DatabaseHandler db;
    @Override
    public void onReceive(Context context, Intent intent) {

        db = new DatabaseHandler(context);

/*
* Cho hàm ktra xem database có rỗng ko? nếu rỗng thì false notification + thông báo
* ngược lại hirenj thông báo như bình thường
*
* */

        if(db.checkForTables()) {//nếu có data
            list = db.getAllVocabulary();
            if(list==null || list.isEmpty()) return;
            numOfList = list.size();
            Random random = new Random();
            int num = random.nextInt(numOfList);
            String tuMoi = getTu(num, 1);
            String nghia = getTu(num, 2);


            Intent repeatingIntent = new Intent(context, MainActivity.class);
            repeatingIntent.putExtra("code", "1");

            repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


            repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent notifiIntent = PendingIntent.getActivity(
                    context, 0, repeatingIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Load mLoad = PugNotification.with(context).load()
                    .smallIcon(R.drawable.ic_launcher)
                    .autoCancel(false)
                    .largeIcon(R.drawable.logo_lv)
                    .title(tuMoi)
                    .message(nghia)
                    .flags(Notification.DEFAULT_ALL)
                    .onlyAlertOnce(true)
                    .ongoing(true)
                    .click(notifiIntent);
            //mLoad.flags(Notification.FLAG_AUTO_CANCEL);
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
        else
        {

            //Toast.makeText(context,context.getString(R.string.empty_vo),Toast.LENGTH_SHORT).show();

        }
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