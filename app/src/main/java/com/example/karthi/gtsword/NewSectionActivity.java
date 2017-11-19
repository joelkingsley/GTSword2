package com.example.karthi.gtsword;

/**
 * Created by Karthi on 11-Sep-17.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewSectionActivity extends AppCompatActivity {

    int chunkSize = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_section);
        addItemsOnBookNameSpinner();
    }

    private Spinner spinner1,spinner2;
    private NumberPicker numberPicker1,numberPicker2;

    String[] bookItems = new String[]{"Genesis", "Exodus", "Leviticus", "Numbers", "Deuteronomy", "Joshua",
            "Judges", "Ruth", "1 Samuel", "2 Samuel", "1 Kings", "2 Kings", "1 Chronicles",
            "2 Chronicles", "Ezra", "Nehemiah", "Esther", "Job", "Psalms", "Proverbs", "Ecclesiastes",
            "Song of Solomon", "Isaiah", "Jeremiah", "Lamentations", "Ezekiel", "Daniel", "Hosea",
            "Joel", "Amos", "Obadiah", "Jonah", "Micah", "Nahum", "Habakkuk", "Zephaniah", "Haggai",
            "Zechariah", "Malachi", "Matthew", "Mark", "Luke", "John", "Acts", "Romans",
            "1 Corinthians", "2 Corinthians", "Galatians", "Ephesians", "Philippians", "Colossians",
            "1 Thessalonians", "2 Thessalonians", "1 Timothy", "2 Timothy", "Titus", "Philemon",
            "Hebrews", "James", "1 Peter", "2 Peter", "1 John", "2 John", "3 John", "Jude", "Revelation"};

    Integer numOfChap[] = {50,40,27,36,34,24,21,4,31,24,22,25,29,36,10,13,10,42,150,31,12,8,66,52,5,48,
            12,14,3,9,1,4,7,3,3,3,2,14,4,28,16,24,21,28,16,16,13,6,6,4,4,5,3,6,4,3,1,13,5,5,3,5,1,1,1,22};


    public void addItemsOnBookNameSpinner(){
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> StringAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,bookItems);
        StringAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(StringAdapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int bookItemPos = position;
                addItemsOnChapterNumSpinner(bookItemPos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void addItemsOnChapterNumSpinner(final int bookItemPos){
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<Integer> chapList = new ArrayList<Integer>();
        for(int i=1;i<=numOfChap[bookItemPos];i++){
            chapList.add(i);
        }
        ArrayAdapter<Integer> IntegerAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,chapList);
        IntegerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(IntegerAdapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int chapNum = position+1;
                setStartVerseNumPicker(bookItemPos,chapNum);
                setEndVerseNumPicker(bookItemPos,chapNum,1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setStartVerseNumPicker(int bookItemPos,int chapNum){
        numberPicker1 = (NumberPicker) findViewById(R.id.numberPicker1);
        DBHelper db = new DBHelper(this);
        int numOfVerse = db.getNumOfVerse(bookItems[bookItemPos],chapNum);
        db.close();
        numberPicker1.setMinValue(1);
        numberPicker1.setMaxValue(numOfVerse);
        numberPicker1.setWrapSelectorWheel(true);
        setEndVerseNumPicker(bookItemPos,chapNum,1);
        numberPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                setEndVerseNumPicker(spinner1.getSelectedItemPosition(),spinner2.getSelectedItemPosition()+1,newVal);
            }
        });
    }

    public void setEndVerseNumPicker(int bookItemPos,int chapNum,int startVerse){
        numberPicker2 = (NumberPicker) findViewById(R.id.numberPicker2);
        DBHelper db = new DBHelper(this);
        int numOfVerse = db.getNumOfVerse(bookItems[bookItemPos],chapNum);
        db.close();
        numberPicker2.setMinValue(startVerse);
        numberPicker2.setMaxValue(numOfVerse);
        numberPicker2.setWrapSelectorWheel(true);
    }

    public void submit(View view){
        int secId;
        String bookName = spinner1.getSelectedItem().toString();
        int chapNum = Integer.parseInt(spinner2.getSelectedItem().toString());
        int startVerse = numberPicker1.getValue();
        int endVerse = numberPicker2.getValue();
        Toast.makeText(NewSectionActivity.this, "Added " + bookName + " " + chapNum + ":" + startVerse + "-" + endVerse,
                Toast.LENGTH_LONG).show();
        DBHelper db = new DBHelper(this);

        if(db.getMaxSecId()==-1){
            secId = 1;
        }
        else{
            secId = db.getMaxSecId() + 1;
        }

        Chunk chunk = new Chunk(1,bookName,chapNum,startVerse,endVerse,"",1,secId,false);
        db.addSection(chunk);
        Chunkizer cr = new Chunkizer();
        List<Chunk> chunkList = cr.Chunkize(chunk,chunkSize);
        for(int i=0;i<chunkList.size();i++){
            Log.d("Add Sub Chunk:","SID=" + chunkList.get(i).getSecId() + " " + chunkList.get(i).toString() + " " + chunkList.get(i).getNextDateOfReview());
            db.addChunk(chunkList.get(i));
        }
        Intent intent = new Intent(NewSectionActivity.this,MainActivity.class);
        startActivity(intent);
    }
}