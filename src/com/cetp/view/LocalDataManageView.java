package com.cetp.view;

import java.io.InputStream;
import java.io.ObjectInputStream.GetField;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import com.cetp.R;
import com.cetp.R.layout;
import com.cetp.R.menu;
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

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LocalDataManageView extends FinalActivity {
	@ViewInject(id = R.id.rlt_download0, click = "rltButton")
	RelativeLayout rlt_download0;
	@ViewInject(id = R.id.rlt_download1, click = "rltButton")
	RelativeLayout rlt_download1;
	@ViewInject(id = R.id.rlt_download2, click = "rltButton")
	RelativeLayout rlt_download2;
	@ViewInject(id = R.id.rlt_download3, click = "rltButton")
	RelativeLayout rlt_download3;
	@ViewInject(id = R.id.rlt_download4, click = "rltButton")
	RelativeLayout rlt_download4;
	@ViewInject(id = R.id.rlt_reset, click = "rltButton")
	RelativeLayout rlt_reset;
	// 文件路径
	private String FilePath = AppConstant.File.WEB_FILE_PATH;
	// 文件名
	private String listeningFile = AppConstant.File.LIS_FILE;
	private String readingFile = AppConstant.File.REA_FILE;
	private String clozingFile = AppConstant.File.CLO_FILE;
	private String vocabularyFile = AppConstant.File.VOC_FILE;
	private String mp3File = AppConstant.File.MP3_FILE;
	Cursor cur;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_local_datamanage_view);

		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
				ActionBar.DISPLAY_HOME_AS_UP);
		setView();
	}

	private void setView() {
		TextView txt;
		String str_temp;
		switch (AppVariable.Common.TypeOfView) {
		case 0:// Listening
			if (DBCommon.isListeningOfQuestion) {
				rlt_download0.setVisibility(View.GONE);
			} else {
				txt = (TextView) rlt_download0.findViewById(R.id.txt_loadload0);
				txt.setText("导入听力题目");
			}
			if (DBCommon.isListeningOfText)
				rlt_download1.setVisibility(View.GONE);
			else {
				txt = (TextView) rlt_download1.findViewById(R.id.txt_loadload1);
				txt.setText("导入听力原文");
			}
			if (DBCommon.isListeningOfConversation)
				rlt_download2.setVisibility(View.GONE);
			else {
				txt = (TextView) rlt_download2.findViewById(R.id.txt_loadload2);
				txt.setText("导入听力对话");
			}

			rlt_download3.setVisibility(View.GONE);
			/* 得到mp3文件名 */
			AppVariable.Common.MP3FILE = "/Lis_cet" + AppVariable.Common.CetX
					+ "_" + AppVariable.Common.YearMonth + "_mp3.mp3";
			if (MyFile.isFileExist(AppVariable.Common.MP3FILE))
				rlt_download4.setVisibility(View.GONE);
			break;
		case 1:// clozing

			if (DBCommon.isClozingOfQuestion) {
				rlt_download0.setVisibility(View.GONE);
			} else {
				txt = (TextView) rlt_download0.findViewById(R.id.txt_loadload0);
				txt.setText("导入完型题目");
			}
			if (DBCommon.isClozingOfText)
				rlt_download1.setVisibility(View.GONE);
			else {
				txt = (TextView) rlt_download1.findViewById(R.id.txt_loadload1);
				txt.setText("导入完型原文");
			}

			rlt_download3.setVisibility(View.GONE);
			rlt_download4.setVisibility(View.GONE);
			break;
		case 2:

			if (DBCommon.isReadingOfQuestion) {
				rlt_download0.setVisibility(View.GONE);
			} else {
				txt = (TextView) rlt_download0.findViewById(R.id.txt_loadload0);
				txt.setText("导入阅读题目");
			}
			if (DBCommon.isReadingOfPassage)
				rlt_download1.setVisibility(View.GONE);
			else {
				txt = (TextView) rlt_download1.findViewById(R.id.txt_loadload1);
				txt.setText("导入阅读原文");
			}

			rlt_download2.setVisibility(View.GONE);
			rlt_download3.setVisibility(View.GONE);
			rlt_download4.setVisibility(View.GONE);
			break;
		case 3:

			if (DBCommon.isVocabulary) {
				rlt_download0.setVisibility(View.GONE);
			} else {
				txt = (TextView) rlt_download0.findViewById(R.id.txt_loadload0);
				txt.setText("导入词汇题目");
			}

			rlt_download1.setVisibility(View.GONE);
			rlt_download2.setVisibility(View.GONE);
			rlt_download3.setVisibility(View.GONE);
			rlt_download4.setVisibility(View.GONE);
			break;
		}
	}

	public void rltButton(View v) {
		if (v == rlt_download0) {
			rlt_download0
					.setBackgroundResource(R.drawable.ic_preference_single_dark);
			switch (AppVariable.Common.TypeOfView) {
			case 0:
				importListeningQuestion();
				break;
			case 1:
				importClozingQuestion();
				break;
			case 2:
				importReadingQuestion();
				break;
			case 3:
				importVocabularyQuestion();
				break;
			}
		} else if (v == rlt_download1) {
			rlt_download1
					.setBackgroundResource(R.drawable.ic_preference_single_dark);
			switch (AppVariable.Common.TypeOfView) {
			case 0:
				importListeningText();
				break;
			case 1:
				importClozingText();
				break;
			case 2:
				importReadingPassage();
				break;
			}
		} else if (v == rlt_download2) {
			rlt_download2
					.setBackgroundResource(R.drawable.ic_preference_single_dark);
			importListeningConversation();
		} else if (v == rlt_download4) {
			rlt_download4
					.setBackgroundResource(R.drawable.ic_preference_single_dark);
		} else if (v == rlt_reset) {
			DatabaseHelper databaseHelper = new DatabaseHelper(
					getApplicationContext());
			databaseHelper.deleteDatabase(getApplicationContext());
			databaseHelper.close();
			Toast.makeText(getApplicationContext(), "数据重置完毕！",
					Toast.LENGTH_SHORT).show();
		}
	}

	InputStream getInputStream(String yyyymm) {
		if (yyyymm.equals("201106")) {
			switch (AppVariable.Common.TypeOfView) {
			case 0:
				return getResources().openRawResource(R.raw.lis_cet4_201106);
			case 1:
				return null;
			case 2:
				return null;// getResources().openRawResource(R.raw.rea_cet4_201106);
			case 3:
				return null;
			}
		} else if (yyyymm.equals("201112")) {
			return null;
		}
		return null;
	}

	public void importListeningQuestion() {
		DBListeningOfQuestion db1 = new DBListeningOfQuestion(
				getApplicationContext());
		db1.open();// 打开数据库
		cur = db1.getItemFromYM(AppVariable.Common.YearMonth);
		if (cur.getCount() == 0) {
			InputStream ips = getInputStream(AppVariable.Common.YearMonth);
			if (ips != null) {
				ReadXLS.readAllData(ips, db1);
				Toast.makeText(getApplicationContext(), "数据导入完毕！",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "没有本地数据！待更新。。。",// "请先下载文件！",
						Toast.LENGTH_SHORT).show();
			}
		}
		db1.close();
		cur.close();
	}

	public void importListeningText() {
		DBListeningOfText db1 = new DBListeningOfText(getApplicationContext());
		db1.open();// 打开数据库
		cur = db1.getItemFromYM(AppVariable.Common.YearMonth);
		if (cur.getCount() == 0) {
			InputStream ips = getInputStream(AppVariable.Common.YearMonth);
			if (ips != null) {
				ReadXLS.readAllData(ips, db1);
				Toast.makeText(getApplicationContext(), "数据导入完毕！",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "没有本地数据！待更新。。。",// "请先下载文件！",
						Toast.LENGTH_SHORT).show();
			}
		}
		db1.close();
		cur.close();
	}

	public void importListeningConversation() {
		DBListeningOfConversation db1 = new DBListeningOfConversation(
				getApplicationContext());
		db1.open();// 打开数据库
		cur = db1.getItemFromYM(AppVariable.Common.YearMonth);
		// cur = db1.getAllItem();
		if (cur.getCount() == 0) {
			InputStream ips = getInputStream(AppVariable.Common.YearMonth);
			if (ips != null) {
				ReadXLS.readAllData(ips, db1);
				Toast.makeText(getApplicationContext(), "数据导入完毕！",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "没有本地数据！待更新。。。",// "请先下载文件！",
						Toast.LENGTH_SHORT).show();
			}
		}
		db1.close();
		cur.close();
	}

	public void importClozingQuestion() {
		// ---------导入完型题目-----------------
		DBClozingOfQuestion db_ClozingOfQuestion = new DBClozingOfQuestion(
				getApplicationContext());
		// db.deleteAllItem();
		db_ClozingOfQuestion.open();// 打开数据库
		cur = db_ClozingOfQuestion.getItemFromYM(AppVariable.Common.YearMonth);
		if (cur.getCount() == 0) {
			InputStream ips = getInputStream(AppVariable.Common.YearMonth);
			if (ips != null) {
				ReadXLS.readAllData(ips, db_ClozingOfQuestion);
				Toast.makeText(getApplicationContext(), "数据导入完毕！",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "没有本地数据！待更新。。。",// "请先下载文件！",
						Toast.LENGTH_SHORT).show();
			}
		}
		db_ClozingOfQuestion.close();
		cur.close();
	}

	public void importClozingText() {
		// ---------导入完型题目-----------------
		DBClozingOfText db_ClozingOfQuestion = new DBClozingOfText(
				getApplicationContext());
		// db.deleteAllItem();
		db_ClozingOfQuestion.open();// 打开数据库
		cur = db_ClozingOfQuestion.getItemFromYM(AppVariable.Common.YearMonth);
		if (cur.getCount() == 0) {
			InputStream ips = getInputStream(AppVariable.Common.YearMonth);
			if (ips != null) {
				ReadXLS.readAllData(ips, db_ClozingOfQuestion);
				Toast.makeText(getApplicationContext(), "数据导入完毕！",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "没有本地数据！待更新。。。",// "请先下载文件！",
						Toast.LENGTH_SHORT).show();
			}
		}
		db_ClozingOfQuestion.close();
		cur.close();
	}

	public void importReadingQuestion() {
		DBReadingOfQuestion db1 = new DBReadingOfQuestion(
				getApplicationContext());
		db1.open();// 打开数据库
		cur = db1.getItemFromYM(AppVariable.Common.YearMonth);
		if (cur.getCount() == 0) {
			InputStream ips = getInputStream(AppVariable.Common.YearMonth);
			if (ips != null) {
				ReadXLS.readAllData(ips, db1);
				Toast.makeText(getApplicationContext(), "数据导入完毕！",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "没有本地数据！待更新。。。",// "请先下载文件！",
						Toast.LENGTH_SHORT).show();
			}
		}
		db1.close();
		cur.close();
	}

	public void importReadingPassage() {
		DBReadingOfPassage db1 = new DBReadingOfPassage(getApplicationContext());
		db1.open();// 打开数据库
		cur = db1.getItemFromYM(AppVariable.Common.YearMonth);
		if (cur.getCount() == 0) {
			InputStream ips = getInputStream(AppVariable.Common.YearMonth);
			if (ips != null) {
				ReadXLS.readAllData(ips, db1);
				Toast.makeText(getApplicationContext(), "数据导入完毕！",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "没有本地数据！待更新。。。",// "请先下载文件！",
						Toast.LENGTH_SHORT).show();
			}
		}
		db1.close();
		cur.close();
	}

	public void importVocabularyQuestion() {
		DBVocabulary db1 = new DBVocabulary(getApplicationContext());
		db1.open();// 打开数据库

		if (cur.getCount() == 0) {
			InputStream ips = getInputStream(AppVariable.Common.YearMonth);
			if (ips != null) {
				ReadXLS.readAllData(ips, db1);
				Toast.makeText(getApplicationContext(), "数据导入完毕！",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "没有本地数据！待更新。。。",// "请先下载文件！",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.local_datamanage_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
