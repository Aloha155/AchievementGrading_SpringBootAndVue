package com.bank.performance.entity;


import com.bank.performance.core.base.BaseEntity;
import com.bank.performance.constant.RoleGrade;
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
 * 角色
 * @author yj
 * @date 2021-04-26 9:36
 */
@Entity
@Table(name = "role")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity implements Serializable {

    private String name;

    private RoleGrade roleGrade;

}