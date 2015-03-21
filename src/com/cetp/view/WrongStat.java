package com.cetp.view;

import com.cetp.R;
import com.cetp.R.layout;
import com.cetp.R.menu;
import com.cetp.database.DBWrongStat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;

public class WrongStat extends Activity {
	final String TAG = "WrongStat";
	private static final int MSG_DATA_CHANGE = 0x11;
	private LineView mLineView;
	private Handler mHandler;
	private int mX = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wrong_stat);
		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
				ActionBar.DISPLAY_HOME_AS_UP);
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		final int width = metric.widthPixels; // 屏幕宽度（像素）
		final int height = metric.heightPixels; // 屏幕高度（像素）

		Log.v(TAG + "_onCreate", "分辨率：" + height + "x" + width);

		DBWrongStat db = new DBWrongStat(this);
		db.open();
		Cursor cur = db.getAllItem();
		// 记录的数目
		int dataCount = cur.getCount();

		String[] x = new String[] { "", "", "", "", "", "", "" };
		String[] data = new String[] { "", "", "", "", "", "", "" };
		if (dataCount > 0) {
			cur.move(dataCount > 7 ? dataCount - 7 : 0);

			Log.v(TAG, "dataCount=" + String.valueOf(dataCount));
			int number = 0;
			while (cur.moveToNext()) {
				String t = cur.getString(cur.getColumnIndex("YYYYMMDDHHMMSS"));
				if (t.length() > 4)
					x[number] = t.substring(0, 2) + "-" + t.substring(2, 4);
				else
					x[number] = t;
				Log.v(TAG, "x[" + String.valueOf(number) + "]=" + x[number]);
				data[number] = cur.getString(cur.getColumnIndex("WrongStat"));
				Log.v(TAG, "data[" + String.valueOf(number) + "]="
						+ data[number]);
				number++;
			}
		}
		mLineView = (LineView) this.findViewById(R.id.line);

		mLineView.SetInfo(x,
				new String[] { "0", "20", "40", "60", "80", "100" }, // Y轴刻度
				data, "错误率");
		cur.close();
		db.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wrong, menu);
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
