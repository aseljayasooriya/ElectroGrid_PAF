package main.java.com.model;

public class MeterReading {
	int id;
	int meterReaderId;
	String accountNo;
	int year;
	int month;
	int reading;
	int archived;

	public MeterReading() {}

	public MeterReading(int meterReaderId, String accountNo, int readYear, int readMonth, int archived) {
		this.meterReaderId = meterReaderId;
		this.accountNo = accountNo;
		this.year = readYear;
		this.month = readMonth;
		this.archived = archived;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMeterReaderId() {
		return meterReaderId;
	}

	@Override
	public String toString() {
		return "MeterReading{" +
				"meterReaderId=" + meterReaderId +
				", accountNo='" + accountNo + '\'' +
				", year=" + year +
				", month=" + month +
				", reading='" + reading + '\'' +
				'}';
	}

	public void setMeterReaderId(int meterReaderId) {
		this.meterReaderId = meterReaderId;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int readYear) {
		this.year = readYear;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int readMonth) {
		this.month = readMonth;
	}

	public int getReading() {
		return reading;
	}

	public void setReading(int reading) {
		this.reading = reading;
	}
}
