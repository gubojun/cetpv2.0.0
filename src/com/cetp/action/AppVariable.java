package com.cetp.action;

public class AppVariable {
	/**
	 * 一般变量
	 * 
	 * @author 顾博君
	 * @date 2013-4-29
	 */
	public static class Common {
		// 0听力，1阅读，2完型，3词汇
		public static int TypeOfView = 0;
		// 0短对话，1长对话，3文章，4复合式听写
		public static int TypeOfListening = 0;
		//最大题目数,初始化题目数组时使用
		public static int TOTAL_QUESTION_NUMBER=200;
		// 记录题目的年份月份
		public static String YearMonth = "201406";
		// 记录四级六级
		public static int CetX = 4;
		// 记录音频文件名
		public static String MP3FILE="";
		// 记录皮肤号
		// public static int skinNumber;
		// 记录手机屏幕宽度
		public static int SCREEN_WIDTH;
		// 记录手机屏幕高度
		public static int SCREEN_HEIGHT;
		// 记录切换界面的类型
		public static int CHANGETYPE = 0;
		// 记录可变的文件名
		public static String QUESTION_FILENAME = "";
		// 记录书架的页面
		public static int BOOKLIBRARY_PAGE = 0;
		// 记录程序是否开启过
		//用于开机检测新版本判断
		public static Boolean loaded = false;
		//是否是模拟测试
		public static boolean isSimulation=false;
		
		//是否srcollview滑到最底部变化窗体
		
		public boolean isBottomSrcollChange=false;
	}

	/**
	 * 字体设置相关变量
	 * 
	 * @author 顾博君
	 * @date 2013-4-29
	 */
	public static class Font {
		// 题目文字颜色
		public static int G_TEXTCOLOR_QUESTION;
		// 文章文字颜色
		public static int G_TEXTCOLOR_PASSAGE;
		// 标题文字颜色
		public static int G_TEXTCOLOR_ANSWER;
		// 题目文字大小
		public static int G_TEXTSIZE_QUESTION;
		// 文章文字大小
		public static int G_TEXTSIZE_PASSAGE;
		// 标题文字大小
		public static int G_TEXTSIZE_ANSWER;
	}

	/**
	 * 用户设置相关变量
	 * 
	 * @author 顾博君
	 * @date 2013-4-29
	 */
	public static class User {
		// 用户名
		public static String G_USER_NAME;
		// 用户权限
		public static String G_USER_PERMISSION;
		// 用户编号
		public static String G_USER_ID;
		// 是否登录成功
		public static boolean G_LOGINSUCCEEDED;
	}

	/**
	 * 时间设置相关变量
	 * 
	 * @author 顾博君
	 * @date 2013-4-29
	 */
	public static class Time {
		// 总时间
		public static String G_TIME_TOTAL;
		// 听力时间
		public static String G_TIME_LISTENING = "30";
		// 阅读时间
		public static String G_TIME_READING = "30";
		// 完型时间
		public static String G_TIME_CLOZING = "30";
		// 词汇时间
		public static String G_TIME_VOCABULARY = "30";

		// 剩余时间,以秒为单位
		public static int G_TIME_REMAIN;
		// 剩余小时数
		public static int G_TIME_REMAIN_HOUR;
		// 剩余分钟数
		public static int G_TIME_REMAIN_MINUTE;
		// 剩余不到一分钟的秒数
		public static int G_TIME_REMAIN_SECOND;
	}

}
