package com.cetp.database;

import java.util.Date;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

@Table(name = "favorite_clozing_q")
public class TableClozingOfQuestionFavorite {
	@Id(column = "ID")
	private String ID;// id
	private String YYYYMM;// 年月
	private String QuestionType;// 题目类型
	private String QuestionNumber;// 题号
	private String SelectionA;
	private String SelectionB;
	private String SelectionC;
	private String SelectionD;
	private String Answer;
	private String Comments;
	private Date date;

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

	/**
	 * @return the selectionA
	 */
	public String getSelectionA() {
		return SelectionA;
	}

	/**
	 * @param selectionA
	 *            the selectionA to set
	 */
	public void setSelectionA(String selectionA) {
		SelectionA = selectionA;
	}

	/**
	 * @return the selectionB
	 */
	public String getSelectionB() {
		return SelectionB;
	}

	/**
	 * @param selectionB
	 *            the selectionB to set
	 */
	public void setSelectionB(String selectionB) {
		SelectionB = selectionB;
	}

	/**
	 * @return the selectionC
	 */
	public String getSelectionC() {
		return SelectionC;
	}

	/**
	 * @param selectionC
	 *            the selectionC to set
	 */
	public void setSelectionC(String selectionC) {
		SelectionC = selectionC;
	}

	/**
	 * @return the selectionD
	 */
	public String getSelectionD() {
		return SelectionD;
	}

	/**
	 * @param selectionD
	 *            the selectionD to set
	 */
	public void setSelectionD(String selectionD) {
		SelectionD = selectionD;
	}

	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return Answer;
	}

	/**
	 * @param answer
	 *            the answer to set
	 */
	public void setAnswer(String answer) {
		Answer = answer;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return Comments;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(String comments) {
		Comments = comments;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
}
