package com.cetp.view;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cetp.R;
import com.cetp.action.AppVariable;
import com.cetp.action.SkinSettingManager;

public class BackgroundSettingView extends FinalActivity {
	
	String TAG = "BackgroundSettingView";
	
	private SkinSettingManager mSettingManager;
	
	@ViewInject(id = R.id.button1, click = "btnClick")
	Button b;
	@ViewInject(id = R.id.rlt_img1, click = "rltClick")
	RelativeLayout rltImage1;
	@ViewInject(id = R.id.rlt_img2, click = "rltClick")
	RelativeLayout rltImage2;
	@ViewInject(id = R.id.rlt_img3, click = "rltClick")
	RelativeLayout rltImage3;
	@ViewInject(id = R.id.rlt_img4, click = "rltClick")
	RelativeLayout rltImage4;
	@ViewInject(id = R.id.rlt_img5, click = "rltClick")
	RelativeLayout rltImage5;
	@ViewInject(id = R.id.rlt_img6, click = "rltClick")
	RelativeLayout rltImage6;

	@ViewInject(id = R.id.img_bgsetting1)
	ImageView imgBGSetting1;
	@ViewInject(id = R.id.img_bgsetting2)
	ImageView imgBGSetting2;
	@ViewInject(id = R.id.img_bgsetting3)
	ImageView imgBGSetting3;
	@ViewInject(id = R.id.img_bgsetting4)
	ImageView imgBGSetting4;
	@ViewInject(id = R.id.img_bgsetting5)
	ImageView imgBGSetting5;
	@ViewInject(id = R.id.img_bgsetting6)
	ImageView imgBGSetting6;

	@ViewInject(id = R.id.imageView1)
	ImageView img1;
	@ViewInject(id = R.id.imageView2)
	ImageView img2;
	@ViewInject(id = R.id.imageView3)
	ImageView img3;
	@ViewInject(id = R.id.imageView4)
	ImageView img4;
	@ViewInject(id = R.id.imageView5)
	ImageView img5;
	@ViewInject(id = R.id.imageView6)
	ImageView img6;

	int IMG1 = R.drawable.wallpaper_2;
	int IMG2 = R.drawable.wallpaper_default;
	int IMG3 = R.drawable.wallpaper_3;
	int IMG4 = R.drawable.wallpaper_4;
	int IMG5 = R.drawable.wallpaper_5;
	int IMG6 = R.drawable.wallpaper_6;

	CheckBox ch;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.backgroundsettingview);
		// 初始化皮肤
		mSettingManager = new SkinSettingManager(this);
		mSettingManager.initSkins();
		// mSettingManager.changeSkin(mSettingManager.getSkinType());
		setVisibility(mSettingManager.getSkinType(), View.VISIBLE);
		initView();

		ch = (CheckBox) findViewById(R.id.checkBox1);
		ch.setOnCheckedChangeListener(new CheckBoxListener());
		if (mSettingManager.isColorBG())
			ch.setChecked(true);

		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
				ActionBar.DISPLAY_HOME_AS_UP);
	}

	class CheckBoxListener implements
			android.widget.CompoundButton.OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (isChecked) {
				mSettingManager.setisColorBG(true);
			} else
				mSettingManager.setisColorBG(false);
			mSettingManager.initSkins();
		}
	}

	/**
	 * 获得Bitmap
	 * 
	 * @param key
	 * @return Bitmap
	 */
	private Bitmap getBitmap(int key) {
		switch (key) {
		case 0:
			return BitmapFactory.decodeResource(getResources(), IMG1);
		case 1:
			return BitmapFactory.decodeResource(getResources(), IMG2);
		case 2:
			return BitmapFactory.decodeResource(getResources(), IMG3);
		case 3:
			return BitmapFactory.decodeResource(getResources(), IMG4);
		case 4:
			return BitmapFactory.decodeResource(getResources(), IMG5);
		case 5:
			return BitmapFactory.decodeResource(getResources(), IMG6);
		}
		return null;
	}

	/**
	 * 初始化界面
	 * 
	 */
	private void initView() {
		int padding = (int) getResources().getDimension(
				R.dimen.lin_vertical_padding);
		int imgWidth = (AppVariable.Common.SCREEN_WIDTH - padding * 2) / 3;
		int imgHeight = (AppVariable.Common.SCREEN_HEIGHT - padding * 2) / 3;
		// --------------------------------------------------------------------
		Log.v(TAG, "padding=" + padding + "  imgWidth=" + imgWidth
				+ "  imgHeight=" + imgHeight);
		// --------------------------------------------------------------------
		RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
				imgWidth, imgHeight);
		rltImage1.setLayoutParams(param);
		rltImage2.setX(0);
		rltImage2.setLayoutParams(param);
		rltImage2.setX(imgWidth);
		rltImage3.setLayoutParams(param);
		rltImage3.setX(imgWidth * 2);

		rltImage4.setLayoutParams(param);
		rltImage4.setX(0);
		rltImage5.setLayoutParams(param);
		rltImage5.setX(imgWidth);
		rltImage6.setLayoutParams(param);
		rltImage6.setX(imgWidth * 2);

		imgBGSetting1.setImageBitmap(getRoundCornerImage(getBitmap(0), 50));
		imgBGSetting2.setImageBitmap(getRoundCornerImage(getBitmap(1), 50));
		imgBGSetting3.setImageBitmap(getRoundCornerImage(getBitmap(2), 50));
		imgBGSetting4.setImageBitmap(getRoundCornerImage(getBitmap(3), 50));
		imgBGSetting5.setImageBitmap(getRoundCornerImage(getBitmap(4), 50));
		imgBGSetting6.setImageBitmap(getRoundCornerImage(getBitmap(5), 50));
	}

	// private void setColorBG(int color) {
	// getWindow().setBackgroundDrawable(new ColorDrawable(color));
	// }

	private void setVisibility(int key, int visibility) {
		switch (key) {
		case 0:
			img1.setVisibility(visibility);
			break;
		case 1:
			img2.setVisibility(visibility);
			break;
		case 2:
			img3.setVisibility(visibility);
			break;
		case 3:
			img4.setVisibility(visibility);
			break;
		case 4:
			img5.setVisibility(visibility);
			break;
		case 5:
			img6.setVisibility(visibility);
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void btnClick(View v) {
		startActivity(new Intent(getApplicationContext(), ColorSelectView.class));
	}

	public void rltClick(View v) {
		setVisibility(mSettingManager.getSkinType(), View.INVISIBLE);
		if (v == rltImage1) {
			setVisibility(0, View.VISIBLE);
			mSettingManager.changeSkin(0);
		} else if (v == rltImage2) {
			setVisibility(1, View.VISIBLE);
			mSettingManager.changeSkin(1);
		} else if (v == rltImage3) {
			setVisibility(2, View.VISIBLE);
			mSettingManager.changeSkin(2);
		} else if (v == rltImage4) {
			setVisibility(3, View.VISIBLE);
			mSettingManager.changeSkin(3);
		} else if (v == rltImage5) {
			setVisibility(4, View.VISIBLE);
			mSettingManager.changeSkin(4);
		} else if (v == rltImage6) {
			setVisibility(5, View.VISIBLE);
			mSettingManager.changeSkin(5);
		}
	}

	public static Bitmap getRoundCornerImage(Bitmap bitmap, int roundPixels) {
		// 创建一个和原始图片一样大小位图
		Bitmap roundConcerImage = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		// 创建带有位图roundConcerImage的画布
		Canvas canvas = new Canvas(roundConcerImage);
		// 创建画笔
		Paint paint = new Paint();
		// 创建一个和原始图片一样大小的矩形
		Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		RectF rectF = new RectF(rect);
		// 去锯齿
		paint.setAntiAlias(true);
		// 画一个和原始图片一样大小的圆角矩形
		canvas.drawRoundRect(rectF, roundPixels, roundPixels, paint);
		// 设置相交模式
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		// 把图片画到矩形去
		canvas.drawBitmap(bitmap, null, rect, paint);
		return roundConcerImage;
	}

	// 这里为了简单实现，实现换肤
	public boolean onTouchEvent(MotionEvent event) {
		mSettingManager.toggleSkins();
		return super.onTouchEvent(event);
	}

	@Override
	protected void onResume() {
		mSettingManager.initSkins();
		super.onResume();
	}
}
