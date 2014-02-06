package vo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Customer {
	private long id;
	private String name;
	private double balance;
	private String level;
	
	private List<Book> purchaseList;
	private List<Voucher> voucherList;

	public Customer() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public List<Book> getPurchaseList() {
		return purchaseList;
	}

	public void setPurchaseList(List<Book> purchaseList) {
		this.purchaseList = purchaseList;
	}

	public List<Voucher> getVoucherList() {
		return voucherList;
	}

	public void setVoucherList(List<Voucher> voucherList) {
		this.voucherList = voucherList;
	}

	@Override
	public String toString() {
		return "{id: " + id + ", name: " + name + ", level: "
				+ level + "}";
	}
}