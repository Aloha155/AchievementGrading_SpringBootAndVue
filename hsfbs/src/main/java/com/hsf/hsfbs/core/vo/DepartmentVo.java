package com.hsf.hsfbs.core.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.hsf.hsfbs.entity.entity.Department;

/**
 * @author yujie
 * @date 2021-05-06 21:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentVo {
    private Integer id;

    private String name;

    public DepartmentVo(Department department) {
        this.id = department.getId();
        this.name = department.getName();
    }
}
