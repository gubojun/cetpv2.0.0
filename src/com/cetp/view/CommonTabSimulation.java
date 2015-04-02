/**
 * 
 */
package com.cetp.view;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;

import com.cetp.R;
import com.cetp.action.AppVariable;
import com.cetp.action.SkinSettingManager;
import com.cetp.database.DBCommon;
import com.cetp.service.PlayerService;
import com.cetp.view.CommonTab.MyOnClickListener;
import com.cetp.view.CommonTab.MyOnPageChangeListener;

/**
 * @author 顾博君
 * @since 2015-03-26
 * @version 1.0
 * 
 *          模拟测试
 */
public class CommonTabSimulation extends Activity {
	public static CommonTabSimulation instance = null;
	private static String TAG = "CommonTabSimulation";
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
	private boolean isWrongView;
	private PopupWindow menuWindow;
	private LayoutInflater inflater;
	final String TYPE_OF_VIEW = "typeofview";

	ListeningViewAnswer listeningviewanswer = new ListeningViewAnswer(this);
	ReadingViewAnswer readingviewanswer = new ReadingViewAnswer(this);
	ClozingViewAnswer clozingviewanswer = new ClozingViewAnswer(this);
	VocabularyViewAnswer vocabularyviewanswer = new VocabularyViewAnswer(this);
	View viewAnswer;

	ScrollView scroll;

	// private Button mRightBtn;
	SkinSettingManager mSettingManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去掉标题栏
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.commontab);
		// 设置ActionBar
		setDisplayOptions();
		// 初始化皮肤
		initSkins();
		// 启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		instance = this;

		findView();
		setListener();

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

		setView();
		// 在setView方法中找到scroll
		scroll.setOnTouchListener(new OnTouchListener() {

			private int scrollViewMeasuredHeight;
			private int lastY = 0;
			private int touchEventId = -9983761;
			Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					View scroller = (View) msg.obj;
					if (msg.what == touchEventId) {
						if (lastY == scroller.getScrollY()) {
							isBottom(scroller, lastY);
							handleStop(scroller);
						} else {
							handler.sendMessageDelayed(handler.obtainMessage(
									touchEventId, scroller), 5);
							lastY = scroller.getScrollY();
							isBottom(scroller, lastY);
						}
					}
				}

			};

			/**
			 * 是否滑动停止的处理方法
			 * 
			 * @param scroller
			 */
			private void handleStop(View scroller) {
				Log.d(TAG, "the scrollView is stop:"
						+ scroller.getClass().getName());
			}

			@Override
			public boolean onTouch(View view, MotionEvent e) {

				switch (e.getAction()) {
				case MotionEvent.ACTION_MOVE:
					scrollViewMeasuredHeight = ((ScrollView) view)
							.getChildAt(0).getMeasuredHeight();
					isBottom(view, view.getScrollY());
					break;
				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_UP:
					handler.sendMessageDelayed(
							handler.obtainMessage(touchEventId, view), 5);
					break;
				}
				return false;
			}

			boolean isShow;

			private void isBottom(View view, int scrollY) {

				if (scrollY == 0) {
					boolean BACK = false;
					if (isShow) {
						isShow = false;
						showMessage(BACK);
					}
				} else if (scrollY + view.getHeight() == scrollViewMeasuredHeight) {
					Log.d(TAG, "it is buttom,scrollY:" + scrollY + "  Height:"
							+ view.getHeight() + "  scrollViewMeasuredHeight:"
							+ scrollViewMeasuredHeight);
					boolean NEXT = true;
					if (isShow) {
						isShow = false;
						showMessage(NEXT);
					}
				} else {
					isShow = true;
					Log.d(TAG, "it is not buttom,scrollY:" + scrollY
							+ "  Height:" + view.getHeight()
							+ "  scrollViewMeasuredHeight:"
							+ scrollViewMeasuredHeight);
				}
			}
		});
	}

	// 设置ActionBar-----------------------------------------------------------------
	private void setDisplayOptions() {
		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
				ActionBar.DISPLAY_HOME_AS_UP);

		// actionBar.setSubtitle("mytest");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// 初始化皮肤----------------------------------------------------------------------
	private void initSkins() {
		mSettingManager = new SkinSettingManager(this);
		mSettingManager.initSkins();
	}

	// ----------------------------------------------------------------------------
	private void findView() {
		mTabPager = (ViewPager) findViewById(R.id.tabpager);
		mTab1 = (ImageView) findViewById(R.id.img_question);
		mTab2 = (ImageView) findViewById(R.id.img_passage);
		mTab3 = (ImageView) findViewById(R.id.img_answer);
		mTab4 = (ImageView) findViewById(R.id.img_settings);
		mTabImg = (ImageView) findViewById(R.id.img_tab_now);
	}

	// ----------------------------------------------------------------------------
	private void setListener() {
		mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());

		mTab1.setOnClickListener(new MyOnClickListener(0));
		mTab2.setOnClickListener(new MyOnClickListener(1));
		mTab3.setOnClickListener(new MyOnClickListener(2));
		mTab4.setOnClickListener(new MyOnClickListener(3));
	}

	// ----------------------------------------------------------------------------
	private void setView() {
		// 将要分页显示的View装入数组中
		LayoutInflater mLi = LayoutInflater.from(this);
		View view1, view2, view3, view4;
		if (AppVariable.Common.TypeOfView == 0) {
			view1 = mLi.inflate(R.layout.listeningview_question, null);
			view2 = mLi.inflate(R.layout.listeningview_questiontext, null);
			view3 = mLi.inflate(R.layout.listeningview_answer, null);

			ListeningViewQuestion listeningviewquestion = new ListeningViewQuestion(
					this);
			listeningviewquestion.setView(view1);

			ListeningViewQuestiontext listeningviewquestiontext = new ListeningViewQuestiontext(
					this);
			listeningviewquestiontext.setView(view2);
			listeningviewanswer.setView(view3);
			scroll = (ScrollView) view1
					.findViewById(R.id.scr_listeningquestion_scroll);
		} else if (AppVariable.Common.TypeOfView == 1) {
			view1 = mLi.inflate(R.layout.clozingview_question, null);
			view2 = mLi.inflate(R.layout.clozingview_passage, null);
			view3 = mLi.inflate(R.layout.clozingview_answer, null);
			ClozingViewQuestion clozingviewquestion = new ClozingViewQuestion(
					this);
			ClozingViewPassage clozingviewpassage = new ClozingViewPassage(this);

			clozingviewquestion.setView(view1);
			clozingviewpassage.setView(view2);
			clozingviewanswer.setView(view3);
			scroll = (ScrollView) view1.findViewById(R.id.scr_clozing_question);
		} else if (AppVariable.Common.TypeOfView == 2) {
			view1 = mLi.inflate(R.layout.readingview_question, null);
			view2 = mLi.inflate(R.layout.readingview_passage, null);
			view3 = mLi.inflate(R.layout.readingview_answer, null);
			ReadingViewQuestion readingviewquestion = new ReadingViewQuestion(
					this);
			ReadingViewPassage readingviewpassage = new ReadingViewPassage(this);

			readingviewquestion.setView(view1);
			readingviewpassage.setView(view2);
			readingviewanswer.setView(view3);
			scroll = (ScrollView) view1
					.findViewById(R.id.scr_readingview_question);

		} else {
			view1 = mLi.inflate(R.layout.vocabularyview, null);
			view2 = mLi.inflate(R.layout.vocabularyview_answer, null);
			view3 = mLi.inflate(R.layout.vocabularyview_answer, null);
			VocabularyView vocabularyviewquestion = new VocabularyView(this);
			vocabularyviewquestion.setView(view1);
			vocabularyviewanswer.setView(view2);
			vocabularyviewanswer.setView(view3);
			scroll = (ScrollView) view1
					.findViewById(R.id.scr_vocabulary_question);
		}
		view4 = mLi.inflate(R.layout.settingview, null);
		// 每个页面的view数据
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		if (AppVariable.Common.TypeOfView != 3)
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

	public void showMessage(final boolean next) {
		Builder builder = new AlertDialog.Builder(CommonTabSimulation.this)
				.setTitle("提示").setIcon(android.R.drawable.ic_dialog_info)
				.setMessage(next ? "是否进入下一项？" : "是否返回上一项？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						boolean wrong = false;
						if (AppVariable.Common.TypeOfView == 0) {
							if (next) {
								AppVariable.Common.TypeOfView = 1;
							} else {
								wrong = true;
								Log.v(TAG, "showMessage wrong 0");
							}
						} else if (AppVariable.Common.TypeOfView == 1) {
							if (next) {
								AppVariable.Common.TypeOfView = 2;
							} else
								AppVariable.Common.TypeOfView = 0;
						} else if (AppVariable.Common.TypeOfView == 2) {
							if (next) {
								AppVariable.Common.TypeOfView = 3;
							} else
								AppVariable.Common.TypeOfView = 1;
						} else if (AppVariable.Common.TypeOfView == 3) {
							if (next) {
								wrong = true;
								Log.v(TAG, "showMessage wrong 1");
							} else
								AppVariable.Common.TypeOfView = 2;
						} else {
							wrong = true;
							Log.v(TAG, "showMessage wrong 5");
						}
						if (!wrong) {
							instance.finish();
							Intent intent = new Intent();
							intent.setClass(getApplicationContext(),
									CommonTabSimulation.class);
							startActivity(intent);
						}
					}

				}).setNegativeButton("取消", null);
		if ((AppVariable.Common.TypeOfView == 0 && !next)
				|| (AppVariable.Common.TypeOfView == 3 && next))
			;
		else
			builder.show();

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

	@Override
	protected void onResume() {
		mSettingManager.initSkins();
		super.onResume();
	}
}
