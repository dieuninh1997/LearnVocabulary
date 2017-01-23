package dieuninh.com.learnvocabulary.learnvocabulary.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

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
    public static String [] arrayList;
    public static LinearLayout frRoot;

    //android.support.design.widget.FloatingActionButton fab;
    @Bind(R.id.rcv_vocabulary)
    RecyclerView rcvVocabulary;
    List<Vocabulary> list;
    VocabularyResultAdapter adapter;
    public  static int dem=0;
    int SIZE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ButterKnife.bind(this);
        arrayList = getResources().getStringArray(R.array.colors);
        mCardStack = (CardStack)findViewById(R.id.container);
        frRoot = (LinearLayout) findViewById(R.id.frRoot);
        mCardStack.setContentResource(R.layout.card_content);
//
        list = AppController.getInstance().getListVocabularies();
        SIZE=list.size();
        Log.e("Loi ERR : SIZE=",SIZE+"");
        Collections.shuffle(list);
        mCardAdapter = new CardsAdapter(this, list);
        int i=0;
        mCardAdapter.add(String.valueOf(list.get(i)));
        mCardStack.setAdapter(mCardAdapter);

        mCardStack.setListener(new CardStack.CardEventListener() {
            @Override
            public boolean swipeEnd(int section, float distance) {
//                finish();
                if(mCardAdapter.chuyenCard)
                {
                    dem++;
                    Log.e("Loi ERR dem1=",dem+"");
                    if(dem==SIZE) {
                        rcvVocabulary.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        rcvVocabulary.setHasFixedSize(true);


                        adapter = new VocabularyResultAdapter(getApplicationContext(), list);
                        rcvVocabulary.setAdapter(adapter);
                    }
                    return true;
                }
                else
                {
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


//        Log.e("Loi ERR dem2=",dem+"");

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
