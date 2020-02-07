package cn.edu.nciae.contentcenter.utils;

import cn.edu.nciae.contentcenter.common.entity.Problem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/** 
* FPSUtils Tester. 
* 
* @author <Authors name> 
* @since <pre>Feb 7, 2020</pre> 
* @version 1.0 
*/ 
public class FPSUtilsTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: FPS2Problems(Long uid, String filepath) 
* 
*/ 
@Test
public void testFPS2Problems() throws Exception { 
//TODO: Test goes here...
    List<Problem> problems = FPSUtils.FPS2Problems(Long.valueOf("1"), "/Users/rexmao/Documents/RexStudio/NCIAE-OJ/Doc/fps-loj-small-pics.xml");
    for(Problem p : problems) {
        System.out.println(p.toString());
    }
} 


/** 
* 
* Method: itemToProblem(Node item) 
* 
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
* 
* Method: parseXML(String filepath) 
* 
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
