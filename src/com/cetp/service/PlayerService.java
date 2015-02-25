package com.cetp.service;

import java.io.IOException;

import com.cetp.R;
import com.cetp.action.AppConstant;
import com.cetp.action.AppVariable;
import com.cetp.view.ListeningViewQuestion;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class PlayerService extends Service implements Runnable,
		MediaPlayer.OnCompletionListener {
	/* ����һ����ý����� */
	public static MediaPlayer mMediaPlayer = null;

	public static PlayerService instance = null;
	// �Ƿ���ѭ��
	private static boolean isLoop = false;
	// �û�����
	private int MSG;
	private String TAG = "PlayerService";

	/* ����Ҫ���ŵ��ļ���·�� */
	private static final String MUSIC_PATH = AppConstant.PlayerMag.MUSIC_PATH;

	@Override
	public IBinder onBind(Intent intent) {
		return null;// ����İ�û���ã���ƪ����������ν�activity��service�󶨵Ĵ���
	}

	@Override
	public void onCreate() {
		instance = this;
		super.onCreate();
		if (mMediaPlayer != null) {
			mMediaPlayer.reset();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
		mMediaPlayer = new MediaPlayer();
		/* ���������Ƿ���� */
		mMediaPlayer.setOnCompletionListener(this);
		mMediaPlayer.setOnErrorListener(listener);
		System.out.println("service onCreate");
	}
	OnErrorListener listener=new OnErrorListener() {
		
		@Override
		public boolean onError(MediaPlayer mp, int what, int arg2) {
			if(what==MediaPlayer.MEDIA_ERROR_UNKNOWN)
				mMediaPlayer.reset();
			else if(what ==-38)
				Toast.makeText(PlayerService.this, "-38", Toast.LENGTH_SHORT)
				.show();
			else
				Log.w("OnErrorListener","what=="+what+"  arg2=="+arg2);
			return false;
		}
	};
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

	/* ����serviceʱִ�еķ��� */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		/* �õ���startService�����Ķ���������Ĭ�ϲ��������������Զ���ĳ��� */
		MSG = intent.getIntExtra("MSG", AppConstant.PlayerMag.PLAY_MAG);
		if (MSG == AppConstant.PlayerMag.PLAY_MAG) {
			playMusic();
		}
		if (MSG == AppConstant.PlayerMag.PAUSE) {
			if (mMediaPlayer.isPlaying()) {// ���ڲ���
				mMediaPlayer.pause();// ��ͣ
			} else {// û�в���
				mMediaPlayer.start();
			}
		}

		return super.onStartCommand(intent, flags, startId);
	}

	public void playMusic() {
		try {
			/* ���ö�ý�� */
			mMediaPlayer.reset();
			/* ��ȡmp3�ļ� */
			AppVariable.Common.MP3FILE = "/Lis_cet" + AppVariable.Common.CetX
					+ "_" + AppVariable.Common.YearMonth + "_mp3.mp3";
			mMediaPlayer.setDataSource(MUSIC_PATH + AppVariable.Common.MP3FILE);
			// + AppConstant.PlayerMag.LISTENING_MUSIC_NAME);

			Log.v(TAG, AppVariable.Common.MP3FILE);
			/* ׼������ */
			mMediaPlayer.prepare();
			/* ��ʼ���� */
			mMediaPlayer.start();
			/* �Ƿ���ѭ�� */
			mMediaPlayer.setLooping(isLoop);
			// ���ý��������ֵ
			ListeningViewQuestion.audioSeekBar
					.setMax(PlayerService.mMediaPlayer.getDuration());
			// ������ʱ��
			int totalTime = mMediaPlayer.getDuration() / 1000;// ������ʱ��
			int secondTime = totalTime % 60;// ��������
			int minuteTime = totalTime / 60;// ���������

			ListeningViewQuestion.txtListeningTimeTotal.setText(String.format(
					"%1$02d", minuteTime)
					+ ":"
					+ String.format("%1$02d", secondTime));
			Log.w(TAG, Integer.toString(totalTime));
			new Thread(this).start();
		} catch (IOException e) {
		}
	}

	// ˢ�½�����
	@Override
	public void run() {
		int CurrentPosition = 0;// ����Ĭ�Ͻ�������ǰλ��
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
			// ���ý�����λ��
			ListeningViewQuestion.audioSeekBar.setProgress(CurrentPosition);
		}

	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		/* �����굱ǰ�������Զ�������һ�� */

		// if (++TestMediaPlayer.currentListItme >= TestMediaPlayer.mMusicList
		// .size()) {
		// Toast.makeText(PlayerService.this, "�ѵ����һ�׸���", Toast.LENGTH_SHORT)
		// .show();
		// TestMediaPlayer.currentListItme--;
		// TestMediaPlayer.audioSeekBar.setMax(0);
		// } else {
		// playMusic();
		// }
		
//			Toast.makeText(PlayerService.this, "�������Խ�����", Toast.LENGTH_SHORT)
//					.show();

		ListeningViewQuestion.audioSeekBar.setMax(0);
		ListeningViewQuestion.ifMusicStart = 1;
		ListeningViewQuestion.imgListeningMedia
				.setImageResource(android.R.drawable.ic_media_play);
		// ListeningViewQuestion.audioSeekBar.setMax(0);
		// ListeningViewQuestion.ifMusicStart = 1;
		// ListeningViewQuestion.imgListeningMedia
		// .setImageResource(R.drawable.btnmediaplay);
	}
}