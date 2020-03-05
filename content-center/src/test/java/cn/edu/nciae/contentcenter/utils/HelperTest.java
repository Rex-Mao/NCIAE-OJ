package cn.edu.nciae.contentcenter.utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * FPSUtils Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Feb 7, 2020</pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HelperTest {

    @Test
    public void hashMapTest() {
        Map<String, String> stringMap = new HashMap<String, String>();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                for (int j = 0; j < 3; j++) {
                    stringMap.put(Thread.currentThread().getName(), "j = " + String.valueOf(j));
                    System.out.println(stringMap);
                }
            }).start();
        }
    }

    @Test
    public void concurentHashMapTest() {
        Map<String, String> stringMap = new ConcurrentHashMap<String, String>();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                for (int j = 0; j < 3; j++) {
                    stringMap.put(Thread.currentThread().getName(), "j = " + String.valueOf(j));
                    System.out.println(stringMap);
                }
            }).start();
        }
    }

    @Test
    public void hashCodeAndEqualsTest() {
        String username = "RexALun";
        username.equals("RexALun");
    }

}
