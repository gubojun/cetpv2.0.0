package com.cetp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class ChartView extends View{
    public int XPoint=40;    //ԭ���X����
    public int YPoint=260;     //ԭ���Y����
    public int XScale=55;     //X�Ŀ̶ȳ���
    public int YScale=40;     //Y�Ŀ̶ȳ���
    public int XLength=380;        //X��ĳ���
    public int YLength=240;        //Y��ĳ���
    public String[] XLabel;    //X�Ŀ̶�
    public String[] YLabel;    //Y�Ŀ̶�
    public String[] Data;      //����
    public String Title;    //��ʾ�ı���
    public ChartView(Context context)
    {
        super(context);
    }
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
                        canvas.drawLine(XPoint+(i-1)*XScale, YCoord(Data[i-1]), XPoint+i*XScale, YCoord(Data[i]), paint);
                    canvas.drawCircle(XPoint+i*XScale,YCoord(Data[i]), 2, paint);
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
}
