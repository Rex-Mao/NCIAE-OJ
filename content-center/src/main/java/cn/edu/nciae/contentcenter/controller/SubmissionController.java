package cn.edu.nciae.contentcenter.controller;

import cn.edu.nciae.contentcenter.common.dto.SubmissionDTO;
import cn.edu.nciae.contentcenter.common.entity.Language;
import cn.edu.nciae.contentcenter.common.vo.MessageVO;
import cn.edu.nciae.contentcenter.common.vo.SubmissionVO;
import cn.edu.nciae.contentcenter.service.ILanguageService;
import cn.edu.nciae.contentcenter.service.ISubmissionService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
@RestController
public class SubmissionController {

    @Autowired
    private ISubmissionService submissionService;

    @Autowired
    private ILanguageService languageService;

    @PostMapping("/submission")
    public MessageVO<String> judgeSubmissionAndReturnSID(SubmissionDTO submissionDTO) throws IOException {

        Language language = languageService.getOne(Wrappers.<Language>lambdaQuery()
                .eq(Language::getLanguageName, submissionDTO.getLanguage().getLanguageName()));
        submissionDTO.setLanguage(language);
        String submissionID = submissionService.createSubmissionAndJudge(submissionDTO);
        System.out.println(submissionDTO);
        return MessageVO.<String>builder()
                .error(null)
                .data(submissionID)
                .build();
    }

    @GetMapping("/submission")
    public MessageVO<SubmissionVO> getSubmissionInfo(SubmissionDTO submissionDTO) {
        return MessageVO.<SubmissionVO>builder()
                .error(null)
                .data(new SubmissionVO())
                .build();
    }

    @PutMapping("/submission")
    public MessageVO<SubmissionVO> updateSubmission(SubmissionDTO submissionDTO) {
        return MessageVO.<SubmissionVO>builder()
                .error(null)
                .data(new SubmissionVO())
                .build();
    }
}
