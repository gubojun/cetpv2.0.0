package com.cetp.view;

import java.util.Calendar;

import com.cetp.R;
import com.cetp.database.DBClozingOfQuestion;
import com.cetp.question.QuestionContext;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ClozingViewQuestion1 implements Runnable {
	public static final String TAG = "ClozingViewQuestion";
	private Thread mThread;
	private Handler myHandler;
	private Calendar myCalendar; // 日历类
	protected static final int msg_Key = 0x1234;
	private Chronometer timer;
	private boolean timerstop = false;
	/**
	 * 记录用户所答的答案 下标从1开始
	 */
	public static String[] clozingAnswer_All = new String[200];

	int progressbar = 0;// 进度条

	Context context;
	Activity activity;

	public ClozingViewQuestion1(Context c) {
		context = c;
		activity = (Activity) c;
	}

	public void setView(View v) {

		// 新建词汇数据类
		DBClozingOfQuestion db = new DBClozingOfQuestion(context);
		// 找到控件
		timer = (Chronometer) v.findViewById(R.id.chr_clozing_time);

		// 长按计时器时，出现上下文菜单
		activity.registerForContextMenu(timer);
		// 初始化时间
		timer.setBase(SystemClock.elapsedRealtime());
		timer.setTextColor(Color.WHITE);
		timer.start();

		activity.setProgressBarVisibility(true);

		activity.setProgress(0);
		activity.setSecondaryProgress(0);

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
						activity.setProgress(progressbar);
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
			clozingAnswer_All[i] = null;
		}
		db.open();

		LinearLayout scrollContext = (LinearLayout) v
				.findViewById(R.id.lin_clozingquestion_scrollcontext);

		Cursor cur;// 结果集
		cur = db.getAllItem();
		if (cur.getCount() == 0)
			Toast.makeText(context, "请先下载并导入数据！", Toast.LENGTH_SHORT).show();
		int NUMBER = 0;// NUMBER表示id号，表示题号（要加1），表示题目数
		while (cur.moveToNext()) {// 循环产生RadioGroup控件
			NUMBER++;
			QuestionContext mylayout = new QuestionContext(context, NUMBER, cur);
			scrollContext.addView(mylayout);
		}
		cur.close();
		db.close();
	}

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
			e.printStackTrace();
		}
	}

}
