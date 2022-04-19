package com.tuling;

import com.tuling.config.SpringDataJPAConfig;
import com.tuling.pojo.Customer;
import com.tuling.repositories.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@ContextConfiguration(classes = SpringDataJPAConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class JpqlTest {

    @Autowired
    CustomerRepository repository;

    //查询
    @Test
    public void testR() {
        List<Customer> customer = repository.findCustomerByCustName("李四");
        System.out.println(customer);
    }

    @Test
    public void testU() {
        int result = repository.updateCustomer("修改", 6L);
        System.out.println(result);
    }

    @Test
    public void testD() {
        int result = repository.deleteCustomer(5L);
        System.out.println(result);
    }

    @Test
    public void testC() {
        int result = repository.insertCustomerBySelect(1L);
        System.out.println(result);
    }

    @Test
    public void testR_sql() {
        List<Customer> customer = repository.selectCustomerByCustNameBySql("李四");
        System.out.println(customer);
    }

}