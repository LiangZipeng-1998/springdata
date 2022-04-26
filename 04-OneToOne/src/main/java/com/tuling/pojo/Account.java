package com.tuling.pojo;

import lombok.*;

import javax.persistence.*;

/**
 * 一对一
 * 一个客户对一个账户
 */

/**
 * @Data 等同于以下四个注解
 *
 * @Getter    生成所有属性的get方法
 * @Setter    生成所有属性的set方法
 * @RequiredArgsConstructor  生成final属性的构造函数， 如果没有final就是无参构造函数
 * @EqualsAndHashCode
 **/
@Entity
@Table(name="tb_account")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //配置生成策略
    private Long id;
    private String username;
    private String password;

    @OneToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

}
