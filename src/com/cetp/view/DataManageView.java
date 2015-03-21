package com.cetp.view;

import com.cetp.R;
import com.cetp.R.layout;
import com.cetp.R.menu;
import com.cetp.action.AppConstant;
import com.cetp.action.AppVariable;
import com.cetp.database.DBCommon;
import com.cetp.view.SettingView.RelativeLayoutOnClickListener;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class DataManageView extends Activity {
	private final String TAG = "DataManageView";
	private RelativeLayout rltDataManageLocal;
	private RelativeLayout rltDataManageInternet;
	/** 界面切换效果 */
	private int[][] ChangeType = AppConstant.Anim.ANIM_CHANGE_TYPE;
	final String TYPE_OF_VIEW = "typeofview";
	final String YEARMONTH = "yearmonth";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.data_manage_view);
		rltDataManageLocal = (RelativeLayout) findViewById(R.id.rlt_setting_data_local);
		rltDataManageInternet = (RelativeLayout) findViewById(R.id.rlt_setting_data_internet);
		initRelativeLayoutButton();
		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
				ActionBar.DISPLAY_HOME_AS_UP);
		actionBar.setTitle(AppVariable.Common.YearMonth.substring(0, 4) + "年"
				+ AppVariable.Common.YearMonth.substring(4) + "月" + "CET"
				+ AppVariable.Common.CetX + "题库");
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
		getMenuInflater().inflate(R.menu.data_manage_view, menu);
		return true;
	}

	public void initRelativeLayoutButton() {
		rltDataManageLocal
				.setOnClickListener(new RelativeLayoutOnClickListener());
		rltDataManageInternet
				.setOnClickListener(new RelativeLayoutOnClickListener());
	}

	class RelativeLayoutOnClickListener implements
			RelativeLayout.OnClickListener {
		SharedPreferences MainTabPrefs = getSharedPreferences("view.MainTab",
				DataManageView.MODE_PRIVATE);
		int selectedIndex;
		final String[] array = new String[] { "听力题库", "完型题库", "阅读题库", "词汇题库" };

		@Override
		public void onClick(View v) {
			if (v == rltDataManageLocal) {
				AppVariable.Common.TypeOfView = MainTabPrefs.getInt(
						TYPE_OF_VIEW, 0);
				selectedIndex = AppVariable.Common.TypeOfView;
				Dialog alertDialog = new AlertDialog.Builder(
						DataManageView.this)
						.setTitle("请选择")
						.setSingleChoiceItems(array,
								AppVariable.Common.TypeOfView,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										selectedIndex = which;
									}
								})
						.setPositiveButton("确认",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										AppVariable.Common.TypeOfView = selectedIndex;
										/** 检查数据表是否存在，数据表中是否有数据，没有则下载数据，导入数据 */
										if (!DBCommon.checkDB(selectedIndex,
												getApplicationContext(),
												AppVariable.Common.YearMonth)) {
											Intent intent = new Intent();
											intent.putExtra("VIEW",
													selectedIndex);
											intent.setClass(
													getApplicationContext(),
													LocalDataManageView.class);
											startActivity(intent);
											// Toast.makeText(mContext, "跳转到下载",
											// Toast.LENGTH_LONG).show();
										} else
											Toast.makeText(DataManageView.this,
													"数据已下载", Toast.LENGTH_LONG)
													.show();
									}

								}).setNegativeButton("取消", null).create();
				alertDialog.show();

				// startActivity(new Intent(getApplicationContext(),
				// LocalDataManageView.class));
				// 第一个参数为启动时动画效果，第二个参数为退出时动画效果
				overridePendingTransition(
						ChangeType[AppVariable.Common.CHANGETYPE][0],
						ChangeType[AppVariable.Common.CHANGETYPE][1]);
			} else if (v == rltDataManageInternet) {
				AppVariable.Common.TypeOfView = MainTabPrefs.getInt(
						TYPE_OF_VIEW, 0);
				selectedIndex = AppVariable.Common.TypeOfView;
				Dialog alertDialog = new AlertDialog.Builder(
						DataManageView.this)
						.setTitle("请选择")
						.setSingleChoiceItems(array,
								AppVariable.Common.TypeOfView,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										selectedIndex = which;
									}
								})
						.setPositiveButton("确认",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										AppVariable.Common.TypeOfView = selectedIndex;
										/** 检查数据表是否存在，数据表中是否有数据，没有则下载数据，导入数据 */
										if (!DBCommon.checkDB(selectedIndex,
												DataManageView.this,
												AppVariable.Common.YearMonth)) {
											Intent intent = new Intent();
											intent.putExtra("VIEW",
													selectedIndex);
											intent.setClass(
													DataManageView.this,
													DownLoadView.class);
											startActivity(intent);
											// Toast.makeText(mContext, "跳转到下载",
											// Toast.LENGTH_LONG).show();
										} else
											Toast.makeText(DataManageView.this,
													"数据已下载", Toast.LENGTH_LONG)
													.show();
									}

								}).setNegativeButton("取消", null).create();
				alertDialog.show();
				overridePendingTransition(
						ChangeType[AppVariable.Common.CHANGETYPE][0],
						ChangeType[AppVariable.Common.CHANGETYPE][1]);
			}
		}
	}
}
