package com.hulkaach.hr_skill_ninja.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.Properties;

@org.springframework.context.annotation.Configuration
public class HibernateConfig {
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Bean
    @Primary
    public DataSource dataSource() {
        //Конфигурируем DataSource c помощью HikariCP
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driverClassName);
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        config.setPoolName("HikariCP-CandidatePool");

        return new HikariDataSource(config);
    }

    @Bean
    @Primary
    public SessionFactory sessionFactory(DataSource dataSource) {
        Configuration configuration = new Configuration();

        //Конфигурация свойств Hibernate
        configuration.setProperty(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty(Environment.HBM2DDL_AUTO, "validate");
        configuration.setProperty(Environment.SHOW_SQL, "true");
        configuration.setProperty(Environment.FORMAT_SQL, "true");
        configuration.setProperty(Environment.USE_SQL_COMMENTS, "true");

        //Конфигурация свойства подключения к БД
        Properties properties = new Properties();
        properties.put(AvailableSettings.JAKARTA_JTA_DATASOURCE, dataSource);

        configuration.setProperties(properties);

        //Добаим классы сущностей
        configuration.addAnnotatedClass(com.hulkaach.hr_skill_ninja.model.Candidate.class);

        return configuration.buildSessionFactory();
    }
}