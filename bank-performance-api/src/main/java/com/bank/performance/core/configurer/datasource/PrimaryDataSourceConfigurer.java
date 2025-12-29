package com.bank.performance.core.configurer.datasource;


/**
 * @author yj
 * @date 2020/9/8
 */

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryPrimary",
        transactionManagerRef = "transactionManagerPrimary",
        basePackages = {"com.bank.performance.dao"}
)
public class PrimaryDataSourceConfigurer {

    private Logger logger = LoggerFactory.getLogger(PrimaryDataSourceConfigurer.class);

    @Autowired
    private JpaProperties jpaProperties;

    @Bean
    @Primary
    @Qualifier("primaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.primary")
    public DataSource primaryDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Primary
    @Bean
    public EntityManager entityManagerPrimary( EntityManagerFactoryBuilder builder,  HibernateProperties hibernateProperties) {
        return Objects.requireNonNull(entityManagerFactoryPrimary(builder, hibernateProperties).getObject()).createEntityManager();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary( EntityManagerFactoryBuilder builder,  HibernateProperties hibernateProperties) {
        Map<String, Object> properties = hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
        return builder
                .dataSource(primaryDataSource())
                .properties(properties)
                .packages("com.bank.performance.entity")
                .persistenceUnit("primaryPersistenceUnit")
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager transactionManagerPrimary( EntityManagerFactoryBuilder builder,  HibernateProperties hibernateProperties) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactoryPrimary(builder, hibernateProperties).getObject()));
    }

}