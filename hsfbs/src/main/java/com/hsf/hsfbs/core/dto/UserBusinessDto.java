package com.hsf.hsfbs.core.dto;


import com.hsf.hsfbs.entity.constant.UserBusinessState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * @author yj
 * @date 2021-05-07 17:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBusinessDto implements Serializable {
    private Integer userId;
    private Integer businessId;
    private String businessName;
    private String businessCode;
    private Integer departmentId;
    private String adminName;
    private String adminPhone;
    private String phone;
    private String username;
    private UserBusinessState userBusinessStat;

}