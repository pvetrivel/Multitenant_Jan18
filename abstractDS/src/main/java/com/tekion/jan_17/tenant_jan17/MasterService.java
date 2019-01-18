package com.tekion.jan_17.tenant_jan17;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.HashMap;


public class MasterService {

  static  HashMap hashMap = new HashMap();

    public static HashMap<String, DataSource> getDataSourceHashMap() {

        DriverManagerDataSource dataSource1 = new DriverManagerDataSource();
        dataSource1.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource1.setUrl("jdbc:mysql://localhost:3306/datasource1");
        dataSource1.setUsername("root");
        dataSource1.setPassword("Spring@123");

        DriverManagerDataSource dataSource2 = new DriverManagerDataSource();
        dataSource2.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource2.setUrl("jdbc:mysql://localhost:3306/datasource2");
        dataSource2.setUsername("root");
        dataSource2.setPassword("Spring@123");

//        HashMap hashMap = new HashMap();

        hashMap.put("tenant1", dataSource1);
        hashMap.put("tenant2", dataSource2);
        return hashMap;

    }
}


