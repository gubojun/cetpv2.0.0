package com.cetp.view;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.cetp.R;

public class HttpActivity extends Activity {
	private DownloadImageTask diTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void doClick(View view){
		if(diTask!=null){
			AsyncTask.Status diStatus=diTask.getStatus();
			Log.v("doClick","diTask status is "+diStatus);
			if(diStatus!=AsyncTask.Status.FINISHED){
				Log.v("doClick","... no need to start a new task");
				return ;
			}
		}
		diTask=new DownloadImageTask(this);
		diTask.execute("http://e.hiphotos.baidu.com/image/pic/item/8601a18b87d6277f12468f272a381f30e924fc5d.jpg");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.http, menu);
		return true;
	}

}
