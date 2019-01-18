package com.tekion.jan_17.tenant_jan17.multitenant;

import com.tekion.jan_17.tenant_jan17.MasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
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


import static java.lang.String.format;


@Slf4j
@Configuration
public class MultiTenantManager {

	private final ThreadLocal<String> currentTenant = new ThreadLocal<>();
	private final Map<Object, Object> tenantDataSources = new ConcurrentHashMap<>(new MasterService().getDataSourceHashMap());
	private final DataSourceProperties properties;

	private AbstractRoutingDataSource multiTenantDataSource;

	public MultiTenantManager(DataSourceProperties properties) {
		this.properties = properties;
	}

	@Bean
	public DataSource dataSource() {
		multiTenantDataSource = new AbstractRoutingDataSource() {
			@Override
			protected Object determineCurrentLookupKey() {
				System.out.println("lookup------->"+currentTenant.get());
				return currentTenant.get();
			}
		};
		multiTenantDataSource.setTargetDataSources(tenantDataSources);
		System.out.println("datasource----->"+tenantDataSources);
		multiTenantDataSource.setDefaultTargetDataSource(defaultDataSource());
		multiTenantDataSource.afterPropertiesSet();
		return multiTenantDataSource;
	}

	public void setCurrentTenant(String tenantId) throws SQLException, TenantNotFoundException, TenantResolvingException {
		if (tenantIsAbsent(tenantId)) {
			throw new TenantNotFoundException(format("Tenant %s not found!", tenantId));
		}
		currentTenant.set(tenantId);
	}

	public boolean tenantIsAbsent(String tenantId) {
		//System.out.println("Tenant is absent----->"+!tenantDataSources.containsKey(tenantId));
		return !tenantDataSources.containsKey(tenantId);
	}

	public Collection<Object> getTenantList() {
		return tenantDataSources.keySet();
	}

	private DriverManagerDataSource defaultDataSource() {
		DriverManagerDataSource defaultDataSource = new DriverManagerDataSource();
		defaultDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		defaultDataSource.setUrl("jdbc:mysql://localhost:3306/datasource1");
		defaultDataSource.setUsername("root");
		defaultDataSource.setPassword("Spring@123");
		return defaultDataSource;
	}
}
