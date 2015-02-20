package com.cetp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	// 类没有实例化,是不能用作父类构造器的参数,必须声明为静态
	private static final String TAG = "DatabaseHelper";
	private static final String name = "cetp"; // 数据库名称
	private static final int version = 1; // 数据库版本
	// 听力数据表
	private static final String[] DATABASE_TABLE_LISTENING = {
			"Listening_Comprehension_Question",
			"Listening_Comprehension_Passage",
			"Listening_Comprehension_Conversation" };
	// 阅读数据表
	private static final String[] DATABASE_TABLE_READING = {
			"Reading_Comprehension_Question", "Reading_Comprehension_Passage" };
	// 完型数据表
	private static final String[] DATABASE_TABLE_CLOZING = {
			"Clozing_Question", "Clozing_Passage" };
	// 词汇数据表
	private static final String DATABASE_TABLE_VOCABULARY = "Vocabulary_and_Structure";
	// 创建听力表
	private static final String DATABASE_CREATE_LISTENING0 = "create table if not exists "
			+ DATABASE_TABLE_LISTENING[0]
			+ " (ID integer primary key autoincrement, "// 自增的
			+ "YYYYMM text not null, " // 年月
			+ "QuestionType text not null, "// 题目类型
			+ "QuestionNumber text not null, " // 题目号
			+ "SelectionA text not null, " // A选项
			+ "SelectionB text not null, " // B选项
			+ "SelectionC text not null, "// C选项
			+ "SelectionD text not null, " // D选项
			+ "Answer text not null, "// 答案
			+ "Comments text);";// 备注
	private static final String DATABASE_CREATE_LISTENING1 = "create table if not exists "
			+ DATABASE_TABLE_LISTENING[1]
			+ " (ID integer primary key autoincrement, "// 自增的
			+ "YYYYMM text not null, " // 年月
			+ "QuestionType text not null, "// 题目类型
			+ "QuestionNumber text not null, "// 题号
			+ "PassageText text not null, "// 题目文本
			+ "QuestionStartNumber text not null, "// 文本所做的开始的题
			+ "QuestionEndNumber text not null, "// 文本所做的结束的题
			+ "QuestionTotal text not null);";// 总共的题数
	private static final String DATABASE_CREATE_LISTENING2 = "create table if not exists "
			+ DATABASE_TABLE_LISTENING[2]
			+ " (ID integer primary key autoincrement, "// 自增的
			+ "YYYYMM text not null, " // 年月
			+ "QuestionType text not null, "// 题目类型
			+ "QuestionNumber text not null, "// 题号
			+ "QuestionText text not null); ";// 题目文本
	/******* Reading ********/
	private static final String DATABASE_CREATE_READING0 = "create table if not exists "
			+ DATABASE_TABLE_READING[0]
			+ " (ID integer primary key autoincrement, "// 自增的
			+ "YYYYMM text not null, " // 年月
			+ "QuestionType text not null, "// 题目类型
			+ "QuestionNumber text not null, " // 题目号
			+ "QuestionText text not null ," // 题目文本
			+ "SelectionA text not null, " // A选项
			+ "SelectionB text not null, " // B选项
			+ "SelectionC text not null, "// C选项
			+ "SelectionD text not null, " // D选项
			+ "Answer text not null, "// 答案
			+ "Comments text);";// 备注
	private static final String DATABASE_CREATE_READING1 = "create table if not exists "
			+ DATABASE_TABLE_READING[1]
			+ " (ID integer primary key autoincrement, "// 自增的
			+ "YYYYMM text not null, " // 年月
			+ "QuestionType text not null, "// 题目类型
			+ "QuestionNumber text not null, "// 题号
			+ "PassageText text not null, "// 题目文本
			+ "QuestionStartNumber text not null, "// 文本所做的开始的题
			+ "QuestionEndNumber text not null, "// 文本所做的结束的题
			+ "QuestionTotal text not null);";// 总共的题数
	// 创建完形表
	private static final String DATABASE_CREATE_CLOZING_QUESTION = "create table if not exists "
			+ DATABASE_TABLE_CLOZING[0]
			+ " (ID integer primary key autoincrement, "// 自增的
			+ "YYYYMM text not null, " // 年月
			+ "QuestionType text not null, "// 题目类型
			+ "QuestionNumber text not null, "// 题号
			+ "SelectionA text not null, " // A选项
			+ "SelectionB text not null, " // B选项
			+ "SelectionC text not null, "// C选项
			+ "SelectionD text not null, " // D选项
			+ "Answer text not null, "// 答案
			+ "Comments text);";// 备注
	private static final String DATABASE_CREATE_CLOZING_PASSAGE = "create table if not exists "
			+ DATABASE_TABLE_CLOZING[1]
			+ " (ID integer primary key autoincrement, "// 自增的
			+ "YYYYMM text not null, " // 年月
			+ "QuestionType text not null, "// 题目类型
			+ "PassageText text not null, "// 题目文本
			+ "QuestionStartNumber text not null, "// 文本所做的开始的题
			+ "QuestionEndNumber text not null, "// 文本所做的结束的题
			+ "QuestionTotal text not null);";// 总共的题数
	// 创建词汇表
	private static final String DATABASE_CREATE_VOCABULARY = "create table if not exists "
			+ DATABASE_TABLE_VOCABULARY // 表名
			+ " (ID integer primary key autoincrement, "// 自增的
			+ "YYYYMM text not null, " // 年月
			+ "QuestionType text not null, "// 题目类型
			+ "QuestionNumber text not null, " // 题目号
			+ "QuestionText text not null, "// 题目文本
			+ "SelectionA text not null, " // A选项
			+ "SelectionB text not null, " // B选项
			+ "SelectionC text not null, "// C选项
			+ "SelectionD text not null, " // D选项
			+ "Answer text not null, "// 答案
			+ "Comments text);";// 备注

	public DatabaseHelper(Context context) {
		// 第三个参数CursorFactory指定在执行查询时获得一个游标实例的工厂类,设置为null,代表使用系统默认的工厂类
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		/***** 建Listening的数据库表 *****/
		db.execSQL(DATABASE_CREATE_LISTENING0);
		db.execSQL(DATABASE_CREATE_LISTENING1);
		db.execSQL(DATABASE_CREATE_LISTENING2);
		/***** 建Reading的数据库表 *****/
		db.execSQL(DATABASE_CREATE_READING0);
		db.execSQL(DATABASE_CREATE_READING1);
		/***** 建Clozing的数据库表 *****/
		db.execSQL(DATABASE_CREATE_CLOZING_QUESTION);
		db.execSQL(DATABASE_CREATE_CLOZING_PASSAGE);
		/***** 建Vocabulary的数据库表 *****/
		db.execSQL(DATABASE_CREATE_VOCABULARY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		/***** 更新Listening的数据库 *****/
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_LISTENING[0]);
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_LISTENING[1]);
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_LISTENING[2]);
		/***** 更新Reading的数据库 *****/
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_READING[0]);
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_READING[1]);
		/***** 更新Clozing的数据库 *****/
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_CLOZING[0]);
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_CLOZING[1]);
		/***** 更新Vocabulary的数据库 *****/
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_VOCABULARY);
		onCreate(db);

		// db.execSQL(" ALTER TABLE person ADD phone VARCHAR(12) NULL "); //
		// 往表中增加一列
		// DROP TABLE IF EXISTS person 删除表
	}

	/**
	 * 删除数据库
	 * 
	 * @param context
	 * @return
	 */
	public boolean deleteDatabase(Context context) {
		return context.deleteDatabase(name);
	}
}
// 当数据库表结构发生更新时，应该避免用户存放于数据库中的数据丢失。
