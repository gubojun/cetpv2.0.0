package com.cetp.action;

import com.cetp.R;

import android.annotation.SuppressLint;

/**
 * @把参数这样封装易于管理和阅读
 * 
 */
public interface AppConstant {
	public class PlayerMag {
		public static final int PLAY_MAG = 1;// 开始播放
		public static final int PAUSE = 2;// 暂停播放
		public static final String LISTENING_MUSIC_NAME = "/"
				+ "cet4_201106.mp3";
		@SuppressLint("SdCardPath")
		public static final String MUSIC_PATH = "/mnt/sdcard/cetpdata/";
	}

	public class WelcomeViewMag {
		/** 欢迎界面持续时间 */
		public static final long SPLASH_DISPLAY_LENGHT = 3000;// 欢迎界面持续时间
	}

	public class Gesture {
		public static final int FLING_MIN_DISTANCE = 10;// 最小滑动距离
		public static final int FLING_MIN_VELOCITY = 10;// 最小滑动速度
	}

	public class File {
		/** 网络资源路径 */
		public static final String WEB_FILE_PATH = "http://www.guanghezhang.com/softwareDownload/";
		public static final String LIS_FILE = "Lis_CET4_201106.xls";
		public static final String REA_FILE = "Rea_CET4_201106.xls";
		public static final String CLO_FILE = "Clo_CET4_201106.xls";
		public static final String VOC_FILE = "Voc_CET4_200606.xls";
		public static final String MP3_FILE = "Lis_cet4_201106_mp3.mp3";
	}

	public class Anim {
		/** 界面切换动画 */
		public static final int[][] ANIM_CHANGE_TYPE = {
				{ R.anim.fade, R.anim.hold },
				{ R.anim.zoomin, R.anim.zoomout },
				{ R.anim.push_left_in, R.anim.push_left_out },
				{ R.anim.push_right_in, R.anim.push_right_out },
				{ R.anim.push_up_in, R.anim.push_up_out } };
	}
}
