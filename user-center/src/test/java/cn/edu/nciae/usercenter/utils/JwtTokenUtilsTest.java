package cn.edu.nciae.usercenter.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

/** 
* JwtTokenUtils Tester. 
* 
* @author RexALun 
* @since <pre>Apr 6, 2020</pre> 
* @version 1.0 
*/ 
@SpringBootTest
@RunWith(SpringRunner.class)
public class JwtTokenUtilsTest { 

    @Before
    public void before() throws Exception { 
    } 
    
    @After
    public void after() throws Exception { 
    } 
    
    /** 
     * 
     * Method: init() 
     * 
     */ 
    @Test
    public void testInit() throws Exception { 
        //TODO: Test goes here... 
    } 

    /** 
     * 
     * Method: createToken(Authentication authentication) 
     * 
     */ 
    @Test
    public void testCreateToken() throws Exception { 
        //TODO: Test goes here... 
    } 

    /** 
     * 
     * Method: getAuthentication(String token) 
     * 
     */ 
    @Test
    public void testGetAuthentication() throws Exception { 
        //TODO: Test goes here... 
    } 

    /** 
     * 
     * Method: validateToken(String token) 
     * 
     */ 
    @Test
    public void testValidateToken() throws Exception { 
        //TODO: Test goes here... 
    } 

    
    /** 
     * 
     * Method: getSecretKey() 
     * 
     */ 
    @Test
    public void testGetSecretKey() throws Exception { 
        //TODO: Test goes here...
        SecretKey secretKey = KeyGenerator.getInstance("DES").generateKey();
        // get base64 encoded version of the key
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println(encodedKey);
                /* 
                try { 
                   Method method = JwtTokenUtils.getClass().getMethod("getSecretKey"); 
                   method.setAccessible(true); 
                   method.invoke(<Object>, <Parameters>); 
                } catch(NoSuchMethodException e) { 
                } catch(IllegalAccessException e) { 
                } catch(InvocationTargetException e) { 
                } 
                */ 
    }

} 
