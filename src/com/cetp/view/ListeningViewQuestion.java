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
	// �½�����������
	DBListeningOfQuestion db = new DBListeningOfQuestion(this);
	// ����
	public static int ifMusicStart = 1;// �ж������Ƿ��ǵ�һ�β���
	public static final int MUSIC_FIRST_START = 1;
	public static final int MUSIC_PAUSE = 2;
	public static final int MUSIC_PLAYING = 3;
	// ��ť
	public static ImageView imgListeningMedia;// ���Ű�ť
	private ImageView imgListeningSetting;// ���ð�ť
	public static TextView txtListeningTimeTotal;// ��Ƶ��ʱ��
	public static TextView txtListeningTimeNow;// ��Ƶ��ǰʱ��

	public static String[] listeningAnswer_All = new String[200];
	private LinearLayout scrollContext;

	boolean preHideTag = false;
	/* ��������� */
	public static SeekBar audioSeekBar = null;
	/* �����ڲ����б��еĵ�ǰѡ���� */
	public static int currentListItem = 0;

	/**
	 * onCreate
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "Activity State: onCreate()");
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		setContentView(R.layout.listeningview_question);

		findView();
		setListener();

		for (int i = 0; i < 200; i++) {
			listeningAnswer_All[i] = null;
		}
		db.open();
		Log.v(TAG, "Activity State: checkTableExists()");
		ListeningViewQuestion.audioSeekBar.setMax(0);
		/* �˳����ٴν�ȥ����ʱ�����������ֳ������� */
		if (PlayerService.mMediaPlayer != null) {
			// ���ý��������ֵ
			ListeningViewQuestion.audioSeekBar
					.setMax(PlayerService.mMediaPlayer.getDuration());
			audioSeekBar.setProgress(PlayerService.mMediaPlayer
					.getCurrentPosition());

			// ������ʱ��
			int totalTime = PlayerService.mMediaPlayer.getDuration() / 1000;// ������ʱ��
			int secondTime = totalTime % 60;// ��������
			int minuteTime = totalTime / 60;// ���������

			ListeningViewQuestion.txtListeningTimeTotal.setText(String.format(
					"%1$02d", minuteTime)
					+ ":"
					+ String.format("%1$02d", secondTime));
		}

		scrollContext = (LinearLayout) findViewById(R.id.lin_listeningquestion_scrollcontext);

		// sqlite���ݿ��α�
		Cursor cur;// = db.getAllItem();
		cur = db.getItemFromYM(AppVariable.Common.YearMonth);
		Log.v("ListeningViewQuestion", AppVariable.Common.YearMonth);

		if (cur.getCount() == 0)
			Toast.makeText(this, "�������ز��������ݣ�", Toast.LENGTH_SHORT).show();

		int NUMBER = 0;// NUMBER��ʾid�ţ���ʾ��ţ�Ҫ��1������ʾ��Ŀ��
		while (cur.moveToNext()) {// ѭ������RadioGroup�ؼ�
			NUMBER++;
			QuestionContext mylayout = new QuestionContext(this, NUMBER, cur);
			scrollContext.addView(mylayout);
		}
		cur.close();
	}

	/**
	 * ��д��onKeyDown���������ղ�������̰����¼�
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
	 * ��д��onDestroy����
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		 Intent intent = new Intent();
		 intent.setClass(this, PlayerService.class);
		 stopService(intent);// ֹͣService
		 ifMusicStart = MUSIC_FIRST_START;
		try {
			this.finish();// �رյ�ǰActivity
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
	 * �õ���ť
	 */
	private void findView() {
		// ���ֲ��Ž�����
		audioSeekBar = (SeekBar) findViewById(R.id.seekBarAudio);

		// txtQuestionNumber = (TextView) this
		// .findViewById(R.id.textView_questionnumber);

		imgListeningMedia = (ImageView) findViewById(R.id.optmedia);
		imgListeningSetting = (ImageView) findViewById(R.id.optsetting);
		txtListeningTimeTotal = (TextView) findViewById(R.id.txt_listening_time_total);
		txtListeningTimeNow = (TextView) findViewById(R.id.txt_listening_time_now);
	}

	/**
	 * �����¼�����
	 */
	private final void setListener() {
		imgListeningMedia.setOnClickListener(ImageViewMusicListener);
		/* ���Ž��ȼ��� */
		audioSeekBar.setOnSeekBarChangeListener(new SeekBarChangeEvent());
		imgListeningSetting.setOnClickListener(this);
	}

	/**
	 * ���ڣ����ֲ��� ���ֲ��ŵĶ���
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
					// ������Ϊ��ͣ��
					imgListeningMedia
							.setImageResource(R.drawable.btnmediapause);
				} else if (ifMusicStart == MUSIC_PAUSE) {
					ifMusicStart = MUSIC_PLAYING;
					playMusic(AppConstant.PlayerMag.PAUSE);
					// ������Ϊ��ͣ��
					imgListeningMedia
							.setImageResource(R.drawable.btnmediapause);
				} else if (ifMusicStart == MUSIC_PLAYING) {
					ifMusicStart = MUSIC_PAUSE;
					playMusic(AppConstant.PlayerMag.PAUSE);
					// ������Ϊ���ż�
					imgListeningMedia.setImageResource(R.drawable.btnmediaplay);
				}
			}
		}
	};

	public void playMusic(int action) {
		Intent intent = new Intent();
		intent.putExtra("MSG", action);
		intent.setClass(ListeningViewQuestion.this, PlayerService.class);
		/* ����service serviceҪ��AndroidManifest.xmlע���磺<service></service> */
		startService(intent);
	}

	/* �ϷŽ��ȼ��� ��������Service���滹�и�������ˢ�� */
	class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			/* ����ı�Դ���û��϶� */
			if (fromUser) {
				if (ifMusicStart != MUSIC_FIRST_START) {
					PlayerService.mMediaPlayer.seekTo(progress);
				}
			}
			int totalTime = progress / 1000;// ������ʱ��
			int secondTime = totalTime % 60;// ��������
			int minuteTime = totalTime / 60;// ���������
			// ���õ�ǰʱ��
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
					PlayerService.mMediaPlayer.pause();// ��ʼ�϶�������ʱ��������ͣ����
					// ������Ϊ���ż�
					imgListeningMedia.setImageResource(R.drawable.btnmediaplay);
				}
			} else
				Toast.makeText(ListeningViewQuestion.this, "�������Ű�ť��",
						Toast.LENGTH_SHORT).show();

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			if (ifMusicStart != MUSIC_FIRST_START) {
				if (!PlayerService.mMediaPlayer.isPlaying()) {
					ifMusicStart = MUSIC_PLAYING;
					PlayerService.mMediaPlayer.start();// ֹͣ�϶�������ʱ�����ֿ�ʼ����
					// ������Ϊ��ͣ��
					imgListeningMedia
							.setImageResource(R.drawable.btnmediapause);
				}
			}
		}
	}

}
