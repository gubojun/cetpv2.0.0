package com.cetp.action;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;

/**
 * 文字管理器
 * 
 * @author 顾博君
 * 
 */
public class TextSettingManager {
	public final static String TEXT_PREF = "textSetting";

	/*
	 * SharedPreferences也是一种轻型的数据存储方式，它的本质是基于XML文件存储key-value键值对数据，
	 * 通常用来存储一些简单的配置信息。 其存储位置在/data/data/<包名>/shared_prefs目录下。
	 * SharedPreferences对象本身只能获取数据而不支持存储和修改 ，存储修改是通过Editor对象实现。
	 */
	public SharedPreferences textSettingPreference;

	private int[] textColorResources = { Color.BLACK, Color.RED, Color.GREEN,
			Color.BLUE, Color.GRAY };
	private int[] textSizeResources = { 5, 10, 15, 20, 25 };
	private Activity mActivity;

	public TextSettingManager(Activity activity) {
		this.mActivity = activity;
		textSettingPreference = mActivity.getSharedPreferences(TEXT_PREF, 3);
	}

	/**
	 * 获取当前程序的文字颜色序号
	 * 
	 * @return int
	 */
	public int getTextColor(int keyID) {
		String[] key = { "text_color_question", "text_color_passage",
				"text_color_answer" };
		return textSettingPreference.getInt(key[keyID], 0);
	}

	public int getTextSize(int keyID) {
		String[] key = { "text_size_question", "text_size_passage",
				"text_size_answer" };
		return textSettingPreference.getInt(key[keyID], 0);
	}

	/**
	 * 把文字颜色序号写到全局设置里去
	 * 
	 * @param j
	 *            文字颜色序号
	 * @param keyID
	 */
	public void setTextColor(int j, int keyID) {
		SharedPreferences.Editor editor = textSettingPreference.edit();
		String[] key = { "text_color_question", "text_color_passage",
				"text_color_answer" };
		editor.putInt(key[keyID], j);
		editor.commit();
	}

	public void setTextSize(int j, int keyID) {
		SharedPreferences.Editor editor = textSettingPreference.edit();
		String[] key = { "text_size_question", "text_size_passage",
				"text_size_answer" };
		editor.putInt(key[keyID], j);
		editor.commit();
	}

	/**
	 * 获取当前文字颜色资源id
	 * 
	 * @param textType
	 *            0 Question 1 Passage 2 Answer
	 * @return
	 */
	public int getCurrentTextColorRes(int textType) {
		int colorLen = textColorResources.length;
		int getTextLen = getTextColor(textType);
		if (getTextLen >= colorLen) {
			getTextLen = 0;
		}
		return textColorResources[getTextLen];
	}

	public int getCurrentTextSizeRes(int textType) {
		int sizeLen = textSizeResources.length;
		int getTextLen = getTextSize(textType);
		if (getTextLen >= sizeLen) {
			getTextLen = 0;
		}
		return textSizeResources[getTextLen];
	}

	/**
	 * 用于初始化文字
	 */
	public void initText() {
		AppVariable.Font.G_TEXTCOLOR_QUESTION = getCurrentTextColorRes(0);
		AppVariable.Font.G_TEXTCOLOR_PASSAGE = getCurrentTextColorRes(1);
		AppVariable.Font.G_TEXTCOLOR_ANSWER = getCurrentTextColorRes(2);
		AppVariable.Font.G_TEXTSIZE_QUESTION = getCurrentTextSizeRes(0);
		AppVariable.Font.G_TEXTSIZE_PASSAGE = getCurrentTextSizeRes(1);
		AppVariable.Font.G_TEXTSIZE_ANSWER = getCurrentTextSizeRes(2);
	}

	/**
	 * 随即切换一个文字颜色
	 */
	public void changeTextColor(int id, int textType) {
		setTextColor(id, textType);
		switch (textType) {
		case 0:
			AppVariable.Font.G_TEXTCOLOR_QUESTION = getCurrentTextColorRes(textType);
			break;
		case 1:
			AppVariable.Font.G_TEXTCOLOR_PASSAGE = getCurrentTextColorRes(textType);
			break;
		case 2:
			AppVariable.Font.G_TEXTCOLOR_ANSWER = getCurrentTextColorRes(textType);
			break;
		}
	}

	public void changeTextSize(int id, int textType) {
		setTextSize(id, textType);
		switch (textType) {
		case 0:
			AppVariable.Font.G_TEXTSIZE_QUESTION = getCurrentTextSizeRes(textType);
			break;
		case 1:
			AppVariable.Font.G_TEXTSIZE_PASSAGE = getCurrentTextSizeRes(textType);
			break;
		case 2:
			AppVariable.Font.G_TEXTSIZE_ANSWER = getCurrentTextSizeRes(textType);
			break;
		}
	}
}
