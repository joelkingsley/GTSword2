package com.example.karthi.gtsword;

/**
 * Created by Karthi on 15-Sep-17.
 */

public class Verse {
    String verse;
    String verseNum;

    Verse(){
        this.verse = null;
        this.verseNum = null;
    }

    Verse(String verseNum, String v){
        verse = v; this.verseNum = verseNum;
    }

    public String getVerse() {
        return verse;
    }

    public void setVerse(String verse) {
        this.verse = verse;
    }

    public String getVerseNum() {
        return verseNum;
    }

    public void setVerseNum(String verseNum) {
        this.verseNum = verseNum;
    }

    @Override
    public String toString() {
        String text = "[" + verseNum + "]" + verse;
        return text;
    }
}
