package com.jseb.define;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.jseb.define.adapters.DefinitionAdapter;
import com.jseb.define.fetchers.DefinitionFetcher;

/**
 * Created by Tyler Sebastian on 11/19/13.
 */
public class DefinitionList extends Activity {
	public DefinitionAdapter mAdapter;
	private ListView mListView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.definition_list_layout);

		this.mAdapter = new DefinitionAdapter(this);
		this.mListView = (ListView) findViewById(R.id.definition_list);
		View blank = getLayoutInflater().inflate(R.layout.blank, null);
		mListView.addHeaderView(blank);
		mListView.addFooterView(blank);
		mListView.setAdapter(mAdapter);

		getActionBar().setTitle(getIntent().getStringExtra("WORD"));
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getDefinition(getIntent().getStringExtra("WORD"));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}

	public void getDefinition(String word) {
		new DefinitionFetcher(this).execute(word);
	}
}
