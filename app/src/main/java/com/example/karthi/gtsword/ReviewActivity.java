package com.example.karthi.gtsword;

/**
 * Created by Karthi on 10-Sep-17.
 */


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ReviewActivity extends AppCompatActivity {
    Chunk c=null;
    long id=-1;
    TextToSpeech t1;
    TextView tv1;
    TextView tv;
    FloatingActionButton fab_speech;
    List<String> verseWithoutNum = new ArrayList<String>();
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        tv1 = (TextView) findViewById(R.id.textView);
        tv = (TextView) findViewById(R.id.parsed_text);
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    t1.setLanguage(Locale.UK);
            }
        }});
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
         fab_speech=(FloatingActionButton)findViewById(R.id.fab_speech);
        fab_speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder builder = new StringBuilder();

                for(String verse : verseWithoutNum){
                    builder.append(verse + "\n");

                }
                String toSpeak = tv.getText().toString();
                Toast.makeText(getApplicationContext(),"Reading..", Toast.LENGTH_SHORT);
                t1.speak(builder.toString(),TextToSpeech.QUEUE_FLUSH,null);
            }
        });

    }

    @Override
    protected void onPause() {
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        DBHelper dbHandler = new DBHelper(this);

        id = getIntent().getLongExtra("EXTRA_CHUNK_ID",-1);
        c = dbHandler.getChunk(id);
        Log.d("Chunk:","Get Chunk " + c.get_id());


        tv1.setText(c.toString());

        List<String> verseList = new ArrayList<String>();
        for(int i=c.getStartVerseNum();i<=c.getEndVerseNum();i++){
            String temp = dbHandler.getVerse(c.getBookName(),c.getChapNum(),i);
            verseWithoutNum.add(temp);
            verseList.add("[" + i + "]" + temp);
        }
        StringBuilder builder = new StringBuilder();



        for(String verse : verseList){
            builder.append(verse + "\n");

        }
        tv.setText(builder.toString());

        //tv.setText(dbHandler.getVerse("Genesis",1,1));
        Button gotit = (Button) findViewById(R.id.button6);
        Button mastered = (Button) findViewById(R.id.button7);
        Button wrong = (Button) findViewById(R.id.button8);

        gotit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGotIt(c);
                Log.d("Click:","Clicked " + "Got it in " + c.get_id());
            }
        });

        mastered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickMastered(c);
            }
        });

        wrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickWrong(c);
            }
        });
    }

    public void onClickWrong(Chunk c){
        Log.d("Function:","onClickWrong " + c.get_id());
        int space = c.getSpace();
        if(space>1){
            c.setSpace(space/2);
        }
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE,c.getSpace());
        String currDate = df.format(ca.getTime());
        c.setNextDateOfReview(currDate);
        DBHelper db = new DBHelper(this);
        db.updateChunk(c, false);
        Intent intent = new Intent(ReviewActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void onClickGotIt(Chunk c){
        Log.d("Function:","onClickGotIt " + c.get_id());
        int space = c.getSpace();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE,space);
        String currDate = df.format(ca.getTime());
        c.setNextDateOfReview(currDate);
        DBHelper db = new DBHelper(this);

        db.updateChunk(c, false);

        db.updateSiblingChunks(c);

        Intent intent = new Intent(ReviewActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void onClickMastered(Chunk c){
        Log.d("Function:","onClickMastered " + c.get_id());
        int space = c.getSpace();
        c.setSpace(space*2);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE,c.getSpace());
        String currDate = df.format(ca.getTime());
        c.setNextDateOfReview(currDate);
        DBHelper db = new DBHelper(this);

        db.updateChunk(c,true);

        db.updateSiblingChunks(c);

        if(db.checkIfMasteredSection(c.getSecId()) && c.getSeq()!=1){
            db.mergeChunksInSection(c.getSecId());
            Section s = db.retSection(c.getSecId());
            Toast.makeText(ReviewActivity.this, "Merged Section " + s.toString(),
                    Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent(ReviewActivity.this,MainActivity.class);
        startActivity(intent);
    }
}