package resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/customer")
public class CustomerResource {

	@Path("dashboard")
	@GET
	public Response testRedirectHere() {
		return Response.ok("really ?!").type(MediaType.TEXT_PLAIN).build();
	}
}
