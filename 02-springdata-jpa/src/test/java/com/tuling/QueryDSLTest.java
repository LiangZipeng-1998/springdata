package com.tuling;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tuling.config.SpringDataJPAConfig;
import com.tuling.pojo.Customer;
import com.tuling.pojo.QCustomer;
import com.tuling.repositories.CustomerQueryDSLRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ContextConfiguration(classes = SpringDataJPAConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class QueryDSLTest {

    @Autowired
    CustomerQueryDSLRepository repository;

    //查询
    @Test
    public void test01() {
        QCustomer customer = QCustomer.customer;

        //通过Id查找
        BooleanExpression eq = customer.custId.eq(1L);

        System.out.println(repository.findOne(eq));

    }

    /**
     * 查询客户范围 (in)
     * id >大于
     * 地址  精确
     */
    @Test
    public void test02() {
        QCustomer customer = QCustomer.customer;

        //通过Id查找
        BooleanExpression and = customer.custName.in("徐庶","王五")
                .and(customer.custId.gt(0L))
                .and(customer.custAddress.eq("beijing"));

        System.out.println(repository.findOne(and));

    }


    @Test
    public void test03() {

        Customer params = new Customer();
        params.setCustAddress("BEIJING");
        params.setCustId(0L);
        params.setCustName("徐庶,王五");

        QCustomer customer = QCustomer.customer;

        //初始条件 1=1  永远都成立的条件
        BooleanExpression expression = customer.isNotNull().or(customer.isNull());

        expression = params.getCustId()>-1?
                expression.and(customer.custId.gt(params.getCustId())): expression;
        expression = !StringUtils.isEmpty(params.getCustName())?
                expression.and(customer.custName.in(params.getCustName().split(","))):expression;
        expression = !StringUtils.isEmpty(params.getCustAddress())?
                expression.and(customer.custAddress.eq(params.getCustAddress())):expression;

        System.out.println(repository.findAll(expression));

    }


    /**
     * 自定义查询、分组
     * 需要使用原生态的方式（Specification）
     *
     * 通过Repository进行查询, 列、表都是固定
     */

    // 解决线程安全问题
    @PersistenceContext
    EntityManager em;

    @Test
    public void test04() {

        JPAQueryFactory factory = new JPAQueryFactory(em);

        QCustomer customer = QCustomer.customer;

        //构建基于QueryDSL的查询
        JPAQuery<Tuple> tupleJPAQuery = factory.select(customer.custId, customer.custName)
                .from(customer)
                .where(customer.custId.eq(1L))
                .orderBy(customer.custId.desc());

        //执行查询
        List<Tuple> fetch = tupleJPAQuery.fetch();

        //处理返回数据
        for (Tuple tuple:fetch) {
            System.out.println(tuple.get(customer.custId));
            System.out.println(tuple.get(customer.custName));
        }

    }


    @Test
    public void test05() {

        JPAQueryFactory factory = new JPAQueryFactory(em);

        QCustomer customer = QCustomer.customer;

        //构建基于QueryDSL的查询
        JPAQuery<Long> longJPAQuery = factory.select(customer.custId.sum())
                .from(customer)
                .orderBy(customer.custId.desc());

        //执行查询
        List<Long> fetch = longJPAQuery.fetch();

        //处理返回数据
        for (Long sum:fetch) {
            System.out.println(sum);
        }

    }


}
