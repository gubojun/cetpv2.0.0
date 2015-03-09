package com.cetp.view;

import com.cetp.R;
import com.cetp.view.SettingView.RelativeLayoutOnClickListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

public class MoreView {
	Context mContext;
	Activity activity;
	// ∂®“Â∞¥≈•
	private RelativeLayout rltMoreWrongStat;

	public MoreView(Context c) {
		mContext = c;
		activity = (Activity) c;
	}

	public void setView(View v) {
		findView(v);
		initRelativeLayoutButton();
	}

	private void findView(View v) {
		rltMoreWrongStat = (RelativeLayout) v
				.findViewById(R.id.rlt_more_wrongstat);
	}

	private void initRelativeLayoutButton() {
		rltMoreWrongStat
				.setOnClickListener(new RelativeLayoutOnClickListener());
	}

	class RelativeLayoutOnClickListener implements
			RelativeLayout.OnClickListener {
		@Override
		public void onClick(View v) {
			if (v == rltMoreWrongStat) {
				mContext.startActivity(new Intent(mContext, WrongStat.class));
			}
		}
	}
}
