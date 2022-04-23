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
     *      true 可以为null(默认) false 不能为null
     *
     *   mappedBy  将外键约束执行另一方维护(通常在双向关联关系中，会放弃一方的外键约束）
     *      值= 另一方关联属性名
     **/
    @OneToOne(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
            /*,optional = false*/)
    @JoinColumn(name="account_id")// 设置外键的字段名(本表)
    private Account account;

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

//    @Override
//    public String toString() {
//        return "Customer{" +
//                "custId=" + custId +
//                ", custName='" + custName + '\'' +
//                ", custAddress='" + custAddress + '\'' +
//                ", account=" + account +
//                ", messages=" + messages +  //会用到懒加载的数据,用到的时候就会执行懒加载查询
//                '}';
//    }

    // 单向多对多
    @ManyToMany(cascade = CascadeType.ALL)
    /*中间表需要通过@JoinTable来维护外键：（不设置也会自动生成）
     * name 指定中间表的名称
     * joinColumns 设置本表的外键名称
     * inverseJoinColumns 设置关联表的外键名称
     * */
    @JoinTable(
            name="tb_customer_role",
            joinColumns = {@JoinColumn(name="c_id")},
            inverseJoinColumns = {@JoinColumn(name="r_id")}
    )
    private List<Role> roles;

}





























