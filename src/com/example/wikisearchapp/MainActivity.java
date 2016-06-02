package com.example.wikisearchapp;

import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;

public class MainActivity extends ListActivity implements
		SearchView.OnQueryTextListener {

	private SearchView searchView;
	protected FragmentTransaction fragmentTransaction;
	protected SimpleFragment simleFragment;
	protected LinearLayout main_layout_fragment ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		loadingProgressBar = (ProgressBar) findViewById(R.id.loadingProgress);
		loadingProgressBar.setVisibility(View.GONE);
		
		main_layout_fragment = (LinearLayout) findViewById(R.id.main_layout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		MenuItem searchItem = menu.findItem(R.id.search);
		searchView = (SearchView) searchItem.getActionView();
		setupSearchView(searchItem);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void setupSearchView(MenuItem searchItem) {

		searchView.setIconifiedByDefault(false);
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchableInfo info = searchManager
				.getSearchableInfo(getComponentName());
		searchView.setSearchableInfo(info);
		searchView.setOnQueryTextListener(this);
	}

	private LoadFeedDataToList loadFeedData;
	private ImageListAdapter adapter;
	private ProgressBar loadingProgressBar;

	public ProgressBar getLoadingProgressBar() {
		return loadingProgressBar;
	}

	public void setLoadingProgressBar(ProgressBar loadingProgress) {
		this.loadingProgressBar = loadingProgress;
	}

	@Override
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub

		if (arg0.length() > 0) {
			adapter = new ImageListAdapter(this);
			setListAdapter(adapter);
			loadFeedData = new LoadFeedDataToList(this, adapter);
			loadFeedData.execute(arg0);
			main_layout_fragment.setVisibility(View.GONE);
		} else {
			adapter = new ImageListAdapter(this);
			setListAdapter(adapter);
			loadFeedData = new LoadFeedDataToList(this, adapter);
			loadFeedData.execute("");
			main_layout_fragment.setVisibility(View.VISIBLE);
		}
		
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
