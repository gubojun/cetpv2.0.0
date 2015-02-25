package com.cetp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBListeningOfQuestion {
	public static final String KEY_ID = "ID";// id
	public static final String KEY_YEARMONTH = "YYYYMM";// ����
	public static final String KEY_QUESIONTYPE = "QuestionType";// ��Ŀ����
	public static final String KEY_QUESTIONNUMBER = "QuestionNumber";// ���
	public static final String KEY_SELECTIONA = "SelectionA";
	public static final String KEY_SELECTIONB = "SelectionB";
	public static final String KEY_SELECTIONC = "SelectionC";
	public static final String KEY_SELECTIOND = "SelectionD";
	public static final String KEY_ANSWER = "Answer";
	public static final String KEY_COMMENTS = "Comments";

	private static final String TAG = "DBListening";
	// private static final String DATABASE_NAME = "cetp";
	private static final String DATABASE_TABLE = "Listening_Comprehension_Question";

	// private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_CREATE = "create table if not exists "
			+ DATABASE_TABLE + " (ID integer primary key autoincrement, "// ������
			+ "YYYYMM text not null, " // ����
			+ "QuestionType text not null, "// ��Ŀ����
			+ "QuestionNumber text not null, " // ��Ŀ��
			+ "SelectionA text not null, " // Aѡ��
			+ "SelectionB text not null, " // Bѡ��
			+ "SelectionC text not null, "// Cѡ��
			+ "SelectionD text not null, " // Dѡ��
			+ "Answer text not null, "// ��
			+ "Comments text);";// ��ע
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public DBListeningOfQuestion(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	public String getDatabaseName() {
		return DATABASE_TABLE;
	}

	// private static class DatabaseHelper extends SQLiteOpenHelper {
	// DatabaseHelper(Context context) {
	// super(context, DATABASE_NAME, null, DATABASE_VERSION);
	// }
	//
	// @Override
	// public void onCreate(SQLiteDatabase db) {
	// db.execSQL(DATABASE_CREATE);
	// }
	//
	// @Override
	// public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	// {
	// Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
	// + newVersion + ", which will destroy all old data");
	// db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
	// onCreate(db);
	// }
	// }

	// ---�����ݿ�---

	public DBListeningOfQuestion open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	// ---�ر����ݿ�---
	public void close() {
		DBHelper.close();
	}

	// ---�����ݿ��в���һ������---
	public long insertItem(String YYYYMM, String QuestionType,
			String QuestionNumber, String SelectionA, String SelectionB,
			String SelectionC, String SelectionD, String Answer, String Comments) {
		ContentValues initialValues = new ContentValues();
		// ����ֵ
		initialValues.put(KEY_YEARMONTH, YYYYMM);
		initialValues.put(KEY_QUESIONTYPE, QuestionType);
		initialValues.put(KEY_QUESTIONNUMBER, QuestionNumber);
		initialValues.put(KEY_SELECTIONA, SelectionA);
		initialValues.put(KEY_SELECTIONB, SelectionB);
		initialValues.put(KEY_SELECTIONC, SelectionC);
		initialValues.put(KEY_SELECTIOND, SelectionD);
		initialValues.put(KEY_ANSWER, Answer);
		initialValues.put(KEY_COMMENTS, Comments);
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

	// public Cursor getAllItem2(){
	// return db.rawQuery("select * from "+DATABASE_TABLE, null);
	// }
	// ---������������---

	public Cursor getAllItem() {
		return db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_YEARMONTH,
				KEY_QUESIONTYPE, KEY_QUESTIONNUMBER, KEY_SELECTIONA,
				KEY_SELECTIONB, KEY_SELECTIONC, KEY_SELECTIOND, KEY_ANSWER,
				KEY_COMMENTS }, null, null, null, null, null);
	}

	// ---����һ��ָ������---

	public Cursor getItem(long rowId) throws SQLException {
		Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] { KEY_ID,
				KEY_YEARMONTH, KEY_QUESIONTYPE, KEY_QUESTIONNUMBER,
				KEY_SELECTIONA, KEY_SELECTIONB, KEY_SELECTIONC, KEY_SELECTIOND,
				KEY_ANSWER, KEY_COMMENTS }, KEY_ID + "=" + rowId, null, null,
				null, null, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor getItemFromYM(String YYYYMM) throws SQLException {
		Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] { KEY_ID,
				KEY_YEARMONTH, KEY_QUESIONTYPE, KEY_QUESTIONNUMBER,
				KEY_SELECTIONA, KEY_SELECTIONB, KEY_SELECTIONC, KEY_SELECTIOND,
				KEY_ANSWER, KEY_COMMENTS }, KEY_YEARMONTH + "=" + YYYYMM, null,
				null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	// ---����һ������---
	public boolean updateItem(long rowId, String YYYYMM, String QuestionType,
			String QuestionNumber, String SelectionA, String SelectionB,
			String SelectionC, String SelectionD, String Answer, String Comments) {
		ContentValues args = new ContentValues();
		args.put(KEY_YEARMONTH, YYYYMM);
		args.put(KEY_QUESIONTYPE, QuestionType);
		args.put(KEY_QUESTIONNUMBER, QuestionNumber);
		args.put(KEY_SELECTIONA, SelectionA);
		args.put(KEY_SELECTIONB, SelectionB);
		args.put(KEY_SELECTIONC, SelectionC);
		args.put(KEY_SELECTIOND, SelectionD);
		args.put(KEY_ANSWER, Answer);
		args.put(KEY_COMMENTS, Comments);

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
		if (cursor.moveToFirst()) {// Move the cursor to the first row. This
									// method will return false if the cursor is
									// empty.
			// int rowId = cursor.getInt(cursor.getColumnIndex("ID"));
			// String yearmonth = cursor
			// .getString(cursor.getColumnIndex("YYYYMM"));
			// String phone = cursor.getString(cursor.getColumnIndex("phone"));
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
