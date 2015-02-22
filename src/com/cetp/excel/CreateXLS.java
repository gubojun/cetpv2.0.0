package com.cetp.excel;

//����Excel����

import java.io.*;
import jxl.*;
import jxl.write.*;

public class CreateXLS {
	private static String FileURL="����.xls";
	public static void main(String args[]) {
		try {
			// ���ļ�
			WritableWorkbook book = Workbook.createWorkbook(new File(FileURL));
			// ������Ϊ����һҳ���Ĺ���������0��ʾ���ǵ�һҳ
			WritableSheet sheet = book.createSheet("��һҳ", 0);
			// ��Label����Ĺ�������ָ����Ԫ��λ���ǵ�һ�е�һ��(0,0)
			// �Լ���Ԫ������Ϊtest
			Label label = new Label(0, 0, "test");
			// ������õĵ�Ԫ����ӵ���������
			sheet.addCell(label);
			/* ����һ���������ֵĵ�Ԫ�����ʹ��Number��������·�����������﷨���� */
			jxl.write.Number number = new jxl.write.Number(1, 0, 789.123);
			sheet.addCell(number);
			// д�����ݲ��ر��ļ�
			book.write();
			book.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
