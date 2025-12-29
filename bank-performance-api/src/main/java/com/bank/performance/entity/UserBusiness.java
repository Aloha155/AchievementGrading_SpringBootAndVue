package com.bank.performance.entity;


import com.bank.performance.core.base.BaseEntity;
import com.bank.performance.constant.UserBusinessState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * @author yj
 * @date 2021-05-07 9:12
 */
@Entity
@Table(name = "user_business")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserBusiness extends BaseEntity implements Serializable {

    private Integer userId;

    private String adminName;

    private String adminPhone;

    private Integer departmentId;

    private String departmentName;

    private Integer businessId;

    private String businessName;

    private String businessCode;

    private String username;

    private String phone;

    private String remake;

    private String auditCause;

    private String auditRemake;

    private UserBusinessState userBusinessState;

    public UserBusiness(Integer userId, String adminName, String adminPhone, Integer departmentId, String departmentName, Integer businessId, String businessName, String businessCode, String username, String phone, String remake, UserBusinessState userBusinessState) {
        this.userId = userId;
        this.adminName = adminName;
        this.adminPhone = adminPhone;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.businessId = businessId;
        this.businessName = businessName;
        this.businessCode = businessCode;
        this.username = username;
        this.phone = phone;
        this.remake = remake;
        this.userBusinessState = userBusinessState;
    }
}