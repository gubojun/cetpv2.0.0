package com.cetp.action;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;

/**
 * ���ֹ�����
 * 
 * @author �˲���
 * 
 */
public class TextSettingManager {
	public final static String TEXT_PREF = "textSetting";

	/*
	 * SharedPreferencesҲ��һ�����͵����ݴ洢��ʽ�����ı����ǻ���XML�ļ��洢key-value��ֵ�����ݣ�
	 * ͨ�������洢һЩ�򵥵�������Ϣ�� ��洢λ����/data/data/<����>/shared_prefsĿ¼�¡�
	 * SharedPreferences������ֻ�ܻ�ȡ���ݶ���֧�ִ洢���޸� ���洢�޸���ͨ��Editor����ʵ�֡�
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
	 * ��ȡ��ǰ�����������ɫ���
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
	 * ��������ɫ���д��ȫ��������ȥ
	 * 
	 * @param j
	 *            ������ɫ���
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
	 * ��ȡ��ǰ������ɫ��Դid
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
	 * ���ڳ�ʼ������
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
	 * �漴�л�һ��������ɫ
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
