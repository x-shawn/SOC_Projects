package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import vo.Book;
import dao.BooksDAO;

@Path("/books")
public class BooksService {
	@Context
	private UriInfo uriInfo;

	/* GET resource in two typical representations start ***************** */
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<Book> getBooksAsAppXML() {
		return this.getBooks();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> getBooksAsJson() {
		return this.getBooks();
	}

	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCountOfBooks() {
		return String.valueOf(this.getBooks().size());
	}

	/* GET resource in three typical representations end ******************** */

	/**
	 * A typical @GET scenario (i.e. with http status code); Specifically, 1.
	 * 200: OK; 2. 404: Not Found; 3. 500: Internal Error
	 */
	@GET
	@Path("{book}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBook(@PathParam("book") String id) {
		Response response = null;

		try {
			Book book = BooksDAO.getStore().get(id);
			if (book != null) {
				response = Response.ok().entity(book).build();
			} else {
				response = Response.status(Response.Status.NOT_FOUND).build();
			}
		} catch (Exception e) {
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.build();
		}

		return response;
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

		Book b = new Book(id, title, detail);
		BooksDAO.getStore().put(id, b);
		return id;
	}

	/**
	 * A typical @PUT scenario: update resource with http status codes;
	 * Specifically: 
	 * 1. 200/204: updated successfully (200 means the updated
	 * resource is returned to client, while 204 means the request is accepted
	 * without updated resource in the response (no content));
	 * 
	 * 2. 404: the target resource doesn't exist;
	 * 3. 409: PUT method is not allowed, e.g. conflict;
	 * 4. 500: internal error
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_XML)
	public Response updateBook(Book book) {
		Response response = null;
		String target = book.getId();
		
		try{
			Map<String, Book> db = BooksDAO.getStore();
			if (db.containsKey(target)) {
				Book stored = db.get(target);
				if("persistent".equals(stored.getStatus())){
					db.put(target, book);
					// 200: with entity in response body
					//response = Response.ok(book).build();
					// 204: no content in response body
					response = Response.noContent().build();
				}else{
					// 409: method is not allowed because of conflicts, e.g. status inconsistency
					response = Response.status(Response.Status.CONFLICT).build();
				}			
			} else {
				// 404
				response = Response.status(Response.Status.NOT_FOUND).build();
			}
		}catch(Exception e){
			// 500
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return response;
	}

	/**
	 * A typical @DELETE scenario: delete resource with http status codes;
	 * Specifically: 
	 * 1. 204: deleted successfully with no content in the response; 
	 * 2. 404: the target resource cannot be found;
	 * 3. 503: service is not available
	 */
	@DELETE
	@Path("{book}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteBook(@PathParam("book") String id) {
		Response response = null;

		Map<String, Book> db = BooksDAO.getStore();
		if (db.containsKey(id)) {
			// delete logic: it doesn't have to delete/remove the resource on
			// the server side, as long as the client cannot access the resource
			Book book = db.get(id);
			if(!"removed".equals(book.getStatus())){
				book.setStatus("removed");
				// 204
				response = Response.noContent().build();
			}else{
				// 503
				response = Response.status(Response.Status.SERVICE_UNAVAILABLE)
						.header("Allow", "GET").build();
			}
		} else {
			// 404
			response = Response.status(Response.Status.NOT_FOUND).build();
		}

		return response;
	}

	private List<Book> getBooks() {
		return new ArrayList<Book>(BooksDAO.getStore().values());
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