package com.cetp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBReadingOfPassage {
	public static final String KEY_ID = "ID";// id
	public static final String KEY_YEARMONTH = "YYYYMM";// ����
	public static final String KEY_QUESIONTYPE = "QuestionType";// ��Ŀ����
	public static final String KEY_QUESTIONNUMBER = "QuestionNumber";//���
	public static final String KEY_PASSAGETEXT = "PassageText";//��Ŀ�ı�
	public static final String KEY_QUESTIONSTARTNUMBER = "QuestionStartNumber";//�ı������Ŀ�ʼ����
	public static final String KEY_QUESTIONENDNUMBER = "QuestionEndNumber";//�ı������Ľ�������
	public static final String KEY_QUESTIONTOTAL = "QuestionTotal";//�ܹ�������

	private static final String TAG = "DBListening";
	private static final String DATABASE_TABLE = "Reading_Comprehension_Passage";
	private static final String DATABASE_CREATE = "create table if not exists "
			+ DATABASE_TABLE
			+ " (ID integer primary key autoincrement, "// ������
			+ "YYYYMM text not null, " // ����
			+ "QuestionType text not null, "// ��Ŀ����
			+ "QuestionNumber text not null, "//���
			+ "PassageText text not null, "//��Ŀ�ı�
			+ "QuestionStartNumber text not null, "//�ı������Ŀ�ʼ����
			+ "QuestionEndNumber text not null, "//�ı������Ľ�������
			+ "QuestionTotal text not null);";//�ܹ�������
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public DBReadingOfPassage(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	// ---�����ݿ�---

	public DBReadingOfPassage open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	// ---�ر����ݿ�---
	public void close() {
		DBHelper.close();
	}

	// ---�����ݿ��в���һ������---
	public long insertItem(String YYYYMM, String QuestionType,
			String QuestionNumber, String PassageText, String QuestionStartNumber,
			String QusetionEndNumber, String QuestionTotal) {
		ContentValues initialValues = new ContentValues();
		// ����ֵ
		initialValues.put(KEY_YEARMONTH, YYYYMM);
		initialValues.put(KEY_QUESIONTYPE, QuestionType);
		initialValues.put(KEY_QUESTIONNUMBER, QuestionNumber);
		initialValues.put(KEY_PASSAGETEXT, PassageText);
		initialValues.put(KEY_QUESTIONSTARTNUMBER, QuestionStartNumber);
		initialValues.put(KEY_QUESTIONENDNUMBER, QusetionEndNumber);
		initialValues.put(KEY_QUESTIONTOTAL, QuestionTotal);
		return db.insert(DATABASE_TABLE, null, initialValues);
	}

	// ---ɾ����������---
	public void deleteAllItem() {
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
		db.execSQL(DATABASE_CREATE);
	}

	// ---ɾ��һ��ָ������---
	public boolean deleteItem(long rowId) {
		return db.delete(DATABASE_TABLE, KEY_ID + "=" + rowId, null) > 0;
	}

	// ---������������---

	public Cursor getAllItem() {
		return db.query(DATABASE_TABLE, new String[] { KEY_ID,
				KEY_YEARMONTH, KEY_QUESIONTYPE,KEY_QUESTIONNUMBER, KEY_PASSAGETEXT,
				KEY_QUESTIONSTARTNUMBER, KEY_QUESTIONENDNUMBER,
				KEY_QUESTIONTOTAL },
				null, null, null, null, null);
	}

	// ---����һ��ָ������---

	public Cursor getItem(long rowId) throws SQLException {
		Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {
				KEY_ID, KEY_YEARMONTH, KEY_QUESIONTYPE,KEY_QUESTIONNUMBER, KEY_PASSAGETEXT,
				KEY_QUESTIONSTARTNUMBER, KEY_QUESTIONENDNUMBER,
				KEY_QUESTIONTOTAL },
				KEY_ID + "=" + rowId, null, null, null, null, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor getItemFromYM(String YYYYMM) throws SQLException {
		Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {
				KEY_ID, KEY_YEARMONTH, KEY_QUESIONTYPE,KEY_QUESTIONNUMBER, KEY_PASSAGETEXT,
				KEY_QUESTIONSTARTNUMBER, KEY_QUESTIONENDNUMBER,
				KEY_QUESTIONTOTAL},
				KEY_YEARMONTH + "=" + YYYYMM, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	// ---����һ������---
	public boolean updateItem(long rowId,String YYYYMM, String QuestionType,
			String QuestionNumber, String PassageText, String QuestionStartNumber,
			String QusetionEndNumber, String QuestionTotal) {
		ContentValues args = new ContentValues();
		args.put(KEY_YEARMONTH, YYYYMM);
		args.put(KEY_QUESIONTYPE, QuestionType);
		args.put(KEY_PASSAGETEXT, QuestionNumber);
		args.put(KEY_PASSAGETEXT, PassageText);
		args.put(KEY_QUESTIONSTARTNUMBER, QuestionStartNumber);
		args.put(KEY_QUESTIONENDNUMBER, QusetionEndNumber);
		args.put(KEY_QUESTIONTOTAL, QuestionTotal);


		return db.update(DATABASE_TABLE, args, KEY_ID + "=" + rowId, null) > 0;
	}

	/**
	 * ����ID��ѯĳ����¼
	 * 
	 * @param id
	 * @return
	 */
	public Cursor findItem(Integer id) {
		Cursor cursor = db.rawQuery("select * from " + DATABASE_TABLE
				+ " where ID=?", new String[] { id.toString() });// Cursor �α��
																	// ResultSet
																	// ����
		if (cursor.moveToFirst()) {
			return cursor;
		}
		return null;
	}

	/**
	 * ������ݱ��Ƿ����
	 * 
	 * @return boolean
	 */
	public boolean checkTableExists(String tableName) {
		Log.v(TAG, "Activity State: checkSqlExists()");
		boolean result = false;
		if (tableName == null) {
			return false;
		}
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = this.DBHelper.getReadableDatabase();
			String sql = "select count(*) as c from Sqlite_master where type ='table' and name ='"
					+ tableName.trim() + "' ";
			cursor = db.rawQuery(sql, null);
			if (cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (count > 0) {
					result = true;
				}
			}
			cursor.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
}
