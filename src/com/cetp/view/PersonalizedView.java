/**
 * 个性化设置界面
 */
package com.cetp.view;

import com.cetp.R;
import com.cetp.action.AppVariable;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class PersonalizedView extends Activity {
	RadioGroup rdgPersonalizedChangeType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.personalizedview);
		
		rdgPersonalizedChangeType = (RadioGroup) findViewById(R.id.rdg_personalized_changetype);
		rdgPersonalizedChangeType
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {

						switch (checkedId) {
						case R.id.rdb_fadeinout:
							AppVariable.Common.CHANGETYPE = 0;
							break;
						case R.id.rdb_stretch:
							AppVariable.Common.CHANGETYPE = 1;
							break;
						case R.id.rdb_push_left:
							AppVariable.Common.CHANGETYPE = 2;
							break;
						case R.id.rdb_push_right:
							AppVariable.Common.CHANGETYPE = 3;
							break;
						case R.id.rdb_push_up:
							AppVariable.Common.CHANGETYPE = 3;
							break;
						default:
							break;
						}
					}
				});
	}
}
