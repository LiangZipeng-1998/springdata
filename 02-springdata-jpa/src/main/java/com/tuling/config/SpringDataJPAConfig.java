package com.tuling.config;

import com.alibaba.druid.pool.DruidAbstractDataSource;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration  //标记当前类为配置类  =xml配置文件
@EnableJpaRepositories(basePackages = "com.tuling.repositories")  //启动jpa  <jpa:repositories
@EnableTransactionManagement  //开启事务
public class SpringDataJPAConfig {

    /**
     * <bean class="com.alibaba.druid.pool.DruidDataSource" name="dataSource">
     *         <property name="username" value="root"></property>
     *         <property name="password" value="root"></property>
     *         <property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
     *         <property name="url" value="jdbc:mysql://localhost:3306/springdata_jpa?characterEncoding=UTF-8&amp;useSSL=false"></property>
     * </bean>
     */
    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/springdata_jpa?characterEncoding=UTF-8&useSSL=false");
        return dataSource;
    }

    /**
     * <!-- EntityManagerFactory -->
     *     <bean name="entityManagerFactory" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
     *         <property name="jpaVendorAdapter">
     *             <!--Hibernate实现-->
     *             <bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter">
     *                 <!--生成数据库表-->
     *                 <property name="generateDdl" value="true"></property>
     *                 <!--展示SQL语句-->
     *                 <property name="showSql" value="true"></property>
     *             </bean>
     *         </property>
     *         <!--设置实体类的包-->
     *         <property name="packagesToScan" value="com.tuling.pojo"></property>
     *         <property name="dataSource" ref="dataSource"></property>
     *     </bean>
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.tuling.pojo");
        factory.setDataSource(dataSource());
        return factory;
    }

    /**
     * <bean class="org.springframework.orm.jpa.JpaTransactionManager" name="transactionManager">
     *         <property name="entityManagerFactory" ref="entityManagerFactory"></property>
     * </bean>
     */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }

}
