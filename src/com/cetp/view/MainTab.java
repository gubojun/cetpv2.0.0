package com.cetp.view;

import java.io.File;
import java.util.ArrayList;

import com.cetp.R;
import com.cetp.excel.MyFile;
import com.cetp.service.PlayerService;

import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

public class MainTab extends Activity {
	public static MainTab instance = null;
	private ViewPager mTabPager;
	private ImageView mTabImg;// ����ͼƬ
	private ImageView mTab1, mTab2, mTab3, mTab4;
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
	/** ���ڱ����û�״̬ */
	// final SharedPreferences myPrefs = getPreferences(MODE_PRIVATE);
	// final int typeOfView = myPrefs.getInt(TYPE_OF_VIEW, 0);

	MainView mainview = new MainView(this);
	// ListeningViewAnswer1 listeninganswer = new ListeningViewAnswer1(this);
	// ReadingViewAnswer1 readingviewanswer = new ReadingViewAnswer1(this);
	// ClozingViewAnswer1 clozingviewanswer = new ClozingViewAnswer1(this);
	View viewAnswer;

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
	}

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
}
