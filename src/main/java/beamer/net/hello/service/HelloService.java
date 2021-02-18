package beamer.net.hello.service;

import java.util.List;

import beamer.net.hello.model.Job;
import beamer.net.hello.model.Name;

public interface HelloService {
	public Job getJob(String name);

	public Name getName(Long id);

	public List<Name> getNames();

	public Long createName(Name name);

	public int deleteName(String name);
}
