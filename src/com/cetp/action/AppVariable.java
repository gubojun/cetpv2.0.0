package com.cetp.action;

public class AppVariable {
	/**
	 * һ�����
	 * 
	 * @author �˲���
	 * @date 2013-4-29
	 */
	public static class Common {
		// 0������1�Ķ���2���ͣ�3�ʻ�
		public static int TypeOfView = 0;
		// 0�̶Ի���1���Ի���3���£�4����ʽ��д
		public static int TypeOfListening = 0;
		//�����Ŀ��,��ʼ����Ŀ����ʱʹ��
		public static int TOTAL_QUESTION_NUMBER=200;
		// ��¼��Ŀ������·�
		public static String YearMonth = "201406";
		// ��¼�ļ�����
		public static int CetX = 4;
		// ��¼��Ƶ�ļ���
		public static String MP3FILE="";
		// ��¼Ƥ����
		// public static int skinNumber;
		// ��¼�ֻ���Ļ���
		public static int SCREEN_WIDTH;
		// ��¼�ֻ���Ļ�߶�
		public static int SCREEN_HEIGHT;
		// ��¼�л����������
		public static int CHANGETYPE = 0;
		// ��¼�ɱ���ļ���
		public static String QUESTION_FILENAME = "";
		// ��¼��ܵ�ҳ��
		public static int BOOKLIBRARY_PAGE = 0;
		// ��¼�����Ƿ�����
		//���ڿ�������°汾�ж�
		public static Boolean loaded = false;
		//�Ƿ���ģ�����
		public static boolean isSimulation=false;
		
		//�Ƿ�srcollview������ײ��仯����
		
		public boolean isBottomSrcollChange=false;
	}

	/**
	 * ����������ر���
	 * 
	 * @author �˲���
	 * @date 2013-4-29
	 */
	public static class Font {
		// ��Ŀ������ɫ
		public static int G_TEXTCOLOR_QUESTION;
		// ����������ɫ
		public static int G_TEXTCOLOR_PASSAGE;
		// ����������ɫ
		public static int G_TEXTCOLOR_ANSWER;
		// ��Ŀ���ִ�С
		public static int G_TEXTSIZE_QUESTION;
		// �������ִ�С
		public static int G_TEXTSIZE_PASSAGE;
		// �������ִ�С
		public static int G_TEXTSIZE_ANSWER;
	}

	/**
	 * �û�������ر���
	 * 
	 * @author �˲���
	 * @date 2013-4-29
	 */
	public static class User {
		// �û���
		public static String G_USER_NAME;
		// �û�Ȩ��
		public static String G_USER_PERMISSION;
		// �û����
		public static String G_USER_ID;
		// �Ƿ��¼�ɹ�
		public static boolean G_LOGINSUCCEEDED;
	}

	/**
	 * ʱ��������ر���
	 * 
	 * @author �˲���
	 * @date 2013-4-29
	 */
	public static class Time {
		// ��ʱ��
		public static String G_TIME_TOTAL;
		// ����ʱ��
		public static String G_TIME_LISTENING = "30";
		// �Ķ�ʱ��
		public static String G_TIME_READING = "30";
		// ����ʱ��
		public static String G_TIME_CLOZING = "30";
		// �ʻ�ʱ��
		public static String G_TIME_VOCABULARY = "30";

		// ʣ��ʱ��,����Ϊ��λ
		public static int G_TIME_REMAIN;
		// ʣ��Сʱ��
		public static int G_TIME_REMAIN_HOUR;
		// ʣ�������
		public static int G_TIME_REMAIN_MINUTE;
		// ʣ�಻��һ���ӵ�����
		public static int G_TIME_REMAIN_SECOND;
	}

}
