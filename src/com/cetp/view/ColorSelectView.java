package com.cetp.view;

import com.cetp.R;
import com.cetp.action.SkinSettingManager;
import com.cetp.service.PlayerService;
import com.cetp.view.ListeningViewQuestion.SeekBarChangeEvent;

import android.R.color;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

public class ColorSelectView extends Activity {
	String TAG = "ColorSelectView";
	SeekBar skb_red;
	SeekBar skb_green;
	SeekBar skb_blue;
	RelativeLayout rlt_bg;
	int i_red, i_green, i_blue;

	String BGRED = "bgred";
	String BGGREEN = "bggreen";
	String BGBLUE = "bgblue";
	private SkinSettingManager mSettingManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_color_select_view);
		// ≥ı ºªØ∆§∑Ù
		mSettingManager = new SkinSettingManager(this);
		mSettingManager.initSkins();

		skb_red = (SeekBar) findViewById(R.id.skr_red);
		skb_green = (SeekBar) findViewById(R.id.skr_green);
		skb_blue = (SeekBar) findViewById(R.id.skr_blue);
		rlt_bg = (RelativeLayout) findViewById(R.id.rlt_bg);

		skb_red.setOnSeekBarChangeListener(new SeekBarChangeEvent());
		skb_green.setOnSeekBarChangeListener(new SeekBarChangeEvent());
		skb_blue.setOnSeekBarChangeListener(new SeekBarChangeEvent());

		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
				ActionBar.DISPLAY_HOME_AS_UP);
		init();
	}

	void init() {
		// final SharedPreferences myPrefs = getPreferences(MODE_PRIVATE);
		// i_red = myPrefs.getInt(BGRED, 0);
		// i_green = myPrefs.getInt(BGGREEN, 0);
		// i_blue = myPrefs.getInt(BGBLUE, 0);

		i_red = Color.red(mSettingManager.getBGColor());
		i_green = Color.green(mSettingManager.getBGColor());
		i_blue = Color.blue(mSettingManager.getBGColor());

		rlt_bg.setBackgroundColor(Color.rgb(i_red, i_green, i_blue));

		skb_red.setProgress(i_red);
		skb_green.setProgress(i_green);
		skb_blue.setProgress(i_blue);
	}

	class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			final SharedPreferences myPrefs = getPreferences(MODE_PRIVATE);
			Editor editor = myPrefs.edit();
			if (seekBar == skb_red) {
				i_red = progress;
				editor.putInt(BGRED, i_red);
			} else if (seekBar == skb_green) {
				i_green = progress;
				editor.putInt(BGGREEN, i_green);
			} else if (seekBar == skb_blue) {
				i_blue = progress;
				editor.putInt(BGBLUE, i_blue);
			}
			editor.commit();

			Log.v(TAG, "Color=" + Color.rgb(i_red, i_green, i_blue));
			mSettingManager.setBGColor(Color.rgb(i_red, i_green, i_blue));
			rlt_bg.setBackgroundColor(Color.rgb(i_red, i_green, i_blue));
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {

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

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}
}
