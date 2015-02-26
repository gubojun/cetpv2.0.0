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

public class ClozingViewQuestion implements Runnable {
	public static final String TAG = "ClozingViewQuestion";
	private Thread mThread;
	private Handler myHandler;
	private Calendar myCalendar; // ������
	protected static final int msg_Key = 0x1234;
	private Chronometer timer;
	private boolean timerstop = false;
	/**
	 * ��¼�û�����Ĵ� �±��1��ʼ
	 */
	public static String[] clozingAnswer_All = new String[200];

	int progressbar = 0;// ������

	Context context;
	Activity activity;

	public ClozingViewQuestion(Context c) {
		context = c;
		activity = (Activity) c;
	}

	public void setView(View v) {

		// �½��ʻ�������
		DBClozingOfQuestion db = new DBClozingOfQuestion(context);
		// �ҵ��ؼ�
		timer = (Chronometer) v.findViewById(R.id.chr_clozing_time);

		// ������ʱ��ʱ�����������Ĳ˵�
		activity.registerForContextMenu(timer);
		// ��ʼ��ʱ��
		timer.setBase(SystemClock.elapsedRealtime());
		timer.setTextColor(Color.WHITE);
		timer.start();

		activity.setProgressBarVisibility(true);

		activity.setProgress(0);
		activity.setSecondaryProgress(0);

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
			clozingAnswer_All[i] = null;
		}
		db.open();

		LinearLayout scrollContext = (LinearLayout) v
				.findViewById(R.id.lin_clozingquestion_scrollcontext);

		Cursor cur;// �����
		cur = db.getAllItem();
		if (cur.getCount() == 0)
			Toast.makeText(context, "�������ز��������ݣ�", Toast.LENGTH_SHORT).show();
		int NUMBER = 0;// NUMBER��ʾid�ţ���ʾ��ţ�Ҫ��1������ʾ��Ŀ��
		while (cur.moveToNext()) {// ѭ������RadioGroup�ؼ�
			NUMBER++;
			QuestionContext mylayout = new QuestionContext(context, NUMBER, cur);
			scrollContext.addView(mylayout);
		}
		cur.close();
		db.close();
	}

	// �õ�ʱ��
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
