package com.cetp.question;

public class Question {// abstract
	private long id;
	private String yyyymm;
	private String questionnumber;
	private String answer;
	private String comments;
	private String useranswer;

	// get...
	public long getID() {
		return id;
	}

	public String getYYYYMM() {
		return yyyymm;
	}

	public String getNumber() {
		return questionnumber;
	}

	public String getAnswer() {
		return answer;
	}

	public String getComments() {
		return comments;
	}

	public String getUserAnswer() {
		return useranswer;
	}

	// set...
	public void setID(long newid) {
		id = newid;
	}

	public void setYYYYMM(String newyyyymm) {
		yyyymm = newyyyymm;
	}

	public void getNumber(String newnumber) {
		questionnumber = newnumber;
	}

	public void getAnswer(String newanswer) {
		answer = newanswer;
	}

	public void getComments(String newcomments) {
		comments = newcomments;
	}

	public void getUserAnswer(String newuseranswer) {
		useranswer = newuseranswer;
	}
}
