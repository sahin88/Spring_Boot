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
        entityManagerFactoryRef = "ds2EntityManager",
        transactionManagerRef = "ds2TransactionManager",
        basePackages = {"com.restapi.user.repository"}
        )






public class UserConfig {
	@Autowired
    private Environment env;
	@Primary
	@Bean
	@ConfigurationProperties(prefix="spring.datasource")

	public DataSource ds2Datasource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		 dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
		 dataSource.setUrl(env.getProperty("spring.datasource.url"));
		 dataSource.setUsername(env.getProperty("spring.datasource.username"));
		 dataSource.setPassword(env.getProperty("spring.datasource.password"));
		
		return dataSource;
	}

	@Bean
	public  LocalContainerEntityManagerFactoryBean ds2EntityManager()  {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(ds2Datasource());
        // Scan Entities in Package:
        em.setPackagesToScan(new String[] {"com.restapi.user.entity"});
        em.setPersistenceUnitName("destination_database");
 
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
	PlatformTransactionManager ds2TransactionManager() {
		 
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(ds2EntityManager().getObject());
        return transactionManager;
    }
}