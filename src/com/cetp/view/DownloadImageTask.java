package com.cetp.view;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParamBean;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.cetp.R;

public class DownloadImageTask extends AsyncTask<String, Integer, Bitmap> {
	private Context mContext;

	public DownloadImageTask(Context context) {
		mContext = context;
	}

	@Override
	protected Bitmap doInBackground(String... urls) {
		Log.v("doInBackground", "doing download of image");
		return downloadImage(urls);
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		TextView mText = (TextView) ((Activity) mContext)
				.findViewById(R.id.text);
		mText.setText("Progress so far:" + progress[0]);
		//super.onProgressUpdate(progress);
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		if (result != null) {
			ImageView mImage = (ImageView) ((Activity) mContext)
					.findViewById(R.id.image);
			mImage.setImageBitmap(result);
		} else {
			TextView errorMsg = (TextView) ((Activity) mContext)
					.findViewById(R.id.errorMsg);
			errorMsg.setText("Problem downloading image.Please try again later.");
		}
		//super.onPostExecute(result);
	}

	private Bitmap downloadImage(String... urls) {
		HttpClient httpClient=CustomHttpClient.getHttpClient();
		try{
			HttpGet request=new HttpGet(urls[0]);
			HttpParams params=new BasicHttpParams();
			HttpConnectionParams.setSoTimeout(params, 60000);//1 minute
			request.setParams(params);
			publishProgress(25);
			HttpResponse response=httpClient.execute(request);
			publishProgress(50);
			byte[] image=EntityUtils.toByteArray(response.getEntity());
			publishProgress(75);
			Bitmap mBitmap=BitmapFactory.decodeByteArray(image, 0, image.length);
			publishProgress(100);
			return mBitmap;
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
}
