package com.tuling.repositories;

import com.tuling.pojo.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository
        extends PagingAndSortingRepository<Customer,Long>{

}
