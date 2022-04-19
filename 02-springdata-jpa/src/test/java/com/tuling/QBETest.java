package com.tuling;

import com.tuling.config.SpringDataJPAConfig;
import com.tuling.pojo.Customer;
import com.tuling.repositories.CustomerQBERepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 动态条件查询
 * Query by Example
 *  只支持查询
 *      i.不支持嵌套或分组的属性约束,如firstname =? 0 or (firstname=? 1 and lastname=? 2)
 *      ii.只支持字符串 start/contains/ends/regex 匹配和其他属性类型的精确匹配
 */
@ContextConfiguration(classes = SpringDataJPAConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class QBETest {

    @Autowired
    CustomerQBERepository repository;

    /**
     * 简单实例  客户名称,客户地址  动态查询
     */
    @Test
    public void test01() {

        //查询条件
        Customer customer = new Customer();
        customer.setCustName("徐庶");
        customer.setCustAddress("广州");

        //通过Example构建查询条件
        Example<Customer> example = Example.of(customer);

        List<Customer> customers = (List<Customer>) repository.findAll(example);
        System.out.println(customers);
    }

    /**
     * 通过匹配器来进行条件的限制
     */
    @Test
    public void test02() {

        //查询条件
        Customer customer = new Customer();
        customer.setCustName("徐庶");
        customer.setCustAddress("jing");

        // 通过匹配器 对条件行为进行设置
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("custName")  //设置忽略的属性
//                .withIgnoreCase("custAddress") //设置忽略大小写
//                .withStringMatcher(ExampleMatcher.StringMatcher.ENDING) //对字符串进行了结尾匹配
//                .withMatcher("custAddress",m -> m.endsWith()) //针对单个条件进行限制,会使withIgnoreCase失效,需要单独设置ignoreCase
                .withMatcher("custAddress",ExampleMatcher.GenericPropertyMatchers.endsWith().ignoreCase())
                ;

        //通过Example构建查询条件
        Example<Customer> example = Example.of(customer,matcher);

        List<Customer> customers = (List<Customer>) repository.findAll(example);
        System.out.println(customers);
    }

}