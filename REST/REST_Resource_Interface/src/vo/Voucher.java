package vo;

import java.util.Date;

public class Voucher {
	private long number;
	private int value;
	private Date expiry;

	public Voucher() {
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Date getExpiry() {
		return expiry;
	}

	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}

	@Override
	public String toString() {
		return "{voucher_no: " + number + ", value: " + value + ", expiry: "
				+ expiry + "}";
	}
}