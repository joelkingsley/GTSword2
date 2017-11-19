package com.example.karthi.gtsword;

import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

public class DisplayActivity extends AppCompatActivity {

    StringBuilder textToSpeak = new StringBuilder();
    FloatingActionButton fab_speech;
    TextToSpeech t1;

    String[] bookItems = new String[]{"Genesis", "Exodus", "Leviticus", "Numbers", "Deuteronomy", "Joshua",
            "Judges", "Ruth", "1 Samuel", "2 Samuel", "1 Kings", "2 Kings", "1 Chronicles",
            "2 Chronicles", "Ezra", "Nehemiah", "Esther", "Job", "Psalms", "Proverbs", "Ecclesiastes",
            "Song of Solomon", "Isaiah", "Jeremiah", "Lamentations", "Ezekiel", "Daniel", "Hosea",
            "Joel", "Amos", "Obadiah", "Jonah", "Micah", "Nahum", "Habakkuk", "Zephaniah", "Haggai",
            "Zechariah", "Malachi", "Matthew", "Mark", "Luke", "John", "Acts", "Romans",
            "1 Corinthians", "2 Corinthians", "Galatians", "Ephesians", "Philippians", "Colossians",
            "1 Thessalonians", "2 Thessalonians", "1 Timothy", "2 Timothy", "Titus", "Philemon",
            "Hebrews", "James", "1 Peter", "2 Peter", "1 John", "2 John", "3 John", "Jude", "Revelation"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        int chapNo = getIntent().getIntExtra("CHAPTER_NO",-1);
        int BookNo = getIntent().getIntExtra("BOOK_NO",-1);

        DBHelper db = new DBHelper(this);
        final List<String> verses = db.getChapter(bookItems[BookNo],chapNo);

        TextView tv = (TextView) findViewById(R.id.chapterText);

        StringBuilder builder = new StringBuilder();

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    t1.setLanguage(Locale.UK);
                }
            }});
        for(int i=0;i<verses.size();i++){
            builder.append(i+1 + " " + verses.get(i).toString() + "\n\n");
            //textToSpeak.append(verses.get(i).toString() + "\n");
        }
        fab_speech=(FloatingActionButton)findViewById(R.id.fab_speech2);
        fab_speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(),"Reading..", Toast.LENGTH_SHORT);
                //t1.speak(textToSpeak.toString(), TextToSpeech.QUEUE_FLUSH,null);
                for(int i=0;i<verses.size();i++){
                    t1.speak(verses.get(i).toString(), TextToSpeech.QUEUE_ADD,null);
                }
            }
        });
        tv.setText(builder.toString());
    }

    @Override
    protected void onPause() {
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }
}
