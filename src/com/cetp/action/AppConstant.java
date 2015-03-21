package com.cetp.action;

import com.cetp.R;

import android.annotation.SuppressLint;

/**
 * @�Ѳ���������װ���ڹ�����Ķ�
 * 
 */
public interface AppConstant {
	public class PlayerMag {
		public static final int PLAY_MAG = 1;// ��ʼ����
		public static final int PAUSE = 2;// ��ͣ����
		public static final String LISTENING_MUSIC_NAME = "/"
				+ "cet4_201106.mp3";
		@SuppressLint("SdCardPath")
		public static final String MUSIC_PATH = "/mnt/sdcard/cetpdata/";
	}

	public class WelcomeViewMag {
		/** ��ӭ�������ʱ�� */
		public static final long SPLASH_DISPLAY_LENGHT = 3000;// ��ӭ�������ʱ��
	}

	public class Gesture {
		public static final int FLING_MIN_DISTANCE = 10;// ��С��������
		public static final int FLING_MIN_VELOCITY = 10;// ��С�����ٶ�
	}

	public class File {
		/** ������Դ·�� */
		public static final String WEB_FILE_PATH = "http://www.guanghezhang.com/softwareDownload/";
		public static final String LIS_FILE = "Lis_CET4_201106.xls";
		public static final String REA_FILE = "Rea_CET4_201106.xls";
		public static final String CLO_FILE = "Clo_CET4_201106.xls";
		public static final String VOC_FILE = "Voc_CET4_200606.xls";
		public static final String MP3_FILE = "Lis_cet4_201106_mp3.mp3";
	}

	public class Anim {
		/** �����л����� */
		public static final int[][] ANIM_CHANGE_TYPE = {
				{ R.anim.fade, R.anim.hold },
				{ R.anim.zoomin, R.anim.zoomout },
				{ R.anim.push_left_in, R.anim.push_left_out },
				{ R.anim.push_right_in, R.anim.push_right_out },
				{ R.anim.push_up_in, R.anim.push_up_out } };
	}
}
