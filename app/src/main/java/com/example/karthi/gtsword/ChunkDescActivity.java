package com.example.karthi.gtsword;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChunkDescActivity extends AppCompatActivity {

    long id;
    Chunk c=null;
    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    List<String> verseWithoutNum = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chunk_desc);

        tv1 = (TextView) findViewById(R.id.textView16);
        tv2 = (TextView) findViewById(R.id.textView22);
        tv3 = (TextView) findViewById(R.id.textView23);
        tv4 = (TextView) findViewById(R.id.textView24);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DBHelper dbHelper = new DBHelper(this);
        id = getIntent().getLongExtra("EXTRA_CHUNK_ID",-1);
        c = dbHelper.getChunk(id);

        tv1.setText(c.toString());
        List<String> verseList = new ArrayList<String>();
        for(int i=c.getStartVerseNum();i<=c.getEndVerseNum();i++){
            String temp = dbHelper.getVerse(c.getBookName(),c.getChapNum(),i);
            verseWithoutNum.add(temp);
            verseList.add("[" + i + "]" + temp);
        }
        StringBuilder builder = new StringBuilder();



        for(String verse : verseList){
            builder.append(verse + "\n");

        }
        tv2.setText(builder.toString());

        String desc = "Description\n";
        desc += "No. of Reviews: " + 0 + "\n";

        tv3.setText(desc);

        if(dbHelper.checkIfMasteredChunk(id)){
            tv4.setText("Retained");
            tv4.setTextColor(Color.GREEN);
        }
        else{
            tv4.setText("Memorizing");
            tv4.setTextColor(Color.RED);
        }

    }
}
