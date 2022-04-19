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


    // 单向关联  一对一
    /**
     *  cascade 设置关联操作
     *      ALL        所有持久化操作
     *      PERSIST    只有插入才会执行关联操作
     *      MERGE      只有修改才会执行关联操作
     *      REMOVE     只有删除才会执行关联操作
     *
     *  fetch 设置是否懒加载
     *      EAGER 立即加载（默认）
     *      LAZY 懒加载（ 直到用到对象才会进行查询，因为不是所有的关联对象 都需要用到）
     *
     *   orphanRemoval  关联移除（通常在修改的时候会用到）
     *      一旦把关联的数据设置null ，或者修改为其他的关联数据， 如果想删除关联数据， 就可以设置true
     *
     *   optional  限制关联的对象不能为null
     *      true 可以为null(默认 ) false 不能为null
     *
     *   mappedBy  将外键约束执行另一方维护(通常在双向关联关系中，会放弃一方的外键约束）
     *      值= 另一方关联属性名
     **/
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="account_id")// 设置外键的字段名
    private Account account;

}