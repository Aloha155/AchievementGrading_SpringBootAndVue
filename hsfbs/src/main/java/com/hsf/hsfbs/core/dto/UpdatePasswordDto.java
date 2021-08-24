package com.hsf.hsfbs.core.dto;


import com.hsf.hsfbs.core.annotations.Auth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;


/**
 * @author yj
 * @date 2021-04-27 9:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordDto implements Serializable {

    private String oldPassword;

    private String newPassword;



}