package com.cetp.view;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.Toast;

import com.cetp.R;
import com.cetp.database.DBVocabulary;
import com.cetp.question.QuestionContext;

public class VocabularyView extends Activity implements Runnable{
	public static final String TAG = "VocabularyView";
	private Thread mThread;
	private Handler myHandler;
	/** ������ */
	private Calendar myCalendar;
	protected static final int msg_Key = 0x1234;
	private Chronometer timer;
	private boolean timerstop = false;

	public static String[] vocabularyAnswer_All = new String[200];

	/** �½��ʻ������� */
	DBVocabulary db = new DBVocabulary(this);

	// private Menu settingMenu;
	private LinearLayout appMenu;// �˵���ť�ĵ����˵�
	// Animation ����
	private Animation menuShowAnimation = null;
	private Animation menuHideAnimation = null;
	/** preferences */
	// private boolean preIsRoot = false;
	// boolean preHideFile = false;
	boolean preHideTag = false;

	int progressbar = 0;// ������
	// /////////////////////////////////////////////////////
	@SuppressLint("HandlerLeak")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "Activity State: onCreate()");
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.vocabularyview);
		// �ҵ��ؼ�
		timer = (Chronometer) this.findViewById(R.id.chr_vocabulary_time);

		// ������ʱ��ʱ�����������Ĳ˵�
		this.registerForContextMenu(timer);
		// ��ʼ��ʱ��
		timer.setBase(SystemClock.elapsedRealtime());
		timer.setTextColor(Color.RED);
		timer.start();

		setProgressBarVisibility(true);
		// progressHorizontal = (ProgressBar)
		// findViewById(R.id.progress_horizontal);
		setProgress(0);
		setSecondaryProgress(0);

		findView();
		setupToolbar();
		initAppMenu();

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
						setProgress(progressbar);
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
			vocabularyAnswer_All[i] = null;
		}

		db.open();

		LinearLayout scrollContext = (LinearLayout) findViewById(R.id.lin_vocabularyquestion_scrollcontext);
		Cursor cur = db.getAllItem();
		if (cur.getCount() == 0)
			Toast.makeText(this, "�������ز��������ݣ�", Toast.LENGTH_SHORT).show();
		int NUMBER = 0;// NUMBER��ʾid�ţ���ʾ��ţ�Ҫ��1������ʾ��Ŀ��
		while (cur.moveToNext()) {// ѭ������RadioGroup�ؼ�
			NUMBER++;
			QuestionContext mylayout = new QuestionContext(this, NUMBER, cur);
			scrollContext.addView(mylayout);

		}
		cur.close();
		db.close();
	}

	// ���������Ĳ˵�
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		// ContextMenu��Item��֧��Icon�����Բ�������Դ�ļ��У�Ϊ�����趨ͼ��
		if (v.getId() == R.id.chr_vocabulary_time) {
			// ����xml�˵������ļ�
			this.getMenuInflater().inflate(R.menu.context_menu, menu);
			// �趨ͷ��ͼ��
			menu.setHeaderIcon(R.drawable.icon);
			// �趨ͷ������
			menu.setHeaderTitle(" ��ʱ������ѡ�� ");
		}
	}

	// ѡ��˵�������Ӧ
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.timer_start:
			// ����ʱ������
			timer.setBase(SystemClock.elapsedRealtime());
			progressbar = 0;
			setProgress(progressbar);
			// ��ʼ��ʱ
			timer.start();
			timerstop = false;
			break;
		case R.id.timer_stop:
			// ֹͣ��ʱ
			timer.stop();
			timerstop = true;
			break;
		case R.id.timer_reset:
			// ����ʱ������
			timer.setBase(SystemClock.elapsedRealtime());
			progressbar = 0;
			setProgress(progressbar);
			break;
		}
		return super.onContextItemSelected(item);
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
	 * �����·��������ĵ���¼�
	 */
	private final void setupToolbar() {
		// optMenu.setOnClickListener(toolbarListener);
	}

	View.OnClickListener toolbarListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.optmenu:
				if (appMenu.getVisibility() == View.VISIBLE) {
					hideAppMenu();
				} else {
					showAppMenu();
				}
				break;
			default:
				break;
			}
		}
	};

	/**
	 * ��ʾ�˵� ����ʵ�ֵ�Option menu.
	 * */
	private void showAppMenu() {
		if (menuShowAnimation == null) {
			menuShowAnimation = AnimationUtils.loadAnimation(this,
					R.anim.menuhide);
		}
		appMenu.startAnimation(menuShowAnimation);
		appMenu.setVisibility(View.VISIBLE);
	}

	/**
	 * ���ز˵� ����ʵ�ֵ�Option menu.
	 */
	private void hideAppMenu() {
		appMenu.setVisibility(View.INVISIBLE);
		if (menuHideAnimation == null)
			menuHideAnimation = AnimationUtils.loadAnimation(this,
					R.anim.menushow);
		appMenu.startAnimation(menuHideAnimation);
	}

	/** �˵���ʼ�� **/
	private void initAppMenu() {
		appMenu = (LinearLayout) findViewById(R.id.appmenu);
		@SuppressWarnings("unused")
		View.OnClickListener ocl = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				hideAppMenu();
			}
		};
	}

	/** ��д��okKeyDown���������ղ�������̰����¼� */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent e) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (appMenu.getVisibility() == View.VISIBLE)
				hideAppMenu();
			else
				this.finish();
			break;
		}
		return false;// super.onKeyDown(keyCode, e);
	}

	public void onDestory() {
		db.close();
		super.onDestroy();
	}
}
