package com.hsf.hsfbs.entity;


import com.hsf.hsfbs.core.base.BaseEntity;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author yj
 * @date 2021-04-29 14:22
 */
@Entity
@Table(name = "department_business")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DepartmentBusiness extends BaseEntity implements Serializable {

    private Integer departmentId;




}