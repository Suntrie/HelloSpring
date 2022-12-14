package com.example.hellospring.config;

import com.clickhouse.client.config.ClickHouseClientOption;
import com.clickhouse.jdbc.ClickHouseDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javassist.NotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "clickhouseEntityManager",          // Бин, с помощью которого будут созданы экземпляры найденных репозиториев
        transactionManagerRef = "clickhouseTransactionManager",       // Аналогично
        basePackages = {
                "com.example.hellospring.domain.entities",            // Пакеты для поиска аннотированных компонентов
                "com.example.hellospring.repository"
        }
)
@RequiredArgsConstructor
@Data
public class ClickhouseDataSourceConfig {
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.database}")
    private String database;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    private final JpaProperties jpaProperties;
    private final HibernateProperties hibernateProperties;
    /**
     * https://github.com/ClickHouse/clickhouse-jdbc/blob/d6bcbe412d4c7310bba7dc4b0334140b04614004/clickhouse-jdbc/src/test/java/com/clickhouse/jdbc/JdbcIntegrationTest.java#L106
     * @return
     * @throws SQLException
     */

    // Метод для создания объекта с конфигами подключения
    public DataSource getClickhouseDataSource() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty(ClickHouseClientOption.DATABASE.getKey(), database);
        properties.setProperty(ClickHouseClientOption.CLIENT_NAME.getKey(), username);
        properties.setProperty("user", username);
        properties.setProperty("password", password);
        //properties.setProperty("transactionSupported", "true");

        return new ClickHouseDataSource(url, properties);
    }

    // Создаем пулл Hikari, который будет использовать подключение к Кликхаусу
    @Bean(name = "clickhouseDataSource")
    public DataSource clickhouseDataSource() throws SQLException {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDataSource(getClickhouseDataSource());
        hikariConfig.setPoolName("clickhouseDataSourcePool");
        hikariConfig.setDriverClassName(driverClassName);

        return new HikariDataSource(hikariConfig);
    }

    @Bean(name = "clickhouseEntityManager")
    public LocalContainerEntityManagerFactoryBean clickhouseEntityManager(EntityManagerFactoryBuilder builder) throws SQLException {
        return builder
                .dataSource(clickhouseDataSource())
                .properties(getVendorProperties())
                .packages("com.example.hellospring.domain.entities")
                .persistenceUnit("clickhouseEntityManager")
                .build();
    }

    //ClickHouse поддерживает MySQL-интерфейс: https://clickhouse.com/docs/en/interfaces/mysql/
    //Ограничения: некоторые типы данных пересылаются как строки, не поддерживаются prepared statements
    private Map<String, Object> getVendorProperties() {
        Map<String,String> properties=jpaProperties.getProperties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        return hibernateProperties.determineHibernateProperties(
                properties, new HibernateSettings());
    }

    @Bean(name = "clickhouseTransactionManager")
    public PlatformTransactionManager clickhouseTransactionManager(LocalContainerEntityManagerFactoryBean clickhouseEntityManager) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(clickhouseEntityManager.getObject());

        return txManager;
    }
}
