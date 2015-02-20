package com.cetp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBCommon extends DatabaseHelper {
	public static final String TAG = "DBCommon";
	// private final Context context;
	// 类没有实例化,是不能用作父类构造器的参数,必须声明为静态
	// private static final String name = "cetp"; // 数据库名称
	// private static final int version = 1; // 数据库版本
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public DBCommon(Context context) {
		super(context);
	}

	/**
	 * 检查数据表是否存在
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

	// ---打开数据库---

	public DBCommon open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	// ---关闭数据库---
	public void close() {
		DBHelper.close();
	}

	// ---向数据库中插入一个数据---
	// public long insertItem(String YYYYMM, String QuestionType,
	// String QuestionNumber, String QuestionText) {
	// ContentValues initialValues = new ContentValues();
	// // 赋初值
	// initialValues.put(KEY_2, YYYYMM);
	// initialValues.put(KEY_3, QuestionType);
	// initialValues.put(KEY_4, QuestionNumber);
	// initialValues.put(KEY_5, QuestionText);
	// return db.insert(DATABASE_TABLE, null, initialValues);
	// }

	// ---删除所有数据---
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

	// ---删除一个指定数据---
	// public boolean deleteItem(long rowId) {
	// return db.delete(DATABASE_TABLE, KEY_ID + "=" + rowId, null) > 0;
	// }

	// ---检索所有数据---

	// public Cursor getAllItem() {
	// return db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_YEARMONTH,
	// KEY_QUESIONTYPE, KEY_QUESTIONNUMBER, KEY_QUESTIONTEXT }, null,
	// null, null, null, null);
	// }

	// ---检索一个指定数据---

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

	// ---更新一个数据---
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
	 * 根据ID查询某条记录
	 * 
	 * @param id
	 * @return
	 */
	// public Cursor findItem(Integer id) {
	// Cursor cursor = db.rawQuery("select * from " + DATABASE_TABLE
	// + " where ID=?", new String[] { id.toString() });
	// Cursor 游标和ResultSet很像
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
