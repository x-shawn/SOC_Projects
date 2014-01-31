/**
 * 
 */
package cs9322;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author shawn
 * 
 */

@Path("/hello")
public class HelloWorld {

	@GET
	@Produces(MediaType.TEXT_XML)
	public String sayHelloXML() {
		return "<?xml version=\"1.0\"?>" + "<msg>" + "Hello World in REST"
				+ "</msg>";
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHelloHtml() {
		return "<html> " + "<title>" + "Hello Jersey" + "</title>"
				+ "<body><h1>" + "Hello World in REST" + "</body></h1>"
				+ "</html> ";
	}
}