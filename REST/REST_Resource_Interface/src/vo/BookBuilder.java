/**
 * 
 */
package vo;

/**
 * Builder Pattern Demo
 */
public class BookBuilder {
	// compulsory attributes
	private String id;
	private String title;
	
	// optional attributes
	private double price;
	private int year;
	private String author;
	private String detail;
	private String status;

	public BookBuilder(String id, String title) {
		//before instantiate the compulsory attributes, it could validate them ...
		if(id != null && !id.isEmpty() && title != null && !title.isEmpty()){
			this.id = id;
			this.title = title;
		}else{
			throw new IllegalArgumentException("Cannot instantiate the builder because of the illegal arguments");
		}
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public BookBuilder price(double price) {
		this.price = price;
		return this;
	}

	public BookBuilder year(int year) {
		this.year = year;
		return this;
	}
	
	public BookBuilder author(String author) {
		//before instantiate the compulsory attributes, it could validate them ...
		this.author = author;
		return this;
	}
	
	public BookBuilder detail(String detail) {
		//before instantiate the compulsory attributes, it could validate them ...
		this.detail = detail;
		return this;
	}
	
	public BookBuilder status(String status) {
		//before instantiate the compulsory attributes, it could validate them ...
		this.status = status;
		return this;
	}
	
	public Book build() {
		Book b = new Book();
		
		b.setId(id);
		b.setTitle(title);
		b.setPrice(price);
		b.setYear(year);
		b.setAuthor(author);
		b.setDetail(detail);
		b.setStatus(status);
		
		return b;
	}
}
