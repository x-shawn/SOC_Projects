package resource;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import vo.Book;
import vo.BookMark;

import com.sun.jersey.api.ConflictException;
import com.sun.jersey.api.NotFoundException;

import dao.BooksDAO;

/**
 * A single resource demo; specifically,
 * 
 * 1. different from POJO (VO): the latter is used by O-O for domain analysis
 * and design (business entity only); while the former is used by R-O for the
 * interaction between client and server. So, it could be the composite VOs, or
 * business logic ('M' from "MVC")
 */
@Path("/books/{id}")
public class BookResource {

	/* ********************* GET resource scenarios start ******************* */

	/**
	 * A typical @GET scenario
	 * 
	 * Note that this uses @WebApplicationException to send error code (4xx or
	 * 5xx) in response; besides this natural Java way to response with http
	 * status code, you could also construct a @Response by yourself, see the
	 * next demo
	 */
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Book getBookAsXML(@PathParam("id") String bookID) {
		Book book = this.getBook(bookID);

		if (book == null) {
			throw new NotFoundException("the book resource does not exist.");
		}

		return book;
	}

	/**
	 * The similar @GET use case as the above. the difference is to response
	 * client directly with http status code. Specifically, 1. 200: OK; 2. 404:
	 * Not Found; 3. 500: Internal Error
	 */
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBookAsJson(@PathParam("id") String bookID) {
		Response response = null;

		try {
			Book book = this.getBook(bookID);
			if (book != null) {
				response = Response.ok().entity(book).build();
			} else {
				response = Response.status(Response.Status.NOT_FOUND).build();
			}
		} catch (Exception ex) {
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.build();
		}

		return response;
	}

	/**
	 * Sub-resource demo
	 */
	@Path("age")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getAgeOfBook(@PathParam("id") String bookID) {
		String age = null;
		
		Book book = this.getBook(bookID);
		if(book != null){
			int years = Calendar.getInstance().get(Calendar.YEAR) - book.getYear();
			age = String.valueOf(years);
		}
		
		return age;
	}

	/* ********************* GET resource scenarios end ******************* */
	

	/* ********************* PUT resource scenarios start ***************** */

	/**
	 * A typical @PUT scenario: update resource without response. (a natural java way)
	 * 
	 * Note that this is similar to 1st @GET scenario, which use @WebApplicationException
	 * to response client with error code, e.g. 409 for update conflict.
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public void updateBook(Book book) {
		String target = book.getId();
		Map<String, Book> db = BooksDAO.getStore();

		if (db.containsKey(target)) {
			Book stored = db.get(target);
			if (!"locked".equals(stored.getStatus())) {
				db.put(target, book);
			} else {
				// 409: Method Conflict
				throw new ConflictException(
						"the book resource cannot be updated as it has been locked");
			}
		} else {
			// 404: Not Found
			throw new NotFoundException(
					"the target book resource does not exist");
		}
	}

	/**
	 * Similar to 2nd @GET use case: response client with http status codes;
	 * 
	 * Specifically: 1. 200/204: updated successfully (200 means the updated
	 * resource is returned to client, while 204 means the request is accepted
	 * without updated resource in the response (no content));
	 * 
	 * 2. 404: the target resource doesn't exist; 3. 409: PUT method is not
	 * allowed, e.g. conflict; 4. 500: internal error
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateBookWithResponse(Book book) {
		Response response = null;
		String target = book.getId();

		try {
			Map<String, Book> db = BooksDAO.getStore();
			if (db.containsKey(target)) {
				Book stored = db.get(target);
				if ("persistent".equals(stored.getStatus())) {
					db.put(target, book);
					// 200: with entity in response body
					response = Response.ok(book).type(MediaType.APPLICATION_XML).build();

					// 204: no content in response body
					//response = Response.noContent().build();
				} else {
					// 409: method is not allowed because of conflicts, e.g.
					// status inconsistency
					response = Response
							.status(Response.Status.CONFLICT)
							.entity("The state conflict of resource: book_"
									+ target).type(MediaType.TEXT_PLAIN)
							.build();
				}
			} else {
				// 404
				response = Response.status(Response.Status.NOT_FOUND).build();
			}
		} catch (Exception e) {
			// 500
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.build();
		}
		return response;
	}

	/**
	 * Another @PUT use case: create resource by @PUT, when the client is
	 * responsible for maintaining the URI of created resource, e.g. person's
	 * address, self-maintained file system, etc. Similar to the case where a
	 * collection resource creates its member resource.
	 */
	@Path("/bookmarks")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void createBookMark(@PathParam("id") String bookID, List<BookMark> bookmarks) {
		Book book = getBook(bookID);
		book.setBookmarks(bookmarks);
		// use DAO to create a list of BookMarks and update Book ...
		this.modifyBook(book);
	}

	/* ********************* PUT resource scenarios end ***************** */

	
	/* ********************* DELETE resource scenarios start ************ */

	/**
	 * A typical @PUT scenario: update resource without response. (a natural java way)
	 */
	@DELETE
	public void deleteBook(@PathParam("id") String bookID) {
		Book book = getBook(bookID);
		if(book != null && !"removed".equals(book.getStatus())){
			book.setStatus("removed");
			this.modifyBook(book);
		}else{
			throw new NotFoundException("The target resource does not exist");
		}
	}
	
	/**
	 * Similar to 2nd @GET and @PUT scenarios: delete resource with @Response;
	 * Specifically: 1. 204: deleted successfully with no content in the
	 * response; 2. 404: the target resource cannot be found; 3. 503: service is
	 * not available
	 */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteBookWithResponse(@PathParam("id") String bookID) {
		Response response = null;
		Book book = getBook(bookID);

		// delete logic: it doesn't have to delete/remove the resource on the
		// server side, as long as the client cannot access the resource
		if (book != null) {
			if (!"removed".equals(book.getStatus())) {
				book.setStatus("removed");
				this.modifyBook(book);
				// 204
				response = Response.noContent().build();
			} else {
				// 503
				response = Response
						.status(Response.Status.SERVICE_UNAVAILABLE)
						.header("Allow", "GET")
						.entity("The requested service is not available, please use others")
						.type(MediaType.TEXT_PLAIN).build();
			}
		} else {
			// 404
			 response = Response.status(Response.Status.NOT_FOUND).build();
		}

		return response;
	}
	
	/* ********************* DELETE resource scenarios end ************** */


	/* POST resource scenarios start ******************************************* */
	/* POST resource scenarios end ********************************************* */
	
	
	/* *********Helper methods start ************************************ */
	private Book getBook(String bookID) {
		Book persistent = BooksDAO.getStore().get(bookID);
		if(persistent == null) {
			return null;
		}
		
		return Book.copy(persistent);
	}

	private void modifyBook(Book vo) {
		Map<String, Book> db = BooksDAO.getStore();
		
		Book persistent = db.get(vo.getId());
		if(persistent != null) {
			synchronized(db){
				db.put(persistent.getId(), vo);
			}
		}
	}
	/* *********Helper methods end ************************************ */
}
