package cn.edu.nciae.contentcenter.controller;

import cn.edu.nciae.contentcenter.common.dto.SubmissionDTO;
import cn.edu.nciae.contentcenter.common.dto.SubmissionParametersDTO;
import cn.edu.nciae.contentcenter.common.entity.Language;
import cn.edu.nciae.contentcenter.common.entity.Record;
import cn.edu.nciae.contentcenter.common.vo.MessageVO;
import cn.edu.nciae.contentcenter.common.vo.SubmissionListVO;
import cn.edu.nciae.contentcenter.common.vo.SubmissionVO;
import cn.edu.nciae.contentcenter.service.ILanguageService;
import cn.edu.nciae.contentcenter.service.IRecordService;
import cn.edu.nciae.contentcenter.service.ISubmissionService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author RexALun
 * @version 1.0
 * Annotation : Use to accept the submission
 * @date 2020/2/10 5:11 PM
 */
@Slf4j
@RestController
public class SubmissionController {

    @Autowired
    private ISubmissionService submissionService;

    @Autowired
    private IRecordService recordService;

    @Autowired
    private ILanguageService languageService;

    /**
     * desc : create a new submission
     * @param submissionDTO -
     * @return submission ID
     * @throws IOException -
     */
    @PostMapping("/submission")
    public MessageVO<String> judgeSubmissionAndReturnSID(SubmissionDTO submissionDTO) throws IOException {
        Language language = languageService.getOne(Wrappers.<Language>lambdaQuery()
                .eq(Language::getLanguageName, submissionDTO.getLanguage().getLanguageName()));
        submissionDTO.setLanguage(language);
        Long submissionId = submissionService.createSubmissionAndJudge(submissionDTO);
        System.out.println(submissionDTO);
        return MessageVO.<String>builder()
                .error(null)
                .data(String.valueOf(submissionId))
                .build();
    }

    /**
     * desc : get single submission
     * @param submissionDTO -
     * @return Message<SubmissionVO>
     */
    @GetMapping("/submission")
    public MessageVO<SubmissionVO> getSubmissionInfo(SubmissionDTO submissionDTO) {
        Record record = recordService.getById(submissionDTO.getSubmissionId());
        if (record == null) {
            return MessageVO.<SubmissionVO>builder()
                    .error(null)
                    .data(new SubmissionVO(null, 9))
                    .build();
        } else {
            SubmissionVO submissionVO = new SubmissionVO(record, record.getStatus());
            return MessageVO.<SubmissionVO>builder()
                    .error(null)
                    .data(submissionVO)
                    .build();
        }
    }

    @PutMapping("/submission")
    public MessageVO<SubmissionVO> updateSubmission(SubmissionDTO submissionDTO) {
        return MessageVO.<SubmissionVO>builder()
                .error(null)
                .data(new SubmissionVO())
                .build();
    }

    @GetMapping("/submissions")
    public MessageVO<SubmissionListVO> PagingSubmissionListVO(SubmissionParametersDTO submissionParametersDTO) {
        Page<Record> page = new Page<>(submissionParametersDTO.getPage(), submissionParametersDTO.getLimit());
        IPage<Record> results = null;
        if (submissionParametersDTO.getMyself() == 0) {
            results = recordService.listRecordByPaging(page);
        } else {
            results = recordService.listRecordByUid(page, submissionParametersDTO.getUsername());
        }
        return MessageVO.<SubmissionListVO>builder()
                .error(null)
                .data(SubmissionListVO.builder()
                        .results(results.getRecords())
                        .total(results.getTotal())
                        .build())
                .build();
    }
}
