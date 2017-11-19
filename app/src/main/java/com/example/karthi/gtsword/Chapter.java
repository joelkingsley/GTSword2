package com.example.karthi.gtsword;

/**
 * Created by Karthi on 11-Sep-17.
 */
import java.util.List;

public class Chapter {
    String chapterNum;
    List<Verse> verses;
    Chapter(String c ,List<Verse> v){
        chapterNum = c;
        verses = v;
    }

    public String getChapterNum() {
        return chapterNum;
    }

    public void setChapterNum(String chapterNum) {
        this.chapterNum = chapterNum;
    }

    public List<Verse> getVerses() {
        return verses;
    }

    public void setVerses(List<Verse> verses) {
        this.verses = verses;
    }
}