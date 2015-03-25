package com.cetp.view;

import com.cetp.R;
import com.cetp.database.DBClozingOfQuestion;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.LayoutParams;

public class ClozingViewAnswer {
	private String TAG = "ClozingViewAnswer1";

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
			LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	private final LinearLayout.LayoutParams LP_FW = new LinearLayout.LayoutParams(
			LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	@SuppressWarnings("unused")
	private final LinearLayout.LayoutParams LP_WW = new LinearLayout.LayoutParams(
			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	private Cursor cur;

	private int userRightAnswer = 0;

	private int userWrongAnswer = 0;

	private Button userAnswerDiolog;
	Context context;

	public ClozingViewAnswer(Context c) {
		context = c;
	}

	public void setView(View v) {
		DBClozingOfQuestion db = new DBClozingOfQuestion(context);
		questionAmount = 0;

		db.open();
		Log.v(TAG, "Activity State: checkTableExists()");
		if (db.checkTableExists("Clozing_Passage")) {
			// ---ȡ����������---
			cur = db.getAllItem();
			// cur.moveToFirst();
		} else {
			Toast.makeText(context, "���ݲ�����", Toast.LENGTH_SHORT).show();
		}

		ScrollView clozingViewScroll = (ScrollView) v
				.findViewById(R.id.scr_clozing_answer);
		layout = new LinearLayout(context);

		layout.setOrientation(LinearLayout.VERTICAL); // �ؼ����䷽ʽΪ��ֱ����
		layout.setPadding(10, 0, 10, 0);
		// ��¼���ݵ���Ŀ
		dataCount = cur.getCount();
		int AnswerID = 0;
		while (AnswerID < dataCount) {
			AnswerID++;
			// ÿ��������Բ���
			myLayout = new LinearLayout(context);
			myLayout.setOrientation(LinearLayout.HORIZONTAL);// ˮƽ����
			myLayout.setLayoutParams(LP_FW);
			myLayout.setBackgroundResource(R.drawable.login_input);// ���ñ���
			myLayout.setId(AnswerID + 1000);

			txtQuestionNumber = new TextView(context);
			txtClozingAnswer = new TextView(context);// ��ȷ��
			txtClozingAnswerOfUser = new TextView(context);// �û���
			txtClozingAnswerOfUser.setId(AnswerID);
			setAnswerText(txtQuestionNumber, txtClozingAnswer,
					txtClozingAnswerOfUser, AnswerID, context);
			// ÿ��������Բ��ְ�����Ŀ�Ĵ𰸣��û��Ĵ�
			myLayout.addView(txtClozingAnswer);
			myLayout.addView(txtClozingAnswerOfUser);
			// ���ñ���
			myLayout.setBackgroundResource(R.drawable.login_input);
			// ��ż����ܵ����Բ���
			layout.addView(txtQuestionNumber);
			// ��Ŀ���ּ����ܵ����Բ���
			layout.addView(myLayout);
			cur.moveToNext();
		}
		clozingViewScroll.addView(layout);
		cur.close();
		db.close();
	}

	private void setAnswerText(TextView txtQuestionNumber,
			TextView txtClozingAnswer, TextView txtListeningAnswerOfUser,
			int AnswerID, Context context) {
		questionAmount++;
		txtQuestionNumber.setText(String.valueOf(questionAmount));
		txtQuestionNumber.setTextSize(20);
		txtQuestionNumber.setTextColor(context.getResources().getColor(
				R.color.red));
		String theAnswer = ClozingViewQuestion.clozingAnswer_All[Integer
				.parseInt((String) txtQuestionNumber.getText())];
		/** ��¼��ȷ�� */
		theCorrectAnswer[Integer.parseInt((String) txtQuestionNumber.getText())] = cur
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
	void reFresh(View v) {
		int NUM = 0;
		String theAnswer;

		userRightAnswer = 0;
		userWrongAnswer = 0;

		System.out.println(dataCount); // �ж����ݿ��Ƿ�������
		if (dataCount != 0) {
			while (NUM != questionAmount) {
				NUM++;
				theAnswer = ClozingViewQuestion.clozingAnswer_All[NUM];
				txtClozingAnswerOfUser = (TextView) v.findViewById(NUM);
				myLayout = (LinearLayout) v.findViewById(NUM + 1000);
				if (theAnswer == null) {
					theAnswer = "δ����";
				} else if (!theAnswer.equals(theCorrectAnswer[NUM])) {
					myLayout.setBackgroundResource(R.drawable.login_input_dark);// ���ñ���
					txtClozingAnswerOfUser.setTextColor(v.getResources()
							.getColor(R.color.red));
					userWrongAnswer++;
				} else {
					myLayout.setBackgroundResource(R.drawable.login_input_light);// ���ñ���
					txtClozingAnswerOfUser.setTextColor(v.getResources()
							.getColor(R.color.black));
					userRightAnswer++;
				}
				txtClozingAnswerOfUser.setText(theAnswer);
			}
		}

		userAnswerDiolog = (Button) v.findViewById(R.id.user_clozing_diolog);
		userAnswerDiolog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(context);
			}

		});
	}
}
