package com.restapi.config;



import java.util.HashMap;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySources({ @PropertySource("classpath:application.properties") })
@EnableJpaRepositories(
        entityManagerFactoryRef = "ds1EntityManager",
        transactionManagerRef = "ds1TransactionManager",
        basePackages = {"com.restapi.product.repository"}
        )






public class ProductConfig {
	
	@Autowired
    private Environment env;
	@Primary
	@Bean
	@ConfigurationProperties(prefix="spring.prod")

	public DataSource ds1Datasource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		 dataSource.setDriverClassName(env.getProperty("spring.prod.datasource.driver-class-name"));
		 dataSource.setUrl(env.getProperty("spring.prod.datasource.url"));
		 dataSource.setUsername(env.getProperty("spring.prod.datasource.username"));
		 dataSource.setPassword(env.getProperty("spring.prod.datasource.password"));
		
		return dataSource;
	}

	@Bean
	public  LocalContainerEntityManagerFactoryBean ds1EntityManager()  {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(ds1Datasource());
        // Scan Entities in Package:
        em.setPackagesToScan(new String[] {"com.restapi.product.entity"});
        em.setPersistenceUnitName("source_database");
 
     // JPA & Hibernate
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
 
        em.setJpaVendorAdapter(vendorAdapter);
 
        HashMap<String, Object> properties = new HashMap<>();
 
        // JPA & Hibernate
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect.2"));
        properties.put("hibernate.show-sql", env.getProperty("spring.jpa.show_sql.2"));
 
        
        em.setJpaPropertyMap(properties);
        em.afterPropertiesSet();
        return em;
 
	}

	@Bean
	PlatformTransactionManager ds1TransactionManager() {
		 
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(ds1EntityManager().getObject());
        return transactionManager;
    }
}