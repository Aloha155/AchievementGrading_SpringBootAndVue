package com.hsf.hsfbs.core.dto;


import com.hsf.hsfbs.core.annotations.StringLength;
import com.hsf.hsfbs.entity.constant.RoleGrade;
import com.hsf.hsfbs.entity.constant.UserSex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;


/**
 * @author yj
 * @date 2021-04-26 17:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDto {

    @NotEmpty(message = "名字不能为")
    private String name;

    @NotEmpty(message = "手机格式错误")
    @Pattern(regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$", message = "手机号不正确")
    private String phone;

    @NotEmpty(message = "密码不能为空")
    @StringLength(min = 6, max = 10, message = "密码限制6-10位")
    private String password;

    @Email(message = "邮箱格式错误")
    private String email;

    @NotNull(message = "年龄格式错误")
    private Integer age;

    @NotNull(message = "性别格式错误")
    private UserSex sex;

    @NotNull(message = "地址不能设置空")
    private AddressDto addressee;

    private Date birthday;

    @NotNull(message = "角色不能设置空")
    private Integer roleId;

    private Integer departmentId;

    private Integer pay;

}