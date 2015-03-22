package com.cetp.view;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import com.cetp.R;
import com.cetp.R.layout;
import com.cetp.R.menu;
import com.cetp.action.AppVariable;
import com.cetp.database.DBCommon;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class DataManageView extends FinalActivity {
	@ViewInject(id = R.id.rlt_setting_datamanage_local, click = "rltClick")
	RelativeLayout rltSettingDMLocal;
	@ViewInject(id = R.id.rlt_setting_datamanage_internet, click = "rltClick")
	RelativeLayout rltSettingDMInternet;
	int selectedIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_manage_view);

		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
				ActionBar.DISPLAY_HOME_AS_UP);
	}

	public void rltClick(View v) {
		final String[] array = new String[] { "听力题库", "完型题库", "阅读题库", "词汇题库" };
		selectedIndex = AppVariable.Common.TypeOfView;
		if (v == rltSettingDMLocal) {
			Dialog alertDialog = new AlertDialog.Builder(this)
					.setTitle("请选择")
					.setSingleChoiceItems(array, selectedIndex,
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
									if (DBCommon.checkDB(selectedIndex,
											getApplicationContext(),
											AppVariable.Common.YearMonth)) {
										Toast.makeText(getApplicationContext(),
												"题库已完全导入", Toast.LENGTH_LONG)
												.show();
									}
									Intent intent = new Intent();
									intent.putExtra("VIEW", selectedIndex);
									intent.setClass(getApplicationContext(),
											LocalDataManageView.class);
									startActivity(intent);
								}

							}).setNegativeButton("取消", null).create();
			alertDialog.show();
		} else if (v == rltSettingDMInternet) {
			Dialog alertDialog = new AlertDialog.Builder(this)
					.setTitle("请选择")
					.setSingleChoiceItems(array, selectedIndex,
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
									if (DBCommon.checkDB(selectedIndex,
											getApplicationContext(),
											AppVariable.Common.YearMonth)) {
										Toast.makeText(getApplicationContext(),
												"题库已完全导入", Toast.LENGTH_LONG)
												.show();
									}
									Intent intent = new Intent();
									intent.putExtra("VIEW", selectedIndex);
									intent.setClass(getApplicationContext(),
											DownLoadView.class);
									startActivity(intent);
								}

							}).setNegativeButton("取消", null).create();
			alertDialog.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.data_manage_view, menu);
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
