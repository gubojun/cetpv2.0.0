package com.cetp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBListeningOfConversation {
	public static final String KEY_ID = "ID";// id
	public static final String KEY_YEARMONTH = "YYYYMM";// 年月
	public static final String KEY_QUESIONTYPE = "QuestionType";// 题目类型
	public static final String KEY_QUESTIONNUMBER = "QuestionNumber";// 题号
	public static final String KEY_QUESTIONTEXT = "QuestionText";// 题目文本

	private static final String TAG = "DBListening";
	private static final String DATABASE_TABLE = "Listening_Comprehension_Conversation";
	private static final String DATABASE_CREATE = "create table if not exists "
			+ DATABASE_TABLE + " (ID integer primary key autoincrement, "// 自增的
			+ "YYYYMM text not null, " // 年月
			+ "QuestionType text not null, "// 题目类型
			+ "QuestionNumber text not null, "// 题号
			+ "QuestionText text not null); ";// 题目文本
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public DBListeningOfConversation(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	// ---打开数据库---

	public DBListeningOfConversation open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	public String getDatabaseName() {
		return DATABASE_TABLE;
	}

	// ---关闭数据库---
	public void close() {
		DBHelper.close();
	}

	// ---向数据库中插入一个数据---
	public long insertItem(String YYYYMM, String QuestionType,
			String QuestionNumber, String QuestionText) {
		ContentValues initialValues = new ContentValues();
		// 赋初值
		initialValues.put(KEY_YEARMONTH, YYYYMM);
		initialValues.put(KEY_QUESIONTYPE, QuestionType);
		initialValues.put(KEY_QUESTIONNUMBER, QuestionNumber);
		initialValues.put(KEY_QUESTIONTEXT, QuestionText);
		return db.insert(DATABASE_TABLE, null, initialValues);
	}

	// ---删除所有数据---
	public void deleteAllItem() {
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
		db.execSQL(DATABASE_CREATE);
	}

	// ---删除一个指定数据---
	public boolean deleteItem(long rowId) {
		return db.delete(DATABASE_TABLE, KEY_ID + "=" + rowId, null) > 0;
	}

	// ---检索所有数据---

	public Cursor getAllItem() {
		return db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_YEARMONTH,
				KEY_QUESIONTYPE, KEY_QUESTIONNUMBER, KEY_QUESTIONTEXT, }, null,
				null, null, null, null);
	}

	// ---检索一个指定数据---

	public Cursor getItem(long rowId) throws SQLException {
		Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] { KEY_ID,
				KEY_YEARMONTH, KEY_QUESIONTYPE, KEY_QUESTIONNUMBER,
				KEY_QUESTIONTEXT, }, KEY_ID + "=" + rowId, null, null, null,
				null, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor getItemFromYM(String YYYYMM) throws SQLException {
		Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] { KEY_ID,
				KEY_YEARMONTH, KEY_QUESIONTYPE, KEY_QUESTIONNUMBER,
				KEY_QUESTIONTEXT, }, KEY_YEARMONTH + "=" + YYYYMM, null, null,
				null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	// ---更新一个数据---
	public boolean updateItem(long rowId, String YYYYMM, String QuestionType,
			String QuestionNumber, String QuestionText) {
		ContentValues args = new ContentValues();
		args.put(KEY_YEARMONTH, YYYYMM);
		args.put(KEY_QUESIONTYPE, QuestionType);
		args.put(KEY_QUESTIONTEXT, QuestionNumber);
		args.put(KEY_QUESTIONTEXT, QuestionText);

		return db.update(DATABASE_TABLE, args, KEY_ID + "=" + rowId, null) > 0;
	}

	/**
	 * 根据ID查询某条记录
	 * 
	 * @param id
	 * @return
	 */
	public Cursor findItem(Integer id) {
		Cursor cursor = db.rawQuery("select * from " + DATABASE_TABLE
				+ " where ID=?", new String[] { id.toString() });// Cursor 游标和
																	// ResultSet
																	// 很像
		if (cursor.moveToFirst()) {
			return cursor;
		}
		return null;
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
