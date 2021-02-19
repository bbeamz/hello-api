package beamer.net.hello.resource;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import beamer.net.hello.model.Job;
import beamer.net.hello.model.Name;
import beamer.net.hello.service.HelloService;

@RunWith(MockitoJUnitRunner.class)
public class HelloResourceTest {

	private static final String BASE_LOCATION = "http://dummy.co/v1/hello/names";
	private static final String LOCATION_SUFFIX = "3";
	private static final String LOCATION = "Location";

	@Mock
	private HelloService helloService;

	@Mock
	private UriInfo uriInfo;

	@InjectMocks
	private HelloResource helloResource;

	private List<Name> testNames = new ArrayList<>();
	private Name name1;
	private Name name2;
	private Job job;

	@Before
	public void setup() {

		// init happy-path test data
		job = new Job().withCompanyName("The").withJobTitle("Builder");

		name1 = new Name().withId(1L).withName("Bob");
		name2 = new Name().withId(2L).withName("Sam");

		testNames.add(name1);
		testNames.add(name2);

		UriBuilder builder = UriBuilder.fromPath(BASE_LOCATION);

		// mock calls to out-of-scope HelloResource
		when(helloService.getNames()).thenReturn(testNames);
		when(helloService.getName(anyLong())).thenReturn(name1);
		when(helloService.getJob(anyString())).thenReturn(job);
		when(helloService.createName(any(Name.class))).thenReturn(3L);
		when(helloService.deleteName(anyString())).thenReturn(1);

		// mock calls to out-of-scope UriContext
		when(uriInfo.getAbsolutePathBuilder()).thenReturn(builder);

	}

	@Test
	public void testCreate_LocationHeaders() {
		Response expectedResponse = Response.created(URI.create(BASE_LOCATION + "/" + LOCATION_SUFFIX)).build();

		Response actualResponse = helloResource.create(new Name().withId(1L).withName("Bob"), uriInfo);

		// @formatter:off
		assertEquals(expectedResponse.getMetadata().getFirst(LOCATION).toString(), 
				     actualResponse.getMetadata().getFirst(LOCATION).toString());
		// @formatter:on
	}

	@Test
	public void testDelete() {
		Response actualResponse = helloResource.delete("Bob");
		
		assertEquals(204, actualResponse.getStatus());
	}

	@Test
	public void testGetName() {
		Name actualName = helloResource.getName(1L);
		assertEquals(name1, actualName);
	}

	@Test
	public void testGetNames() {
		List<Name> actualNames = helloResource.getNames();
		assertEquals(testNames, actualNames);
	}

	@Test
	public void testGetJob() {
		Job actualJob = helloResource.getJob("Bob");
		assertEquals(job, actualJob);
	}
	
	// purely for coverage of invoked code
	@Test
	public void testInit() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Method postConstruct = HelloResource.class.getDeclaredMethod("init",null);
	    postConstruct.setAccessible(true);
	    postConstruct.invoke(helloResource);
	}
	
	/*
	 * TODO: ADD SOME MORE CASES
	 */
	
	@Test
	public void testDelete_notFound() {

	}
	
	@Test
	public void testDelete_error() {

	}
	
	@Test
	public void testCreate_statusCodes() {

	}
	
	@Test
	public void testCreate_error() {

	}
}
