package cn.edu.nciae.contentcenter.controller;

import org.junit.Test;

import java.util.Calendar;

/**
 * ProblemController Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Feb 8, 2020</pre>
 */
public class ProblemControllerTest {

    /**
     * Method: getProblemList(@RequestParam("paging") Boolean paging, @RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit, ParametersDTO parametersDTO)
     */
    @Test
    public void testGetProblemList() throws Exception {
        String temp = "2009-3-5";
        int year = Integer.parseInt(temp.split("-")[0]);
        int month = Integer.parseInt(temp.split("-")[1]);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH, month-1);
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println(year +"-"+ month +"-1");
        System.out.println(year +"-"+ month +"-"+lastDay);
    }

} 
