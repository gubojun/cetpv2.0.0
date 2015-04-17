package com.cetp.view;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cetp.R;

public class AboutView extends Activity {
	String TAG = "AboutView";
	private ImageView backButton;
	private RelativeLayout rltAboutWelcomePage;
	private RelativeLayout rltAboutFunctionIntroduce;
	private RelativeLayout rltAboutComments;
	private RelativeLayout rltAboutHelp;
	private RelativeLayout rltAboutNewVersion;

	private TextView txtAppName;

	private EditText edtCommentIn;
	/** 检测新版本对话框 */
	ProgressDialog progressDialog = null;
	// ------布局方式--------
	@SuppressWarnings("unused")
	private final LinearLayout.LayoutParams LP_FF = new LinearLayout.LayoutParams(
			LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	@SuppressWarnings("unused")
	private final LinearLayout.LayoutParams LP_FW = new LinearLayout.LayoutParams(
			LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	@SuppressWarnings("unused")
	private final LinearLayout.LayoutParams LP_WW = new LinearLayout.LayoutParams(
			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "Activity State: onCreate()");
		super.onCreate(savedInstanceState);
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.aboutview);
		// 初始化皮肤
		// SkinSettingManager mSettingManager = new SkinSettingManager(this);
		// mSettingManager.initSkins();
		backButton = (ImageView) findViewById(R.id.backtag);
		backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d(TAG, "backButton clicked");
				finish();
			}
		});

		txtAppName = (TextView) findViewById(R.id.txt_app_name);
		txtAppName.setText(getResources().getString(R.string.app_name)
				+ "(手机版)\nV2.0");

		initRelativeLayoutButton();

		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
				ActionBar.DISPLAY_HOME_AS_UP);
		actionBar.setTitle("关于");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			// Intent intent = new Intent(Login.this, Welcome.class);
			// startActivity(intent);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void initRelativeLayoutButton() {
		// 绑定控件
		rltAboutWelcomePage = (RelativeLayout) findViewById(R.id.rlt_about_welcomepage);
		rltAboutFunctionIntroduce = (RelativeLayout) findViewById(R.id.rlt_about_functionintroduce);
		rltAboutComments = (RelativeLayout) findViewById(R.id.rlt_about_comments);
		rltAboutHelp = (RelativeLayout) findViewById(R.id.rlt_about_help);
		rltAboutNewVersion = (RelativeLayout) findViewById(R.id.rlt_about_newversion);
		// 加入侦听
		rltAboutWelcomePage.setOnClickListener(new RelativeOnClickListener());
		rltAboutFunctionIntroduce
				.setOnClickListener(new RelativeOnClickListener());
		rltAboutComments.setOnClickListener(new RelativeOnClickListener());
		rltAboutHelp.setOnClickListener(new RelativeOnClickListener());
		rltAboutNewVersion.setOnClickListener(new RelativeOnClickListener());
	}

	class RelativeOnClickListener implements RelativeLayout.OnClickListener {

		@Override
		public void onClick(View v) {
			if (v == rltAboutWelcomePage) {
				startActivity(new Intent(AboutView.this, Viewpager.class));
			} else if (v == rltAboutFunctionIntroduce) {
				startActivity(new Intent(AboutView.this, Whatsnew.class));
			} else if (v == rltAboutComments) {
				showComments();// 显示意见反馈界面
			} else if (v == rltAboutHelp) {
				showHelp();// 显示帮助
			} else if (v == rltAboutNewVersion) {
				showNewVersion();// 检测新版本
			}
		}
	}

	public void showFunctionIntroduce() {
		AlertDialog.Builder b = new AlertDialog.Builder(AboutView.this);
		InputStream ips = getResources().openRawResource(
				R.raw.functionintroduce);
		// BufferedReader br = new BufferedReader(new
		// InputStreamReader(ips));
		DataInputStream dis = new DataInputStream(ips);
		try {
			byte[] bytes = new byte[dis.available()];
			String str = "";
			while (ips.read(bytes) != -1)
				str = str + new String(bytes, "GBK");
			// StringBuffer str = new StringBuffer();
			// while(br.ready())
			// str.append(br.readLine());
			b.setTitle("功能介绍").setMessage(str);
			b.setPositiveButton("确定", null);
			b.create().show();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				dis.close();
				ips.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void showComments() {
		LayoutInflater inflater = (LayoutInflater) getApplicationContext()
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		// view绑定自定义对话框
		View view = inflater.inflate(R.layout.commentview, null);
		new AlertDialog.Builder(this).setTitle("请输入")
				.setIcon(android.R.drawable.ic_dialog_info).setView(view)
				.setPositiveButton("发送", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).setNegativeButton("取消", null).show();
		edtCommentIn = (EditText) view
				.findViewById(R.id.edt_timesetting_dialog);
		// edtCommentIn.setText("");
	}

	class myPositiveButtonListener implements DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			String stringTime;
			stringTime = String.valueOf(edtCommentIn.getText());
			dialog.dismiss();
		}
	}

	public void showHelp() {
		AlertDialog.Builder b = new AlertDialog.Builder(AboutView.this);
		InputStream ips = getResources().openRawResource(R.raw.readme);
		// BufferedReader br = new BufferedReader(new
		// InputStreamReader(ips));
		DataInputStream dis = new DataInputStream(ips);
		try {
			byte[] bytes = new byte[dis.available()];
			String str = "";
			while (ips.read(bytes) != -1)
				str = str + new String(bytes, "GBK");
			// StringBuffer str = new StringBuffer();
			// while(br.ready())
			// str.append(br.readLine());
			b.setTitle("帮助").setMessage(str);
			b.setPositiveButton("确定", null);
			b.create().show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				dis.close();
				ips.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void showNewVersion() {
		// AlertDialog.Builder b = new AlertDialog.Builder(AboutView.this);
		// b.setTitle("检测新版本").setMessage("版本检测中...");
		// b.setPositiveButton("确定", null);
		// b.setNegativeButton("取消", null);
		// b.create().show();
		/*********************************/
		// Intent intent = new Intent();
		// intent.setClass(this, UpdateActivity.class);
		// startActivity(intent);
		/**********************************/
		progressDialog = ProgressDialog.show(AboutView.this, "检测新版本",
				"检测中.....", true);
		progressDialog.setCancelable(true);// 当点击按钮返回的时候Dialog消失

		Toast.makeText(AboutView.this, "已是最新版本！", Toast.LENGTH_LONG).show();
	}
}
