package cn.edu.nciae.usercenter.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/1/31 1:08 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageVO<T> {
    /**
     * Status
     */
    private String error;

    /**
     * Message Body
     */
    private T data;
}
