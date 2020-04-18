package cn.edu.nciae.contentcenter.controller;


import cn.edu.nciae.contentcenter.common.entity.Language;
import cn.edu.nciae.contentcenter.common.vo.LanguageListVO;
import cn.edu.nciae.contentcenter.common.vo.MessageVO;
import cn.edu.nciae.contentcenter.service.ILanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author RexALun
 * @since 2020-03-05
 */
@RestController
public class LanguageController {

    @Autowired
    private ILanguageService languageService;

    @GetMapping("/public/languages")
    public MessageVO<LanguageListVO> getLanguageList() {
        List<Language> languages = languageService.list();
        return MessageVO.<LanguageListVO>builder().error(null)
                .data(LanguageListVO.builder().results(languages).build())
                .build();
    }

}
