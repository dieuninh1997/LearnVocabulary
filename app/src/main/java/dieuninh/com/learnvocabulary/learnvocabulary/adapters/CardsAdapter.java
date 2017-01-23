package dieuninh.com.learnvocabulary.learnvocabulary.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
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
   private static int demCheckWrong ;//nếu dem>0 thì ko chuyển card.

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


        v = (TextView) (contentView.findViewById(R.id.tv_newword));
        cv = (CardView) (contentView.findViewById(R.id.card));
       RadioGroup radioGroup = (RadioGroup) contentView.findViewById(R.id.rad_group);
         /*RadioButton radioButton1= (RadioButton) radioGroup.findViewById(R.id.rad_ans_a);
        RadioButton radioButton2= (RadioButton) radioGroup.findViewById(R.id.rad_ans_b);
        RadioButton radioButton3= (RadioButton) radioGroup.findViewById(R.id.rad_ans_c);
        RadioButton radioButton4= (RadioButton) radioGroup.findViewById(R.id.rad_ans_d);*/
        chuyenCard = false;
        demCheckWrong=0;
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
        for (int in = 0; in < 4; in++) {
            RadioButton rad = new RadioButton(activity);
            rad.setTextColor(Color.WHITE);
            rad.setTextSize(16);
            rad.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            if (posTrue == in) {
                rad.setText(vocabularyTrue.getMeaning());
                rad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
                            chuyenCard = true;
                            demCheckWrong++;
                            Toast.makeText(getContext(), "ChuyenCard=true", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {

                rad.setText(ansFalse[k]);
                rad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
                            chuyenCard = false;
                            demCheckWrong = 0;
                            Toast.makeText(getContext(), "ChuyenCard=false", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                k++;
            }
            radioGroup.addView(rad);
        }

        if (demCheckWrong == 0) {
            chuyenCard = false;
        }

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