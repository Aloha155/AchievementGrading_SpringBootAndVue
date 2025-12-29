package com.hsf.hsfbs.entity;


import com.hsf.hsfbs.core.base.BaseEntity;
import com.hsf.hsfbs.constant.Rate;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * @author yj
 * @date 2021-04-29 8:54
 */
@Entity
@Table(name = "performance")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Performance extends BaseEntity implements Serializable {

    private Integer userId;

    private Date rateTime;

    private String year;

    private String month;

    private String day;

    private Rate rate;

    private String remake;

    @CreatedBy
    @Column(name = "created_by")
    private Integer createdBy;

    //最后修改，自动获取
    @LastModifiedBy
    @Column(name = "last_modified_by")
    private Integer  lastModifiedBy;

}