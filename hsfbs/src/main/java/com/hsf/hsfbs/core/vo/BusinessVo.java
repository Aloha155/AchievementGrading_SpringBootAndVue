package com.hsf.hsfbs.core.vo;


import com.hsf.hsfbs.entity.constant.BusinessState;
import com.hsf.hsfbs.entity.entity.Business;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.List;


/**
 * @author yj
 * @date 2021-04-29 15:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessVo {

    private Integer id;

    private String departmentName;

    private String name;

    private String code;

    private BusinessState state;

    public BusinessVo(Business business,String departmentName){
        BeanUtils.copyProperties(business, this);
        this.departmentName = departmentName;
    }
}