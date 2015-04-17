package com.cetp.view;

import com.cetp.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
/**
 * 欢迎界面
 * 
 * @author 顾博君
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
		//---------------------------------------------------------------------
		AlertDialog.Builder b = new AlertDialog.Builder(Welcome.this);
		String str = "用户名：cetp\n密码：cetp";
		b.setTitle("系统默认账户").setMessage(str);
		b.setPositiveButton("确定", null);
		b.create().show();
		
		
//		Uri uri = Uri.parse("http://www.guanghezhang.com/userRegister.php");
//		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//		startActivity(intent);
	}

}
