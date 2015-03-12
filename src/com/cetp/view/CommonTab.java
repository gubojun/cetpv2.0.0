package com.cetp.view;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.cetp.R;
import com.cetp.action.AppVariable;
import com.cetp.service.PlayerService;

public class CommonTab extends Activity {
	public static CommonTab instance = null;
	private ViewPager mTabPager;
	private ImageView mTabImg;// 动画图片
	private ImageView mTab1, mTab2, mTab3, mTab4;
	private int zero = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int one;// 单个水平动画位移
	private int two;
	private int three;
	private LinearLayout mClose;
	private LinearLayout mCloseBtn;
	private View layout;
	private boolean menu_display = false;
	private PopupWindow menuWindow;
	private LayoutInflater inflater;
	final String TYPE_OF_VIEW = "typeofview";
	final String WRONGSTAT = "wrongstat";
	ListeningViewAnswer listeningviewanswer = new ListeningViewAnswer(this);
	ReadingViewAnswer readingviewanswer = new ReadingViewAnswer(this);
	ClozingViewAnswer clozingviewanswer = new ClozingViewAnswer(this);
	// VocabularyViewAnswer vocabularyviewanswer = new VocabularyViewAnswer();
	View viewAnswer;

	// private Button mRightBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去掉标题栏
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
				ActionBar.DISPLAY_HOME_AS_UP);

		setContentView(R.layout.commontab);
		/** 用于保存用户状态 */
		SharedPreferences MainTabPrefs = getSharedPreferences("view.MainTab",
				MODE_PRIVATE);
		int type = MainTabPrefs.getInt(TYPE_OF_VIEW, 0);
		final String[] array = new String[] { "听力练习", "完型练习", "阅读练习", "词汇练习" };
		setTitle(array[type]);
		// 初始化皮肤
		// SkinSettingManager mSettingManager = new SkinSettingManager(this);
		// mSettingManager.initSkins();

		// 启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		instance = this;
		/*
		 * mRightBtn = (Button) findViewById(R.id.right_btn);
		 * mRightBtn.setOnClickListener(new Button.OnClickListener() { @Override
		 * public void onClick(View v) { showPopupWindow
		 * (MainWeixin.this,mRightBtn); } });
		 */

		mTabPager = (ViewPager) findViewById(R.id.tabpager);
		mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());

		mTab1 = (ImageView) findViewById(R.id.img_question);
		mTab2 = (ImageView) findViewById(R.id.img_passage);
		mTab3 = (ImageView) findViewById(R.id.img_answer);
		mTab4 = (ImageView) findViewById(R.id.img_settings);
		mTabImg = (ImageView) findViewById(R.id.img_tab_now);
		mTab1.setOnClickListener(new MyOnClickListener(0));
		mTab2.setOnClickListener(new MyOnClickListener(1));
		mTab3.setOnClickListener(new MyOnClickListener(2));
		mTab4.setOnClickListener(new MyOnClickListener(3));
		Display currDisplay = getWindowManager().getDefaultDisplay();// 获取屏幕当前分辨率
		int displayWidth = currDisplay.getWidth();
		int displayHeight = currDisplay.getHeight();
		one = displayWidth / 4; // 设置水平动画平移大小
		two = one * 2;
		three = one * 3;
		// Log.i("info", "获取的屏幕分辨率为" + one + two + three + "X" + displayHeight);
		/* 测量控件的width和height值 */
		int width = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int height = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		mTabImg.measure(width, height);
		int w = mTabImg.getMeasuredWidth();
		int sw = (one - w) / 2;
		mTabImg.setX(sw > 0 ? sw : 0);
		// set();
		// InitImageView();//使用动画
		// 将要分页显示的View装入数组中
		LayoutInflater mLi = LayoutInflater.from(this);
		View view1, view2, view3, view4;
		if (AppVariable.Common.TypeOfView == 0) {
			view1 = mLi.inflate(R.layout.listeningview_question, null);
			view2 = mLi.inflate(R.layout.listeningview_questiontext, null);
			view3 = mLi.inflate(R.layout.listeningview_answer, null);

			ListeningViewQuestion listeningviewquestion = new ListeningViewQuestion(
					this);
			ListeningViewQuestiontext listeningviewquestiontext = new ListeningViewQuestiontext(
					this);
			listeningviewquestion.setView(view1);
			listeningviewquestiontext.setView(view2);
			listeningviewanswer.setView(view3);
		} else if (AppVariable.Common.TypeOfView == 1) {
			view1 = mLi.inflate(R.layout.readingview_question, null);
			view2 = mLi.inflate(R.layout.readingview_passage, null);
			view3 = mLi.inflate(R.layout.readingview_answer, null);
			ReadingViewQuestion readingviewquestion = new ReadingViewQuestion(
					this);
			ReadingViewPassage readingviewpassage = new ReadingViewPassage(this);

			readingviewquestion.setView(view1);
			readingviewpassage.setView(view2);
			readingviewanswer.setView(view3);
		} else if (AppVariable.Common.TypeOfView == 2) {
			view1 = mLi.inflate(R.layout.clozingview_question, null);
			view2 = mLi.inflate(R.layout.clozingview_passage, null);
			view3 = mLi.inflate(R.layout.clozingview_answer, null);
			ClozingViewQuestion clozingviewquestion = new ClozingViewQuestion(
					this);
			ClozingViewPassage clozingviewpassage = new ClozingViewPassage(this);

			clozingviewquestion.setView(view1);
			clozingviewpassage.setView(view2);
			clozingviewanswer.setView(view3);
		} else {
			view1 = mLi.inflate(R.layout.clozingview_question, null);
			view2 = mLi.inflate(R.layout.clozingview_passage, null);
			view3 = mLi.inflate(R.layout.clozingview_answer, null);
			ClozingViewQuestion clozingviewquestion = new ClozingViewQuestion(
					this);
			ClozingViewPassage clozingviewpassage = new ClozingViewPassage(this);

			clozingviewquestion.setView(view1);
			clozingviewpassage.setView(view2);
			clozingviewanswer.setView(view3);
		}
		view4 = mLi.inflate(R.layout.settingview, null);
		// 每个页面的view数据
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);
		viewAnswer = view3;
		views.add(view4);
		// 填充ViewPager的数据适配器
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 头标点击监听
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

	/* 拖放进度监听 ，别忘了Service里面还有个进度条刷新 */
	/*
	 * 页卡切换监听(原作者:D.Winter)
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
						R.drawable.tab_answer_pressed));
				if (AppVariable.Common.TypeOfView == 0)
					listeningviewanswer.reFresh(viewAnswer);
				else if (AppVariable.Common.TypeOfView == 1)
					clozingviewanswer.reFresh(viewAnswer);
				else if (AppVariable.Common.TypeOfView == 2)
					readingviewanswer.reFresh(viewAnswer);
				else if (AppVariable.Common.TypeOfView == 3)
					listeningviewanswer.reFresh(viewAnswer);
				break;
			case 3:
				mTab4.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_settings_pressed));
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
						R.drawable.tab_answer_normal));
			} else if (currIndex == 3) {
				mTab4.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_settings_normal));
			}
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
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
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { // 获取
																				// back键

			if (menu_display) { // 如果 Menu已经打开 ，先关闭Menu
				menuWindow.dismiss();
				menu_display = false;
			} else {
				// Intent intent = new Intent();
				// intent.setClass(CommonTab.this, CETMain.class);
				// startActivity(intent);
				finish();
			}
		}

		else if (keyCode == KeyEvent.KEYCODE_MENU) { // 获取 Menu键
			if (!menu_display) {
				// 获取LayoutInflater实例
				inflater = (LayoutInflater) this
						.getSystemService(LAYOUT_INFLATER_SERVICE);
				// 这里的main布局是在inflate中加入的哦，以前都是直接this.setContentView()的吧？呵呵
				// 该方法返回的是一个View的对象，是布局中的根
				// layout = inflater.inflate(R.layout.main_menu, null);

				// 下面我们要考虑了，我怎样将我的layout加入到PopupWindow中呢？？？很简单
				menuWindow = new PopupWindow(layout, LayoutParams.FILL_PARENT,
						LayoutParams.WRAP_CONTENT); // 后两个参数是width和height
				// menuWindow.showAsDropDown(layout); //设置弹出效果
				// menuWindow.showAsDropDown(null, 0, layout.getHeight());
				menuWindow.showAtLocation(this.findViewById(R.id.maincetp),
						Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
				// 如何获取我们main中的控件呢？也很简单
				// mClose = (LinearLayout) layout.findViewById(R.id.menu_close);
				// mCloseBtn = (LinearLayout) layout
				// .findViewById(R.id.menu_close_btn);

				// 下面对每一个Layout进行单击事件的注册吧。。。
				// 比如单击某个MenuItem的时候，他的背景色改变
				// 事先准备好一些背景图片或者颜色
				mCloseBtn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// Toast.makeText(Main.this, "退出",
						// Toast.LENGTH_LONG).show();
						// Intent intent = new Intent();
						// intent.setClass(CommonTab.this, Exit.class);
						// startActivity(intent);
						menuWindow.dismiss(); // 响应点击事件之后关闭Menu
						menu_display = false;
					}
				});
				menu_display = true;
			} else {
				// 如果当前已经为显示状态，则隐藏起来
				menuWindow.dismiss();
				menu_display = false;
			}

			return false;
		}
		return false;
	}

	@Override
	protected void onDestroy() {
		// Intent intent = new Intent();
		// intent.setClass(this, PlayerService.class);
		// stopService(intent);// 停止Service
		// if(PlayerService.mMediaPlayer!=null)
		// PlayerService.mMediaPlayer=null;

		if (PlayerService.mMediaPlayer != null)
			PlayerService.mMediaPlayer.reset();
		super.onDestroy();
	}

}
