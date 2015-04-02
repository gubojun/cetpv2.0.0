package com.cetp.view;

import com.cetp.R;
import com.cetp.action.SkinSettingManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class FavoriteView {
	//
	private ImageView imgFavoriteListening;
	Context context;
	Activity activity;

	public FavoriteView(Context c) {
		context = c;
		activity = (Activity) c;
	}

	public void setView(View v) {
		findView(v);
		imgFavoriteListening.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.putExtra("VIEW", 0);
				intent.putExtra("KindOfView", 2);
				intent.setClass(context, CommonTab.class);
				context.startActivity(intent);
			}
		});
	}

	private void findView(View v) {
		imgFavoriteListening = (ImageView) v.findViewById(R.id.imageView1);
	}
}
