package com.tekion.tenant.MultiTenant_Prj.multiTenant;


import com.tekion.tenant.MultiTenant_Prj.service.MasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@Configuration
public class MultiTenantManager {

	private String url = "jdbc:mysql://localhost:3306/";
	private String user = "root";
	private String password = "Spring@123";

	private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();
	private final Map<Object, Object> tenantDataSources = new ConcurrentHashMap<>(new MasterService().getDataSourceHashMap());
	private final DataSourceProperties properties;

	private AbstractRoutingDataSource multiTenantDataSource;

	public MultiTenantManager(DataSourceProperties properties) {
		this.properties = properties;
	}

	//Executed by repository during api call
	@Bean
	public DataSource dataSource() {
		multiTenantDataSource = new AbstractRoutingDataSource() {
			@Override
			protected Object determineCurrentLookupKey() {
				return currentTenant.get();
			}
		};
		multiTenantDataSource.setTargetDataSources(tenantDataSources);
		multiTenantDataSource.setDefaultTargetDataSource(defaultDataSource());
		multiTenantDataSource.afterPropertiesSet();
		return multiTenantDataSource;
	}

	//Setting tenant Id to thread local by colleting id from controller
	public void setCurrentTenant(String tenantId) throws SQLException {
		if (tenantIsAbsent(tenantId)) {
			addTenant(tenantId, url + tenantId, user, password);
		}
		currentTenant.set(tenantId);
	}

	//Checking tenant available in Master service class
	public boolean tenantIsAbsent(String tenantId) {
		return !tenantDataSources.containsKey(tenantId);
	}

	//Pre-defined tenant list
	public Collection<Object> getTenantList() {
		return tenantDataSources.keySet();
	}

	//Default Datasource
	private DriverManagerDataSource defaultDataSource() {
		DriverManagerDataSource defaultDataSource = new DriverManagerDataSource();
		defaultDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		defaultDataSource.setUrl("jdbc:mysql://localhost:3306/tenant1");
		defaultDataSource.setUsername("root");
		defaultDataSource.setPassword("Spring@123");
		return defaultDataSource;
	}

	// Adding datasouce to concurret list
	public void addTenant(String tenantId, String url, String username, String password) throws SQLException {
		DataSource dataSource = DataSourceBuilder.create()
				.driverClassName(properties.getDriverClassName())
				.url(url)
				.username(username)
				.password(password)
				.build();

		// Check that new connection is 'live'. If not - throw exception
		try (Connection c = dataSource.getConnection()) {
			tenantDataSources.put(tenantId, dataSource);
			multiTenantDataSource.afterPropertiesSet();
		}
	}
}
