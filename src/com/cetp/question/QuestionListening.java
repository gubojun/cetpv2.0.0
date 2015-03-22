package com.cetp.question;

public class QuestionListening extends Question implements SelectQ {
	private String ID;
	private String YYYYMM;// 年月
	private String QuestionType;// 题目类型
	private String QuestionNumber;// 题目号
	private String SelectionA;
	private String SelectionB;
	private String SelectionC;
	private String SelectionD;
	private String Answer;
	private String Comments;

	public String getQNum() {
		return QuestionNumber;
	}

	public String getSelectionA() {
		return SelectionA;
	}

	public String getSelectionB() {
		return SelectionB;
	}

	public String getSelectionC() {
		return SelectionC;
	}

	public String getSelectionD() {
		return SelectionD;
	}

	public void getQNum(String newtext) {
		QuestionNumber = newtext;
	}

	@Override
	public void setSelectionA(String newselectionA) {
		// TODO Auto-generated method stub
		SelectionA = newselectionA;
	}

	@Override
	public void setSelectionB(String newselectionB) {
		// TODO Auto-generated method stub
		SelectionB = newselectionB;
	}

	@Override
	public void setSelectionC(String newselectionC) {
		// TODO Auto-generated method stub
		SelectionC = newselectionC;
	}

	@Override
	public void setSelectionD(String newselectionD) {
		// TODO Auto-generated method stub
		SelectionD = newselectionD;
	}
}
