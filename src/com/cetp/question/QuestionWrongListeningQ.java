package com.cetp.question;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

@Table(name = "wrong_listening_q")
public class QuestionWrongListeningQ {
	@Id(column = "ID")
	private String ID;
	private String YYYYMM;// 年月
	private String QuestionType;// 题目类型
	private String QuestionNumber;// 题号
	private String SelectionA;
	private String SelectionB;
	private String SelectionC;
	private String SelectionD;
	private String Answer;
	private String Comments;
	private String time;

	/**
	 * @return the yYYYMM
	 */
	public String getYYYYMM() {
		return YYYYMM;
	}

	/**
	 * @param yYYYMM
	 *            the yYYYMM to set
	 */
	public void setYYYYMM(String yYYYMM) {
		YYYYMM = yYYYMM;
	}

	/**
	 * @return the questionType
	 */
	public String getQuestionType() {
		return QuestionType;
	}

	/**
	 * @param questionType
	 *            the questionType to set
	 */
	public void setQuestionType(String questionType) {
		QuestionType = questionType;
	}

	/**
	 * @return the questionNumber
	 */
	public String getQuestionNumber() {
		return QuestionNumber;
	}

	/**
	 * @param questionNumber
	 *            the questionNumber to set
	 */
	public void setQuestionNumber(String questionNumber) {
		QuestionNumber = questionNumber;
	}
}
