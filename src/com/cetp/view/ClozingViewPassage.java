package com.cetp.view;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioGroup.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cetp.R;
import com.cetp.database.DBClozingOfText;

/**
 * 完型文章界面
 * 
 * @author 顾博君
 * @date 2013/5/12
 */
public class ClozingViewPassage extends Activity {
	private String TAG = "ClozingViewPassage";
	/** 题号 */
	private TextView txtQuestionNumber;
	/** 原文 */
	private TextView originalText;

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
	/** 完型文章数据 */
	DBClozingOfText db = new DBClozingOfText(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "Activity State: onCreate()");
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.clozingview_passage);

		db.open();
		Log.v(TAG, "Activity State: checkTableExists()");
		if (db.checkTableExists("Clozing_Passage")) {
			// ---取出所有数据---
			cur = db.getAllItem();

			ScrollView listeningViewScroll = (ScrollView) findViewById(R.id.scr_clozing_passage);
			LinearLayout layout = new LinearLayout(this);

			layout.setOrientation(LinearLayout.VERTICAL); // 控件对其方式为垂直排列
			layout.setPadding(10, 0, 10, 0);// 设置padding
			while (cur.moveToNext()) {
				System.out.println(cur.getCount());
				txtQuestionNumber = new TextView(this);
				// 原文
				originalText = new TextView(this);
				// 加入背景
				originalText.setBackgroundResource(R.drawable.login_input);
				// 设置宽和高的布局
				originalText.setLayoutParams(LP_FW);
				setQuestionText(txtQuestionNumber, originalText, this);

				layout.addView(txtQuestionNumber);
				layout.addView(originalText);
			}
			listeningViewScroll.addView(layout);
			cur.close();
			db.close();
		} else {
			Toast.makeText(ClozingViewPassage.this, "数据不存在", Toast.LENGTH_SHORT)
					.show();
		}
	}

	private void setQuestionText(TextView txtQuestionNumber,
			TextView questionText, Context context) {
		txtQuestionNumber.setText("("
				+ cur.getString(cur.getColumnIndex("QuestionStartNumber"))
				+ "~" + cur.getString(cur.getColumnIndex("QuestionEndNumber"))
				+ ")");
		txtQuestionNumber.setTextSize(20);
		txtQuestionNumber.setTextColor(getResources().getColor(R.color.red));

		questionText.setText(cur.getString(cur.getColumnIndex("PassageText")));
		questionText.setTextSize(17);

	}
}
