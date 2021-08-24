package com.hsf.hsfbs.core.base;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.hsf.hsfbs.core.util.JsonUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 10)
    private Integer id;

    //创建时间 自动生成
    @CreatedDate
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createDate = null;

    //你这快捷键，我没法子
    //修改时间 自动生成
    @LastModifiedDate
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date lastModifiedDate = null;

    @Override
    public String toString() {
        return JsonUtil.objectToString(this);
    }
}