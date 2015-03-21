package com.cetp.view;

import java.io.File;
import java.io.InputStream;

import com.cetp.R;
import com.cetp.R.layout;
import com.cetp.R.menu;
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
import com.cetp.view.DownLoadView.myButtonOnClickListener;

import android.os.Bundle;
import android.os.Environment;
import android.app.ActionBar;
import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LocalDataManageView extends Activity {
	public static final String TAG = "LocalDataManageView";
	// 定义按钮
	private RelativeLayout rlt_download0;
	private RelativeLayout rlt_download1;
	private RelativeLayout rlt_download2;
	private RelativeLayout rlt_download3;
	private RelativeLayout rlt_download4;
	private RelativeLayout rlt_reset;
	private Cursor cur;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_local_data_manage_view);
		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
				ActionBar.DISPLAY_HOME_AS_UP);
		findView();
		ButtonSetListener();
		setView();
		// actionBar.setTitle(AppVariable.Common.YearMonth.substring(0, 4) + "年"
		// + AppVariable.Common.YearMonth.substring(4) + "月" + "CET"
		// + AppVariable.Common.CetX + "题库");
		
	}
	private void findView() {
		rlt_download0 = (RelativeLayout) findViewById(R.id.rlt_download0);
		rlt_download1 = (RelativeLayout) findViewById(R.id.rlt_download1);
		rlt_download2 = (RelativeLayout) findViewById(R.id.rlt_download2);
		rlt_download3 = (RelativeLayout) findViewById(R.id.rlt_download3);
		rlt_download4 = (RelativeLayout) findViewById(R.id.rlt_download4);
		rlt_reset = (RelativeLayout) findViewById(R.id.rlt_reset);
	}

	private void setView() {
		TextView txt;
		switch (AppVariable.Common.TypeOfView) {
		case 0:// Listening
				// 判断是否导入数据库
			if (DBCommon.isListeningOfQuestion)
				rlt_download0.setVisibility(View.GONE);
			else {
				rlt_download0.setVisibility(View.VISIBLE);
				txt = (TextView) rlt_download0.findViewById(R.id.txt_loadload0);
				txt.setText("听力题目导入");
			}
			if (DBCommon.isListeningOfText)
				rlt_download1.setVisibility(View.GONE);
			else {
				rlt_download1.setVisibility(View.VISIBLE);
				txt = (TextView) rlt_download1.findViewById(R.id.txt_loadload1);
				txt.setText("听力原文导入");
			}
			if (DBCommon.isListeningOfConversation)
				rlt_download2.setVisibility(View.GONE);
			else {
				rlt_download2.setVisibility(View.VISIBLE);
				txt = (TextView) rlt_download2.findViewById(R.id.txt_loadload2);
				txt.setText("听力对话导入");
			}
			rlt_download3.setVisibility(View.GONE);

			rlt_download4.setVisibility(View.VISIBLE);
			/* 得到mp3文件名 */
			AppVariable.Common.MP3FILE = "/Lis_cet" + AppVariable.Common.CetX
					+ "_" + AppVariable.Common.YearMonth + "_mp3.mp3";
			if (MyFile.isFileExist(AppVariable.Common.MP3FILE))
				rlt_download4.setVisibility(View.GONE);
			break;
		case 1:// clozing
				// 判断是否导入数据库
			if (DBCommon.isClozingOfQuestion)
				rlt_download0.setVisibility(View.GONE);
			else {
				rlt_download0.setVisibility(View.VISIBLE);
				txt = (TextView) rlt_download0.findViewById(R.id.txt_loadload0);
				txt.setText("完型题目导入");
			}
			if (DBCommon.isClozingOfText)
				rlt_download1.setVisibility(View.GONE);
			else {
				rlt_download1.setVisibility(View.VISIBLE);
				txt = (TextView) rlt_download1.findViewById(R.id.txt_loadload1);
				txt.setText("完型原文导入");
			}
			rlt_download2.setVisibility(View.GONE);
			rlt_download3.setVisibility(View.GONE);
			rlt_download4.setVisibility(View.GONE);
			break;
		case 2:
			// 判断是否导入数据库
			if (DBCommon.isReadingOfQuestion)
				rlt_download0.setVisibility(View.GONE);
			else {
				rlt_download0.setVisibility(View.VISIBLE);
				txt = (TextView) rlt_download0.findViewById(R.id.txt_loadload0);
				txt.setText("阅读题目导入");
			}
			if (DBCommon.isReadingOfPassage)
				rlt_download1.setVisibility(View.GONE);
			else {
				rlt_download1.setVisibility(View.VISIBLE);
				txt = (TextView) rlt_download1.findViewById(R.id.txt_loadload1);
				txt.setText("阅读原文导入");
			}
			rlt_download2.setVisibility(View.GONE);
			rlt_download3.setVisibility(View.GONE);
			rlt_download4.setVisibility(View.GONE);
			break;
		case 3:
			rlt_download0.setVisibility(View.VISIBLE);
			txt = (TextView) rlt_download0.findViewById(R.id.txt_loadload0);
			txt.setText("词汇题目导入");
			rlt_download1.setVisibility(View.GONE);
			rlt_download2.setVisibility(View.GONE);
			rlt_download3.setVisibility(View.GONE);
			rlt_download4.setVisibility(View.GONE);
			break;
		}
	}

	private void ButtonSetListener() {
		rlt_download0.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				rlt_download0.setClickable(false);
				rlt_download0
						.setBackgroundResource(R.drawable.ic_preference_single_dark);
				switch (AppVariable.Common.TypeOfView) {
				case 0: {
					DBListeningOfQuestion db1 = new DBListeningOfQuestion(
							getApplicationContext());
					db1.open();// 打开数据库
					cur = db1.getItemFromYM(AppVariable.Common.YearMonth);
					// cur = db1.getAllItem();
					if (cur.getCount() == 0) {
						InputStream ips = getResources().openRawResource(
								R.raw.lis_cet4_201106);
						ReadXLS.readAllData(ips, db1);
						cur.close();
						db1.close();
						Toast.makeText(getApplicationContext(), "数据导入完毕！",
								Toast.LENGTH_SHORT).show();

					}
				}
					break;

				case 1: {
					DBClozingOfQuestion db1 = new DBClozingOfQuestion(
							getApplicationContext());
					db1.open();// 打开数据库
					cur = db1.getItemFromYM(AppVariable.Common.YearMonth);
					// cur = db1.getAllItem();
					if (cur.getCount() == 0) {
						InputStream ips = getResources().openRawResource(
								R.raw.clo_cet4_201106);
						ReadXLS.readAllData(ips, db1);
						db1.close();
						Toast.makeText(getApplicationContext(), "数据导入完毕！",
								Toast.LENGTH_SHORT).show();

					}
				}
					break;
				case 2: {
					DBReadingOfQuestion db1 = new DBReadingOfQuestion(
							getApplicationContext());
					db1.open();// 打开数据库
					cur = db1.getItemFromYM(AppVariable.Common.YearMonth);
					// cur = db1.getAllItem();
					if (cur.getCount() == 0) {
						InputStream ips = getResources().openRawResource(
								R.raw.rea_cet4_201106);
						ReadXLS.readAllData(ips, db1);
						db1.close();
						Toast.makeText(getApplicationContext(), "数据导入完毕！",
								Toast.LENGTH_SHORT).show();

					}
				}
					break;
				case 3: {
					DBVocabulary db1 = new DBVocabulary(getApplicationContext());
					db1.open();// 打开数据库
					cur = db1.getItemFromYM(AppVariable.Common.YearMonth);
					// cur = db1.getAllItem();
					if (cur.getCount() == 0) {
						InputStream ips = getResources().openRawResource(
								R.raw.voc_cet4_200606);
						ReadXLS.readAllData(ips, db1);
						db1.close();
						Toast.makeText(getApplicationContext(), "数据导入完毕！",
								Toast.LENGTH_SHORT).show();
					}
				}
					break;
				default:
					Log.v(TAG, "downloadview wrong");
				}
			}
		});
		rlt_download1.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				rlt_download1.setClickable(false);
				rlt_download1
						.setBackgroundResource(R.drawable.ic_preference_single_dark);
				switch (AppVariable.Common.TypeOfView) {
				case 0: {
					DBListeningOfText db1 = new DBListeningOfText(
							getApplicationContext());
					db1.open();// 打开数据库
					cur = db1.getItemFromYM(AppVariable.Common.YearMonth);
					// cur = db1.getAllItem();
					if (cur.getCount() == 0) {
						InputStream ips = getResources().openRawResource(
								R.raw.lis_cet4_201106);
						ReadXLS.readAllData(ips, db1);
						cur.close();
						db1.close();
						Toast.makeText(getApplicationContext(), "数据导入完毕！",
								Toast.LENGTH_SHORT).show();

					}
				}
					break;

				case 1: {
					DBClozingOfText db1 = new DBClozingOfText(
							getApplicationContext());
					db1.open();// 打开数据库
					cur = db1.getItemFromYM(AppVariable.Common.YearMonth);
					// cur = db1.getAllItem();
					if (cur.getCount() == 0) {
						InputStream ips = getResources().openRawResource(
								R.raw.clo_cet4_201106);
						ReadXLS.readAllData(ips, db1);
						db1.close();
						Toast.makeText(getApplicationContext(), "数据导入完毕！",
								Toast.LENGTH_SHORT).show();

					}
				}
					break;
				case 2: {
					DBReadingOfPassage db1 = new DBReadingOfPassage(
							getApplicationContext());
					db1.open();// 打开数据库
					cur = db1.getItemFromYM(AppVariable.Common.YearMonth);
					// cur = db1.getAllItem();
					if (cur.getCount() == 0) {
						InputStream ips = getResources().openRawResource(
								R.raw.rea_cet4_201106);
						ReadXLS.readAllData(ips, db1);
						db1.close();
						Toast.makeText(getApplicationContext(), "数据导入完毕！",
								Toast.LENGTH_SHORT).show();

					}
				}
					break;
				default:
					Log.v(TAG, "downloadview wrong");
				}
			}
		});
		rlt_download2.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				rlt_download2.setClickable(false);
				rlt_download2
						.setBackgroundResource(R.drawable.ic_preference_single_dark);
				switch (AppVariable.Common.TypeOfView) {
				case 0: {
					DBListeningOfConversation db1 = new DBListeningOfConversation(
							getApplicationContext());
					db1.open();// 打开数据库
					cur = db1.getItemFromYM(AppVariable.Common.YearMonth);
					// cur = db1.getAllItem();
					if (cur.getCount() == 0) {
						InputStream ips = getResources().openRawResource(
								R.raw.lis_cet4_201106);
						ReadXLS.readAllData(ips, db1);
						cur.close();
						db1.close();
						Toast.makeText(getApplicationContext(), "数据导入完毕！",
								Toast.LENGTH_SHORT).show();

					}
				}
					break;
				default:
					Log.v(TAG, "downloadview wrong");
				}
			}

		});
		rlt_download3.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				rlt_download3.setClickable(false);
				rlt_download3
						.setBackgroundResource(R.drawable.ic_preference_single_dark);
				Toast.makeText(getApplicationContext(), R.string.sdcarderror,
						Toast.LENGTH_SHORT).show();

			}
		});
		rlt_download4.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				rlt_download4.setClickable(false);
				rlt_download4
						.setBackgroundResource(R.drawable.ic_preference_single_dark);
				Toast.makeText(getApplicationContext(), R.string.sdcarderror,
						Toast.LENGTH_SHORT).show();

			}

		});
		rlt_reset.setOnClickListener(new myButtonOnClickListener());
	}

	class myButtonOnClickListener implements Button.OnClickListener {
		@Override
		public void onClick(View v) {
			if (v == rlt_reset) {
				DatabaseHelper databaseHelper = new DatabaseHelper(
						getApplicationContext());
				databaseHelper.deleteDatabase(getApplicationContext());
				databaseHelper.close();
				Toast.makeText(getApplicationContext(), "数据重置完毕！",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			// Intent intent = new Intent(Login.this, Welcome.class);
			// startActivity(intent);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.local_data_manage_view, menu);
		return true;
	}

}
