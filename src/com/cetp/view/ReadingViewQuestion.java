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
	private LinearLayout appMenu;// �˵���ť�ĵ����˵�
	boolean preHideTag = false;

	public static String[] readingQuestion_All = new String[AppVariable.Common.TOTAL_QUESTION_NUMBER];
	public static String[] readingAnswer_All = new String[AppVariable.Common.TOTAL_QUESTION_NUMBER];

	private Thread mThread;
	private Handler myHandler;
	private Calendar myCalendar; // ������
	protected static final int msg_Key = 0x1234;
	private Chronometer timer;
	private boolean timerstop = false;
	int progressbar = 0;// ������
	Context context;
	Activity activity;

	public ReadingViewQuestion(Context c) {
		context = c;
		activity = (Activity) c;
	}

	public void setView(View v) {
		// �½�����������
		DBReadingOfQuestion db = new DBReadingOfQuestion(context);
		// �ҵ��ؼ�
		timer = (Chronometer) v.findViewById(R.id.chr_clozing_time);

		// ������ʱ��ʱ�����������Ĳ˵�
		activity.registerForContextMenu(timer);
		// ��ʼ��ʱ��
		timer.setBase(SystemClock.elapsedRealtime());
		timer.setTextColor(Color.WHITE);
		timer.start();
		/* ͨ��Handler �����ս��������ݵ���Ϣ������TextView */
		myHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					/* ���⴦��ҪTextView����Showʱ����¼� */
					// ���������û�������������ƶ�
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
		/* ͨ������������ȡ��ϵͳʱ�� */
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
			Toast.makeText(context, "�������ز��������ݣ�", Toast.LENGTH_SHORT).show();
		int dataCount = cur.getCount();
		int NUMBER = 0;// NUMBER��ʾid�ţ���ʾ��ţ�Ҫ��1������ʾ��Ŀ��
		while (NUMBER < dataCount) {// ѭ������RadioGroup�ؼ�
			NUMBER++;
			QuestionContext mylayout = new QuestionContext(context, NUMBER, cur);
			scrollContext.addView(mylayout);
			cur.moveToNext();
		}
		db.close();
		cur.close();
	}

	/* ʵ��һ��Runable�ӿڣ�ʵ����һ�����̶��� ��������ȡ��ϵͳʱ�� */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			do {
				Thread.sleep(1000);
				/* ��Ҫ�ؼ�����:ȡ��ʱ��󷢳���Ϣ��Handler */
				Message msg = new Message();
				msg.what = 1;
				myHandler.sendMessage(msg);/* ��Ҫ�ؼ�����:ȡ��ʱ��󷢳���Ϣ��Handler */
			} while (Thread.interrupted() == false);/* ��ϵͳ�����ж���Ϣʱֹͣ��ѭ�� */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
