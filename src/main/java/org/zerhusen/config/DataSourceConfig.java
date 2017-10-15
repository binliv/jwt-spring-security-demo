//package org.zerhusen.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.env.Environment;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.JpaVendorAdapter;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//import java.util.Properties;
//
//@Configuration
////@EnableJpaRepositories(basePackages = {"com.wanban.fileservice.database.repository", "com.yuyan.database.repository"})
//////@EnableTransactionManagement
//////@PropertySource(value = {
//////        "classpath:datasource.properties",
//////        "classpath:datasource_override.properties"
//////}, ignoreResourceNotFound = true)
////@Import(ServiceConfig.class)
//public class DataSourceConfig {
//
//    @Bean()
//    public DataSource dataSource() {
//        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
//        //com.alibaba.druid.pool.DruidDataSource dataSource = new com.alibaba.druid.pool.DruidDataSource();
////        dataSource.setDriverClassName(env.getProperty("db.driverClassName"));
////
////        String url = env.getProperty("db.url");
////        String ip = rootEnv.getDatabaseIp();
////        String realUrl = url.replaceAll("\\d{1,3}(\\.\\d{1,3}){3}", ip);//replace the ip in the data source properties with ip in service properties
////
//        dataSource.setUrl("jdbc:postgresql://127.0.0.1/file_db");
//        dataSource.setUsername("file_user");
//        dataSource.setPassword("123456");
////        dataSource.setMaxActive(env.getProperty("db.maxActive", Integer.class));
////        dataSource.setInitialSize(env.getProperty("db.initialSize", Integer.class));
////        dataSource.setMaxIdle(env.getProperty("db.maxIdle", Integer.class));
////        dataSource.setMinIdle(env.getProperty("db.minIdle", Integer.class));
////        dataSource.setValidationQuery(env.getProperty("db.validationQuery"));
////        dataSource.setTestOnBorrow(env.getProperty("db.testOnBorrow", Boolean.class));
////        dataSource.setTestOnReturn(env.getProperty("db.testOnReturn", Boolean.class));
////        dataSource.setTestWhileIdle(env.getProperty("db.testWhileIdle", Boolean.class));
////        dataSource.setTimeBetweenEvictionRunsMillis(env.getProperty("db.timeBetweenEvictionRunsMillis", Integer.class));
////        dataSource.setMinEvictableIdleTimeMillis(env.getProperty("db.minEvictableIdleTimeMillis", Integer.class));
////
////        dataSource.setRemoveAbandoned(env.getProperty("db.removeAbandoned", Boolean.class));
////        dataSource.setRemoveAbandonedTimeout(env.getProperty("db.removeAbandonedTimeout", Integer.class));
////        dataSource.setMaxWait(env.getProperty("db.maxWait", Integer.class));
////        dataSource.setLogAbandoned(env.getProperty("db.logAbandoned", Boolean.class));
//
//        return dataSource;
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//
//        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//        factory.setJpaVendorAdapter(vendorAdapter);
//        factory.setDataSource(dataSource());
//        factory.setPackagesToScan("com.wanban.fileservice.database.entity", "com.yuyan.database.entity");
//        factory.setJpaProperties(additionalProperties());
//
//        return factory;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
//        return transactionManager;
//    }
//
//    Properties additionalProperties() {
//        return new Properties() {
//            {
//                setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
////                setProperty("hibernate.show_sql", env.getProperty("db.hibernate.show_sql"));
////                setProperty("hibernate.jdbc.batch_size", env.getProperty("db.hibernate.jdbc.batch_size"));
////
////                // hbm2ddl should be used for development, omitted for production
////                String hbm2ddl = env.getProperty("db.hibernate.hbm2ddl.auto");
////                if (hbm2ddl != null) {
////                    //setProperty("hibernate.hbm2ddl.auto", hbm2ddl);
////                }
//            }
//        };
//    }
//
//}
