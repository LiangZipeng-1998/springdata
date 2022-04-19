package com.tuling.test;

import com.tuling.pojo.Customer;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaTest {

    EntityManagerFactory factory;

    @Before
    public void before(){
        factory = Persistence.createEntityManagerFactory("hibernateJPA");
    }

    //添加
    @Test
    public void testC(){
        EntityManager em = factory.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Customer customer = new Customer();
        customer.setCustName("张三");

        em.persist(customer);

        tx.commit();
    }

    //立即查询
    @Test
    public void testR(){
        EntityManager em = factory.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();


        Customer customer = em.find(Customer.class, 1L);
        System.out.println("====================");
        System.out.println(customer);

        tx.commit();
    }

    //延迟查询
    @Test
    public void testR_lazy(){
        EntityManager em = factory.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();


        Customer customer = em.getReference(Customer.class, 1L);
        System.out.println("====================");
        System.out.println(customer);

        tx.commit();
    }

    //修改
    @Test
    public void testU(){
        EntityManager em = factory.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Customer customer = new Customer();
//        customer.setCustId(2L);
        customer.setCustName("王五");

        /**
         * 如果指定了主键:
         *      更新: 1.先查询  看是否有变化
         *      如果有变化    更新
         *      如果没有变化  不更新
         * 如果没有指定主键:
         *      插入
         */
        em.merge(customer);

        tx.commit();
    }


    //更新
    @Test
    public void test_JPQL(){
        EntityManager em = factory.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

//        String jpql = "UPDATE Customer set custName=:name where custId=:id";
//
//        em.createQuery(jpql)
//                .setParameter("name","小鹏")
//                .setParameter("id",1L)
//                .executeUpdate();

        String sql = "UPDATE cst_customer set cust_name=:name where id=:id";
        em.createNativeQuery(sql)
                .setParameter("name","小鹏")
                .setParameter("id",1L)
                .executeUpdate();

        tx.commit();
    }


    //修改
    @Test
    public void testD(){
        EntityManager em = factory.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Customer customer = em.find(Customer.class,2L);

        em.remove(customer);

        tx.commit();
    }

    @Test
    public void testStatus(){
        EntityManager em = factory.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();


        Customer customer = new Customer();  // 临时状态  (瞬时状态)
        customer.setCustId(6L);  // 游离状态
        customer = em.find(Customer.class,1L); // 持久状态

        em.remove(customer); //删除状态 (销毁状态)

        tx.commit();
    }

    @Test
    public void testStatus02(){
        EntityManager em = factory.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // 持久状态 (持久状态进行了修改会同步数据库)
        Customer customer = em.find(Customer.class,1L);

        em.remove(customer);
        em.persist(customer);

//        customer.setCustName("徐庶");

        tx.commit();
    }

    //一级缓存
    @Test
    public void testCatchI(){
        EntityManager em = factory.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Customer customer = em.find(Customer.class,1L);
        Customer customer2 = em.find(Customer.class,1L);

        tx.commit();
    }

}
