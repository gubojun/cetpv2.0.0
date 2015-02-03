package com.cetp.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cetp.R;

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
		rltIndexfenxiang
				.setOnClickListener(new RelativeLayoutOnClickListener());
		rltIndexshoucang
				.setOnClickListener(new RelativeLayoutOnClickListener());
		rltIndexmoni.setOnClickListener(new RelativeLayoutOnClickListener());
		rltIndexmeiri.setOnClickListener(new RelativeLayoutOnClickListener());
	}

	class RelativeLayoutOnClickListener implements
			RelativeLayout.OnClickListener {
		private int selectedIndex = 0;
		/** 用于保存用户状态 */
		final SharedPreferences myPrefs = activity.getPreferences(mContext.MODE_PRIVATE);
		int typeOfView = myPrefs.getInt(TYPE_OF_VIEW, 0);
		@Override
		public void onClick(View v) {
			if (v == rltIndexfenxiang) {
				final String[] array = new String[] { "听力练习", "完型练习", "阅读练习",
						"词汇练习" };
				Dialog alertDialog = new AlertDialog.Builder(mContext)
						.setTitle("请选择")
						.setSingleChoiceItems(array, typeOfView,
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
										Editor editor = myPrefs.edit();
										editor.putInt(TYPE_OF_VIEW, selectedIndex);
										// Write other values as desired
										editor.commit();

										// Toast.makeText(mContext,
										// array[selectedIndex],
										// Toast.LENGTH_LONG).show();
										mContext.startActivity(new Intent(
												mContext, CommonTab.class));
//										activity.setTitle(array[selectedIndex]);
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
}
