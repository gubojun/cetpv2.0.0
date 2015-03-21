package com.cetp.view;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.Select;
import net.tsz.afinal.annotation.view.ViewInject;

import com.cetp.R;
import com.cetp.R.layout;
import com.cetp.R.menu;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;

public class WrongStat extends FinalActivity {
	final String TAG = "WrongStat";
	private static final int MSG_DATA_CHANGE = 0x11;
	@ViewInject(id = R.id.line)
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
		final int width = metric.widthPixels; // ��Ļ���ȣ����أ�
		final int height = metric.heightPixels; // ��Ļ�߶ȣ����أ�

		Log.v(TAG + "_onCreate", "�ֱ��ʣ�" + height + "x" + width);

//		mLineView = (LineView) this.findViewById(R.id.line);

		mLineView.SetInfo(new String[] { "7-11", "7-12", "7-13", "7-14",
				"7-15", "7-16", "7-17" }, // X��̶�
				new String[] { "0", "20", "40", "60", "80", "100" }, // Y��̶�
				new String[] { "15", "23", "10", "36", "45", "40", "12" }, // ����
				"������");
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
