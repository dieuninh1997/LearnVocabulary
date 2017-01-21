package dieuninh.com.learnvocabulary.learnvocabulary.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.rubensousa.raiflatbutton.RaiflatButton;

import java.util.Random;

import dieuninh.com.learnvocabulary.learnvocabulary.R;
import dieuninh.com.learnvocabulary.learnvocabulary.application.AppController;
import dieuninh.com.learnvocabulary.learnvocabulary.models.DatabaseHandler;

public class LockScreenService extends Service {
    WindowManager windowManager;
    TextView tu_moi;
    RaiflatButton one,two,three,four;
    int dapAnDung;
    int tuIndex;
    int[] nghiaIndex;
    String[] tu;
    String[] nghia;

    //
    public final int SIZE_OF_VOCA = 130;
    public int SIZE;
    DatabaseHandler myDb;
    Cursor c=null;
    private static final String TABLE_NAME = "VocabularyTable";


    public LockScreenService() {
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

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        //WindowManager.LayoutParams.TYPE_TOAST
        params.x = 0;
        params.y = 0;
        params.gravity = Gravity.CENTER | Gravity.CENTER;
        LayoutInflater li = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final RelativeLayout theme = (RelativeLayout) li.inflate(R.layout.vo_lockscreen, null);
        windowManager.addView(theme, params);


        Random random = new Random();
        int index = random.nextInt(4);
        switch (index) {
            case 0:
                theme.setBackgroundResource(R.drawable.bg2);
                break;
            case 1:
                theme.setBackgroundResource(R.drawable.bg2);
                break;

            case 2:
                theme.setBackgroundResource(R.drawable.bg2);
                break;
            case 3:
                theme.setBackgroundResource(R.drawable.bg2);
                break;
        }

        //
        myDb = new DatabaseHandler(LockScreenService.this);
        SIZE = getCount();
        //
        tu = getResources().getStringArray(R.array.tu);
        nghia = getResources().getStringArray(R.array.nghia);
        //
        tu_moi = (TextView) theme.findViewById(R.id.txt_tu_moi);
        one = (RaiflatButton) theme.findViewById(R.id.one);
        two = (RaiflatButton) theme.findViewById(R.id.two);
        three = (RaiflatButton) theme.findViewById(R.id.three);
        four = (RaiflatButton) theme.findViewById(R.id.four);


        /*one.setBackgroundResource(R.drawable.button);
        two.setBackgroundResource(R.drawable.button);
        three.setBackgroundResource(R.drawable.button);
        four.setBackgroundResource(R.drawable.button);*/

        one.setFlatEnabled(false);
        two.setFlatEnabled(false);
        three.setFlatEnabled(false);
        four.setFlatEnabled(false);

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                one.setBackgroundResource(R.drawable.button_pressed);

                if (nghiaIndex[0] == tuIndex) {
                    windowManager.removeView(theme);
                    stopSelf();
                } else {
                    Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vib.vibrate(300);
                }
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                two.setBackgroundResource(R.drawable.button_pressed);
                if (nghiaIndex[1] == tuIndex) {
                    windowManager.removeView(theme);
                    stopSelf();
                } else {
                    Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vib.vibrate(300);
                }
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                three.setBackgroundResource(R.drawable.button_pressed);
                if (nghiaIndex[2] == tuIndex) {
                    windowManager.removeView(theme);
                    stopSelf();
                } else {
                    Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vib.vibrate(300);
                }
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                four.setBackgroundResource(R.drawable.button_pressed);
                if (nghiaIndex[3] == tuIndex) {
                    windowManager.removeView(theme);
                    stopSelf();
                } else {
                    Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vib.vibrate(300);
                }
            }
        });

        //nếu databse có dữ liệu thì lấy ra dùng thôi. còn nếu ko có thì ta sẽ dùng 1 số từ mới mặc định nha

        //tạo ngẫu nhiên 1 từ mới

        if (SIZE > 0) {
            random = new Random();
            tuIndex = random.nextInt(SIZE);
            tu_moi.setText(getStringDB(tuIndex, 1));

            //tạo ngẫu nhiên 1 đáp án đúng
            random = new Random();
            dapAnDung = random.nextInt(4);
            nghiaIndex = new int[SIZE];//mảng chỉ số nghĩa
            nghiaIndex[dapAnDung] = tuIndex;
            //tạo đáp án sai
            int[] temp = new int[3];
            temp[0] = rdNumber(tuIndex, tuIndex, tuIndex, SIZE);
            temp[1] = rdNumber(tuIndex, tuIndex, temp[0], SIZE);
            temp[2] = rdNumber(tuIndex, temp[0], temp[1], SIZE);
            int k = 0;
            for (int i = 0; i < 4; i++) {
                if (i != dapAnDung) {
                    nghiaIndex[i] = temp[k];
                    k++;
                }
            }
            //hiện 4 đ.án
            one.setText(getStringDB(nghiaIndex[0], 2));
            two.setText(getStringDB(nghiaIndex[1], 2));
            three.setText(getStringDB(nghiaIndex[2], 2));
            four.setText(getStringDB(nghiaIndex[3], 2));
        }
        else
        {
            random = new Random();
            tuIndex = random.nextInt(SIZE_OF_VOCA);
            tu_moi.setText(tu[tuIndex]);

            //tạo ngẫu nhiên 1 đáp án đúng
            random = new Random();
            dapAnDung = random.nextInt(4);
            nghiaIndex = new int[SIZE_OF_VOCA];//mảng chỉ số nghĩa
            nghiaIndex[dapAnDung] = tuIndex;
            //tạo đáp án sai
            int[] temp = new int[3];
            temp[0] = rdNumber(tuIndex, tuIndex, tuIndex, SIZE_OF_VOCA);
            temp[1] = rdNumber(tuIndex, tuIndex, temp[0], SIZE_OF_VOCA);
            temp[2] = rdNumber(tuIndex, temp[0], temp[1], SIZE_OF_VOCA);
            int k = 0;
            for (int i = 0; i < 4; i++) {
                if (i != dapAnDung) {
                    nghiaIndex[i] = temp[k];
                    k++;
                }
            }
            //hien 4 dap an
            one.setText(nghia[nghiaIndex[0]]);
            two.setText(nghia[nghiaIndex[1]]);
            three.setText(nghia[nghiaIndex[2]]);
            four.setText(nghia[nghiaIndex[3]]);
        }

    }

    //

    int rdNumber(int n1,int n2, int n3, int size)
    {
        Random r=new Random();
        int num=r.nextInt(size);
        while (num==n1||num==n2||num==n3)
        {
            r=new Random();
            num=r.nextInt(size);
        }
        return num;
    }
    private String getStringDB(int index, int column)
    {

        String query1="SELECT * FROM "+TABLE_NAME+ " WHERE ID="+index;
        c=myDb.db.rawQuery(query1,null);
        c.moveToFirst();
        return c.getString(column);
    }
    private int getCount()
    {
        int num;
        if(myDb.checkForTables()) {
            AppController.getInstance().setListVocabularies(myDb.getAllVocabulary());
            num = AppController.getInstance().getListVocabularies().size();
        }
        else
        {
            num=0;
        }
            return num;
    }
}
