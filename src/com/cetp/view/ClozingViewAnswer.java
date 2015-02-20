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
 * 完型答案界面
 * 
 * @author 顾博君
 * @date 2013-5-13
 */
public class ClozingViewAnswer extends Activity {
	private String TAG = "ClozingViewAnswer";

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
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.clozingview_answer);
		questionAmount = 0;

		db.open();
		Log.v(TAG, "Activity State: checkTableExists()");
		if (db.checkTableExists("Clozing_Passage")) {
			// ---取出所有数据---
			cur = db.getAllItem();
			// cur.moveToFirst();
		} else {
			Toast.makeText(ClozingViewAnswer.this, "数据不存在", Toast.LENGTH_SHORT)
					.show();
		}

		ScrollView clozingViewScroll = (ScrollView) findViewById(R.id.scr_clozing_answer);
		layout = new LinearLayout(this);

		layout.setOrientation(LinearLayout.VERTICAL); // 控件对其方式为垂直排列
		layout.setPadding(10, 0, 10, 0);
		// 记录数据的数目
		dataCount = cur.getCount();
		int AnswerID = 0;
		while (cur.moveToNext()) {
			AnswerID++;
			// 每道题的线性布局
			myLayout = new LinearLayout(this);
			myLayout.setOrientation(LinearLayout.HORIZONTAL);// 水平布局
			myLayout.setLayoutParams(LP_FW);
			myLayout.setBackgroundResource(R.drawable.login_input);// 设置背景

			txtQuestionNumber = new TextView(this);
			txtClozingAnswer = new TextView(this);// 正确答案
			txtClozingAnswerOfUser = new TextView(this);// 用户答案
			txtClozingAnswerOfUser.setId(AnswerID);
			setAnswerText(txtQuestionNumber, txtClozingAnswer,
					txtClozingAnswerOfUser, AnswerID, this);
			// 每道题的线性布局包括题目的答案，用户的答案
			myLayout.addView(txtClozingAnswer);
			myLayout.addView(txtClozingAnswerOfUser);
			// 设置背景
			myLayout.setBackgroundResource(R.drawable.login_input);
			// 题号加入总的线性布局
			layout.addView(txtQuestionNumber);
			// 题目布局加入总的线性布局
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
		/** 记录正确答案 */
		theCorrectAnswer[AnswerID] = cur
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
		if(questionAmount == 0){
			score = 0;
		}else{
			score = userRightAnswer*100/questionAmount;
		}
		builder.setMessage("正确：" + userRightAnswer + "\n" + "错误："
				+ userWrongAnswer + "\n" + "未答：" + notAnswer+"\n"+"分数："+score+"分");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// setTitle("确认");
			}
		});

		builder.show();
	}

	/** 更新题目答案 */
	private void reFresh() {
		int NUM = 0;
		String theAnswer;

		userRightAnswer = 0;
		userWrongAnswer = 0;

		System.out.println(dataCount);
		// 判断数据库是否有数据
		if (dataCount != 0) {
			while (NUM != questionAmount) {
				NUM++;
				theAnswer = ClozingViewQuestion.clozingAnswer_All[NUM];
				txtClozingAnswerOfUser = (TextView) findViewById(NUM);
				if (theAnswer == null) {
					theAnswer = "未作答";
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
		// 初始化字体
		//TextSettingManager mSettingManager = new TextSettingManager(this);
		//mSettingManager.initText();
		// txtClozingAnswer.setTextColor(AppVariable.Font.G_TEXTCOLOR_PASSAGE);
	}
}
