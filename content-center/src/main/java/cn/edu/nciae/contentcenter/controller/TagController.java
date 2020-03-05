package cn.edu.nciae.contentcenter.controller;


import cn.edu.nciae.contentcenter.common.entity.Tag;
import cn.edu.nciae.contentcenter.common.vo.MessageVO;
import cn.edu.nciae.contentcenter.service.ITagService;
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
 * @since 2020-02-08
 */
@RestController
public class TagController {

    @Autowired
    private ITagService tagService;

    @GetMapping("/tags")
    public MessageVO<List<Tag>> getTagList() {
        return MessageVO.<List<Tag>>builder()
                .error(null)
                .data(tagService.list())
                .build();
    }
}
