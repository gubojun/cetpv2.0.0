package com.cetp.view;

import com.cetp.R;
import com.cetp.R.layout;
import com.cetp.R.menu;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

public class EverydayView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_everyday_view);
		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
				ActionBar.DISPLAY_HOME_AS_UP);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.everyday_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
