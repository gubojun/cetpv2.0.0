package com.cetp.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup.LayoutParams;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.cetp.R;
import com.cetp.database.DBVocabulary;

/**
 * �ʻ�𰸽���
 * 
 * @author ���ӻ�
 * @data 2013/5/9
 */
public class VocabularyViewAnswer extends Activity {
	private String TAG = "VocabularyViewQuestiontext";
	/** ��� */
	private TextView txtQuestionNumber;
	/** ˮƽ���Բ��� */
	private LinearLayout myLayout;
	/** ���岼�� */
	private LinearLayout layout;
	/** ��ȷ�� */
	private TextView txtVocabularyAnswer;
	/** �û�����Ĵ� */
	private TextView txtVocabularyAnswerOfUser;
	/** ��¼��Ŀ����Ŀ */
	private static int questionAmount;

	private static int userWrongAnswer = 0;

	private static int userRightAnswer = 0;
	/** ��¼���ݵ���Ŀ */
	private static int dataCount;

	private Button userAnswerDiolog;

	TabHost tabHost;
	// ------���ַ�ʽ--------
	@SuppressWarnings("unused")
	private final LinearLayout.LayoutParams LP_FF = new LinearLayout.LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
	@SuppressWarnings("unused")
	private final LinearLayout.LayoutParams LP_FW = new LinearLayout.LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
	@SuppressWarnings("unused")
	private final LinearLayout.LayoutParams LP_WW = new LinearLayout.LayoutParams(
			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	private Cursor cur;

	DBVocabulary db = new DBVocabulary(this);
	private static String[] theCorrectAnswer = new String[200];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "Activity State: onCreate()");
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		setContentView(R.layout.vocabularyview_answer);

		// ��ʼ����Ŀ��Ŀ
		questionAmount = 0;

		db.open();
		Log.v(TAG, "Activity State: checkTableExists()");
		if (db.checkTableExists("Vocabulary_and_Structure")) {
			// ---ȡ����������---
			cur = db.getAllItem();
		} else {
			Toast.makeText(VocabularyViewAnswer.this, "���ݲ�����",
					Toast.LENGTH_SHORT).show();
		}

		ScrollView VocabularyViewScroll = (ScrollView) findViewById(R.id.scr_vocabularyview_answer);
		layout = new LinearLayout(this);

		layout.setOrientation(LinearLayout.VERTICAL); // �ؼ����䷽ʽΪ��ֱ����
		layout.setPadding(10, 0, 10, 0);
		int NUMBER = 0;
		// ��¼���ݵ���Ŀ
		dataCount = cur.getCount();
		while (NUMBER < dataCount) {
			NUMBER++;
			// ÿ��������Բ���
			myLayout = new LinearLayout(this);
			myLayout.setOrientation(LinearLayout.HORIZONTAL);// ˮƽ����
			myLayout.setLayoutParams(LP_FW);
			myLayout.setBackgroundResource(R.drawable.login_input);// ���ñ���

			txtQuestionNumber = new TextView(this);
			txtVocabularyAnswer = new TextView(this);// ��ȷ��
			txtVocabularyAnswerOfUser = new TextView(this);// �û���
			txtVocabularyAnswerOfUser.setId(NUMBER);
			setAnswerText(txtQuestionNumber, txtVocabularyAnswer,
					txtVocabularyAnswerOfUser, NUMBER, this);
			// ÿ��������Բ��ְ�����Ŀ�Ĵ𰸣��û��Ĵ�
			myLayout.addView(txtVocabularyAnswer);
			myLayout.addView(txtVocabularyAnswerOfUser);

			// ��ż����ܵ����Բ���
			layout.addView(txtQuestionNumber);
			// ��Ŀ���ּ����ܵ����Բ���
			layout.addView(myLayout);
			cur.moveToNext();
		}
		VocabularyViewScroll.addView(layout);
		cur.close();
		db.close();

	}

	/**
	 * ���ô��ı�
	 * 
	 * @param txtQuestionNumber
	 * @param txtVocabularyAnswer
	 * @param txtVocabularyAnswrOfUser
	 * @param context
	 */
	private void setAnswerText(TextView txtQuestionNumber,
			TextView txtVocabularyAnswer, TextView txtVocabularyAnswerOfUser,
			int NUMBER, Context context) {

		// ��¼��Ŀ��Ŀ
		questionAmount++;
		txtQuestionNumber.setText(cur.getString(cur
				.getColumnIndex("QuestionNumber")));
		txtQuestionNumber.setTextSize(20);
		txtQuestionNumber.setTextColor(getResources().getColor(R.color.red));
		String theAnswer = VocabularyView.vocabularyAnswer_All[Integer
				.parseInt((String) txtQuestionNumber.getText()) - 40];
		theCorrectAnswer[NUMBER] = cur.getString(cur.getColumnIndex("Answer"));
		txtVocabularyAnswer.setText("��:" + theCorrectAnswer[NUMBER] + "  "
				+ "�������:");
		txtVocabularyAnswer.setTextSize(17);

		txtVocabularyAnswerOfUser.setText(theAnswer);
		txtVocabularyAnswerOfUser.setTextSize(17);

	}

	private void showDialog(Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle("��ͳ��");
		int notAnswer = questionAmount - userRightAnswer - userWrongAnswer;
		int score = 0;
		if (questionAmount == 0) {
			score = 0;
		} else {
			score = userRightAnswer * 100 / questionAmount;
		}
		builder.setMessage("��ȷ��" + userRightAnswer + "\n" + "����"
				+ userWrongAnswer + "\n" + "δ��" + notAnswer + "\n������" + score
				+ "��");
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// setTitle("ȷ��");
			}
		});

		builder.show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			finish();
			break;
		}

		return super.onKeyDown(keyCode, event);
	}

	/** ������Ŀ�� */
	private void reFresh() {
		int NUM = 0;
		String theAnswer;
		userRightAnswer = 0;
		userWrongAnswer = 0;
		// ��������ʱ������Ŀ
		if (dataCount > 0) {
			while (NUM != questionAmount) {
				NUM++;
				theAnswer = VocabularyView.vocabularyAnswer_All[NUM];
				txtVocabularyAnswerOfUser = (TextView) findViewById(NUM);
				if (theAnswer == null) {
					theAnswer = "δ����";
				} else if (!theAnswer.equals(theCorrectAnswer[NUM])) {
					txtVocabularyAnswerOfUser.setBackgroundColor(getResources()
							.getColor(R.color.red));
					userWrongAnswer++;
				} else {
					txtVocabularyAnswerOfUser
							.setBackgroundColor(BIND_AUTO_CREATE);
					userRightAnswer++;
				}
				txtVocabularyAnswerOfUser.setText(theAnswer);
			}
		}

		userAnswerDiolog = (Button) findViewById(R.id.user_vocabulary_diolog);
		userAnswerDiolog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(VocabularyViewAnswer.this);
			}

		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.w(TAG, "onDestroy");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.w(TAG, "onPause");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.w(TAG, "onResume");
		// ������Ŀ��
		reFresh();
		// ��ʼ������
		// TextSettingManager mSettingManager = new TextSettingManager(this);
		// mSettingManager.initText();
		// txtVocabularyAnswer.setTextColor(AppVariable.Font.G_TEXTCOLOR_PASSAGE);
	}
}
