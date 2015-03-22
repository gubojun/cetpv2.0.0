package com.cetp.action;

import com.cetp.R;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * 皮肤管理器
 * 
 * @author 来自网络资源的帮助
 * 
 */
public class SkinSettingManager {

	public final static String SKIN_PREF = "skinSetting";
	/*
	 * SharedPreferences也是一种轻型的数据存储方式，它的本质是基于XML文件存储key-value键值对数据，
	 * 通常用来存储一些简单的配置信息。 其存储位置在/data/data/<包名>/shared_prefs目录下。
	 * SharedPreferences对象本身只能获取数据而不支持存储和修改 ，存储修改是通过Editor对象实现。
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
	 * 获取当前程序的皮肤序号
	 * 
	 * @return int
	 */
	public int getSkinType() {
		String key = "skin_type";
		return skinSettingPreference.getInt(key, 0);
	}

	/**
	 * 把皮肤序号写到全局设置里去
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
	 * 获取当前皮肤的背景图资源id
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
	 * 用于初始化皮肤
	 */
	public void initSkins() {
		mActivity.getWindow()
				.setBackgroundDrawableResource(getCurrentSkinRes());
	}

	/**
	 * 随即切换一个背景皮肤
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
