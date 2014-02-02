/**
 * 
 */
package vo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * VO demo
 */
@XmlRootElement
public class Book {
	// compulsory attributes
	private String id;
	private String title;
	private String status;
	// optional attributes
	private int year;
	private String author;
	private String detail;

	public Book() {
		this.status = "new";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
}
