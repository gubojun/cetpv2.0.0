package com.cetp.view;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import net.tsz.afinal.FinalDb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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

import com.cetp.R;
import com.cetp.action.AppVariable;
import com.cetp.database.DBListeningOfQuestion;
import com.cetp.database.DBWrongStat;
import com.cetp.database.TableListeningOfQuestion;
import com.cetp.database.TableListeningOfQuestionWrong;
import com.cetp.question.QuestionListening;
import com.cetp.view.MainTab.DateUtils;

public class ListeningViewAnswer {
	final String TAG = "ListeningViewAnswer";
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
	private final LinearLayout.LayoutParams LP_MM = new LinearLayout.LayoutParams(
			LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	private final LinearLayout.LayoutParams LP_MW = new LinearLayout.LayoutParams(
			LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	private final LinearLayout.LayoutParams LP_WW = new LinearLayout.LayoutParams(
			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	private Cursor cur;

	private static int userRightAnswer = 0;
	private static int userWrongAnswer = 0;
	private Button userAnswerDiolog;
	Context context;

	public ListeningViewAnswer(Context c) {
		context = c;
	}

	public void setView(View v) {
		questionAmount = 0;
		userAnswerDiolog = (Button) v.findViewById(R.id.user_listening_diolog);
		userAnswerDiolog.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(context);
			}
		});

		DBListeningOfQuestion db = new DBListeningOfQuestion(context);
		LinearLayout layout1;
		LinearLayout myLayout;

		db.open();
		// Log.v(TAG, "Activity State: checkTableExists()");
		if (db.checkTableExists("Listening_Comprehension_Passage")) {
			// ---ȡ����������---
			// cur = db.getAllItem();
			cur = db.getItemFromYM(AppVariable.Common.YearMonth);
			// cur.moveToFirst();
		} else {
			Toast.makeText(context, "���ݲ�����", Toast.LENGTH_SHORT).show();
		}
		ScrollView listeningViewScroll = (ScrollView) v
				.findViewById(R.id.scr_listening_answer);
		layout1 = new LinearLayout(context);

		layout1.setOrientation(LinearLayout.VERTICAL); // �ؼ����䷽ʽΪ��ֱ����
		layout1.setPadding(10, 0, 10, 0);
		// ��¼��Ŀ����Ŀ
		dataCount = cur.getCount();
		int NUMBER = 0;
		while (NUMBER < dataCount) {
			NUMBER++;
			// ÿ��������Բ���
			myLayout = new LinearLayout(context);
			myLayout.setOrientation(LinearLayout.HORIZONTAL);// ˮƽ����
			myLayout.setLayoutParams(LP_MW);
			myLayout.setBackgroundResource(R.drawable.login_input);// ���ñ���
			myLayout.setId(NUMBER + 1000);

			txtQuestionNumber = new TextView(context);
			txtListeningAnswer = new TextView(context);// ��ȷ��
			txtListeningAnswerOfUser = new TextView(context);// �û���
			txtListeningAnswerOfUser.setId(NUMBER);
			setAnswerText(txtQuestionNumber, txtListeningAnswer,
					txtListeningAnswerOfUser, NUMBER, context);
			// ÿ��������Բ��ְ�����Ŀ�Ĵ𰸣��û��Ĵ�
			myLayout.addView(txtListeningAnswer);
			myLayout.addView(txtListeningAnswerOfUser);

			// ��ż����ܵ����Բ���
			layout1.addView(txtQuestionNumber);
			// ��Ŀ���ּ����ܵ����Բ���
			layout1.addView(myLayout);
			cur.moveToNext();
		}
		listeningViewScroll.addView(layout1);
		cur.close();
		db.close();

		Date dateNow = new Date(System.currentTimeMillis());// ��ȡ��ǰʱ��
		String now = MainTab.DateUtils.dateToStr("MMddHHmmss", dateNow);
		Log.v(TAG, "now=" + now);
	}

	private void setAnswerText(TextView txtQuestionNumber,
			TextView txtListeningAnswer, TextView txtVocabularyAnswrOfUser,
			int NUMBER, Context context) {

		// ��¼��Ŀ��Ŀ
		questionAmount++;
		txtQuestionNumber.setText(String.valueOf(questionAmount));
		txtQuestionNumber.setTextSize(20);
		txtQuestionNumber.setTextColor(context.getResources().getColor(
				R.color.red));
		String theAnswer = ListeningViewQuestion.listeningAnswer_All[Integer
				.parseInt((String) txtQuestionNumber.getText())];
		theCorrectAnswer[Integer.parseInt((String) txtQuestionNumber.getText())] = cur
				.getString(cur.getColumnIndex("Answer"));

		txtListeningAnswer.setText("��:"
				+ theCorrectAnswer[Integer.parseInt((String) txtQuestionNumber
						.getText())] + "  " + "�������:");
		txtListeningAnswer.setTextSize(17);

		txtListeningAnswerOfUser.setText(theAnswer);
		txtListeningAnswerOfUser.setTextSize(17);
		// ���ñ���
		// if (questionAnswer.equals(theAnswer))
		// txtListeningAnswer.setBackgroundResource(R.drawable.login_input);
		// else
		// txtListeningAnswer.setBackgroundResource(R.drawable.optbg);
	}

	class userAnswerDiologOnClickListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			showDialog(context);
		}
	}

	/** ������Ŀ�� */
	public void reFresh(View v) {
		int NUM = 0;
		String theAnswer;
		userRightAnswer = 0;
		userWrongAnswer = 0;

		// 20150324--------------------------------------------------------
		TableListeningOfQuestion t = new TableListeningOfQuestion();
		TableListeningOfQuestionWrong tw = new TableListeningOfQuestionWrong();
		FinalDb fdb = FinalDb.create(context, "cetp");
		List<TableListeningOfQuestion> lisOfQList = fdb.findAllByWhere(
				TableListeningOfQuestion.class, "YYYYMM="
						+ AppVariable.Common.YearMonth);

		Log.v(TAG, "questionAmount=" + questionAmount + "  dataCount="
				+ dataCount);
		// 20150324--------------------------------------------------------
		// �ж����ݿ��Ƿ�������
		if (dataCount != 0) {
			while (NUM != questionAmount) {
				NUM++;
				theAnswer = ListeningViewQuestion.listeningAnswer_All[NUM];
				txtListeningAnswerOfUser = (TextView) v.findViewById(NUM);
				myLayout = (LinearLayout) v.findViewById(NUM + 1000);

				if (theAnswer == null) {
					theAnswer = "δ����";
				} else if (!theAnswer.equals(theCorrectAnswer[NUM])) {// �ش����
					myLayout.setBackgroundResource(R.drawable.login_input_dark);// ���ñ���
					txtListeningAnswerOfUser.setTextColor(v.getResources()
							.getColor(R.color.red));
					if (lisOfQList != null) {
						String s;
						s = lisOfQList.get(NUM - 1).getYYYYMM();
						t.setYYYYMM(s);
						tw.setYYYYMM(s);
						s = lisOfQList.get(NUM - 1).getQuestionType();
						t.setQuestionType(s);
						tw.setQuestionType(s);
						s = lisOfQList.get(NUM - 1).getQuestionNumber();
						t.setQuestionNumber(s);
						tw.setQuestionNumber(s);
						s = lisOfQList.get(NUM - 1).getSelectionA();
						t.setSelectionA(s);
						tw.setSelectionA(s);
						s = lisOfQList.get(NUM - 1).getSelectionB();
						t.setSelectionB(s);
						tw.setSelectionB(s);
						s = lisOfQList.get(NUM - 1).getSelectionC();
						t.setSelectionC(s);
						tw.setSelectionC(s);
						s = lisOfQList.get(NUM - 1).getSelectionD();
						t.setSelectionD(s);
						tw.setSelectionD(s);
						s = lisOfQList.get(NUM - 1).getAnswer();
						t.setAnswer(s);
						tw.setAnswer(s);
						s = lisOfQList.get(NUM - 1).getComments();
						if (lisOfQList.get(NUM - 1).getComments().equals("")) {
							t.setComments("w");// ����wrong
						} else {
							t.setComments("wf");// ����wrong���ղ�favorite
						}
						tw.setComments(s);
						tw.setDate(new Date());

						if (!(lisOfQList.get(NUM - 1).getComments().trim()
								.equals("w") || lisOfQList.get(NUM - 1)
								.getComments().trim().equals("wf"))) {
							fdb.save(tw);
							fdb.update(
									t,
									"YYYYMM=" + t.getYYYYMM()
											+ " and QuestionNumber="
											+ t.getQuestionNumber());
						}
					}
					// txtListeningAnswerOfUser.setBackgroundColor(getResources()
					// .getColor(R.color.red));
					userWrongAnswer++;
				} else {// �ش���ȷ
					myLayout.setBackgroundResource(R.drawable.login_input_light);// ���ñ���
					txtListeningAnswerOfUser.setTextColor(v.getResources()
							.getColor(R.color.black));
					// txtListeningAnswerOfUser.setTextColor(BIND_AUTO_CREATE);
					// txtListeningAnswerOfUser
					// .setBackgroundColor(BIND_AUTO_CREATE);
					userRightAnswer++;
				}
				txtListeningAnswerOfUser.setText(theAnswer);

			}
		}

		Log.v(TAG, "��¼������" + (lisOfQList != null ? lisOfQList.size() : 0));

		cur.close();

		Date dateNow = new Date(System.currentTimeMillis());// ��ȡ��ǰʱ��
		/** ���ڱ�������� */
		DBWrongStat dbWrong = new DBWrongStat(context);
		dbWrong.open();
		int wrongstat = userRightAnswer + userWrongAnswer > 0 ? userWrongAnswer
				* 100 / (userRightAnswer + userWrongAnswer) : -1;
		// ���£�����������Ŀ����
		String[] result = new String[] {
				DateUtils.dateToStr("yyyyMMddHHmmss", dateNow),
				String.valueOf(userWrongAnswer),
				String.valueOf(questionAmount), String.valueOf(wrongstat) };
		if (wrongstat >= 0)
			dbWrong.insertItem(result[0], result[1], result[2], result[3]);
		dbWrong.close();
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
}
