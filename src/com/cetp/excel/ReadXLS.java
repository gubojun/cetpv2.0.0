package com.cetp.excel;

//��ȡExcel����
import java.io.File;
import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import android.annotation.SuppressLint;

import com.cetp.database.DBClozingOfQuestion;
import com.cetp.database.DBClozingOfText;
import com.cetp.database.DBListeningOfConversation;
import com.cetp.database.DBListeningOfQuestion;
import com.cetp.database.DBListeningOfText;
import com.cetp.database.DBReadingOfPassage;
import com.cetp.database.DBReadingOfQuestion;
import com.cetp.database.DBVocabulary;

/**
 * ����excel
 * 
 * @author �˲��������ӻ�
 * @date 2013-3-29
 */
@SuppressLint("SdCardPath")
public class ReadXLS {
	// private static String FileURL = "mnt/sdcard/cetpdata/Listening_CET4.xls";
	/**
	 * ��������ѡ�������
	 * 
	 * @param fileUrl
	 *            String����
	 * @param db
	 *            DBListeningOfQuestion����
	 */
	public static void readAllData(String fileUrl, DBListeningOfQuestion db) {
		try {
			String[] result = new String[14];
			Workbook book = Workbook.getWorkbook(new File(fileUrl));
			@SuppressWarnings("unused")
			int numberofsheets = book.getNumberOfSheets();
			// ��õ�һ�����������
			Sheet sheet = book.getSheet(0);
			int row = 0, col;
			Cell cell1;
			// �õ���ͷ
			for (row = 0;; row++) {
				cell1 = sheet.getCell(row, 0);
				result[row] = cell1.getContents();
				if (result[row].equals("NULL"))
					break;
			}

			// �õ���i�е�col�еĵ�Ԫ��
			for (col = 1;; col++) {
				cell1 = sheet.getCell(0, col);
				result[0] = cell1.getContents();
				if (result[0].equals("NULL"))
					break;
				for (int i = 0; i < row; i++) {
					cell1 = sheet.getCell(i, col);
					result[i] = cell1.getContents();
				}
				db.insertItem(result[0], result[1], result[2], result[3],
						result[4], result[5], result[6], result[7], result[8]);
			}
			book.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * ��������ѡ�������
	 * 
	 * @param in
	 *            InputStream
	 * @param db
	 *            DBListeningOfQuestion����
	 */
	public static void readAllData(InputStream in, DBListeningOfQuestion db) {
		try {
			String[] result = new String[14];
			Workbook book = Workbook.getWorkbook(in);
			@SuppressWarnings("unused")
			int numberofsheets = book.getNumberOfSheets();
			// ��õ�һ�����������
			Sheet sheet = book.getSheet(0);
			int row = 0, col;
			Cell cell1;
			// �õ���ͷ
			for (row = 0;; row++) {
				cell1 = sheet.getCell(row, 0);
				result[row] = cell1.getContents();
				if (result[row].equals("NULL"))
					break;
			}

			// �õ���i�е�col�еĵ�Ԫ��
			for (col = 1;; col++) {
				cell1 = sheet.getCell(0, col);
				result[0] = cell1.getContents();
				if (result[0].equals("NULL"))
					break;
				for (int i = 0; i < row; i++) {
					cell1 = sheet.getCell(i, col);
					result[i] = cell1.getContents();
				}
				db.insertItem(result[0], result[1], result[2], result[3],
						result[4], result[5], result[6], result[7], result[8]);
			}
			book.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 
	 * @param fileUrl
	 *            String����
	 * @param db
	 *            DBListeningOfText����
	 */
	public static void readAllData(String fileUrl, DBListeningOfText db) {
		try {
			String[] result = new String[15];
			Workbook book = Workbook.getWorkbook(new File(fileUrl));
			@SuppressWarnings("unused")
			int numberofsheets = book.getNumberOfSheets();
			// ��õ�һ�����������
			Sheet sheet = book.getSheet(1);
			int row = 0, col;
			Cell cell1;
			// �õ���ͷ
			for (row = 0;; row++) {
				cell1 = sheet.getCell(row, 0);
				result[row] = cell1.getContents();
				if (result[row].equals("NULL"))
					break;
			}

			// �õ���i�е�col�еĵ�Ԫ��
			for (col = 1;; col++) {
				cell1 = sheet.getCell(0, col);
				result[0] = cell1.getContents();
				if (result[0].equals("NULL"))
					break;
				for (int i = 0; i < row; i++) {
					cell1 = sheet.getCell(i, col);
					result[i] = cell1.getContents();
				}
				db.insertItem(result[0], result[1], result[2], result[3],
						result[4], result[5], result[6]);
			}
			book.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 
	 * @param in
	 *            InputStream
	 * @param db
	 *            DBListeningOfText����
	 */
	public static void readAllData(InputStream in, DBListeningOfText db) {
		try {
			String[] result = new String[15];
			Workbook book = Workbook.getWorkbook(in);
			@SuppressWarnings("unused")
			int numberofsheets = book.getNumberOfSheets();
			// ��õ�һ�����������
			Sheet sheet = book.getSheet(1);
			int row = 0, col;
			Cell cell1;
			// �õ���ͷ
			for (row = 0;; row++) {
				cell1 = sheet.getCell(row, 0);
				result[row] = cell1.getContents();
				if (result[row].equals("NULL"))
					break;
			}

			// �õ���i�е�col�еĵ�Ԫ��
			for (col = 1;; col++) {
				cell1 = sheet.getCell(0, col);
				result[0] = cell1.getContents();
				if (result[0].equals("NULL"))
					break;
				for (int i = 0; i < row; i++) {
					cell1 = sheet.getCell(i, col);
					result[i] = cell1.getContents();
				}
				db.insertItem(result[0], result[1], result[2], result[3],
						result[4], result[5], result[6]);
			}
			book.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 
	 * @param fileUrl
	 *            String����
	 * @param db
	 *            DBListeningOfConversation����
	 */
	public static void readAllData(String fileUrl, DBListeningOfConversation db) {
		try {
			String[] result = new String[15];
			Workbook book = Workbook.getWorkbook(new File(fileUrl));
			@SuppressWarnings("unused")
			int numberofsheets = book.getNumberOfSheets();
			// ��õ�һ�����������
			Sheet sheet = book.getSheet(2);
			int row = 0, col;
			Cell cell1;
			// �õ���ͷ
			for (row = 0;; row++) {
				cell1 = sheet.getCell(row, 0);
				result[row] = cell1.getContents();
				if (result[row].equals("NULL"))
					break;
			}
			// �õ���i�е�col�еĵ�Ԫ��
			for (col = 1;; col++) {
				cell1 = sheet.getCell(0, col);
				result[0] = cell1.getContents();
				if (result[0].equals("NULL"))
					break;
				for (int i = 0; i < row; i++) {
					cell1 = sheet.getCell(i, col);
					result[i] = cell1.getContents();

				}
				db.insertItem(result[0], result[1], result[2], result[3]);
			}
			book.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 
	 * @param in
	 *            InputStream����
	 * @param db
	 *            DBListeningOfConversation����
	 */
	public static void readAllData(InputStream in, DBListeningOfConversation db) {
		try {
			String[] result = new String[15];
			Workbook book = Workbook.getWorkbook(in);
			@SuppressWarnings("unused")
			int numberofsheets = book.getNumberOfSheets();
			// ��õ�һ�����������
			Sheet sheet = book.getSheet(2);
			int row = 0, col;
			Cell cell1;
			// �õ���ͷ
			for (row = 0;; row++) {
				cell1 = sheet.getCell(row, 0);
				result[row] = cell1.getContents();
				if (result[row].equals("NULL"))
					break;
			}
			// �õ���i�е�col�еĵ�Ԫ��
			for (col = 1;; col++) {
				cell1 = sheet.getCell(0, col);
				result[0] = cell1.getContents();
				if (result[0].equals("NULL"))
					break;
				for (int i = 0; i < row; i++) {
					cell1 = sheet.getCell(i, col);
					result[i] = cell1.getContents();

				}
				db.insertItem(result[0], result[1], result[2], result[3]);
			}
			book.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * �����Ķ�ѡ�������
	 * 
	 * @param fileUrl
	 * @param db
	 */
	public static void readAllData(String fileUrl, DBReadingOfQuestion db) {
		try {
			String[] result = new String[14];
			Workbook book = Workbook.getWorkbook(new File(fileUrl));
			@SuppressWarnings("unused")
			int numberofsheets = book.getNumberOfSheets();
			// ��õ�һ�����������
			Sheet sheet = book.getSheet(0);
			int row = 0, col;
			Cell cell1;
			// �õ���ͷ
			for (row = 0;; row++) {
				cell1 = sheet.getCell(row, 0);
				result[row] = cell1.getContents();
				if (result[row].equals("NULL"))
					break;
			}

			// �õ���i�е�col�еĵ�Ԫ��
			for (col = 1;; col++) {
				cell1 = sheet.getCell(0, col);
				result[0] = cell1.getContents();
				if (result[0].equals("NULL"))
					break;
				for (int i = 0; i < row; i++) {
					cell1 = sheet.getCell(i, col);
					result[i] = cell1.getContents();
				}
				db.insertItem(result[0], result[1], result[2], result[3],
						result[4], result[5], result[6], result[7], result[8],
						result[9]);
			}
			book.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * �����Ķ�ѡ�������
	 * 
	 * @param in
	 *            InputStream
	 * @param db
	 */
	public static void readAllData(InputStream in, DBReadingOfQuestion db) {
		try {
			String[] result = new String[14];
			Workbook book = Workbook.getWorkbook(in);
			@SuppressWarnings("unused")
			int numberofsheets = book.getNumberOfSheets();
			// ��õ�һ�����������
			Sheet sheet = book.getSheet(0);
			int row = 0, col;
			Cell cell1;
			// �õ���ͷ
			for (row = 0;; row++) {
				cell1 = sheet.getCell(row, 0);
				result[row] = cell1.getContents();
				if (result[row].equals("NULL"))
					break;
			}

			// �õ���i�е�col�еĵ�Ԫ��
			for (col = 1;; col++) {
				cell1 = sheet.getCell(0, col);
				result[0] = cell1.getContents();
				if (result[0].equals("NULL"))
					break;
				for (int i = 0; i < row; i++) {
					cell1 = sheet.getCell(i, col);
					result[i] = cell1.getContents();
				}
				db.insertItem(result[0], result[1], result[2], result[3],
						result[4], result[5], result[6], result[7], result[8],
						result[9]);
			}
			book.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * �����Ķ�ѡ�������
	 * 
	 * @param fileUrl
	 *            String
	 * @param db
	 */
	public static void readAllData(String fileUrl, DBReadingOfPassage db) {
		try {
			String[] result = new String[15];
			Workbook book = Workbook.getWorkbook(new File(fileUrl));
			@SuppressWarnings("unused")
			int numberofsheets = book.getNumberOfSheets();
			// ��õ�һ�����������
			Sheet sheet = book.getSheet(1);
			int row = 0, col;
			Cell cell1;
			// �õ���ͷ
			for (row = 0;; row++) {
				cell1 = sheet.getCell(row, 0);
				result[row] = cell1.getContents();
				if (result[row].equals("NULL"))
					break;
			}

			// �õ���i�е�col�еĵ�Ԫ��
			for (col = 1;; col++) {
				cell1 = sheet.getCell(0, col);
				result[0] = cell1.getContents();
				if (result[0].equals("NULL"))
					break;
				for (int i = 0; i < row; i++) {
					cell1 = sheet.getCell(i, col);
					result[i] = cell1.getContents();
				}
				db.insertItem(result[0], result[1], result[2], result[3],
						result[4], result[5], result[6]);
			}
			book.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * �����Ķ�ѡ�������
	 * 
	 * @param in
	 *            InputStream
	 * @param db
	 */
	public static void readAllData(InputStream in, DBReadingOfPassage db) {
		try {
			String[] result = new String[15];
			Workbook book = Workbook.getWorkbook(in);
			@SuppressWarnings("unused")
			int numberofsheets = book.getNumberOfSheets();
			// ��õ�һ�����������
			Sheet sheet = book.getSheet(1);
			int row = 0, col;
			Cell cell1;
			// �õ���ͷ
			for (row = 0;; row++) {
				cell1 = sheet.getCell(row, 0);
				result[row] = cell1.getContents();
				if (result[row].equals("NULL"))
					break;
			}

			// �õ���i�е�col�еĵ�Ԫ��
			for (col = 1;; col++) {
				cell1 = sheet.getCell(0, col);
				result[0] = cell1.getContents();
				if (result[0].equals("NULL"))
					break;
				for (int i = 0; i < row; i++) {
					cell1 = sheet.getCell(i, col);
					result[i] = cell1.getContents();
				}
				db.insertItem(result[0], result[1], result[2], result[3],
						result[4], result[5], result[6]);
			}
			book.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * ����������Ŀ������
	 * 
	 * @param fileUrl
	 *            String
	 * @param db
	 *            DBClozingOfQuestion
	 */
	public static void readAllData(String fileUrl, DBClozingOfQuestion db) {
		try {
			String[] result = new String[14];
			Workbook book = Workbook.getWorkbook(new File(fileUrl));
			@SuppressWarnings("unused")
			int numberofsheets = book.getNumberOfSheets();
			// ��õ�һ�����������
			Sheet sheet = book.getSheet(0);
			int row = 0, col;
			Cell cell1;
			// �õ���ͷ
			for (row = 0;; row++) {
				cell1 = sheet.getCell(row, 0);
				result[row] = cell1.getContents();
				if (result[row].equals("NULL"))
					break;
			}

			// �õ���i�е�col�еĵ�Ԫ��
			for (col = 1;; col++) {
				cell1 = sheet.getCell(0, col);
				result[0] = cell1.getContents();
				if (result[0].equals("NULL"))
					break;
				for (int i = 0; i < row; i++) {
					cell1 = sheet.getCell(i, col);
					result[i] = cell1.getContents();
				}
				db.insertItem(result[0], result[1], result[2], result[3],
						result[4], result[5], result[6], result[7], result[8]);
			}
			book.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * ����������Ŀ������
	 * 
	 * @param in
	 *            InputStream
	 * @param db
	 *            DBClozingOfQuestion
	 */
	public static void readAllData(InputStream in, DBClozingOfQuestion db) {
		try {
			String[] result = new String[14];
			Workbook book = Workbook.getWorkbook(in);
			@SuppressWarnings("unused")
			int numberofsheets = book.getNumberOfSheets();
			// ��õ�һ�����������
			Sheet sheet = book.getSheet(0);
			int row = 0, col;
			Cell cell1;
			// �õ���ͷ
			for (row = 0;; row++) {
				cell1 = sheet.getCell(row, 0);
				result[row] = cell1.getContents();
				if (result[row].equals("NULL"))
					break;
			}

			// �õ���i�е�col�еĵ�Ԫ��
			for (col = 1;; col++) {
				cell1 = sheet.getCell(0, col);
				result[0] = cell1.getContents();
				if (result[0].equals("NULL"))
					break;
				for (int i = 0; i < row; i++) {
					cell1 = sheet.getCell(i, col);
					result[i] = cell1.getContents();
				}
				db.insertItem(result[0], result[1], result[2], result[3],
						result[4], result[5], result[6], result[7], result[8]);
			}
			book.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * �����������µ�����
	 * 
	 * @param fileUrl
	 *            String
	 * @param db
	 *            DBClozingOfText
	 */
	public static void readAllData(String fileUrl, DBClozingOfText db) {
		try {
			String[] result = new String[15];
			Workbook book = Workbook.getWorkbook(new File(fileUrl));
			@SuppressWarnings("unused")
			int numberofsheets = book.getNumberOfSheets();
			// ��õ�һ�����������
			Sheet sheet = book.getSheet(1);
			int row = 0, col;
			Cell cell1;
			// �õ���ͷ
			for (row = 0;; row++) {
				cell1 = sheet.getCell(row, 0);
				result[row] = cell1.getContents();
				if (result[row].equals("NULL"))
					break;
			}

			// �õ���i�е�col�еĵ�Ԫ��
			for (col = 1;; col++) {
				cell1 = sheet.getCell(0, col);
				result[0] = cell1.getContents();
				if (result[0].equals("NULL"))
					break;
				for (int i = 0; i < row; i++) {
					cell1 = sheet.getCell(i, col);
					result[i] = cell1.getContents();
				}
				db.insertItem(result[0], result[1], result[2], result[3],
						result[4], result[5]);
			}
			book.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * �����������µ�����
	 * 
	 * @param in
	 *            InputStream
	 * @param db
	 *            DBClozingOfText
	 */
	public static void readAllData(InputStream in, DBClozingOfText db) {
		try {
			String[] result = new String[15];
			Workbook book = Workbook.getWorkbook(in);
			@SuppressWarnings("unused")
			int numberofsheets = book.getNumberOfSheets();
			// ��õ�һ�����������
			Sheet sheet = book.getSheet(1);
			int row = 0, col;
			Cell cell1;
			// �õ���ͷ
			for (row = 0;; row++) {
				cell1 = sheet.getCell(row, 0);
				result[row] = cell1.getContents();
				if (result[row].equals("NULL"))
					break;
			}

			// �õ���i�е�col�еĵ�Ԫ��
			for (col = 1;; col++) {
				cell1 = sheet.getCell(0, col);
				result[0] = cell1.getContents();
				if (result[0].equals("NULL"))
					break;
				for (int i = 0; i < row; i++) {
					cell1 = sheet.getCell(i, col);
					result[i] = cell1.getContents();
				}
				db.insertItem(result[0], result[1], result[2], result[3],
						result[4], result[5]);
			}
			book.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * ����ʻ�����
	 * 
	 * @author �˲���
	 * @param fileUrl
	 *            String
	 * @param db
	 *            DBVocabulary
	 */
	public static void readAllData(String fileUrl, DBVocabulary db) {
		try {
			String[] result = new String[15];
			Workbook book = Workbook.getWorkbook(new File(fileUrl));
			@SuppressWarnings("unused")
			int numberofsheets = book.getNumberOfSheets();
			// ��õ�һ�����������
			Sheet sheet = book.getSheet(0);
			int row = 0, col;
			Cell cell1;
			// �õ���ͷ
			for (row = 0;; row++) {
				cell1 = sheet.getCell(row, 0);
				result[row] = cell1.getContents();
				if (result[row].equals("NULL"))
					break;
				// Toast.makeText(this, result[row], 0).show();
				// System.out.print(result[row] + "  ");
			}
			// System.out.println();

			// �õ���i�е�col�еĵ�Ԫ��
			for (col = 1;; col++) {
				cell1 = sheet.getCell(0, col);
				result[0] = cell1.getContents();
				if (result[0].equals("NULL"))
					break;
				for (int i = 0; i < row; i++) {
					cell1 = sheet.getCell(i, col);
					result[i] = cell1.getContents();
					// Toast.makeText(this, result[i], 0).show();
					// System.out.print(result[i] + " ");
				}
				// System.out.println();
				db.insertItem(result[0], result[1], result[2], result[3],
						result[4], result[5], result[6], result[7], result[8],
						result[9]);
			}
			book.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		// return db;
	}

	/**
	 * ����ʻ�����
	 * 
	 * @author �˲���
	 * @param in
	 *            InputStream
	 * @param db
	 *            DBVocabulary
	 */
	public static void readAllData(InputStream in, DBVocabulary db) {
		try {
			String[] result = new String[15];
			Workbook book = Workbook.getWorkbook(in);
			@SuppressWarnings("unused")
			int numberofsheets = book.getNumberOfSheets();
			// ��õ�һ�����������
			Sheet sheet = book.getSheet(0);
			int row = 0, col;
			Cell cell1;
			// �õ���ͷ
			for (row = 0;; row++) {
				cell1 = sheet.getCell(row, 0);
				result[row] = cell1.getContents();
				if (result[row].equals("NULL"))
					break;
				// Toast.makeText(this, result[row], 0).show();
				// System.out.print(result[row] + "  ");
			}
			// System.out.println();

			// �õ���i�е�col�еĵ�Ԫ��
			for (col = 1;; col++) {
				cell1 = sheet.getCell(0, col);
				result[0] = cell1.getContents();
				if (result[0].equals("NULL"))
					break;
				for (int i = 0; i < row; i++) {
					cell1 = sheet.getCell(i, col);
					result[i] = cell1.getContents();
					// Toast.makeText(this, result[i], 0).show();
					// System.out.print(result[i] + " ");
				}
				// System.out.println();
				db.insertItem(result[0], result[1], result[2], result[3],
						result[4], result[5], result[6], result[7], result[8],
						result[9]);
			}
			book.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		// return db;
	}
}
