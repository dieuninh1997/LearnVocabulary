package dieuninh.com.learnvocabulary.learnvocabulary.activities;

import android.app.SearchManager;
import android.content.Context;
import android.database.MatrixCursor;
import android.graphics.Color;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.widget.CursorAdapter;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dieuninh.com.learnvocabulary.learnvocabulary.R;
import dieuninh.com.learnvocabulary.learnvocabulary.adapters.VocabularyResultAdapter;
import dieuninh.com.learnvocabulary.learnvocabulary.application.AppController;
import dieuninh.com.learnvocabulary.learnvocabulary.models.DatabaseHandler;
import dieuninh.com.learnvocabulary.learnvocabulary.models.Vocabulary;

public class VocaListActivity extends AppCompatActivity {

    public static RelativeLayout frRoot;
    @Bind(R.id.rcv_voca)
    RecyclerView rcvVocabulary;
    TextView tv_thongbao;
    ArrayList<Vocabulary> list=null;
    VocabularyResultAdapter adapter;
    android.support.v4.widget.SimpleCursorAdapter searchAdapter;
    SearchView.SearchAutoComplete searchAutoComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voca_list);
        addControls();

    }
    //tạo thanh search cho user tìm kiếm tù nhanh hơn,
    // gợi ý khi search vài chữ : tìm kiếm chữ đó cả ở nghĩa và từ mới có chứa chữ đó luôn

    private void addControls()
    {
        final String[] from=new String[]{"tuSearch"};
        final int[]to=new int[]{android.R.id.text1};
        searchAdapter= new android.support.v4.widget.SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,null,from,to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        frRoot = (RelativeLayout) findViewById(R.id.frRoot);
        tv_thongbao= (TextView) findViewById(R.id.tv_thongbao);
        ButterKnife.bind(this);
        list = (ArrayList<Vocabulary>) AppController.getInstance().getListVocabularies();
        rcvVocabulary.setLayoutManager(new LinearLayoutManager(this));
        rcvVocabulary.setHasFixedSize(true);

        int size = list.size();
        if (size == 0) {
            tv_thongbao.setText("Empty List : 0 word(s)");
        } else {
            adapter = new VocabularyResultAdapter(this, list);
            rcvVocabulary.setAdapter(adapter);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);

        SearchManager searchManager= (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView= (SearchView) menu.findItem(R.id.menu_search).getActionView();

        searchAutoComplete= (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(Color.WHITE);
        searchAutoComplete.setTextColor(Color.WHITE);
        searchAutoComplete.setThreshold(1);
        searchAutoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals(""))
                {
                   addControls();
                    Log.e("ERROR list size =",list.size()+"");

                }
                else
                {
                    getSuggestion(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        /*    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSuggestionsAdapter(searchAdapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                 getSuggestion(newText);
                return true;
            }
        });

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return true;
            }

            @Override
            public boolean onSuggestionClick(int position) {
              *//*  searchAdapter.getCursor().moveToPosition(position);
                String query=searchAdapter.getCursor().getString(1);
*//*
                return true;
            }
        });*/
    return super.onCreateOptionsMenu(menu);
    }

    public void getSuggestion(String text)
    {
//        MatrixCursor c=new MatrixCursor(new String[]{BaseColumns._ID,"tuSearch"});
        ArrayList<Vocabulary> itemSearchList=new ArrayList<Vocabulary>();
        String s=searchAutoComplete.getText().toString();
        int s_length=s.length();

        String str_Nw, str_M;
        int list_size=list.size();
        for(int i=0;i<list_size;i++)
        {
            str_Nw=list.get(i).getNewWord();
            str_M=list.get(i).getMeaning();
            if(str_Nw.contains(s))
            {
                itemSearchList.add(list.get(i));
//                c.addRow(new Object[]{i,list.get(i).getNewWord()});
            }
            else
            if(str_M.contains(s))
            {
                itemSearchList.add(list.get(i));
//                c.addRow(new Object[]{i,list.get(i).getMeaning()});
            }
        }
        adapter = new VocabularyResultAdapter(VocaListActivity.this, itemSearchList);
        rcvVocabulary.setAdapter(adapter);

//        adapter.notifyDataSetChanged();
    }

}
