package bie.com.yuekao.modle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created 朱治文lenovo on 2018/6/1
 * .
 */

public class MyHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "news.db";
    public static final String NEWS_TABLE_NAME = "news";
    private static final int VERSION = 1;
    public MyHelper(Context context) {

        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + NEWS_TABLE_NAME + " (_id Integer PRIMARY KEY ,json text)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
