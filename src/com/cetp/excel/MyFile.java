package com.cetp.excel;

import java.io.File;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;

public class MyFile {
	Button btCreate;
	File path;
	File file;
	String sdPath;

	// File destDir = new File("/data/data/com.cetp/cet");
	public MyFile() {
		sdPath = getsdPath(); // 获得SD卡路径
		path = new File(sdPath); // 创建文件夹对象
		// file = new File(path + "/hzy.txt");
	}

	/**
	 * 得到SD卡的路径
	 */
	public static String getsdPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/cetpdata";
	}

	/**
	 * 判断SD卡是否存在
	 */
	public static boolean hasSdcard() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 创建目录
	 */
	// public static void createPath(String path) {
	// File file = new File(path);
	// if (!file.exists()) {
	// file.mkdir();
	// }
	// }

	public boolean createPath() {
		if (hasSdcard()) {
			if (!path.exists()) {
				Log.w("createPath","mkdirs");
				path.mkdirs();
			}
			return true;
		} else
			return false;

		// if (!file.exists()) {
		// try {
		// if (file.createNewFile()) { // 创建文件
		// Toast.makeText(null, "创建文件" + file.toString(), 0).show();
		// }
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// } else {
		// Toast.makeText(null, "文件" + file.toString() + "已存在", 0).show();
		// }
	}

	/**
	 * 判断SD卡上的文件夹是否存在
	 */
	public static boolean isFileExist(String fileName) {
		File file = new File(getsdPath() + fileName);
		return file.exists();
	}
}
