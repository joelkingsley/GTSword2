package com.example.karthi.gtsword;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Joel Kingsley on 22-09-2017.
 */

public class DBHelper extends SQLiteAssetHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "sword.db";

    private static final String TABLE_BIBLE = "bible";
    private static final String TABLE_CHUNK = "chunk";
    private static final String TABLE_SECTION = "section";

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
    public static final String C_KEY_SEC_ID = "sec_id";
    public static final String C_KEY_MASTERED = "mastered";

    public static final String S_KEY_ID = "id";
    public static final String S_KEY_BOOK_NAME = "book_name";
    public static final String S_KEY_CHAP_NUM = "chapter_num";
    public static final String S_KEY_START_VERSE_NUM = "start_verse_num";
    public static final String S_KEY_END_VERSE_NUM = "end_verse_num";
    public static final String S_KEY_SEC_ID = "sec_id";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

    public void addChunk(Chunk chunk){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(C_KEY_SEQ,chunk.getSeq());
        values.put(C_KEY_BOOK_NAME,chunk.getBookName());
        values.put(C_KEY_CHAP_NUM,chunk.getChapNum());
        values.put(C_KEY_START_VERSE_NUM,chunk.getStartVerseNum());
        values.put(C_KEY_END_VERSE_NUM,chunk.getEndVerseNum());
        values.put(C_KEY_NEXT_DATE_OF_REVIEW,chunk.getNextDateOfReview());
        values.put(C_KEY_SPACE,chunk.getSpace());
        values.put(C_KEY_SEC_ID,chunk.getSecId());
        values.put(C_KEY_MASTERED,chunk.isMastered());

        db.insert(TABLE_CHUNK,null,values);
        db.close();
        Log.d("Function:","Add Chunk");

    }

    public void addSection(Chunk chunk){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(S_KEY_BOOK_NAME,chunk.getBookName());
        values.put(S_KEY_CHAP_NUM,chunk.getChapNum());
        values.put(S_KEY_START_VERSE_NUM,chunk.getStartVerseNum());
        values.put(S_KEY_END_VERSE_NUM,chunk.getEndVerseNum());
        values.put(S_KEY_SEC_ID,chunk.getSecId());

        db.insert(TABLE_SECTION,null,values);
        db.close();
        Log.d("Function:","Add Section " + chunk.toString());
    }

    public List<Chunk> getAllChunks(){
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
                chunk.setSecId(Integer.parseInt(cursor.getString(8)));
                chunk.setMastered(Boolean.parseBoolean(cursor.getString(9)));

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
        db.close();
        return bookNames;
    }

    public String getVerse(String bookName, int chapNum, int verseNum) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + B_KEY_VERSE_TEXT + " FROM " + TABLE_BIBLE + " WHERE "
                + B_KEY_BOOK_NAME + " LIKE " + '"' + bookName + '"' + " AND " + B_KEY_CHAP_NUM + " = " + chapNum
                + " AND " + B_KEY_VERSE_NUM + " = " + verseNum;
        Cursor cursor = db.rawQuery(query,null);
        String verse= "";
        if(cursor.moveToFirst()){
            do{
                verse += cursor.getString(0);
            }while(cursor.moveToNext());
        }
        Log.d("Function:","getVerse");
        db.close();
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
            chunk.setSecId(Integer.parseInt(cursor.getString(8)));
            chunk.setMastered(Boolean.parseBoolean(cursor.getString(9)));
        }

        Log.d("Function:","Get Chunk");
        cursor.close();
        db.close();
        return chunk;
    }

    public List<Section> getAllSections(){
        List<Section> sections = new ArrayList<Section>();

        String selectQuery = "SELECT * FROM " + TABLE_SECTION;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                Section section = new Section();
                section.set_id(Long.parseLong(cursor.getString(0)));
                section.set_book_name(cursor.getString(1));
                section.set_chap_num(Integer.parseInt(cursor.getString(2)));
                section.set_start_verse_num(Integer.parseInt(cursor.getString(3)));
                section.set_end_verse_num(Integer.parseInt(cursor.getString(4)));
                section.set_sec_id(Integer.parseInt(cursor.getString(5)));
                sections.add(section);
                Log.d("Function:","Get Section " + section.toString());
            }while(cursor.moveToNext());

        }
        return sections;
    }

    public void deleteAllChunks(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CHUNK,null,null);
        Log.d("Function:","Delete All Chunks");

    }

    public void deleteSection(long secId){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_SECTION,S_KEY_SEC_ID + "=" + secId,null);
        db.delete(TABLE_CHUNK,C_KEY_SEC_ID + "=" + secId,null);
        Log.d("Function:","Deleted " + secId);
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
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        Log.d("Function:","Get Chunks Count");
        return count;
    }

    public void updateChunk(Chunk c, boolean b) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        Log.d("Update:","Updating Chunk " + c.toString());
        cv.put(C_KEY_BOOK_NAME,c.getBookName());
        cv.put(C_KEY_CHAP_NUM,c.getChapNum());
        cv.put(C_KEY_START_VERSE_NUM,c._start_verse_num);
        cv.put(C_KEY_END_VERSE_NUM,c.getEndVerseNum());
        cv.put(C_KEY_NEXT_DATE_OF_REVIEW,c.getNextDateOfReview());
        cv.put(C_KEY_SEQ,c.getSeq());
        cv.put(C_KEY_SPACE,c.getSpace());
        cv.put(C_KEY_SEC_ID,c.getSecId());
        if(b==true){
            cv.put(C_KEY_MASTERED,true);
        }
        else{
            cv.put(C_KEY_MASTERED,c.isMastered());
        }

        db.update(TABLE_CHUNK,cv,"id="+c.get_id(),null);
    }

    public Chunk getNextChunk(long id){
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
            chunk.setSecId(Integer.parseInt(cursor.getString(8)));
            chunk.setMastered(Boolean.parseBoolean(cursor.getString(9)));
        }

        Log.d("Function:","Get Chunk");
        cursor.close();
        return chunk;
    }

    public int getMaxSecId(){
        int maxSecId=-1;
        String selectQuery = "SELECT " + C_KEY_SEC_ID + " FROM " + TABLE_SECTION + " ORDER BY " + C_KEY_SEC_ID;
        Log.d("Select:",selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            maxSecId = Integer.parseInt(cursor.getString(0));
        }
        Log.d("MaxSecId:","MaxSecId=" + maxSecId);
        cursor.close();
        return maxSecId;
    }

    public void updateSiblingChunks(Chunk c){
        int seq = c.getSeq() + 1;
        String selectQuery = "SELECT " + C_KEY_ID + "," + C_KEY_NEXT_DATE_OF_REVIEW + " FROM " + TABLE_CHUNK + " WHERE "
                + C_KEY_SEC_ID + "=" + c.getSecId() + " AND " + C_KEY_SEQ + "=" + seq;
        Log.d("Select:",selectQuery);
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            ContentValues cv = new ContentValues();
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Calendar ca = Calendar.getInstance();
            //ca.add(Calendar.DATE,1);
            String initDOR = df.format(ca.getTime());
            Log.d("Update:","Going to update " + cursor.getString(0) + " to " + cursor.getString(1));
            if(cursor.getString(1).equals("NA")){
                cv.put(C_KEY_NEXT_DATE_OF_REVIEW,initDOR);
                cv.put(C_KEY_SPACE,1);
                db.update(TABLE_CHUNK,cv,"id="+Long.parseLong(cursor.getString(0)),null);
                Log.d("Update:","Updated " + cursor.getString(0) + " to " + initDOR);
            }
        }
        db.close();
    }

    public void mergeChunksInSection(int secId){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_CHUNK,C_KEY_SEC_ID + "=" + secId,null);
        Log.d("Function:","Deleted all chunks of " + secId);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE,1);
        String currDate = df.format(ca.getTime());

        String selectQuery = "SELECT * FROM " + TABLE_SECTION + " WHERE " + S_KEY_SEC_ID + "=" + secId;
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            addChunk(new Chunk(1,cursor.getString(1),Integer.parseInt(cursor.getString(2)),
                    Integer.parseInt(cursor.getString(3)),Integer.parseInt(cursor.getString(4)),currDate,1,
                    secId,false));
            Log.d("Function:","Added Master Chunk");
        }
        db.close();
        Log.d("Function:","Add " + secId);
    }

    public List<Integer> retSectionIds(){
        List<Integer> sections = new ArrayList<Integer>();

        SQLiteDatabase db = getWritableDatabase();
        String selectQuery = "SELECT DISTINCT " + C_KEY_SEC_ID + " FROM " + TABLE_CHUNK;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                sections.add(Integer.parseInt(cursor.getString(0)));
            }while(cursor.moveToNext());
        }
        return sections;
    }

    public Section retSection(int secId){
        Section s = new Section();
        String selectQuery = "SELECT * FROM " + TABLE_SECTION + " WHERE " + S_KEY_SEC_ID + "=" + secId;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                s.set_id(Long.parseLong(cursor.getString(0)));
                s.set_book_name(cursor.getString(1));
                s.set_chap_num(Integer.parseInt(cursor.getString(2)));
                s.set_start_verse_num(Integer.parseInt(cursor.getString(3)));
                s.set_end_verse_num(Integer.parseInt(cursor.getString(4)));
                s.set_sec_id(secId);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return s;
    }

    public boolean checkIfMasteredSection(int secId){
        boolean isMastered;
        SQLiteDatabase db = getWritableDatabase();
        String selectQuery = "SELECT " + C_KEY_MASTERED + " FROM " + TABLE_CHUNK + " WHERE " + C_KEY_SEC_ID + "=" + secId;
        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                int flag = Integer.parseInt(cursor.getString(0));
                if(flag == 0){
                    Log.d("Function:","checkIfMasteredSection False " + flag + ": " + secId);
                    return false;
                }
            }while(cursor.moveToNext());
            Log.d("Function:","checkIfMasteredSection True " + ": " + secId);
        }
        return true;
    }

    public List<String> getChapter(String bookName,int chapNo){
        List<String> chapter = new ArrayList<String>();

        SQLiteDatabase db = getWritableDatabase();
        String selectQuery = "SELECT " + B_KEY_VERSE_TEXT + " FROM " + TABLE_BIBLE + " WHERE "
                + B_KEY_BOOK_NAME + " LIKE " + '"' + bookName + '"' + " AND " + B_KEY_CHAP_NUM
                + " = " + chapNo + " ORDER BY " + B_KEY_VERSE_NUM;
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                chapter.add((cursor.getString(0)));
            }while(cursor.moveToNext());
        }
        return chapter;
    }

    public boolean checkIfMasteredChunk(long id){
        boolean isMastered;
        SQLiteDatabase db = getWritableDatabase();
        String selectQuery = "SELECT " + C_KEY_MASTERED + " FROM " + TABLE_CHUNK + " WHERE " + C_KEY_ID + "=" + id;
        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            int flag = Integer.parseInt(cursor.getString(0));
            if(flag == 0){
                Log.d("Function:","checkIfMasteredChunk False " + flag + ": " + id);
                return false;
            }
        }
        return true;
    }

    public List<Chunk> getChunksOfSection(int sec_id) {
        List<Chunk> chunks = new ArrayList<Chunk>();

        SQLiteDatabase db = getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_CHUNK + " WHERE " + C_KEY_SEC_ID + "=" + sec_id;
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                Chunk chunk = new Chunk();
                chunk.set_id(Long.parseLong(cursor.getString(0)));
                chunk.setSeq(Integer.parseInt(cursor.getString(1)));
                chunk.setBookName(cursor.getString(2));
                chunk.setChapNum(Integer.parseInt(cursor.getString(3)));
                chunk.setStartVerseNum(Integer.parseInt(cursor.getString(4)));
                chunk.setEndVerseNum(Integer.parseInt(cursor.getString(5)));
                chunk.setNextDateOfReview(cursor.getString(6));
                chunk.setSpace(Integer.parseInt(cursor.getString(7)));
                chunk.setSecId(Integer.parseInt(cursor.getString(8)));
                chunk.setMastered(Boolean.parseBoolean(cursor.getString(9)));

                chunks.add(chunk);
            }while(cursor.moveToNext());
        }
        return chunks;
    }

}
