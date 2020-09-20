package cn.edu.nciae.contentcenter.common.dto;

import lombok.Data;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/4/19 7:33 PM
 */
@Data
public class PageParametersDTO {
    private Boolean paging;
    private Integer offset;
    private Integer limit;
    private Integer page;
    PageParametersDTO() {
        filterEmptyValue();
    }
    public void filterEmptyValue() {
        this.paging = this.paging == null ? false : this.paging;
        this.offset = this.offset == null ? 0 : this.offset;
        this.limit = this.limit == null ? 10 : this.limit;
        this.page = this.page == null ? 1 : this.page;
    }
}
