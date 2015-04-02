package com.cetp.view;

import java.util.List;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cetp.R;
import com.cetp.action.AppVariable;
import com.cetp.database.DBCommon;
import com.cetp.database.TableListeningOfQuestion;

public class MainView {
	private static final String TAG = "MainView";
	final String DEFAULTUSER = "defaultUser";
	// ���尴ť
	@ViewInject(id = R.id.index_rlt_fenxiang)
	private RelativeLayout rltIndexfenxiang;
	@ViewInject(id = R.id.index_rlt_wrong)
	private RelativeLayout rltIndexWrong;
	private RelativeLayout rltIndexmoni;
	private RelativeLayout rltIndexmeiri;
	// private ImageView img1, img2, img3, img4;

	private LinearLayout linIndexTop;

	private RadioGroup rgIndexfenxiang;
	private RadioButton rb0, rb1, rb2, rb3;
	private TextView txtUserName;
	private Spinner spnYM;
	private int Type;
	Context mContext;
	Activity activity;
	View viewDialog;
	final String TYPE_OF_VIEW = "typeofview";
	final String YEARMONTH = "yearmonth";
	SharedPreferences AppstartPrefs;

	public MainView(Context c) {
		mContext = c;
		activity = (Activity) c;
	}

	public void setView(View v) {
		findView(v);
		setListener();
		init();
	}

	private void findView(View v) {
		rltIndexfenxiang = (RelativeLayout) v
				.findViewById(R.id.index_rlt_fenxiang);
		rltIndexWrong = (RelativeLayout) v.findViewById(R.id.index_rlt_wrong);
		rltIndexmoni = (RelativeLayout) v.findViewById(R.id.index_rlt_moni);
		rltIndexmeiri = (RelativeLayout) v.findViewById(R.id.index_rlt_meiri);

		linIndexTop = (LinearLayout) v.findViewById(R.id.index_lin_top);

		txtUserName = (TextView) v.findViewById(R.id.txt_username);

		spnYM = (Spinner) v.findViewById(R.id.spn_ym);
	}

	private void setListener() {
		/** ���ش洢�ļ��е����� */
		AppstartPrefs = mContext.getSharedPreferences("view.MainTab",
				mContext.MODE_PRIVATE);
		AppVariable.Common.YearMonth = AppstartPrefs.getString(YEARMONTH,
				"200606");

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				mContext, R.array.yearmonth,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnYM.setAdapter(adapter);
		int pos = adapter.getPosition(AppVariable.Common.YearMonth.substring(0,
				4) + "." + AppVariable.Common.YearMonth.substring(4));
		// /////////////////////////////////
		System.out.println("pos=" + pos);
		// /////////////////////////////////

		spnYM.setSelection(pos, true);
		spnYM.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				/** �õ���Դ�ļ��е����� */
				Resources res = mContext.getResources();
				String[] YM = res.getStringArray(R.array.yearmonth);
				AppVariable.Common.YearMonth = YM[position].substring(0, 4)
						+ YM[position].substring(5);
				/** �洢����д�� */
				Editor editor = AppstartPrefs.edit();
				editor.putString(YEARMONTH, AppVariable.Common.YearMonth);
				// Write other values as desired
				editor.commit();

				// showToast(AppVariable.Common.YearMonth);
				// showToast("Spinner2: position=" + position + " id=" + id);
			}

			public void onNothingSelected(AdapterView<?> parent) {
				showToast("Spinner2: unselected");
			}
		});

		rltIndexfenxiang.setOnClickListener(new ViewOnClickListener());
		rltIndexWrong.setOnClickListener(new ViewOnClickListener());
		rltIndexmoni.setOnClickListener(new ViewOnClickListener());
		rltIndexmeiri.setOnClickListener(new ViewOnClickListener());
	}

	private void init() {
		/** ��ʾ�û��� ***/
		AppstartPrefs = mContext.getSharedPreferences("view.Login",
				mContext.MODE_PRIVATE);
		AppVariable.User.G_USER_NAME = AppstartPrefs.getString(DEFAULTUSER,
				null);
		txtUserName.setText(AppVariable.User.G_USER_NAME);
		if (AppVariable.Common.SCREEN_HEIGHT > 900) {
			// ------���ַ�ʽ--------
			LinearLayout.LayoutParams LP_FW = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					AppVariable.Common.SCREEN_HEIGHT / 4);

			LinearLayout.LayoutParams rlt_LP_FW = (LinearLayout.LayoutParams) rltIndexfenxiang
					.getLayoutParams();
			rlt_LP_FW.height = AppVariable.Common.SCREEN_HEIGHT / 4;

			linIndexTop.setLayoutParams(LP_FW);
			// �������ø߶�
			rltIndexfenxiang.setLayoutParams(rlt_LP_FW);
			rltIndexmeiri.setLayoutParams(rlt_LP_FW);
			rltIndexmoni.setLayoutParams(rlt_LP_FW);
			rltIndexWrong.setLayoutParams(rlt_LP_FW);

			Log.d(TAG, "height=" + AppVariable.Common.SCREEN_HEIGHT);
		}
	}

	void showToast(CharSequence msg) {
		Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
	}

	class ViewOnClickListener implements View.OnClickListener {
		private int selectedIndex = 0;
		/** ���ڱ����û�״̬ */
		final SharedPreferences myPrefs = activity
				.getPreferences(mContext.MODE_PRIVATE);

		@Override
		public void onClick(View v) {
			if (v == rltIndexfenxiang) {
				final String[] array = new String[] { "������ϰ", "������ϰ", "�Ķ���ϰ",
						"�ʻ���ϰ" };
				AppVariable.Common.TypeOfView = selectedIndex = myPrefs.getInt(
						TYPE_OF_VIEW, 0);
				Dialog alertDialog = new AlertDialog.Builder(mContext)
						.setTitle("��ѡ��")
						.setSingleChoiceItems(array,
								AppVariable.Common.TypeOfView,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										selectedIndex = which;
									}
								})
						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										/** ��ѡ�����Ŀ���� *************************************/
										Editor editor = myPrefs.edit();
										editor.putInt(TYPE_OF_VIEW,
												selectedIndex);
										// Write other values as desired
										editor.commit();
										AppVariable.Common.TypeOfView = selectedIndex;
										// Toast.makeText(mContext,
										// array[selectedIndex],
										// Toast.LENGTH_LONG).show();
										/** ������ݱ��Ƿ���ڣ����ݱ����Ƿ������ݣ�û�����������ݣ��������� */
										if (!DBCommon.checkDB(selectedIndex,
												mContext,
												AppVariable.Common.YearMonth)) {
											Intent intent = new Intent();
											intent.putExtra("VIEW",
													selectedIndex);
											intent.setClass(mContext,
													DownLoadView.class);
											mContext.startActivity(intent);
											// Toast.makeText(mContext, "��ת������",
											// Toast.LENGTH_LONG).show();
										} else {
											Intent intent = new Intent();
											// intent.putExtra("VIEW",
											// selectedIndex);
											// intent.putExtra("isWrongView",
											// false);
											intent.setClass(mContext,
													CommonTab.class);
											mContext.startActivity(intent);
										}
										// activity.setTitle(array[selectedIndex]);
									}

								}).setNegativeButton("ȡ��", null).create();
				alertDialog.show();
			} else if (v == rltIndexWrong) {
				final String[] array = new String[] { "��������", "���ʹ���", "�Ķ�����",
						"�ʻ����" };
				AppVariable.Common.TypeOfView = selectedIndex = myPrefs.getInt(
						TYPE_OF_VIEW, 0);
				Dialog alertDialog = new AlertDialog.Builder(mContext)
						.setTitle("��ѡ��")
						.setSingleChoiceItems(array,
								AppVariable.Common.TypeOfView,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										selectedIndex = which;
									}
								})
						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										/** ��ѡ�����Ŀ���� *************************************/
										Editor editor = myPrefs.edit();
										editor.putInt(TYPE_OF_VIEW,
												selectedIndex);
										// Write other values as desired
										editor.commit();
										AppVariable.Common.TypeOfView = selectedIndex;

										FinalDb fdb = FinalDb.create(mContext,
												"cetp");

										switch (AppVariable.Common.TypeOfView) {
										case 0:
											List<TableListeningOfQuestion> lisOfQList = fdb
													.findAll(TableListeningOfQuestion.class);
											if (lisOfQList != null) {
												Intent intent = new Intent();
												intent.putExtra("VIEW",
														selectedIndex);
												intent.putExtra("KindOfView", 1);// ������ϰ������1
												intent.setClass(mContext,
														CommonTab.class);
												mContext.startActivity(intent);
											}
											break;
										case 1:
											break;
										case 2:
											break;
										case 3:
											break;
										}
									}

								}).setNegativeButton("ȡ��", null).create();
				alertDialog.show();

				// Toast.makeText(mContext, "2", Toast.LENGTH_LONG).show();
			} else if (v == rltIndexmoni) {

				if (!DBCommon
						.checkDB(0, mContext, AppVariable.Common.YearMonth)) {
					Intent intent = new Intent();
					AppVariable.Common.TypeOfView = 0;
					intent.setClass(mContext, DownLoadView.class);
					mContext.startActivity(intent);
					// Toast.makeText(mContext, "��ת������",
					// Toast.LENGTH_LONG).show();
				} else if (!DBCommon.checkDB(1, mContext,
						AppVariable.Common.YearMonth)) {
					Intent intent = new Intent();
					AppVariable.Common.TypeOfView = 1;
					intent.setClass(mContext, DownLoadView.class);
					mContext.startActivity(intent);
				} else if (!DBCommon.checkDB(2, mContext,
						AppVariable.Common.YearMonth)) {
					Intent intent = new Intent();
					AppVariable.Common.TypeOfView = 2;
					intent.setClass(mContext, DownLoadView.class);
					mContext.startActivity(intent);
				} else if (!DBCommon.checkDB(3, mContext,
						AppVariable.Common.YearMonth)) {
					Intent intent = new Intent();
					AppVariable.Common.TypeOfView = 3;
					intent.setClass(mContext, DownLoadView.class);
					mContext.startActivity(intent);
				} else {
					AppVariable.Common.isSimulation = true;
					// ģ����Կ�ʼ��ʱ�����������棬���԰�TypeOfView����Ϊ0
					AppVariable.Common.TypeOfView = 0;
					// ת��ģ�����ͨ��Tab����
					Intent intent = new Intent();
					intent.setClass(mContext, CommonTabSimulation.class);
					mContext.startActivity(intent);
				}
				// Toast.makeText(mContext, "3", Toast.LENGTH_LONG).show();
			} else if (v == rltIndexmeiri) {
				Intent intent = new Intent();
				intent.setClass(mContext, EverydayView.class);
				mContext.startActivity(intent);
			}
		}
	}

	// private boolean checkDB(int index) {
	// boolean result = false;
	// if (index == 0)
	// result = checkListeningDB();
	// else
	//
	// return result;
	// }
	//
	// private boolean checkListeningDB() {
	// boolean result = false;
	// DBListeningOfQuestion db = new DBListeningOfQuestion(mContext);
	// // else;
	// db.open();
	// if (db.checkTableExists(db.getDatabaseName())) {
	// System.out.println("checkDB:Table(" + db.getDatabaseName()
	// + ") exist");
	// Cursor cur;// �����
	// cur = db.getAllItem();
	// if (cur.getCount() == 0) {
	// Log.v("MainView", "no data in " + db.getDatabaseName());
	// result = false;
	// } else
	// result = true;
	// cur.close();
	// }
	// db.close();
	// return result;
	// }
}
