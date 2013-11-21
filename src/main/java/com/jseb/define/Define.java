package com.jseb.define;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jseb.define.objects.Definition;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Define extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.define_layout);

		Intent intent = getIntent();
		if (intent.getAction().equals(Intent.ACTION_SEND) && intent.getType() != null) handleIntent(intent);


		((EditText) findViewById(R.id.define_card_input)).setOnKeyListener(new View.OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
					getDefinition();
				return true;
			}
		});

		((TextView) findViewById(R.id.define_card_button)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				getDefinition();
			}
		});

		getWordOfDay();
    }

	public void handleIntent(Intent intent) {
		if (intent.getType().equals("text/plain")) getDefinition(intent.getStringExtra(Intent.EXTRA_TEXT));
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.define, menu);
        return true;
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	public void getWordOfDay() {
		new AsyncTask<Void, Void, Definition>() {
			String base_url = "http://api.wordnik.com/v4/words.json/wordOfTheDay";

			public Definition doInBackground(Void... voids) {
				try {
					URL url = new URL(base_url + "?api_key=" + DictionaryAPI.DICTIONARY_KEY);
					URLConnection connection = url.openConnection();

					JSONObject response = new JSONObject(new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine());
					return new Definition(response.getString("word"), response.getJSONArray("definitions").getJSONObject(0).getString("partOfSpeech"), response.getJSONArray("definitions").getJSONObject(0).getString("text"));
				} catch (IOException e) {

				} catch (JSONException e) {

				}

				return null;
			}

			@Override
			protected void onPostExecute(Definition result) {
				((TextView) findViewById(R.id.word)).setText(result.word);
				((TextView) findViewById(R.id.type)).setText(result.type);
				((TextView) findViewById(R.id.definition)).setText(result.definition);
				findViewById(R.id.word_of_day_container).setVisibility(View.VISIBLE);
			}
		}.execute();
	}

	public void getDefinition() {
		getDefinition(((TextView) findViewById(R.id.define_card_input)).getText().toString());
	}

	public void getDefinition(String word) {
		Intent intent = new Intent(this, DefinitionList.class);
		intent.putExtra("WORD", word);
		startActivity(intent);
	}
}
