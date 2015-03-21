package com.cetp.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Jesse write this view for draw line,use it easy.
 */
public class LineView extends View {
//	private final static String X_KEY = "Xpos";
//	private final static String Y_KEY = "Ypos";
	
	public int XPoint = 40; // ԭ���X����
	public int YPoint = 260; // ԭ���Y����
	public int XScale = 55; // X�Ŀ̶ȳ���
	public int YScale = 40; // Y�Ŀ̶ȳ���
	public int XLength = 380; // X��ĳ���
	public int YLength = 240; // Y��ĳ���
	public String[] XLabel; // X�Ŀ̶�
	public String[] YLabel; // Y�Ŀ̶�
	public String[] Data; // ����
	public String Title; // ��ʾ�ı���
	
	
	
	public void SetInfo(String[] XLabels,String[] YLabels,String[] AllData,String strTitle)
    {
        XLabel=XLabels;
        YLabel=YLabels;
        Data=AllData;
        Title=strTitle;
    }
	
	
	 @Override
	    protected void onDraw(Canvas canvas){
	        super.onDraw(canvas);//��дonDraw����

	        //canvas.drawColor(Color.WHITE);//���ñ�����ɫ
	        Paint paint= new Paint();
	        paint.setStyle(Paint.Style.STROKE);
	        paint.setAntiAlias(true);//ȥ���
	        paint.setColor(Color.BLUE);//��ɫ
	        Paint paint1=new Paint();
	        paint1.setStyle(Paint.Style.STROKE);
	        paint1.setAntiAlias(true);//ȥ���
	        paint1.setColor(Color.DKGRAY);
	        paint.setTextSize(12);  //���������ִ�С
	        //����Y��
	        canvas.drawLine(XPoint, YPoint-YLength, XPoint, YPoint, paint);   //����
	        for(int i=0;i*YScale<YLength ;i++)                
	        {
	            canvas.drawLine(XPoint,YPoint-i*YScale, XPoint+5, YPoint-i*YScale, paint);  //�̶�
	            try
	            {
	                canvas.drawText(YLabel[i] , XPoint-22, YPoint-i*YScale+5, paint);  //����
	            }
	            catch(Exception e)
	            {
	            }
	        }
	        canvas.drawLine(XPoint,YPoint-YLength,XPoint-3,YPoint-YLength+6,paint);  //��ͷ
	        canvas.drawLine(XPoint,YPoint-YLength,XPoint+3,YPoint-YLength+6,paint);            
	        //����X��
	        canvas.drawLine(XPoint,YPoint,XPoint+XLength,YPoint,paint);   //����
	        for(int i=0;i*XScale<XLength;i++)    
	        {
	            canvas.drawLine(XPoint+i*XScale, YPoint, XPoint+i*XScale, YPoint-5, paint);  //�̶�
	            try
	            {
	                canvas.drawText(XLabel[i] , XPoint+i*XScale-10, YPoint+20, paint);  //����
	                //����ֵ
	                    if(i>0&&YCoord(Data[i-1])!=-999&&YCoord(Data[i])!=-999)  //��֤��Ч����
	                        canvas.drawLine(XPoint+(i-1)*XScale, YCoord(Data[i-1]), XPoint+i*XScale, YCoord(Data[i]), paint1);
	                    canvas.drawCircle(XPoint+i*XScale,YCoord(Data[i]), 2, paint1);
	           }
	            catch(Exception e)
	            {
	            }
	        }
	        canvas.drawLine(XPoint+XLength,YPoint,XPoint+XLength-6,YPoint-3,paint);    //��ͷ
	        canvas.drawLine(XPoint+XLength,YPoint,XPoint+XLength-6,YPoint+3,paint);  
	        paint.setTextSize(16);
	        canvas.drawText(Title, 150, 50, paint);
	    }
	    private int YCoord(String y0)  //�������ʱ��Y���꣬������ʱ����-999
	    {
	        int y;
	        try
	        {
	            y=Integer.parseInt(y0);
	        }
	        catch(Exception e)
	        {
	            return -999;    //�����򷵻�-999
	        }
	        try
	        {
	            return YPoint-y*YScale/Integer.parseInt(YLabel[1]);
	        }
	        catch(Exception e)
	        {
	        }
	        return y;
	    }
	
	
	
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	private List<Map<String, Integer>> mListPoint = new ArrayList<Map<String, Integer>>();
//
//	Paint mPaint = new Paint();
//
	public LineView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public LineView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public LineView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

//	@Override
//	protected void onDraw(Canvas canvas) {
//		// TODO Auto-generated method stub
//		super.onDraw(canvas);
//
//		mPaint.setColor(Color.RED);
//		mPaint.setAntiAlias(true);
//
//		for (int index = 0; index < mListPoint.size(); index++) {
//			if (index > 0) {
//				canvas.drawLine(mListPoint.get(index - 1).get(X_KEY),
//						mListPoint.get(index - 1).get(Y_KEY),
//						mListPoint.get(index).get(X_KEY), mListPoint.get(index)
//								.get(Y_KEY), mPaint);
//				canvas.setDrawFilter(new PaintFlagsDrawFilter(0,
//						Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
//			}
//		}
//	}

//	/**
//	 * @param curX
//	 *            which x position you want to draw.
//	 * @param curY
//	 *            which y position you want to draw.
//	 * @see all you put x-y position will connect to a line.
//	 */
//	public void setLinePoint(int curX, int curY) {
//		Map<String, Integer> temp = new HashMap<String, Integer>();
//		temp.put(X_KEY, curX);
//		temp.put(Y_KEY, curY);
//		mListPoint.add(temp);
//		invalidate();
//	}
}