package com.cetp.view;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cetp.R;
import com.cetp.action.AppConstant;
import com.cetp.action.AppVariable;
import com.cetp.database.DBListeningOfQuestion;
import com.cetp.excel.MyFile;
import com.cetp.question.QuestionContext;
import com.cetp.service.PlayerService;

public class ListeningViewQuestion {
	public int FINGER_MOVE_ACTION = 0;

	public static final String TAG = "ListeningView";
	// 新建听力数据类
	// DBListeningOfQuestion db = new DBListeningOfQuestion(this);
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

	public static String[] listeningAnswer_All = new String[AppVariable.Common.TOTAL_QUESTION_NUMBER];
	private LinearLayout scrollContext;

	boolean preHideTag = false;
	/* 定义进度条 */
	public static SeekBar audioSeekBar = null;
	/* 定义在播放列表中的当前选择项 */
	public static int currentListItem = 0;
	Context context;

	// View view;
	public ListeningViewQuestion(Context c) {
		context = c;
		// view =v;
	}

	public void setView(View v) {
		findView(v);
		setListener(context);
		// 新建听力数据类
		DBListeningOfQuestion db = new DBListeningOfQuestion(context);
		for (int i = 0; i < AppVariable.Common.TOTAL_QUESTION_NUMBER; i++) {
			listeningAnswer_All[i] = null;
		}
		db.open();
		scrollContext = (LinearLayout) v
				.findViewById(R.id.lin_listeningquestion_scrollcontext);

		// sqlite数据库游标
		Cursor cur;// = db.getAllItem();
		cur = db.getItemFromYM(AppVariable.Common.YearMonth);
		Log.v("ListeningViewQuestion1", AppVariable.Common.YearMonth);

		if (cur.getCount() == 0)
			Toast.makeText(context, "请先下载并导入数据！", Toast.LENGTH_SHORT).show();

		int NUMBER = 0;// NUMBER表示id号，表示题号（要加1），表示题目数
		while (cur.moveToNext()) {// 循环产生RadioGroup控件
			NUMBER++;
			QuestionContext mylayout = new QuestionContext(context, NUMBER, cur);
			scrollContext.addView(mylayout);
		}
		cur.close();
		/* 得到mp3文件名 */
		AppVariable.Common.MP3FILE = "/Lis_cet"
				+ AppVariable.Common.CetX + "_"
				+ AppVariable.Common.YearMonth + "_mp3.mp3";
		if (!MyFile.isFileExist(AppVariable.Common.MP3FILE)) {
			dialogDownloadTip();
		} 
	}

	/**
	 * 得到按钮
	 */
	private void findView(View v) {
		// 音乐播放进度条
		audioSeekBar = (SeekBar) v.findViewById(R.id.seekBarAudio);

		// txtQuestionNumber = (TextView) this
		// .findViewById(R.id.textView_questionnumber);

		imgListeningMedia = (ImageView) v.findViewById(R.id.optmedia);
		imgListeningSetting = (ImageView) v.findViewById(R.id.optsetting);
		txtListeningTimeTotal = (TextView) v
				.findViewById(R.id.txt_listening_time_total);
		txtListeningTimeNow = (TextView) v
				.findViewById(R.id.txt_listening_time_now);
	}

	/**
	 * 设置事件侦听
	 */
	private final void setListener(Context c) {
		imgListeningMedia.setOnClickListener(ImageViewMusicListener);
		/* 播放进度监听 */
		audioSeekBar.setOnSeekBarChangeListener(new SeekBarChangeEvent());
		// imgListeningSetting.setOnClickListener(this);
	}

	// @Override
	// private ImageView.OnClickListener
	// public void onClick(View v) {
	// if (v == imgListeningSetting) {
	// startActivity(new Intent(this, SettingView.class));
	// }
	// }
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
							.setImageResource(android.R.drawable.ic_media_pause);

				} else if (ifMusicStart == MUSIC_PAUSE) {
					ifMusicStart = MUSIC_PLAYING;
					playMusic(AppConstant.PlayerMag.PAUSE);
					// 按键变为暂停键
					imgListeningMedia
							.setImageResource(android.R.drawable.ic_media_pause);
				} else if (ifMusicStart == MUSIC_PLAYING) {
					ifMusicStart = MUSIC_PAUSE;
					playMusic(AppConstant.PlayerMag.PAUSE);
					// 按键变为播放键
					imgListeningMedia
							.setImageResource(android.R.drawable.ic_media_play);
				}
			}
		}
	};

	protected void dialogDownloadTip() {
		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage("您需要下载吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				Intent intent = new Intent();
				intent.setClass(context, DownLoadView.class);
				context.startActivity(intent);
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	public void playMusic(int action) {
		Intent intent = new Intent();
		intent.putExtra("MSG", action);
		intent.setClass(context, PlayerService.class);
		/* 启动service service要在AndroidManifest.xml注册如：<service></service> */
		context.startService(intent);
		// context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
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
					imgListeningMedia
							.setImageResource(android.R.drawable.ic_media_play);
				}
			} else
				Toast.makeText(context, "请点击播放按钮！", Toast.LENGTH_SHORT).show();

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			if (ifMusicStart != MUSIC_FIRST_START) {
				if (!PlayerService.mMediaPlayer.isPlaying()) {
					ifMusicStart = MUSIC_PLAYING;
					PlayerService.mMediaPlayer.start();// 停止拖动进度条时，音乐开始播放
					// 按键变为暂停键
					imgListeningMedia
							.setImageResource(android.R.drawable.ic_media_pause);
				}
			}
		}
	}
}
