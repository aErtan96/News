package com.dertsizvebugsiz.news;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.dertsizvebugsiz.news.dataclasses.News;
import com.dertsizvebugsiz.news.enums.Vote;

import java.util.LinkedHashMap;

public class SqliteConnector extends SQLiteOpenHelper {

    private final static String dbName = "bcnews";
    private final static int dbVersion = 1;

    private static SqliteConnector instance;

    public static SqliteConnector getInstance(Context c){
        if(instance == null){
            instance = new SqliteConnector(c, dbName, null, dbVersion);
        }
        return instance;
    }

    private SqliteConnector(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE bookmarks " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "news_id INTEGER, " +
                        "title TEXT, " +
                        "publish_time_str TEXT, " +
                        "site_id INTEGER, " +
                        "link TEXT)"
        );
        db.execSQL(
                "CREATE TABLE feedbacks " +
                        "(news_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "vote INTEGER)"
        );
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public LinkedHashMap<Integer, News> getAllBookmarks(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT news_id, title, publish_time_str, site_id, link FROM bookmarks ORDER BY id DESC", null);

        LinkedHashMap<Integer, News> newsMap = new LinkedHashMap<>();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            News news = new News();
            news.newsId = c.getInt(0);
            news.title = c.getString(1);
            news.publishDateStr = c.getString(2);
            news.siteId = c.getInt(3);
            news.link = c.getString(4);
            newsMap.put(news.newsId, news);
        }
        c.close();
        return newsMap;
    }

    public void addBookmark(News news){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("news_id", news.newsId);
        cv.put("title", news.title);
        cv.put("publish_time_str", news.publishDateStr);
        cv.put("site_id", news.siteId);
        cv.put("link", news.link);

        db.insert("bookmarks",null, cv);
    }

    public void deleteBookmark(int newsId){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("bookmarks", "news_id=?", new String[]{String.valueOf(newsId)});
    }




    public Vote getVoteOfNews(int newsId){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT vote FROM feedbacks WHERE news_id = " + newsId, null);

        Vote v;
        if(c.getCount() == 0){
            v = Vote.NONE;
        }
        else{
            c.moveToFirst();
            v = Vote.parseEnum(c.getInt(0));
        }

        c.close();
        return v;
    }

    public void setVoteOfNews(int newsId, Vote vote){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT vote FROM feedbacks WHERE news_id = " + newsId, null);

        boolean feedbackExists = c.getCount() != 0;
        c.close();

        if(feedbackExists){
            ContentValues cv = new ContentValues();
            cv.put("vote", Vote.parseInt(vote));
            db.update("feedbacks", cv, "news_id=?", new String[]{ String.valueOf(newsId) });
        }
        else{
            ContentValues cv = new ContentValues();
            cv.put("news_id", newsId);
            cv.put("vote", Vote.parseInt(vote));
            db.insert("feedbacks", null, cv);
        }

    }

}
