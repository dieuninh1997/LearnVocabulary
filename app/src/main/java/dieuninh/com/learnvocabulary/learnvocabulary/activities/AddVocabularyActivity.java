package dieuninh.com.learnvocabulary.learnvocabulary.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dieuninh.com.learnvocabulary.learnvocabulary.R;
import dieuninh.com.learnvocabulary.learnvocabulary.adapters.VocabularyAdapter;
import dieuninh.com.learnvocabulary.learnvocabulary.application.AppController;
import dieuninh.com.learnvocabulary.learnvocabulary.models.DatabaseHandler;
import dieuninh.com.learnvocabulary.learnvocabulary.models.Vocabulary;

public class AddVocabularyActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.rcv_vocabulary)
    RecyclerView rcvVocabulary;
    List<Vocabulary> list;
    VocabularyAdapter adapter;
    DatabaseHandler db;

  //  public int number;
    @Bind(R.id.fab_save)
    FloatingActionButton fabSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vocabulary);
        ButterKnife.bind(this);
        rcvVocabulary.setLayoutManager(new LinearLayoutManager(this));
        rcvVocabulary.setHasFixedSize(true);
        list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new Vocabulary(i));
        }
        adapter = new VocabularyAdapter(this, list);
        rcvVocabulary.setAdapter(adapter);
        db=new DatabaseHandler(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_save: {
                // TODO: 12/19/2016 add listVo vao db.
                List<Vocabulary> listVo = new ArrayList<>();
                String tuMoi, nghia;
                int id;
                int err=0;
                for (Vocabulary vs : list) {
                    id = vs.getId();
                    nghia = vs.getMeaning();
                    tuMoi = vs.getNewWord();
                    if (!tuMoi.isEmpty() && !nghia.isEmpty()) {
                        boolean kt = db.addVocabulary(id, tuMoi, nghia);
                        if (kt)
                            listVo.add(vs);
                    }
                    else
                    {
                        err++;
                        //Toast.makeText(this,"Fail Save ",Toast.LENGTH_SHORT).show();
                    }
                }
                if(err>0)
                {
                    Toast.makeText(this,"Please fill out !",Toast.LENGTH_SHORT).show();
                }
                else {
                    //
                    Intent myIntent = new Intent(AddVocabularyActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    int number = list.size();
                    bundle.putInt("number", number);
                    myIntent.putExtra("MyIntent", bundle);
                    startActivity(myIntent);
                    //

                    if (number > 0) {
                        Toast.makeText(this, "Please remember Vocabulary to ready to contest", Toast.LENGTH_SHORT).show();
//                    return;
                    }


//                Log.e("ERROR list.size()=",db.getAllVocabulary().size()+"");
                    AppController.getInstance().setListVocabularies(db.getAllVocabulary());
                    //lôi ra nè
                    // number=AppController.getInstance().getListVocabularies().size();
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
        }
    }
}
