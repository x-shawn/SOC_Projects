/**
 * 
 */
package dao;

import java.util.HashMap;
import java.util.Map;

import vo.Book;
import vo.BookBuilder;

/**
 * @author shawn
 * 
 */
public final class BooksDAO {
	private static final HashMap<String, Book> bookstore = new HashMap<String, Book>();

	static {
		Book b1 = new BookBuilder("1", "RESTful Web Services").year(2008)
				.author("O'Reilly")
				.detail("http://oreilly.com/catalog/9780596529260").build();

		Book b2 = new BookBuilder("2", "RESTful Java with JAX-RS").year(2009)
				.author("O'Reilly")
				.detail("http://oreilly.com/catalog/9780596158057")
				.status("persistent").build();
		
		Book b3 = new BookBuilder("3", "REST in Action").year(2010)
				.author("O'Reilly")
				.detail("http://oreilly.com/catalog/123456789")
				.status("persistent").build();

		bookstore.put("1", b1);
		bookstore.put("2", b2);
		bookstore.put("3", b3);
	}

	private BooksDAO() {
	}

	public static Map<String, Book> getStore() {
		return bookstore;
	}
}
