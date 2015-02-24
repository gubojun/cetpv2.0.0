package com.cetp.view;

import java.io.File;

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
	// ���尴ť
	private RelativeLayout rlt_listening_download;
	private RelativeLayout rlt_reading_download;
	private RelativeLayout rlt_clozing_download;
	private RelativeLayout rlt_vocabulary_download;
	private RelativeLayout rlt_listeningmp3_download;
	private RelativeLayout rlt_reset;
	/** �������� */
	private Button btn_cet4_listening;
	private Button btn_cet4_reading;
	private Button btn_cet4_clozing;
	private Button btn_cet4_vocabulary;

	private LinearLayout layout;
	public static int NOW_VIEW = 1;// ��ǰ����ţ�������
	/** ������������ */
	private Button downloadtxt_Listening;
	private Button downloadtxt_Reading;
	private Button downloadtxt_Clozing;
	private Button downloadtxt_Vocabulary;
	private Button downloadmp3button;
	/** �������� */
	private Button btn_ResetData;

	/** ���ؽ�����-���� */
	private ProgressBar downloadbar;
	// private EditText pathText;
	/** ���ؽ��Ȱٷֱ� */
	private TextView resultView;
	private TextView txt_filelibrary_title;
	// �ļ�·��
	private String FilePath = AppConstant.File.WEB_FILE_PATH;
	// �ļ���
	private String listeningFile = AppConstant.File.LIS_FILE;
	private String readingFile = AppConstant.File.REA_FILE;
	private String clozingFile = AppConstant.File.CLO_FILE;
	private String vocabularyFile = AppConstant.File.VOC_FILE;
	private String mp3File = AppConstant.File.MP3_FILE + "xixi";
	// �ļ�����·��
	private String listeningUrl = FilePath + listeningFile;
	private String readingUrl = FilePath + readingFile;
	private String clozingUrl = FilePath + clozingFile;
	private String vocabularyUrl = FilePath + vocabularyFile;
	private String mp3Url = FilePath + mp3File;
	AlertDialog downloadDialog;
	Cursor cur;
	DatabaseHelper mDbHelper = null;
	/** ���� */
	private Handler handler = new Handler() {
		// ��Ϣ
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
					Toast.makeText(DownLoadView.this, "���سɹ�",
							Toast.LENGTH_SHORT).show();
				}
				break;
			case -1:
				downloadDialog.dismiss();
				Toast.makeText(DownLoadView.this, "����ʧ��", Toast.LENGTH_SHORT)
						.show();
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "Activity State: onCreate()");
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		setContentView(R.layout.downloadview);

		findView();
		ButtonSetListener();
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
		txt_filelibrary_title.setText(AppVariable.Common.YearMonth.substring(0,
				4)
				+ "��"
				+ AppVariable.Common.YearMonth.substring(4)
				+ "��"
				+ "CET" + AppVariable.Common.CetX + "���");
	}

//	private void showDownloadDialog() {
//		LayoutInflater inflater = (LayoutInflater) getApplicationContext()
//				.getSystemService(LAYOUT_INFLATER_SERVICE);
//		// view���Զ���Ի���
//		View view = inflater.inflate(R.layout.downloadview_dialog, null);
//		downloadDialog = new AlertDialog.Builder(this).setTitle("��������")
//				.setIcon(android.R.drawable.ic_dialog_info).setView(view)
//				.setNegativeButton("ȡ��", null).show();
//		downloadbar = (ProgressBar) view
//				.findViewById(R.id.prg_downloadview_dialog_downloadbar);
//		resultView = (TextView) view
//				.findViewById(R.id.txt_downloadview_dialog_result);
//	}

	private void ButtonSetListener() {
		rlt_listening_download.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				String path = listeningUrl;
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
//					showDownloadDialog();
					File dir = new File(Environment
							.getExternalStorageDirectory() + "/cetpdata");// �ļ�����Ŀ¼
					download(path, dir);
					importListeningQuestion();
				} else {
					Toast.makeText(DownLoadView.this, R.string.sdcarderror,
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		rlt_reading_download.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				String path = readingUrl;
				// SD����������
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
//					showDownloadDialog();
					File dir = new File(Environment
							.getExternalStorageDirectory() + "/cetpdata/");// �ļ�����Ŀ¼
					download(path, dir);
					importReadingQuestion();
				} else {
					Toast.makeText(DownLoadView.this, R.string.sdcarderror,
							Toast.LENGTH_SHORT).show();
				}
			}

		});
		rlt_clozing_download.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				String path = clozingUrl;
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
//					showDownloadDialog();
					File dir = new File(Environment
							.getExternalStorageDirectory() + "/cetpdata/");// �ļ�����Ŀ¼
					download(path, dir);
					importClozingQuestion();
				} else {
					Toast.makeText(DownLoadView.this, R.string.sdcarderror,
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		rlt_vocabulary_download
				.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						String path = vocabularyUrl;
						if (Environment.getExternalStorageState().equals(
								Environment.MEDIA_MOUNTED)) {
//							showDownloadDialog();
							File dir = new File(Environment
									.getExternalStorageDirectory()
									+ "/cetpdata/");// �ļ�����Ŀ¼
							download(path, dir);
							importVocabularyQuestion();
						} else {
							Toast.makeText(DownLoadView.this,
									R.string.sdcarderror, Toast.LENGTH_SHORT)
									.show();
						}
					}
				});
		rlt_listeningmp3_download
				.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						mp3Url = FilePath + mp3File;
						Log.v(TAG, mp3Url);
						String path = mp3Url;
						if (Environment.getExternalStorageState().equals(
								Environment.MEDIA_MOUNTED)) {
//							showDownloadDialog();
							File dir = new File(Environment
									.getExternalStorageDirectory()
									+ "/cetpdata/");// �ļ�����Ŀ¼
							download(path, dir);
						} else {
							Toast.makeText(DownLoadView.this,
									R.string.sdcarderror, Toast.LENGTH_SHORT)
									.show();
						}
					}

				});

		rlt_reset.setOnClickListener(new myButtonOnClickListener());

	}

	private void findView() {
		layout = (LinearLayout) findViewById(R.id.lin1);
		rlt_listening_download = (RelativeLayout) findViewById(R.id.rlt_cet4_listening_download);
		rlt_reading_download = (RelativeLayout) findViewById(R.id.rlt_cet4_reading_download);
		rlt_clozing_download = (RelativeLayout) findViewById(R.id.rlt_cet4_clozing_download);
		rlt_vocabulary_download = (RelativeLayout) findViewById(R.id.rlt_cet4_vocabulary_download);
		rlt_listeningmp3_download = (RelativeLayout) findViewById(R.id.rlt_cet4_listeningmp3_download);
		rlt_reset = (RelativeLayout) findViewById(R.id.rlt_reset);

		txt_filelibrary_title = (TextView) findViewById(R.id.txt_filelibrary_title);
	}

	public void importListeningQuestion() {
		DBListeningOfQuestion db1 = new DBListeningOfQuestion(DownLoadView.this);
		db1.open();// �����ݿ�
		cur = db1.getItemFromYM(AppVariable.Common.YearMonth);
		// cur = db1.getAllItem();
		if (cur.getCount() == 0) {
			String FileURL = MyFile.getsdPath() + "/" + listeningFile;
			if (MyFile.isFileExist("/" + listeningFile)) {

				ReadXLS.readAllData(FileURL, db1);
				db1.close();
				Toast.makeText(DownLoadView.this, "���ݵ�����ϣ�", Toast.LENGTH_SHORT)
						.show();
				DBListeningOfText db2 = new DBListeningOfText(DownLoadView.this);
				db2.open();// �����ݿ�

				// String FileURL = MyFile.getsdPath() + "/Listening_CET4.xls";
				ReadXLS.readAllData(FileURL, db2);
				db2.close();
				Toast.makeText(DownLoadView.this, "���ݵ�����ϣ�", Toast.LENGTH_SHORT)
						.show();
				DBListeningOfConversation db3 = new DBListeningOfConversation(
						DownLoadView.this);
				db3.open();// �����ݿ�

				// String FileURL = MyFile.getsdPath() + "/Listening_CET4.xls";
				ReadXLS.readAllData(FileURL, db3);
				db3.close();
				Toast.makeText(DownLoadView.this, "���ݵ�����ϣ�", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(DownLoadView.this, "û���������ݣ������¡�����",// "���������ļ���",
						Toast.LENGTH_SHORT).show();
			}
		} else
			Toast.makeText(DownLoadView.this, "���������ļ���", Toast.LENGTH_SHORT)
					.show();
		db1.close();
		cur.close();
	}

	public void importReadingQuestion() {
		DBReadingOfQuestion db1 = new DBReadingOfQuestion(DownLoadView.this);
		db1.open();// �����ݿ�
		cur = db1.getItemFromYM(AppVariable.Common.YearMonth);
		// cur = db1.getAllItem();
		if (cur.getCount() == 0) {
			Toast.makeText(DownLoadView.this, "reading����...",
					Toast.LENGTH_SHORT).show();
			// �õ��ļ���·��
			String FileURL = MyFile.getsdPath() + "/" + readingFile;
			if (MyFile.isFileExist("/" + readingFile)) {

				ReadXLS.readAllData(FileURL, db1);
				db1.close();
				Toast.makeText(DownLoadView.this, "���ݵ�����ϣ�", Toast.LENGTH_SHORT)
						.show();
				DBReadingOfPassage db2 = new DBReadingOfPassage(
						DownLoadView.this);
				db2.open();// �����ݿ�

				ReadXLS.readAllData(FileURL, db2);
				db2.close();
				Toast.makeText(DownLoadView.this, "���ݵ�����ϣ�", Toast.LENGTH_SHORT)
						.show();
			} else
				Toast.makeText(DownLoadView.this, "�����Ѿ����룡", Toast.LENGTH_SHORT)
						.show();
		} else {
			Toast.makeText(DownLoadView.this, "û���������ݣ������¡�����"// "���������ļ���"
					, Toast.LENGTH_SHORT).show();
		}
		db1.close();
		cur.close();
	}

	public void importClozingQuestion() {
		// ---------����������Ŀ-----------------
		DBClozingOfQuestion db_ClozingOfQuestion = new DBClozingOfQuestion(
				DownLoadView.this);
		// db.deleteAllItem();
		db_ClozingOfQuestion.open();// �����ݿ�
		cur = db_ClozingOfQuestion.getItemFromYM(AppVariable.Common.YearMonth);
		// cur = db_ClozingOfQuestion.getAllItem();
		if (cur.getCount() == 0) {
			String FileURL = MyFile.getsdPath() + "/" + clozingFile;
			if (MyFile.isFileExist("/" + clozingFile)) {
				ReadXLS.readAllData(FileURL, db_ClozingOfQuestion);
				db_ClozingOfQuestion.close();
				Log.d(TAG, "db.close()");
				Toast.makeText(DownLoadView.this, "���ݵ�����ϣ�", Toast.LENGTH_SHORT)
						.show();
				// ----------������������-----------------
				DBClozingOfText db_ClozingOfText = new DBClozingOfText(
						DownLoadView.this);
				// db.deleteAllItem();
				db_ClozingOfText.open();// �����ݿ�
				// String FileURL = MyFile.getsdPath() + "/Clozing.xls";
				ReadXLS.readAllData(FileURL, db_ClozingOfText);
				db_ClozingOfText.close();
				Log.d(TAG, "db.close()");
				Toast.makeText(DownLoadView.this, "���ݵ�����ϣ�", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(DownLoadView.this, "�����Ѿ����룡", Toast.LENGTH_SHORT)
						.show();
			}
		} else {
			Toast.makeText(DownLoadView.this, "û���������ݣ������¡�����"// "���������ļ���"
					, Toast.LENGTH_SHORT).show();
		}
		db_ClozingOfQuestion.close();
		cur.close();
	}

	public void importVocabularyQuestion() {
		DBVocabulary db = new DBVocabulary(DownLoadView.this);
		// db.deleteAllItem();
		db.open();// �����ݿ�
		cur = db.getItemFromYM(AppVariable.Common.YearMonth);
		// cur = db.getAllItem();
		if (cur.getCount() == 0) {
			String FileURL = MyFile.getsdPath() + "/" + vocabularyFile;
			if (MyFile.isFileExist("/" + vocabularyFile)) {
				ReadXLS.readAllData(FileURL, db);
				db.close();
				Log.d(TAG, "db.close()");
				Toast.makeText(DownLoadView.this, "���ݵ�����ϣ�", Toast.LENGTH_SHORT)
						.show();
			} else
				Toast.makeText(DownLoadView.this, "�����Ѿ����룡", Toast.LENGTH_SHORT)
						.show();
		} else {
			Toast.makeText(DownLoadView.this, "û���������ݣ������¡�����"// "���������ļ���"
					, Toast.LENGTH_SHORT).show();
		}
		db.close();
		cur.close();
	}

	// ����UI�ؼ��ĸ���ֻ�������߳�(UI�߳�)��������ڷ�UI�̸߳���UI�ؼ������µĽ�����ᷴӳ����Ļ�ϣ�ĳЩ�ؼ��������
	private void download(final String path, final File dir) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					SmartFileDownloader loader = new SmartFileDownloader(
							DownLoadView.this, path, dir, 3);
					int length = loader.getFileSize();// ��ȡ�ļ��ĳ���
					System.out.println(String.valueOf(length));
					downloadbar.setMax(length);
					loader.download(new SmartDownloadProgressListener() {
						@Override
						public void onDownloadSize(int size) {// ����ʵʱ�õ��ļ����صĳ���
							Message msg = new Message();
							msg.what = 1;
							msg.getData().putInt("size", size);
							handler.sendMessage(msg);
						}
					});
				} catch (Exception e) {
					Message msg = new Message();// ��Ϣ��ʾ
					msg.what = -1;
					msg.getData().putString("error", "����ʧ��");// ������ش�����ʾ��ʾʧ�ܣ�
					handler.sendMessage(msg);
				}
			}
		}).start();// ��ʼ
	}

	/**
	 * ��д��okKeyDown���������ղ�������̰����¼�
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
			if (v == btn_cet4_listening || v == rlt_listening_download) {
				importListeningQuestion();
			} else if (v == btn_cet4_reading || v == rlt_reading_download) {
				importReadingQuestion();
			} else if (v == btn_cet4_clozing || v == rlt_clozing_download) {
				importClozingQuestion();
			} else if (v == btn_cet4_vocabulary || v == rlt_vocabulary_download) {
				importVocabularyQuestion();
			} else if (v == btn_ResetData || v == rlt_reset) {
				DatabaseHelper databaseHelper = new DatabaseHelper(
						DownLoadView.this);
				databaseHelper.deleteDatabase(DownLoadView.this);
				databaseHelper.close();
				Toast.makeText(DownLoadView.this, "����������ϣ�", Toast.LENGTH_SHORT)
						.show();
			} else if (v == rlt_listeningmp3_download) {
				mp3Url = FilePath + mp3File;
				Log.v(TAG, mp3Url);
				String path = mp3Url;
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
//					showDownloadDialog();
					File dir = new File(
							Environment.getExternalStorageDirectory()
									+ "/cetpdata/");// �ļ�����Ŀ¼
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
