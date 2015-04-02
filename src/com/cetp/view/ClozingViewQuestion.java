package com.cetp.view;

import java.util.Calendar;

import com.cetp.R;
import com.cetp.action.AppVariable;
import com.cetp.database.DBClozingOfQuestion;
import com.cetp.question.QuestionContext;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ClozingViewQuestion implements Runnable {
	public static final String TAG = "ClozingViewQuestion";
	private Thread mThread;
	private Handler myHandler;
	private Calendar myCalendar; // ������
	protected static final int msg_Key = 0x1234;
	private Chronometer timer;
	private boolean timerstop = false;
	public static String[] clozingQuestion_All = new String[AppVariable.Common.TOTAL_QUESTION_NUMBER];
	/**
	 * ��¼�û�����Ĵ� �±��1��ʼ
	 */
	public static String[] clozingAnswer_All = new String[AppVariable.Common.TOTAL_QUESTION_NUMBER];

	int progressbar = 0;// ������

	Context context;
	Activity activity;
	private int startTime = 0;

	private TextView txt_part_2;

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
		startTime = Integer.parseInt(AppVariable.Time.G_TIME_CLOZING) * 60;
		timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
			@Override
			public void onChronometerTick(Chronometer chronometer) {
				// �����ʼ��ʱ�����ڳ�����startime��
				if (SystemClock.elapsedRealtime() - chronometer.getBase() > startTime * 1000) {
					chronometer.stop();
					// ���û���ʾ
					Toast.makeText(context, "ʱ�䵽��", Toast.LENGTH_LONG).show();
				}
			}
		});

		timer.start();

		txt_part_2 = (TextView) v.findViewById(R.id.textView_part_2);
		if (!AppVariable.Common.isSimulation) {
			txt_part_2.setVisibility(View.GONE);
		} else {
			txt_part_2.setText("Part II Clozing ("
					+ AppVariable.Time.G_TIME_CLOZING + "minutes)");
		}

		activity.setProgressBarVisibility(true);

		activity.setProgress(0);
		activity.setSecondaryProgress(0);

		/* ͨ��Handler �����ս��������ݵ���Ϣ������TextView */
		// myHandler = new Handler() {
		// @Override
		// public void handleMessage(Message msg) {
		// switch (msg.what) {
		// case 1:
		// /* ���⴦��ҪTextView����Showʱ����¼� */
		// // ���������û�������������ƶ�
		// if (progressbar < 10000) {
		// if (timerstop != true)
		// progressbar += 100;
		// // Title progress is in range 0..10000
		// activity.setProgress(progressbar);
		// }
		// break;
		// default:
		// break;
		// }
		// super.handleMessage(msg);
		// }
		// };
		// /* ͨ������������ȡ��ϵͳʱ�� */
		// mThread = new Thread(this);
		// mThread.start();

		for (int i = 0; i < 200; i++) {
			clozingAnswer_All[i] = null;
		}
		db.open();

		LinearLayout scrollContext = (LinearLayout) v
				.findViewById(R.id.lin_clozingquestion_scrollcontext);

		Cursor cur;// �����
		cur = db.getItemFromYM(AppVariable.Common.YearMonth);
		if (cur.getCount() == 0)
			Toast.makeText(context, "�������ز��������ݣ�", Toast.LENGTH_SHORT).show();
		int dataCount = cur.getCount();
		Log.d(TAG, "dataCount=" + dataCount);
		int NUMBER = 0;// NUMBER��ʾid�ţ���ʾ��ţ�Ҫ��1������ʾ��Ŀ��
		while (NUMBER < dataCount) {// ѭ������RadioGroup�ؼ�
			NUMBER++;
			QuestionContext mylayout = new QuestionContext(context, NUMBER, cur);
			scrollContext.addView(mylayout);
			cur.moveToNext();
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
