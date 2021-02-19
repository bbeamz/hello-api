package beamer.net.hello.resource;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.google.inject.Guice;
import com.google.inject.Inject;

import beamer.net.hello.bootstrap.ServiceModule;
import beamer.net.hello.model.Job;
import beamer.net.hello.model.Name;
import beamer.net.hello.service.HelloService;

@Path("hello")
public class HelloResource {

	@Inject
	private HelloService helloService;

	@PostConstruct
	public void init() {
		helloService = Guice.createInjector(new ServiceModule()).getInstance(HelloService.class);
	}

	@GET
	@Path("names/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Name getName(@PathParam("id") Long id) {
		return helloService.getName(id);
	}

	@GET
	@Path("names")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Name> getNames() {
		return helloService.getNames();
	}

	@POST
	@Path("names")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(Name name, @Context UriInfo uriInfo) {
		Long newId = helloService.createName(name);

		if (newId == null) {
			return Response.serverError().entity("Unexpected problem creating new name").build();
		} else {
			UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
			uriBuilder.path(Long.toString(newId));
			return Response.created(uriBuilder.build()).build();
		}
	}

	@DELETE
	@Path("names/{name}")
	public Response delete(@PathParam("name") String name) {
		int deleted = helloService.deleteName(name);

		if (deleted == 1) {
			return Response.noContent().build();
		} else if (deleted == 0) {
			return Response.status(404).build();
		} else {
			return Response.serverError().entity("Unexpected problem deleting name").build();
		}
	}

	@GET
	@Path("jobs/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Job getJob(@PathParam("name") String name) {
		return helloService.getJob(name);
	}

}
