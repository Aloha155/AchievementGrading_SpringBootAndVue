package com.bank.performance.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.bank.performance.core.base.BaseEntity;
import com.bank.performance.constant.BusinessState;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * 业务表
 *
 * @author yj
 * @date 2021-04-26 9:38
 */
@Entity
@Table(name = "business", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "code"})})
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Business extends BaseEntity implements Serializable {

    private String name;

    private String code;

    private BusinessState state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @JsonIgnoreProperties(value = "businessList")
    private Department department;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Business business = (Business) o;
        return Objects.equals(name, business.name) && Objects.equals(code, business.code) && state == business.state && Objects.equals(department, business.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, code, state, department);
    }
}