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
 * @author ���ӻ�
 * @data 2013/5/9
 */
public class ReadingViewAnswer extends Activity {
	private String TAG = "ReadingViewQuestiontext";
	private TextView txtQuestionNumber;// ���
	private LinearLayout myLayout;// ˮƽ���Բ���
	private LinearLayout layout;
	private TextView txtReadingAnswer;// ��ȷ��
	private TextView txtReadingAnswerOfUser;// �û�����Ĵ�
	private static int questionAmount;// ��¼����Ŀ��Ŀ
	private static int dataCount;

//	public static ReadingViewAnswer INSTANCE;
	TabHost tabHost;
	// ------���ַ�ʽ--------
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
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		setContentView(R.layout.readingview_answer);
		questionAmount = 0;
		
		
//		INSTANCE = this;
		db.open();
		Log.v(TAG, "Activity State: checkTableExists()");
		if (db.checkTableExists("Reading_Comprehension_Question")) {
			// ---ȡ����������---
			cur = db.getAllItem();
		} else {
			Toast.makeText(ReadingViewAnswer.this, "���ݲ�����", Toast.LENGTH_SHORT)
					.show();
		}

		
		dataCount = cur.getCount();
		ScrollView ReadingViewScroll = (ScrollView) findViewById(R.id.scr_reading_answer);
		layout = new LinearLayout(this);

		layout.setOrientation(LinearLayout.VERTICAL); // �ؼ����䷽ʽΪ��ֱ����
		layout.setPadding(10, 0, 10, 0);
		int NUMBER = 0;
		while (cur.moveToNext()) {
			NUMBER++;
			myLayout = new LinearLayout(this);
			myLayout.setOrientation(LinearLayout.HORIZONTAL);// ˮƽ����
			myLayout.setLayoutParams(LP_FW);
			myLayout.setBackgroundResource(R.drawable.login_input);// ���ñ���
			
			txtQuestionNumber = new TextView(this);
			txtReadingAnswer = new TextView(this);// ��ȷ��
			txtReadingAnswerOfUser = new TextView(this);// �û���
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

		// ��¼��Ŀ��Ŀ
		questionAmount++;
		txtQuestionNumber.setText(cur.getString(cur
				.getColumnIndex("QuestionNumber")));
		txtQuestionNumber.setTextSize(20);
		txtQuestionNumber.setTextColor(getResources().getColor(R.color.red));
		String theAnswer = ReadingViewQuestion.readingAnswer_All[Integer
				.parseInt((String) txtQuestionNumber.getText()) - 21];
		if (theAnswer == null) {
			theAnswer = "δ����";
		}
		txtReadingAnswer.setText("��:"
				+ cur.getString(cur.getColumnIndex("Answer")) + "  " + "�������:");
		txtReadingAnswer.setTextSize(17);

		txtReadingAnswerOfUser.setText(theAnswer);
		txtReadingAnswerOfUser.setTextSize(15);

		// ���ñ���
//		txtReadingAnswer.setBackgroundResource(R.drawable.login_input);
	}

	 private void showDialog(Context context) {  
	        AlertDialog.Builder builder = new AlertDialog.Builder(context);  
	        builder.setIcon(android.R.drawable.ic_dialog_info);  
	        builder.setTitle("��ͳ��");  
	        int notAnswer = questionAmount-userRightAnswer-userWrongAnswer;
	        int score = 0;
			if(questionAmount == 0){
				score = 0;
			}else{
				score = userRightAnswer*100/questionAmount;
			}
	        builder.setMessage("��ȷ��"+userRightAnswer+"\n"+"����"+userWrongAnswer+"\n"+"δ��"+notAnswer+"\n"+"������"+score+"��");
	        builder.setPositiveButton("ȷ��",  
	                new DialogInterface.OnClickListener() {  
	                    public void onClick(DialogInterface dialog, int whichButton) {  
//	                        setTitle("ȷ��");  
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
					theAnswer = "δ����";
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
		// ��ʼ������
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
