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
		sdPath = getsdPath(); // ���SD��·��
		path = new File(sdPath); // �����ļ��ж���
		// file = new File(path + "/hzy.txt");
	}

	/**
	 * �õ�SD����·��
	 */
	public static String getsdPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/cetpdata";
	}

	/**
	 * �ж�SD���Ƿ����
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
	 * ����Ŀ¼
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
		// if (file.createNewFile()) { // �����ļ�
		// Toast.makeText(null, "�����ļ�" + file.toString(), 0).show();
		// }
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// } else {
		// Toast.makeText(null, "�ļ�" + file.toString() + "�Ѵ���", 0).show();
		// }
	}

	/**
	 * �ж�SD���ϵ��ļ����Ƿ����
	 */
	public static boolean isFileExist(String fileName) {
		File file = new File(getsdPath() + fileName);
		return file.exists();
	}
}
