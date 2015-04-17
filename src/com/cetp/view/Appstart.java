package com.cetp.view;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.cetp.R;
import com.cetp.action.AppVariable;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class Appstart extends Activity {
	private static final String TAG = "SavingState";
	final String INITIALIZED = "initialized";
	final String LOGINTIMES = "logintimes";
	final String CANLOGIN = "canLogin";
	final String DEFAULTVIEW = "defaultview";
	private int times;
	// 界面
	private final static int MAIN_LISTENING_VIEW = 0;
	private final static int MAIN_READING_VIEW = 1;
	private final static int MAIN_CLOZING_VIEW = 2;
	private final static int MAIN_VOCABULARY_VIEW = 3;

	// private Handler mHandler = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN); //全屏显示
		// Toast.makeText(getApplicationContext(), "孩子！好好背诵！",
		// Toast.LENGTH_LONG).show();
		// overridePendingTransition(R.anim.hyperspace_in,
		// R.anim.hyperspace_out);
		setContentView(R.layout.appstart);
		/** 用于保存用户状态 */
		final SharedPreferences myPrefs = getPreferences(MODE_PRIVATE);
		final boolean hasPreferences = myPrefs.getBoolean(INITIALIZED, false);
		final boolean canLogin = myPrefs.getBoolean(CANLOGIN, false);
		final int defaultview = myPrefs.getInt(DEFAULTVIEW, 0);
		if (hasPreferences) {
			Log.v(TAG, "We've been called before");
			times = myPrefs.getInt(LOGINTIMES, 1) + 1;
		} else {
			Log.v(TAG, "First time ever being called");
			times = 1;
		}
		Log.v(TAG, "logintimes = " + times);
		// Later when ready to write out values
		Editor editor = myPrefs.edit();
		editor.putBoolean(INITIALIZED, true);
		editor.putInt(LOGINTIMES, times);
		// Write other values as desired
		editor.commit();
		Log.v(TAG, String.valueOf(canLogin));

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent;

				if (hasPreferences) {
					 if (canLogin) {
					//if(false){
						// 如果canLogin标记为true，直接进入主界面
						// intent = new Intent(Appstart.this, CETMain.class);
						// startActivity(intent);
						if (defaultview == MAIN_LISTENING_VIEW) {
							AppVariable.Common.QUESTION_FILENAME = "Lis_";
							AppVariable.Common.TypeOfView = MAIN_LISTENING_VIEW;
						} else if (defaultview == MAIN_READING_VIEW) {
							AppVariable.Common.QUESTION_FILENAME = "Rea_";
							AppVariable.Common.TypeOfView = MAIN_READING_VIEW;
						} else if (defaultview == MAIN_CLOZING_VIEW) {
							AppVariable.Common.QUESTION_FILENAME = "Clo_";
							AppVariable.Common.TypeOfView = MAIN_CLOZING_VIEW;
						}
						if (AppVariable.Common.CetX == 4)
							AppVariable.Common.QUESTION_FILENAME = AppVariable.Common.QUESTION_FILENAME
									+ "CET4_";
						else if (AppVariable.Common.CetX == 6)
							AppVariable.Common.QUESTION_FILENAME = AppVariable.Common.QUESTION_FILENAME
									+ "CET6_";
						AppVariable.Common.YearMonth = "201406";
						intent = new Intent(Appstart.this, MainTab.class);
						// **********窗体切换动画*********
						int version = Integer
								.valueOf(android.os.Build.VERSION.SDK);
						if (version >= 5) {
							overridePendingTransition(R.anim.zoomin,
									R.anim.zoomout);
						}
						// ***************************

					} else {
						// 如果canLogin标记为false，进入欢迎界面
						 intent = new Intent(Appstart.this, Welcome.class);
						 System.out.println("appstart->welcome");
//						intent = new Intent(Appstart.this, MainTab.class);
					}
				} else{
					// intent = new Intent(Appstart.this, Viewpager.class);
					intent = new Intent(Appstart.this, Welcome.class);
					System.out.println("appstart->2");
				}
				startActivity(intent);
				AppVariable.Common.loaded = true;
				Appstart.this.finish();
			}
		}, 1000);

		// new MyThread().start();
	}

	class MyThread extends Thread {
		public void run() {
			// 检测新版本
			// Intent intent = new Intent(Appstart.this, UpdateActivity.class);
			// startActivity(intent);
		}
	}

	public void guanggao(View v) { // 忘记密码按钮
		Uri uri = Uri.parse("http://www.guanghezhang.com");
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}
}