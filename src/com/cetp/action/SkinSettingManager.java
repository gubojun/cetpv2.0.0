package com.cetp.action;

import com.cetp.R;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Ƥ��������
 * 
 * @author ����������Դ�İ���
 * 
 */
public class SkinSettingManager {

	public final static String SKIN_PREF = "skinSetting";
	/*
	 * SharedPreferencesҲ��һ�����͵����ݴ洢��ʽ�����ı����ǻ���XML�ļ��洢key-value��ֵ�����ݣ�
	 * ͨ�������洢һЩ�򵥵�������Ϣ�� ��洢λ����/data/data/<����>/shared_prefsĿ¼�¡�
	 * SharedPreferences������ֻ�ܻ�ȡ���ݶ���֧�ִ洢���޸� ���洢�޸���ͨ��Editor����ʵ�֡�
	 */
	public SharedPreferences skinSettingPreference;

	private int[] skinResources = { R.drawable.wallpaper_default,
			R.drawable.wallpaper_2, R.drawable.wallpaper_3,
			R.drawable.wallpaper_4, R.drawable.wallpaper_5 };

	private Activity mActivity;

	public SkinSettingManager(Activity activity) {
		this.mActivity = activity;
		skinSettingPreference = mActivity.getSharedPreferences(SKIN_PREF, 3);
	}

	/**
	 * ��ȡ��ǰ�����Ƥ�����
	 * 
	 * @return int
	 */
	public int getSkinType() {
		String key = "skin_type";
		return skinSettingPreference.getInt(key, 0);
	}

	/**
	 * ��Ƥ�����д��ȫ��������ȥ
	 * 
	 * @param j
	 */
	public void setSkinType(int j) {
		SharedPreferences.Editor editor = skinSettingPreference.edit();
		String key = "skin_type";

		editor.putInt(key, j);
		editor.commit();
	}

	/**
	 * ��ȡ��ǰƤ���ı���ͼ��Դid
	 * 
	 * @return
	 */
	public int getCurrentSkinRes() {
		int skinLen = skinResources.length;
		int getSkinLen = getSkinType();
		if (getSkinLen >= skinLen) {
			getSkinLen = 0;
		}
		return skinResources[getSkinLen];
	}

	public void toggleSkins() {
		int skinType = getSkinType();
		if (skinType == skinResources.length - 1) {
			skinType = 0;
		} else {
			skinType++;
		}
		setSkinType(skinType);
		mActivity.getWindow().setBackgroundDrawable(null);
		try {
			mActivity.getWindow().setBackgroundDrawableResource(
					getCurrentSkinRes());
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���ڳ�ʼ��Ƥ��
	 */
	public void initSkins() {
		mActivity.getWindow()
				.setBackgroundDrawableResource(getCurrentSkinRes());
	}

	/**
	 * �漴�л�һ������Ƥ��
	 */
	public void changeSkin(int id) {

		setSkinType(id);
		mActivity.getWindow().setBackgroundDrawable(null);
		try {
			mActivity.getWindow().setBackgroundDrawableResource(
					getCurrentSkinRes());
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
