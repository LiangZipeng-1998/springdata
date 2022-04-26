package com.tuling.pojo;

import lombok.Data;

import javax.persistence.*;

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

}





























