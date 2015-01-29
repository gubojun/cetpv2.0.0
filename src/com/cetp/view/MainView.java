package com.cetp.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cetp.R;

public class MainView {
	// 定义按钮
	private RelativeLayout rltIndexfenxiang;
	private RelativeLayout rltIndexshoucang;
	private RelativeLayout rltIndexmoni;
	private RelativeLayout rltIndexmeiri;
	Context mContext;
	Activity activity;

	public MainView(Context c) {
		mContext = c;
		activity = (Activity) c;
	}

	public void setView(View v) {
		rltIndexfenxiang=(RelativeLayout) v.findViewById(R.id.index_rlt_fenxiang);
		rltIndexshoucang=(RelativeLayout) v.findViewById(R.id.index_rlt_shoucang);
		rltIndexmoni=(RelativeLayout) v.findViewById(R.id.index_rlt_moni);
		rltIndexmeiri=(RelativeLayout) v.findViewById(R.id.index_rlt_meiri);
		rltIndexfenxiang
				.setOnClickListener(new RelativeLayoutOnClickListener());
		rltIndexshoucang
				.setOnClickListener(new RelativeLayoutOnClickListener());
		rltIndexmoni.setOnClickListener(new RelativeLayoutOnClickListener());
		rltIndexmeiri.setOnClickListener(new RelativeLayoutOnClickListener());
	}

	class RelativeLayoutOnClickListener implements
			RelativeLayout.OnClickListener {

		@Override
		public void onClick(View v) {
			if (v == rltIndexfenxiang) {
				Toast.makeText(mContext, "1123", Toast.LENGTH_LONG).show();
				newDialog("1");
			}
			else if (v == rltIndexshoucang) {
				mContext.startActivity(new Intent(mContext,
						HttpActivity.class));
			}
			else if (v == rltIndexmoni) {

			}
			else if (v == rltIndexmeiri) {

			}
		}
	}
	public void newDialog(String time) {
		LayoutInflater inflater = (LayoutInflater) mContext.getApplicationContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// view绑定自定义对话框
//		View view = inflater.inflate(R.layout.timesettingview_dialog, null);
		new AlertDialog.Builder(mContext).setTitle("请输入")
				.setIcon(android.R.drawable.ic_dialog_info)//.setView(view)
				.setPositiveButton("确定",null)
				.setNegativeButton("取消", null).show();
//		edtTimeSettingDialog = (EditText) view
//				.findViewById(R.id.edt_timesetting_dialog);
//		edtTimeSettingDialog.setText(time);
	}
}
