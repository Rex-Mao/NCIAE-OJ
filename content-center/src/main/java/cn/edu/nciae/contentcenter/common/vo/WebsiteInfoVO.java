package cn.edu.nciae.contentcenter.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/2/9 12:55 PM
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebsiteInfoVO {
    private String websiteBaseUrl;
    private String websiteName;
    private String websiteNameShortcut;
    private String websiteFooter;
    private Boolean allowRegistry;
    private Boolean submissionListShowAll;
}
