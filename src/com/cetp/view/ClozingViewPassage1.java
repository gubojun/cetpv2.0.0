package com.cetp.view;

import com.cetp.R;
import com.cetp.database.DBClozingOfText;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.LayoutParams;

public class ClozingViewPassage1 {
	private String TAG = "ClozingViewPassage";
	/** ��� */
	private TextView txtQuestionNumber;
	/** ԭ�� */
	private TextView originalText;

	// ------���ַ�ʽ--------
	@SuppressWarnings("unused")
	private final LinearLayout.LayoutParams LP_FF = new LinearLayout.LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
	private final LinearLayout.LayoutParams LP_FW = new LinearLayout.LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
	@SuppressWarnings("unused")
	private final LinearLayout.LayoutParams LP_WW = new LinearLayout.LayoutParams(
			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	private Cursor cur;

	Context context;

	public ClozingViewPassage1(Context c) {
		context = c;
	}

	public void setView(View v) {
		/** ������������ */
		DBClozingOfText db = new DBClozingOfText(context);

		db.open();
		Log.v(TAG, "Activity State: checkTableExists()");
		if (db.checkTableExists("Clozing_Passage")) {
			// ---ȡ����������---
			cur = db.getAllItem();

			ScrollView listeningViewScroll = (ScrollView) v
					.findViewById(R.id.scr_clozing_passage);
			LinearLayout layout = new LinearLayout(context);

			layout.setOrientation(LinearLayout.VERTICAL); // �ؼ����䷽ʽΪ��ֱ����
			layout.setPadding(10, 0, 10, 0);// ����padding
			while (cur.moveToNext()) {
				System.out.println(cur.getCount());
				txtQuestionNumber = new TextView(context);
				// ԭ��
				originalText = new TextView(context);
				// ���뱳��
				originalText.setBackgroundResource(R.drawable.login_input);
				// ���ÿ�͸ߵĲ���
				originalText.setLayoutParams(LP_FW);
				setQuestionText(txtQuestionNumber, originalText, context);

				layout.addView(txtQuestionNumber);
				layout.addView(originalText);
			}
			listeningViewScroll.addView(layout);
			cur.close();
			db.close();
		} else {
			Toast.makeText(context, "���ݲ�����", Toast.LENGTH_SHORT).show();
		}
	}

	private void setQuestionText(TextView txtQuestionNumber,
			TextView questionText, Context context) {
		txtQuestionNumber.setText("("
				+ cur.getString(cur.getColumnIndex("QuestionStartNumber"))
				+ "~" + cur.getString(cur.getColumnIndex("QuestionEndNumber"))
				+ ")");
		txtQuestionNumber.setTextSize(20);
		txtQuestionNumber.setTextColor(context.getResources().getColor(
				R.color.red));

		questionText.setText(cur.getString(cur.getColumnIndex("PassageText")));
		questionText.setTextSize(17);

	}
}
