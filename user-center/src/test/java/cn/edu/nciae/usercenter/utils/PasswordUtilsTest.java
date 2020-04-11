package cn.edu.nciae.usercenter.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/** 
* PasswordUtils Tester. 
* 
* @author RexALun 
* @since <pre>Apr 5, 2020</pre> 
* @version 1.0 
*/ 
@SpringBootTest
@RunWith(SpringRunner.class)
public class PasswordUtilsTest {

    @Autowired
    private PasswordUtils passwordUtils;

    @Before
    public void before() throws Exception { 
    } 
    
    @After
    public void after() throws Exception { 
    } 
    
    /** 
     * 
     * Method: encode(CharSequence charSequence) 
     * 
     */ 
    @Test
    public void testEncode() throws Exception {
        System.out.println(passwordUtils.encode("123456"));
    } 

    /** 
     * 
     * Method: matches(CharSequence charSequence, String s) 
     * 
     */ 
    @Test
    public void testMatches() throws Exception { 
        //TODO: Test goes here... 
    } 

    
} 
