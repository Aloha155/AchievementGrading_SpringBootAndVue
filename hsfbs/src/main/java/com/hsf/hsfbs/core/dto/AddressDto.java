package com.hsf.hsfbs.core.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;


/**
 * @author yj
 * @date 2021-04-27 9:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    @NotEmpty
    private String province;

    @NotEmpty
    private String city;

    @NotEmpty
    private String area;

    @NotEmpty
    private String detailedAddress;

}