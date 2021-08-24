package com.hsf.hsfbs.core.vo;


import com.hsf.hsfbs.entity.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 * @author yj
 * @date 2021-04-27 9:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressVo implements Serializable {

    @NotEmpty
    private String province;

    @NotEmpty
    private String city;

    @NotEmpty
    private String area;

    @NotEmpty
    private String detailedAddress;


    public AddressVo(Address address) {
        BeanUtils.copyProperties(address, this);
    }
}