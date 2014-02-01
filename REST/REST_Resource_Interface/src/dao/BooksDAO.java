/**
 * 
 */
package dao;

import java.util.HashMap;
import java.util.Map;

import vo.Book;

/**
 * @author shawn
 * 
 */
public final class BooksDAO {
	private static final HashMap<String, Book> bookstore = new HashMap<String, Book>();

	static {
		Book b1 = new Book("1", "RESTful Web Services",
				"http://oreilly.com/catalog/9780596529260");
		b1.setStatus("persistent");
		Book b2 = new Book("2", "RESTful Java with JAX-RS",
				"http://oreilly.com/catalog/9780596158057");
		b2.setStatus("persistent");
		
		bookstore.put("1", b1);
		bookstore.put("2", b2);
	}

	private BooksDAO() {
	}

	public static Map<String, Book> getStore() {
		return bookstore;
	}
}
