package com.tuling.test;

import com.tuling.pojo.Customer;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class OpenJpaTest {

    EntityManagerFactory factory;

    @Before
    public void before(){
        factory = Persistence.createEntityManagerFactory("openJpa");
    }

    @Test
    public void testC(){
        EntityManager em = factory.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Customer customer = new Customer();
        customer.setCustName("李四");

        em.persist(customer);

        tx.commit();
    }

}
