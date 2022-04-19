package com.tuling;

import com.tuling.config.SpringDataJPAConfig;
import com.tuling.pojo.Customer;
import com.tuling.repositories.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Optional;

//基于 JUnit4 spring 单元测试
//@ContextConfiguration(locations = "/spring.xml")
@ContextConfiguration(classes = SpringDataJPAConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class springdataJpaTest {

    @Autowired
    CustomerRepository repository;

    //查询
    @Test
    public void testR() {
        Optional<Customer> byId = repository.findById(1L);
        System.out.println(byId.get());
    }

    //添加
    @Test
    public void testC() {
        Customer customer = new Customer();
        customer.setCustName("李四");
        System.out.println(repository.save(customer));
    }

    //修改
    @Test
    public void testU() {
        Customer customer = new Customer();
        customer.setCustId(3L);
        customer.setCustName("修改");
        repository.save(customer);
    }

    //删除
    @Test
    public void testD() {
        Customer customer = new Customer();
        customer.setCustId(3L);
        repository.delete(customer);
    }

    @Test
    public void testFindAll(){
        Iterable<Customer> allById = repository.findAllById(Arrays.asList(1l, 4l, 5l));
        System.out.println(allById);
    }

}
