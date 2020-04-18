package cn.edu.nciae.usercenter.security.service; 

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;

/** 
* UserDetailsServiceImpl Tester. 
* 
* @author RexALun 
* @since <pre>Apr 18, 2020</pre> 
* @version 1.0 
*/ 
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserDetailsServiceImplTest {

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;
    
    /** 
     * 
     * Method: loadUserByUsername(String username) 
     * 
     */ 
    @Test
    public void testLoadUserByUsername() throws Exception { 
        System.out.println(userDetailsService.loadUserByUsername("Rex"));
    }
} 
