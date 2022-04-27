package com.tuling.pojo;

import lombok.*;

import javax.persistence.*;

/**
 * 一对一
 * 一个客户对一个账户
 */
@Entity
@Table(name="tb_account")
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //配置生成策略
    private Long id;
    private String username;
    private String password;

    @OneToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
