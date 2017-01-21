package dieuninh.com.learnvocabulary.learnvocabulary.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DieuLinh on 12/30/2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="VocabularyDB";
    private static final String TABLE_NAME = "VocabularyTable";

    private static final String COL_NEWWORD = "NewWord";
    private static final String COL_MEAN = "MeanNW";
    private static final String COL_ID = "ID";

    public SQLiteDatabase db;
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_VOCABULARY_TABLE="CREATE TABLE  IF NOT EXISTS "
                +TABLE_NAME+
                "("+COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COL_NEWWORD+" TEXT ,"+
                COL_MEAN+" TEXT"+")";
        db.execSQL(CREATE_VOCABULARY_TABLE);
        this.db=db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        this.onCreate(db);
    }
    public int getVocabularyCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        //cursor.close();
        return cursor.getCount();
    }
    public List<Vocabulary> getAllVocabulary() {
        List<Vocabulary> list = new ArrayList<Vocabulary>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Vocabulary vo = new Vocabulary();
                vo.setId(Integer.parseInt(cursor.getString(0)));
                vo.setNewWord(cursor.getString(1));
                vo.setMeaning(cursor.getString(2));

                list.add(vo);

            } while (cursor.moveToNext());
        }
        return list;
    }


    public boolean checkForTables(){

        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " +TABLE_NAME, null);

        if(cursor != null){

            cursor.moveToFirst();

            int count = cursor.getInt(0);

            if(count > 0){
                return true;//ko rỗng: True
            }

            cursor.close();
        }

        return false;//rỗng: false
    }


    public void deleteAllData()
    {
        db=this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.close();
    }

    public void addData(int id,String new_word, String mean_new_word)
    {
        db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COL_ID,id);
        cv.put(COL_NEWWORD,new_word);
        cv.put(COL_MEAN,mean_new_word);
        db.insert(TABLE_NAME,null,cv);
        db.close();
    }

    public boolean addVocabulary(int id, String new_word, String mean_new_word)
    {
        db=this.getWritableDatabase();
        boolean res=true;
        ContentValues cv=new ContentValues();
        cv.put(COL_ID,id);
        cv.put(COL_NEWWORD,new_word);
        cv.put(COL_MEAN,mean_new_word);
        long r= db.insert(TABLE_NAME,null,cv);
        if(r==-1)res=false;
        db.close();
        return res;
    }


}
