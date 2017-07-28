package dieuninh.com.learnvocabulary.learnvocabulary.application;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

import java.util.List;

import dieuninh.com.learnvocabulary.learnvocabulary.models.Vocabulary;

/**
 * Created by DieuLinh on 12/30/2016.
 */

public class AppController extends Application{
    static AppController instance;
    List<Vocabulary> listVocabularies;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Utils.init(this);
    }

    public static synchronized AppController getInstance() {
        if (instance == null) instance = new AppController();
        return instance;
    }

    public final List<Vocabulary>  getListVocabularies() {

        return listVocabularies;
    }

    public void setListVocabularies(List<Vocabulary> listVocabularies) {
        this.listVocabularies = listVocabularies;
    }
}
