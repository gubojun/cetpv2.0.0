package com.cetp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	// ��û��ʵ����,�ǲ����������๹�����Ĳ���,��������Ϊ��̬
	private static final String TAG = "DatabaseHelper";
	private static final String name = "cetp"; // ���ݿ�����
	private static final int version = 1; // ���ݿ�汾
	// �������ݱ�
	private static final String[] DATABASE_TABLE_LISTENING = {
			"Listening_Comprehension_Question",
			"Listening_Comprehension_Passage",
			"Listening_Comprehension_Conversation" };
	// �Ķ����ݱ�
	private static final String[] DATABASE_TABLE_READING = {
			"Reading_Comprehension_Question", "Reading_Comprehension_Passage" };
	// �������ݱ�
	private static final String[] DATABASE_TABLE_CLOZING = {
			"Clozing_Question", "Clozing_Passage" };
	// �ʻ����ݱ�
	private static final String DATABASE_TABLE_VOCABULARY = "Vocabulary_and_Structure";
	// ����������
	private static final String DATABASE_CREATE_LISTENING0 = "create table if not exists "
			+ DATABASE_TABLE_LISTENING[0]
			+ " (ID integer primary key autoincrement, "// ������
			+ "YYYYMM text not null, " // ����
			+ "QuestionType text not null, "// ��Ŀ����
			+ "QuestionNumber text not null, " // ��Ŀ��
			+ "SelectionA text not null, " // Aѡ��
			+ "SelectionB text not null, " // Bѡ��
			+ "SelectionC text not null, "// Cѡ��
			+ "SelectionD text not null, " // Dѡ��
			+ "Answer text not null, "// ��
			+ "Comments text);";// ��ע
	private static final String DATABASE_CREATE_LISTENING1 = "create table if not exists "
			+ DATABASE_TABLE_LISTENING[1]
			+ " (ID integer primary key autoincrement, "// ������
			+ "YYYYMM text not null, " // ����
			+ "QuestionType text not null, "// ��Ŀ����
			+ "QuestionNumber text not null, "// ���
			+ "PassageText text not null, "// ��Ŀ�ı�
			+ "QuestionStartNumber text not null, "// �ı������Ŀ�ʼ����
			+ "QuestionEndNumber text not null, "// �ı������Ľ�������
			+ "QuestionTotal text not null);";// �ܹ�������
	private static final String DATABASE_CREATE_LISTENING2 = "create table if not exists "
			+ DATABASE_TABLE_LISTENING[2]
			+ " (ID integer primary key autoincrement, "// ������
			+ "YYYYMM text not null, " // ����
			+ "QuestionType text not null, "// ��Ŀ����
			+ "QuestionNumber text not null, "// ���
			+ "QuestionText text not null); ";// ��Ŀ�ı�
	/******* Reading ********/
	private static final String DATABASE_CREATE_READING0 = "create table if not exists "
			+ DATABASE_TABLE_READING[0]
			+ " (ID integer primary key autoincrement, "// ������
			+ "YYYYMM text not null, " // ����
			+ "QuestionType text not null, "// ��Ŀ����
			+ "QuestionNumber text not null, " // ��Ŀ��
			+ "QuestionText text not null ," // ��Ŀ�ı�
			+ "SelectionA text not null, " // Aѡ��
			+ "SelectionB text not null, " // Bѡ��
			+ "SelectionC text not null, "// Cѡ��
			+ "SelectionD text not null, " // Dѡ��
			+ "Answer text not null, "// ��
			+ "Comments text);";// ��ע
	private static final String DATABASE_CREATE_READING1 = "create table if not exists "
			+ DATABASE_TABLE_READING[1]
			+ " (ID integer primary key autoincrement, "// ������
			+ "YYYYMM text not null, " // ����
			+ "QuestionType text not null, "// ��Ŀ����
			+ "QuestionNumber text not null, "// ���
			+ "PassageText text not null, "// ��Ŀ�ı�
			+ "QuestionStartNumber text not null, "// �ı������Ŀ�ʼ����
			+ "QuestionEndNumber text not null, "// �ı������Ľ�������
			+ "QuestionTotal text not null);";// �ܹ�������
	// �������α�
	private static final String DATABASE_CREATE_CLOZING_QUESTION = "create table if not exists "
			+ DATABASE_TABLE_CLOZING[0]
			+ " (ID integer primary key autoincrement, "// ������
			+ "YYYYMM text not null, " // ����
			+ "QuestionType text not null, "// ��Ŀ����
			+ "QuestionNumber text not null, "// ���
			+ "SelectionA text not null, " // Aѡ��
			+ "SelectionB text not null, " // Bѡ��
			+ "SelectionC text not null, "// Cѡ��
			+ "SelectionD text not null, " // Dѡ��
			+ "Answer text not null, "// ��
			+ "Comments text);";// ��ע
	private static final String DATABASE_CREATE_CLOZING_PASSAGE = "create table if not exists "
			+ DATABASE_TABLE_CLOZING[1]
			+ " (ID integer primary key autoincrement, "// ������
			+ "YYYYMM text not null, " // ����
			+ "QuestionType text not null, "// ��Ŀ����
			+ "PassageText text not null, "// ��Ŀ�ı�
			+ "QuestionStartNumber text not null, "// �ı������Ŀ�ʼ����
			+ "QuestionEndNumber text not null, "// �ı������Ľ�������
			+ "QuestionTotal text not null);";// �ܹ�������
	// �����ʻ��
	private static final String DATABASE_CREATE_VOCABULARY = "create table if not exists "
			+ DATABASE_TABLE_VOCABULARY // ����
			+ " (ID integer primary key autoincrement, "// ������
			+ "YYYYMM text not null, " // ����
			+ "QuestionType text not null, "// ��Ŀ����
			+ "QuestionNumber text not null, " // ��Ŀ��
			+ "QuestionText text not null, "// ��Ŀ�ı�
			+ "SelectionA text not null, " // Aѡ��
			+ "SelectionB text not null, " // Bѡ��
			+ "SelectionC text not null, "// Cѡ��
			+ "SelectionD text not null, " // Dѡ��
			+ "Answer text not null, "// ��
			+ "Comments text);";// ��ע

	public DatabaseHelper(Context context) {
		// ����������CursorFactoryָ����ִ�в�ѯʱ���һ���α�ʵ���Ĺ�����,����Ϊnull,����ʹ��ϵͳĬ�ϵĹ�����
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		/***** ��Listening�����ݿ�� *****/
		db.execSQL(DATABASE_CREATE_LISTENING0);
		db.execSQL(DATABASE_CREATE_LISTENING1);
		db.execSQL(DATABASE_CREATE_LISTENING2);
		/***** ��Reading�����ݿ�� *****/
		db.execSQL(DATABASE_CREATE_READING0);
		db.execSQL(DATABASE_CREATE_READING1);
		/***** ��Clozing�����ݿ�� *****/
		db.execSQL(DATABASE_CREATE_CLOZING_QUESTION);
		db.execSQL(DATABASE_CREATE_CLOZING_PASSAGE);
		/***** ��Vocabulary�����ݿ�� *****/
		db.execSQL(DATABASE_CREATE_VOCABULARY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		/***** ����Listening�����ݿ� *****/
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_LISTENING[0]);
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_LISTENING[1]);
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_LISTENING[2]);
		/***** ����Reading�����ݿ� *****/
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_READING[0]);
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_READING[1]);
		/***** ����Clozing�����ݿ� *****/
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_CLOZING[0]);
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_CLOZING[1]);
		/***** ����Vocabulary�����ݿ� *****/
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_VOCABULARY);
		onCreate(db);

		// db.execSQL(" ALTER TABLE person ADD phone VARCHAR(12) NULL "); //
		// ����������һ��
		// DROP TABLE IF EXISTS person ɾ����
	}

	/**
	 * ɾ�����ݿ�
	 * 
	 * @param context
	 * @return
	 */
	public boolean deleteDatabase(Context context) {
		return context.deleteDatabase(name);
	}
}
// �����ݿ��ṹ��������ʱ��Ӧ�ñ����û���������ݿ��е����ݶ�ʧ��
