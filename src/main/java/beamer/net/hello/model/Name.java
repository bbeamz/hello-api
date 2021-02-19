package beamer.net.hello.model;

public class Name {
	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Name withId(Long id) {
		this.id = id;
		return this;
	}
	
	public Name withName(String name) {
		this.name = name;
		return this;
	}
}
