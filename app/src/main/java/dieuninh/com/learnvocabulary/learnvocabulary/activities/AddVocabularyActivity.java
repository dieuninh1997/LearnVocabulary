package dieuninh.com.learnvocabulary.learnvocabulary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dieuninh.com.learnvocabulary.learnvocabulary.R;
import dieuninh.com.learnvocabulary.learnvocabulary.adapters.VocabularyAdapter;
import dieuninh.com.learnvocabulary.learnvocabulary.application.AppController;
import dieuninh.com.learnvocabulary.learnvocabulary.models.DatabaseHandler;
import dieuninh.com.learnvocabulary.learnvocabulary.models.MySharedPreference;
import dieuninh.com.learnvocabulary.learnvocabulary.models.Vocabulary;

public class AddVocabularyActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.rcv_vocabulary)
    RecyclerView rcvVocabulary;
    List<Vocabulary> list;
    VocabularyAdapter adapter;
    DatabaseHandler db;
    static int err;
    @Bind(R.id.fab_save)
    FloatingActionButton fabSave;
    @Bind(R.id.fa_menu)
    FloatingActionMenu menu;
    String tuMoi, nghia;
    int id;
    List<Vocabulary> listVo;

    ArrayList<Vocabulary> vocabulies;
    MySharedPreference sharedPreference;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vocabulary);
        ButterKnife.bind(this);
        gson = new Gson();
        sharedPreference = new MySharedPreference(getApplicationContext());
        db = new DatabaseHandler(this);
        rcvVocabulary.setLayoutManager(new LinearLayoutManager(this));
        rcvVocabulary.setHasFixedSize(true);
        list = getVocaListFromSharedPreference();
        adapter = new VocabularyAdapter(this, list);
        rcvVocabulary.setAdapter(adapter);
        menu.close(false);

        menu.setClosedOnTouchOutside(true);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);//cái này fix focus edittext
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_save: {
                err = 0;
                for (Vocabulary vs : list) {
                    id = vs.getId();
                    nghia = vs.getMeaning();
                    tuMoi = vs.getNewWord();
                    if (!tuMoi.isEmpty() && !nghia.isEmpty()) {

                    } else {
                        err++;
                    }
                }
                if (err > 0) {
                    Toast.makeText(this, "Please fill out !", Toast.LENGTH_SHORT).show();
                } else {
                    //
                    listVo = new ArrayList<>();
                    for (Vocabulary vs : list) {
                        id = vs.getId();
                        nghia = vs.getMeaning();
                        tuMoi = vs.getNewWord();
                        if (!tuMoi.isEmpty() && !nghia.isEmpty()) {
                            boolean kt = db.addVocabulary(id, tuMoi, nghia);
                            if (kt) {
                                listVo.add(vs);
                            }
                        }
                    }

                    //
                    Intent myIntent = new Intent(AddVocabularyActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    int number = list.size();
                    bundle.putInt("number", number);
                    myIntent.putExtra("MyIntent", bundle);
                    startActivity(myIntent);
                    //
                    if (number > 0) {
                        Toast.makeText(this, getString(R.string.remember_to_test), Toast.LENGTH_SHORT).show();
                    }
                    AppController.getInstance().setListVocabularies(db.getAllVocabulary());

                    startActivity(new Intent(AddVocabularyActivity.this, TestActivity.class));
                    finish();
                }
                break;
            }
            case R.id.fab_more: {
                list.add(new Vocabulary(list.size()));
                adapter.notifyItemInserted(adapter.getItemCount() - 1);
                rcvVocabulary.smoothScrollToPosition(adapter.getItemCount());
                break;
            }
            case R.id.fab_eraser: {
                xoaAllTextVocabulary();
                break;
            }
        }
    }

    private void xoaAllTextVocabulary() {
        sharedPreference.removeVocaList();
        list = getVocaListFromSharedPreference();
        adapter = new VocabularyAdapter(getApplicationContext(), list);
        rcvVocabulary.setAdapter(adapter);

    }

    private void saveVoListToSharedPreference(ArrayList voList) {
        //convert ArrayList object to String by Gson
        String jsonVo = gson.toJson(voList);
        //save to shared preference
        sharedPreference.saveVocaList(jsonVo);
    }

    /**
     * Retrieving data from sharepref
     */
    private ArrayList<Vocabulary> getVocaListFromSharedPreference() {
        //retrieve data from shared preference
        String jsonVo = sharedPreference.getVocaList();
        Type type = new TypeToken<List<Vocabulary>>() {
        }.getType();
        vocabulies = gson.fromJson(jsonVo, type);
        if (vocabulies == null) {
            vocabulies = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                vocabulies.add(new Vocabulary(i));
            }
        }
        return vocabulies;
    }

    @Override
    protected void onPause() {
        super.onPause();
        ArrayList<Vocabulary> mList = new ArrayList<>();
        if (list != null) {
            for (Vocabulary i : list) {
                mList.add(i);
            }
            saveVoListToSharedPreference(mList);
        }
    }

    /*private void xoaDongInListVoca(int position) {
           list.remove(position);
           rcvVocabulary.removeViewAt(position);
           adapter.notifyItemRemoved(position);
           adapter.notifyItemRangeChanged(position, list.size());
       }*/
}
