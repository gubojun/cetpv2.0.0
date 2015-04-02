package com.cetp.question;

import java.util.Date;
import java.util.List;

import net.tsz.afinal.FinalDb;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
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
import com.cetp.database.TableListeningOfQuestion;
import com.cetp.database.TableListeningOfQuestionFavorite;
import com.cetp.database.TableListeningOfQuestionWrong;
import com.cetp.view.ClozingViewQuestion;
import com.cetp.view.ListeningViewQuestion;
import com.cetp.view.ReadingViewQuestion;
import com.cetp.view.VocabularyView;

public class QuestionContext extends LinearLayout {
	private final String TAG = "QuestionContext";
	LinearLayout mylayout;
	TextView txtQuestionNumber;
	TextView txtQuestionText;
	CheckBox checkBoxText;
	RadioGroup radiogroup;
	Context mContext;
	// ------���ַ�ʽ--------
	private final LinearLayout.LayoutParams LP_FW = new LinearLayout.LayoutParams(
			LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

	public QuestionContext(Context context, int NUMBER, Cursor cur) {
		super(context);
		mContext = context;
		/* mylayout������еĲ��ַ�ʽ */
		mylayout = new LinearLayout(context);
		mylayout.setOrientation(LinearLayout.HORIZONTAL);// ˮƽ���Բ���
		/* ��� */
		txtQuestionNumber = new TextView(context);
		/* ��ѡ�� */
		checkBoxText = new CheckBox(context);
		checkBoxText.setOnCheckedChangeListener(new CheckBoxListener());
		// checkBoxText.setChecked(true);
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
		// ������Ķ���TypeOfView==2�����ߴʻ㣨TypeOfView==3������Ŀ�ı�����ʾ
		if (AppVariable.Common.TypeOfView == 2
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
		String str = cur.getString(cur.getColumnIndex("Comments"));
		if (str.equals("f") || str.equals("wf"))
			checkBoxText.setChecked(true);
		else
			checkBoxText.setChecked(false);
	}

	public QuestionContext(Context context, int NUMBER, List<?> list, int kind) {
		super(context);
		/* mylayout������еĲ��ַ�ʽ */
		mylayout = new LinearLayout(context);
		mylayout.setOrientation(LinearLayout.HORIZONTAL);// ˮƽ���Բ���
		/* ��� */
		txtQuestionNumber = new TextView(context);
		/* ��ѡ�� */
		checkBoxText = new CheckBox(context);
		checkBoxText.setOnCheckedChangeListener(new CheckBoxListener());

		checkBoxText.setText("");// setText("���");
		checkBoxText.setTextColor(getResources().getColor(R.color.orange));
		checkBoxText.setTextSize(14);
		checkBoxText.setVisibility(INVISIBLE);

		/* ��ѡ��ť�� */
		radiogroup = new RadioGroup(context);
		if (kind == 1)
			setRadioButton(radiogroup, txtQuestionNumber, checkBoxText,
					context, NUMBER, list);
		else if (kind == 2)
			setRadioButtonF(radiogroup, txtQuestionNumber, checkBoxText,
					context, NUMBER, list);
		radiogroup.setOnCheckedChangeListener(new myRadioGroupListener());
		radiogroup.setLayoutParams(LP_FW);
		radiogroup.setVisibility(View.VISIBLE);
		// ���ñ���
		radiogroup.setBackgroundResource(R.drawable.login_input);
		mylayout.addView(txtQuestionNumber);
		mylayout.addView(checkBoxText);
		this.setOrientation(LinearLayout.VERTICAL);// ��ֱˮƽ���Բ���
		this.addView(mylayout);
		if (AppVariable.Common.TypeOfView == 2) {
			txtQuestionText = new TextView(context);
			// txtQuestionText.setText(((TableListeningOfQuestionWrong)list.get(NUMBER-1)).;
		} else if (AppVariable.Common.TypeOfView == 3) {
			/* ��Ŀ */
			txtQuestionText = new TextView(context);
			// txtQuestionText.setText(cur.getString(cur
			// .getColumnIndex("QuestionText")));
			// ���ñ���
			txtQuestionText.setBackgroundResource(R.drawable.login_input);
			this.addView(txtQuestionText);
		}
		this.addView(radiogroup);

		// if (kind == 1) {
		// String str = ((TableListeningOfQuestionWrong) list.get(NUMBER - 1))
		// .getComments();
		// if (str.equals("f") || str.equals("wf"))
		// checkBoxText.setChecked(true);
		// else
		// checkBoxText.setChecked(false);
		// } else if (kind == 2) {
		// checkBoxText.setChecked(true);
		// }
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

	private void setRadioButtonF(RadioGroup radiogroup,
			TextView texQuestionNumber, CheckBox checkBoxText, Context context,
			int NUMBER, List<?> list) {
		radiogroup.setId(NUMBER * 10);

		RadioButton rdbSelectionA = new RadioButton(context);
		rdbSelectionA.setId(radiogroup.getId() + 1);
		rdbSelectionA.setText("A)."
				+ ((TableListeningOfQuestionFavorite) list.get(NUMBER - 1))
						.getSelectionA());
		rdbSelectionA.setChecked(false);
		radiogroup.addView(rdbSelectionA);

		RadioButton rdbSelectionB = new RadioButton(context);
		rdbSelectionB.setId(radiogroup.getId() + 2);
		rdbSelectionB.setText("B)."
				+ ((TableListeningOfQuestionFavorite) list.get(NUMBER - 1))
						.getSelectionB());
		rdbSelectionB.setChecked(false);
		radiogroup.addView(rdbSelectionB);

		RadioButton rdbSelectionC = new RadioButton(context);
		rdbSelectionC.setId(radiogroup.getId() + 3);
		rdbSelectionC.setText("C)."
				+ ((TableListeningOfQuestionFavorite) list.get(NUMBER - 1))
						.getSelectionC());
		rdbSelectionC.setChecked(false);
		radiogroup.addView(rdbSelectionC);

		RadioButton rdbSelectionD = new RadioButton(context);
		rdbSelectionD.setId(radiogroup.getId() + 4);
		rdbSelectionD.setText("D)."
				+ ((TableListeningOfQuestionFavorite) list.get(NUMBER - 1))
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

			int NUM = radiogroup.getId() / 10;
			Log.v(TAG, "NUM=" + NUM);
			int dataCount = 0;
			// radiogroup.setBackgroundColor(getResources().getColor(
			// R.color.gray));

			// ----------------------------------------------------------------
			TableListeningOfQuestion t = new TableListeningOfQuestion();
			TableListeningOfQuestionFavorite tf = new TableListeningOfQuestionFavorite();
			FinalDb fdb = FinalDb.create(mContext, "cetp");
			List<TableListeningOfQuestion> lisOfQList = fdb.findAllByWhere(
					TableListeningOfQuestion.class, "YYYYMM="
							+ AppVariable.Common.YearMonth);
			dataCount = lisOfQList.size();
			Log.v(TAG, "dataCount=" + dataCount);

			if (lisOfQList != null) {
				String s;
				s = lisOfQList.get(NUM - 1).getYYYYMM();
				t.setYYYYMM(s);
				tf.setYYYYMM(s);
				s = lisOfQList.get(NUM - 1).getQuestionType();
				t.setQuestionType(s);
				tf.setQuestionType(s);
				s = lisOfQList.get(NUM - 1).getQuestionNumber();
				t.setQuestionNumber(s);
				tf.setQuestionNumber(s);
				s = lisOfQList.get(NUM - 1).getSelectionA();
				t.setSelectionA(s);
				tf.setSelectionA(s);
				s = lisOfQList.get(NUM - 1).getSelectionB();
				t.setSelectionB(s);
				tf.setSelectionB(s);
				s = lisOfQList.get(NUM - 1).getSelectionC();
				t.setSelectionC(s);
				tf.setSelectionC(s);
				s = lisOfQList.get(NUM - 1).getSelectionD();
				t.setSelectionD(s);
				tf.setSelectionD(s);
				s = lisOfQList.get(NUM - 1).getAnswer();
				t.setAnswer(s);
				tf.setAnswer(s);

			}
			Log.v(TAG, "��¼������" + (lisOfQList != null ? lisOfQList.size() : 0));

			// ----------------------------------------------------------------

			if (isChecked == true) {
				String s;
				s = lisOfQList.get(NUM - 1).getComments();
				if (lisOfQList.get(NUM - 1).getComments().equals("")) {
					t.setComments("f");// �ղ�favorite
				} else {
					t.setComments("wf");// ����wrong���ղ�favorite
				}
				tf.setComments(s);
				tf.setDate(new Date());
				if (!(lisOfQList.get(NUM - 1).getComments().trim().equals("f") || lisOfQList
						.get(NUM - 1).getComments().trim().equals("wf"))) {
					fdb.save(tf);
					fdb.update(t, "YYYYMM=" + t.getYYYYMM()
							+ " and QuestionNumber=" + t.getQuestionNumber());
				}
				radiogroup.setBackgroundResource(R.drawable.login_input_dark);
			} else {
				if (lisOfQList.get(NUM - 1).getComments().equals("f")) {
					t.setComments("");// �ղ�favorite
				} else {
					t.setComments("w");// ����wrong���ղ�favorite
				}
				fdb.update(t, "YYYYMM=" + t.getYYYYMM()
						+ " and QuestionNumber=" + t.getQuestionNumber());
				fdb.deleteByWhere(
						TableListeningOfQuestionFavorite.class,
						"YYYYMM = '" + t.getYYYYMM() + "' and QuestionNumber = '"
								+ t.getQuestionNumber()+"'");

				if (Answer_All[radiogroup.getId() / 10] != null) {
					radiogroup
							.setBackgroundResource(R.drawable.login_input_light);
				} else {
					radiogroup.setBackgroundResource(R.drawable.login_input);
					checkBoxText = (CheckBox) findViewById(buttonView.getId());
					checkBoxText.setChecked(false);
				}
			}
		}
	}
}