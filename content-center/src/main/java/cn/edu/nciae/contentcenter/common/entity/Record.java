package cn.edu.nciae.contentcenter.common.entity;

import cn.edu.nciae.contentcenter.common.dto.SubmissionDTO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author RexALun
 * @since 2020-02-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("record")
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 解题记录ID
     */
    @TableId(value = "record_id")
    private Long recordId;

    /**
     * 解题用户ID
     */
    private Long commitUid;

    /**
     * 题目ID
     */
    private Long pid;

    /**
     * 所属竞赛ID
     */
    private Long cid;

    /**
     * 提交语言
     */
    private Integer languageId;
//    private Language language;

    /**
     * 提交源码
     */
    private String sourceCode;

    /**
     * 提交时间
     */
    private Date commitTime;

    /**
     * 判题状态 not in submission
     */
    private Integer status;

    /**
     * 运行时间(MS) not in submission
     */
    private Double usedTime;

    /**
     * 运行所需内存大小(MB) not in submission
     */
    private Double usedMemory;

    /**
     * desc : use submission info to fill record
     * @param submissionDTO -
     */
    public void initRecordWithSubmissionDTO(SubmissionDTO submissionDTO) {
        this.recordId = submissionDTO.getSubmissionId();
        this.commitUid = submissionDTO.getUserId();
        this.pid = submissionDTO.getProblemId();
        this.cid = submissionDTO.getContestId();
        this.languageId = submissionDTO.getLanguage().getLanguageId();
        this.sourceCode = submissionDTO.getCode();
        this.commitTime = submissionDTO.getCommitTime();
    }

    /**
     * desc : use the runtime slug to fill the status field
     *        the status define as the fronted definition
     * @param runtimeSlug -
     */
    public void transferRuntimeSlugToStatus(String runtimeSlug) {
        switch (runtimeSlug) {
            case "CE":
                this.status = -2;
                break;
            case "WA":
                this.status = -1;
            case "AC":
                this.status = 0;
                break;
            case "TLE":
                this.status = 1;
                break;
            case "MLE":
                this.status = 3;
                break;
            case "RE":
                this.status = 4;
                break;
            case "SE":
                this.status = 5;
                break;
            case "Pending":
                this.status = 6;
                break;
            case "Judging":
                this.status = 7;
                break;
            case "PAC":
                this.status = 8;
                break;
            case "Submitting":
                this.status = 9;
                break;
            default:
                this.status = -3;
                break;
        }
    }

}
