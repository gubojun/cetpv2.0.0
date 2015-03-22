package com.cetp.view;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.cetp.R;
import com.cetp.action.SkinSettingManager;

public class BackgroundSettingView extends FinalActivity {
	private SkinSettingManager mSettingManager;
	@ViewInject(id = R.id.button1, click = "btnClick")
	Button b;

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
	}

	public void btnClick(View v) {
		startActivity(new Intent(getApplicationContext(), ColorSelectView.class));
	}

	// ����Ϊ�˼�ʵ�֣�ʵ�ֻ���
	public boolean onTouchEvent(MotionEvent event) {
		mSettingManager.toggleSkins();
		return super.onTouchEvent(event);
	}

}
