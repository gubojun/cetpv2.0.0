package com.cetp.view;

import com.cetp.R;
import com.cetp.action.AppVariable;
import com.cetp.action.SkinSettingManager;
import com.cetp.action.TextSettingManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FontSettingView extends Activity {
	/** 字体管理器 */
	private TextSettingManager mTextSettingManager;
	/** 字体颜色-相对布局 */
	private RelativeLayout rltFontSettingQuestionColor;
	private RelativeLayout rltFontSettingPassageColor;
	private RelativeLayout rltFontSettingAnswerColor;
	private RelativeLayout rltFontSettingQuestionSize;
	private RelativeLayout rltFontSettingPassageSize;
	private RelativeLayout rltFontSettingAnswerSize;
	/** 字体颜色-文本框 */
	private TextView txtFontSettingQuestionColor;
	private TextView txtFontSettingPassageColor;
	private TextView txtFontSettingAnswerColor;
	private TextView txtFontSettingQuestionSize;
	private TextView txtFontSettingPassageSize;
	private TextView txtFontSettingAnswerSize;
	/** 字体相关变量 */
	private int FontColor_Question;
	private int FontColor_Passage;
	private int FontColor_Answer;
	private int fontSize_Question;
	private int fontSize_Passage;
	private int fontSize_Answer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fontsettingview);
		findView();
		setListener();
		settingInit();
	}

	private void settingInit() {
		// 初始化字体
		mTextSettingManager = new TextSettingManager(this);
		mTextSettingManager.initText();
		FontColor_Question = mTextSettingManager.getTextColor(0);
		FontColor_Passage = mTextSettingManager.getTextColor(1);
		FontColor_Answer = mTextSettingManager.getTextColor(2);
		fontSize_Question = mTextSettingManager.getTextSize(0);
		fontSize_Passage = mTextSettingManager.getTextSize(1);
		fontSize_Answer = mTextSettingManager.getTextSize(2);
		// 设置题目字体颜色
		txtFontSettingQuestionColor
				.setTextColor(AppVariable.Font.G_TEXTCOLOR_QUESTION);
		txtFontSettingPassageColor
				.setTextColor(AppVariable.Font.G_TEXTCOLOR_PASSAGE);
		txtFontSettingAnswerColor
				.setTextColor(AppVariable.Font.G_TEXTCOLOR_ANSWER);

		txtFontSettingQuestionSize
				.setTextSize(AppVariable.Font.G_TEXTSIZE_QUESTION);
		txtFontSettingPassageSize
				.setTextSize(AppVariable.Font.G_TEXTSIZE_PASSAGE);
		txtFontSettingAnswerSize
				.setTextSize(AppVariable.Font.G_TEXTSIZE_ANSWER);
	}

	private void setListener() {
		rltFontSettingQuestionColor
				.setOnClickListener(new myRelativeLayoutOnClickListener());
		rltFontSettingPassageColor
				.setOnClickListener(new myRelativeLayoutOnClickListener());
		rltFontSettingAnswerColor
				.setOnClickListener(new myRelativeLayoutOnClickListener());
		rltFontSettingQuestionSize
				.setOnClickListener(new myRelativeLayoutOnClickListener());
		rltFontSettingPassageSize
				.setOnClickListener(new myRelativeLayoutOnClickListener());
		rltFontSettingAnswerSize
				.setOnClickListener(new myRelativeLayoutOnClickListener());
	}

	private void findView() {
		rltFontSettingQuestionColor = (RelativeLayout) findViewById(R.id.rlt_fontsetting_questioncolor);
		rltFontSettingPassageColor = (RelativeLayout) findViewById(R.id.rlt_fontsetting_passagecolor);
		rltFontSettingAnswerColor = (RelativeLayout) findViewById(R.id.rlt_fontsetting_answercolor);
		rltFontSettingQuestionSize = (RelativeLayout) findViewById(R.id.rlt_fontsetting_questionsize);
		rltFontSettingPassageSize = (RelativeLayout) findViewById(R.id.rlt_fontsetting_passagesize);
		rltFontSettingAnswerSize = (RelativeLayout) findViewById(R.id.rlt_fontsetting_answersize);

		txtFontSettingQuestionColor = (TextView) findViewById(R.id.txt_fontsetting_questioncolor);
		txtFontSettingPassageColor = (TextView) findViewById(R.id.txt_fontsetting_passagecolor);
		txtFontSettingAnswerColor = (TextView) findViewById(R.id.txt_fontsetting_answercolor);
		txtFontSettingQuestionSize = (TextView) findViewById(R.id.txt_fontsetting_questionsize);
		txtFontSettingPassageSize = (TextView) findViewById(R.id.txt_fontsetting_passagesize);
		txtFontSettingAnswerSize = (TextView) findViewById(R.id.txt_fontsetting_answersize);
	}

	public void newDialog(int fontColor, DialogInterface.OnClickListener listen) {
		new AlertDialog.Builder(this)
				.setTitle("请选择")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setSingleChoiceItems(
						new String[] { "黑色", "红色", "绿色", "蓝色", "灰色" },
						fontColor, listen)
				// .setPositiveButton("确定", new myPositiveButtonListener())
				.setNegativeButton("取消", null).show();
	}

	public void newSizeDialog(int fontSize,
			DialogInterface.OnClickListener listen) {
		new AlertDialog.Builder(this)
				.setTitle("请选择")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setSingleChoiceItems(
						new String[] { "5", "10", "15", "20", "25" },
						fontSize, listen)
				// .setPositiveButton("确定", new myPositiveButtonListener())
				.setNegativeButton("取消", null).show();
	}

	class myRelativeLayoutOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (v == rltFontSettingQuestionColor) {
				newDialog(FontColor_Question,
						new myDialogInterfaceListener_Question());
			} else if (v == rltFontSettingPassageColor) {
				newDialog(FontColor_Passage,
						new myDialogInterfaceListener_Passage());
			} else if (v == rltFontSettingAnswerColor) {
				newDialog(FontColor_Answer,
						new myDialogInterfaceListener_Answer());
			} else if (v == rltFontSettingQuestionSize) {
				newSizeDialog(fontSize_Question,
						new mySizeInterfaceListener_Question());
			} else if (v == rltFontSettingPassageSize) {
				newSizeDialog(fontSize_Passage,
						new mySizeInterfaceListener_Passage());
			} else if (v == rltFontSettingAnswerSize) {
				newSizeDialog(fontSize_Answer,
						new mySizeInterfaceListener_Answer());
			}
		}
	}

	class myDialogInterfaceListener_Question implements
			DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			FontColor_Question = which;
			mTextSettingManager.changeTextColor(FontColor_Question, 0);
			txtFontSettingQuestionColor
					.setTextColor(AppVariable.Font.G_TEXTCOLOR_QUESTION);
			dialog.dismiss();
		}
	}

	class myDialogInterfaceListener_Passage implements
			DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			FontColor_Passage = which;
			mTextSettingManager.changeTextColor(FontColor_Passage, 1);
			txtFontSettingPassageColor
					.setTextColor(AppVariable.Font.G_TEXTCOLOR_PASSAGE);
			dialog.dismiss();
		}
	}

	class myDialogInterfaceListener_Answer implements
			DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			FontColor_Answer = which;
			mTextSettingManager.changeTextColor(FontColor_Answer, 2);
			txtFontSettingAnswerColor
					.setTextColor(AppVariable.Font.G_TEXTCOLOR_ANSWER);
			dialog.dismiss();
		}
	}

	class mySizeInterfaceListener_Question implements
			DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			fontSize_Question = which;
			mTextSettingManager.changeTextSize(fontSize_Question, 0);
			txtFontSettingQuestionSize
					.setTextSize(AppVariable.Font.G_TEXTSIZE_QUESTION);
			dialog.dismiss();
		}
	}

	class mySizeInterfaceListener_Passage implements
			DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			fontSize_Passage = which;
			mTextSettingManager.changeTextSize(fontSize_Passage, 1);
			txtFontSettingPassageSize
					.setTextSize(AppVariable.Font.G_TEXTSIZE_PASSAGE);
			dialog.dismiss();
		}
	}

	class mySizeInterfaceListener_Answer implements
			DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			fontSize_Answer = which;
			mTextSettingManager.changeTextSize(fontSize_Answer, 2);
			txtFontSettingAnswerSize
					.setTextSize(AppVariable.Font.G_TEXTSIZE_ANSWER);
			dialog.dismiss();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// -------------【初始化皮肤】--------------------------
		SkinSettingManager mSettingManager = new SkinSettingManager(this);
		mSettingManager.initSkins();
	}
}
