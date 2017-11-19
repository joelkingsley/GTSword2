package com.example.karthi.gtsword;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DeleteSectionActivity extends AppCompatActivity {

    List<Section> sections = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_section);


        DBHelper db = new DBHelper(this);
        sections = db.getAllSections();

        SectionListAdapter adapterAll = new SectionListAdapter(this, sections);
        ListView listViewAll = (ListView) findViewById(R.id.sectionList);
        listViewAll.setAdapter(adapterAll);

        listViewAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                deleteFromDB(sections.get(i));
                Toast.makeText(DeleteSectionActivity.this, "Deleted "
                                + sections.get(i).get_book_name() + " "
                                + sections.get(i).get_chap_num() + ":"
                                + sections.get(i).get_start_verse_num()
                                + "-" + sections.get(i).get_end_verse_num(),
                        Toast.LENGTH_LONG).show();
                setupList();
            }
        });
    }

    public void setupList(){
        DBHelper db = new DBHelper(this);
        sections = db.getAllSections();

        SectionListAdapter adapterAll = new SectionListAdapter(this, sections);
        ListView listViewAll = (ListView) findViewById(R.id.sectionList);
        listViewAll.setAdapter(adapterAll);
    }

    public void deleteFromDB(Section section){
        DBHelper db = new DBHelper(this);
        db.deleteSection(section.get_sec_id());
    }
}
