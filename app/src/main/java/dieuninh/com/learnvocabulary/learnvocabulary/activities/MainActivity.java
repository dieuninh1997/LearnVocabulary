package dieuninh.com.learnvocabulary.learnvocabulary.activities;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import dieuninh.com.learnvocabulary.learnvocabulary.R;
import dieuninh.com.learnvocabulary.learnvocabulary.application.AppController;
import dieuninh.com.learnvocabulary.learnvocabulary.models.DatabaseHandler;
import dieuninh.com.learnvocabulary.learnvocabulary.models.MySharedPreference;

public class MainActivity extends AppCompatActivity {
    CardView cv_start, cv_new, cv_storedList, cv_settings;
    DatabaseHandler db;
    TextView tv_stortedwords;
    static int num;
    MySharedPreference mySharedPreference;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        clickNotification();
        anhXa();
        chon();
        AppController.getInstance().setListVocabularies(db.getAllVocabulary());
        num = AppController.getInstance().getListVocabularies().size();
        tv_stortedwords.setText(String.format(getString(R.string.stored_number_words), num));


      /*  cv_start.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                Drawable background=view.getBackground();
                if(background!=null)
                {
                    background.getOutline(outline);
                }
                else {
                    outline.setRect(0, 0, view.getWidth(), view.getHeight());
                    outline.setAlpha(0.0f);
                }
            }
        });*/


    }

    public void clickNotification() {
        Intent intent = getIntent();
//        Toast.makeText(getApplicationContext(),intent.getStringExtra("code")+"",Toast.LENGTH_SHORT).show();
        if (intent.getStringExtra("code") != null) {
            int code = Integer.parseInt(intent.getStringExtra("code"));
            if (code == 1) {
                startActivity(new Intent(getApplicationContext(), VocaListActivity.class));
            }
        }
    }

    private void chon() {

        cv_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!db.checkForTables()) {

                    startActivity(new Intent(MainActivity.this, AddVocabularyActivity.class));
                } else {
                    AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
                    b.setTitle("Confirm");
//                    b.setIcon(R.drawable.logo_lv);
                    b.setMessage(getString(R.string.thong_bao_confirm_message));
                    b.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            db.deleteAllData();
                            mySharedPreference.removeVocaList();
                            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.cancelAll();
/*
* sau khi tạo list từ mới thì phải cập nhật lại stored words ở List Activity
* và phải tắt notification nữa.
*
* */
//                          Toast.makeText(getApplicationContext(),"Xoa thanh cong",Toast.LENGTH_SHORT).show();
                            num = 0;
                            tv_stortedwords.setText(String.format(getString(R.string.stored_number_words), num));
                            Intent intent = new Intent(MainActivity.this, AddVocabularyActivity.class);
                            startActivity(intent);
                        }
                    });
                    b.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
//                            Toast.makeText(getApplicationContext(),"No lan xx",Toast.LENGTH_SHORT).show();
                            dialogInterface.cancel();
                        }
                    });
                    b.create().show();
                }
            }
        });
        cv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!db.checkForTables()) {
                    startActivity(new Intent(MainActivity.this, AddVocabularyActivity.class));
                } else//ko rỗng: True
                {
                    AppController.getInstance().setListVocabularies(db.getAllVocabulary());
                    //lôi ra nè
                    //num=AppController.getInstance().getListVocabularies().size();
                    //tv_stortedwords.setText(String.format("Stored %1$d  words",num));
                    startActivity(new Intent(MainActivity.this, TestActivity.class));

                }

            }
        });
        cv_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });
        cv_storedList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppController.getInstance().setListVocabularies(db.getAllVocabulary());
                startActivity(new Intent(MainActivity.this, VocaListActivity.class));
            }
        });
    }

    public void reNum() {
        Intent callerIntent = getIntent();
        Bundle bundleFromCaller = callerIntent.getBundleExtra("MyIntent");
        num = bundleFromCaller.getInt("number");
        tv_stortedwords.setText(String.format(getString(R.string.stored_number_words), num));
    }

    private void anhXa() {

        cv_start = (CardView) findViewById(R.id.cv_start);
        cv_new = (CardView) findViewById(R.id.cv_new);
        cv_storedList = (CardView) findViewById(R.id.cv_storedList);
        cv_settings = (CardView) findViewById(R.id.cv_settings);
        tv_stortedwords = (TextView) findViewById(R.id.tv_storedwords);
        db = new DatabaseHandler(this);
        mySharedPreference = new MySharedPreference(getApplicationContext());
    }

    @Override
    protected void onResume() {
        db.getWritableDatabase();
        super.onResume();
    }
}
