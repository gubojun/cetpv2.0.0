package com.cetp.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cetp.R;
import com.cetp.action.AppVariable;
import com.cetp.action.SkinSettingManager;

public class TimeSettingView extends Activity {
	/** ʱ�����ù����� */
	// private TimeSettingManager mTimeSettingManager;
	/** ��Բ��� */
	private RelativeLayout rltTimeSettingListening;
	private RelativeLayout rltTimeSettingReading;
	private RelativeLayout rltTimeSettingClozing;
	private RelativeLayout rltTimeSettingVocabulary;
	private RelativeLayout rltTimeSettingDefault;
	/** �ı��� */
	private TextView txtTimeSettingListening;
	private TextView txtTimeSettingReading;
	private TextView txtTimeSettingClozing;
	private TextView txtTimeSettingVocabulary;
	private EditText edtTimeSettingDialog;

	private String timeOfListening;
	private String timeOfReading;
	private String timeOfClozing;
	private String timeOfVocabulary;

	private int Type = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ȡ��������
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.timesettingview);

		findView();
		setListener();
		initSetting();
	}

	private void initSetting() {
		timeOfListening = AppVariable.Time.G_TIME_LISTENING;
		timeOfReading = AppVariable.Time.G_TIME_READING;
		timeOfClozing = AppVariable.Time.G_TIME_CLOZING;
		timeOfVocabulary = AppVariable.Time.G_TIME_VOCABULARY;

		txtTimeSettingListening.setText(txtTimeSettingListening.getText()
				.subSequence(0, 4) + " " + timeOfListening + "����");
		txtTimeSettingReading.setText(txtTimeSettingReading.getText()
				.subSequence(0, 4) + " " + timeOfReading + "����");
		txtTimeSettingClozing.setText(txtTimeSettingClozing.getText()
				.subSequence(0, 4) + " " + timeOfClozing + "����");
		txtTimeSettingVocabulary.setText(txtTimeSettingVocabulary.getText()
				.subSequence(0, 4) + " " + timeOfVocabulary + "����");
	}

	private void setListener() {
		rltTimeSettingListening
				.setOnClickListener(new myRelativeLayoutOnClickListener());
		rltTimeSettingReading
				.setOnClickListener(new myRelativeLayoutOnClickListener());
		rltTimeSettingClozing
				.setOnClickListener(new myRelativeLayoutOnClickListener());
		rltTimeSettingVocabulary
				.setOnClickListener(new myRelativeLayoutOnClickListener());
		rltTimeSettingDefault
				.setOnClickListener(new myRelativeLayoutOnClickListener());
	}

	private void findView() {
		rltTimeSettingListening = (RelativeLayout) findViewById(R.id.rlt_timesetting_listening);
		rltTimeSettingReading = (RelativeLayout) findViewById(R.id.rlt_timesetting_reading);
		rltTimeSettingClozing = (RelativeLayout) findViewById(R.id.rlt_timesetting_clozing);
		rltTimeSettingVocabulary = (RelativeLayout) findViewById(R.id.rlt_timesetting_vocabulary);
		rltTimeSettingDefault = (RelativeLayout) findViewById(R.id.rlt_timesetting_default);

		txtTimeSettingListening = (TextView) findViewById(R.id.txt_timesetting_listening);
		txtTimeSettingReading = (TextView) findViewById(R.id.txt_timesetting_reading);
		txtTimeSettingClozing = (TextView) findViewById(R.id.txt_timesetting_clozing);
		txtTimeSettingVocabulary = (TextView) findViewById(R.id.txt_timesetting_vocabulary);
	}

	public void newDialog(String time) {
		LayoutInflater inflater = (LayoutInflater) getApplicationContext()
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		// view���Զ���Ի���
		View view = inflater.inflate(R.layout.timesettingview_dialog, null);
		new AlertDialog.Builder(this).setTitle("������")
				.setIcon(android.R.drawable.ic_dialog_info).setView(view)
				.setPositiveButton("ȷ��", new myPositiveButtonListener())
				.setNegativeButton("ȡ��", null).show();
		edtTimeSettingDialog = (EditText) view
				.findViewById(R.id.edt_timesetting_dialog);
		edtTimeSettingDialog.setText(time);
	}

	class myRelativeLayoutOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (v == rltTimeSettingListening) {
				Type = 0;
				newDialog(timeOfListening);
			} else if (v == rltTimeSettingReading) {
				Type = 1;
				newDialog(timeOfReading);
			} else if (v == rltTimeSettingClozing) {
				Type = 2;
				newDialog(timeOfClozing);
			} else if (v == rltTimeSettingVocabulary) {
				Type = 3;
				newDialog(timeOfVocabulary);
			} else if (v == rltTimeSettingDefault) {
				Type = 4;
			}
		}
	}

	class myPositiveButtonListener implements DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			String stringTime;
			stringTime = String.valueOf(edtTimeSettingDialog.getText());
			switch (Type) {
			case 0:
				AppVariable.Time.G_TIME_LISTENING = timeOfListening = stringTime;
				txtTimeSettingListening.setText(txtTimeSettingListening
						.getText().subSequence(0, 4) + " " + stringTime + "����");
				break;
			case 1:
				AppVariable.Time.G_TIME_READING = timeOfReading = stringTime;
				txtTimeSettingReading.setText(txtTimeSettingReading.getText()
						.subSequence(0, 4) + " " + stringTime + "����");
				break;
			case 2:
				AppVariable.Time.G_TIME_CLOZING = timeOfClozing = stringTime;
				txtTimeSettingClozing.setText(txtTimeSettingClozing.getText()
						.subSequence(0, 4) + " " + stringTime + "����");
				break;
			case 3:
				AppVariable.Time.G_TIME_VOCABULARY = timeOfVocabulary = stringTime;
				txtTimeSettingVocabulary.setText(txtTimeSettingVocabulary
						.getText().subSequence(0, 4) + " " + stringTime + "����");
				break;
			}
			dialog.dismiss();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// -------------����ʼ��Ƥ����--------------------------
		SkinSettingManager mSettingManager = new SkinSettingManager(this);
		mSettingManager.initSkins();
	}
}
