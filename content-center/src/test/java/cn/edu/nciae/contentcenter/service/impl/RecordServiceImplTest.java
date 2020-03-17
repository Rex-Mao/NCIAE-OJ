package cn.edu.nciae.contentcenter.service.impl;

import cn.edu.nciae.contentcenter.common.entity.Language;
import cn.edu.nciae.contentcenter.common.entity.Record;
import cn.edu.nciae.contentcenter.service.ILanguageService;
import cn.edu.nciae.contentcenter.service.IRecordService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/** 
* RecordServiceImpl Tester. 
* 
* @author RexALun 
* @since <pre>Mar 17, 2020</pre> 
* @version 1.0 
*/ 
@SpringBootTest
@RunWith(SpringRunner.class)
public class RecordServiceImplTest {

    @Autowired
    private IRecordService recordService;

    @Autowired
    private ILanguageService languageService;

    @Test
    public void insertTest() {
        Language language = languageService.getOne(Wrappers.<Language>lambdaQuery().eq(Language::getLanguageId, 1L));
        Record record = new Record();
        record.setRecordId(524524524524534L);
        record.setCid(0L);
        record.setCommitTime(new Date());
        record.setCommitUid(1L);
        record.setPid(1L);
        record.setSourceCode("adfadf");
        record.setStatus(0);
        record.setUsedMemory(0.0);
        record.setUsedTime(0.0);
        record.setLanguageId(language.getLanguageId());
        recordService.save(record);
    }
    
} 
