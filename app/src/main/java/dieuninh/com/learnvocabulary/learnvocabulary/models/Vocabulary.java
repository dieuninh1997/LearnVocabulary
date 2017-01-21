package dieuninh.com.learnvocabulary.learnvocabulary.models;

/**
 * Created by DieuLinh on 12/30/2016.
 */

public class Vocabulary {
    String newWord; // tu moi
    String meaning;// nghia
    int id;

    public Vocabulary() {
    }

    public Vocabulary(int i){
        id= i;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getNewWord() {
        return newWord;
    }

    public void setNewWord(String newWord) {
        this.newWord = newWord;
    }
}
