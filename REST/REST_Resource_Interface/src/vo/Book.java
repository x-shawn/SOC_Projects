/**
 * 
 */
package vo;

import java.util.ArrayList;
import java.util.List;

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
	private double price;
	private int year;
	private String author;
	private String detail;
	private List<BookMark> bookmarks;
	
	public Book() {
		this.status = "new";
		bookmarks = new ArrayList<BookMark>();
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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

	public List<BookMark> getBookmarks() {
		return bookmarks;
	}

	public void setBookmarks(List<BookMark> bookmarks) {
		this.bookmarks = bookmarks;
	}

	public static Book copy(Book source) {
		if(source == null) {
			return null;
		}
		
		Book target = new Book();
		target.id = source.id;
		target.title = source.title;
		target.status = source.status;
		target.price = source.price;
		target.year = source.year;
		target.author = source.author;
		target.detail = source.detail;
		
		for(BookMark m : source.bookmarks){
			target.bookmarks.add(m.clone());
		}
		
		return target;
	}
}
