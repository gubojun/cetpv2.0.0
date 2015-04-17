package com.cetp.view;

import com.cetp.R;
import com.cetp.action.AppConstant;
import com.cetp.action.AppVariable;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

public class SettingView {
	// 定义按钮
	private RelativeLayout rltSettingPersonalized;
	private RelativeLayout rltSettingFont;
	private RelativeLayout rltSettingTime;
	private RelativeLayout rltSettingBackground;
	private RelativeLayout rltSettingDataManage;
	private RelativeLayout rltSettingPrivate;
	private RelativeLayout rltSettingMessage;

	private RelativeLayout rltSettingLogin;
	private RelativeLayout rltSettingAbout;

	// 定义音量调节按钮
	private ImageView imgSettingVoiceDown;
	private ImageView imgSettingVoiceUp;

	/* 声明音量管理器 */
	private AudioManager mAudioManager = null;
	/* 定义音量大小 */
	private SeekBar audioVolume = null;
	/* 定义进度条 */
	public static SeekBar audioSeekBar = null;
	/** 界面切换效果 */
	private int[][] ChangeType = AppConstant.Anim.ANIM_CHANGE_TYPE;
	Context context;
	Activity activity;

	public SettingView(Context c) {
		context = c;
		activity = (Activity) c;
	}

	public void setView(View v) {
		findView(v);
		initRelativeLayoutButton();

		/* 得到当前音量对象 */
		// mAudioManager = (AudioManager) context
		// .getSystemService(Context.AUDIO_SERVICE);
		//
		// /* 把当前音量值赋给进度条 */
		// audioVolume.setProgress(mAudioManager
		// .getStreamVolume(AudioManager.STREAM_MUSIC));
		// /* 监听音量 */
		// audioVolume.setOnSeekBarChangeListener(new AudioVolumeChangeEvent());
		// imgSettingVoiceDown.setOnClickListener(new
		// AudioVolumeOnClickEvent());
		// imgSettingVoiceUp.setOnClickListener(new AudioVolumeOnClickEvent());
	}

	private void findView(View v) {
		// 声音进度条
		// audioVolume = (SeekBar) v.findViewById(R.id.seekBarAudioVolume);
		rltSettingPersonalized = (RelativeLayout) v
				.findViewById(R.id.rlt_setting_personalized);
		rltSettingFont = (RelativeLayout) v.findViewById(R.id.rlt_setting_font);
		rltSettingTime = (RelativeLayout) v.findViewById(R.id.rlt_setting_time);
		rltSettingBackground = (RelativeLayout) v
				.findViewById(R.id.rlt_setting_background);

		rltSettingLogin = (RelativeLayout) v
				.findViewById(R.id.rlt_setting_login);

		rltSettingDataManage = (RelativeLayout) v
				.findViewById(R.id.rlt_setting_datamanage);
		rltSettingPrivate = (RelativeLayout) v
				.findViewById(R.id.rlt_setting_private);
		rltSettingMessage = (RelativeLayout) v
				.findViewById(R.id.rlt_setting_message);
		rltSettingAbout = (RelativeLayout) v
				.findViewById(R.id.rlt_setting_about);

		// imgSettingVoiceDown = (ImageView) v
		// .findViewById(R.id.img_setting_voice_down);
		// imgSettingVoiceUp = (ImageView) v
		// .findViewById(R.id.img_setting_voice_up);
	}

	/**
	 * 音量进度条监听
	 */
	class AudioVolumeChangeEvent implements SeekBar.OnSeekBarChangeListener {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// mAudioManager.adjustVolume(AudioManager.ADJUST_LOWER, 0);
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress,
					0);
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}
	}

	/**
	 * 音量按钮监听
	 */
	class AudioVolumeOnClickEvent implements ImageView.OnClickListener {
		@Override
		public void onClick(View v) {
			if (v == imgSettingVoiceDown) {
				int volume = mAudioManager
						.getStreamVolume(AudioManager.STREAM_MUSIC);
				if (volume > 0)
					audioVolume.setProgress(volume - 1);
			} else {
				audioVolume.setProgress(mAudioManager
						.getStreamVolume(AudioManager.STREAM_MUSIC) + 1);
			}
		}
	}

	class RelativeLayoutOnClickListener implements
			RelativeLayout.OnClickListener {

		@Override
		public void onClick(View v) {
			if (v == rltSettingPersonalized) {
				context.startActivity(new Intent(context
						.getApplicationContext(), PersonalizedView.class));
				// 第一个参数为启动时动画效果，第二个参数为退出时动画效果
				((Activity) context).overridePendingTransition(
						ChangeType[AppVariable.Common.CHANGETYPE][0],
						ChangeType[AppVariable.Common.CHANGETYPE][1]);
			} else if (v == rltSettingFont) {
				context.startActivity(new Intent(context, FontSettingView.class));
				// 第一个参数为启动时动画效果，第二个参数为退出时动画效果
				((Activity) context).overridePendingTransition(
						ChangeType[AppVariable.Common.CHANGETYPE][0],
						ChangeType[AppVariable.Common.CHANGETYPE][1]);
			} else if (v == rltSettingTime) {
				context.startActivity(new Intent(context, TimeSettingView.class));
				// 第一个参数为启动时动画效果，第二个参数为退出时动画效果
				((Activity) context).overridePendingTransition(
						ChangeType[AppVariable.Common.CHANGETYPE][0],
						ChangeType[AppVariable.Common.CHANGETYPE][1]);
			} else if (v == rltSettingLogin) {
				context.startActivity(new Intent(context, Welcome.class));
				// 第一个参数为启动时动画效果，第二个参数为退出时动画效果
				((Activity) context).overridePendingTransition(
						ChangeType[AppVariable.Common.CHANGETYPE][0],
						ChangeType[AppVariable.Common.CHANGETYPE][1]);
			} else if (v == rltSettingBackground) {
				context.startActivity(new Intent(context,
						BackgroundSettingView.class));
				// 第一个参数为启动时动画效果，第二个参数为退出时动画效果
				((Activity) context).overridePendingTransition(
						ChangeType[AppVariable.Common.CHANGETYPE][0],
						ChangeType[AppVariable.Common.CHANGETYPE][1]);
			} else if (v == rltSettingDataManage) {
				context.startActivity(new Intent(context, DataManageView.class));
				// 第一个参数为启动时动画效果，第二个参数为退出时动画效果
				((Activity) context).overridePendingTransition(
						ChangeType[AppVariable.Common.CHANGETYPE][0],
						ChangeType[AppVariable.Common.CHANGETYPE][1]);

				// new AlertDialog.Builder(SettingView.this).setTitle("通用")
				// .setIcon(android.R.drawable.ic_dialog_info)
				// .setMessage("无").setNegativeButton("取消", null).show();
			} else if (v == rltSettingPrivate) {
				new AlertDialog.Builder(context).setTitle("隐私")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setMessage("无").setNegativeButton("取消", null).show();
			} else if (v == rltSettingMessage) {
				showMessage();
			} else if (v == rltSettingAbout) {
				context.startActivity(new Intent(context, AboutView.class));
				// 第一个参数为启动时动画效果，第二个参数为退出时动画效果
				((Activity) context).overridePendingTransition(
						ChangeType[AppVariable.Common.CHANGETYPE][0],
						ChangeType[AppVariable.Common.CHANGETYPE][1]);
			}
		}
	}

	public void showMessage() {
		new AlertDialog.Builder(context).setTitle("系统通知")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setMessage("没有系统通知。").setNegativeButton("取消", null).show();
	}

	public void initRelativeLayoutButton() {
		rltSettingPersonalized
				.setOnClickListener(new RelativeLayoutOnClickListener());
		rltSettingFont.setOnClickListener(new RelativeLayoutOnClickListener());
		rltSettingTime.setOnClickListener(new RelativeLayoutOnClickListener());
		// ------------------------------------------------
		rltSettingBackground
				.setOnClickListener(new RelativeLayoutOnClickListener());

		rltSettingLogin.setOnClickListener(new RelativeLayoutOnClickListener());
		// ------------------------------------------------
		rltSettingDataManage
				.setOnClickListener(new RelativeLayoutOnClickListener());
		rltSettingPrivate
				.setOnClickListener(new RelativeLayoutOnClickListener());
		rltSettingMessage
				.setOnClickListener(new RelativeLayoutOnClickListener());
		// ------------------------------------------------
		rltSettingAbout.setOnClickListener(new RelativeLayoutOnClickListener());
	}
}
