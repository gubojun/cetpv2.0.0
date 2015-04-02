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
import com.cetp.action.AppVariable;
import com.cetp.database.DBVocabulary;

/**
 * 词汇答案界面
 * 
 * @author 胡灿华
 * @data 2013/5/9
 */
public class VocabularyViewAnswer {
	private String TAG = "VocabularyViewAnswer";
	/** 题号 */
	private TextView txtQuestionNumber;
	/** 水平线性布局 */
	private LinearLayout myLayout;
	/** 总体布局 */
	private LinearLayout layout;
	/** 正确答案 */
	private TextView txtVocabularyAnswer;
	/** 用户所答的答案 */
	private TextView txtVocabularyAnswerOfUser;
	/** 记录题目的数目 */
	private static int questionAmount;

	private static int userWrongAnswer = 0;

	private static int userRightAnswer = 0;
	/** 记录数据的数目 */
	private static int dataCount;

	private Button userAnswerDiolog;

	TabHost tabHost;
	// ------布局方式--------
	@SuppressWarnings("unused")
	private final LinearLayout.LayoutParams LP_FF = new LinearLayout.LayoutParams(
			LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	@SuppressWarnings("unused")
	private final LinearLayout.LayoutParams LP_FW = new LinearLayout.LayoutParams(
			LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	@SuppressWarnings("unused")
	private final LinearLayout.LayoutParams LP_WW = new LinearLayout.LayoutParams(
			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	private Cursor cur;

	private static String[] theCorrectAnswer = new String[200];
	Context context;

	public VocabularyViewAnswer(Context c) {
		context = c;
	}

	public void setView(View v) {
		DBVocabulary db = new DBVocabulary(context);
		// 初始化题目数目
		questionAmount = 0;

		db.open();
		Log.v(TAG, "Activity State: checkTableExists()");
		if (db.checkTableExists("Vocabulary_and_Structure")) {
			// ---取出所有数据---
			cur = db.getItemFromYM(AppVariable.Common.YearMonth);
		} else {
			Toast.makeText(context, "数据不存在", Toast.LENGTH_SHORT).show();
		}

		ScrollView VocabularyViewScroll = (ScrollView) v
				.findViewById(R.id.scr_vocabularyview_answer);
		layout = new LinearLayout(context);

		layout.setOrientation(LinearLayout.VERTICAL); // 控件对其方式为垂直排列
		layout.setPadding(10, 0, 10, 0);
		int NUMBER = 0;
		// 记录数据的数目
		dataCount = cur.getCount();
		while (NUMBER < dataCount) {
			NUMBER++;
			// 每道题的线性布局
			myLayout = new LinearLayout(context);
			myLayout.setOrientation(LinearLayout.HORIZONTAL);// 水平布局
			myLayout.setLayoutParams(LP_FW);
			myLayout.setBackgroundResource(R.drawable.login_input);// 设置背景

			txtQuestionNumber = new TextView(context);
			txtVocabularyAnswer = new TextView(context);// 正确答案
			txtVocabularyAnswerOfUser = new TextView(context);// 用户答案
			txtVocabularyAnswerOfUser.setId(NUMBER);
			setAnswerText(txtQuestionNumber, txtVocabularyAnswer,
					txtVocabularyAnswerOfUser, NUMBER, context);
			// 每道题的线性布局包括题目的答案，用户的答案
			myLayout.addView(txtVocabularyAnswer);
			myLayout.addView(txtVocabularyAnswerOfUser);

			// 题号加入总的线性布局
			layout.addView(txtQuestionNumber);
			// 题目布局加入总的线性布局
			layout.addView(myLayout);
			cur.moveToNext();
		}
		VocabularyViewScroll.addView(layout);
		cur.close();
		db.close();
	}

	/**
	 * 设置答案文本
	 * 
	 * @param txtQuestionNumber
	 * @param txtVocabularyAnswer
	 * @param txtVocabularyAnswrOfUser
	 * @param context
	 */
	private void setAnswerText(TextView txtQuestionNumber,
			TextView txtVocabularyAnswer, TextView txtVocabularyAnswerOfUser,
			int NUMBER, Context context) {

		// 记录题目数目
		questionAmount++;
		txtQuestionNumber.setText(cur.getString(cur
				.getColumnIndex("QuestionNumber")));
		txtQuestionNumber.setTextSize(20);
		txtQuestionNumber.setTextColor(context.getResources().getColor(
				R.color.red));
		String theAnswer = VocabularyView.vocabularyAnswer_All[Integer
				.parseInt((String) txtQuestionNumber.getText()) - 40];
		theCorrectAnswer[NUMBER] = cur.getString(cur.getColumnIndex("Answer"));
		txtVocabularyAnswer.setText("答案:" + theCorrectAnswer[NUMBER] + "  "
				+ "你的作答:");
		txtVocabularyAnswer.setTextSize(17);

		txtVocabularyAnswerOfUser.setText(theAnswer);
		txtVocabularyAnswerOfUser.setTextSize(17);

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
				+ userWrongAnswer + "\n" + "未答：" + notAnswer + "\n分数：" + score
				+ "分");
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
		// 当有数据时更新题目
		if (dataCount > 0) {
			while (NUM != questionAmount) {
				NUM++;
				theAnswer = VocabularyView.vocabularyAnswer_All[NUM];
				txtVocabularyAnswerOfUser = (TextView) v.findViewById(NUM);
				if (theAnswer == null) {
					theAnswer = "未作答";
				} else if (!theAnswer.equals(theCorrectAnswer[NUM])) {
					txtVocabularyAnswerOfUser.setBackgroundColor(context
							.getResources().getColor(R.color.red));
					userWrongAnswer++;
				} else {
					txtVocabularyAnswerOfUser
							.setBackgroundColor(context.BIND_AUTO_CREATE);
					userRightAnswer++;
				}
				txtVocabularyAnswerOfUser.setText(theAnswer);
			}
		}

		userAnswerDiolog = (Button) v.findViewById(R.id.user_vocabulary_diolog);
		userAnswerDiolog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(context);
			}
		});
	}
}
