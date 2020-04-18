package cn.edu.nciae.contentcenter.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * @author RexALun
 * @version 1.0
 * Annotation : use to transfer the urls to jwt
 * @date 2020/4/19 12:23 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClaimsDTO {
    Collection<String> urlResources;
}
