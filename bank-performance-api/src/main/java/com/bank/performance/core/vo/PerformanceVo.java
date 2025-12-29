package com.bank.performance.core.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.bank.performance.constant.Rate;
import com.bank.performance.entity.Performance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;


/**
 * @author yj
 * @date 2021-04-29 11:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceVo {

    private String username;

    private Rate rate;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date rateTime;

    private String roleName;

    private String createdName;

    private String updateName;

    private String remake;


    public PerformanceVo(Performance performance, String roleName, String username, String createdName, String updateName) {
        BeanUtils.copyProperties(performance, this);
        this.roleName = roleName;
        this.username = username;
        this.createdName = createdName;
        this.updateName = updateName;
    }
}