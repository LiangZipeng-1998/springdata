package com.tuling.repositories;

import com.tuling.pojo.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * CrudRepository<类名,主键类型>
 */
public interface CustomerRepository extends PagingAndSortingRepository<Customer,Long> {

    //增删改查

    //查询
    /**
     * 1.jpql(原生SQL)
     *      a.@Query
     *          i.查询如果返回单个实体 就用pojo接收,如果是多个需要通过集合
     *          ii.参数设置方式
     *              1.索引:  ?数字
     *              2.具名:  :参数名  结合@Param注解指定参数名字
     *          iii.增删改
     *              1.要加上事务的支持
     *              @Transactional //通常会放在业务逻辑层上去声明
     *              @Modifying     //通知springdatajpa 是增删改的操作
     *              2.如果是插入方法: 一定只能在Hibernate下才支持 (Insert into...select)
     */
//    @Query("from Customer where custName=?1")
//    List<Customer> findCustomerByCustName(String custName);
    @Query("from Customer where custName=:custName")
    List<Customer> findCustomerByCustName(@Param("custName") String custName);


    //修改
    @Transactional
    @Modifying  //通知springdatajpa 是增删改的操作
    @Query("update Customer c set c.custName=:custName where c.custId=:id")
    int updateCustomer(@Param("custName") String custName, @Param("id") Long id);

    //修改
    @Transactional
    @Modifying  //通知springdatajpa 是增删改的操作
    @Query("delete from Customer c where c.custId=?1")
    int deleteCustomer(Long id);

    //新增 JPQL
    @Transactional
    @Modifying  //通知springdatajpa 是增删改的操作
    @Query("INSERT INTO Customer (custName) select c.custName from Customer c where c.custId=?1")
    int insertCustomerBySelect(Long id);

    //原生SQL
    @Query(value = "select * from cst_customer where cust_name=:custName",nativeQuery = true)
    List<Customer> selectCustomerByCustNameBySql(@Param("custName") String custName);

}


















