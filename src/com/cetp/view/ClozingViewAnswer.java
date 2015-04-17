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

	private TextView txtQuestionNumber;// 题号
	/** 正确答案 */
	private TextView txtClozingAnswer;
	/** 用户所答的答案 */
	private TextView txtClozingAnswerOfUser;
	/** 记录题目的数目 */
	private static int questionAmount;
	/** 记录数据的数目 */
	private static int dataCount;

	// ------布局方式--------
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
			// ---取出所有数据---
			cur = db.getItemFromYM(AppVariable.Common.YearMonth);
			cur.moveToFirst();
		} else {
			Toast.makeText(context, "数据不存在", Toast.LENGTH_SHORT).show();
		}

		ScrollView clozingViewScroll = (ScrollView) v
				.findViewById(R.id.scr_clozing_answer);
		layout = new LinearLayout(context);

		layout.setOrientation(LinearLayout.VERTICAL); // 控件对其方式为垂直排列
		layout.setPadding(10, 0, 10, 0);
		// 记录数据的数目
		dataCount = cur.getCount();
		int AnswerID = 0;
		while (AnswerID < dataCount) {
			AnswerID++;
			// 每道题的线性布局
			myLayout = new LinearLayout(context);
			myLayout.setOrientation(LinearLayout.HORIZONTAL);// 水平布局
			myLayout.setLayoutParams(LP_FW);
			myLayout.setBackgroundResource(R.drawable.login_input);// 设置背景
			myLayout.setId(AnswerID + 1000);

			txtQuestionNumber = new TextView(context);
			txtClozingAnswer = new TextView(context);// 正确答案
			txtClozingAnswerOfUser = new TextView(context);// 用户答案
			txtClozingAnswerOfUser.setId(AnswerID);
			setAnswerText(txtQuestionNumber, txtClozingAnswer,
					txtClozingAnswerOfUser, AnswerID, context);
			// 每道题的线性布局包括题目的答案，用户的答案
			myLayout.addView(txtClozingAnswer);
			myLayout.addView(txtClozingAnswerOfUser);
			// 设置背景
			myLayout.setBackgroundResource(R.drawable.login_input);
			// 题号加入总的线性布局
			layout.addView(txtQuestionNumber);
			// 题目布局加入总的线性布局
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
		/** 记录正确答案 */
		theCorrectAnswer[Integer.parseInt((String) txtQuestionNumber.getText())] = cur
				.getString(cur.getColumnIndex("Answer"));
		String questionAnswer = cur.getString(cur.getColumnIndex("Answer"));
		txtClozingAnswer.setText("答案:" + questionAnswer + "  " + "你的作答:");
		txtClozingAnswer.setTextSize(17);

		txtListeningAnswerOfUser.setText(theAnswer);
		txtListeningAnswerOfUser.setTextSize(17);
		// 设置背景
		// if (questionAnswer.equals(theAnswer))
		// txtClozingAnswer.setBackgroundResource(R.drawable.login_input);
		// else
		// txtListeningAnswer.setBackgroundResource(R.drawable.optbg);
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

	/** 更新题目答案 */
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
		// 判断数据库是否有数据
		System.out.println(dataCount); // 判断数据库是否有数据
		if (dataCount != 0) {
			while (NUM != questionAmount) {
				NUM++;
				theAnswer = ClozingViewQuestion.clozingAnswer_All[NUM];
				txtClozingAnswerOfUser = (TextView) v.findViewById(NUM);
				myLayout = (LinearLayout) v.findViewById(NUM + 1000);
				if (theAnswer == null) {
					theAnswer = "未作答";
				} else if (!theAnswer.equals(theCorrectAnswer[NUM])) {
					myLayout.setBackgroundResource(R.drawable.login_input_dark);// 设置背景
					txtClozingAnswerOfUser.setTextColor(v.getResources()
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
					txtClozingAnswerOfUser.setTextColor(v.getResources()
							.getColor(R.color.black));
					userRightAnswer++;
				}
				txtClozingAnswerOfUser.setText(theAnswer);
			}
		}
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
