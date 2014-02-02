package client;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class CustomTester {

	public static void main(String[] args) {
		Client client = Client.create(new DefaultClientConfig());
		WebResource service = client.resource(getBaseURI());

		System.out.println(service.path("/books")
				.accept(MediaType.APPLICATION_XML).get(String.class));
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri(
				"http://localhost:8080/REST_Resource_Interface/resources")
				.build();
	}
}
