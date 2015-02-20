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
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RadioGroup.LayoutParams;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cetp.R;
import com.cetp.database.DBClozingOfQuestion;

/**
 * ���ʹ𰸽���
 * 
 * @author �˲���
 * @date 2013-5-13
 */
public class ClozingViewAnswer extends Activity {
	private String TAG = "ClozingViewAnswer";

	private static String[] theCorrectAnswer = new String[200];
	private LinearLayout myLayout;
	private LinearLayout layout;

	private TextView txtQuestionNumber;// ���
	/** ��ȷ�� */
	private TextView txtClozingAnswer;
	/** �û�����Ĵ� */
	private TextView txtClozingAnswerOfUser;
	/** ��¼��Ŀ����Ŀ */
	private static int questionAmount;
	/** ��¼���ݵ���Ŀ */
	private static int dataCount;

	// ------���ַ�ʽ--------
	@SuppressWarnings("unused")
	private final LinearLayout.LayoutParams LP_FF = new LinearLayout.LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
	private final LinearLayout.LayoutParams LP_FW = new LinearLayout.LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
	@SuppressWarnings("unused")
	private final LinearLayout.LayoutParams LP_WW = new LinearLayout.LayoutParams(
			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	private Cursor cur;

	DBClozingOfQuestion db = new DBClozingOfQuestion(this);

	private int userRightAnswer = 0;

	private int userWrongAnswer = 0;

	private Button userAnswerDiolog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "Activity State: onCreate()");
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		setContentView(R.layout.clozingview_answer);
		questionAmount = 0;

		db.open();
		Log.v(TAG, "Activity State: checkTableExists()");
		if (db.checkTableExists("Clozing_Passage")) {
			// ---ȡ����������---
			cur = db.getAllItem();
			// cur.moveToFirst();
		} else {
			Toast.makeText(ClozingViewAnswer.this, "���ݲ�����", Toast.LENGTH_SHORT)
					.show();
		}

		ScrollView clozingViewScroll = (ScrollView) findViewById(R.id.scr_clozing_answer);
		layout = new LinearLayout(this);

		layout.setOrientation(LinearLayout.VERTICAL); // �ؼ����䷽ʽΪ��ֱ����
		layout.setPadding(10, 0, 10, 0);
		// ��¼���ݵ���Ŀ
		dataCount = cur.getCount();
		int AnswerID = 0;
		while (cur.moveToNext()) {
			AnswerID++;
			// ÿ��������Բ���
			myLayout = new LinearLayout(this);
			myLayout.setOrientation(LinearLayout.HORIZONTAL);// ˮƽ����
			myLayout.setLayoutParams(LP_FW);
			myLayout.setBackgroundResource(R.drawable.login_input);// ���ñ���

			txtQuestionNumber = new TextView(this);
			txtClozingAnswer = new TextView(this);// ��ȷ��
			txtClozingAnswerOfUser = new TextView(this);// �û���
			txtClozingAnswerOfUser.setId(AnswerID);
			setAnswerText(txtQuestionNumber, txtClozingAnswer,
					txtClozingAnswerOfUser, AnswerID, this);
			// ÿ��������Բ��ְ�����Ŀ�Ĵ𰸣��û��Ĵ�
			myLayout.addView(txtClozingAnswer);
			myLayout.addView(txtClozingAnswerOfUser);
			// ���ñ���
			myLayout.setBackgroundResource(R.drawable.login_input);
			// ��ż����ܵ����Բ���
			layout.addView(txtQuestionNumber);
			// ��Ŀ���ּ����ܵ����Բ���
			layout.addView(myLayout);
		}
		clozingViewScroll.addView(layout);
		cur.close();
		db.close();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			this.finish();
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void setAnswerText(TextView txtQuestionNumber,
			TextView txtClozingAnswer, TextView txtListeningAnswerOfUser,
			int AnswerID, Context context) {
		questionAmount++;
		txtQuestionNumber.setText(cur.getString(cur
				.getColumnIndex("QuestionNumber")));
		txtQuestionNumber.setTextSize(20);
		txtQuestionNumber.setTextColor(getResources().getColor(R.color.red));
		String theAnswer = ClozingViewQuestion.clozingAnswer_All[AnswerID];
		/** ��¼��ȷ�� */
		theCorrectAnswer[AnswerID] = cur
				.getString(cur.getColumnIndex("Answer"));
		String questionAnswer = cur.getString(cur.getColumnIndex("Answer"));
		txtClozingAnswer.setText("��:" + questionAnswer + "  " + "�������:");
		txtClozingAnswer.setTextSize(17);

		txtListeningAnswerOfUser.setText(theAnswer);
		txtListeningAnswerOfUser.setTextSize(17);
		// ���ñ���
		// if (questionAnswer.equals(theAnswer))
		// txtClozingAnswer.setBackgroundResource(R.drawable.login_input);
		// else
		// txtListeningAnswer.setBackgroundResource(R.drawable.optbg);
	}

	private void showDialog(Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle("��ͳ��");
		int notAnswer = questionAmount - userRightAnswer - userWrongAnswer;
		int score = 0;
		if(questionAmount == 0){
			score = 0;
		}else{
			score = userRightAnswer*100/questionAmount;
		}
		builder.setMessage("��ȷ��" + userRightAnswer + "\n" + "����"
				+ userWrongAnswer + "\n" + "δ��" + notAnswer+"\n"+"������"+score+"��");
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// setTitle("ȷ��");
			}
		});

		builder.show();
	}

	/** ������Ŀ�� */
	private void reFresh() {
		int NUM = 0;
		String theAnswer;

		userRightAnswer = 0;
		userWrongAnswer = 0;

		System.out.println(dataCount);
		// �ж����ݿ��Ƿ�������
		if (dataCount != 0) {
			while (NUM != questionAmount) {
				NUM++;
				theAnswer = ClozingViewQuestion.clozingAnswer_All[NUM];
				txtClozingAnswerOfUser = (TextView) findViewById(NUM);
				if (theAnswer == null) {
					theAnswer = "δ����";
				} else if (!theAnswer.equals(theCorrectAnswer[NUM])) {
					txtClozingAnswerOfUser.setBackgroundColor(getResources()
							.getColor(R.color.red));
					userWrongAnswer++;
				} else {
					txtClozingAnswerOfUser.setBackgroundColor(BIND_AUTO_CREATE);
					userRightAnswer++;
				}
				txtClozingAnswerOfUser.setText(theAnswer);
			}
		}

		userAnswerDiolog = (Button) findViewById(R.id.user_clozing_diolog);
		userAnswerDiolog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(ClozingViewAnswer.this);
			}

		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		reFresh();
		// ��ʼ������
		//TextSettingManager mSettingManager = new TextSettingManager(this);
		//mSettingManager.initText();
		// txtClozingAnswer.setTextColor(AppVariable.Font.G_TEXTCOLOR_PASSAGE);
	}
}
