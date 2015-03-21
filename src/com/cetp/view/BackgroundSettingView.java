package com.cetp.view;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.cetp.R;
import com.cetp.action.SkinSettingManager;

public class BackgroundSettingView extends Activity {
	private SkinSettingManager mSettingManager;

	// private RadioButton radioButton1;
	// private RadioButton radioButton2;
	// private RadioButton radioButton3;
	// private RadioButton radioButton4;
	// private RadioButton radioButton5;
	private Button b;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ȡ��������
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ��ɴ����ȫ����ʾ // ȡ����״̬��
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.backgroundsettingview);
		// ��ʼ��Ƥ��
		mSettingManager = new SkinSettingManager(this);
		mSettingManager.initSkins();

		// ͨ����ѡ��ť����Ƥ��(���Զ�������ķ�ʽ���絼������Ҳ���Լ���Ԥ�����ܣ��˴�����ʵ��)
		// radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
		// radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
		// radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
		// radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
		// radioButton5 = (RadioButton) findViewById(R.id.radioButton5);
		b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(getApplicationContext(),
						ColorSelectView.class));

			}
		});
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.skin_options);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (checkedId) {
				case R.id.radioButton1:
					mSettingManager.changeSkin(1);
					break;
				case R.id.radioButton2:
					mSettingManager.changeSkin(2);
					break;
				case R.id.radioButton3:
					mSettingManager.changeSkin(3);
					break;
				case R.id.radioButton4:
					mSettingManager.changeSkin(4);
					break;
				case R.id.radioButton5:
					mSettingManager.changeSkin(5);
					break;
				default:
					break;
				}
			}
		});
//		ActionBar actionBar = this.getActionBar();
//		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
//				ActionBar.DISPLAY_HOME_AS_UP);
	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		if (item.getItemId() == android.R.id.home) {
//			finish();
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}

	// ����Ϊ�˼�ʵ�֣�ʵ�ֻ���
	public boolean onTouchEvent(MotionEvent event) {
		mSettingManager.toggleSkins();
		return super.onTouchEvent(event);
	}

}
