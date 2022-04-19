package com.tuling.repositories;

import com.tuling.pojo.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * CrudRepository<类名,主键类型>
 */
public interface CustomerMethodNameRepository extends PagingAndSortingRepository<Customer,Long> {

    /**
     * 使用Get或Set后跟着的属性名
     * @param custName
     * @return
     */
    List<Customer> findByCustName(String custName);

    boolean existsByCustName(String custName);

    @Transactional
    @Modifying
    int deleteByCustId(Long custId);

    List<Customer> findByCustNameLike(String custName);

}


















