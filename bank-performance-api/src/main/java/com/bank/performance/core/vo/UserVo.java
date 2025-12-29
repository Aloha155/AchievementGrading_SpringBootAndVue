package com.bank.performance.core.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.bank.performance.core.util.SafetyUtil;
import com.bank.performance.constant.UserSex;
import com.bank.performance.entity.Address;
import com.bank.performance.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Date;


/**
 * @author yj
 * @date 2021-04-26 15:42
 */
@Data
@NoArgsConstructor
public class UserVo {

    private Integer id;

    private String name;

    @NotEmpty
    private String phone;

    private Date registerTime;

    @Email(message = "邮箱格式错误")
    private String email;

    private AddressVo addressee;

    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date birthday;

    private Integer age;

    @NotEmpty
    private UserSex sex;

    private String role;

    private String departmentName;

    private Integer pay;

    public UserVo(User user, Address address, String role, String departmentName) {
        BeanUtils.copyProperties(user, this);
        this.phone = SafetyUtil.phoneSafety(user.getPhone());
        this.email = SafetyUtil.emailSafety(user.getEmail());
        this.addressee = new AddressVo(address);
        this.role = role;
        this.departmentName = departmentName;
    }

}