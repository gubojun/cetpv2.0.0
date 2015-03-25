package com.cetp.view;

import java.util.Calendar;

import com.cetp.R;
import com.cetp.action.AppVariable;
import com.cetp.database.DBReadingOfQuestion;
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

public class ReadingViewQuestion implements Runnable {
	public static final String TAG = "ReadingViewOfQuestion";
	private LinearLayout appMenu;// 菜单按钮的弹出菜单
	boolean preHideTag = false;

	public static String[] readingQuestion_All = new String[AppVariable.Common.TOTAL_QUESTION_NUMBER];
	public static String[] readingAnswer_All = new String[AppVariable.Common.TOTAL_QUESTION_NUMBER];

	private Thread mThread;
	private Handler myHandler;
	private Calendar myCalendar; // 日历类
	protected static final int msg_Key = 0x1234;
	private Chronometer timer;
	private boolean timerstop = false;
	int progressbar = 0;// 进度条
	Context context;
	Activity activity;

	public ReadingViewQuestion(Context c) {
		context = c;
		activity = (Activity) c;
	}

	public void setView(View v) {
		// 新建听力数据类
		DBReadingOfQuestion db = new DBReadingOfQuestion(context);
		// 找到控件
		timer = (Chronometer) v.findViewById(R.id.chr_clozing_time);

		// 长按计时器时，出现上下文菜单
		activity.registerForContextMenu(timer);
		// 初始化时间
		timer.setBase(SystemClock.elapsedRealtime());
		timer.setTextColor(Color.WHITE);
		timer.start();
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
			readingAnswer_All[i] = null;
		}
		db.open();
		LinearLayout scrollContext = (LinearLayout) v
				.findViewById(R.id.lin_readingquestion_scrollcontext);
		Cursor cur = db.getAllItem();
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
		db.close();
		cur.close();
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
