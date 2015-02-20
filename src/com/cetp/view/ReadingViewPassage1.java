package com.cetp.view;

import com.cetp.R;
import com.cetp.database.DBReadingOfPassage;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.LayoutParams;

public class ReadingViewPassage1 {
	private String TAG = "ReadingViewQuestiontext";
	private TextView txtQuestionNumber;// 题号
	private TextView originalText;// 原文

	// ------布局方式--------
	private final LinearLayout.LayoutParams LP_FF = new LinearLayout.LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
	private final LinearLayout.LayoutParams LP_FW = new LinearLayout.LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
	private final LinearLayout.LayoutParams LP_WW = new LinearLayout.LayoutParams(
			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	private Cursor cur;

	Context context;
	public ReadingViewPassage1(Context c){
		context=c;
	}
	public void setView(View v){
		DBReadingOfPassage db = new DBReadingOfPassage(context);

		db.open();
		Log.v(TAG, "Activity State: checkTableExists()");
		if (db.checkTableExists("Reading_Comprehension_Passage")) {
			// ---取出所有数据---
			cur = db.getAllItem();
			// cur.moveToFirst();
		} else {
			Toast.makeText(context, "数据不存在",
					Toast.LENGTH_SHORT).show();
		}

		ScrollView readingViewScroll = (ScrollView) v.findViewById(R.id.scr_reading_questiontext);
		LinearLayout layout = new LinearLayout(context);

		layout.setOrientation(LinearLayout.VERTICAL); // 控件对其方式为垂直排列
		layout.setPadding(10, 0, 10, 0);// 设置padding
		while (cur.moveToNext()) {
			txtQuestionNumber = new TextView(context);
			originalText = new TextView(context);// 原文
			//加入背景
			originalText.setBackgroundResource(R.drawable.login_input);
			//设置宽和高的布局
			originalText.setLayoutParams(LP_FW);
			setQuestionText(txtQuestionNumber, originalText, context);

			layout.addView(txtQuestionNumber);
			layout.addView(originalText);
		}
		readingViewScroll.addView(layout);
		cur.close();
		db.close();
	}

	private void setQuestionText(TextView txtQuestionNumber,
			TextView questionText, Context context) {
		txtQuestionNumber.setText(cur.getString(cur
				.getColumnIndex("QuestionNumber"))
				+ "("
				+ cur.getString(cur.getColumnIndex("QuestionStartNumber"))
				+ "~"
				+ cur.getString(cur.getColumnIndex("QuestionEndNumber"))
				+ ")");
		txtQuestionNumber.setTextSize(20);
		txtQuestionNumber.setTextColor(context.getResources().getColor(R.color.red));

		questionText.setText(cur.getString(cur.getColumnIndex("PassageText")));
		questionText.setTextSize(17);

	}
}
