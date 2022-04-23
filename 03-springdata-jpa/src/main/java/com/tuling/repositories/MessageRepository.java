package com.tuling.repositories;

import com.tuling.pojo.Customer;
import com.tuling.pojo.Message;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MessageRepository
        extends PagingAndSortingRepository<Message,Long>{

    // 根据客户Id查询所有信息
    // 通过规定方法名来实现关联查询: 需要通过关联属性来进行匹配
    // 但是只能通过id来进行匹配
    List<Message> findByCustomer(Customer customer);

}
