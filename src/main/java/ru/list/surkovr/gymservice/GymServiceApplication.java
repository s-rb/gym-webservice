package ru.list.surkovr.gymservice;

import org.apache.commons.dbcp2.cpdsadapter.DriverAdapterCPDS;
import org.apache.commons.dbcp2.datasources.SharedPoolDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.UserCredentialsDataSourceAdapter;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@SpringBootApplication
public class GymServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymServiceApplication.class, args);
    }

    @Bean(name = "dataSource")
    public UserCredentialsDataSourceAdapter dataSource(
            @Value("${spring.datasource.url}") String url
    ) {
        DriverAdapterCPDS driverAdapter = new DriverAdapterCPDS();
        driverAdapter.setUrl(url + "?stringtype=unspecified");

        SharedPoolDataSource dataSource = new SharedPoolDataSource();
        dataSource.setValidationQuery("SELECT 1;");

        dataSource.setDefaultTestOnBorrow(true);
        dataSource.setValidationQueryTimeout(0);
        dataSource.setConnectionPoolDataSource(driverAdapter);

        UserCredentialsDataSourceAdapter adapter = new UserCredentialsDataSourceAdapter();
        adapter.setTargetDataSource(dataSource);

        return adapter;
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("dataSource") DataSource ds) {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(ds);
        em.setPackagesToScan("ru.list.surkovr.gymservice.domain");

        Properties props = new Properties();
        props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        em.setJpaProperties(props);

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        return em;
    }
}
