package com.tuling.test;

import com.tuling.pojo.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class HibernateTest {

    // Session工厂  Session:数据库回话  代码和数据库的一个桥梁
    private SessionFactory sf;

    @Before
    public void inti(){
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("/hibernate.cfg.xml").build();
        //根据服务注册类创建一个元数据资源集,同时构建元数据并生成应用一般唯一的session工厂
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @Test
    public void testC(){
        // Session进行持久化操作
        try (Session session = sf.openSession()){
            Transaction tx = session.beginTransaction();

            Customer customer = new Customer();
            customer.setCustName("徐庶");

            session.save(customer);

            tx.commit();
        }
    }

    @Test
    public void testR(){
        // Session进行持久化操作
        try (Session session = sf.openSession()){
            Transaction tx = session.beginTransaction();

            Customer customer = session.find(Customer.class, 1L);
            System.out.println("=======================");
            System.out.println(customer);

            tx.commit();
        }
    }

    @Test
    public void test_lazy(){
        // Session进行持久化操作
        try (Session session = sf.openSession()){
            Transaction tx = session.beginTransaction();

            Customer customer = session.load(Customer.class, 1L);
            System.out.println("=======================");
            System.out.println(customer);

            tx.commit();
        }
    }

    @Test
    public void testU(){
        // Session进行持久化操作
        try (Session session = sf.openSession()){
            Transaction tx = session.beginTransaction();

            Customer customer = new Customer();
            //customer.setCustId(1L);
            customer.setCustName("梓鹏");

            // 插入 session.save(customer);
            // 更新 session.update(customer);
            session.saveOrUpdate(customer);

            tx.commit();
        }
    }

    @Test
    public void testD(){
        // Session进行持久化操作
        try (Session session = sf.openSession()){
            Transaction tx = session.beginTransaction();

            Customer customer = new Customer();
            customer.setCustId(1L);

            session.remove(customer);

            tx.commit();
        }
    }

    @Test
    public void testHQL(){
        try (Session session = sf.openSession()){
            Transaction tx = session.beginTransaction();

            String hql = " FROM Customer where custId=:id";
            List<Customer> resultList = session.createQuery(hql, Customer.class)
                    .setParameter("id",3L)
                    .getResultList();
            System.out.println(resultList);

            tx.commit();
        }
    }

}
