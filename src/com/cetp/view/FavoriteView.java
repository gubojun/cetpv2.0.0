package com.cetp.view;

import com.cetp.R;
import com.cetp.action.SkinSettingManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class FavoriteView {
	//

	private RelativeLayout rltListening;
	private RelativeLayout rltClozing;
	private RelativeLayout rltReading;
	Context context;
	Activity activity;

	public FavoriteView(Context c) {
		context = c;
		activity = (Activity) c;
	}

	public void setView(View v) {
		findView(v);
		rltListening.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.putExtra("VIEW", 0);
				intent.putExtra("KindOfView", 2);
				intent.setClass(context, CommonTab.class);
				context.startActivity(intent);
			}
		});
		rltClozing.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.putExtra("VIEW", 1);
				intent.putExtra("KindOfView", 2);
				intent.setClass(context, CommonTab.class);
				context.startActivity(intent);
			}
		});
		rltReading.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.putExtra("VIEW", 2);
				intent.putExtra("KindOfView", 2);
				intent.setClass(context, CommonTab.class);
				context.startActivity(intent);
			}
		});
	}

	private void findView(View v) {
		rltListening=(RelativeLayout)v.findViewById(R.id.fav_rlt_listening);
		rltClozing=(RelativeLayout)v.findViewById(R.id.fav_rlt_clozing);
		rltReading=(RelativeLayout)v.findViewById(R.id.fav_rlt_reading);
	}
}
