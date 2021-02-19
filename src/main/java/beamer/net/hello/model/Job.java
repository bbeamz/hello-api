package beamer.net.hello.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Job {
	private String companyName;
	private String jobTitle;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	
	public Job withCompanyName(String companyName) {
		this.companyName = companyName;
		return this;
	}
	
	public Job withJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
		return this;
	}
}
