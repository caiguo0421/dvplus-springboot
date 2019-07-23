package cn.sf_soft.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;


//@Configuration
public class HibernateConfig {

//    @Autowired
//    private EntityManagerFactory entityManagerFactory;
//
//    public Session getSession() {
//        return entityManagerFactory.unwrap(SessionFactory.class).openSession();
//    }


//    @Bean
//    public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf) {
//        return hemf.getSessionFactory();
//    }


//    @Bean
//    public SessionFactory sessionFactory(@Qualifier("entityManagerFactory") EntityManagerFactory emf){
//        return emf.unwrap(SessionFactory.class);
//    }

//    @Autowired
//    private EntityManagerFactory entityManagerFactory;
//
//    @Bean
//    public SessionFactory getSessionFactory() {
//        return entityManagerFactory.unwrap(SessionFactory.class);
//    }


}