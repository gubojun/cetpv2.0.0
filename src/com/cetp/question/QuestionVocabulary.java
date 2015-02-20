package com.cetp.question;

public class QuestionVocabulary extends Question implements SelectQ{
	private String questiontext;
	private String selectionA;
	private String selectionB;
	private String selectionC;
	private String selectionD;

	public String getText() {
		return questiontext;
	}

	public String getSelectionA() {
		return selectionA;
	}

	public String getSelectionB() {
		return selectionB;
	}

	public String getSelectionC() {
		return selectionC;
	}

	public String getSelectionD() {
		return selectionD;
	}

	public void getText(String newtext) {
		questiontext = newtext;
	}

	@Override
	public void setSelectionA(String newselectionA) {
		// TODO Auto-generated method stub
		selectionA = newselectionA;
	}

	@Override
	public void setSelectionB(String newselectionB) {
		// TODO Auto-generated method stub
		selectionB = newselectionB;
	}

	@Override
	public void setSelectionC(String newselectionC) {
		// TODO Auto-generated method stub
		selectionC = newselectionC;
	}

	@Override
	public void setSelectionD(String newselectionD) {
		// TODO Auto-generated method stub
		selectionD = newselectionD;
	}
}
