package com.example.karthi.gtsword;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ChaptersActivity extends AppCompatActivity {

    Integer numOfChap[] = {50,40,27,36,34,24,21,4,31,24,22,25,29,36,10,13,10,42,150,31,12,8,66,52,5,48,
            12,14,3,9,1,4,7,3,3,3,2,14,4,28,16,24,21,28,16,16,13,6,6,4,4,5,3,6,4,3,1,13,5,5,3,5,1,1,1,22};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters);

        final int bookNo = getIntent().getIntExtra("BOOK_NO",-1);
        List<String> chapterList = new ArrayList<String>();

        for(int i=1;i<=numOfChap[bookNo];i++){
            chapterList.add("Chapter " + i);
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_list,
                chapterList);
        ListView listViewToday = (ListView) findViewById(R.id.chaptersList);
        listViewToday.setAdapter(adapter);
        listViewToday.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChaptersActivity.this,DisplayActivity.class);
                intent.putExtra("BOOK_NO",bookNo);
                intent.putExtra("CHAPTER_NO", position+1);
                startActivity(intent);

            }
        });
    }
}
