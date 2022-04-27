package com.tuling;

import com.tuling.config.SpringDataJPAConfig;
import com.tuling.pojo.Customer;
import com.tuling.pojo.Message;
import com.tuling.repositories.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ContextConfiguration(classes = SpringDataJPAConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class OneToManyTest {

    @Autowired
    CustomerRepository repository;

    /**
     * 插入
     *
     * Hibernate: insert into tb_customer (cust_address, cust_name) values (?, ?)
     * Hibernate: insert into tb_message (info) values (?)
     * Hibernate: insert into tb_message (info) values (?)
     * Hibernate: update tb_message set customer_id=? where id=?
     * Hibernate: update tb_message set customer_id=? where id=?
     */
    @Test
    public void test01(){

        List<Message> messageList = new ArrayList<>();
        messageList.add(new Message("您好!"));
        messageList.add(new Message("在吗?"));

        Customer customer = new Customer();
        customer.setCustName("徐庶帅哥");
        customer.setMessages(messageList);

        repository.save(customer);

    }

    //查询 默认(懒加载)
    @Test
    @Transactional(readOnly = true) //加强性能,直到整个方法执行完之后,才会关闭Session
    public void test02(){

        Optional<Customer> byId = repository.findById(1l); //执行到此处,会关闭Session
        System.out.println("=================");
        System.out.println(byId);

    }

    /**
     * 删除
     *
     * Hibernate: select customer0_.id as id1_1_0_, customer0_.cust_address as cust_add2_1_0_, customer0_.cust_name as cust_nam3_1_0_, messages1_.customer_id as customer3_2_1_, messages1_.id as id1_2_1_, messages1_.id as id1_2_2_, messages1_.info as info2_2_2_ from tb_customer customer0_ left outer join tb_message messages1_ on customer0_.id=messages1_.customer_id where customer0_.id=?
     * Hibernate: select account0_.id as id1_0_2_, account0_.customer_id as customer4_0_2_, account0_.password as password2_0_2_, account0_.username as username3_0_2_, customer1_.id as id1_1_0_, customer1_.cust_address as cust_add2_1_0_, customer1_.cust_name as cust_nam3_1_0_, messages2_.customer_id as customer3_2_4_, messages2_.id as id1_2_4_, messages2_.id as id1_2_1_, messages2_.info as info2_2_1_ from tb_account account0_ left outer join tb_customer customer1_ on account0_.customer_id=customer1_.id left outer join tb_message messages2_ on customer1_.id=messages2_.customer_id where account0_.customer_id=?
     * Hibernate: update tb_message set customer_id=null where customer_id=?
     * Hibernate: delete from tb_message where id=?
     * Hibernate: delete from tb_message where id=?
     * Hibernate: delete from tb_customer where id=?
     */
    @Test
    public void test03(){
        repository.deleteById(1l);
    }

}
