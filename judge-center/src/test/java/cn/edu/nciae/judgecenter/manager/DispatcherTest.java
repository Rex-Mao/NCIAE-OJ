package cn.edu.nciae.judgecenter.manager;

import cn.edu.nciae.judgecenter.common.dto.SubmissionDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/** 
* Dispatcher Tester. 
* 
* @author RexALun 
* @since <pre>Mar 4, 2020</pre> 
* @version 1.0 
*/ 
@SpringBootTest
@RunWith(SpringRunner.class)
public class DispatcherTest {

    @Autowired
    private Dispatcher dispatcher;

    @Before
    public void before() throws Exception { 
    } 
    
    @After
    public void after() throws Exception { 
    } 
    
    /** 
     * 
     * Method: createNewTask(SubmissionDTO submissionDTO) 
     * 
     */ 
    @Test
    public void testCreateNewTask() throws Exception {
        dispatcher.createNewTask(SubmissionDTO.builder().build());
        //TODO: Test goes here... 
    } 

} 
