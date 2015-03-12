package com.cetp.view;

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

		mLineView = (LineView) this.findViewById(R.id.line);

		mLineView.SetInfo(new String[] { "7-11", "7-12", "7-13", "7-14",
				"7-15", "7-16", "7-17" }, // X轴刻度
				new String[] { "0", "20", "40", "60", "80", "100" }, // Y轴刻度
				new String[] { "15", "23", "10", "36", "45", "40", "12" }, // 数据
				"错误率");


//		mHandler = new Handler() {
//			@Override
//			public void handleMessage(Message msg) {
//				// TODO Auto-generated method stub
//				switch (msg.what) {
//				case MSG_DATA_CHANGE:
//					mLineView.setLinePoint(msg.arg1, msg.arg2);
//					break;
//
//				default:
//					break;
//				}
//				super.handleMessage(msg);
//			}
//		};
//
//		new Thread() {
//			public void run() {
//				for (int index = 0; index < 20; index++) {
//					Message message = new Message();
//					if (mX < width - 100) {
//						message.what = MSG_DATA_CHANGE;
//						message.arg1 = mX;
//						message.arg2 = (int) (Math.random() * 200);
//						/** Log *************************************/
//						String msg = String.valueOf(mX);
//						Log.v(TAG, msg);
//						/********************************************/
//						mHandler.sendMessage(message);
//						// interrupt();
//					} else {
//						this.interrupt();
//						Log.v(TAG, "interrupt");
//					}
//					try {
//						sleep(1000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//
//					mX += 30;
//				}
//			};
//		}.start();
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
