package com.cetp.view;

import java.util.Date;
import java.util.List;

import net.tsz.afinal.FinalDb;

import com.cetp.R;
import com.cetp.action.AppVariable;
import com.cetp.database.DBReadingOfQuestion;
import com.cetp.database.DBWrongStat;
import com.cetp.database.TableClozingOfQuestion;
import com.cetp.database.TableClozingOfQuestionWrong;
import com.cetp.database.TableReadingOfQuestion;
import com.cetp.database.TableReadingOfQuestionWrong;
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
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.LayoutParams;

public class ReadingViewAnswer {
	private String TAG = "ReadingViewAnswer1";
	private TextView txtQuestionNumber;// 题号
	private LinearLayout myLayout;// 水平线性布局
	private LinearLayout layout;
	private TextView txtReadingAnswer;// 正确答案
	private TextView txtReadingAnswerOfUser;// 用户所答的答案
	private static int questionAmount;// 记录的题目数目
	private static int dataCount;

	// public static ReadingViewAnswer INSTANCE;
	TabHost tabHost;
	// ------布局方式--------
	private final LinearLayout.LayoutParams LP_FF = new LinearLayout.LayoutParams(
			LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	private final LinearLayout.LayoutParams LP_FW = new LinearLayout.LayoutParams(
			LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	private final LinearLayout.LayoutParams LP_WW = new LinearLayout.LayoutParams(
			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	private Cursor cur;

	private static int userRightAnswer = 0;
	private static int userWrongAnswer = 0;
	private Button userAnswerDiolog;
	private static String[] theCorrectAnswer = new String[200];
	Context context;

	public ReadingViewAnswer(Context c) {
		context = c;
	}

	public void setView(View v) {
		DBReadingOfQuestion db = new DBReadingOfQuestion(context);
		questionAmount = 0;

		// INSTANCE = this;
		db.open();
		Log.v(TAG, "Activity State: checkTableExists()");
		if (db.checkTableExists("Reading_Comprehension_Question")) {
			// ---取出所有数据---
			cur = db.getItemFromYM(AppVariable.Common.YearMonth);
		} else {
			Toast.makeText(context, "数据不存在", Toast.LENGTH_SHORT).show();
		}

		dataCount = cur.getCount();
		ScrollView ReadingViewScroll = (ScrollView) v
				.findViewById(R.id.scr_reading_answer);
		layout = new LinearLayout(context);

		layout.setOrientation(LinearLayout.VERTICAL); // 控件对其方式为垂直排列
		layout.setPadding(10, 0, 10, 0);
		int dataCount = cur.getCount();
		int NUMBER = 0;
		while (NUMBER < dataCount) {
			NUMBER++;
			myLayout = new LinearLayout(context);
			myLayout.setOrientation(LinearLayout.HORIZONTAL);// 水平布局
			myLayout.setLayoutParams(LP_FW);
			myLayout.setBackgroundResource(R.drawable.login_input);// 设置背景
			myLayout.setId(NUMBER + 1000);

			txtQuestionNumber = new TextView(context);
			txtReadingAnswer = new TextView(context);// 正确答案
			txtReadingAnswerOfUser = new TextView(context);// 用户答案
			txtReadingAnswerOfUser.setId(NUMBER);
			setAnswerText(txtQuestionNumber, txtReadingAnswer,
					txtReadingAnswerOfUser, context);
			myLayout.addView(txtReadingAnswer);
			myLayout.addView(txtReadingAnswerOfUser);
			layout.addView(txtQuestionNumber);
			layout.addView(myLayout);
			cur.moveToNext();
		}
		ReadingViewScroll.addView(layout);
		cur.close();
		db.close();
	}

	private void setAnswerText(TextView txtQuestionNumber,
			TextView txtReadingAnswer, TextView txtReadingAnswerOfUser,
			Context context) {

		// 记录题目数目
		questionAmount++;
		txtQuestionNumber.setText(String.valueOf(questionAmount));
		// txtQuestionNumber.setText(cur.getString(cur
		// .getColumnIndex("QuestionNumber")));
		txtQuestionNumber.setTextSize(20);
		txtQuestionNumber.setTextColor(context.getResources().getColor(
				R.color.red));
		String theAnswer = ReadingViewQuestion.readingAnswer_All[Integer
				.parseInt((String) txtQuestionNumber.getText())];
		theCorrectAnswer[Integer.parseInt((String) txtQuestionNumber.getText())] = cur
				.getString(cur.getColumnIndex("Answer"));
		if (theAnswer == null) {
			theAnswer = "未作答";
		}
		txtReadingAnswer.setText("答案:"
				+ cur.getString(cur.getColumnIndex("Answer")) + "  " + "你的作答:");
		txtReadingAnswer.setTextSize(17);

		txtReadingAnswerOfUser.setText(theAnswer);
		txtReadingAnswerOfUser.setTextSize(17);

		// 设置背景
		// txtReadingAnswer.setBackgroundResource(R.drawable.login_input);
	}

	private void showDialog(Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle("答案统计");
		int notAnswer = questionAmount - userRightAnswer - userWrongAnswer;
		int score = 0;
		if (questionAmount == 0) {
			score = 0;
		} else {
			score = userRightAnswer * 100 / questionAmount;
		}
		builder.setMessage("正确：" + userRightAnswer + "\n" + "错误："
				+ userWrongAnswer + "\n" + "未答：" + notAnswer + "\n" + "分数："
				+ score + "分");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// setTitle("确认");
			}
		});

		builder.show();
	}

	public void reFresh(View v) {
		int NUM = 0;
		String theAnswer;
		userRightAnswer = 0;
		userWrongAnswer = 0;
		// ----------------------------------------------------------------
		TableReadingOfQuestion t = new TableReadingOfQuestion();
		TableReadingOfQuestionWrong tw = new TableReadingOfQuestionWrong();
		FinalDb fdb = FinalDb.create(context, "cetp");
		List<TableReadingOfQuestion> lisOfQList = fdb.findAllByWhere(
				TableReadingOfQuestion.class, "YYYYMM="
						+ AppVariable.Common.YearMonth);

		Log.v(TAG, "questionAmount=" + questionAmount + "  dataCount="
				+ dataCount);
		// ----------------------------------------------------------------
		// 判断数据库是否有数据
		if (dataCount != 0) {
			while (NUM != questionAmount) {
				NUM++;
				theAnswer = ReadingViewQuestion.readingAnswer_All[NUM];
				txtReadingAnswerOfUser = (TextView) v.findViewById(NUM);
				myLayout = (LinearLayout) v.findViewById(NUM + 1000);
				if (theAnswer == null) {
					theAnswer = "未作答";
				} else if (!theAnswer.equals(theCorrectAnswer[NUM])) {
					myLayout.setBackgroundResource(R.drawable.login_input_dark);// 设置背景
					txtReadingAnswerOfUser.setTextColor(context.getResources()
							.getColor(R.color.red));
					// 错题写回数据库--------------------------------------------------
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
						s = lisOfQList.get(NUM - 1).getQuestionText();
						t.setQuestionText(s);
						tw.setQuestionText(s);
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
							t.setComments("w");// 错误wrong
						} else {
							t.setComments("wf");// 错误wrong和收藏favorite
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
					myLayout.setBackgroundResource(R.drawable.login_input_light);// 设置背景
					txtReadingAnswerOfUser.setTextColor(context.getResources()
							.getColor(R.color.black));
					userRightAnswer++;
				}
				txtReadingAnswerOfUser.setText(theAnswer);
			}
		}
		cur.close();

		userAnswerDiolog = (Button) v.findViewById(R.id.user_reading_diolog);
		userAnswerDiolog.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(context);
			}

		});
		Log.v(TAG, "记录数量：" + (lisOfQList != null ? lisOfQList.size() : 0));

		Date dateNow = new Date(System.currentTimeMillis());// 获取当前时间
		/** 用于保存错误率 */
		DBWrongStat dbWrong = new DBWrongStat(context);
		dbWrong.open();
		int wrongstat = userRightAnswer + userWrongAnswer > 0 ? userWrongAnswer
				* 100 / (userRightAnswer + userWrongAnswer) : -1;
		// 年月，错题数，题目总数
		String[] result = new String[] {
				DateUtils.dateToStr("yyyyMMddHHmmss", dateNow),
				String.valueOf(userWrongAnswer),
				String.valueOf(questionAmount), String.valueOf(wrongstat) };
		if (wrongstat >= 0)
			dbWrong.insertItem(result[0], result[1], result[2], result[3]);
		dbWrong.close();
		// -----------------------------------------------------------------------------------
	}
}
