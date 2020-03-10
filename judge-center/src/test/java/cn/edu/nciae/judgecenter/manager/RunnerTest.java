package cn.edu.nciae.judgecenter.manager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/** 
* Runner Tester. 
* 
* @author RexALun 
* @since <pre>Mar 10, 2020</pre> 
* @version 1.0 
*/ 
@SpringBootTest
@RunWith(SpringRunner.class)
public class RunnerTest {

    @Autowired
    private Runner runner;
    
    /** 
     * 
     * Method: getRuntimeResult(SubmissionDTO submissionDTO, String workDirectory, String baseFileName, String inputFilePath, String outputFilePath) 
     * 
     */ 
    @Test
    public void testGetRuntimeResultForSubmissionDTOWorkDirectoryBaseFileNameInputFilePathOutputFilePath() throws Exception { 
        //TODO: Test goes here... 
    } 

    /** 
     * 
     * Method: getRuntimeResult(String commandLine, String inputFilePath, String outputFilePath, int timeLimit, int memoryLimit) 
     * 
     */ 
    @Test
    public void testGetRuntimeResultForCommandLineInputFilePathOutputFilePathTimeLimitMemoryLimit() throws Exception {

        //TODO: Test goes here...
    } 

    /** 
     * 
     * Method: getRuntimeResult(String commandLine, String systemUsername, String systemPassword, String inputFilePath, String outputFilePath, int timeLimit, int memoryLimit) 
     * 
     */ 
    @Test
    public void testGetRuntimeResultForCommandLineSystemUsernameSystemPasswordInputFilePathOutputFilePathTimeLimitMemoryLimit() throws Exception {
        String commandLine = "g++-7 -O2 -s -Wall -std=c++11 -o /Users/rexmao/Documents/RexStudio/NCIAE-OJ-Data/workDir/judge-686949447627837440/biEaTJQgyUEq.out /Users/rexmao/Documents/RexStudio/NCIAE-OJ-Data/workDir/judge-686949447627837440/biEaTJQgyUEq.cpp -lm";
        String inputFilePath = null;
        String outputFilePath = "/Users/rexmao/Documents/RexStudio/NCIAE-OJ-Data/workDir/judge-686949447627837440/biEaTJQgyUEq-compile.log";
        Map<String, Object> results = null;
        try {
             results = runner.getRuntimeResult(commandLine, "root", "lyyh521A", inputFilePath, outputFilePath, 1000, 256);
        } catch (UnsatisfiedLinkError error) {
            error.printStackTrace();
        }
        if (results != null) {
            for (String key : results.keySet()) {
                System.out.println(results.get(key));
            }
        }
    }

    
    /** 
     * 
     * Method: getCommandLineForRun(SubmissionDTO submissionDTO, String workDirectory, String baseFileName) 
     * 
     */ 
    @Test
    public void testGetCommandLineForRun() throws Exception { 
    //TODO: Test goes here... 
                /* 
                try { 
                   Method method = Runner.getClass().getMethod("getCommandLineForRun", SubmissionDTO.class, String.class, String.class); 
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
     * Method: getTimeLimit(SubmissionDTO submissionDTO) 
     * 
     */ 
    @Test
    public void testGetTimeLimit() throws Exception { 
    //TODO: Test goes here... 
                /* 
                try { 
                   Method method = Runner.getClass().getMethod("getTimeLimit", SubmissionDTO.class); 
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
     * Method: getMemoryLimit(SubmissionDTO submissionDTO) 
     * 
     */ 
    @Test
    public void testGetMemoryLimit() throws Exception { 
    //TODO: Test goes here... 
                /* 
                try { 
                   Method method = Runner.getClass().getMethod("getMemoryLimit", SubmissionDTO.class); 
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
     * Method: getRuntimeResultSlug(int exitCode, int timeLimit, int timeUsed, int memoryLimit, int memoryUsed) 
     * 
     */ 
    @Test
    public void testGetRuntimeResultSlug() throws Exception { 
    //TODO: Test goes here... 
                /* 
                try { 
                   Method method = Runner.getClass().getMethod("getRuntimeResultSlug", int.class, int.class, int.class, int.class, int.class); 
                   method.setAccessible(true); 
                   method.invoke(<Object>, <Parameters>); 
                } catch(NoSuchMethodException e) { 
                } catch(IllegalAccessException e) { 
                } catch(InvocationTargetException e) { 
                } 
                */ 
            } 

} 
