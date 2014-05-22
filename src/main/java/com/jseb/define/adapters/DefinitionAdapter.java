package com.jseb.define.adapters;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jseb.define.R;
import com.jseb.define.objects.Definition;

import java.util.ArrayList;

/**
 * Created by Tyler Sebastian on 11/2/13.
 */
public class DefinitionAdapter extends BaseAdapter {
	ArrayList<Definition> mDefinitions;
	Context mContext;

	public DefinitionAdapter(Context context) {
		this.mContext = context;
		this.mDefinitions = new ArrayList<Definition>();
	}

	public void addItems(ArrayList<Definition> newItems) {
		mDefinitions.addAll(newItems);
		notifyDataSetChanged();
	}

	public void clear() {
		mDefinitions.clear();
		notifyDataSetChanged();
	}

	@Override
	public long getItemId(int i) {
		return 0;
	}

	@Override
	public Definition getItem(int i) {
		return this.mDefinitions.get(i);
	}

	@Override
	public int getCount() {
		return this.mDefinitions.size();
	}

	@Override
	public View getView(final int i, View view, ViewGroup viewGroup) {
		View mView = LayoutInflater.from(mContext).inflate(R.layout.definition_card, null);
		((TextView) mView.findViewById(R.id.word)).setText(getItem(i).word);
		((TextView) mView.findViewById(R.id.type)).setText(getItem(i).type);
		((TextView) mView.findViewById(R.id.definition)).setText(getItem(i).definition);

		mView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				View mView = LayoutInflater.from(mContext).inflate(R.layout.definition_options, null);
				((TextView) mView.findViewById(R.id.definition)).setText(getItem(i).definition);

				new AlertDialog.Builder(mContext).setNegativeButton("word", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						((ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("word", getItem(i).word));
						Toast.makeText(mContext, "copied word to clipboard", Toast.LENGTH_LONG).show();
					}
				}).setPositiveButton("definition", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						((ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("definition", getItem(i).definition));
						Toast.makeText(mContext, "copied definition to clipboard", Toast.LENGTH_LONG).show();
					}
				}).setView(mView).create().show();
			}
		});

		return mView;
	}
}
