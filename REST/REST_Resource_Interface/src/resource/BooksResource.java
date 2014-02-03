package resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import vo.Book;
import vo.BookBuilder;
import dao.BooksDAO;

/**
 * A collection resource demo; specifically,
 * 
 * 1. a collection resource is often queried by client;
 * 2. a collection resource is often regarded as a factory to create a new member resource;
 * 3. a collection resource is necessary for the same operation on multiple member resources.
 */
@Path("/books")
public class BooksResource {
	@Context
	private UriInfo uriInfo;

	/**
	 * Another typical @GET scenario: query with parameters
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> queryBooks(
			@DefaultValue("") @QueryParam("author") String author,
			@DefaultValue("0") @QueryParam("year") int year) {
		ArrayList<Book> result = new ArrayList<Book>();

		for (Book b : this.getBooks()) {
			if (author.equals(b.getAuthor()) && year == b.getYear()) {
				result.add(b);
			}
		}

		return result;
	}

	/**
	 * A typical @POST scenario: resource creation with http status codes;
	 * Specifically: 1. 201: created successfully; 2. 400: bad request (i.e.
	 * de-serialization failed) 3. 500: internal server error (i.e. an exception
	 * occurs)
	 */
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response createBook(Book newBook) {
		Response response = null;

		try {
			Map<String, Book> db = BooksDAO.getStore();
			String id = newBook.getId();

			if (!db.containsKey(id)) {
				db.put(id, newBook);
				// set the POST success code (i.e. 201 - created) and URI for
				// the new created resource
				String uri = uriInfo.getAbsolutePath().toString() + "/" + id;
				response = Response.status(201).header("Location", uri)
						.entity(newBook).build();
				// response =
				// Response.created(URI.create(uri)).entity(newBook).build();
			}
		} catch (Exception e) {
			response = Response.status(500).build();
		}

		return response;
	}

	/**
	 * Another @POST scenario: creation via html form
	 */
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String createBookByForm(@FormParam("id") String id,
			@FormParam("title") String title, @FormParam("detail") String detail) {

		Book b = new BookBuilder(id, title).detail(detail).build();
		BooksDAO.getStore().put(id, b);
		return id;
	}

	private List<Book> getBooks() {
		ArrayList<Book> result = new ArrayList<Book>();
		
		for(Book stored : BooksDAO.getStore().values()){
			Book vo = Book.copy(stored);
			result.add(vo);
		}
		
		return result;
	}

	/* others ...*****************************************************/
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateBook2(JAXBElement<Book> xml) {
		String result;
		Book b = xml.getValue();

		Map<String, Book> db = BooksDAO.getStore();
		String updated = b.getId();
		if (db.containsKey(updated)) {
			db.put(updated, b);
			result = updated;
		} else {
			// error-code: 404
			result = "updated resource does not exist";
		}

		return result;
	}
}