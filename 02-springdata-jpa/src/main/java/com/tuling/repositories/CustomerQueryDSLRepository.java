package com.tuling.repositories;

import com.tuling.pojo.Customer;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * CrudRepository<类名,主键类型>
 */
public interface CustomerQueryDSLRepository
        extends PagingAndSortingRepository<Customer,Long>,
        QuerydslPredicateExecutor<Customer> {

}
