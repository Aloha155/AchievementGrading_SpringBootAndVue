package com.hsf.hsfbs.entity.entity;


import com.hsf.hsfbs.core.base.BaseEntity;
import com.hsf.hsfbs.entity.constant.UserSex;
import com.hsf.hsfbs.entity.constant.UserState;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * hello
 */

/**
 * 用户
 *
 * @author yj
 * @date 2021-04-26 9:34
 */
@Entity
@Table(name = "user",uniqueConstraints = {@UniqueConstraint(columnNames = {"phone"})})
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity implements Serializable {

    @NotEmpty
    private String name;

    @NotNull
    @Column(length = 32)
    private String password;

    @NotEmpty
    @Column(length = 11)
    @Pattern(regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$", message = "手机号不正确")
    private String phone;

    @Email(message = "邮箱格式错误")

    private String email;

    @NotNull
    private Integer age;

    private Date birthday;

    @NotNull
    private UserSex sex;

    private Integer addresseeId;

    private Integer pay;

    //默认是使用
    private UserState userState = UserState.PASS;



}