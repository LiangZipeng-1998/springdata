package com.tuling;

import com.tuling.config.SpringDataJPAConfig;
import com.tuling.pojo.Customer;
import com.tuling.pojo.Message;
import com.tuling.repositories.MessageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@ContextConfiguration(classes = SpringDataJPAConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ManyToOneTest {

    @Autowired
    MessageRepository repository;

    /**
     * 多对一 插入
     * 得出: 当插入"多"的数据的时候,使用多对一的关联关系是更加合理的
     *
     * Hibernate: insert into tb_customer (cust_address, cust_name) values (?, ?)
     * Hibernate: insert into tb_message (customer_id, info) values (?, ?)
     * Hibernate: insert into tb_message (customer_id, info) values (?, ?)
     */
    @Test
    public void test01(){

        //一
        Customer customer = new Customer();
        customer.setCustName("司马懿");

        //多
        List<Message> list = new ArrayList<>();
        list.add(new Message("你好!",customer));
        list.add(new Message("在吗?",customer));

        repository.saveAll(list);

    }


    // 多对一: 根据客户id查询对应的所有信息
    // 通过"一"进行条件查询,在一对多中实现是更为合理的
    // select message0_.id as id1_2_, message0_.customer_id as customer3_2_, message0_.info as info2_2_ from tb_message message0_ where message0_.customer_id=?
    @Test
    public void test02(){

        Customer customer = new Customer();
        customer.setCustId(1L);
        customer.setCustName("xxx");

        List<Message> messages = repository.findByCustomer(customer);

        repository.deleteAll(messages);

        //隐式的调用toString方法
        System.out.println(messages);


    }


}
