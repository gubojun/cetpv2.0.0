package com.cetp.view;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cetp.R;
import com.cetp.action.AppVariable;
import com.cetp.database.DBVocabulary;
import com.cetp.question.QuestionContext;

public class VocabularyView implements Runnable {
	public static final String TAG = "VocabularyView";
	private Thread mThread;
	private Handler myHandler;
	/** 日历类 */
	private Calendar myCalendar;
	protected static final int msg_Key = 0x1234;
	private Chronometer timer;
	private boolean timerstop = false;

	private TextView txt_part_4;

	public static String[] vocabularyQuestion_All = new String[AppVariable.Common.TOTAL_QUESTION_NUMBER];
	public static String[] vocabularyAnswer_All = new String[AppVariable.Common.TOTAL_QUESTION_NUMBER];

	// private Menu settingMenu;
	private LinearLayout appMenu;// 菜单按钮的弹出菜单
	// Animation 动画
	private Animation menuShowAnimation = null;
	private Animation menuHideAnimation = null;
	/** preferences */
	// private boolean preIsRoot = false;
	// boolean preHideFile = false;
	boolean preHideTag = false;

	int progressbar = 0;// 进度条

	Context context;
	Activity activity;
	private int startTime = 0;

	public VocabularyView(Context c) {
		context = c;
		activity = (Activity) c;
	}

	public void setView(View v) {
		/** 新建词汇数据类 */
		DBVocabulary db = new DBVocabulary(context);

		// 找到控件
		timer = (Chronometer) v.findViewById(R.id.chr_vocabulary_time);

		// 长按计时器时，出现上下文菜单
		activity.registerForContextMenu(timer);
		// 初始化时间
		timer.setBase(SystemClock.elapsedRealtime());
		// timer.setTextColor(Color.RED);
		timer.setTextColor(Color.WHITE);
		startTime = Integer.parseInt(AppVariable.Time.G_TIME_VOCABULARY) * 60;
		timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
			@Override
			public void onChronometerTick(Chronometer chronometer) {
				// 如果开始计时到现在超过了startime秒
				if (SystemClock.elapsedRealtime() - chronometer.getBase() > startTime * 1000) {
					chronometer.stop();
					// 给用户提示
					Toast.makeText(context, "时间到。", Toast.LENGTH_LONG).show();
				}
			}
		});

		timer.start();

		txt_part_4 = (TextView) v.findViewById(R.id.textView_part_4);
		if (!AppVariable.Common.isSimulation) {
			txt_part_4.setVisibility(View.GONE);
		} else {
			txt_part_4.setText("Part II Vocabulary ("
					+ AppVariable.Time.G_TIME_VOCABULARY + "minutes)");
		}

		activity.setProgressBarVisibility(true);
		// progressHorizontal = (ProgressBar)
		// findViewById(R.id.progress_horizontal);
		activity.setProgress(0);
		activity.setSecondaryProgress(0);

		findView();
		// setupToolbar();
		// initAppMenu();

		/* 通过Handler 来接收进程所传递的信息并更新TextView */
		myHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					/* 在这处理要TextView对象Show时间的事件 */
					// 如果进度条没有满，进度条移动
					if (progressbar < 10000) {
						if (timerstop != true)
							progressbar += 100;
						// Title progress is in range 0..10000
						((Activity) context).setProgress(progressbar);
					}
					break;
				default:
					break;
				}
				super.handleMessage(msg);
			}
		};
		/* 通过进程来持续取得系统时间 */
		mThread = new Thread(this);
		mThread.start();

		for (int i = 0; i < 200; i++) {
			vocabularyAnswer_All[i] = null;
		}

		db.open();

		LinearLayout scrollContext = (LinearLayout) v
				.findViewById(R.id.lin_vocabularyquestion_scrollcontext);
		Cursor cur = db.getItemFromYM(AppVariable.Common.YearMonth);
		if (cur.getCount() == 0)
			Toast.makeText(context, "请先下载并导入数据！", Toast.LENGTH_SHORT).show();
		int dataCount = cur.getCount();
		int NUMBER = 0;// NUMBER表示id号，表示题号（要加1），表示题目数
		while (NUMBER < dataCount) {// 循环产生RadioGroup控件
			NUMBER++;
			QuestionContext mylayout = new QuestionContext(context, NUMBER, cur);
			scrollContext.addView(mylayout);
			cur.moveToNext();
		}
		cur.close();
		db.close();
	}

	// // 创建上下文菜单
	// @Override
	// public void onCreateContextMenu(ContextMenu menu, View v,
	// ContextMenuInfo menuInfo) {
	// super.onCreateContextMenu(menu, v, menuInfo);
	// // ContextMenu的Item不支持Icon，所以不用再资源文件中，为它们设定图标
	// if (v.getId() == R.id.chr_vocabulary_time) {
	// // 加载xml菜单布局文件
	// this.getMenuInflater().inflate(R.menu.context_menu, menu);
	// // 设定头部图标
	// menu.setHeaderIcon(R.drawable.icon);
	// // 设定头部标题
	// menu.setHeaderTitle(" 计时器控制选项 ");
	// }
	// }
	//
	// // 选择菜单项后的响应
	// @Override
	// public boolean onContextItemSelected(MenuItem item) {
	// switch (item.getItemId()) {
	// case R.id.timer_start:
	// // 将计时器清零
	// timer.setBase(SystemClock.elapsedRealtime());
	// progressbar = 0;
	// setProgress(progressbar);
	// // 开始计时
	// timer.start();
	// timerstop = false;
	// break;
	// case R.id.timer_stop:
	// // 停止计时
	// timer.stop();
	// timerstop = true;
	// break;
	// case R.id.timer_reset:
	// // 将计时器清零
	// timer.setBase(SystemClock.elapsedRealtime());
	// progressbar = 0;
	// setProgress(progressbar);
	// break;
	// }
	// return super.onContextItemSelected(item);
	// }

	// 得到时间
	public int[] gettime(long begintime) {
		int[] time = new int[3];
		long Time = System.currentTimeMillis() - begintime;
		myCalendar = Calendar.getInstance();
		myCalendar.setTimeInMillis(Time);
		time[0] = myCalendar.get(Calendar.HOUR);
		time[1] = myCalendar.get(Calendar.MINUTE);
		time[2] = myCalendar.get(Calendar.SECOND);
		return time;
	}

	/* 实现一个Runable接口，实例化一个进程对象， 用来持续取得系统时间 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			do {
				Thread.sleep(1000);
				/* 重要关键程序:取得时间后发出信息给Handler */
				Message msg = new Message();
				msg.what = 1;
				myHandler.sendMessage(msg);/* 重要关键程序:取得时间后发出信息给Handler */
			} while (Thread.interrupted() == false);/* 当系统发出中断信息时停止本循环 */
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	/** Find child views */
	private void findView() {
		// optMenu = (ImageView) findViewById(R.id.optmenu);
		// settingMenu=(Menu)findViewById(R.id.menu_settings);
	}

	/**
	 * 设置下方工具栏的点击事件
	 */
	private final void setupToolbar() {
		// optMenu.setOnClickListener(toolbarListener);
	}

	// View.OnClickListener toolbarListener = new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// switch (v.getId()) {
	// case R.id.optmenu:
	// if (appMenu.getVisibility() == View.VISIBLE) {
	// hideAppMenu();
	// } else {
	// showAppMenu();
	// }
	// break;
	// default:
	// break;
	// }
	// }
	// };
	//
	// /**
	// * 显示菜单 重新实现的Option menu.
	// * */
	// private void showAppMenu() {
	// if (menuShowAnimation == null) {
	// menuShowAnimation = AnimationUtils.loadAnimation(this,
	// R.anim.menuhide);
	// }
	// appMenu.startAnimation(menuShowAnimation);
	// appMenu.setVisibility(View.VISIBLE);
	// }
	//
	// /**
	// * 隐藏菜单 重新实现的Option menu.
	// */
	// private void hideAppMenu() {
	// appMenu.setVisibility(View.INVISIBLE);
	// if (menuHideAnimation == null)
	// menuHideAnimation = AnimationUtils.loadAnimation(this,
	// R.anim.menushow);
	// appMenu.startAnimation(menuHideAnimation);
	// }
	//
	// /** 菜单初始化 **/
	// private void initAppMenu() {
	// appMenu = (LinearLayout) findViewById(R.id.appmenu);
	// @SuppressWarnings("unused")
	// View.OnClickListener ocl = new View.OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// hideAppMenu();
	// }
	// };
	// }
}
