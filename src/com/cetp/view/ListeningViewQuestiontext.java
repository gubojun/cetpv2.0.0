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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cetp.R;
import com.cetp.action.AppVariable;
import com.cetp.database.DBListeningOfText;
import com.cetp.service.PlayerService;
import com.cetp.view.ListeningViewQuestion.SeekBarChangeEvent;

/**
 * 
 * @author 胡灿华
 * @data 2013/5/9
 */
public class ListeningViewQuestiontext extends Activity implements Runnable {
	private String TAG = "ListeningViewQuestiontext";
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
	DBListeningOfText db = new DBListeningOfText(this);
	/* 定义进度条 */
	public static SeekBar audioSeekBar = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "Activity State: onCreate()");
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.listeningview_questiontext);

		// 音乐播放进度条
		audioSeekBar = (SeekBar) findViewById(R.id.seekBarAudio_listeningviewquestiontext);
		db.open();
		Log.v(TAG, "Activity State: checkTableExists()");
		if (db.checkTableExists("Listening_Comprehension_Passage")) {
			// ---取出所有数据---
			// cur = db.getAllItem();
			cur = db.getItemFromYM(AppVariable.Common.YearMonth);
			// cur.moveToFirst();
		} else {
			Toast.makeText(ListeningViewQuestiontext.this, "数据不存在",
					Toast.LENGTH_SHORT).show();
		}

		ScrollView listeningViewScroll = (ScrollView) findViewById(R.id.scr_listening_questiontext);
		LinearLayout layout = new LinearLayout(this);

		layout.setOrientation(LinearLayout.VERTICAL); // 控件对其方式为垂直排列
		layout.setPadding(10, 0, 10, 0);// 设置padding
		while (cur.moveToNext()) {
			txtQuestionNumber = new TextView(this);
			originalText = new TextView(this);// 原文
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
//		new Thread(this).start();
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
		txtQuestionNumber.setTextColor(getResources().getColor(R.color.red));

		questionText.setText(cur.getString(cur.getColumnIndex("PassageText")));
		questionText.setTextSize(17);

	}/* 拖放进度监听 ，别忘了Service里面还有个进度条刷新 */

	// 刷新进度条
	@Override
	public void run() {
		int CurrentPosition = 0;// 设置默认进度条当前位置
		while (PlayerService.mMediaPlayer != null
				&& CurrentPosition < PlayerService.mMediaPlayer.getDuration()) {
			try {
				Thread.sleep(1000);
				if (PlayerService.mMediaPlayer != null) {
					CurrentPosition = PlayerService.mMediaPlayer
							.getCurrentPosition();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			audioSeekBar.setMax(PlayerService.mMediaPlayer.getDuration());
			// 设置进度条位置
			ListeningViewQuestiontext.audioSeekBar.setProgress(CurrentPosition);
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}
