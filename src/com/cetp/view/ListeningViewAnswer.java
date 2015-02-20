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
import com.cetp.action.AppVariable;
import com.cetp.database.DBListeningOfQuestion;

/**
 * 
 * @author ���ӻ�
 * @data 2013/5/9
 */
public class ListeningViewAnswer extends Activity {
	private String TAG = "ListeningViewQuestiontext";
	private TextView txtQuestionNumber;// ���
	/** ��ȷ�� */
	private TextView txtListeningAnswer;
	/** �û�����Ĵ� */
	private TextView txtListeningAnswerOfUser;
	/** ��¼��Ŀ����Ŀ */
	private static int questionAmount;
	/** ��¼���ݵ���Ŀ */
	private int dataCount;
	/** ��¼��ȷ�� */
	private static String[] theCorrectAnswer = new String[200];

	LinearLayout layout;
	LinearLayout myLayout;
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

	DBListeningOfQuestion db = new DBListeningOfQuestion(this);
	private static int userRightAnswer = 0;
	private static int userWrongAnswer = 0;
	private Button userAnswerDiolog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.w(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		setContentView(R.layout.listeningview_answer);
		questionAmount = 0;

		db.open();
		Log.v(TAG, "Activity State: checkTableExists()");
		if (db.checkTableExists("Listening_Comprehension_Passage")) {
			// ---ȡ����������---
			//cur = db.getAllItem();
			cur=db.getItemFromYM(AppVariable.Common.YearMonth);
			// cur.moveToFirst();
		} else {
			Toast.makeText(ListeningViewAnswer.this, "���ݲ�����",
					Toast.LENGTH_SHORT).show();
		}

		ScrollView listeningViewScroll = (ScrollView) findViewById(R.id.scr_listening_answer);
		layout = new LinearLayout(this);

		layout.setOrientation(LinearLayout.VERTICAL); // �ؼ����䷽ʽΪ��ֱ����
		layout.setPadding(10, 0, 10, 0);
		// ��¼��Ŀ����Ŀ
		dataCount = cur.getCount();
		int NUMBER = 0;
		while (cur.moveToNext()) {
			NUMBER++;
			// ÿ��������Բ���
			myLayout = new LinearLayout(this);
			myLayout.setOrientation(LinearLayout.HORIZONTAL);// ˮƽ����
			myLayout.setLayoutParams(LP_FW);
			myLayout.setBackgroundResource(R.drawable.login_input);// ���ñ���

			txtQuestionNumber = new TextView(this);
			txtListeningAnswer = new TextView(this);// ��ȷ��
			txtListeningAnswerOfUser = new TextView(this);// �û���
			txtListeningAnswerOfUser.setId(NUMBER);
			setAnswerText(txtQuestionNumber, txtListeningAnswer,
					txtListeningAnswerOfUser, NUMBER, this);
			// ÿ��������Բ��ְ�����Ŀ�Ĵ𰸣��û��Ĵ�
			myLayout.addView(txtListeningAnswer);
			myLayout.addView(txtListeningAnswerOfUser);

			// ��ż����ܵ����Բ���
			layout.addView(txtQuestionNumber);
			// ��Ŀ���ּ����ܵ����Բ���
			layout.addView(myLayout);
		}
		listeningViewScroll.addView(layout);
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
			TextView txtListeningAnswer, TextView txtVocabularyAnswrOfUser,
			int NUMBER, Context context) {

		// ��¼��Ŀ��Ŀ
		questionAmount++;
		txtQuestionNumber.setText(cur.getString(cur
				.getColumnIndex("QuestionNumber")));
		txtQuestionNumber.setTextSize(20);
		txtQuestionNumber.setTextColor(getResources().getColor(R.color.red));
		String theAnswer = ListeningViewQuestion.listeningAnswer_All[NUMBER];
		theCorrectAnswer[NUMBER] = cur.getString(cur.getColumnIndex("Answer"));

		txtListeningAnswer.setText("��:" + theCorrectAnswer[NUMBER] + "  "
				+ "�������:");
		txtListeningAnswer.setTextSize(17);

		txtListeningAnswerOfUser.setText(theAnswer);
		txtListeningAnswerOfUser.setTextSize(17);
		// ���ñ���
		// if (questionAnswer.equals(theAnswer))
		// txtListeningAnswer.setBackgroundResource(R.drawable.login_input);
		// else
		// txtListeningAnswer.setBackgroundResource(R.drawable.optbg);
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
				+ userWrongAnswer + "\n" + "δ��" + notAnswer + "\n" + "������"
				+ score + "��");
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
		// �ж����ݿ��Ƿ�������
		if (dataCount != 0) {
			while (NUM != questionAmount) {
				NUM++;
				theAnswer = ListeningViewQuestion.listeningAnswer_All[NUM];
				txtListeningAnswerOfUser = (TextView) findViewById(NUM);
				if (theAnswer == null) {
					theAnswer = "δ����";
				} else if (!theAnswer.equals(theCorrectAnswer[NUM])) {
					txtListeningAnswerOfUser.setTextColor(getResources()
							.getColor(R.color.red));
					// txtListeningAnswerOfUser.setBackgroundColor(getResources()
					// .getColor(R.color.red));
					userWrongAnswer++;
				} else {
					txtListeningAnswerOfUser.setTextColor(getResources()
							.getColor(R.color.black));
//					txtListeningAnswerOfUser.setTextColor(BIND_AUTO_CREATE);
					// txtListeningAnswerOfUser
					// .setBackgroundColor(BIND_AUTO_CREATE);
					userRightAnswer++;
				}
				txtListeningAnswerOfUser.setText(theAnswer);

			}
		}
		cur.close();

		userAnswerDiolog = (Button) findViewById(R.id.user_listening_diolog);
		userAnswerDiolog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(ListeningViewAnswer.this);
			}

		});
	}

	@Override
	protected void onResume() {
		Log.w(TAG, "onResume");
		reFresh();
		super.onResume();

		// ��ʼ������
//		TextSettingManager mSettingManager = new TextSettingManager(this);
//		mSettingManager.initText();
		// txtListeningAnswer.setTextColor(AppVariable.Font.G_TEXTCOLOR_PASSAGE);
	}
}
