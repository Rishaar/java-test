package com.etnetera.hr.data;

import javax.persistence.*;

/**
 * Simple data entity describing basic properties of every JavaScript framework.
 * 
 * @author Etnetera
 *
 */
@Entity
public class JavaScriptFramework {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 30)
	private String name;
	@Column(nullable = true, length = 30)
	private String version;
	@Column(nullable = true)
	private Long deprecationDate;
	@Column(nullable = true)
	private Double hype;

	public JavaScriptFramework() {
	}

	public JavaScriptFramework(String name, String version, Long deprecationDate, Double hype) {
		this.name = name;
		this.version = version;
		this.deprecationDate = deprecationDate;
		this.hype = hype;
	}

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

	public java.lang.String getVersion() {
		return version;
	}

	public void setVersion(java.lang.String version) {
		this.version = version;
	}

	public Long getDeprecationDate() {
		return deprecationDate;
	}

	public void setDeprecationDate(Long deprecationDate) {
		this.deprecationDate = deprecationDate;
	}

	public Double getHype() {
		return hype;
	}

	public void setHype(Double hype) {
		this.hype = hype;
	}

	@Override
	public String toString() {
		return "JavaScriptFramework [id=" + id + ", name=" + name + "]";
	}

}
