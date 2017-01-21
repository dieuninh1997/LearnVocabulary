package dieuninh.com.learnvocabulary.learnvocabulary.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.TextView;

import dieuninh.com.learnvocabulary.learnvocabulary.R;
import dieuninh.com.learnvocabulary.learnvocabulary.application.AppController;
import dieuninh.com.learnvocabulary.learnvocabulary.models.DatabaseHandler;

public class MainActivity extends AppCompatActivity {
    CardView cv_start,cv_new,cv_storedList,cv_settings;
    DatabaseHandler db;
    TextView tv_stortedwords;
    static int num;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhXa();


        chon();

        AppController.getInstance().setListVocabularies(db.getAllVocabulary());
         num= AppController.getInstance().getListVocabularies().size();
        tv_stortedwords.setText(String.format("Stored %1$d  words",num));


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


    private void chon() {
        cv_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(db.checkForTables()==false)
                {

                    startActivity(new Intent(MainActivity.this, AddVocabularyActivity.class));
                }
                else {

                    AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
                    b.setTitle("Confirm");
                    b.setMessage("Do you really create a new vocabulary list? " +
                            "If you choose YES ,all precious vocabulary will be removed.");
                    b.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            db.deleteAllData();
                            num=0;
                            tv_stortedwords.setText(String.format("Stored %1$d  words",num));
                            Intent intent = new Intent(MainActivity.this, AddVocabularyActivity.class);
                            startActivity(intent);
                        }
                    });
                    b.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

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
                if(db.checkForTables()==false)
                {
                    startActivity(new Intent(MainActivity.this, AddVocabularyActivity.class));
                }
                else//ko rỗng: True
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

    public void reNum()
    {
        Intent callerIntent=getIntent();
        Bundle bundleFromCaller=callerIntent.getBundleExtra("MyIntent");
        num=bundleFromCaller.getInt("number");
        tv_stortedwords.setText(String.format("Stored %1$d  words",num));
    }
    private void anhXa() {

        cv_start= (CardView) findViewById(R.id.cv_start);
        cv_new= (CardView) findViewById(R.id.cv_new);
        cv_storedList= (CardView) findViewById(R.id.cv_storedList);
        cv_settings= (CardView) findViewById(R.id.cv_settings);
        tv_stortedwords= (TextView) findViewById(R.id.tv_storedwords);
        db=new DatabaseHandler(this);
    }

    @Override
    protected void onResume() {
        db.getWritableDatabase();
        super.onResume();
    }
}
