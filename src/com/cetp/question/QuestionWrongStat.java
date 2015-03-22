package com.cetp.question;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

@Table(name = "Wrong_Stat")
public class QuestionWrongStat {
	@Id(column = "ID")
	private String ID;
	private String YYYYMMDDHHMMSS;
	private String Wrong;
	private String Total;
	private String WrongStat;

	public String getWrong() {
		return Wrong;
	}

	public void setWrong(String wrong) {
		Wrong = wrong;
	}

	public String getYYYYMMDDHHMMSS() {
		return YYYYMMDDHHMMSS;
	}

	public void setYYYYMMDDHHMMSS(String yYYYMMDDHHMMSS) {
		YYYYMMDDHHMMSS = yYYYMMDDHHMMSS;
	}

	public String getTotal() {
		return Total;
	}

	public void setTotal(String total) {
		Total = total;
	}

	public String getWrongStat() {
		return WrongStat;
	}

	public void setWrongStat(String wrongStat) {
		WrongStat = wrongStat;
	}
}
