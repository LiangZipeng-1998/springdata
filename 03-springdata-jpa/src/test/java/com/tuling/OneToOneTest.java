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

        account.setCustomer(customer);

        repository.save(customer);

    }


    // 插入
    @Test
    // 为什么懒加载要配置事务 ：
    // 当通过repository调用完查询方法，session就会立即关闭， 一旦session你就不能查询，
    // 加了事务后， 就能让session直到事务方法执行完毕后才会关闭
    public void test02() {

        Optional<Customer> customer = repository.findById(2L);// 只查询出客户， session关闭
        System.out.println("=================");
        System.out.println(customer.get());// toString

    }


    @Test
    public void testD(){
        repository.deleteById(2L);
    }

    @Test
    public void testU(){
        Customer customer = new Customer();
        customer.setCustId(16L);
        customer.setCustName("徐庶");
        customer.setAccount(null);
        repository.save(customer);
    }

}
