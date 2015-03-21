package com.cetp.view;

import com.cetp.R;
import com.cetp.R.layout;
import com.cetp.R.menu;
import com.cetp.service.PlayerService;
import com.cetp.view.ListeningViewQuestion.SeekBarChangeEvent;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class ColorSelectView extends Activity {
	SeekBar seekBarRed;
	SeekBar seekBarBlue;
	SeekBar seekBarGreen;
	LinearLayout lin;
	String RED = "red";
	String GREEN = "green";
	String BLUE = "blue";
	int int_red, int_blue, int_green;
	SharedPreferences myPrefs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_color_select_view);
		myPrefs = getPreferences(MODE_PRIVATE);
		findView();
		setListener();
		init();
		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
				ActionBar.DISPLAY_HOME_AS_UP);
	}

	void findView() {
		seekBarRed = (SeekBar) findViewById(R.id.seekBar_red);
		seekBarBlue = (SeekBar) findViewById(R.id.seekBar_blue);
		seekBarGreen = (SeekBar) findViewById(R.id.seekBar_green);
		lin = (LinearLayout) findViewById(R.id.lin_color);
	}

	void setListener() {
		seekBarRed.setOnSeekBarChangeListener(new SeekBarChangeEvent());
		seekBarBlue.setOnSeekBarChangeListener(new SeekBarChangeEvent());
		seekBarGreen.setOnSeekBarChangeListener(new SeekBarChangeEvent());
	}

	void init() {

		int_red = myPrefs.getInt(RED, 0);
		int_green = myPrefs.getInt(GREEN, 0);
		int_blue = myPrefs.getInt(BLUE, 0);
		seekBarRed.setProgress(int_red);
		seekBarGreen.setProgress(int_green);
		seekBarBlue.setProgress(int_blue);
		lin.setBackgroundColor(Color.rgb(int_red, int_green, int_blue));
	}

	/* 拖放进度监听 ，别忘了Service里面还有个进度条刷新 */
	class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			Editor editor = myPrefs.edit();

			if (seekBar == seekBarRed) {
				int_red = seekBarRed.getProgress();
				editor.putInt(RED, int_red);
			} else if (seekBar == seekBarGreen) {
				int_green = seekBarGreen.getProgress();
				editor.putInt(GREEN, int_green);
			} else if (seekBar == seekBarBlue) {
				int_blue = seekBarBlue.getProgress();
				editor.putInt(BLUE, int_blue);
			}
			editor.commit();
			lin.setBackgroundColor(Color.rgb(int_red, int_green, int_blue));
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.color_select_view, menu);
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
