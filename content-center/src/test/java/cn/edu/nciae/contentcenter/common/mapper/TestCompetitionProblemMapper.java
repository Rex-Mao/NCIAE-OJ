package cn.edu.nciae.contentcenter.common.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/4/25 12:09 PM
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestCompetitionProblemMapper {
    
    @Autowired
    private CompetitionProblemMapper competitionProblemMapper;

    @Test
    public void testBaseMapper() {
    }
}
