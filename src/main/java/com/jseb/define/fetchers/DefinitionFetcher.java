package com.jseb.define.fetchers;

import android.os.AsyncTask;

import com.jseb.define.DefinitionList;
import com.jseb.define.DictionaryAPI;
import com.jseb.define.objects.Definition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Tyler Sebastian on 11/2/13.
 */
public class DefinitionFetcher extends AsyncTask<String, Void, ArrayList<Definition>> {
	private DefinitionList mContext;
	private final String base_url = "http://api.wordnik.com/v4/word.json/";
	private final String definitions_url = "/definitions";

	public DefinitionFetcher(DefinitionList context) {
		this.mContext = context;
	}

	public ArrayList<Definition> doInBackground(String... strings) {
		ArrayList<Definition> definitions = new ArrayList<Definition>();

		try {
			URL url = new URL(base_url + strings[0] + definitions_url + "?api_key=" + DictionaryAPI.DICTIONARY_KEY + "&useCanonical=true");
			URLConnection connection = url.openConnection();

			JSONArray response = new JSONArray(new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine());

			for (int i = 0; i < response.length(); i++) {
				JSONObject definition = response.getJSONObject(i);

				definitions.add(new Definition(definition.getString("word"), definition.getString("partOfSpeech"), definition.getString("text")));
			}
		} catch (IOException e) {

		} catch (JSONException e) {

		}

		return definitions;
	}

	@Override
	protected void onPostExecute(ArrayList<Definition> result) {
		mContext.mAdapter.addItems(result);
	}
}
