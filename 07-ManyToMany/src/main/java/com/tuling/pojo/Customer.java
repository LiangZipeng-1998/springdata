package com.tuling.pojo;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity     // 作为hibernate 实体类
@Table(name = "tb_customer")       // 映射的表明
@Data
@EntityListeners(AuditingEntityListener.class) //审计 设置监听
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long custId; //客户的主键

    @Column(name = "cust_name")
    private String custName;//客户名称

    @Column(name="cust_address")
    private String custAddress;//客户地址

    // 单向多对多
    @ManyToMany(cascade = CascadeType.ALL)
    /**
     * 中间表需要通过@JoinTable来维护外键：（不设置也会自动生成）
     * name 指定中间表的名称
     * joinColumns 设置本表的外键名称
     * inverseJoinColumns 设置关联表的外键名称
     */
    @JoinTable(
            name="tb_customer_role",
            joinColumns = {@JoinColumn(name="c_id")},
            inverseJoinColumns = {@JoinColumn(name="r_id")}
    )
    private List<Role> roles;

}





























