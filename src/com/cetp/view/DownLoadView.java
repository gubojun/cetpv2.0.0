package com.cetp.view;

import java.io.File;

import javax.crypto.spec.PSource;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cetp.R;
import com.cetp.action.AppConstant;
import com.cetp.action.AppVariable;
import com.cetp.database.DBClozingOfQuestion;
import com.cetp.database.DBClozingOfText;
import com.cetp.database.DBCommon;
import com.cetp.database.DBListeningOfConversation;
import com.cetp.database.DBListeningOfQuestion;
import com.cetp.database.DBListeningOfText;
import com.cetp.database.DBReadingOfPassage;
import com.cetp.database.DBReadingOfQuestion;
import com.cetp.database.DBVocabulary;
import com.cetp.database.DatabaseHelper;
import com.cetp.excel.MyFile;
import com.cetp.excel.ReadXLS;
import com.cetp.smart.impl.SmartDownloadProgressListener;
import com.cetp.smart.impl.SmartFileDownloader;

public class DownLoadView extends Activity {
	public static final String TAG = "DownLoadView";
	// 定义按钮
	private RelativeLayout rlt_download0;
	private RelativeLayout rlt_download1;
	private RelativeLayout rlt_download2;
	private RelativeLayout rlt_download3;
	private RelativeLayout rlt_download4;
	private RelativeLayout rlt_reset;

	private LinearLayout layout;
	public static int NOW_VIEW = 1;// 当前窗体号，测试用

	/** 下载进度条-方形 */
	private ProgressBar downloadbar;
	// private EditText pathText;
	/** 下载进度百分比 */
	private TextView resultView;
	private TextView txt_title;
	// 文件路径
	private String FilePath = AppConstant.File.WEB_FILE_PATH;
	// 文件名
	private String listeningFile = AppConstant.File.LIS_FILE;
	private String readingFile = AppConstant.File.REA_FILE;
	private String clozingFile = AppConstant.File.CLO_FILE;
	private String vocabularyFile = AppConstant.File.VOC_FILE;
	private String mp3File = AppConstant.File.MP3_FILE;
	// 文件下载路径
	private String listeningUrl = FilePath + listeningFile;
	private String readingUrl = FilePath + readingFile;
	private String clozingUrl = FilePath + clozingFile;
	private String vocabularyUrl = FilePath + vocabularyFile;
	private String mp3Url = FilePath + mp3File;
	AlertDialog downloadDialog;
	Cursor cur;
	DatabaseHelper mDbHelper = null;
	/** 下载 */
	private Handler handler = new Handler() {
		// 信息
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				int size = msg.getData().getInt("size");
				downloadbar.setProgress(size);
				float result = (float) downloadbar.getProgress()
						/ (float) downloadbar.getMax();
				int p = (int) (result * 100);
				resultView.setText(p + "%");
				if (downloadbar.getProgress() == downloadbar.getMax()) {
					downloadDialog.dismiss();
					Toast.makeText(DownLoadView.this, "下载成功",
							Toast.LENGTH_SHORT).show();
				}
				break;
			case -1:
				downloadDialog.dismiss();
				Toast.makeText(DownLoadView.this, "下载失败", Toast.LENGTH_SHORT)
						.show();
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "Activity State: onCreate()");
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.downloadview);

		findView();
		ButtonSetListener();
		setView();

		if (AppVariable.Common.YearMonth != ""
				&& AppVariable.Common.QUESTION_FILENAME != "") {
			listeningFile = "Lis_CET" + AppVariable.Common.CetX + "_"
					+ AppVariable.Common.YearMonth + ".xls";
			readingFile = "Rea_CET" + AppVariable.Common.CetX + "_"
					+ AppVariable.Common.YearMonth + ".xls";
			clozingFile = "Clo_CET" + AppVariable.Common.CetX + "_"
					+ AppVariable.Common.YearMonth + ".xls";
			vocabularyFile = "Voc_CET" + AppVariable.Common.CetX + "_"
					+ AppVariable.Common.YearMonth + ".xls";
			mp3File = "Lis_cet" + AppVariable.Common.CetX + "_"
					+ AppVariable.Common.YearMonth + "_mp3.mp3";
		}
		// txt_title.setText(AppVariable.Common.YearMonth.substring(0, 4) + "年"
		// + AppVariable.Common.YearMonth.substring(4) + "月" + "CET"
		// + AppVariable.Common.CetX + "题库");
	}

	private void showDownloadDialog() {
		LayoutInflater inflater = (LayoutInflater) getApplicationContext()
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		// view绑定自定义对话框
		View view = inflater.inflate(R.layout.downloadview_dialog, null);
		downloadDialog = new AlertDialog.Builder(this).setTitle("下载数据")
				.setIcon(android.R.drawable.ic_dialog_info).setView(view)
				.setNegativeButton("取消", null).show();
		downloadbar = (ProgressBar) view
				.findViewById(R.id.prg_downloadview_dialog_downloadbar);
		resultView = (TextView) view
				.findViewById(R.id.txt_downloadview_dialog_result);
	}

	private void setView() {
		TextView txt;
		switch (AppVariable.Common.TypeOfView) {
		case 0:// Listening
			if (DBCommon.isListeningOfQuestion)
				rlt_download0.setVisibility(View.GONE);
			else {
				txt = (TextView) rlt_download0.findViewById(R.id.txt_loadload0);
				txt.setText("听力题目下载");
			}
			if (DBCommon.isListeningOfText)
				rlt_download1.setVisibility(View.GONE);
			else {
				txt = (TextView) rlt_download1.findViewById(R.id.txt_loadload1);
				txt.setText("听力原文下载");
			}
			if (DBCommon.isListeningOfConversation)
				rlt_download2.setVisibility(View.GONE);
			else {
				txt = (TextView) rlt_download2.findViewById(R.id.txt_loadload2);
				txt.setText("听力对话下载");
			}
			rlt_download3.setVisibility(View.GONE);

			/* 得到mp3文件名 */
			AppVariable.Common.MP3FILE = "/Lis_cet" + AppVariable.Common.CetX
					+ "_" + AppVariable.Common.YearMonth + "_mp3.mp3";
			if (MyFile.isFileExist(AppVariable.Common.MP3FILE))
				rlt_download4.setVisibility(View.GONE);
			break;
		case 1:// clozing
			if (DBCommon.isClozingOfQuestion)
				rlt_download0.setVisibility(View.GONE);
			else {
				txt = (TextView) rlt_download0.findViewById(R.id.txt_loadload0);
				txt.setText("完型题目下载");
			}
			if (DBCommon.isClozingOfText)
				rlt_download1.setVisibility(View.GONE);
			else {
				txt = (TextView) rlt_download1.findViewById(R.id.txt_loadload1);
				txt.setText("完型原文下载");
			}
			rlt_download2.setVisibility(View.GONE);
			rlt_download3.setVisibility(View.GONE);
			rlt_download4.setVisibility(View.GONE);
			break;
		case 2:
			if (DBCommon.isReadingOfQuestion)
				rlt_download0.setVisibility(View.GONE);
			else {
				txt = (TextView) rlt_download0.findViewById(R.id.txt_loadload0);
				txt.setText("阅读题目下载");
			}
			if (DBCommon.isReadingOfPassage)
				rlt_download1.setVisibility(View.GONE);
			else {
				txt = (TextView) rlt_download1.findViewById(R.id.txt_loadload1);
				txt.setText("阅读原文下载");
			}
			rlt_download2.setVisibility(View.GONE);
			rlt_download3.setVisibility(View.GONE);
			rlt_download4.setVisibility(View.GONE);
			break;
		case 3:
			if (DBCommon.isVocabulary)
				rlt_download0.setVisibility(View.GONE);
			else {
				txt = (TextView) rlt_download0.findViewById(R.id.txt_loadload0);
				txt.setText("词汇题目下载");
			}
			rlt_download1.setVisibility(View.GONE);
			rlt_download2.setVisibility(View.GONE);
			rlt_download3.setVisibility(View.GONE);
			rlt_download4.setVisibility(View.GONE);
			break;
		}

	}

	private void ButtonSetListener() {
		LinearLayout lin = (LinearLayout) findViewById(R.id.lin_download);
		ProgressBar progress = new ProgressBar(this);
		lin.addView(progress);
		rlt_download0.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				String path = listeningUrl;
				// 自己网盘里的文件，下载下来测试一下
				path = "http://180.97.83.168:443/down/c33e861cd11e91a906383bf96fef539a-44544/Lis_CET4_201106.xls?cts=dx-f-182A136A67A2499659345&ctp=182A136A67A249&ctt=1424874350&limit=2&spd=2200000&ctk=cc1e0ee623b0a16999500c6ff0d49777&chk=c33e861cd11e91a906383bf96fef539a-44544&mtd=1";
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					showDownloadDialog();
					File dir = new File(Environment
							.getExternalStorageDirectory() + "/cetpdata");// 文件保存目录
					download(path, dir);
					// importListeningQuestion();
				} else {
					Toast.makeText(DownLoadView.this, R.string.sdcarderror,
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		rlt_download1.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				String path = clozingUrl;
				path = "http://180.97.83.168:443/down/90c0b91845f0ac268e85227fe1b0c2b5-29184/Clo_CET4_201106.xls?cts=dx-f-182A136A67A2499659345&ctp=182A136A67A249&ctt=1424879051&limit=2&spd=2200000&ctk=2a93701350298d72dc6151cb0a07d3d0&chk=90c0b91845f0ac268e85227fe1b0c2b5-29184&mtd=1";
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// showDownloadDialog();
					File dir = new File(Environment
							.getExternalStorageDirectory() + "/cetpdata/");// 文件保存目录
					download(path, dir);
					// importClozingQuestion();
				} else {
					Toast.makeText(DownLoadView.this, R.string.sdcarderror,
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		rlt_download2.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				String path = readingUrl;
				// SD卡正常挂载
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// showDownloadDialog();
					File dir = new File(Environment
							.getExternalStorageDirectory() + "/cetpdata/");// 文件保存目录
					download(path, dir);
					importReadingQuestion();
				} else {
					Toast.makeText(DownLoadView.this, R.string.sdcarderror,
							Toast.LENGTH_SHORT).show();
				}
			}

		});

		rlt_download3.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				String path = vocabularyUrl;
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// showDownloadDialog();
					File dir = new File(Environment
							.getExternalStorageDirectory() + "/cetpdata/");// 文件保存目录
					download(path, dir);
					importVocabularyQuestion();
				} else {
					Toast.makeText(DownLoadView.this, R.string.sdcarderror,
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		rlt_download4.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				mp3Url = FilePath + mp3File;
				Log.v(TAG, mp3Url);
				String path = mp3Url;
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// showDownloadDialog();
					File dir = new File(Environment
							.getExternalStorageDirectory() + "/cetpdata/");// 文件保存目录
					download(path, dir);
				} else {
					Toast.makeText(DownLoadView.this, R.string.sdcarderror,
							Toast.LENGTH_SHORT).show();
				}
			}

		});

		rlt_reset.setOnClickListener(new myButtonOnClickListener());

	}

	private void findView() {
		layout = (LinearLayout) findViewById(R.id.lin1);
		rlt_download0 = (RelativeLayout) findViewById(R.id.rlt_download0);
		rlt_download1 = (RelativeLayout) findViewById(R.id.rlt_download1);
		rlt_download2 = (RelativeLayout) findViewById(R.id.rlt_download2);
		rlt_download3 = (RelativeLayout) findViewById(R.id.rlt_download3);
		rlt_download4 = (RelativeLayout) findViewById(R.id.rlt_download4);
		rlt_reset = (RelativeLayout) findViewById(R.id.rlt_reset);

		// txt_title = (TextView) findViewById(R.id.title);
	}

	public void importListeningQuestion() {
		DBListeningOfQuestion db1 = new DBListeningOfQuestion(DownLoadView.this);
		db1.open();// 打开数据库
		cur = db1.getItemFromYM(AppVariable.Common.YearMonth);
		// cur = db1.getAllItem();
		if (cur.getCount() == 0) {
			String FileURL = MyFile.getsdPath() + "/" + listeningFile;
			if (MyFile.isFileExist("/" + listeningFile)) {

				ReadXLS.readAllData(FileURL, db1);
				db1.close();
				Toast.makeText(DownLoadView.this, "数据导入完毕！", Toast.LENGTH_SHORT)
						.show();
				DBListeningOfText db2 = new DBListeningOfText(DownLoadView.this);
				db2.open();// 打开数据库

				// String FileURL = MyFile.getsdPath() + "/Listening_CET4.xls";
				ReadXLS.readAllData(FileURL, db2);
				db2.close();
				Toast.makeText(DownLoadView.this, "数据导入完毕！", Toast.LENGTH_SHORT)
						.show();
				DBListeningOfConversation db3 = new DBListeningOfConversation(
						DownLoadView.this);
				db3.open();// 打开数据库

				// String FileURL = MyFile.getsdPath() + "/Listening_CET4.xls";
				ReadXLS.readAllData(FileURL, db3);
				db3.close();
				Toast.makeText(DownLoadView.this, "数据导入完毕！", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(DownLoadView.this, "没有网络数据！待更新。。。",// "请先下载文件！",
						Toast.LENGTH_SHORT).show();
			}
		} else
			Toast.makeText(DownLoadView.this, "请先下载文件！", Toast.LENGTH_SHORT)
					.show();
		db1.close();
		cur.close();
	}

	public void importReadingQuestion() {
		DBReadingOfQuestion db1 = new DBReadingOfQuestion(DownLoadView.this);
		db1.open();// 打开数据库
		cur = db1.getItemFromYM(AppVariable.Common.YearMonth);
		// cur = db1.getAllItem();
		if (cur.getCount() == 0) {
			Toast.makeText(DownLoadView.this, "reading导入...",
					Toast.LENGTH_SHORT).show();
			// 得到文件的路径
			String FileURL = MyFile.getsdPath() + "/" + readingFile;
			if (MyFile.isFileExist("/" + readingFile)) {

				ReadXLS.readAllData(FileURL, db1);
				db1.close();
				Toast.makeText(DownLoadView.this, "数据导入完毕！", Toast.LENGTH_SHORT)
						.show();
				DBReadingOfPassage db2 = new DBReadingOfPassage(
						DownLoadView.this);
				db2.open();// 打开数据库

				ReadXLS.readAllData(FileURL, db2);
				db2.close();
				Toast.makeText(DownLoadView.this, "数据导入完毕！", Toast.LENGTH_SHORT)
						.show();
			} else
				Toast.makeText(DownLoadView.this, "数据已经导入！", Toast.LENGTH_SHORT)
						.show();
		} else {
			Toast.makeText(DownLoadView.this, "没有网络数据！待更新。。。"// "请先下载文件！"
					, Toast.LENGTH_SHORT).show();
		}
		db1.close();
		cur.close();
	}

	public void importClozingQuestion() {
		// ---------导入完型题目-----------------
		DBClozingOfQuestion db_ClozingOfQuestion = new DBClozingOfQuestion(
				DownLoadView.this);
		// db.deleteAllItem();
		db_ClozingOfQuestion.open();// 打开数据库
		cur = db_ClozingOfQuestion.getItemFromYM(AppVariable.Common.YearMonth);
		// cur = db_ClozingOfQuestion.getAllItem();
		if (cur.getCount() == 0) {
			String FileURL = MyFile.getsdPath() + "/" + clozingFile;
			if (MyFile.isFileExist("/" + clozingFile)) {
				ReadXLS.readAllData(FileURL, db_ClozingOfQuestion);
				db_ClozingOfQuestion.close();
				Log.d(TAG, "db.close()");
				Toast.makeText(DownLoadView.this, "数据导入完毕！", Toast.LENGTH_SHORT)
						.show();
				// ----------导入完型文章-----------------
				DBClozingOfText db_ClozingOfText = new DBClozingOfText(
						DownLoadView.this);
				// db.deleteAllItem();
				db_ClozingOfText.open();// 打开数据库
				// String FileURL = MyFile.getsdPath() + "/Clozing.xls";
				ReadXLS.readAllData(FileURL, db_ClozingOfText);
				db_ClozingOfText.close();
				Log.d(TAG, "db.close()");
				Toast.makeText(DownLoadView.this, "数据导入完毕！", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(DownLoadView.this, "数据已经导入！", Toast.LENGTH_SHORT)
						.show();
			}
		} else {
			Toast.makeText(DownLoadView.this, "没有网络数据！待更新。。。"// "请先下载文件！"
					, Toast.LENGTH_SHORT).show();
		}
		db_ClozingOfQuestion.close();
		cur.close();
	}

	public void importVocabularyQuestion() {
		DBVocabulary db = new DBVocabulary(DownLoadView.this);
		// db.deleteAllItem();
		db.open();// 打开数据库
		cur = db.getItemFromYM(AppVariable.Common.YearMonth);
		// cur = db.getAllItem();
		if (cur.getCount() == 0) {
			String FileURL = MyFile.getsdPath() + "/" + vocabularyFile;
			if (MyFile.isFileExist("/" + vocabularyFile)) {
				ReadXLS.readAllData(FileURL, db);
				db.close();
				Log.d(TAG, "db.close()");
				Toast.makeText(DownLoadView.this, "数据导入完毕！", Toast.LENGTH_SHORT)
						.show();
			} else
				Toast.makeText(DownLoadView.this, "数据已经导入！", Toast.LENGTH_SHORT)
						.show();
		} else {
			Toast.makeText(DownLoadView.this, "没有网络数据！待更新。。。"// "请先下载文件！"
					, Toast.LENGTH_SHORT).show();
		}
		db.close();
		cur.close();
	}

	// 对于UI控件的更新只能由主线程(UI线程)负责，如果在非UI线程更新UI控件，更新的结果不会反映在屏幕上，某些控件还会出错
	private void download(final String path, final File dir) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					SmartFileDownloader loader = new SmartFileDownloader(
							DownLoadView.this, path, dir, 3);
					int length = loader.getFileSize();// 获取文件的长度
					System.out.println(String.valueOf(length));
					downloadbar.setMax(length);
					loader.download(new SmartDownloadProgressListener() {
						@Override
						public void onDownloadSize(int size) {// 可以实时得到文件下载的长度
							Message msg = new Message();
							msg.what = 1;
							msg.getData().putInt("size", size);
							handler.sendMessage(msg);
						}
					});
				} catch (Exception e) {
					Message msg = new Message();// 信息提示
					msg.what = -1;
					msg.getData().putString("error", "下载失败");// 如果下载错误，显示提示失败！
					handler.sendMessage(msg);
				}
			}
		}).start();// 开始
	}

	/**
	 * 重写的okKeyDown方法，接收并处理键盘按下事件
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent e) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK: {
			this.finish();
		}
		}
		return super.onKeyDown(keyCode, e);
	}

	class myButtonOnClickListener implements Button.OnClickListener {

		@Override
		public void onClick(View v) {
			if (v == rlt_reset) {
				DatabaseHelper databaseHelper = new DatabaseHelper(
						DownLoadView.this);
				databaseHelper.deleteDatabase(DownLoadView.this);
				databaseHelper.close();
				Toast.makeText(DownLoadView.this, "数据重置完毕！", Toast.LENGTH_SHORT)
						.show();
			} else if (v == rlt_download4) {
				mp3Url = FilePath + mp3File;
				Log.v(TAG, mp3Url);
				String path = mp3Url;
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// showDownloadDialog();
					File dir = new File(
							Environment.getExternalStorageDirectory()
									+ "/cetpdata/");// 文件保存目录
					download(path, dir);
				} else {
					Toast.makeText(DownLoadView.this, R.string.sdcarderror,
							Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	class DownloadbuttonListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// if (v == downloadtxt_Listening) {
			// downloadFile("http://www.guanghezhang.com/public/Listening_CET4_198901.xls");
			// } else if (v == downloadtxt_Reading) {
			//
			// } else if (v == downloadtxt_Clozing) {
			//
			// } else if (v == downloadtxt_Vocabulary) {
			// downloadFile("http://www.guanghezhang.com/public/Vocabulary_and_Structure_CET4_198901.xls");
			// } else if (v == downloadmp3button) {
			// downloadFile("http://www.guanghezhang.com/public/cet4_199301.mp3");
			// }
		}
	}
}
