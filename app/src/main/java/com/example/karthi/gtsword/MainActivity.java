package com.example.karthi.gtsword;

import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    FloatingActionButton fab,fab_add,fab_motivate,fab_about,fab_delete,fab_bible;
    TextView tv_add,tv_motivate,tv_about,tv_delete,tv_bible;
    boolean fab_status=false;
    private Toolbar toolbar;

    String fileNames[] = {"Genesis", "Exodus", "Leviticus", "Numbers", "Deuteronomy",
            "Joshua", "Judges", "Ruth", "1Samuel", "2Samuel", "1Kings", "2Kings",
            "1Chronicles", "2Chronicles", "Ezra", "Nehemiah", "Esther", "Job", "Psalms",
            "Proverbs", "Ecclesiastes", "SongofSolomon", "Isaiah", "Jeremiah",
            "Lamentations", "Ezekiel", "Daniel", "Hosea", "Joel", "Amos", "Obadiah",
            "Jonah", "Micah", "Nahum", "Habakkuk", "Zephaniah", "Haggai", "Zechariah",
            "Malachi", "Matthew", "Mark", "Luke", "John", "Acts", "Romans", "1Corinthians",
            "2Corinthians", "Galatians", "Ephesians", "Philippians", "Colossians",
            "1Thessalonians", "2Thessalonians", "1Timothy", "2Timothy", "Titus", "Philemon",
            "Hebrews", "James", "1Peter", "2Peter", "1John", "2John", "3John", "Jude",
            "Revelation"};

    List<String> StringArrayToday = new ArrayList<String>();
    List<String> StringArrayOverdue = new ArrayList<String>();
    List<String> StringArrayAll = new ArrayList<String>();
    List<Chunk> chunks = null;
    List<Chunk> chunksToday = new ArrayList<Chunk>();
    List<Chunk> chunksOverdue = new ArrayList<Chunk>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //int decode = Integer.decode("0xFF16CDE6");
        /*
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.show();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFF16CDE6));
        actionBar.setTitle("GirdThySword");
        */

        //this.deleteDatabase("sword");
        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true)) {
            //the app is being launched for first time, do something
            Log.d("Comments", "First time");

            // first time task
            //parseThemAll();

            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).commit();
        }
        else{

        }
        setupTab();
        setupFab();


        NavigationView sidenavigation = (NavigationView) findViewById(R.id.sidenavig);

    }

    @Override
    protected void onResume() {
        super.onResume();
        StringArrayToday = new ArrayList<String>();
        StringArrayOverdue = new ArrayList<String>();
        StringArrayAll = new ArrayList<String>();
        chunks = null;
        chunksToday = new ArrayList<Chunk>();
        chunksOverdue = new ArrayList<Chunk>();
        setupListView();
    }

    //final FragmentManager fragmentManager = getSupportFragmentManager();

        //final Fragment fragment1 = new HomeFragment();
        //final Fragment fragment2 = new BibleFragment();
        //final Fragment fragment3 = new RewardsFragment();
        //final Fragment fragment4 = new ProfileFragment();

        //BottomNavigationView bottomNaviagtionView=(BottomNavigationView) findViewById(R.id.bottomnavig);
/*        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) { switch(item.getItemId())
               {
                   case R.id.home:

                       FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
                       fragmentTransaction1.replace(R.id.flContainer, fragment1).commit();

                       break;
                   case R.id.addsection:

                       FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
                       fragmentTransaction2.replace(R.id.flContainer, fragment2).commit();

                       break;
                   case R.id.rewards:
                       /*
                       FragmentTransaction fragmentTransaction3 = fragmentManager.beginTransaction();
                       fragmentTransaction3.replace(R.id.flContainer, fragment3).commit();

                       return true;
                   case R.id.profile:
                       /*
                       FragmentTransaction fragmentTransaction4 = fragmentManager.beginTransaction();
                       fragmentTransaction4.replace(R.id.flContainer, fragment4).commit();

                       return true;
               }
               return true;
            }
        };

        toolbar=(Toolbar) findViewById(R.id.nav_drawer_action);
        setSupportActionBar(toolbar);


        drawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

     // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomnavig);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
*/

    private void setupFab(){
        fab = (FloatingActionButton) findViewById(R.id.fab) ;
        fab_about=(FloatingActionButton) findViewById(R.id.fab_about);
        fab_add=(FloatingActionButton) findViewById(R.id.fab_add);
        fab_motivate=(FloatingActionButton) findViewById(R.id.fab_motivate);
        fab_delete = (FloatingActionButton) findViewById(R.id.fab_delete);
        fab_bible = (FloatingActionButton) findViewById(R.id.fab_bible);
        tv_about = (TextView) findViewById(R.id.tv_about);
        tv_add = (TextView) findViewById(R.id.tv_add);
        tv_motivate = (TextView) findViewById(R.id.tv_motivate);
        tv_delete = (TextView) findViewById(R.id.tv_delete);
        tv_bible = (TextView) findViewById(R.id.tv_bible);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MainActivity.this,NewSectionActivity.class);
                startActivity(intent1);
            }
        });

        fab_motivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MainActivity.this,MotivationActivity.class);
                startActivity(intent1);
            }
        });

        fab_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent1);
            }
        });

        fab_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MainActivity.this,DeleteSectionActivity.class);
                startActivity(intent1);
            }
        });

        fab_bible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MainActivity.this,BibleActivity.class);
                startActivity(intent1);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fab_status == false){
                    fab_show();
                    fab_status=true;
                }else {
                    fab_hide();
                    fab_status=false;
                }
            }
        });
    }
    private void parseThemAll() {
        JsonParser jp = new JsonParser();
        DBHelper db = new DBHelper(this);

        for(int k=0;k<66;k++) {
            InputStream in = null;
            try {
                in = getAssets().open(fileNames[k]+".json");
                Book genesis = jp.readJsonStream(in);
                for (int i = 0; i < genesis.chapters.size(); i++) {
                    for (int j = 0; j < genesis.chapters.get(i).verses.size(); j++) {
                        //result = result + genesis.chapters.get(i).verses.get(j).getVerse() + "\n";
                        db.addVerse(genesis.bookName,i+1,j+1,genesis.chapters.get(i).verses.get(j).getVerse());
                        //String text = db.getVerse(genesis.bookName, i + 1, j + 1);
                        //result = "Doing";
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }



// Side Navigation Drawer
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(toggle.onOptionsItemSelected(item)){
            switch(item.getItemId())
            {
                case R.id.home:

                    return true;
                case R.id.addsection:
                    Intent intent = new Intent(MainActivity.this,NewSectionActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.motivation:

                    return true;
                case R.id.rewards:

                    return true;
                case R.id.profile:

                    return true;
                case R.id.share:

                    return true;
                case R.id.about:

                    return true;
            }
            return true;

        }

        return super.onOptionsItemSelected(item);

        //NavigationView sidenavigation = (NavigationView) findViewById(R.id.sidenavig);
        //sidenavigation.

    }



    private void setupListView() {
        try{
            DBHelper dbHandler = new DBHelper(this);
            //dbHandler.deleteAllChunks();
            //Log.d("Insert: ", "Inserting ..");

            //dbHandler.addChunk(new Chunk(1, 1, "Titus", 1, 1, 6, "03/10/2017", 1,3,true));
            /*
            dbHandler.addChunk(new Chunk(2, 1, "Exodus", 1, 4, 6, "06/09/2017", 1));
            dbHandler.addChunk(new Chunk(3,1, "John",3,16,18, "07/09/2017", 2));
            dbHandler.addChunk(new Chunk(4,2, "Romans",4,5,7, "09/09/2017", 4));
            dbHandler.addChunk(new Chunk(5,1, "Romans",1,1,3, "07/09/2017", 1));
            */
            chunks = dbHandler.getAllChunks();

            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Calendar ca = Calendar.getInstance();
            String currDate = df.format(ca.getTime());

            for (Chunk c : chunks) {
                Log.d("Reading: ", c.toString());
                StringArrayAll.add(c.toString());
                if(!c.getNextDateOfReview().equals("NA")){
                    Date dateObj = df.parse(c.getNextDateOfReview());
                    Date currDateObj = df.parse(currDate);
                    if(currDateObj.equals(dateObj)){
                        StringArrayToday.add(c.toString());
                        chunksToday.add(c);
                    }
                    else if(dateObj.before(currDateObj)){
                        StringArrayOverdue.add(c.toString());
                        chunksOverdue.add(c);
                    }
                }
            }

            //ArrayAdapter adapterToday = new ArrayAdapter<String>(this, R.layout.activity_list, StringArrayToday);
            CustomList2Adapter adapterToday = new CustomList2Adapter(this,chunksToday);

            ListView listViewToday = (ListView) findViewById(R.id.today_listView);
            listViewToday.setAdapter(adapterToday);
            listViewToday.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this,ReviewActivity.class);
                    intent.putExtra("EXTRA_CHUNK_ID", chunksToday.get(position).get_id());
                    startActivity(intent);

                }
            });
            //ArrayAdapter adapterOverdue = new ArrayAdapter<String>(this, R.layout.activity_list, StringArrayOverdue);
            CustomList2Adapter adapterOverdue = new CustomList2Adapter(this,chunksOverdue);

            ListView listViewOverdue = (ListView) findViewById(R.id.overdue_listView);
            listViewOverdue.setAdapter(adapterOverdue);
            listViewOverdue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this,ReviewActivity.class);
                    intent.putExtra("EXTRA_CHUNK_ID", chunksOverdue.get(position).get_id());
                    startActivity(intent);
                }
            });



            CustomListAdapter adapterAll = new CustomListAdapter(this, chunks);
            ListView listViewAll = (ListView) findViewById(R.id.all_listView);
            listViewAll.setAdapter(adapterAll);
            listViewAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this,ChunkDescActivity.class);
                    intent.putExtra("EXTRA_CHUNK_ID", chunks.get(position).get_id());
                    startActivity(intent);
                }
            });

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private void setupTab() {
        try {
            TabHost tabHost = (TabHost) findViewById(R.id.tabhost); // initiate TabHost
            TabHost.TabSpec spec; // Reusable TabSpec for each tab
            tabHost.setup();
            spec = tabHost.newTabSpec("Today"); // Create a new TabSpec using tab host
            spec.setIndicator("TODAY"); // set the “HOME” as an indicator
            // Create an Intent to launch an Activity for the tab (to be reused)
            //spec.setContent(new Intent(this,TodayActivity.class));
            spec.setContent(R.id.tab1);
            tabHost.addTab(spec);
            //tabHost.addTab(tabHost.newTabSpec("today").setIndicator(getResources().getString(R.string.today)).setContent(intent));
            // Do the same for the other tabs

            spec = tabHost.newTabSpec("Overdue"); // Create a new TabSpec using tab host
            spec.setIndicator("OVERDUE"); // set the “CONTACT” as an indicator
            // Create an Intent to launch an Activity for the tab (to be reused)
            //spec.setContent(new Intent(this,OverdueActivity.class));
            spec.setContent(R.id.tab2);
            tabHost.addTab(spec);

            spec = tabHost.newTabSpec("All"); // Create a new TabSpec using tab host
            spec.setIndicator("ALL"); // set the “ABOUT” as an indicator
            // Create an Intent to launch an Activity for the tab (to be reused)
            //spec.setContent(new Intent(this,AllActivity.class));
            spec.setContent(R.id.tab3);
            tabHost.addTab(spec);

            //set tab which one you want to open first time 0 or 1 or 2
            tabHost.setCurrentTab(0);
            tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
                @Override
                public void onTabChanged(String tabId) {
                    // display the name of the tab whenever a tab is changed
                    //Toast.makeText(getApplicationContext(), tabId, Toast.LENGTH_SHORT).show();

                    switch(tabId){
                        case "Today":
                            break;
                        case "Overdue":
                            break;
                        case "All":
                            break;
                        default:
                    }
                }
            });
        }
        catch(Exception e){
            Log.e("MYAPP", "exception", e);
        }
    }

    private void copyDataBase()
    {
        ContextWrapper cw =new ContextWrapper(getApplicationContext());
        String DB_PATH =cw.getFilesDir().getAbsolutePath()+ "/databases/"; //edited to databases
        String DB_NAME = "example.db";
        Log.i("Database","New database is being copied to device!");
        byte[] buffer = new byte[1024];
        OutputStream myOutput = null;
        int length;
        // Open your local db as the input stream
        InputStream myInput = null;
        try
        {
            myInput = cw.getAssets().open(DB_NAME);
            // transfer bytes from the inputfile to the
            // outputfile
            myOutput =new FileOutputStream(DB_PATH+ DB_NAME);
            while((length = myInput.read(buffer)) > 0)
            {
                myOutput.write(buffer, 0, length);
            }
            myOutput.close();
            myOutput.flush();
            myInput.close();
            Log.i("Database",
                    "New database has been copied to device!");


        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private void fab_show(){
        fab_motivate.show();
        fab_add.show();
        fab_about.show();
        fab_delete.show();
        fab_bible.show();

        tv_about.setVisibility(View.VISIBLE);
        tv_add.setVisibility(View.VISIBLE);
        tv_motivate.setVisibility(View.VISIBLE);
        tv_delete.setVisibility(View.VISIBLE);
        tv_bible.setVisibility(View.VISIBLE);

    }
    private void fab_hide(){
        fab_motivate.hide();
        fab_add.hide();
        fab_about.hide();
        fab_delete.hide();
        fab_bible.hide();

        tv_about.setVisibility(View.INVISIBLE);
        tv_add.setVisibility(View.INVISIBLE);
        tv_motivate.setVisibility(View.INVISIBLE);
        tv_delete.setVisibility(View.INVISIBLE);
        tv_bible.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
    }


}
//hi 