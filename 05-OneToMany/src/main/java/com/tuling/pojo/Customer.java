package com.tuling.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity     // 作为hibernate 实体类
@Table(name = "tb_customer")       // 映射的表明
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long custId; //客户的主键

    @Column(name = "cust_name")
    private String custName;//客户名称

    @Column(name="cust_address")
    private String custAddress;//客户地址

    // 一对多
    /**
     * fetch 默认是懒加载
     *
     * 懒加载的优点:
     *  1.提高查询性能
     */
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id") //设置外键关联(关联表)
    private List<Message> messages;

    @Override
    public String toString() {
        return "Customer{" +
                "custId=" + custId +
                ", custName='" + custName + '\'' +
                ", custAddress='" + custAddress + '\'' +
                ", messages=" + messages +  //会用到懒加载的数据,用到的时候就会执行懒加载查询
                '}';
    }

}
