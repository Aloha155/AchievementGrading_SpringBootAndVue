package com.bank.performance.entity;


import com.bank.performance.core.base.BaseEntity;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * @author yj
 * @date 2021-04-26 11:12
 */
@Entity
@Table(name = "address")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address extends BaseEntity implements Serializable {

    private String province;

    private String city;

    private String area;

    private String detailedAddress;


}