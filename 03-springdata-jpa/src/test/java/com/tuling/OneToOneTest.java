package com.tuling;

import com.tuling.config.SpringDataJPAConfig;
import com.tuling.pojo.Account;
import com.tuling.pojo.Customer;
import com.tuling.repositories.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

@ContextConfiguration(classes = SpringDataJPAConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class OneToOneTest {

    @Autowired
    CustomerRepository repository;

    //插入
    @Test
    public void test01() {

        //初始化数据
        Account account = new Account();
        account.setUsername("xushu");


        Customer customer = new Customer();
        customer.setCustName("徐庶");
        customer.setAccount(account);

        repository.save(customer);

    }


    //查询
    @Test
    public void test02() {

        Optional<Customer> byId = repository.findById(2L);

        System.out.println(byId);

    }

}
