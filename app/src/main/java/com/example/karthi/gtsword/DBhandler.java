package com.example.karthi.gtsword;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DBhandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "sword";

    private static final String TABLE_BIBLE = "bible";
    private static final String TABLE_CHUNK = "chunk";

    public static final String B_KEY_ID = "id";
    public static final String B_KEY_BOOK_NAME = "book_name";
    public static final String B_KEY_CHAP_NUM = "chapter_num";
    public static final String B_KEY_VERSE_NUM = "verse_num";
    public static final String B_KEY_VERSE_TEXT = "verse_text";

    public static final String C_KEY_ID = "id";
    public static final String C_KEY_SEQ = "seq";
    public static final String C_KEY_BOOK_NAME = "book_name";
    public static final String C_KEY_CHAP_NUM = "chapter_num";
    public static final String C_KEY_START_VERSE_NUM = "start_verse_num";
    public static final String C_KEY_END_VERSE_NUM = "end_verse_num";
    public static final String C_KEY_NEXT_DATE_OF_REVIEW = "next_date_of_review";
    public static final String C_KEY_SPACE = "space";

    public DBhandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBhandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Function:","Creating Table Bible");
        String CREATE_BIBLE_TABLE = "CREATE TABLE " + TABLE_BIBLE + "("
                + B_KEY_ID + "INTEGER PRIMARY KEY,"
                + B_KEY_BOOK_NAME + " TEXT, "
                + B_KEY_CHAP_NUM + " INTEGER, "
                + B_KEY_VERSE_NUM + " INTEGER, "
                + B_KEY_VERSE_TEXT + " TEXT" + ")";
        db.execSQL(CREATE_BIBLE_TABLE);

        Log.d("Function:","Creating Table Chunk");
        String CREATE_CHUNK_TABLE = "CREATE TABLE " + TABLE_CHUNK + "("
                + C_KEY_ID + " INTEGER PRIMARY KEY,"
                + C_KEY_SEQ + " INTEGER, "
                + C_KEY_BOOK_NAME + " TEXT, "
                + C_KEY_CHAP_NUM + " INTEGER, "
                + C_KEY_START_VERSE_NUM + " INTEGER, "
                + C_KEY_END_VERSE_NUM + " INTEGER, "
                + C_KEY_NEXT_DATE_OF_REVIEW + " TEXT, "
                + C_KEY_SPACE + " INTEGER" + ")";
        db.execSQL(CREATE_CHUNK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BIBLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHUNK);
        onCreate(db);
    }

    public void addVerse(String bookName, int chapNum, int verseNum,String verse){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(B_KEY_BOOK_NAME,bookName);
        values.put(B_KEY_CHAP_NUM,chapNum);
        values.put(B_KEY_VERSE_NUM,verseNum);
        values.put(B_KEY_VERSE_TEXT,verse);

        db.insert(TABLE_BIBLE,null,values);
        db.close();
        Log.d("Function:","Add Verse");

    }

    void addChunk(Chunk chunk){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(C_KEY_SEQ,chunk.getSeq());
        values.put(C_KEY_BOOK_NAME,chunk.getBookName());
        values.put(C_KEY_CHAP_NUM,chunk.getChapNum());
        values.put(C_KEY_START_VERSE_NUM,chunk.getStartVerseNum());
        values.put(C_KEY_END_VERSE_NUM,chunk.getEndVerseNum());
        values.put(C_KEY_NEXT_DATE_OF_REVIEW,chunk.getNextDateOfReview());
        values.put(C_KEY_SPACE,chunk.getSpace());

        db.insert(TABLE_CHUNK,null,values);
        db.close();
        Log.d("Function:","Add Chunk");

    }

    public List<Chunk> getAllChunks() {
        List<Chunk> chunkList = new ArrayList<Chunk>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CHUNK;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Chunk chunk = new Chunk();
                chunk.set_id(Integer.parseInt(cursor.getString(0)));
                chunk.setSeq(Integer.parseInt(cursor.getString(1)));
                chunk.setBookName(cursor.getString(2));
                chunk.setChapNum(Integer.parseInt(cursor.getString(3)));
                chunk.setStartVerseNum(Integer.parseInt(cursor.getString(4)));
                chunk.setEndVerseNum(Integer.parseInt(cursor.getString(5)));
                chunk.setNextDateOfReview(cursor.getString(6));
                chunk.setSpace(Integer.parseInt(cursor.getString(7)));
                // Adding contact to list
                chunkList.add(chunk);
            } while (cursor.moveToNext());
        }
        Log.d("Function:","Get All Chunks");
        // return contact list
        return chunkList;
    }

    public List<String> getBookNames(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + B_KEY_BOOK_NAME + " FROM " + TABLE_BIBLE;
        Cursor cursor = db.rawQuery(query,null);
        List<String> bookNames = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                bookNames.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        Log.d("Function:","getBookNames");
        return bookNames;
    }

    public String getVerse(String bookName, int chapNum, int verseNum) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + B_KEY_VERSE_TEXT + " FROM " + TABLE_BIBLE + " WHERE "
                + B_KEY_BOOK_NAME + " LIKE " + '"' + bookName + '"' + " AND " + B_KEY_CHAP_NUM + " = " + chapNum
                + " AND " + B_KEY_VERSE_NUM + " = " + verseNum;
        Cursor cursor = db.rawQuery(query,null);
        String verse="No data";
        if(cursor.moveToFirst()){
            do{
                verse = cursor.getString(0);
            }while(cursor.moveToNext());
        }
        Log.d("Function:","getVerse");
        return verse;
    }

    public Chunk getChunk(long id){
        Chunk chunk = new Chunk();
        String selectQuery = "SELECT * FROM " + TABLE_CHUNK + " WHERE " + C_KEY_ID + " = " + id + ";";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            chunk.set_id(Long.parseLong(cursor.getString(0)));
            chunk.setSeq(Integer.parseInt(cursor.getString(1)));
            chunk.setBookName(cursor.getString(2));
            chunk.setChapNum(Integer.parseInt(cursor.getString(3)));
            chunk.setStartVerseNum(Integer.parseInt(cursor.getString(4)));
            chunk.setEndVerseNum(Integer.parseInt(cursor.getString(5)));
            chunk.setNextDateOfReview(cursor.getString(6));
            chunk.setSpace(Integer.parseInt(cursor.getString(7)));
        }
        Log.d("Function:","Get Chunk");
        return chunk;
    }

    public void deleteAllChunks(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CHUNK,null,null);
        Log.d("Function:","Delete All Chunks");

    }

    public int getNumOfVerse(String bookName, int chapNum){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_BIBLE + " WHERE "
                + B_KEY_BOOK_NAME + " LIKE " + '"' + bookName + '"' + " AND " + B_KEY_CHAP_NUM + " = " + chapNum;
        Cursor cursor = db.rawQuery(query,null);
        int count = cursor.getCount();
        return count;
    }

    public int getChunksCount(){
        String countQuery = "SELECT * FROM " + TABLE_CHUNK;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        Log.d("Function:","Get Chunks Count");
        return cursor.getCount();
    }
}