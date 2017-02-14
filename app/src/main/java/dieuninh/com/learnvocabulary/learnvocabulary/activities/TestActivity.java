package dieuninh.com.learnvocabulary.learnvocabulary.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wenchao.cardstack.CardStack;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dieuninh.com.learnvocabulary.learnvocabulary.R;
import dieuninh.com.learnvocabulary.learnvocabulary.adapters.CardsAdapter;
import dieuninh.com.learnvocabulary.learnvocabulary.adapters.VocabularyResultAdapter;
import dieuninh.com.learnvocabulary.learnvocabulary.application.AppController;
import dieuninh.com.learnvocabulary.learnvocabulary.models.Vocabulary;

public class TestActivity extends AppCompatActivity {
    private CardStack mCardStack;
    private CardsAdapter mCardAdapter;
    public static String[] arrayList;
    public LinearLayout frRoot;

    //android.support.design.widget.FloatingActionButton fab;
    @Bind(R.id.rcv_vocabulary)
    RecyclerView rcvVocabulary;
    List<Vocabulary> list;
    VocabularyResultAdapter adapter;
    public static int dem = 0;
    int SIZE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ButterKnife.bind(this);
        arrayList = getResources().getStringArray(R.array.colors);
        mCardStack = (CardStack) findViewById(R.id.container);
        frRoot = (LinearLayout) findViewById(R.id.frRoot);
        mCardStack.setContentResource(R.layout.card_content);
        list = AppController.getInstance().getListVocabularies();
        SIZE = list.size();
//        final MediaPlayer sound_correct=MediaPlayer.create(getApplicationContext(),R.raw.sound_correct);
//        final MediaPlayer sound_wrong=MediaPlayer.create(getApplicationContext(),R.raw.sound_wrong);

        final SoundPool sp_correct = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        final SoundPool sp_wrong = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        final int sp_correctId = sp_correct.load(getApplicationContext(), R.raw.sound_correct, 1);
        final int sp_wrongId = sp_wrong.load(getApplicationContext(), R.raw.sound_wrong, 1);

        Collections.shuffle(list);
        mCardAdapter = new CardsAdapter(this, list);
        int i = 0;
        mCardAdapter.add(String.valueOf(list.get(i)));
        mCardStack.setAdapter(mCardAdapter);

        final SharedPreferences s = getApplicationContext().getSharedPreferences("sharedPrefSound", Context.MODE_PRIVATE);


        mCardStack.setListener(new CardStack.CardEventListener() {
            @Override
            public boolean swipeEnd(int section, float distance) {


                if (CardsAdapter.chuyenCard) {

                    if (s.getBoolean("sound", false)) {
//                        sound_correct.start();
                        sp_correct.play(sp_correctId, 1, 1, 0, 0, 1);
//                        Toast.makeText(getApplicationContext(),"sound True", Toast.LENGTH_SHORT).show();
                    }

                    dem++;
                    CardsAdapter.chuyenCard = false;
//                    Toast.makeText(getApplicationContext(),"dem="+dem, Toast.LENGTH_SHORT).show();
                    if (dem == SIZE) {
                        rcvVocabulary.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        rcvVocabulary.setHasFixedSize(true);

                        adapter = new VocabularyResultAdapter(getApplicationContext(), list);
                        rcvVocabulary.setAdapter(adapter);
                        dem = 0;
                    }

                    return true;
                } else {
                    if (s.getBoolean("sound", false)) {
//                        sound_wrong.start();
                        sp_wrong.play(sp_wrongId, 1, 1, 0, 0, 1);
//                        Toast.makeText(getApplicationContext(),"sound False", Toast.LENGTH_SHORT).show();

                    }


                    Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vib.vibrate(300);
                    return false;
                }
            }

            @Override
            public boolean swipeStart(int section, float distance) {


                return false;
            }

            @Override
            public boolean swipeContinue(int section, float distanceX, float distanceY) {

                return false;
            }

            @Override
            public void discarded(int mIndex, int direction) {


            }

            @Override
            public void topCardTapped() {

            }
        });
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    @Override
    protected void onPause() {
        super.onPause();
        dem = 0;
    }
    /*private void showDialogHelp() {
       dialog =  new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("View Sample app on Google Play")
                .setContentText("GO GO GO GO GO GO GO GO GO GO GO GO GO GO ")
                .setCancelText("No")
                .setConfirmText("Yes")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        openGGPlay();
                        dialog.dismissWithAnimation();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        openGGPlay();
                        dialog.dismissWithAnimation();
                    }
                });
        dialog.show();
    }

    private void openGGPlay() {
        String url = Utils.APP_URL;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.about) {
            share();
            return true;
        }
        if (id == R.id.add) {
            share();
            return true;
        }
        if (id == R.id.share) {
            share();
            return true;
        }
        if (id == R.id.chplay) {
            share();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void share(){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, "#ask2know");
        i.putExtra(Intent.EXTRA_TEXT, Utils.APP_URL);
        startActivity(Intent.createChooser(i, "ask2know"));
    }*/
}
