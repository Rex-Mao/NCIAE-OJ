package cn.edu.nciae.contentcenter.utils;

import cn.edu.nciae.contentcenter.common.entity.Checkpoint;
import cn.edu.nciae.contentcenter.common.entity.Problem;
import cn.edu.nciae.contentcenter.common.entity.Sample;
import cn.edu.nciae.contentcenter.common.mapper.ProblemMapper;
import cn.edu.nciae.contentcenter.common.mapper.SampleMapper;
import cn.edu.nciae.contentcenter.common.vo.ProblemVO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * FPSUtils Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Feb 7, 2020</pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FPSUtilsTest {

    @Autowired
    private ProblemMapper problemMapper;
    @Autowired
    private SampleMapper sampleMapper;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: FPS2Problems(Long uid, String filepath)
     */
    @Test
    public void testFPS2ProblemVO() throws Exception {
        List<ProblemVO> problemVOList = FPSUtils.fps2ProblemVO(Long.valueOf("1"), "/Users/rexmao/Documents/RexStudio/NCIAE-OJ/Doc/standard-test-fps.xml");
        for (ProblemVO p : problemVOList) {
            System.out.println(p.toString());
            Problem problem = p.unzipProblemVO();
//            problemMapper.insert(problem);
            for (Sample sample : p.getSamples()) {
                sample.setPid(problem.getPid());
//                sampleMapper.insert(sample);
                System.out.println(sample.getSid());
            }
            for (Checkpoint checkpoint : p.getCheckpoints()) {
                System.out.println(checkpoint);
            }
            System.out.println(problem.getPid());
        }
    }


    /**
     * Method: itemToProblem(Node item)
     */
    @Test
    public void testItemToProblem() throws Exception {
        //TODO: Test goes here...
        /*
        try {
           Method method = FPSUtils.getClass().getMethod("itemToProblem", Node.class);
           method.setAccessible(true);
           method.invoke(<Object>, <Parameters>);
        } catch(NoSuchMethodException e) {
        } catch(IllegalAccessException e) {
        } catch(InvocationTargetException e) {
        }
        */
    }

    /**
     * Method: parseXML(String filepath)
     */
    @Test
    public void testParseXML() throws Exception {
        //TODO: Test goes here...
        /*
        try {
           Method method = FPSUtils.getClass().getMethod("parseXML", String.class);
           method.setAccessible(true);
           method.invoke(<Object>, <Parameters>);
        } catch(NoSuchMethodException e) {
        } catch(IllegalAccessException e) {
        } catch(InvocationTargetException e) {
        }
        */
    }

} 
