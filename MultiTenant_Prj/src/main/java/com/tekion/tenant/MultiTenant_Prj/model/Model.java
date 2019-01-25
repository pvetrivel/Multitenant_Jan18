package com.tekion.tenant.MultiTenant_Prj.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Model {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Column(nullable = false)
	private String name;
	private String created_on;
	private String update_on;

	public String getCreated_on() {
		return created_on;
	}

	public void setCreated_on(long currentMillis) {
		this.created_on =""+ currentMillis;
	}

	public String getUpdate_on() {
		return update_on;
	}

	public void setUpdate_on(long currentMillis) {
		this.update_on = ""+currentMillis;
	}

	private String tenant;

	public Model(String tenant) {
		this.tenant = tenant;
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

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}


}
