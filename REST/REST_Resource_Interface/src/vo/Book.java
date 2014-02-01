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
	private String id;
	private String title;
	private String detail;
	private String status;

	public Book() {
		this.status = "new";
	}
	
	public Book(String id, String title, String detail) {
		this.id = id;
		this.title = title;
		this.detail = detail;
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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
