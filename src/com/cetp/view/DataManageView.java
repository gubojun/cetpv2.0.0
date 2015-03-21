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
	/** �����л�Ч�� */
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
		actionBar.setTitle(AppVariable.Common.YearMonth.substring(0, 4) + "��"
				+ AppVariable.Common.YearMonth.substring(4) + "��" + "CET"
				+ AppVariable.Common.CetX + "���");
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
		final String[] array = new String[] { "�������", "�������", "�Ķ����", "�ʻ����" };

		@Override
		public void onClick(View v) {
			if (v == rltDataManageLocal) {
				AppVariable.Common.TypeOfView = MainTabPrefs.getInt(
						TYPE_OF_VIEW, 0);
				selectedIndex = AppVariable.Common.TypeOfView;
				Dialog alertDialog = new AlertDialog.Builder(
						DataManageView.this)
						.setTitle("��ѡ��")
						.setSingleChoiceItems(array,
								AppVariable.Common.TypeOfView,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										selectedIndex = which;
									}
								})
						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										AppVariable.Common.TypeOfView = selectedIndex;
										/** ������ݱ��Ƿ���ڣ����ݱ����Ƿ������ݣ�û�����������ݣ��������� */
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
											// Toast.makeText(mContext, "��ת������",
											// Toast.LENGTH_LONG).show();
										} else
											Toast.makeText(DataManageView.this,
													"����������", Toast.LENGTH_LONG)
													.show();
									}

								}).setNegativeButton("ȡ��", null).create();
				alertDialog.show();

				// startActivity(new Intent(getApplicationContext(),
				// LocalDataManageView.class));
				// ��һ������Ϊ����ʱ����Ч�����ڶ�������Ϊ�˳�ʱ����Ч��
				overridePendingTransition(
						ChangeType[AppVariable.Common.CHANGETYPE][0],
						ChangeType[AppVariable.Common.CHANGETYPE][1]);
			} else if (v == rltDataManageInternet) {
				AppVariable.Common.TypeOfView = MainTabPrefs.getInt(
						TYPE_OF_VIEW, 0);
				selectedIndex = AppVariable.Common.TypeOfView;
				Dialog alertDialog = new AlertDialog.Builder(
						DataManageView.this)
						.setTitle("��ѡ��")
						.setSingleChoiceItems(array,
								AppVariable.Common.TypeOfView,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										selectedIndex = which;
									}
								})
						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										AppVariable.Common.TypeOfView = selectedIndex;
										/** ������ݱ��Ƿ���ڣ����ݱ����Ƿ������ݣ�û�����������ݣ��������� */
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
											// Toast.makeText(mContext, "��ת������",
											// Toast.LENGTH_LONG).show();
										} else
											Toast.makeText(DataManageView.this,
													"����������", Toast.LENGTH_LONG)
													.show();
									}

								}).setNegativeButton("ȡ��", null).create();
				alertDialog.show();
				overridePendingTransition(
						ChangeType[AppVariable.Common.CHANGETYPE][0],
						ChangeType[AppVariable.Common.CHANGETYPE][1]);
			}
		}
	}
}
