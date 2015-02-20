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
 * @author 胡灿华
 * @data 2013/5/9
 */
public class ListeningViewAnswer extends Activity {
	private String TAG = "ListeningViewQuestiontext";
	private TextView txtQuestionNumber;// 题号
	/** 正确答案 */
	private TextView txtListeningAnswer;
	/** 用户所答的答案 */
	private TextView txtListeningAnswerOfUser;
	/** 记录题目的数目 */
	private static int questionAmount;
	/** 记录数据的数目 */
	private int dataCount;
	/** 记录正确答案 */
	private static String[] theCorrectAnswer = new String[200];

	LinearLayout layout;
	LinearLayout myLayout;
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

	DBListeningOfQuestion db = new DBListeningOfQuestion(this);
	private static int userRightAnswer = 0;
	private static int userWrongAnswer = 0;
	private Button userAnswerDiolog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.w(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.listeningview_answer);
		questionAmount = 0;

		db.open();
		Log.v(TAG, "Activity State: checkTableExists()");
		if (db.checkTableExists("Listening_Comprehension_Passage")) {
			// ---取出所有数据---
			//cur = db.getAllItem();
			cur=db.getItemFromYM(AppVariable.Common.YearMonth);
			// cur.moveToFirst();
		} else {
			Toast.makeText(ListeningViewAnswer.this, "数据不存在",
					Toast.LENGTH_SHORT).show();
		}

		ScrollView listeningViewScroll = (ScrollView) findViewById(R.id.scr_listening_answer);
		layout = new LinearLayout(this);

		layout.setOrientation(LinearLayout.VERTICAL); // 控件对其方式为垂直排列
		layout.setPadding(10, 0, 10, 0);
		// 记录题目的数目
		dataCount = cur.getCount();
		int NUMBER = 0;
		while (cur.moveToNext()) {
			NUMBER++;
			// 每道题的线性布局
			myLayout = new LinearLayout(this);
			myLayout.setOrientation(LinearLayout.HORIZONTAL);// 水平布局
			myLayout.setLayoutParams(LP_FW);
			myLayout.setBackgroundResource(R.drawable.login_input);// 设置背景

			txtQuestionNumber = new TextView(this);
			txtListeningAnswer = new TextView(this);// 正确答案
			txtListeningAnswerOfUser = new TextView(this);// 用户答案
			txtListeningAnswerOfUser.setId(NUMBER);
			setAnswerText(txtQuestionNumber, txtListeningAnswer,
					txtListeningAnswerOfUser, NUMBER, this);
			// 每道题的线性布局包括题目的答案，用户的答案
			myLayout.addView(txtListeningAnswer);
			myLayout.addView(txtListeningAnswerOfUser);

			// 题号加入总的线性布局
			layout.addView(txtQuestionNumber);
			// 题目布局加入总的线性布局
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

		// 记录题目数目
		questionAmount++;
		txtQuestionNumber.setText(cur.getString(cur
				.getColumnIndex("QuestionNumber")));
		txtQuestionNumber.setTextSize(20);
		txtQuestionNumber.setTextColor(getResources().getColor(R.color.red));
		String theAnswer = ListeningViewQuestion.listeningAnswer_All[NUMBER];
		theCorrectAnswer[NUMBER] = cur.getString(cur.getColumnIndex("Answer"));

		txtListeningAnswer.setText("答案:" + theCorrectAnswer[NUMBER] + "  "
				+ "你的作答:");
		txtListeningAnswer.setTextSize(17);

		txtListeningAnswerOfUser.setText(theAnswer);
		txtListeningAnswerOfUser.setTextSize(17);
		// 设置背景
		// if (questionAnswer.equals(theAnswer))
		// txtListeningAnswer.setBackgroundResource(R.drawable.login_input);
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
	private void reFresh() {
		int NUM = 0;
		String theAnswer;

		userRightAnswer = 0;
		userWrongAnswer = 0;
		// 判断数据库是否有数据
		if (dataCount != 0) {
			while (NUM != questionAmount) {
				NUM++;
				theAnswer = ListeningViewQuestion.listeningAnswer_All[NUM];
				txtListeningAnswerOfUser = (TextView) findViewById(NUM);
				if (theAnswer == null) {
					theAnswer = "未作答";
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

		// 初始化字体
//		TextSettingManager mSettingManager = new TextSettingManager(this);
//		mSettingManager.initText();
		// txtListeningAnswer.setTextColor(AppVariable.Font.G_TEXTCOLOR_PASSAGE);
	}
}
