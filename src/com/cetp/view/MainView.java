package com.cetp.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.database.Cursor;

import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cetp.R;
import com.cetp.action.AppVariable;
import com.cetp.database.DBCommon;
import com.cetp.database.DBListeningOfQuestion;
import com.cetp.service.PlayerService;

public class MainView {
	// 定义按钮
	private RelativeLayout rltIndexfenxiang;
	private RelativeLayout rltIndexshoucang;
	private RelativeLayout rltIndexmoni;
	private RelativeLayout rltIndexmeiri;
	private RadioGroup rgIndexfenxiang;
	private RadioButton rb0, rb1, rb2, rb3;
	private int Type;
	Context mContext;
	Activity activity;
	View viewDialog;
	final String TYPE_OF_VIEW = "typeofview";
	final String YEARMONTH = "yearmonth";
	SharedPreferences AppstartPrefs;

	public MainView(Context c) {
		mContext = c;
		activity = (Activity) c;
	}

	public void setView(View v) {
		rltIndexfenxiang = (RelativeLayout) v
				.findViewById(R.id.index_rlt_fenxiang);
		rltIndexshoucang = (RelativeLayout) v
				.findViewById(R.id.index_rlt_shoucang);
		rltIndexmoni = (RelativeLayout) v.findViewById(R.id.index_rlt_moni);
		rltIndexmeiri = (RelativeLayout) v.findViewById(R.id.index_rlt_meiri);
		/** 加载存储文件中的数据 */
		AppstartPrefs = mContext.getSharedPreferences("view.MainTab",
				mContext.MODE_PRIVATE);
		AppVariable.Common.YearMonth = AppstartPrefs.getString(YEARMONTH,
				"200606");
		// ////////////////////////////////////////////////
		System.out.println(AppVariable.Common.YearMonth);
		// ////////////////////////////////////////////////
		Spinner spnYM = (Spinner) v.findViewById(R.id.spn_ym);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				mContext, R.array.yearmonth,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnYM.setAdapter(adapter);
		int pos = adapter.getPosition(AppVariable.Common.YearMonth.substring(0,
				4) + "." + AppVariable.Common.YearMonth.substring(4));
		// /////////////////////////////////
		System.out.println("pos=" + pos);
		// /////////////////////////////////
		
		spnYM.setSelection(pos, true);
		spnYM.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				/** 得到资源文件中的年月 */
				Resources res = mContext.getResources();
				String[] YM = res.getStringArray(R.array.yearmonth);
				AppVariable.Common.YearMonth = YM[position].substring(0, 4)
						+ YM[position].substring(5);
				/** 存储数据写回 */
				Editor editor = AppstartPrefs.edit();
				editor.putString(YEARMONTH, AppVariable.Common.YearMonth);
				// Write other values as desired
				editor.commit();

				// showToast(AppVariable.Common.YearMonth);
				// showToast("Spinner2: position=" + position + " id=" + id);
			}

			public void onNothingSelected(AdapterView<?> parent) {
				showToast("Spinner2: unselected");
			}
		});

		rltIndexfenxiang
				.setOnClickListener(new RelativeLayoutOnClickListener());
		rltIndexshoucang
				.setOnClickListener(new RelativeLayoutOnClickListener());
		rltIndexmoni.setOnClickListener(new RelativeLayoutOnClickListener());
		rltIndexmeiri.setOnClickListener(new RelativeLayoutOnClickListener());
	}

	void showToast(CharSequence msg) {
		Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
	}

	class RelativeLayoutOnClickListener implements
			RelativeLayout.OnClickListener {
		private int selectedIndex = 0;
		/** 用于保存用户状态 */
		final SharedPreferences myPrefs = activity
				.getPreferences(mContext.MODE_PRIVATE);

		@Override
		public void onClick(View v) {
			if (v == rltIndexfenxiang) {
				final String[] array = new String[] { "听力练习", "完型练习", "阅读练习",
						"词汇练习" };
				AppVariable.Common.TypeOfView = myPrefs.getInt(TYPE_OF_VIEW, 0);
				Dialog alertDialog = new AlertDialog.Builder(mContext)
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
										/** 将选择的项目保存 *************************************/
										Editor editor = myPrefs.edit();
										editor.putInt(TYPE_OF_VIEW,
												selectedIndex);
										// Write other values as desired
										editor.commit();
										AppVariable.Common.TypeOfView = selectedIndex;
										// Toast.makeText(mContext,
										// array[selectedIndex],
										// Toast.LENGTH_LONG).show();
										/** 检查数据表是否存在，数据表中是否有数据，没有则下载数据，导入数据 */
										if (!DBCommon.checkDB(selectedIndex,
												mContext,
												AppVariable.Common.YearMonth)) {
											Intent intent = new Intent();
											intent.putExtra("VIEW",
													selectedIndex);
											intent.setClass(mContext,
													DownLoadView.class);
											mContext.startActivity(intent);
											// Toast.makeText(mContext, "跳转到下载",
											// Toast.LENGTH_LONG).show();
										} else
											mContext.startActivity(new Intent(
													mContext, CommonTab.class));
										// activity.setTitle(array[selectedIndex]);
									}

								}).setNegativeButton("取消", null).create();
				alertDialog.show();
			} else if (v == rltIndexshoucang) {
				// mContext.startActivity(new Intent(mContext,
				// this));
			} else if (v == rltIndexmoni) {

			} else if (v == rltIndexmeiri) {

			}
		}
	}

	// private boolean checkDB(int index) {
	// boolean result = false;
	// if (index == 0)
	// result = checkListeningDB();
	// else
	//
	// return result;
	// }
	//
	// private boolean checkListeningDB() {
	// boolean result = false;
	// DBListeningOfQuestion db = new DBListeningOfQuestion(mContext);
	// // else;
	// db.open();
	// if (db.checkTableExists(db.getDatabaseName())) {
	// System.out.println("checkDB:Table(" + db.getDatabaseName()
	// + ") exist");
	// Cursor cur;// 结果集
	// cur = db.getAllItem();
	// if (cur.getCount() == 0) {
	// Log.v("MainView", "no data in " + db.getDatabaseName());
	// result = false;
	// } else
	// result = true;
	// cur.close();
	// }
	// db.close();
	// return result;
	// }
}
