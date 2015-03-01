package com.cetp.view;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.cetp.R;
import com.cetp.action.AppVariable;
import com.cetp.excel.MyFile;
import com.cetp.service.PlayerService;

import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.MonthDisplayHelper;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainTab extends Activity {
	public static MainTab instance = null;
	private ViewPager mTabPager;
	private ImageView mTabImg;// ����ͼƬ
	private ImageView mTab1, mTab2, mTab3, mTab4;
	private TextView txtDate;
	private TextView txtSetting1, txtSetting2;
	private int zero = 0;// ����ͼƬƫ����
	private int currIndex = 0;// ��ǰҳ�����
	private int one;// ����ˮƽ����λ��
	private int two;
	private int three;
	private LinearLayout mClose;
	private LinearLayout mCloseBtn;
	private View layout;
	private boolean menu_display = false;
	private PopupWindow menuWindow;
	private LayoutInflater inflater;
	final String TYPE_OF_VIEW = "typeofview";
	final String YEARMONTH = "yearmonth";
	/** ���ڱ����û�״̬ */
	// final SharedPreferences myPrefs = getPreferences(MODE_PRIVATE);
	// final int typeOfView = myPrefs.getInt(TYPE_OF_VIEW, 0);

	MainView mainview = new MainView(this);
	// ListeningViewAnswer1 listeninganswer = new ListeningViewAnswer1(this);
	// ReadingViewAnswer1 readingviewanswer = new ReadingViewAnswer1(this);
	// ClozingViewAnswer1 clozingviewanswer = new ClozingViewAnswer1(this);
	View viewAnswer;
	// date and time
	private int mYear;
	private int mMonth;
	private int mDay;
	private int mHour;
	private int mMinute;

	static final int TIME_DIALOG_ID = 0;
	static final int DATE_DIALOG_ID = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ȥ��������
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tab_main);

		// ����activityʱ���Զ����������
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		instance = this;

		MyFile file = new MyFile();
		file.createPath();

		Log.w("OnCreate", Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/cetpdata");

		mTabPager = (ViewPager) findViewById(R.id.tabpager);
		mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());

		mTab1 = (ImageView) findViewById(R.id.img_question);
		mTab2 = (ImageView) findViewById(R.id.img_passage);
		mTab3 = (ImageView) findViewById(R.id.img_settings);
		mTab4 = (ImageView) findViewById(R.id.img_answer);
		mTabImg = (ImageView) findViewById(R.id.img_tab_now);

		mTab1.setOnClickListener(new MyOnClickListener(0));
		mTab2.setOnClickListener(new MyOnClickListener(1));
		mTab3.setOnClickListener(new MyOnClickListener(2));
		mTab4.setOnClickListener(new MyOnClickListener(3));
		Display currDisplay = getWindowManager().getDefaultDisplay();// ��ȡ��Ļ��ǰ�ֱ���
		int displayWidth = currDisplay.getWidth();
		int displayHeight = currDisplay.getHeight();
		one = displayWidth / 4; // ����ˮƽ����ƽ�ƴ�С
		two = one * 2;
		three = one * 3;
		Log.i("info", "��ȡ����Ļ�ֱ���Ϊ" + one + two + three + "X" + displayHeight);

		/* �����ؼ���width��heightֵ */
		int width = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int height = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		mTabImg.measure(width, height);
		int w = mTabImg.getMeasuredWidth();
		int sw = (one - w) / 2;
		mTabImg.setX(sw > 0 ? sw : 0);
		// set();
		// InitImageView();//ʹ�ö���
		// ��Ҫ��ҳ��ʾ��Viewװ��������
		LayoutInflater mLi = LayoutInflater.from(this);
		View view1, view2, view3, view4;

		view1 = mLi.inflate(R.layout.view_index, null);
		view2 = mLi.inflate(R.layout.tab_main, null);
		view3 = mLi.inflate(R.layout.settingview, null);
		view4 = mLi.inflate(R.layout.tab_main, null);
		mainview.setView(view1);

		// ÿ��ҳ���view����
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);
		// ���ViewPager������������
		PagerAdapter mPagerAdapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(views.get(position));
			}

			// @Override
			// public CharSequence getPageTitle(int position) {
			// return titles.get(position);
			// }

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(views.get(position));
				return views.get(position);
			}
		};

		mTabPager.setAdapter(mPagerAdapter);
		txtDate = (TextView) view1.findViewById(R.id.txtdate);
		txtSetting1 = (TextView) view1.findViewById(R.id.txt_setting1);
		txtSetting2 = (TextView) view1.findViewById(R.id.txt_setting2);
		txtSetting1.setOnClickListener(txtonclick);
		txtSetting2.setOnClickListener(txtonclick);
		/** ���õ�ǰ������ ****************************************************/
		Date dateNow = new Date(System.currentTimeMillis());// ��ȡ��ǰʱ��
		txtDate.setText(DateUtils.dateToStr("yyyy/MM/dd", dateNow));
		/********************************************************************/
		/** ���ò������ ******************************************************/
		/** ���ڱ����û�״̬ */
		final SharedPreferences myPrefs = this
				.getPreferences(this.MODE_PRIVATE);
		AppVariable.Common.YearMonth = myPrefs.getString(YEARMONTH, "200606");
		Date d = DateUtils.strToDate("yyyyMM", AppVariable.Common.YearMonth);
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		Log.v("yyyyMMdd", mYear + ":" + mMonth + ":" + mDay);
		updateDisplay();

		
	}

	OnClickListener txtonclick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v == txtSetting1) {
				// showMonPicker();
				showDialog(DATE_DIALOG_ID);
				// Toast.makeText(MainTab.this, "1", Toast.LENGTH_LONG).show();
			} else if (v == txtSetting2) {
				Toast.makeText(MainTab.this, "2", Toast.LENGTH_LONG).show();
			}
		}
	};

	/**
	 * ͷ��������
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mTabPager.setCurrentItem(index);
		}
	};

	/* �ϷŽ��ȼ��� ��������Service���滹�и�������ˢ�� */
	/*
	 * ҳ���л�����(ԭ����:D.Winter)
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				mTab1.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_question_pressed));
				break;
			case 1:
				mTab2.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_passage_pressed));
				break;
			case 2:
				mTab3.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_settings_pressed));
				break;
			case 3:
				mTab4.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_answer_pressed));
				break;
			}
			animation = new TranslateAnimation(one * currIndex, one * arg0, 0,
					0);
			if (currIndex == 0) {
				mTab1.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_question_normal));
			} else if (currIndex == 1) {
				mTab2.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_passage_normal));
			} else if (currIndex == 2) {
				mTab3.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_settings_normal));
			} else if (currIndex == 3) {
				mTab4.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_answer_normal));
			}
			currIndex = arg0;
			animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��
			animation.setDuration(150);
			mTabImg.startAnimation(animation);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { // ��ȡ
			if (menu_display) { // ��� Menu�Ѿ��� ���ȹر�Menu
				menuWindow.dismiss();
				menu_display = false;
			} else {
				// Intent intent = new Intent();
				// intent.setClass(CommonTab.this, CETMain.class);
				// startActivity(intent);
				finish();
			}
		}

		else if (keyCode == KeyEvent.KEYCODE_MENU) { // ��ȡ Menu��
			if (!menu_display) {
				// ��ȡLayoutInflaterʵ��
				inflater = (LayoutInflater) this
						.getSystemService(LAYOUT_INFLATER_SERVICE);
				// �����main��������inflate�м����Ŷ����ǰ����ֱ��this.setContentView()�İɣ��Ǻ�
				// �÷������ص���һ��View�Ķ����ǲ����еĸ�
				layout = inflater.inflate(R.menu.main, null);

				// ��������Ҫ�����ˣ����������ҵ�layout���뵽PopupWindow���أ������ܼ�
				menuWindow = new PopupWindow(layout, LayoutParams.FILL_PARENT,
						LayoutParams.WRAP_CONTENT); // ������������width��height
				// menuWindow.showAsDropDown(layout); //���õ���Ч��
				// menuWindow.showAsDropDown(null, 0, layout.getHeight());
				menuWindow.showAtLocation(this.findViewById(R.id.maincetp),
						Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // ����layout��PopupWindow����ʾ��λ��
				// ��λ�ȡ����main�еĿؼ��أ�Ҳ�ܼ�
				// mClose = (LinearLayout) layout.findViewById(R.id.menu_close);
				// mCloseBtn = (LinearLayout) layout
				// .findViewById(R.id.menu_close_btn);

				// �����ÿһ��Layout���е����¼���ע��ɡ�����
				// ���絥��ĳ��MenuItem��ʱ�����ı���ɫ�ı�
				// ����׼����һЩ����ͼƬ������ɫ
				mCloseBtn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {

					}
				});
				menu_display = true;
			} else {
				// �����ǰ�Ѿ�Ϊ��ʾ״̬������������
				menuWindow.dismiss();
				menu_display = false;
			}

			return false;
		}
		return false;
	}

	@Override
	protected void onDestroy() {
		Intent intent = new Intent();
		intent.setClass(this, PlayerService.class);
		stopService(intent);// ֹͣService
		super.onDestroy();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
//		/**********************************************************/
//		Locale locale = new Locale("CH");
//		Locale.setDefault(locale);
//		Configuration config = new Configuration();
//		config.locale = locale;
//		getApplicationContext().getResources()
//				.updateConfiguration(config, null);
		switch (id) {
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute,
					false);
		case DATE_DIALOG_ID:
			return new MyDatePickerDialog(this, mDateSetListener, mYear,
					mMonth, mDay);
	
		}
		return null;
	}

	/**
	 * ��дdatePicker 1.ֻ��ʾ ��-�� 2.title ֻ��ʾ ��-��
	 * 
	 * @author gubojun
	 */
	public class MyDatePickerDialog extends DatePickerDialog {
		public MyDatePickerDialog(Context context, OnDateSetListener callBack,
				int year, int monthOfYear, int dayOfMonth) {

			super(context, callBack, year, monthOfYear, dayOfMonth);

			this.setTitle(year + "��" + (monthOfYear + 1) + "��");
			/**��������ѡ��*/
			 ((ViewGroup) ((ViewGroup) this.getDatePicker().getChildAt(0))
			 .getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
		}

		@Override
		public void onDateChanged(DatePicker view, int year, int month, int day) {
			super.onDateChanged(view, year, month, day);
			this.setTitle(year + "��" + (month + 1) + "��");
		}

	}

	public void showMonPicker() {
		final Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(DateUtils.strToDate("yyyy-MM", txtSetting1
				.getText().toString()));
		new MyDatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				localCalendar.set(1, year);
				localCalendar.set(2, monthOfYear);
				txtSetting1.setText(DateUtils.clanderTodatetime(localCalendar,
						"yyyy-MM"));
			}
		}, localCalendar.get(1), localCalendar.get(2), localCalendar.get(5))
				.show();
	}

	private static class DateUtils {

		// �ַ�����������ת����date����
		public static Date strToDate(String style, String date) {
			SimpleDateFormat formatter = new SimpleDateFormat(style);
			try {
				return formatter.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
				return new Date();
			}
		}

		public static String dateToStr(String style, Date date) {
			SimpleDateFormat formatter = new SimpleDateFormat(style);
			return formatter.format(date);
		}

		public static String clanderTodatetime(Calendar calendar, String style) {
			SimpleDateFormat formatter = new SimpleDateFormat(style);
			return formatter.format(calendar.getTime());
		}
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case TIME_DIALOG_ID:
			((TimePickerDialog) dialog).updateTime(mHour, mMinute);
			break;
		case DATE_DIALOG_ID:
			((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
			break;
		}
	}

	private void updateDisplay() {

		Calendar c = Calendar.getInstance();
		c.set(mYear, mMonth, mDay);
		Date d = c.getTime();

		txtSetting1.setText(Html.fromHtml("<u>"
				+ DateUtils.dateToStr("yyyy��MM��", d) + "</u>"));
		// txtSetting2.setText(Html.fromHtml("<u>" + "����" + "</u>"));

		// txtSetting1.setText(new StringBuilder()
		// // Month is 0 based so add 1
		// .append(mMonth + 1).append("-").append(mDay).append("-")
		// .append(mYear));
		// .append(" ").append(pad(mHour)).append(":")
		// .append(pad(mMinute)));
		final SharedPreferences myPrefs = this
				.getPreferences(this.MODE_PRIVATE);
		AppVariable.Common.YearMonth = DateUtils.dateToStr("yyyyMM", d);
		Editor editor = myPrefs.edit();
		editor.putString(YEARMONTH, AppVariable.Common.YearMonth);
		// Write other values as desired
		editor.commit();
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay();
		}
	};

	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHour = hourOfDay;
			mMinute = minute;
			updateDisplay();
		}
	};

	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}
}
