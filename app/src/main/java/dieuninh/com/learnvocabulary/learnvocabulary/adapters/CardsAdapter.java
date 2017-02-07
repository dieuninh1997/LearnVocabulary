package dieuninh.com.learnvocabulary.learnvocabulary.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import dieuninh.com.learnvocabulary.learnvocabulary.R;
import dieuninh.com.learnvocabulary.learnvocabulary.activities.TestActivity;
import dieuninh.com.learnvocabulary.learnvocabulary.models.Vocabulary;


public class CardsAdapter extends ArrayAdapter<String>  {
    public static String TAG = CardsAdapter.class.getSimpleName().toUpperCase();
  private   Activity activity;
    private CardView cv;
    private TextView v;
   private List<Vocabulary> list;
    public static boolean chuyenCard;//chuyển card
    private RadioGroup radioGroup;

    public CardsAdapter(Activity context, List<Vocabulary> list) {
        super(context, R.layout.card_content);
        this.activity = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();

    }

    @Override
    public View getView(int position, final View contentView, ViewGroup parent) {
        final MediaPlayer sound_click=MediaPlayer.create(getContext(),R.raw.sound_click);
        v = (TextView) (contentView.findViewById(R.id.tv_newword));
        cv = (CardView) (contentView.findViewById(R.id.card));
       radioGroup = (RadioGroup) contentView.findViewById(R.id.rad_group);
         /*RadioButton radioButton1= (RadioButton) radioGroup.findViewById(R.id.rad_ans_a);
        RadioButton radioButton2= (RadioButton) radioGroup.findViewById(R.id.rad_ans_b);
        RadioButton radioButton3= (RadioButton) radioGroup.findViewById(R.id.rad_ans_c);
        RadioButton radioButton4= (RadioButton) radioGroup.findViewById(R.id.rad_ans_d);*/
        Vocabulary vocabularyTrue = list.get(position);
        v.setText(vocabularyTrue.getNewWord());
        int list_size = getCount();

        int posAnsFalse1;
        boolean ktra;
        do {
            posAnsFalse1 = new Random().nextInt(list_size - 1);
            ktra = list.get(posAnsFalse1).getNewWord().equals(vocabularyTrue.getNewWord());
        } while (ktra);

        int posAnsFalse2;
        do {
            posAnsFalse2 = new Random().nextInt(list_size - 1);
            ktra = list.get(posAnsFalse2).getNewWord().equals(vocabularyTrue.getNewWord());
        } while (posAnsFalse1 == posAnsFalse2 || ktra);

        int posAnsFalse3;
        do {
            posAnsFalse3 = new Random().nextInt(list_size - 1);
            ktra = list.get(posAnsFalse3).getNewWord().equals(vocabularyTrue.getNewWord());
        } while (posAnsFalse3 == posAnsFalse2 || posAnsFalse3 == posAnsFalse1 || ktra);

        String[] ansFalse = {list.get(posAnsFalse1).getMeaning(), list.get(posAnsFalse2).getMeaning(), list.get(posAnsFalse3).getMeaning()};
        final int posTrue= new Random().nextInt(3);
        int k = 0;
        final RadioButton rad[] = new RadioButton[4];
        for (int in = 0; in < 4; in++) {
            rad[in] = new RadioButton(activity);
            rad[in].setTextColor(Color.WHITE);
            rad[in].setTextSize(16);
            rad[in].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            if (posTrue == in) {
                rad[in].setText(vocabularyTrue.getMeaning());
            } else {

                rad[in].setText(ansFalse[k]);
                k++;
            }
            radioGroup.addView(rad[in]);
            rad[in].setId(in);
        }
        chuyenCard=false;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                for(int i=0;i<radioGroup.getChildCount();i++) {
                    RadioButton btn = (RadioButton) radioGroup.getChildAt(i);
//                    int t = radioGroup.getId();
                    if (btn.getId() == checkedId) {
                        sound_click.start();
                        if (btn.getId() == posTrue) {
                            chuyenCard = true;
//                            Toast.makeText(getContext(), "chuyencard =true, change", Toast.LENGTH_SHORT).show();
                        } else {
                            chuyenCard = false;
//                            Toast.makeText(getContext(), "chuyencard =false, change", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        int i = new Random().nextInt(TestActivity.arrayList.length - 3);
        cv.setCardBackgroundColor(Color.parseColor(TestActivity.arrayList[i]));
        return contentView;
    }


}

/*
* ý tưởng: nếu check đúng thì chuyenCard=true
* nếu check sai thì demCheckWrong++
*  nếu vài lần sai mà 1 lần check đúng thì tại lần đúng đó cho demCheckWrong=0
*
*  nếu ng dùng ko check lần nào cả thì mặc định chuyenCard=false rồi.
*
*  nếu demCheckWrong>0 thì chuyenCard=false
*
* */