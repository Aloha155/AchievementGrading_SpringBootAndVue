package com.hsf.hsfbs.core.dto;


import com.hsf.hsfbs.core.annotations.StringLength;
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
 * @date 2021-04-27 9:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {

    private Integer id;

    private String name;

    @NotNull
    private Integer age;

    @NotNull
    private UserSex sex;

    @NotEmpty
    private AddressDto addressee;

    private Date birthday;

    private Integer pay;

}