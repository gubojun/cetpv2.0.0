package com.cetp.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.cetp.R;
import com.cetp.action.AppVariable;

public class Login extends Activity {

	private static final String TAG = "login";
	final String DEFAULTUSER = "defaultUser";
	final String CANLOGIN = "canLogin";
	private EditText mUser;
	private EditText mPassword;
	private String mUserName;
	private String mUserPsd;
	private CheckBox mWritepass;
	private boolean writepass = false;
	ActionBar actionBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		final SharedPreferences myPrefs = getPreferences(MODE_PRIVATE);

		String defaultUser = myPrefs.getString(DEFAULTUSER, "");

		mUser = (EditText) findViewById(R.id.login_user_edit);
		mPassword = (EditText) findViewById(R.id.login_passwd_edit);
		mWritepass = (CheckBox) findViewById(R.id.chk_writepass);
		mWritepass.setOnCheckedChangeListener(new CheckBoxListener());
		mUser.setText(defaultUser);

		actionBar = this.getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
				ActionBar.DISPLAY_HOME_AS_UP);
		actionBar.setTitle("登录");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			Intent intent = new Intent(Login.this, Welcome.class);
			startActivity(intent);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	class CheckBoxListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (isChecked == true) {
				writepass = true;
			} else {
				writepass = false;
			}
		}
	}

	public void login_CETMain(View v) throws ParseException, IOException,
			JSONException {
		System.out.println("login_CETMain");
		final SharedPreferences myPrefs = getPreferences(MODE_PRIVATE);
		SharedPreferences AppstartPrefs = getSharedPreferences("view.Appstart",
				MODE_PRIVATE);

		mUserName = mUser.getText().toString();
		mUserPsd = mPassword.getText().toString();
		String connectURL = "http://www.guanghezhang.com/loginFromAndroid.php";
		boolean isLoginSucceed = gotoLogin(mUserName, mUserPsd, connectURL);
		if (isLoginSucceed) {
			System.out.println("login succeed");

			Editor Appstart_editor = AppstartPrefs.edit();
			Appstart_editor.putBoolean(CANLOGIN, writepass);
			Appstart_editor.commit();
			// /** 记录最近成功登录的用户 */
			// // --------------------------------------------------------
			// Log.v(TAG, "defaultUser = " + mUserName);
			// Later when ready to write out values
			Editor editor = myPrefs.edit();
			editor.putString(DEFAULTUSER, mUserName);
			editor.commit();
			AppVariable.User.G_USER_NAME = mUserName;

			Intent intent = new Intent();
			// intent.setClass(Login.this, LoadingActivity.class);
			intent.setClass(Login.this, MainTab.class);
			startActivity(intent);
			this.finish();
		} else {
			System.out.println("login fail");
		}
	}

	private boolean gotoLogin(String userName, String password,
			String connectURL) throws ParseException, IOException,
			JSONException {
		boolean isLoginSucceed = false;
		String str = new String();
		int code = 0;

		HttpPost httpRequest = new HttpPost(connectURL);
		List params = new ArrayList();
		params.add(new BasicNameValuePair("name", userName));
		params.add(new BasicNameValuePair("pwd", password));

		httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		// HttpResponse httpResponse = new
		// DefaultHttpClient().execute(httpRequest);
		if (userName.equals("")) {
			code = 4;
		} else if (password.equals("")) {
			code = 5;
		} else
			code = 1;
		/*
		 * else if (httpResponse.getStatusLine().getStatusCode() ==
		 * HttpStatus.SC_OK) { BufferedReader buffer=new BufferedReader(new
		 * InputStreamReader(httpResponse.getEntity().getContent())); for(String
		 * s = buffer.readLine(); s.length()>0 ;) { if(s.charAt(0) == '{') {
		 * str=s; break; } s = buffer.readLine(); }
		 * 
		 * JSONObject json = new JSONObject(str.toString()); code =
		 * json.getInt("code"); Log.v("result","successful"); } else{
		 * System.out.println("false to send data"); Toast.makeText(this,
		 * "登录失败！", Toast.LENGTH_LONG).show(); }
		 */

		if (code == 1) {
			System.out.println("login successful");
			isLoginSucceed = true;
		} else if (code == 0) {
			System.out.println("password error");
			Toast.makeText(this, "密码错误！", Toast.LENGTH_LONG).show();
		} else if (code == 3) {
			System.out.println("user is not exist");
			Toast.makeText(this, "用户不存在！", Toast.LENGTH_LONG).show();
		} else if (code == 4) {
			Toast.makeText(this, "请输入用户名！", Toast.LENGTH_LONG).show();
		} else if (code == 5) {
			Toast.makeText(this, "请输入密码！", Toast.LENGTH_LONG).show();
		}
		return isLoginSucceed;
	}

	public void login_back(View v) { // 标题栏 返回按钮
		Intent intent = new Intent();
		intent.setClass(Login.this, Welcome.class);
		startActivity(intent);
		this.finish();
	}

	public void login_pw(View v) { // 忘记密码按钮
		Uri uri = Uri.parse("http://3g.qq.com");
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
		// Intent intent = new Intent();
		// intent.setClass(Login.this,Whatsnew.class);
		// startActivity(intent);
	}

}
