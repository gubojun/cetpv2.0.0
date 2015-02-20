package com.cetp.view;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cetp.R;
import com.cetp.action.AppConstant;
import com.cetp.action.AppVariable;
import com.cetp.database.DBListeningOfQuestion;
import com.cetp.question.QuestionContext;
import com.cetp.service.PlayerService;

public class ListeningViewQuestion extends Activity implements OnClickListener {
	public int FINGER_MOVE_ACTION = 0;

	public static final String TAG = "ListeningView";
	// 新建听力数据类
	DBListeningOfQuestion db = new DBListeningOfQuestion(this);
	// 音乐
	public static int ifMusicStart = 1;// 判断音乐是否是第一次播放
	public static final int MUSIC_FIRST_START = 1;
	public static final int MUSIC_PAUSE = 2;
	public static final int MUSIC_PLAYING = 3;
	// 按钮
	public static ImageView imgListeningMedia;// 播放按钮
	private ImageView imgListeningSetting;// 设置按钮
	public static TextView txtListeningTimeTotal;// 音频总时间
	public static TextView txtListeningTimeNow;// 音频当前时间

	public static String[] listeningAnswer_All = new String[200];
	private LinearLayout scrollContext;

	boolean preHideTag = false;
	/* 定义进度条 */
	public static SeekBar audioSeekBar = null;
	/* 定义在播放列表中的当前选择项 */
	public static int currentListItem = 0;

	/**
	 * onCreate
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "Activity State: onCreate()");
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.listeningview_question);

		findView();
		setListener();

		for (int i = 0; i < 200; i++) {
			listeningAnswer_All[i] = null;
		}
		db.open();
		Log.v(TAG, "Activity State: checkTableExists()");
		ListeningViewQuestion.audioSeekBar.setMax(0);
		/* 退出后再次进去程序时，进度条保持持续更新 */
		if (PlayerService.mMediaPlayer != null) {
			// 设置进度条最大值
			ListeningViewQuestion.audioSeekBar
					.setMax(PlayerService.mMediaPlayer.getDuration());
			audioSeekBar.setProgress(PlayerService.mMediaPlayer
					.getCurrentPosition());

			// 设置总时间
			int totalTime = PlayerService.mMediaPlayer.getDuration() / 1000;// 计算总时间
			int secondTime = totalTime % 60;// 计算秒数
			int minuteTime = totalTime / 60;// 计算分钟数

			ListeningViewQuestion.txtListeningTimeTotal.setText(String.format(
					"%1$02d", minuteTime)
					+ ":"
					+ String.format("%1$02d", secondTime));
		}

		scrollContext = (LinearLayout) findViewById(R.id.lin_listeningquestion_scrollcontext);

		// sqlite数据库游标
		Cursor cur;// = db.getAllItem();
		cur = db.getItemFromYM(AppVariable.Common.YearMonth);
		Log.v("ListeningViewQuestion", AppVariable.Common.YearMonth);

		if (cur.getCount() == 0)
			Toast.makeText(this, "请先下载并导入数据！", Toast.LENGTH_SHORT).show();

		int NUMBER = 0;// NUMBER表示id号，表示题号（要加1），表示题目数
		while (cur.moveToNext()) {// 循环产生RadioGroup控件
			NUMBER++;
			QuestionContext mylayout = new QuestionContext(this, NUMBER, cur);
			scrollContext.addView(mylayout);
		}
		cur.close();
	}

	/**
	 * 重写的onKeyDown方法，接收并处理键盘按下事件
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent e) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (keyCode == KeyEvent.KEYCODE_BACK && e.getRepeatCount() == 0) {
				db.close();
			}
			// this.finish();
			onDestroy();
			break;
		}
		return false;// super.onKeyDown(keyCode, e);
	}

	/**
	 * 重写的onDestroy方法
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		 Intent intent = new Intent();
		 intent.setClass(this, PlayerService.class);
		 stopService(intent);// 停止Service
		 ifMusicStart = MUSIC_FIRST_START;
		try {
			this.finish();// 关闭当前Activity
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	@Override
	public void onClick(View v) {
		if (v == imgListeningSetting) {
			//startActivity(new Intent(this, SettingView.class));
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	/**
	 * 得到按钮
	 */
	private void findView() {
		// 音乐播放进度条
		audioSeekBar = (SeekBar) findViewById(R.id.seekBarAudio);

		// txtQuestionNumber = (TextView) this
		// .findViewById(R.id.textView_questionnumber);

		imgListeningMedia = (ImageView) findViewById(R.id.optmedia);
		imgListeningSetting = (ImageView) findViewById(R.id.optsetting);
		txtListeningTimeTotal = (TextView) findViewById(R.id.txt_listening_time_total);
		txtListeningTimeNow = (TextView) findViewById(R.id.txt_listening_time_now);
	}

	/**
	 * 设置事件侦听
	 */
	private final void setListener() {
		imgListeningMedia.setOnClickListener(ImageViewMusicListener);
		/* 播放进度监听 */
		audioSeekBar.setOnSeekBarChangeListener(new SeekBarChangeEvent());
		imgListeningSetting.setOnClickListener(this);
	}

	/**
	 * 关于：音乐播放 音乐播放的动作
	 */
	private ImageView.OnClickListener ImageViewMusicListener = new ImageView.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v == imgListeningMedia) {
				System.out.println("onclick v==imgListeningMedia");
				System.out.println(ifMusicStart);
				if (ifMusicStart == MUSIC_FIRST_START) {
					ifMusicStart = MUSIC_PLAYING;
					playMusic(AppConstant.PlayerMag.PLAY_MAG);
					// 按键变为暂停键
					imgListeningMedia
							.setImageResource(R.drawable.btnmediapause);
				} else if (ifMusicStart == MUSIC_PAUSE) {
					ifMusicStart = MUSIC_PLAYING;
					playMusic(AppConstant.PlayerMag.PAUSE);
					// 按键变为暂停键
					imgListeningMedia
							.setImageResource(R.drawable.btnmediapause);
				} else if (ifMusicStart == MUSIC_PLAYING) {
					ifMusicStart = MUSIC_PAUSE;
					playMusic(AppConstant.PlayerMag.PAUSE);
					// 按键变为播放键
					imgListeningMedia.setImageResource(R.drawable.btnmediaplay);
				}
			}
		}
	};

	public void playMusic(int action) {
		Intent intent = new Intent();
		intent.putExtra("MSG", action);
		intent.setClass(ListeningViewQuestion.this, PlayerService.class);
		/* 启动service service要在AndroidManifest.xml注册如：<service></service> */
		startService(intent);
	}

	/* 拖放进度监听 ，别忘了Service里面还有个进度条刷新 */
	class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			/* 假设改变源于用户拖动 */
			if (fromUser) {
				if (ifMusicStart != MUSIC_FIRST_START) {
					PlayerService.mMediaPlayer.seekTo(progress);
				}
			}
			int totalTime = progress / 1000;// 计算总时间
			int secondTime = totalTime % 60;// 计算秒数
			int minuteTime = totalTime / 60;// 计算分钟数
			// 设置当前时间
			ListeningViewQuestion.txtListeningTimeNow.setText(String.format(
					"%1$02d", minuteTime)
					+ ":"
					+ String.format("%1$02d", secondTime));
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			if (ifMusicStart != MUSIC_FIRST_START) {
				if (PlayerService.mMediaPlayer.isPlaying()) {
					ifMusicStart = MUSIC_PAUSE;
					PlayerService.mMediaPlayer.pause();// 开始拖动进度条时，音乐暂停播放
					// 按键变为播放键
					imgListeningMedia.setImageResource(R.drawable.btnmediaplay);
				}
			} else
				Toast.makeText(ListeningViewQuestion.this, "请点击播放按钮！",
						Toast.LENGTH_SHORT).show();

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			if (ifMusicStart != MUSIC_FIRST_START) {
				if (!PlayerService.mMediaPlayer.isPlaying()) {
					ifMusicStart = MUSIC_PLAYING;
					PlayerService.mMediaPlayer.start();// 停止拖动进度条时，音乐开始播放
					// 按键变为暂停键
					imgListeningMedia
							.setImageResource(R.drawable.btnmediapause);
				}
			}
		}
	}

}
