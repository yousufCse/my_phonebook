package csebd.yousuf.myphonebook;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity  {
    private ListView lvPhonebookItem;
    private TextView tvMainNoRecordFound;

    private DbAdapter dbAdapter;
    private ArrayList<Phonebook> allPhonebookList;
    public static final int ADD_REQ_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFF3763CA));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.p2);

        lvPhonebookItem = (ListView) findViewById(R.id.lvPhonebookItem);
        lvPhonebookItem.setSmoothScrollbarEnabled(true);
        lvPhonebookItem.setFastScrollEnabled(false);
        lvPhonebookItem.setFriction(ViewConfiguration.getScrollFriction() * 500);
        dbAdapter = new DbAdapter(getApplicationContext());

//        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);




        init();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
//        MenuItem menuItem = menu.findItem(R.id.m_add_search);
////        getMenuInflater().inflate(R.menu.menu_main, menu);
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        android.widget.SearchView searchView = (android.widget.SearchView) MenuItemCompat.getActionView(menuItem);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.m_add_contact) {
            Intent i = new Intent(getApplicationContext(), AddContactActivity.class);
//            startActivityForResult(i, ADD_REQ_CODE );
            startActivity(i);
            return true;
        }

        if (id == R.id.m_add_search) {
            startActivity(new Intent(getApplicationContext(), SearchableActivity.class));
            return true;
        }

        if (id == R.id.m_about_us) {
            startActivity(new Intent(this, AboutUs.class));
        }

        return super.onOptionsItemSelected(item);
    }

    // initialization
    private void init() {
        allPhonebookList = dbAdapter.getAllPhonebook();

        if (allPhonebookList.size() == 0 ) {
            tvMainNoRecordFound = (TextView) findViewById(R.id.tvMainNoRecordFound);
            tvMainNoRecordFound.setVisibility(View.VISIBLE);
        }

        final PhonebookAdapter adapter = new PhonebookAdapter(getApplicationContext(),0, allPhonebookList);
        lvPhonebookItem.setAdapter(adapter);

        lvPhonebookItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Phonebook phonebook = adapter.getItem(position);
//                T.d(phonebook.getId() + "");
                Intent i = new Intent(getApplicationContext(), DetailActivity.class);
//                i.putExtra("id", phonebook.getId()+"");
                i.putExtra("id", phonebook.getId());
                startActivity(i);
            }
        });
        // long click
        lvPhonebookItem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Phonebook phonebook = adapter.getItem(position);
                String phoneNo = phonebook.getPhone();
                Intent call = new Intent(Intent.ACTION_CALL);
//                T.d(phoneNo);  // check phone no
                call.setData(Uri.parse("tel:"+ phoneNo));
                startActivity(call);
                return true;
            }
        });
    }

    // activity result, just test
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MainActivity.ADD_REQ_CODE && resultCode == RESULT_OK) {
            String s = data.getStringExtra("msg");
//            T.d("Result is: "+s);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }
}
