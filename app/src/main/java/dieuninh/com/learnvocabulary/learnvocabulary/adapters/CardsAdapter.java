package dieuninh.com.learnvocabulary.learnvocabulary.adapters;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dieuninh.com.learnvocabulary.learnvocabulary.R;
import dieuninh.com.learnvocabulary.learnvocabulary.activities.TestActivity;
import dieuninh.com.learnvocabulary.learnvocabulary.application.AppController;
import dieuninh.com.learnvocabulary.learnvocabulary.models.DatabaseHandler;
import dieuninh.com.learnvocabulary.learnvocabulary.models.Vocabulary;
import dieuninh.com.learnvocabulary.learnvocabulary.services.LockScreenService;

/**
 * Created by DieuLinh on 12/30/2016.
 */

public class CardsAdapter extends ArrayAdapter<String>{
    public static String TAG = CardsAdapter.class.getSimpleName().toUpperCase();
    Activity activity;
    CardView cv;
    TextView v;
    List<Vocabulary> list;
    public static boolean chuyenCard=false;
    public static int dem=0;
 /*   public int SIZE;
    DatabaseHandler myDb;
    Cursor c=null;
    private static final String TABLE_NAME = "VocabularyTable";
    int tuIndex;
    int[] nghiaIndex;
    int dapAnDung;*/


    public CardsAdapter(Activity context, List<Vocabulary> list) {
        super(context, R.layout.card_content);
        this.activity = context;
        this.list = list;
    }
/*
    public CardsAdapter(Activity context) {
        super(context, R.layout.card_content);
        this.activity = context;

    }*/
    @Override
    public int getCount() {
        return list.size();

    }
    @Override
    public View getView(int position, final View contentView, ViewGroup parent) {


        v = (TextView) (contentView.findViewById(R.id.tv_newword));
        cv = (CardView) (contentView.findViewById(R.id.card));
        RadioGroup radioGroup = (RadioGroup) contentView.findViewById(R.id.rad_group);
      /*    RadioButton radioButtonA = (RadioButton)radioGroup.findViewById(R.id.rad_ans_a);
        RadioButton radioButtonB = (RadioButton)radioGroup.findViewById(R.id.rad_ans_b);
        RadioButton radioButtonC = (RadioButton)radioGroup.findViewById(R.id.rad_ans_c);
        RadioButton radioButtonD = (RadioButton)radioGroup.findViewById(R.id.rad_ans_d);
        List<RadioButton> listRAd = new ArrayList<>();*/

        //TODO: fix lỗi ko hiển thị hết từ 1/1/2017
       Vocabulary vocabularyTrue = list.get(position);
        v.setText(vocabularyTrue.getNewWord());
        int list_size=getCount();
        int posAnsFalse1;
        boolean ktra;
        do {
            posAnsFalse1 = new Random().nextInt(list_size - 1);
            ktra=list.get(posAnsFalse1).getNewWord().equals(vocabularyTrue.getNewWord());
        } while (ktra);

        int posAnsFalse2;
        do {
            posAnsFalse2 = new Random().nextInt(list_size - 1);
            ktra=list.get(posAnsFalse2).getNewWord().equals(vocabularyTrue.getNewWord());
        } while (posAnsFalse1 == posAnsFalse2 || ktra);

        int posAnsFalse3;
        do {
            posAnsFalse3 = new Random().nextInt(list_size - 1);
            ktra=list.get(posAnsFalse3).getNewWord().equals(vocabularyTrue.getNewWord());
        } while (posAnsFalse3 == posAnsFalse2 || posAnsFalse3 == posAnsFalse1|| ktra);


        String[] ansFalse = {list.get(posAnsFalse1).getMeaning(), list.get(posAnsFalse2).getMeaning(), list.get(posAnsFalse3).getMeaning()};
        int posTrue = new Random().nextInt(3);
        int k = 0;


        for (int in = 0; in < 4; in++) {
            RadioButton rad = new RadioButton(activity);
            rad.setTextColor(Color.WHITE);
            rad.setTextSize(16);
            rad.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            if (posTrue == in) {
                rad.setText(vocabularyTrue.getMeaning());
                rad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b) {
                            chuyenCard = true;
                            dem ++;
                        }


                    }
                });
            } else {

                rad.setText(ansFalse[k]);
                rad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b) {
                            chuyenCard = false;
                            dem++;
                        }

                    }
                });
                k++;
            }
            if(dem>0)
            {
                chuyenCard=false;
            }
            radioGroup.addView(rad);

        }



       /* myDb = new DatabaseHandler(getContext());
        //
        SIZE = getCountRad();
        //tạo ngẫu nhiên 1 từ mới
        Random random = new Random();
        tuIndex=random.nextInt(SIZE);
        v.setText(getStringDB(tuIndex,1));

        //tạo ngẫu nhiên 1 đáp án đúng
        random=new Random();
        dapAnDung=random.nextInt(4);
        nghiaIndex=new int[SIZE];//mảng chỉ số nghĩa
        nghiaIndex[dapAnDung]=tuIndex;
        //tạo đáp án sai
        int[] temp=new int[3];
        temp[0]=rdNumber(tuIndex,tuIndex,tuIndex,SIZE);
        temp[1]=rdNumber(tuIndex,tuIndex,temp[0],SIZE);
        temp[2]=rdNumber(tuIndex,temp[0],temp[1],SIZE);
        int k=0;
        for(int i=0;i<4;i++)
        {
            if(i!=dapAnDung)
            {
                nghiaIndex[i]=temp[k];
                k++;
            }
        }
//        nghia=myDb.getMeaning();
        //hiện 4 đ.án
        for (int in = 0; in < 4; in++) {
            RadioButton rad = new RadioButton(activity);
            rad.setTextColor(Color.WHITE);
            rad.setTextSize(16);
            rad.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            if (tuIndex == nghiaIndex[in]) {
                rad.setText(getStringDB(nghiaIndex[in],2));
//                rad.setTextColor(1);
                rad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        chuyenCard = true;
                    }
                });
            } else {
                chuyenCard = false;
                rad.setText(getStringDB(nghiaIndex[in],2));
                //k++;
            }
            radioGroup.addView(rad);

        }*/

      /*  one.setText(getStringDB(nghiaIndex[0],2));
        two.setText(getStringDB(nghiaIndex[1],2));
        three.setText(getStringDB(nghiaIndex[2],2));
        four.setText(getStringDB(nghiaIndex[3],2));*/

        int i = new Random().nextInt(TestActivity.arrayList.length - 3);
//        v.setBackgroundColor(Color.parseColor(TestActivity.arrayList[i+3]));
        cv.setCardBackgroundColor(Color.parseColor(TestActivity.arrayList[i]));


        return contentView;
    }

    //

   /* int rdNumber(int n1,int n2, int n3, int size)
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
        //index=index+1;
        String query1="SELECT * FROM "+TABLE_NAME+ " WHERE ID="+index;
        c=myDb.db.rawQuery(query1,null);
        c.moveToFirst();
        return c.getString(column);
    }
    private int getCountRad() {

        AppController.getInstance().setListVocabularies(myDb.getAllVocabulary());
        int num = AppController.getInstance().getListVocabularies().size();
        return num;
    }*/


}
