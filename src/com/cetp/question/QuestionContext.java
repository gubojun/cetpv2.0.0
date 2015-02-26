package com.cetp.question;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cetp.R;
import com.cetp.action.AppVariable;
import com.cetp.view.ClozingViewQuestion;
import com.cetp.view.ListeningViewQuestion;
import com.cetp.view.ReadingViewQuestion;
import com.cetp.view.VocabularyView;

public class QuestionContext extends LinearLayout {
	LinearLayout mylayout;
	TextView txtQuestionNumber;
	TextView txtQuestionText;
	CheckBox checkBoxText;
	RadioGroup radiogroup;
	// ------布局方式--------
	private final LinearLayout.LayoutParams LP_FW = new LinearLayout.LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

	public QuestionContext(Context context, int NUMBER, Cursor cur) {
		super(context);
		/* mylayout是题号行的布局方式 */
		mylayout = new LinearLayout(context);
		mylayout.setOrientation(LinearLayout.HORIZONTAL);// 水平线性布局
		/* 题号 */
		txtQuestionNumber = new TextView(context);
		/* 复选框 */
		checkBoxText = new CheckBox(context);
		checkBoxText.setOnCheckedChangeListener(new CheckBoxListener());
		checkBoxText.setChecked(false);
		checkBoxText.setText("标记");
		checkBoxText.setTextColor(getResources().getColor(R.color.orange));
		checkBoxText.setTextSize(14);
		checkBoxText.setVisibility(INVISIBLE);
		/* 单选按钮组 */
		radiogroup = new RadioGroup(context);
		setRadioButton(radiogroup, txtQuestionNumber, checkBoxText, context,
				NUMBER, cur);
		radiogroup.setOnCheckedChangeListener(new myRadioGroupListener());
		radiogroup.setLayoutParams(LP_FW);
		radiogroup.setVisibility(View.VISIBLE);
		// 设置背景
		radiogroup.setBackgroundResource(R.drawable.login_input);
		mylayout.addView(txtQuestionNumber);
		mylayout.addView(checkBoxText);
		this.setOrientation(LinearLayout.VERTICAL);// 垂直水平线性布局
		this.addView(mylayout);
		if (AppVariable.Common.TypeOfView == 1
				|| AppVariable.Common.TypeOfView == 3) {
			/* 题目 */
			txtQuestionText = new TextView(context);
			txtQuestionText.setText(cur.getString(cur
					.getColumnIndex("QuestionText")));
			// 设置背景
			txtQuestionText.setBackgroundResource(R.drawable.login_input);
			this.addView(txtQuestionText);
		}
		this.addView(radiogroup);
	}

	/**
	 * @author 胡灿华
	 * @param radiogroup
	 *            传递一个单选按钮组
	 * @param texQuestionNumber
	 *            传递一个题号
	 * @param checkBoxtext
	 *            复选框，主要功能：设置一个id
	 * @param context
	 *            这里表示整个类
	 * @param num
	 *            表示第几个单选按钮组，也表示它的ID号
	 *            这个方法中的ID因radioButton与radiogroup有冲突，故每个radiobutton的ID从1起
	 */
	private void setRadioButton(RadioGroup radiogroup,
			TextView texQuestionNumber, CheckBox checkBoxText, Context context,
			int NUMBER, Cursor cur) {
		radiogroup.setId(NUMBER * 10);

		RadioButton rdbSelectionA = new RadioButton(context);
		rdbSelectionA.setId(radiogroup.getId() + 1);
		rdbSelectionA.setText("A)."
				+ cur.getString(cur.getColumnIndex("SelectionA")));
		rdbSelectionA.setChecked(false);
		radiogroup.addView(rdbSelectionA);

		RadioButton rdbSelectionB = new RadioButton(context);
		rdbSelectionB.setId(radiogroup.getId() + 2);
		rdbSelectionB.setText("B)."
				+ cur.getString(cur.getColumnIndex("SelectionB")));
		rdbSelectionB.setChecked(false);
		radiogroup.addView(rdbSelectionB);

		RadioButton rdbSelectionC = new RadioButton(context);
		rdbSelectionC.setId(radiogroup.getId() + 3);
		rdbSelectionC.setText("C)."
				+ cur.getString(cur.getColumnIndex("SelectionC")));
		rdbSelectionC.setChecked(false);
		radiogroup.addView(rdbSelectionC);

		RadioButton rdbSelectionD = new RadioButton(context);
		rdbSelectionD.setId(radiogroup.getId() + 4);
		rdbSelectionD.setText("D)."
				+ cur.getString(cur.getColumnIndex("SelectionD")));
		rdbSelectionD.setChecked(false);
		radiogroup.addView(rdbSelectionD);

		txtQuestionNumber.setText("(" + NUMBER + ")." + "你的答案:");
		txtQuestionNumber.setId(radiogroup.getId() + 0x10000000);
		checkBoxText.setId(radiogroup.getId() + 0x20000000);
	}

	// -------------单选按钮组的点击事件---------------
	class myRadioGroupListener implements
			android.widget.RadioGroup.OnCheckedChangeListener {
		// CheckBox checkBoxText;
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			radiogroup.setBackgroundResource(R.drawable.login_input_light);
			txtQuestionNumber = (TextView) findViewById(group.getId() + 0x10000000);
			if (txtQuestionNumber.getText().charAt(
					txtQuestionNumber.length() - 1) != ':') {
				txtQuestionNumber.setText(txtQuestionNumber.getText()
						.subSequence(0,
								txtQuestionNumber.getText().length() - 1));
			}
			String[] Answer_All = null;
			switch (AppVariable.Common.TypeOfView) {
			case 0:
				Answer_All = ListeningViewQuestion.listeningAnswer_All;
				break;
			case 1:
				Answer_All = ReadingViewQuestion.readingAnswer_All;
				break;
			case 2:
				Answer_All = ClozingViewQuestion.clozingAnswer_All;
				break;
			case 3:
				Answer_All = VocabularyView.vocabularyAnswer_All;
				break;
			default:
				break;
			}

			switch (checkedId % 10) {
			case 1:
				txtQuestionNumber.append("A");
				Answer_All[group.getId() / 10] = "A";
				break;
			case 2:
				txtQuestionNumber.append("B");
				Answer_All[group.getId() / 10] = "B";
				break;
			case 3:
				txtQuestionNumber.append("C");
				Answer_All[group.getId() / 10] = "C";
				break;
			case 4:
				txtQuestionNumber.append("D");
				Answer_All[group.getId() / 10] = "D";
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 
	 * @author 胡灿华
	 * 
	 *         监听CheckBox控件
	 */
	class CheckBoxListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			radiogroup = (RadioGroup) findViewById(buttonView.getId() - 0x20000000);
			if (isChecked == true) {
				// radiogroup.setBackgroundColor(getResources().getColor(
				// R.color.gray));
				radiogroup.setBackgroundResource(R.drawable.login_input_light);
			} else {
				radiogroup.setBackgroundResource(R.drawable.login_input);
				checkBoxText = (CheckBox) findViewById(buttonView.getId());
				checkBoxText.setChecked(false);
			}
		}
	}
}