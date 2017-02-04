package dieuninh.com.learnvocabulary.learnvocabulary.models;


import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class MySharedPreference {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private Context context;

    private int PRIVATE_MODE=0;

    private static final String PREF_NAME="pref";
    private static final String VOCABULARY="vocabulary";

    public MySharedPreference(Context context)
    {
        this.context=context;
        pref=context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor=pref.edit();
    }

    public void saveVocaList(String voca)
    {
        editor.putString(VOCABULARY,voca);
        editor.commit();
    }
    public String getVocaList()
    {
        return pref.getString(VOCABULARY,"");
    }
    public void removeVocaList()
    {
        editor.clear().commit();



    }
}

