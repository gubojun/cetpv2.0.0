package com.cetp.view;

import java.util.Iterator;
import java.util.List;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.Select;
import net.tsz.afinal.annotation.view.ViewInject;

import com.cetp.R;
import com.cetp.R.layout;
import com.cetp.R.menu;
import com.cetp.action.SkinSettingManager;
import com.cetp.question.QuestionWrongStat;

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

	private SkinSettingManager mSettingManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wrong_stat);

		// 初始化皮肤
		mSettingManager = new SkinSettingManager(this);
		mSettingManager.initSkins();

		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
				ActionBar.DISPLAY_HOME_AS_UP);
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		final int width = metric.widthPixels; // 屏幕宽度（像素）
		final int height = metric.heightPixels; // 屏幕高度（像素）

		Log.v(TAG + "_onCreate", "分辨率：" + height + "x" + width);

		// mLineView = (LineView) this.findViewById(R.id.line);

		String[] x = new String[7];
		String[] data = new String[7];
		int num;
		FinalDb dbWrong = FinalDb.create(this, "cetp");
		List<QuestionWrongStat> qList = dbWrong
				.findAll(QuestionWrongStat.class);
		num = qList != null ? qList.size() : 0;
		List<QuestionWrongStat> q = qList.subList(num >= 7 ? num - 7 : 0, num);
		Iterator<QuestionWrongStat> i = q.iterator();
		int c = 0;
		while (i.hasNext()) {
			QuestionWrongStat t = (QuestionWrongStat) i.next();
			String s = t.getYYYYMMDDHHMMSS();
			x[c] = s.substring(4, 6) + "-" + s.substring(6, 8);
			data[c++] = t.getWrongStat();
		}
		mLineView.SetInfo(x, // X轴刻度
				new String[] { "0", "20", "40", "60", "80", "100" }, // Y轴刻度
				data, // 数据
				"错误率");
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

	@Override
	protected void onResume() {
		mSettingManager.initSkins();
		super.onResume();
	}
}
