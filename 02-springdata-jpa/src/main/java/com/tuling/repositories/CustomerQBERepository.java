package com.tuling.repositories;

import com.tuling.pojo.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface CustomerQBERepository
        extends PagingAndSortingRepository<Customer,Long>,
        QueryByExampleExecutor<Customer> {

}


















