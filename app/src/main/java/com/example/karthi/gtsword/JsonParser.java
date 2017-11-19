package com.example.karthi.gtsword;

/**
 * Created by Karthi on 11-Sep-17.
 */

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class JsonParser {
    public Book readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readBookObject(reader);
        } finally {
            reader.close();
        }
    }

    private Book readBookObject(JsonReader reader) throws IOException {
        Book book;

        book = (Book) new Book(readBook(reader));

        return book;
    }

    private Book readBook(JsonReader reader) {
        String BookName = null;
        List<Chapter> l = null;
        try{
            reader.beginObject();

            while(reader.hasNext()){
                String name = reader.nextName();
                if(name.equals("book")){
                    BookName = reader.nextString();
                }
                else if(name.equals("chapters")){
                    l = readChapters(reader);
                }
                else{
                    reader.skipValue();
                }
            }

            reader.endObject();



        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Book(BookName, l);

    }

    private List<Chapter> readChapters(JsonReader reader) {
        List<Chapter> l = new ArrayList<Chapter>();

        try {
            reader.beginArray();

            while(reader.hasNext()){

                l.add(readChapter(reader));

            }

            reader.endArray();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return l;
    }

    private Chapter readChapter(JsonReader reader) {
        String ChapNum = null;
        List<Verse> l = null;

        try {
            reader.beginObject();

            while(reader.hasNext()){

                String name = reader.nextName();
                if(name.equals("chapter")){
                    ChapNum = reader.nextString();
                }
                else if(name.equals("verses")){
                    l = readVerses(reader);
                }
                else{
                    reader.skipValue();
                }
            }

            reader.endObject();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Chapter(ChapNum,l);
    }

    private List<Verse> readVerses(JsonReader reader) {
        List<Verse> l = new ArrayList<Verse>();
        try {
            reader.beginArray();

            while(reader.hasNext()){
                l.add(readVerse(reader));
            }

            reader.endArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return l;

    }

    private Verse readVerse(JsonReader reader) {
        String VerseNum = null;
        String verse = null;
        try {
            reader.beginObject();

            while(reader.hasNext()){
                String name = reader.nextName();
                VerseNum = name;
                verse = reader.nextString();
            }

            reader.endObject();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Verse(VerseNum,verse);
    }


}