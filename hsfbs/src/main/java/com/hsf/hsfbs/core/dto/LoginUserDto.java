package com.hsf.hsfbs.core.dto;


import com.hsf.hsfbs.core.annotations.StringLength;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


/**
 * @author yj
 * @date 2021-04-26 15:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto {

    @NotEmpty
    @Pattern(regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$", message = "手机号不正确")
    @StringLength(min = 11, max = 11, message = "手机号长度限制11")
    private String phone;


    @NotEmpty(message = "密码不能为空")
    @StringLength(min = 6, max = 10, message = "密码限制6-10位")
    private String password;


}