package com.cetp.view;

import android.content.Context;
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
import com.cetp.question.QuestionContext;
import com.cetp.service.PlayerService;

public class ListeningViewQuestion1 {
	public int FINGER_MOVE_ACTION = 0;

	public static final String TAG = "ListeningView";
	// �½�����������
	//DBListeningOfQuestion db = new DBListeningOfQuestion(this);
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
	Context context;
	//View view;
	public ListeningViewQuestion1(Context c){
		context=c;
		//view =v;
	}
	public void setView(View v){
		findView(v);
		setListener(context);
		// �½�����������
		DBListeningOfQuestion db = new DBListeningOfQuestion(context);
		for (int i = 0; i < 200; i++) {
			listeningAnswer_All[i] = null;
		}
		db.open();
		scrollContext = (LinearLayout) v
				.findViewById(R.id.lin_listeningquestion_scrollcontext);

		// sqlite���ݿ��α�
		Cursor cur;// = db.getAllItem();
		cur = db.getItemFromYM(AppVariable.Common.YearMonth);
		Log.v("ListeningViewQuestion1", AppVariable.Common.YearMonth);

		if (cur.getCount() == 0)
			Toast.makeText(context, "�������ز��������ݣ�", Toast.LENGTH_SHORT).show();

		int NUMBER = 0;// NUMBER��ʾid�ţ���ʾ��ţ�Ҫ��1������ʾ��Ŀ��
		while (cur.moveToNext()) {// ѭ������RadioGroup�ؼ�
			NUMBER++;
			QuestionContext mylayout = new QuestionContext(context, NUMBER, cur);
			scrollContext.addView(mylayout);
		}
		cur.close();
	}
	/**
	 * �õ���ť
	 */
	private void findView(View v) {
		// ���ֲ��Ž�����
		audioSeekBar = (SeekBar) v.findViewById(R.id.seekBarAudio);

		// txtQuestionNumber = (TextView) this
		// .findViewById(R.id.textView_questionnumber);

		imgListeningMedia = (ImageView) v.findViewById(R.id.optmedia);
		imgListeningSetting = (ImageView) v.findViewById(R.id.optsetting);
		txtListeningTimeTotal = (TextView) v.findViewById(R.id.txt_listening_time_total);
		txtListeningTimeNow = (TextView) v.findViewById(R.id.txt_listening_time_now);
	}

	/**
	 * �����¼�����
	 */
	private final void setListener(Context c) {
		imgListeningMedia.setOnClickListener(ImageViewMusicListener);
		/* ���Ž��ȼ��� */
		audioSeekBar.setOnSeekBarChangeListener(new SeekBarChangeEvent());
//		imgListeningSetting.setOnClickListener(this);
	}
	//@Override
//	private ImageView.OnClickListener 
//	public void onClick(View v) {
//		if (v == imgListeningSetting) {
//			startActivity(new Intent(this, SettingView.class));
//		}
//	}
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
		intent.setClass(context, PlayerService.class);
		/* ����service serviceҪ��AndroidManifest.xmlע���磺<service></service> */
		context.startService(intent);
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
			ListeningViewQuestion1.txtListeningTimeNow.setText(String.format(
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
				Toast.makeText(context, "�������Ű�ť��",
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
