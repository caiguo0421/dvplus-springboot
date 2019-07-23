package cn.sf_soft;

import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@ComponentScan(basePackages= {"cn.sf_soft"})
@EntityScan(basePackages= {"cn.sf_soft"})
@EnableJpaRepositories(basePackages= {"cn.sf_soft"})
@EnableTransactionManagement
@SpringBootApplication
public class DvplusApplication {

//    @Bean
//    public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf){
//        return hemf.getSessionFactory();
//    }

    public static void main(String[] args) {
        SpringApplication.run(DvplusApplication.class, args);
    }

}
