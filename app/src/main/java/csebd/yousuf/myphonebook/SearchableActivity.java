package csebd.yousuf.myphonebook;


        import java.util.ArrayList;
        import java.util.List;

        import android.annotation.TargetApi;
        import android.app.Activity;
        import android.app.SearchManager;
        import android.app.SearchableInfo;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.drawable.ColorDrawable;
        import android.os.Build;
        import android.os.Bundle;
        import android.support.v7.app.ActionBar;
        import android.support.v7.app.ActionBarActivity;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.Window;
        import android.widget.AdapterView;
        import android.widget.ListView;
        import android.widget.SearchView;
        import android.widget.TextView;

public class SearchableActivity extends ActionBarActivity {

    private SearchView searchView;
    private TextView tvSearchContactNotMatch;
    private ListView lvSearchContact;
    private DbAdapter dbAdapter;
    private PhonebookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFF3763CA));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.search);

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.menu_search, menu);

        // Add SearchWidget.
//        SearchManager searchManager = (SearchManager) getSystemService( Context.SEARCH_SERVICE );
//        SearchView searchView = (SearchView) menu.findItem( R.id.m_search_search ).getActionView();
//
//        searchView.setSearchableInfo( searchManager.getSearchableInfo( getComponentName() ) );

        return super.onCreateOptionsMenu( menu );
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void init() {
        dbAdapter = new DbAdapter(getApplicationContext());

        lvSearchContact = (ListView) findViewById(R.id.lvSearchContact);
        tvSearchContactNotMatch = (TextView) findViewById(R.id.tvSearchContactNotMatch);

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.onActionViewExpanded(); // expand search view

        // now not working
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    lvSearchContact.setVisibility(View.INVISIBLE);

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                ArrayList<Phonebook> searchedBookList = dbAdapter.getSearchPhonebookList(query.trim()); // searched contact total item
                if (searchedBookList.size() == 0 ) {
                    tvSearchContactNotMatch.setVisibility(View.VISIBLE);
                    lvSearchContact.setVisibility(View.GONE);
                } else {
                    lvSearchContact.setVisibility(View.VISIBLE);
                    tvSearchContactNotMatch.setVisibility(View.GONE);

                    adapter = new PhonebookAdapter(getApplicationContext(), 0, searchedBookList);
                    lvSearchContact.setAdapter(adapter);
                    lvSearchContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Phonebook phonebook = adapter.getItem(position);
                            startActivity(new Intent(getApplicationContext(), DetailActivity.class).putExtra("id", phonebook.getId()));
                            finish();
                        }
                    });
                    T.t(getApplication(), "Total Contacts : " + searchedBookList.size());
//                    T.t(getApplication(), query);
                }



                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });



    }

    @Override
    protected void onPause() {
        super.onPause();
        dbAdapter.close();
    }
}