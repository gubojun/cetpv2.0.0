package com.cetp.view;

import com.cetp.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
/**
 * ��ӭ����
 * 
 * @author �˲���
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
		String str = "�û�����cetp\n���룺cetp";
		b.setTitle("ϵͳĬ���˻�").setMessage(str);
		b.setPositiveButton("ȷ��", null);
		b.create().show();
		
		
//		Uri uri = Uri.parse("http://www.guanghezhang.com/userRegister.php");
//		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//		startActivity(intent);
	}

}
