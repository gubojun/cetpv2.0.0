package com.cetp.view;

import com.cetp.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
/**
 * »¶Ó­½çÃæ
 * 
 * @author ¹Ë²©¾ý
 * @date 2013-7
 */
public class Welcome extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
	}

	public void welcome_login(View v) {
		Intent intent = new Intent();
		intent.setClass(Welcome.this, Login.class);
		startActivity(intent);
		this.finish();
	}

	public void welcome_register(View v) {
//		Intent intent = new Intent();
//		intent.setClass(Welcome.this, CETMain.class);
//		startActivity(intent);
//		this.finish();
		Uri uri = Uri.parse("http://www.guanghezhang.com/userRegister.php");
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}

}
