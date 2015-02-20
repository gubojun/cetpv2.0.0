package com.cetp.service;

import java.io.IOException;

import com.cetp.R;
import com.cetp.action.AppConstant;
import com.cetp.action.AppVariable;
import com.cetp.view.ListeningViewQuestion1;
import com.cetp.view.ListeningViewQuestion;
import com.cetp.view.ListeningViewQuestiontext;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class PlayerService extends Service implements Runnable,
		MediaPlayer.OnCompletionListener {
	/* 定于一个多媒体对象 */
	public static MediaPlayer mMediaPlayer = null;
	// 是否单曲循环
	private static boolean isLoop = false;
	// 用户操作
	private int MSG;
	private String TAG = "PlayerService";

	/* 定义要播放的文件夹路径 */
	private static final String MUSIC_PATH = AppConstant.PlayerMag.MUSIC_PATH;

	@Override
	public IBinder onBind(Intent intent) {
		return null;// 这里的绑定没的用，上篇我贴出了如何将activity与service绑定的代码
	}

	@Override
	public void onCreate() {
		super.onCreate();
		if (mMediaPlayer != null) {
			mMediaPlayer.reset();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
		mMediaPlayer = new MediaPlayer();
		/* 监听播放是否完成 */
		mMediaPlayer.setOnCompletionListener(this);
		System.out.println("service onCreate");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		if (mMediaPlayer != null) {
			mMediaPlayer.stop();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
		System.out.println("service onDestroy");
	}

	/* 启动service时执行的方法 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		/* 得到从startService传来的动作，后是默认参数，这里是我自定义的常量 */
		MSG = intent.getIntExtra("MSG", AppConstant.PlayerMag.PLAY_MAG);
		if (MSG == AppConstant.PlayerMag.PLAY_MAG) {
			playMusic();
		}
		if (MSG == AppConstant.PlayerMag.PAUSE) {
			if (mMediaPlayer.isPlaying()) {// 正在播放
				mMediaPlayer.pause();// 暂停
			} else {// 没有播放
				mMediaPlayer.start();
			}
		}

		return super.onStartCommand(intent, flags, startId);
	}

	public void playMusic() {
		try {
			/* 重置多媒体 */
			mMediaPlayer.reset();
			/* 读取mp3文件 */
			AppVariable.Common.MP3FILE = "/Lis_cet" + AppVariable.Common.CetX + "_"
					+ AppVariable.Common.YearMonth + "_mp3.mp3";
			mMediaPlayer.setDataSource(MUSIC_PATH + AppVariable.Common.MP3FILE);
			// + AppConstant.PlayerMag.LISTENING_MUSIC_NAME);
			
			Log.v(TAG,AppVariable.Common.MP3FILE);
			/* 准备播放 */
			mMediaPlayer.prepare();
			/* 开始播放 */
			mMediaPlayer.start();
			/* 是否单曲循环 */
			mMediaPlayer.setLooping(isLoop);
			// 设置进度条最大值
			ListeningViewQuestion1.audioSeekBar
					.setMax(PlayerService.mMediaPlayer.getDuration());
			// 设置总时间
			int totalTime = mMediaPlayer.getDuration() / 1000;// 计算总时间
			int secondTime = totalTime % 60;// 计算秒数
			int minuteTime = totalTime / 60;// 计算分钟数

			ListeningViewQuestion1.txtListeningTimeTotal.setText(String.format(
					"%1$02d", minuteTime)
					+ ":"
					+ String.format("%1$02d", secondTime));
			Log.w(TAG, Integer.toString(totalTime));
			new Thread(this).start();
		} catch (IOException e) {
		}
	}

	// 刷新进度条
	@Override
	public void run() {
		int CurrentPosition = 0;// 设置默认进度条当前位置
		int total = mMediaPlayer.getDuration();//
		while (mMediaPlayer != null && CurrentPosition < total) {
			try {
				Thread.sleep(1000);
				if (mMediaPlayer != null) {
					CurrentPosition = mMediaPlayer.getCurrentPosition();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// 设置进度条位置
			ListeningViewQuestion1.audioSeekBar.setProgress(CurrentPosition);
		}

	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		/* 播放完当前歌曲，自动播放下一首 */

		// if (++TestMediaPlayer.currentListItme >= TestMediaPlayer.mMusicList
		// .size()) {
		// Toast.makeText(PlayerService.this, "已到最后一首歌曲", Toast.LENGTH_SHORT)
		// .show();
		// TestMediaPlayer.currentListItme--;
		// TestMediaPlayer.audioSeekBar.setMax(0);
		// } else {
		// playMusic();
		// }
		Toast.makeText(PlayerService.this, "听力测试结束！", Toast.LENGTH_SHORT)
				.show();
		ListeningViewQuestion1.audioSeekBar.setMax(0);
		ListeningViewQuestion1.ifMusicStart = 1;
		ListeningViewQuestion1.imgListeningMedia
		.setImageResource(R.drawable.btnmediaplay);
//		ListeningViewQuestion.audioSeekBar.setMax(0);
//		ListeningViewQuestion.ifMusicStart = 1;
//		ListeningViewQuestion.imgListeningMedia
//				.setImageResource(R.drawable.btnmediaplay);
	}
}