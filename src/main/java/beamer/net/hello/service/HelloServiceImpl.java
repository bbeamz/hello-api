package beamer.net.hello.service;

import java.util.List;

import javax.ws.rs.WebApplicationException;

import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.guice.transactional.Transactional;

import com.google.inject.Inject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import beamer.net.hello.dao.HelloMapper;
import beamer.net.hello.model.Job;
import beamer.net.hello.model.Name;

public class HelloServiceImpl implements HelloService {
	private static final String APPLICATION_JSON = "application/json";
	private static final String V1_JOBS = "/v1/jobs";
	private static final String BASE_URL = "http://localhost:10500";
	private static final String NAME = "name";
	
	@Inject
    private HelloMapper helloMapper;

	@Override
	public Job getJob(String name) {
		
		ClientResponse response = Client.create()
				.resource(BASE_URL + V1_JOBS)
				.queryParam(NAME, name)
				.accept(APPLICATION_JSON)
				.get(ClientResponse.class);
				

		if (response.getStatus() >= 500) {
			throw new WebApplicationException(response.getStatus());
		}

		return response.getEntity(Job.class);
	}

	@Override
	public Name getName(Long id) {
		return helloMapper.getName(id);
	}

	@Override
	public List<Name> getNames() {
		return helloMapper.getNames();
	}

	@Override
	@Transactional
	public Long createName(Name name) {
		
		int rows = helloMapper.createName(name.getName());
		
		if (rows != 1) {
			throw new PersistenceException("New name was not created!");
		}
		
		Name newName = helloMapper
				.getNames().stream()
					.filter(x -> x.getName().equals(name.getName()))
						.findFirst()
						.orElse(null);
							
		Long id = newName != null ? newName.getId() : null;
		
		return id;
		
	}

	@Override
	@Transactional
	public int deleteName(String name) {
		return helloMapper.deleteName(name);
	}
}
