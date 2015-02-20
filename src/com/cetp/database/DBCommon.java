package com.cetp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBCommon extends DatabaseHelper {
	public static final String TAG = "DBCommon";
	// private final Context context;
	// ��û��ʵ����,�ǲ����������๹�����Ĳ���,��������Ϊ��̬
	// private static final String name = "cetp"; // ���ݿ�����
	// private static final int version = 1; // ���ݿ�汾
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public DBCommon(Context context) {
		super(context);
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
			db = this.getReadableDatabase();
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

	// ---�����ݿ�---

	public DBCommon open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	// ---�ر����ݿ�---
	public void close() {
		DBHelper.close();
	}

	// ---�����ݿ��в���һ������---
	// public long insertItem(String YYYYMM, String QuestionType,
	// String QuestionNumber, String QuestionText) {
	// ContentValues initialValues = new ContentValues();
	// // ����ֵ
	// initialValues.put(KEY_2, YYYYMM);
	// initialValues.put(KEY_3, QuestionType);
	// initialValues.put(KEY_4, QuestionNumber);
	// initialValues.put(KEY_5, QuestionText);
	// return db.insert(DATABASE_TABLE, null, initialValues);
	// }

	// ---ɾ����������---
	public void deleteAllItem(String DATABASE_TABLE) {
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
		// db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	// ---ɾ��һ��ָ������---
	// public boolean deleteItem(long rowId) {
	// return db.delete(DATABASE_TABLE, KEY_ID + "=" + rowId, null) > 0;
	// }

	// ---������������---

	// public Cursor getAllItem() {
	// return db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_YEARMONTH,
	// KEY_QUESIONTYPE, KEY_QUESTIONNUMBER, KEY_QUESTIONTEXT }, null,
	// null, null, null, null);
	// }

	// ---����һ��ָ������---

	// public Cursor getItem(long rowId) throws SQLException {
	// Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] { KEY_ID,
	// KEY_YEARMONTH, KEY_QUESIONTYPE, KEY_QUESTIONNUMBER,
	// KEY_QUESTIONTEXT }, KEY_ID + "=" + rowId, null, null, null,
	// null, null);
	//
	// if (mCursor != null) {
	// mCursor.moveToFirst();
	// }
	// return mCursor;
	// }

	// ---����һ������---
	// public boolean updateItem(long rowId, String YYYYMM, String QuestionType,
	// String QuestionNumber, String QuestionText) {
	// ContentValues args = new ContentValues();
	// args.put(KEY_YEARMONTH, YYYYMM);
	// args.put(KEY_QUESIONTYPE, QuestionType);
	// args.put(KEY_QUESTIONNUMBER, QuestionNumber);
	// args.put(KEY_QUESTIONTEXT, QuestionText);
	//
	// return db.update(DATABASE_TABLE, args, KEY_ID + "=" + rowId, null) > 0;
	// }

	/**
	 * ����ID��ѯĳ����¼
	 * 
	 * @param id
	 * @return
	 */
	// public Cursor findItem(Integer id) {
	// Cursor cursor = db.rawQuery("select * from " + DATABASE_TABLE
	// + " where ID=?", new String[] { id.toString() });
	// Cursor �α��ResultSet����
	// if (cursor.moveToFirst()) {
	// Move the cursor to the first row. This
	// method will return false if the cursor is
	// empty.
	// int rowId = cursor.getInt(cursor.getColumnIndex("ID"));
	// String yearmonth = cursor
	// .getString(cursor.getColumnIndex("YYYYMM"));
	// String phone = cursor.getString(cursor.getColumnIndex("phone"));
	// return cursor;
	// }
	// return null;
	// }
}
