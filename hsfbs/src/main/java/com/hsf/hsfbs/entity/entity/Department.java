package com.hsf.hsfbs.entity.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hsf.hsfbs.core.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * 部门
 * @author yj
 * @date 2021-04-26 9:37
 */
@Entity
@Table(name = "department")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Department extends BaseEntity implements Serializable {

    private String name;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "department")
    @JsonIgnoreProperties(value = "department")
    private List<Business> businessList = new ArrayList<>();

    public Department(String name){
        this.name = name;
    }

}