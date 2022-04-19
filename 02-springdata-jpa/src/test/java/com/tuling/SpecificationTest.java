package com.tuling;

import com.tuling.config.SpringDataJPAConfig;
import com.tuling.pojo.Customer;
import com.tuling.repositories.CustomerSpecificationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;


@ContextConfiguration(classes = SpringDataJPAConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpecificationTest {

    @Autowired
    CustomerSpecificationRepository repository;

    @Test
    public void test01() {

        Iterable<Customer> customers = repository.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                // root: from Customer,获取列
                // criteriaBuilder: where 设置各种条件 (> < in ...)
                // query:  组合 (order by, where)

                return null;
            }
        });

        System.out.println(customers);
    }


    /**
     * 查询客户范围 (in)
     * id >大于
     * 地址  精确
     */
    @Test
    public void test02() {

        Iterable<Customer> customers = repository.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                // root: from Customer,获取列
                // criteriaBuilder: where 设置各种条件 (> < in ...)
                // query:  组合 (order by, where)
                Path<Object> custId = root.get("custId");
                Path<Object> custName = root.get("custName");
                Path<Object> custAddress = root.get("custAddress");

                //参数1: 为哪个字段设置条件  参数2: 值
                Predicate predicate = cb.equal(custAddress, "beijing");

                return predicate;
            }
        });

        System.out.println(customers);
    }


    @Test
    public void test03() {

        Iterable<Customer> customers = repository.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                // root: from Customer,获取列
                // criteriaBuilder: where 设置各种条件 (> < in ...)
                // query:  组合 (order by, where)
                Path<Long> custId = root.get("custId");
                Path<Object> custName = root.get("custName");
                Path<Object> custAddress = root.get("custAddress");

                //参数1: 为哪个字段设置条件  参数2: 值
                Predicate custAddressP = cb.equal(custAddress, "beijing");
                Predicate custIdP = cb.greaterThan(custId, 0L);

                CriteriaBuilder.In<Object> in = cb.in(custName);
                in.value("徐庶").value("王五");

                Predicate and = cb.and(custAddressP, custIdP, in);

                return and;
            }
        });

        System.out.println(customers);
    }


    @Test
    public void test04() {

        Customer params = new Customer();
        params.setCustAddress("Beijing");
        params.setCustId(0L);
        params.setCustName("徐庶,王五");

        Iterable<Customer> customers = repository.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                // root: from Customer,获取列
                // criteriaBuilder: where 设置各种条件 (> < in ...)
                // query:  组合 (order by, where)
                Path<Long> custId = root.get("custId");
                Path<Object> custName = root.get("custName");
                Path<Object> custAddress = root.get("custAddress");

                //参数1: 为哪个字段设置条件  参数2: 值
                List<Predicate> list = new ArrayList<>();

                if (!StringUtils.isEmpty(params.getCustAddress())){
                    list.add(cb.equal(custAddress, "beijing"));
                }

                if (params.getCustId()>-1){
                    list.add(cb.greaterThan(custId, 0L));
                }

                if (!StringUtils.isEmpty(params.getCustName())){
                    CriteriaBuilder.In<Object> in = cb.in(custName);
                    in.value("徐庶").value("王五");
                    list.add(in);
                }

                Predicate and = cb.and(list.toArray(new Predicate[list.size()]));
                return and;
            }
        });

        System.out.println(customers);
    }


    @Test
    public void test05() {

        Customer params = new Customer();
        params.setCustAddress("Beijing");
        params.setCustId(0L);
        params.setCustName("徐庶,王五");

        Iterable<Customer> customers = repository.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                // root: from Customer,获取列
                // criteriaBuilder: where 设置各种条件 (> < in ...)
                // query:  组合 (order by, where)

                //1.通过root拿到需要设置条件的字段
                Path<Long> custId = root.get("custId");
                Path<Object> custName = root.get("custName");
                Path<Object> custAddress = root.get("custAddress");


                //2.通过CriteriaBuilder设置不同类型条件
                List<Predicate> list = new ArrayList<>();
                if (!StringUtils.isEmpty(params.getCustAddress())){
                    //参数1: 为哪个字段设置条件  参数2: 值
                    list.add(cb.equal(custAddress, "beijing"));
                }

                if (params.getCustId()>-1){
                    list.add(cb.greaterThan(custId, 0L));
                }

                if (!StringUtils.isEmpty(params.getCustName())){
                    CriteriaBuilder.In<Object> in = cb.in(custName);
                    in.value("徐庶").value("王五");
                    list.add(in);
                }

                //3.组合条件
                Predicate and = cb.and(list.toArray(new Predicate[list.size()]));

                Order desc = cb.desc(custId);

                return query.where(and).orderBy(desc).getRestriction();
            }
        });

        System.out.println(customers);
    }

}