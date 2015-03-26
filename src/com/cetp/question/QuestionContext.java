package com.cetp.question;

import java.util.List;

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
import android.widget.Toast;

import com.cetp.R;
import com.cetp.action.AppVariable;
import com.cetp.database.TableListeningOfQuestionWrong;
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
	// ------���ַ�ʽ--------
	private final LinearLayout.LayoutParams LP_FW = new LinearLayout.LayoutParams(
			LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

	public QuestionContext(Context context, int NUMBER, Cursor cur) {
		super(context);
		/* mylayout������еĲ��ַ�ʽ */
		mylayout = new LinearLayout(context);
		mylayout.setOrientation(LinearLayout.HORIZONTAL);// ˮƽ���Բ���
		/* ��� */
		txtQuestionNumber = new TextView(context);
		/* ��ѡ�� */
		checkBoxText = new CheckBox(context);
		checkBoxText.setOnCheckedChangeListener(new CheckBoxListener());
		checkBoxText.setChecked(false);
		checkBoxText.setText("");// setText("���");
		checkBoxText.setTextColor(getResources().getColor(R.color.orange));
		checkBoxText.setTextSize(14);
		checkBoxText.setVisibility(VISIBLE);
		/* ��ѡ��ť�� */
		radiogroup = new RadioGroup(context);
		setRadioButton(radiogroup, txtQuestionNumber, checkBoxText, context,
				NUMBER, cur);
		radiogroup.setOnCheckedChangeListener(new myRadioGroupListener());
		radiogroup.setLayoutParams(LP_FW);
		radiogroup.setVisibility(View.VISIBLE);
		// ���ñ���
		radiogroup.setBackgroundResource(R.drawable.login_input);
		mylayout.addView(txtQuestionNumber);
		mylayout.addView(checkBoxText);
		this.setOrientation(LinearLayout.VERTICAL);// ��ֱˮƽ���Բ���
		this.addView(mylayout);
		if (AppVariable.Common.TypeOfView == 1
				|| AppVariable.Common.TypeOfView == 3) {
			/* ��Ŀ */
			txtQuestionText = new TextView(context);
			txtQuestionText.setText(cur.getString(cur
					.getColumnIndex("QuestionText")));
			// ���ñ���
			txtQuestionText.setBackgroundResource(R.drawable.login_input);
			this.addView(txtQuestionText);
		}
		this.addView(radiogroup);
	}

	public QuestionContext(Context context, int NUMBER, List<?> list) {
		super(context);
		/* mylayout������еĲ��ַ�ʽ */
		mylayout = new LinearLayout(context);
		mylayout.setOrientation(LinearLayout.HORIZONTAL);// ˮƽ���Բ���
		/* ��� */
		txtQuestionNumber = new TextView(context);
		/* ��ѡ�� */
		checkBoxText = new CheckBox(context);
		checkBoxText.setOnCheckedChangeListener(new CheckBoxListener());
		checkBoxText.setChecked(false);
		checkBoxText.setText("");// setText("���");
		checkBoxText.setTextColor(getResources().getColor(R.color.orange));
		checkBoxText.setTextSize(14);
		checkBoxText.setVisibility(VISIBLE);
		/* ��ѡ��ť�� */
		radiogroup = new RadioGroup(context);
		setRadioButton(radiogroup, txtQuestionNumber, checkBoxText, context,
				NUMBER, list);
		radiogroup.setOnCheckedChangeListener(new myRadioGroupListener());
		radiogroup.setLayoutParams(LP_FW);
		radiogroup.setVisibility(View.VISIBLE);
		// ���ñ���
		radiogroup.setBackgroundResource(R.drawable.login_input);
		mylayout.addView(txtQuestionNumber);
		mylayout.addView(checkBoxText);
		this.setOrientation(LinearLayout.VERTICAL);// ��ֱˮƽ���Բ���
		this.addView(mylayout);
		if (AppVariable.Common.TypeOfView == 1) {
			txtQuestionText = new TextView(context);
			//txtQuestionText.setText(((TableListeningOfQuestionWrong)list.get(NUMBER-1)).;
		} else if (AppVariable.Common.TypeOfView == 3) {
			/* ��Ŀ */
			txtQuestionText = new TextView(context);
//			txtQuestionText.setText(cur.getString(cur
//					.getColumnIndex("QuestionText")));
			// ���ñ���
			txtQuestionText.setBackgroundResource(R.drawable.login_input);
			this.addView(txtQuestionText);
		}
		this.addView(radiogroup);
	}

	/**
	 * @author ���ӻ�
	 * @param radiogroup
	 *            ����һ����ѡ��ť��
	 * @param texQuestionNumber
	 *            ����һ�����
	 * @param checkBoxtext
	 *            ��ѡ����Ҫ���ܣ�����һ��id
	 * @param context
	 *            �����ʾ������
	 * @param num
	 *            ��ʾ�ڼ�����ѡ��ť�飬Ҳ��ʾ����ID��
	 *            ��������е�ID��radioButton��radiogroup�г�ͻ����ÿ��radiobutton��ID��1��
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

		txtQuestionNumber.setText("(" + NUMBER + ")." + "��Ĵ�:");
		txtQuestionNumber.setId(radiogroup.getId() + 0x10000000);
		checkBoxText.setId(radiogroup.getId() + 0x20000000);
	}

	private void setRadioButton(RadioGroup radiogroup,
			TextView texQuestionNumber, CheckBox checkBoxText, Context context,
			int NUMBER, List<?> list) {
		radiogroup.setId(NUMBER * 10);

		RadioButton rdbSelectionA = new RadioButton(context);
		rdbSelectionA.setId(radiogroup.getId() + 1);
		rdbSelectionA.setText("A)."
				+ ((TableListeningOfQuestionWrong) list.get(NUMBER - 1))
						.getSelectionA());
		rdbSelectionA.setChecked(false);
		radiogroup.addView(rdbSelectionA);

		RadioButton rdbSelectionB = new RadioButton(context);
		rdbSelectionB.setId(radiogroup.getId() + 2);
		rdbSelectionB.setText("B)."
				+ ((TableListeningOfQuestionWrong) list.get(NUMBER - 1))
						.getSelectionB());
		rdbSelectionB.setChecked(false);
		radiogroup.addView(rdbSelectionB);

		RadioButton rdbSelectionC = new RadioButton(context);
		rdbSelectionC.setId(radiogroup.getId() + 3);
		rdbSelectionC.setText("C)."
				+ ((TableListeningOfQuestionWrong) list.get(NUMBER - 1))
						.getSelectionC());
		rdbSelectionC.setChecked(false);
		radiogroup.addView(rdbSelectionC);

		RadioButton rdbSelectionD = new RadioButton(context);
		rdbSelectionD.setId(radiogroup.getId() + 4);
		rdbSelectionD.setText("D)."
				+ ((TableListeningOfQuestionWrong) list.get(NUMBER - 1))
						.getSelectionD());
		rdbSelectionD.setChecked(false);
		radiogroup.addView(rdbSelectionD);

		txtQuestionNumber.setText("(" + NUMBER + ")." + "��Ĵ�:");
		txtQuestionNumber.setId(radiogroup.getId() + 0x10000000);
		checkBoxText.setId(radiogroup.getId() + 0x20000000);
	}

	// -------------��ѡ��ť��ĵ���¼�---------------
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
			// String[] Question_All = null;
			String[] Answer_All = null;
			switch (AppVariable.Common.TypeOfView) {
			case 0:
				// Question_All = ListeningViewQuestion.listeningQuestion_All;
				Answer_All = ListeningViewQuestion.listeningAnswer_All;
				break;
			case 1:
				// Question_All = ClozingViewQuestion.clozingQuestion_All;
				Answer_All = ClozingViewQuestion.clozingAnswer_All;
				break;
			case 2:
				// Question_All = ReadingViewQuestion.readingQuestion_All;
				Answer_All = ReadingViewQuestion.readingAnswer_All;
				break;

			case 3:
				// Question_All = VocabularyView.vocabularyQuestion_All;
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
	 * @author ���ӻ�
	 * 
	 *         ����CheckBox�ؼ�
	 */
	class CheckBoxListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			/***************************************/
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
			/*******************************************/
			radiogroup = (RadioGroup) findViewById(buttonView.getId() - 0x20000000);
			if (isChecked == true) {
				// radiogroup.setBackgroundColor(getResources().getColor(
				// R.color.gray));
				radiogroup.setBackgroundResource(R.drawable.login_input_dark);
			} else if (Answer_All[radiogroup.getId() / 10] != null) {
				radiogroup.setBackgroundResource(R.drawable.login_input_light);
			} else {
				radiogroup.setBackgroundResource(R.drawable.login_input);
				checkBoxText = (CheckBox) findViewById(buttonView.getId());
				checkBoxText.setChecked(false);
			}
		}
	}
}