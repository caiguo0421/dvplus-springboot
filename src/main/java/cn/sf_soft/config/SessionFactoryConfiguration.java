package cn.sf_soft.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;

import javax.persistence.EntityManagerFactory;


@Configuration
public class SessionFactoryConfiguration {


//    @Bean
//    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory){
//        return new HibernateTransactionManager(sessionFactory);
//    }

//    @Autowired
//    private EntityManagerFactory entityManagerFactory;
//
//    @Bean
//    public SessionFactory getSessionFactory() {
//        return entityManagerFactory.unwrap(SessionFactory.class);
//    }

//    @Bean
//    public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf){
//        return hemf.getSessionFactory();
//    }

//    @Autowired
//    private EntityManagerFactory entityManagerFactory;
//
//    @Bean
//    public Session getSession() {
//        return entityManagerFactory.unwrap(SessionFactory.class).openSession();
//    }


    @Bean
    public SessionFactory sessionFactory(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.unwrap(SessionFactory.class);
    }

}
