package cn.edu.nciae.usercenter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @Author: RexALun
 * @Date: 2019/12/26 11:07 PM
 * @Version 1.0
 * @Annotation :
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserinfoDTO {

    private Long userid;

    private Date regTime;

    private Integer solvedNum;
}
