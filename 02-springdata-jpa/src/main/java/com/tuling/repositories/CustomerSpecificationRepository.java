package com.tuling.repositories;

import com.tuling.pojo.Customer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * CrudRepository<类名,主键类型>
 */
public interface CustomerSpecificationRepository
        extends PagingAndSortingRepository<Customer,Long>,
        JpaSpecificationExecutor<Customer> {


}


















