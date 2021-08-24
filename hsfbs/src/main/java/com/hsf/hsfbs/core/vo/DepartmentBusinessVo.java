package com.hsf.hsfbs.core.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * @author yj
 * @date 2021-04-29 15:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentBusinessVo {

    private String departmentName;

    private List<BusinessVo> list;
}