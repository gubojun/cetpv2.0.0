package com.cetp.view;

import java.util.Date;
import java.util.List;

import net.tsz.afinal.FinalDb;

import com.cetp.R;
import com.cetp.action.AppVariable;
import com.cetp.database.DBClozingOfQuestion;
import com.cetp.database.DBWrongStat;
import com.cetp.database.TableClozingOfQuestion;
import com.cetp.database.TableClozingOfQuestionWrong;
import com.cetp.database.TableListeningOfQuestion;
import com.cetp.database.TableListeningOfQuestionWrong;
import com.cetp.view.MainTab.DateUtils;

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
			cur = db.getItemFromYM(AppVariable.Common.YearMonth);
			cur.moveToFirst();
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

		// ----------------------------------------------------------------
		TableClozingOfQuestion t = new TableClozingOfQuestion();
		TableClozingOfQuestionWrong tw = new TableClozingOfQuestionWrong();
		FinalDb fdb = FinalDb.create(context, "cetp");
		List<TableClozingOfQuestion> lisOfQList = fdb.findAllByWhere(
				TableClozingOfQuestion.class, "YYYYMM="
						+ AppVariable.Common.YearMonth);

		Log.v(TAG, "questionAmount=" + questionAmount + "  dataCount="
				+ dataCount);
		// ----------------------------------------------------------------
		// �ж����ݿ��Ƿ�������
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
					// ����д�����ݿ�--------------------------------------------------
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
		Log.v(TAG, "��¼������" + (lisOfQList != null ? lisOfQList.size() : 0));

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
		// -----------------------------------------------------------------------------------
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
