package com.hsf.hsfbs.entity;


import com.hsf.hsfbs.core.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * 用户关联角色
 * @author yj
 * @date 2021-04-26 9:37
 */
@Entity
@Table(name = "user_role")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRole extends BaseEntity implements Serializable {

    private Integer userId;

    private Integer roleId;

}