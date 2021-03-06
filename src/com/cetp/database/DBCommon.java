package com.cetp.database;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalDb.DaoConfig;
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
	public static boolean isListeningOfQuestion, isListeningOfText,
			isListeningOfConversation;
	public static boolean isClozingOfQuestion, isClozingOfText;
	public static boolean isReadingOfQuestion, isReadingOfPassage;
	public static boolean isVocabulary;
	final private static int WRONG = 4;

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

	/** 0听力 1完型 2阅读 3词汇 **/
	public static boolean checkDB(int index, Context context, String YM) {
		boolean result = false;
		if (index == 0) {
			isListeningOfQuestion = checkDBListeningOfQuestion(context, YM);
			isListeningOfText = checkDBListeningOfText(context, YM);
			isListeningOfConversation = checkDBListeningOfConversation(context,
					YM);
			result = isListeningOfQuestion & isListeningOfText
					& isListeningOfConversation;
		} else if (index == 1) {
			isClozingOfQuestion = checkDBClozingOfQuestion(context, YM);
			isClozingOfText = checkDBClozingOfText(context, YM);
			result = isClozingOfQuestion & isClozingOfText;
		} else if (index == 2) {
			isReadingOfQuestion = checkDBReadingOfQuestion(context, YM);
			isReadingOfPassage = checkDBReadingOfPassage(context, YM);
			result = isReadingOfPassage & isReadingOfQuestion;
		} else if (index == 3) {
			isVocabulary = checkDBVocabulary(context, YM);
			result = isVocabulary;
		}
		return result;
	}

	public static boolean checkDBListeningOfQuestion(Context context, String YM) {
		boolean result = false;
		DBListeningOfQuestion db = new DBListeningOfQuestion(context);
		db.open();
		if (db.checkTableExists(db.getDatabaseName())) {
			System.out.println("checkDB:Table(" + db.getDatabaseName()
					+ ") exist");
			Cursor cur;// 结果集
			cur = db.getItemFromYM(YM);
			if (cur.getCount() == 0) {
				Log.v("MainView", "no data in " + db.getDatabaseName());
				result = false;
			} else
				result = true;
			cur.close();
		}
		db.close();
		return result;
	}

	public static boolean checkDBListeningOfText(Context context, String YM) {
		boolean result = false;
		DBListeningOfText db = new DBListeningOfText(context);
		db.open();
		if (db.checkTableExists(db.getDatabaseName())) {
			System.out.println("checkDB:Table(" + db.getDatabaseName()
					+ ") exist");
			Cursor cur;// 结果集
			cur = db.getItemFromYM(YM);
			if (cur.getCount() == 0) {
				Log.v("MainView", "no data in " + db.getDatabaseName());
				result = false;
			} else
				result = true;
			cur.close();
		}
		db.close();
		return result;
	}

	public static boolean checkDBListeningOfConversation(Context context,
			String YM) {
		boolean result = false;
		DBListeningOfConversation db = new DBListeningOfConversation(context);
		db.open();
		if (db.checkTableExists(db.getDatabaseName())) {
			System.out.println("checkDB:Table(" + db.getDatabaseName()
					+ ") exist");
			Cursor cur;// 结果集
			cur = db.getItemFromYM(YM);
			if (cur.getCount() == 0) {
				Log.v("MainView", "no data in " + db.getDatabaseName());
				result = false;
			} else
				result = true;
			cur.close();
		}
		db.close();
		return result;
	}

	public static boolean checkDBClozingOfQuestion(Context context, String YM) {
		boolean result = false;
		DBClozingOfQuestion db = new DBClozingOfQuestion(context);
		db.open();
		if (db.checkTableExists(db.getDatabaseName())) {
			System.out.println("checkDB:Table(" + db.getDatabaseName()
					+ ") exist");
			Cursor cur;// 结果集
			cur = db.getItemFromYM(YM);
			if (cur.getCount() == 0) {
				Log.v("MainView", "no data in " + db.getDatabaseName());
				result = false;
			} else
				result = true;
			cur.close();
		}
		db.close();
		return result;
	}

	public static boolean checkDBClozingOfText(Context context, String YM) {
		boolean result = false;
		DBClozingOfText db = new DBClozingOfText(context);
		db.open();
		if (db.checkTableExists(db.getDatabaseName())) {
			System.out.println("checkDB:Table(" + db.getDatabaseName()
					+ ") exist");
			Cursor cur;// 结果集
			cur = db.getItemFromYM(YM);
			if (cur.getCount() == 0) {
				Log.v("MainView", "no data in " + db.getDatabaseName());
				result = false;
			} else
				result = true;
			cur.close();
		}
		db.close();
		return result;
	}

	public static boolean checkDBReadingOfQuestion(Context context, String YM) {
		boolean result = false;
		DBReadingOfQuestion db = new DBReadingOfQuestion(context);
		db.open();
		if (db.checkTableExists(db.getDatabaseName())) {
			System.out.println("checkDB:Table(" + db.getDatabaseName()
					+ ") exist");
			Cursor cur;// 结果集
			cur = db.getItemFromYM(YM);
			if (cur.getCount() == 0) {
				Log.v("MainView", "no data in " + db.getDatabaseName());
				result = false;
			} else
				result = true;
			cur.close();
		}
		db.close();
		return result;
	}

	public static boolean checkDBReadingOfPassage(Context context, String YM) {
		boolean result = false;
		DBReadingOfPassage db = new DBReadingOfPassage(context);
		db.open();
		if (db.checkTableExists(db.getDatabaseName())) {
			System.out.println("checkDB:Table(" + db.getDatabaseName()
					+ ") exist");
			Cursor cur;// 结果集
			cur = db.getItemFromYM(YM);
			if (cur.getCount() == 0) {
				Log.v("MainView", "no data in " + db.getDatabaseName());
				result = false;
			} else
				result = true;
			cur.close();
		}
		db.close();
		return result;
	}

	public static boolean checkDBVocabulary(Context context, String YM) {
		boolean result = false;
		DBVocabulary db = new DBVocabulary(context);
		db.open();
		if (db.checkTableExists(db.getDatabaseName())) {
			System.out.println("checkDB:Table(" + db.getDatabaseName()
					+ ") exist");
			Cursor cur;// 结果集
			cur = db.getItemFromYM(YM);
			if (cur.getCount() == 0) {
				Log.v("MainView", "no data in " + db.getDatabaseName());
				result = false;
			} else
				result = true;
			cur.close();
		}
		db.close();
		return result;
	}
}
