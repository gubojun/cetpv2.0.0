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
import com.cetp.database.DBReadingOfQuestion;

/**
 * 
 * @author 胡灿华
 * @data 2013/5/9
 */
public class ReadingViewAnswer extends Activity {
	private String TAG = "ReadingViewQuestiontext";
	private TextView txtQuestionNumber;// 题号
	private LinearLayout myLayout;// 水平线性布局
	private LinearLayout layout;
	private TextView txtReadingAnswer;// 正确答案
	private TextView txtReadingAnswerOfUser;// 用户所答的答案
	private static int questionAmount;// 记录的题目数目
	private static int dataCount;

//	public static ReadingViewAnswer INSTANCE;
	TabHost tabHost;
	// ------布局方式--------
	private final LinearLayout.LayoutParams LP_FF = new LinearLayout.LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
	private final LinearLayout.LayoutParams LP_FW = new LinearLayout.LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
	private final LinearLayout.LayoutParams LP_WW = new LinearLayout.LayoutParams(
			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	private Cursor cur;

	DBReadingOfQuestion db = new DBReadingOfQuestion(this);
	private static int userRightAnswer = 0;
	private static int userWrongAnswer = 0;
	private Button userAnswerDiolog;
	private static String[] theCorrectAnswer = new String[200];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "Activity State: onCreate()");
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.readingview_answer);
		questionAmount = 0;
		
		
//		INSTANCE = this;
		db.open();
		Log.v(TAG, "Activity State: checkTableExists()");
		if (db.checkTableExists("Reading_Comprehension_Question")) {
			// ---取出所有数据---
			cur = db.getAllItem();
		} else {
			Toast.makeText(ReadingViewAnswer.this, "数据不存在", Toast.LENGTH_SHORT)
					.show();
		}

		
		dataCount = cur.getCount();
		ScrollView ReadingViewScroll = (ScrollView) findViewById(R.id.scr_reading_answer);
		layout = new LinearLayout(this);

		layout.setOrientation(LinearLayout.VERTICAL); // 控件对其方式为垂直排列
		layout.setPadding(10, 0, 10, 0);
		int NUMBER = 0;
		while (cur.moveToNext()) {
			NUMBER++;
			myLayout = new LinearLayout(this);
			myLayout.setOrientation(LinearLayout.HORIZONTAL);// 水平布局
			myLayout.setLayoutParams(LP_FW);
			myLayout.setBackgroundResource(R.drawable.login_input);// 设置背景
			
			txtQuestionNumber = new TextView(this);
			txtReadingAnswer = new TextView(this);// 正确答案
			txtReadingAnswerOfUser = new TextView(this);// 用户答案
			txtReadingAnswerOfUser.setId(NUMBER);
			setAnswerText(txtQuestionNumber, txtReadingAnswer,
					txtReadingAnswerOfUser, this);
			myLayout.addView(txtReadingAnswer);
			myLayout.addView(txtReadingAnswerOfUser);
			layout.addView(txtQuestionNumber);
			layout.addView(myLayout);
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
		txtQuestionNumber.setText(cur.getString(cur
				.getColumnIndex("QuestionNumber")));
		txtQuestionNumber.setTextSize(20);
		txtQuestionNumber.setTextColor(getResources().getColor(R.color.red));
		String theAnswer = ReadingViewQuestion.readingAnswer_All[Integer
				.parseInt((String) txtQuestionNumber.getText()) - 21];
		if (theAnswer == null) {
			theAnswer = "未作答";
		}
		txtReadingAnswer.setText("答案:"
				+ cur.getString(cur.getColumnIndex("Answer")) + "  " + "你的作答:");
		txtReadingAnswer.setTextSize(17);

		txtReadingAnswerOfUser.setText(theAnswer);
		txtReadingAnswerOfUser.setTextSize(15);

		// 设置背景
//		txtReadingAnswer.setBackgroundResource(R.drawable.login_input);
	}

	 private void showDialog(Context context) {  
	        AlertDialog.Builder builder = new AlertDialog.Builder(context);  
	        builder.setIcon(android.R.drawable.ic_dialog_info);  
	        builder.setTitle("答案统计");  
	        int notAnswer = questionAmount-userRightAnswer-userWrongAnswer;
	        int score = 0;
			if(questionAmount == 0){
				score = 0;
			}else{
				score = userRightAnswer*100/questionAmount;
			}
	        builder.setMessage("正确："+userRightAnswer+"\n"+"错误："+userWrongAnswer+"\n"+"未答："+notAnswer+"\n"+"分数："+score+"分");
	        builder.setPositiveButton("确认",  
	                new DialogInterface.OnClickListener() {  
	                    public void onClick(DialogInterface dialog, int whichButton) {  
//	                        setTitle("确认");  
	                    }  
	                });  
	        
	        builder.show();  
	    }  
	
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			finish();
			break;
		}

		return super.onKeyDown(keyCode, event);
	}

	private void refresh() {
		int NUM = 0;
		String theAnswer;
		userRightAnswer = 0;
		userWrongAnswer = 0;
		if (dataCount != 0) {
			while (NUM != questionAmount) {
				NUM++;
				theAnswer = ReadingViewQuestion.readingAnswer_All[NUM];
				txtReadingAnswerOfUser = (TextView) findViewById(NUM);
				if (theAnswer == null) {
					theAnswer = "未作答";
				}else if(!theAnswer.equals(theCorrectAnswer [NUM])){
					txtReadingAnswerOfUser.setBackgroundColor(getResources().getColor(R.color.red));
					userWrongAnswer++;
				}else{
					txtReadingAnswerOfUser.setBackgroundColor(BIND_AUTO_CREATE);
					userRightAnswer++;
				}
				txtReadingAnswerOfUser.setText(theAnswer);
			}
		}
		cur.close();
		
		userAnswerDiolog = (Button)findViewById(R.id.user_reading_diolog);
		userAnswerDiolog.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 showDialog(ReadingViewAnswer.this);
			}
			
		});
		// 初始化字体
//		TextSettingManager mSettingManager = new TextSettingManager(this);
//		mSettingManager.initText();
		// txtReadingAnswer.setTextColor(AppVariable.Font.G_TEXTCOLOR_PASSAGE);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.w(TAG, "onResume");
		// ******************
		refresh();
		// ******************
	}

//	@Override
//	protected void onStop() {
//		// TODO Auto-generated method stub
//		this.finish();
//		ReadingView.INSTANCE.finish();
//		super.onStop();
//	}

}
